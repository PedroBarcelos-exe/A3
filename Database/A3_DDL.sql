CREATE DATABASE  IF NOT EXISTS `a3_transfere_veiculo` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `a3_transfere_veiculo`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: a3_transfere_veiculo
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `pessoas`
--

DROP TABLE IF EXISTS `pessoas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pessoas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `dataNascimento` date NOT NULL,
  `genero` varchar(1) NOT NULL DEFAULT 'I' COMMENT 'Valores permitidos: M - masculino, F- feminino, I - indefinido/não informado.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cpf_UNIQUE` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Tabela para armazer os dados das pessoas do sistema.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoas`
--

LOCK TABLES `pessoas` WRITE;
/*!40000 ALTER TABLE `pessoas` DISABLE KEYS */;
INSERT INTO `pessoas` VALUES (1,'Henrique','01005079056','1999-07-04','M'),(2,'Pedro','07468192006','2002-01-01','M'),(3,'Luccas ','09034700046','2001-01-01','M'),(4,'Julia','83681469091','2001-01-01','F');
/*!40000 ALTER TABLE `pessoas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transferencias`
--

DROP TABLE IF EXISTS `transferencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transferencias` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_veiculo` int NOT NULL,
  `id_pessoa_compra` int NOT NULL,
  `id_pessoa_venda` int DEFAULT NULL,
  `placa_anterior` varchar(7) DEFAULT NULL,
  `placa_atual` varchar(7) NOT NULL,
  `status_transferencia` varchar(45) NOT NULL DEFAULT 'N' COMMENT 'Status da transferência: ''N'' - Nova(mais recente), ''A''- anterior',
  `data` date NOT NULL COMMENT 'Data em que foi realizada a transferencia.',
  PRIMARY KEY (`id`),
  KEY `fk_veiculo_idx` (`id_veiculo`),
  KEY `fk_pessoa_venda_idx` (`id_pessoa_venda`),
  KEY `fk_pessoa_compra_idx` (`id_pessoa_compra`),
  CONSTRAINT `fk_pessoa_compra` FOREIGN KEY (`id_pessoa_compra`) REFERENCES `pessoas` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_pessoa_venda` FOREIGN KEY (`id_pessoa_venda`) REFERENCES `pessoas` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_veiculo` FOREIGN KEY (`id_veiculo`) REFERENCES `veiculos` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Tabela para armazenar todos os dados das transferências, apenas colunas desta tabela serão modificadas quando o usuário realizar uma transferência';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transferencias`
--

LOCK TABLES `transferencias` WRITE;
/*!40000 ALTER TABLE `transferencias` DISABLE KEYS */;
INSERT INTO `transferencias` VALUES (1,2,1,NULL,NULL,'HYF9175','N','2025-05-28'),(2,1,2,NULL,NULL,'MZX6980','N','2025-05-28'),(3,3,3,NULL,NULL,'JZA3230','N','2025-05-28');
/*!40000 ALTER TABLE `transferencias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `veiculos`
--

DROP TABLE IF EXISTS `veiculos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `veiculos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `marca` varchar(45) NOT NULL COMMENT 'Marca do carro: Fiat, Ford, etc... ',
  `modelo` varchar(45) NOT NULL,
  `ano` int NOT NULL COMMENT 'Ano de fabricação do carro.',
  `cor` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Tabela para armazenamento exclusivo das informações dos veículos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `veiculos`
-- 

LOCK TABLES `veiculos` WRITE;
/*!40000 ALTER TABLE `veiculos` DISABLE KEYS */;
INSERT INTO `veiculos` VALUES (1,'Fiat','Uno Mille',2002,'Branco'),(2,'volkswagen','Gol',1998,'Preto'),(3,'volkswagen','Kombi',1980,'Amarelo'),(4,'Ford','Ka',2020,'Vermelho'),(5,'Peugeot','206',2015,'Prata');
/*!40000 ALTER TABLE `veiculos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-02 14:51:28
