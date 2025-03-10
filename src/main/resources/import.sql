INSERT INTO Postcode(postcode, latitude, longitude)
SELECT * FROM CSVREAD('classpath:data/ukpostcodes.csv');
