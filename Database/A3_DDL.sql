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
-- Table structure for table `pessoa`
--

DROP TABLE IF EXISTS `pessoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pessoa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `dataNascimento` date NOT NULL,
  `genero` varchar(1) NOT NULL DEFAULT 'O' COMMENT 'Valores permitidos: M - masculino, F- feminino, O - Outro.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cpf_UNIQUE` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Tabela para armazer os dados das pessoas do sistema.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa`
--

LOCK TABLES `pessoa` WRITE;
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
INSERT INTO `pessoa` VALUES (1,'Henrique','01005079056','1999-07-04','M'),(2,'Pedro','07468192006','2002-01-01','M'),(3,'Luccas ','09034700046','2001-01-01','M'),(4,'Julia','83681469091','2001-01-01','F'),(5,'Jose carlos','12345678901','2025-06-10','M'),(6,'jesus','10987654321','0001-01-01','M');
/*!40000 ALTER TABLE `pessoa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transferencia`
--

DROP TABLE IF EXISTS `transferencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transferencia` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_veiculo` int NOT NULL,
  `id_pessoa_compra` int NOT NULL,
  `id_pessoa_venda` int DEFAULT NULL,
  `placa_anterior` varchar(8) DEFAULT NULL,
  `placa_atual` varchar(8) NOT NULL,
  `status_transferencia` varchar(45) NOT NULL DEFAULT 'N' COMMENT 'Status da transferência: ''N'' - Nova(mais recente), ''A''- anterior',
  `data` date NOT NULL COMMENT 'Data em que foi realizada a transferencia.',
  PRIMARY KEY (`id`),
  KEY `fk_veiculo_idx` (`id_veiculo`),
  KEY `fk_pessoa_venda_idx` (`id_pessoa_venda`),
  KEY `fk_pessoa_compra_idx` (`id_pessoa_compra`),
  CONSTRAINT `fk_pessoa_compra` FOREIGN KEY (`id_pessoa_compra`) REFERENCES `pessoa` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_pessoa_venda` FOREIGN KEY (`id_pessoa_venda`) REFERENCES `pessoa` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_veiculo` FOREIGN KEY (`id_veiculo`) REFERENCES `veiculo` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Tabela para armazenar todos os dados das transferências, apenas colunas desta tabela serão modificadas quando o usuário realizar uma transferência';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transferencia`
--

LOCK TABLES `transferencia` WRITE;
/*!40000 ALTER TABLE `transferencia` DISABLE KEYS */;
INSERT INTO `transferencia` VALUES (1,2,1,NULL,NULL,'HYF-9175','A','2025-05-28'),(2,1,2,NULL,NULL,'MZX-6980','N','2025-05-28'),(3,3,3,NULL,NULL,'JZA3230','N','2025-05-28'),(13,6,1,NULL,NULL,'CSZ3V13','N','2025-06-10'),(18,2,2,1,'HYF-9175','HYF9B75','A','2025-06-16'),(19,2,1,2,'HYF9B75','HYF9B75','A','2025-06-16'),(20,2,2,1,'HYF9B75','APE0J97','N','2025-06-16');
/*!40000 ALTER TABLE `transferencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `veiculo`
--

DROP TABLE IF EXISTS `veiculo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `veiculo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `marca` varchar(45) NOT NULL COMMENT 'Marca do carro: Fiat, Ford, etc... ',
  `modelo` varchar(45) NOT NULL,
  `ano` int NOT NULL COMMENT 'Ano de fabricação do carro.',
  `cor` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Tabela para armazenamento exclusivo das informações dos veículos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `veiculo`
--

LOCK TABLES `veiculo` WRITE;
/*!40000 ALTER TABLE `veiculo` DISABLE KEYS */;
INSERT INTO `veiculo` VALUES (1,'Fiat','Uno Mille',2002,'Branco'),(2,'volkswagen','Gol',1998,'Preto'),(3,'volkswagen','Kombi',1980,'Amarelo'),(4,'Ford','Ka',2020,'Vermelho'),(5,'Peugeot','206',2015,'Prata'),(6,'Ford','Fiesta',2010,'azul'),(7,'Ford','Fiesta',2020,'azul');
/*!40000 ALTER TABLE `veiculo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-16 16:00:55
