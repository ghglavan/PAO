-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: localhost    Database: PAO_App
-- ------------------------------------------------------
-- Server version	5.7.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `articole`
--

DROP TABLE IF EXISTS `articole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articole` (
  `articol_id` int(11) NOT NULL AUTO_INCREMENT,
  `titlu` varchar(45) NOT NULL,
  `autor_id` int(11) NOT NULL,
  `continut` varchar(800) NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `views` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`articol_id`),
  UNIQUE KEY `articol_id_UNIQUE` (`articol_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articole`
--

LOCK TABLES `articole` WRITE;
/*!40000 ALTER TABLE `articole` DISABLE KEYS */;
INSERT INTO `articole` VALUES (1,'da',1,'dsa','2017-05-20 17:15:37',4),(2,'asdas',1,'asdasd','2017-05-20 17:16:56',0),(3,'da',10,'damerge','2017-05-20 23:51:04',2),(4,'titlu',10,'merge merge measjdkasjhdkhjsadkjhjsjsdnkajsbdasnkjdbasbdkasdkbaskdbkasbdkjasbdkasbdkjbaskbdkhasdkhasdkhbsakdbsakbdkasbdkhasbdkhaskdaskhdbkhasdkbasdkhsabd','2017-05-21 00:03:54',0),(5,'dasdas',10,'dasdas','2017-05-21 01:22:41',0),(6,'blablabla',10,'blabla','2017-05-21 01:25:03',0),(7,'titlu art',10,'contetm','2017-05-21 01:34:04',0);
/*!40000 ALTER TABLE `articole` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-25 14:45:16
