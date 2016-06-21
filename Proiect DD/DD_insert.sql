--SHIPPING
INSERT INTO SHIPPING (ID, DESCRIPTION) VALUES (1, 'Plane');
INSERT INTO SHIPPING (ID, DESCRIPTION) VALUES (2, 'Truck');
INSERT INTO SHIPPING (ID, DESCRIPTION) VALUES (3, 'Train');
INSERT INTO SHIPPING (ID, DESCRIPTION) VALUES (4, 'Boat');

--CATEGORY
INSERT INTO CATEGORY (ID, NAME) VALUES (1, 'Food');
INSERT INTO CATEGORY (ID, NAME) VALUES (2, 'Pets');
INSERT INTO CATEGORY (ID, NAME) VALUES (3, 'Antiques');
INSERT INTO CATEGORY (ID, NAME) VALUES (4, 'Art');
INSERT INTO CATEGORY (ID, NAME) VALUES (5, 'Audio');
INSERT INTO CATEGORY (ID, NAME) VALUES (6, 'Watches');
INSERT INTO CATEGORY (ID, NAME) VALUES (7, 'Gaming');
INSERT INTO CATEGORY (ID, NAME) VALUES (8, 'Fashion');
INSERT INTO CATEGORY (ID, NAME) VALUES (9, 'TV');
INSERT INTO CATEGORY (ID, NAME) VALUES (10, 'Toys');
INSERT INTO CATEGORY (ID, NAME) VALUES (11, 'Tools');
INSERT INTO CATEGORY (ID, NAME) VALUES (12, 'Small Appliances');
INSERT INTO CATEGORY (ID, NAME) VALUES (13, 'Big Appliances');

--PERSON
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, ADDRESS)
 VALUES(1, 'Rodney', 'Kresge', '3853 Boone Crockett Lane Bremerton, WA 98337');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, ADDRESS)
 VALUES(2, 'Linda', 'White', '995 Stoneybrook Road Cocoa, FL 32922');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, ADDRESS)
 VALUES(3, 'Catherine', 'Maynard', '3433 Don Jackson Lane Flint, MI 48502');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, ADDRESS)
 VALUES(4, 'Robert', 'Ruiz', '3150 Tori Lane Salt Lake City, UT 84111');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, ADDRESS)
 VALUES(5, 'Elizabeth', 'Robb', '3174 Davis Avenue Concord, CA 94520');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, ADDRESS)
 VALUES(6, 'Abigail', 'Greer', '4910 Mulberry Avenue Little Rock, AR 72212');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, ADDRESS)
 VALUES(7, 'Edwin', 'Royal', '268 Bates Brothers Road Columbus, OH 43214');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, ADDRESS)
 VALUES(8, 'Erik', 'Muse', '2249 Riverwood Drive Sacramento, CA 95814');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, ADDRESS)
 VALUES(9, 'Kyle', 'Murphy', '3260 Poe Road Florence, SC 29501');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, ADDRESS)
 VALUES(10, 'Edward', 'Harris', '3562 Ocala Street Oviedo, FL 32765');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, ADDRESS)
 VALUES(11, 'Shirley', 'Moore', '2178 Commerce Boulevard Shelby, NE 68662');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, ADDRESS)
 VALUES(12, 'Marshall', 'Birmingham', '470 Cessna Drive Fort Wayne, IN 46802');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, ADDRESS)
 VALUES(13, 'Gabrielle', 'Kemp', '1746 Thompson Street Irvine, CA 92614');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, ADDRESS)
 VALUES(14, 'Thomas', 'Vasquez', '2996 Sharon Lane South Bend, IN 46601');
INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, ADDRESS)
 VALUES(15, 'Clarence', 'Hung', '1547 Deans Lane New York, NY 10011');

--AUCTIONEER
INSERT INTO AUCTIONEER(PERSON_ID, PHONE_NUMBER, EMAIL)
 VALUES(1, '2259374372', 'rodney_kresge@gmail.com');
INSERT INTO AUCTIONEER(PERSON_ID, PHONE_NUMBER, EMAIL)
 VALUES(2, '4157536792', 'linda_white@gmail.com');
INSERT INTO AUCTIONEER(PERSON_ID, PHONE_NUMBER, EMAIL)
 VALUES(3, '5016420095', 'catherine_maynard@yahoo.com');
INSERT INTO AUCTIONEER(PERSON_ID, PHONE_NUMBER, EMAIL)
 VALUES(4, '7817256147', 'robert_ruiz@yahoo.com');
INSERT INTO AUCTIONEER(PERSON_ID, PHONE_NUMBER, EMAIL)
 VALUES(5, '8582704247', 'elisabeth_rob@yahoo.com');
INSERT INTO AUCTIONEER(PERSON_ID, PHONE_NUMBER, EMAIL)
 VALUES(6, '4510349240', 'abigail_greer@gmail.com');
INSERT INTO AUCTIONEER(PERSON_ID, PHONE_NUMBER, EMAIL)
 VALUES(7, '4952537597', 'edwin_royal@yahoo.com');
INSERT INTO AUCTIONEER(PERSON_ID, PHONE_NUMBER, EMAIL)
 VALUES(8, '4842766812', 'erik_muse@gmail.com');
INSERT INTO AUCTIONEER(PERSON_ID, PHONE_NUMBER, EMAIL)
 VALUES(9, '3176498852', 'kyle_m	urphy@gmail.com');
INSERT INTO AUCTIONEER(PERSON_ID, PHONE_NUMBER, EMAIL)
 VALUES(10, '9953081582', 'edward_harris@yahoo.com');
INSERT INTO AUCTIONEER(PERSON_ID, PHONE_NUMBER, EMAIL)
 VALUES(11, '5806395477', 'wilma_brown@gmail.com');
INSERT INTO AUCTIONEER(PERSON_ID, PHONE_NUMBER, EMAIL)
 VALUES(12, '2154769021', 'marshall_brim@gmail.com');
INSERT INTO AUCTIONEER(PERSON_ID, PHONE_NUMBER, EMAIL)
 VALUES(13, '1492583001', 'gabrielle_kemp@gmail.com');
INSERT INTO AUCTIONEER(PERSON_ID, PHONE_NUMBER, EMAIL)
 VALUES(14, '2051843255', 'thomas_vasq@yahoo.com');
INSERT INTO AUCTIONEER(PERSON_ID, PHONE_NUMBER, EMAIL)
 VALUES(15, '8551266925', 'clarence_hung@gmail.com');

--PAYMENT
INSERT INTO PAYMENT(ID, NAME) VALUES(1, 'Cash');
INSERT INTO PAYMENT(ID, NAME) VALUES(2, 'Credit card');
INSERT INTO PAYMENT(ID, NAME) VALUES(3, 'Paypal');
INSERT INTO PAYMENT(ID, NAME) VALUES(4, 'Google Wallet');
INSERT INTO PAYMENT(ID, NAME) VALUES(5, 'WePay');

--TYPE
INSERT INTO TYPE(ID, NAME) VALUES(1, 'Public');
INSERT INTO TYPE(ID, NAME) VALUES(2, 'Private');

--AUCTION_ITEM
INSERT INTO AUCTION_ITEM(ID, DESCRIPTION, START_PRICE, OPTIMAL_PRICE, CATEGORY_ID, TYPE_ID, PAYMENT_ID, WINNER_ID, OWNER_ID)
 VALUES (1, 'Dog, Golden Retriever, named Ruby; Intelligent, playful', 100, 250, 2, 1, 2, NULL, 3);
INSERT INTO AUCTION_ITEM(ID, DESCRIPTION, START_PRICE, OPTIMAL_PRICE, CATEGORY_ID, TYPE_ID, PAYMENT_ID, WINNER_ID, OWNER_ID)
 VALUES (2, 'Chinese antiquity from XVI centiry', 350, 500, 3, 2, 1, NULL, 10);
INSERT INTO AUCTION_ITEM(ID, DESCRIPTION, START_PRICE, OPTIMAL_PRICE, CATEGORY_ID, TYPE_ID, PAYMENT_ID, WINNER_ID, OWNER_ID)
 VALUES (3, 'DOM PERIGNON, 1995', 150, 300, 1, 1, 5, NULL, 4);
INSERT INTO AUCTION_ITEM(ID, DESCRIPTION, START_PRICE, OPTIMAL_PRICE, CATEGORY_ID, TYPE_ID, PAYMENT_ID, WINNER_ID, OWNER_ID)
 VALUES (4, 'Royal Red and Blue', 780, 1000, 4, 2, 1, NULL, 7);
INSERT INTO AUCTION_ITEM(ID, DESCRIPTION, START_PRICE, OPTIMAL_PRICE, CATEGORY_ID, TYPE_ID, PAYMENT_ID, WINNER_ID, OWNER_ID)
 VALUES (5, 'Sony audio system, 2015', 200, 250, 5, 1, 3, NULL, 3);
INSERT INTO AUCTION_ITEM(ID, DESCRIPTION, START_PRICE, OPTIMAL_PRICE, CATEGORY_ID, TYPE_ID, PAYMENT_ID, WINNER_ID, OWNER_ID)
 VALUES (6, 'Samsung Smart TV', 250, 350, 9, 1, 2, NULL, 10);
INSERT INTO AUCTION_ITEM(ID, DESCRIPTION, START_PRICE, OPTIMAL_PRICE, CATEGORY_ID, TYPE_ID, PAYMENT_ID, WINNER_ID, OWNER_ID)
 VALUES (7, 'Nvidia GTX 1080', 1000, 1100, 7, 1, 1, NULL, 1); 
INSERT INTO AUCTION_ITEM(ID, DESCRIPTION, START_PRICE, OPTIMAL_PRICE, CATEGORY_ID, TYPE_ID, PAYMENT_ID, WINNER_ID, OWNER_ID)
 VALUES (8, 'Xbox One, 1TB', 750, 810, 7, 1, 1, NULL, 1); 
INSERT INTO AUCTION_ITEM(ID, DESCRIPTION, START_PRICE, OPTIMAL_PRICE, CATEGORY_ID, TYPE_ID, PAYMENT_ID, WINNER_ID, OWNER_ID)
 VALUES (9, 'Train toy made of wood', 50, 70, 10, 1, 2, NULL, 7);
INSERT INTO AUCTION_ITEM(ID, DESCRIPTION, START_PRICE, OPTIMAL_PRICE, CATEGORY_ID, TYPE_ID, PAYMENT_ID, WINNER_ID, OWNER_ID)
 VALUES (10, 'Canadian winter jacket', 150, 170, 8, 1, 4, NULL, 3);
INSERT INTO AUCTION_ITEM(ID, DESCRIPTION, START_PRICE, OPTIMAL_PRICE, CATEGORY_ID, TYPE_ID, PAYMENT_ID, WINNER_ID, OWNER_ID)
 VALUES (11, 'Rolex Submariner', 1500, 2100, 6, 2, 2, NULL, 5);
INSERT INTO AUCTION_ITEM(ID, DESCRIPTION, START_PRICE, OPTIMAL_PRICE, CATEGORY_ID, TYPE_ID, PAYMENT_ID, WINNER_ID, OWNER_ID)
 VALUES (12, 'Samsung Refrigerator, 2010', 270, 300, 13, 1, 1, NULL, 11);
INSERT INTO AUCTION_ITEM(ID, DESCRIPTION, START_PRICE, OPTIMAL_PRICE, CATEGORY_ID, TYPE_ID, PAYMENT_ID, WINNER_ID, OWNER_ID)
 VALUES (13, 'No-name toaster', 80, 100, 12, 1, 1, NULL, 15);
INSERT INTO AUCTION_ITEM(ID, DESCRIPTION, START_PRICE, OPTIMAL_PRICE, CATEGORY_ID, TYPE_ID, PAYMENT_ID, WINNER_ID, OWNER_ID)
 VALUES (14, 'Metal shovel', 20, 30, 11, 1, 1, NULL, 8);
INSERT INTO AUCTION_ITEM(ID, DESCRIPTION, START_PRICE, OPTIMAL_PRICE, CATEGORY_ID, TYPE_ID, PAYMENT_ID, WINNER_ID, OWNER_ID)
 VALUES (15, 'Custom made PC, Intel i7 4700K, nVidia GTX 980', 700, 800, 7, 1, 5, NULL, 14);
	
--AUCTION_ITEM_SHIPPING
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(1, 1);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(1, 2);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(2, 4);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(3, 1);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(3, 2);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(4, 4);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(5, 3);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(5, 4);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(6, 1);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(7, 1);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(8, 3);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(8, 4);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(9, 1);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(9, 2);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(9, 4);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(10, 1);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(11, 2);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(12, 4);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(13, 4);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(14, 2);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(14, 3);
INSERT INTO AUCTION_ITEM_SHIPPING(AUCTION_ITEM_ID, SHIPPING_ID) VALUES(15, 1);

--BID

	--AUCTION_ITEM 1
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(1,10,110); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(1,15,130);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(1,4,150);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(1,10,165);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(1,8,200);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(1,10,240);

	--AUCTION_ITEM 2
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(2,6,350); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(2,1,365); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(2,3,400); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(2,6,410); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(2,8,440); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(2,9,460); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(2,14,480); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(2,3,500); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(2,2,510); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(2,13,550);

	--AUCTION_ITEM 3
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(3,4,150);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(3,9,180);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(3,4,190);

	--AUCTION_ITEM 4
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(4,6,780); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(4,8,800);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(4,15,860);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(4,11,870);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(4,15,910);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(4,3,950);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(4,2,960);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(4,4,980);

	--AUCTION_ITEM 5
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(5,6,200); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(5,1,210);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(5,8,230);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(5,7,235);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(5,5,255);

	--AUCTION_ITEM 6
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(6,8,250); 

	--AUCTION_ITEM 7
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(7,8,1000);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(7,4,1100);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(7,9,1300);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(7,11,1350);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(7,14,1400);


	--AUCTION_ITEM 8
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(8,8,750); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(8,2,760);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(8,6,780);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(8,10,810);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(8,14,830);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(8,2,835);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(8,4,840);

	--AUCTION_ITEM 9
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(9,3,50); 

	--AUCTION_ITEM 10
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(10,8,150); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(10,4,160);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(10,8,180);
	
	--AUCTION_ITEM 11
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(11,7,1500); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(11,1,1650); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(11,10,1800); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(11,15,2000); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(11,11,2100); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(11,3,2200); 
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(11,4,2300);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(11,15,2350);  

	--AUCTION_ITEM 12
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(12,15,270);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(12,12,280);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(12,15,300);

	--AUCTION_ITEM 13
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(13,1,80);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(13,8,90);
	
	--AUCTION_ITEM 14
--NO ONE AUCTIONED
	
	--AUCTION_ITEM 15
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(15,10,700);
INSERT INTO BID(AUCTION_ITEM_ID, AUCTIONEER_ID, VALUE) VALUES(15,2,750);

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	