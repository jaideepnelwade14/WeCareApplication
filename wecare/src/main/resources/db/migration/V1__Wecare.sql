DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`(
    `role_id` int NOT NULL AUTO_INCREMENT,
    `role_name` varchar(8) NOT NULL,
    PRIMARY KEY (`role_id`)
);

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`(
    `admin_id` int NOT NULL AUTO_INCREMENT,
    `admin_name` varchar(40) NOT NULL,
    `email` varchar(60) NOT NULL,
    `password` varchar(255) NOT NULL,
    `phone` varchar(10) NOT NULL,
    `registered_date` datetime NOT NULL,
    `modified_date` datetime DEFAULT NULL,
    `role_id` int NOT NULL,
     PRIMARY KEY (`admin_id`),
     FOREIGN KEY (`role_id`) REFERENCES `roles`(`role_id`)
);

DROP TABLE IF EXISTS `jurisdiction`;
CREATE TABLE `jurisdiction` (
    `jury_id` int NOT NULL AUTO_INCREMENT,
    `area` varchar(30) NOT NULL,
    `ward` varchar(8) NOT NULL,
    `layout` varchar(35) NOT NULL,
    `registered_date` datetime NOT NULL,
    `modified_date` datetime DEFAULT NULL,
    PRIMARY KEY (`jury_id`)
);

DROP TABLE IF EXISTS `officer`;
CREATE TABLE `officer` (
    `officer_id` int NOT NULL AUTO_INCREMENT,
    `officer_name` varchar(40) NOT NULL,
    `address` varchar(255) NOT NULL,
    `phone` varchar(10) NOT NULL,
    `email` varchar(60) NOT NULL,
    `password` varchar(255) NOT NULL,
    `created_date` datetime NOT NULL,
    `modified_date` datetime DEFAULT NULL,
    `jury_id` int DEFAULT NULL,
    `role_id` int NOT NULL,
    PRIMARY KEY (`officer_id`),
    FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
    FOREIGN KEY (`jury_id`) REFERENCES `jurisdiction` (`jury_id`)
);

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `user_id` int NOT NULL AUTO_INCREMENT,
    `user_name` varchar(15) NOT NULL,
    `first_name` varchar(15) NOT NULL,
    `last_name` varchar(15) DEFAULT NULL,
    `phone` varchar(10) NOT NULL,
    `email` varchar(60) NOT NULL,
    `password` varchar(255) NOT NULL,
    `verified` bit(1) NOT NULL,
    `role_id` int NOT NULL,
    `registered_date` datetime NOT NULL,
    `modified_date` datetime DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
);

DROP TABLE IF EXISTS `complaint`;
CREATE TABLE `complaint` (
  `compl_id` int NOT NULL AUTO_INCREMENT,
  `issue` varchar(30) NOT NULL,
  `address` varchar(255) NOT NULL,
  `landmark` varchar(50) NOT NULL,
  `comments` varchar(2000) NOT NULL,
  `response` varchar(1000) DEFAULT NULL,
  `status` enum('WAITING', 'OPEN', 'IN_PROGRESS', 'CLOSED') NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_date` datetime DEFAULT NULL,
  `jury_id` int NOT NULL,
  `officer_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`compl_id`),
  FOREIGN KEY (`officer_id`) REFERENCES `officer` (`officer_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  FOREIGN KEY (`jury_id`) REFERENCES `jurisdiction` (`jury_id`)
);

DROP TABLE IF EXISTS `email_otp`;
CREATE TABLE `email_otp` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(60) NOT NULL,
  `expiration_date` datetime NOT NULL,
  `otp` varchar(6) NOT NULL,
  `creation_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `roles` VALUES (1,'ADMIN'),(2,'USER'),(3,'OFFICER');

INSERT INTO `admin` VALUES (1,'Jaideep','nelwadej@gmail.com','$2a$12$E0NyjteYLoBnwW2yl4S8seTlJ1ohurytbakwcGj75oQbTSDQ8QGqm','8431767022','2023-12-28 11:05:50',NULL,1);

INSERT INTO `jurisdiction` VALUES (1,'Basaveshwara Nagar','Ward-100','Gayathri Layout','2024-01-10 10:31:43',NULL),
				  (2,'Basaveshwara Nagar','Ward-100','Bhadrappa Layout','2024-01-10 10:32:21',NULL),
				  (3,'Basaveshwara Nagar','Ward-100','BEML Layout','2024-01-10 10:32:35',NULL),
				  (4,'Kamakshipalya','Ward-101','Priyadarshini Layout','2024-01-10 10:33:16',NULL),
				  (5,'Kamakshipalya','Ward-101','Kempegowda Layout','2024-01-10 10:34:14',NULL),
				  (6,'Kamakshipalya','Ward-101','BDA Layout','2024-01-10 10:34:27',NULL),
				  (7,'Dr Rajkumar','Ward-106','Rajkumar Layout','2024-01-10 11:02:10',NULL),
				  (8,'Dr Rajkumar','Ward-106','Puneeth Rajkumar Layout','2024-01-10 11:02:31',NULL),
				  (9,'Chickpete','Ward-109','Balepete Layout','2024-01-10 11:05:41',NULL),
				  (10,'Chickpete','Ward-109','Basettypet Lane','2024-01-10 11:08:15',NULL),
				  (11,'Domlur','Ward-112','Domlur Layout','2024-01-10 11:09:58',NULL),
				  (12,'Domlur','Ward-112','Eshwara Layout','2024-01-10 11:10:15',NULL),
				  (13,'Vijaynagar','Ward-123','Vinayaka Layout','2024-01-10 11:11:16',NULL),
				  (14,'Vijaynagar','Ward-123','Kaveri Layout','2024-01-10 11:11:37',NULL),
				  (15,'Mudalapalya','Ward-127','Shivananada Layout','2024-01-10 11:18:37',NULL),
				  (16,'Nagharbavi','Ward-128','Malagala','2024-01-10 11:21:04',NULL),
				  (17,'Nagharbavi','Ward-128','Kottigepalya','2024-01-10 11:21:17',NULL),
				  (18,'Ullalu','Ward-130','Upkar Layout','2024-01-10 11:24:31',NULL),
				  (19,'Nayandahalli','Ward-131','Krishna Layout','2024-01-10 11:26:03',NULL),
				  (20,'Basavanagudi','Ward-154','Gandhi Nagar','2024-01-10 11:28:50',NULL);
                  
INSERT INTO `officer` VALUES (1,'Ankita','Kengeri','8147924396','ankitapatilankitapatil9@gmail.com','$2a$10$WKR1r3R5a8uDP14XS4TyqeSFCmPw92ySb2GTgUmikeKYTHSKzQ1Gi','2024-01-12 14:50:49',NULL,1,3),
			     (2,'Sonu','BDA Layout','9606817113','praveenmegharaj131@gmail.com','$2a$10$obtfBEUkmtKR0vWEub4UPOgd/gW.Y4Y0xiAsOwpc6ZmsKriWdUf4O','2024-01-12 14:54:31',NULL,2,3),
			     (3,'Gourish','Hesaraghatta','7022454659','bhalkedegourish@gmail.com','$2a$10$TW9q.7M349u5afRtv70XzO3z6PRSJcNF5HlaRCeBH.oYEH7rWp9mS','2024-01-12 14:55:07',NULL,3,3),
			     (4,'jaideep','Ullal Upanagara','8431767022','jaideepnelwade14@gmail.com','$2a$10$Qdd3IOwKCl5cHNSQCuYhjuQFW62gHLO9RNXsLfOJ2TGAfygZ318BG','2024-01-12 14:55:59',NULL,4,3),
			     (5,'Govind Biradar','J P Nagar','9353761490','govindbiradar2001@gmail.com','$2a$10$Cf8rn9CWwapYtQvd1E/2fuVn82rIM52acPodDHeq1uidOkUcBDzzm','2024-01-12 14:56:35',NULL,5,3),
			     (6,'Ratandeep Nelwade ','Sunkkadakatte','9902111604','ratannelwade@gmail.com','$2a$10$XGyt0OeyzRNyFzWyUfifz.FhhMFKTHieQ5vHoY95hw5HqjCEACwCG','2024-01-12 14:58:04',NULL,6,3);
                 
INSERT INTO `user` VALUES (1,'kruthik71','Kruthik','Bhupal','9916878237','kruthikbhupal007@gmail.com','$2a$10$7c/Ou8o4Cp7C2o7XGc7UtOlSr7OErGrkHR/h.EPmtAEnnADYnizF2',_binary '\0',2,'2024-01-11 12:31:22',NULL),
			  (2,'Praveen81','Praveen','Megharaj','9380587407','praveenmegharaj131@gmail.com','$2a$10$7yuGj06fAy0adXx2/1.pR.qYD6.EL575Ucbu0soNiNneGCi3UoQV6',_binary '\0',2,'2024-01-11 14:47:08',NULL),
			  (3,'kruthik7','Kruthik','Bhupal','9916878237','kruthikbhupal13@gmail.com','$2a$10$U8JyCliortoLLuP6uZbxmOLnidMML2Yx3Wyw/EXB94U3lxr2vssfS',_binary '\0',2,'2024-01-11 15:08:03',NULL);
              

INSERT INTO `complaint` VALUES (1,'Water Scarcity','3rd Cross Subhash Nagar ','Near Hanuman Temple','Kaveri water not available from past 3-4 days.',NULL,'WAITING','2024-01-15 19:35:43',NULL,6,6,1),
			       (2,'Noise Pollution','14th Cross, Colony road ','Near Krishna cafe','Severe traffic from past few days.',NULL,'WAITING','2024-01-15 19:39:57',NULL,5,5,1),
			       (3,'Air Quality','3rd Cross, 4th main road Lalith nagar','Near Euro Kids playhome','Pollution has taken over the school.',NULL,'WAITING','2024-01-15 19:41:44',NULL,4,4,3),
                               (4,'Street Lighting','5th Main road, 1st Main road ','Near Ashoka Circle','The lights are down from past few days during nights and they flicker.',NULL,'WAITING','2024-01-15 19:43:44',NULL,3,3,3),
                               (5,'Waste Management','1st Main road, 3rd Cross','Near Rameshwaram Cafe','The BBMP trucks have stopped coming from a past few days.',NULL,'WAITING','2024-01-15 19:45:21',NULL,2,2,2),
                               (6,'Public Transportation','1st Main road, 3rd Cross','Near Zudio Outlet','Proper public transportation schedule is not maintianed.',NULL,'WAITING','2024-01-15 19:46:24',NULL,1,1,2);