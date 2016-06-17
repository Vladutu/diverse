-- INDEX
CREATE UNIQUE INDEX category_idx
	ON "CATEGORY" (name);

-- PROCEDURES
CREATE OR REPLACE PROCEDURE SetWinner
	(item_id IN NUMBER)
IS
	auctioneer_id NUMBER;
	
	cursor c1 is
	SELECT AUCTIONEER_ID
		FROM AUCTION_ITEM_AUCTIONEER
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

CREATE OR REPLACE TYPE NUMBER_LIST AS TABLE  OF NUMBER;
CREATE OR REPLACE PROCEDURE AddShipping
	(item_id IN NUMBER, shippings IN NUMBER_LIST) IS
BEGIN
	FOR i IN 1..shippings.COUNT LOOP
		INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID)
			VALUES (item_id, shippings(i));
	END LOOP;
END;

-- TRIGGER
CREATE OR REPLACE TRIGGER bigger_value_trigger BEFORE INSERT OR UPDATE ON AUCTION_ITEM_AUCTIONEER
	FOR EACH ROW
	
DECLARE
	max_value NUMBER;
BEGIN
	SELECT MAX(VALUE) INTO max_value
		FROM AUCTION_ITEM_AUCTIONEER
		WHERE AUCTION_ITEM_ID = :NEW.AUCTION_ITEM_ID;
	
	IF :NEW.VALUE <= max_value THEN 
		RAISE_APPLICATION_ERROR(-20002, 'Please enter a higher bet');
	END IF;
END;

	
-- REMOVE ABOVE
DROP INDEX category_idx;
DROP TRIGGER bigger_value_trigger;
DROP PROCEDURE SetWinner;
DROP PROCEDURE AddShipping;


-- CALL PROCEDURE
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
