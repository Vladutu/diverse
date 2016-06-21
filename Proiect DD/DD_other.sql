-- INDEX
CREATE UNIQUE INDEX category_idx
	ON "CATEGORY" (NAME);

CREATE UNIQUE INDEX bix_idx
	ON "BID" (VALUE);
-- END INDEX

-- PROCEDURES
-- SETS THE WINNER OF ITEM I (PASSED AS PARAMETER)
CREATE OR REPLACE PROCEDURE SetWinner
	(item_id IN NUMBER)
IS
	auctioneer_id NUMBER;
	
	cursor c1 is
	SELECT AUCTIONEER_ID
		FROM BID
		WHERE AUCTION_ITEM_ID = item_id
		ORDER BY VALUE DESC
		FETCH FIRST 1 ROWS ONLY;
		
BEGIN
	OPEN c1;
	FETCH c1 INTO auctioneer_id;
	
	IF c1%NOTFOUND THEN
		auctioneer_id := NULL;
	END IF;
	
	UPDATE AUCTION_ITEM
		SET WINNER_ID = auctioneer_id
		WHERE ID = item_id;
	
	COMMIT;
	CLOSE c1;
	
END;	

-- ADDS THE SHIPPING METHODS TO AN ITEM
CREATE OR REPLACE TYPE NUMBER_LIST AS TABLE  OF NUMBER;
CREATE OR REPLACE PROCEDURE AddShipping
	(item_id IN NUMBER, shippings IN NUMBER_LIST) IS
BEGIN
	FOR i IN 1..shippings.COUNT LOOP
		INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID)
			VALUES (item_id, shippings(i));
	END LOOP;
END;
-- END PROCEDURES

-- TRIGGER
-- CHECKS WHEN A BET IS PLACED IF IT IS HIGHER THAN THE HIGHEST BET PLACED ON THE
-- RESPECTIVE ITEM SO FAR
CREATE OR REPLACE TRIGGER bigger_value_trigger BEFORE INSERT OR UPDATE ON BID
	FOR EACH ROW
	
DECLARE
	max_value NUMBER;
BEGIN
	SELECT MAX(VALUE) INTO max_value
		FROM BID
		WHERE AUCTION_ITEM_ID = :NEW.AUCTION_ITEM_ID;
	
	IF :NEW.VALUE <= max_value THEN 
		RAISE_APPLICATION_ERROR(-20002, 'Please enter a higher bet');
	END IF;
END;
-- END TRIGGER

-- FUNCTIONS
-- COUNTS THE NUMBER OF ACTIONS BY TYPE (PUBLIC OR PRIVATE)
CREATE OR REPLACE FUNCTION CountAuctionsByType(type_in IN TYPE.NAME%TYPE)
RETURN NUMBER
IS
	res NUMBER;
BEGIN
	SELECT COUNT(i.TYPE_ID) INTO res FROM AUCTION_ITEM i INNER JOIN TYPE t ON i.TYPE_ID = t.ID WHERE t.name = type_in ;
RETURN res;
END;

-- RETURNS THE ID OF THE MOST EXPENSIVE ITEM IN THE AUCTION
CREATE OR REPLACE FUNCTION FindMostExpensiveItem
RETURN NUMBER
IS 
	res NUMBER;
BEGIN
	SELECT AUCTION_ITEM_ID INTO res
		FROM BID
		ORDER BY VALUE DESC
		FETCH FIRST 1 ROWS ONLY;
RETURN res;
END;
-- END FUNCTIONS	

-- QUERIES
SELECT DESCRIPTION 
	FROM SHIPPING;
	
SELECT NAME AS CATEGORY_NAME
	FROM CATEGORY
	WHERE NAME LIKE 'A%';
	
SELECT * 
	FROM PERSON
	WHERE ADDRESS LIKE '%CA%'
	ORDER BY FIRST_NAME DESC;
	
SELECT p.ID, p.FIRST_NAME, p.LAST_NAME, p.ADDRESS, a.PHONE_NUMBER, a.EMAIL 
	FROM PERSON p 
	INNER JOIN AUCTIONEER a ON p.ID = a.PERSON_ID
	WHERE a.EMAIL LIKE '%yahoo%';

SELECT * 
	FROM PAYMENT;

SELECT *
	FROM TYPE;

-- SELECT ITEMS W/O FK (WITH WINNER)
SELECT i.ID, i.DESCRIPTION, i.START_PRICE, i.OPTIMAL_PRICE, c.NAME AS CATEGORY, t.NAME AS TYPE , p.NAME AS PAYMENT, o.FIRST_NAME || ' ' || o.LAST_NAME AS OWNER_NAME, wp.FIRST_NAME || ' ' || wp.LAST_NAME AS WINNER_NAME
	FROM AUCTION_ITEM i
	INNER JOIN CATEGORY c ON i.CATEGORY_ID = c.ID
	INNER JOIN TYPE t ON i.TYPE_ID = t.ID
	INNER JOIN PAYMENT p ON i.PAYMENT_ID = p.ID
	INNER JOIN PERSON o ON i.OWNER_ID = o.ID
    INNER JOIN AUCTIONEER w ON i.WINNER_ID = w.PERSON_ID
    INNER JOIN PERSON wp ON w.PERSON_ID = wp.ID
    ORDER BY i.ID;
	
-- SELECT ITEMS W/O FK FROM GAMING CATEGORY(WITH WINNER)
SELECT i.ID, i.DESCRIPTION, i.START_PRICE, i.OPTIMAL_PRICE, c.NAME AS CATEGORY, t.NAME AS TYPE , p.NAME AS PAYMENT, o.FIRST_NAME || ' ' || o.LAST_NAME AS OWNER_NAME, wp.FIRST_NAME || ' ' || wp.LAST_NAME AS WINNER_NAME
	FROM AUCTION_ITEM i
	INNER JOIN CATEGORY c ON i.CATEGORY_ID = c.ID
	INNER JOIN TYPE t ON i.TYPE_ID = t.ID
	INNER JOIN PAYMENT p ON i.PAYMENT_ID = p.ID
	INNER JOIN PERSON o ON i.OWNER_ID = o.ID
    INNER JOIN AUCTIONEER w ON i.WINNER_ID = w.PERSON_ID
    INNER JOIN PERSON wp ON w.PERSON_ID = wp.ID
	WHERE c.NAME = 'Gaming'
    ORDER BY i.ID;

-- SELECT ITEMS THAT THOMES BET ON
SELECT i.DESCRIPTION, b.VALUE 
	FROM BID b
	INNER JOIN AUCTION_ITEM i ON b.AUCTION_ITEM_ID = i.ID
	INNER JOIN AUCTIONEER a ON a.PERSON_ID = b.AUCTIONEER_ID
	INNER JOIN PERSON p ON p.ID = a.PERSON_ID
	WHERE p.FIRST_NAME = 'Thomas'
	ORDER BY i.DESCRIPTION;

-- SELECT HOW MANY BETS DID EVERYONE PLACED
SELECT p.FIRST_NAME || ' ' || p.LAST_NAME AS AUCTIONEER, COUNT(b.VALUE) AS NO_BETS
	FROM BID b
	INNER JOIN AUCTIONEER a ON a.PERSON_ID = b.AUCTIONEER_ID
	INNER JOIN PERSON p ON p.ID = a.PERSON_ID
	GROUP BY p.FIRST_NAME || ' ' || p.LAST_NAME
	ORDER BY NO_BETS;
-- END QUERIES
	

-- CALL PROCEDURES
BEGIN
    SetWinner(1);
END;

DECLARE
	shippings NUMBER_LIST;
BEGIN
	shippings := NUMBER_LIST();
	shippings.EXTEND(3);
	shippings(1) := 2;
	shippings(2) := 3;
	shippings(3) := 4;
	AddShipping(15, shippings);
END;
-- END CALL PROCEDURES

-- CALL FUNCTIONS
BEGIN
DBMS_OUTPUT.PUT_LINE('Public auctions:'||CountAuctionsByType('Public'));
END;

SELECT i.ID, i.DESCRIPTION, i.START_PRICE, i.OPTIMAL_PRICE, c.NAME AS CATEGORY, t.NAME AS TYPE , p.NAME AS PAYMENT, o.FIRST_NAME || ' ' || o.LAST_NAME AS OWNER_NAME, wp.FIRST_NAME || ' ' || wp.LAST_NAME AS WINNER_NAME
	FROM AUCTION_ITEM i
	INNER JOIN CATEGORY c ON i.CATEGORY_ID = c.ID
	INNER JOIN TYPE t ON i.TYPE_ID = t.ID
	INNER JOIN PAYMENT p ON i.PAYMENT_ID = p.ID
	INNER JOIN PERSON o ON i.OWNER_ID = o.ID
    INNER JOIN AUCTIONEER w ON i.WINNER_ID = w.PERSON_ID
    INNER JOIN PERSON wp ON w.PERSON_ID = wp.ID
    WHERE i.ID = FindMostExpensiveItem();
-- END CALL FUNCTIONS

