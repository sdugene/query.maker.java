--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` text,
  `last_name` text,
  `status` int(2) unsigned DEFAULT NULL,
  `mail` varchar(150) DEFAULT NULL,
  `login` varchar(40) DEFAULT NULL,
  `id_key` varchar(32) NOT NULL,
  `last_logon` timestamp NULL DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `validated` timestamp NULL DEFAULT NULL,
  `certified` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `alfred`
--

DROP TABLE IF EXISTS `alfred`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alfred` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(10) NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `value` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `alfred_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `address_name` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `category_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `content`
--

DROP TABLE IF EXISTS `content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `content` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `author_id` bigint(20) unsigned DEFAULT NULL,
  `folder_id` bigint(20) unsigned DEFAULT NULL,
  `admin_id` bigint(20) unsigned DEFAULT NULL,
  `module_id` bigint(20) unsigned DEFAULT NULL,
  `parent_group_id` bigint(20) unsigned DEFAULT NULL,
  `name` text,
  `address_name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` text,
  `type` varchar(255) DEFAULT 'default',
  `tags` text,
  `value` longtext,
  `updated_value` longtext,
  `online` timestamp NULL DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `module_id` (`module_id`),
  KEY `page_group_id` (`parent_group_id`),
  KEY `admin_id` (`admin_id`),
  KEY `folder_id` (`folder_id`),
  KEY `author_id` (`author_id`),
  CONSTRAINT `content_ibfk_2` FOREIGN KEY (`module_id`) REFERENCES `module` (`id`),
  CONSTRAINT `content_ibfk_4` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`) ON DELETE SET NULL,
  CONSTRAINT `content_ibfk_6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL,
  CONSTRAINT `content_ibfk_7` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`),
  CONSTRAINT `content_ibfk_9` FOREIGN KEY (`author_id`) REFERENCES `user_blog_author` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=5553 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `content_category`
--

DROP TABLE IF EXISTS `content_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `content_category` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `content_id` bigint(20) unsigned DEFAULT NULL,
  `category_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `content_id_category_id` (`content_id`,`category_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `content_category_ibfk_1` FOREIGN KEY (`content_id`) REFERENCES `content` (`id`),
  CONSTRAINT `content_category_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `css`
--

DROP TABLE IF EXISTS `css`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `css` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `content` longtext,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `css_entry`
--

DROP TABLE IF EXISTS `css_entry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `css_entry` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(10) DEFAULT NULL,
  `identification` varchar(255) DEFAULT NULL,
  `value` text,
  `created` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_identification` (`type`,`identification`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `folder_id` bigint(20) unsigned DEFAULT '0',
  `url` text,
  `name` varchar(255) DEFAULT NULL,
  `extension` varchar(10) DEFAULT NULL,
  `alt` varchar(255) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `height` int(10) unsigned DEFAULT NULL,
  `width` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `folder_id` (`folder_id`),
  CONSTRAINT `file_ibfk_2` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`) ON DELETE SET NULL,
  CONSTRAINT `file_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4649 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `firewall`
--

DROP TABLE IF EXISTS `firewall`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `firewall` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) unsigned DEFAULT NULL,
  `ip` varchar(40) DEFAULT NULL,
  `name` text,
  `created` timestamp NULL DEFAULT NULL,
  `updated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `firewall_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `folder_id` bigint(20) unsigned DEFAULT NULL,
  `secured` int(1) unsigned DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `folder_id` (`folder_id`),
  CONSTRAINT `folder_ibfk_2` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`) ON DELETE SET NULL,
  CONSTRAINT `folder_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=397 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `google_fonts`
--

DROP TABLE IF EXISTS `google_fonts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `google_fonts` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `icon`
--

DROP TABLE IF EXISTS `icon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `icon` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` text,
  `value` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `language` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `abbr` varchar(5) DEFAULT NULL,
  `name` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mail`
--

DROP TABLE IF EXISTS `mail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `from_address` varchar(150) DEFAULT NULL,
  `from_name` varchar(150) DEFAULT NULL,
  `recipient` varchar(150) DEFAULT NULL,
  `subject` tinytext,
  `body` mediumtext,
  `attachment` mediumtext,
  `error_count` int(4) unsigned DEFAULT NULL,
  `error_message` tinytext,
  `date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mail_send`
--

DROP TABLE IF EXISTS `mail_send`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mail_send` (
  `linFonct` bigint(20) NOT NULL AUTO_INCREMENT,
  `Username` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `FromMail` varchar(100) NOT NULL,
  `FromName` varchar(100) NOT NULL,
  `AddAddress` varchar(100) NOT NULL,
  `Subject` varchar(255) NOT NULL,
  `Body` longtext NOT NULL,
  `AddAttachment` mediumtext NOT NULL,
  `nbError` int(4) NOT NULL DEFAULT '0',
  `msgError` text NOT NULL,
  `Status` int(2) NOT NULL DEFAULT '1',
  `msgDossier` int(10) NOT NULL DEFAULT '0',
  `msgDate` bigint(14) NOT NULL,
  `msgUtil` int(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`linFonct`)
) ENGINE=InnoDB AUTO_INCREMENT=3369 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mail_template`
--

DROP TABLE IF EXISTS `mail_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mail_template` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) unsigned DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `folder` int(10) unsigned DEFAULT NULL,
  `description` longtext,
  `price` float unsigned DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `validated` timestamp NULL DEFAULT NULL,
  `checked` timestamp NULL DEFAULT NULL,
  `updated` timestamp NULL DEFAULT NULL,
  `deprecated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `admin_id` (`admin_id`),
  KEY `folder` (`folder`),
  CONSTRAINT `mail_template_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `folder_id` bigint(20) unsigned DEFAULT NULL,
  `group_id` bigint(20) unsigned DEFAULT NULL,
  `language_id` bigint(20) unsigned DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `address_name` tinytext,
  `content` longtext,
  `css` longtext,
  `orientation` varchar(255) DEFAULT 'horizontal',
  `online` timestamp NULL DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `folder_id` (`folder_id`),
  KEY `group_id` (`group_id`),
  KEY `language_id` (`language_id`),
  CONSTRAINT `menu_ibfk_2` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`) ON DELETE SET NULL,
  CONSTRAINT `menu_ibfk_3` FOREIGN KEY (`group_id`) REFERENCES `menu` (`id`) ON DELETE SET NULL,
  CONSTRAINT `menu_ibfk_4` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`),
  CONSTRAINT `menu_ibfk_5` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) unsigned DEFAULT NULL,
  `folder_id` bigint(20) unsigned DEFAULT NULL,
  `group_id` bigint(20) unsigned DEFAULT NULL COMMENT 'group different version of the same module',
  `name` varchar(255) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  `description` longtext,
  `price` float unsigned DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `password` varchar(40) DEFAULT NULL,
  `object` int(1) unsigned DEFAULT '1',
  `dynamic` int(1) unsigned DEFAULT '0',
  `created` timestamp NULL DEFAULT NULL,
  `validated` timestamp NULL DEFAULT NULL,
  `checked` timestamp NULL DEFAULT NULL,
  `updated` timestamp NULL DEFAULT NULL,
  `deprecated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `admin_id` (`admin_id`),
  KEY `folder_id` (`folder_id`),
  KEY `group_id` (`group_id`),
  KEY `password` (`password`),
  CONSTRAINT `module_ibfk1` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`),
  CONSTRAINT `module_ibfk_2` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`) ON DELETE SET NULL,
  CONSTRAINT `module_ibfk_4` FOREIGN KEY (`group_id`) REFERENCES `module` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `page`
--

DROP TABLE IF EXISTS `page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `page` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `folder_id` bigint(20) unsigned DEFAULT NULL,
  `group_id` bigint(20) unsigned DEFAULT NULL,
  `language_id` bigint(20) unsigned DEFAULT NULL,
  `template_group_id` bigint(20) unsigned DEFAULT '1',
  `link` tinytext,
  `name` varchar(255) DEFAULT NULL,
  `address_name` tinytext,
  `title` varchar(255) DEFAULT NULL,
  `description` text,
  `content` longtext,
  `css` longtext,
  `parameters` longtext,
  `online` timestamp NULL DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `language_id` (`language_id`),
  KEY `folder_id` (`folder_id`),
  KEY `group_id` (`group_id`),
  KEY `template_id` (`template_group_id`),
  CONSTRAINT `FKqufc9wnpcj7x51dxxbvwwceon` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`),
  CONSTRAINT `page_ibfk_2` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`),
  CONSTRAINT `page_ibfk_3` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`) ON DELETE SET NULL,
  CONSTRAINT `page_ibfk_4` FOREIGN KEY (`template_group_id`) REFERENCES `template` (`group_id`) ON DELETE SET NULL,
  CONSTRAINT `page_ibfk_5` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=769 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address_name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `icon_id` bigint(20) unsigned DEFAULT NULL,
  `form` text,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `validated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `icon_id` (`icon_id`),
  CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`icon_id`) REFERENCES `icon` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `folder_id` bigint(20) unsigned DEFAULT NULL,
  `group_id` bigint(20) unsigned DEFAULT NULL,
  `language_id` bigint(20) unsigned DEFAULT NULL,
  `sticker_group_id` bigint(20) unsigned DEFAULT NULL,
  `name` text,
  `address_name` text,
  `reference` varchar(255) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `range` varchar(255) DEFAULT NULL,
  `gen_code` varchar(255) DEFAULT NULL,
  `keywords` text,
  `summary` text,
  `description` text,
  `files` text,
  `price` float unsigned DEFAULT NULL,
  `shipping` float unsigned DEFAULT NULL,
  `discount` float unsigned DEFAULT NULL,
  `discount_type` int(1) unsigned DEFAULT '1',
  `online` timestamp NULL DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `folder_id` (`folder_id`),
  KEY `group_id` (`group_id`),
  KEY `language_id` (`language_id`),
  KEY `sticker_group_id` (`sticker_group_id`),
  CONSTRAINT `FKe7cpbpxindm6xcdy0g3d2aq6q` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`),
  CONSTRAINT `product_ibfk_2` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`),
  CONSTRAINT `product_ibfk_4` FOREIGN KEY (`group_id`) REFERENCES `product` (`id`) ON DELETE SET NULL,
  CONSTRAINT `product_ibfk_5` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`),
  CONSTRAINT `product_ibfk_8` FOREIGN KEY (`sticker_group_id`) REFERENCES `sticker` (`group_id`) ON DELETE SET NULL,
  CONSTRAINT `product_ibfk_9` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_stock`
--

DROP TABLE IF EXISTS `product_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_stock` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `product_group_id` bigint(20) unsigned DEFAULT NULL,
  `quantity` int(10) unsigned DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_group_id` (`product_group_id`),
  CONSTRAINT `product_stock_ibfk_3` FOREIGN KEY (`product_group_id`) REFERENCES `product` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_variant`
--

DROP TABLE IF EXISTS `product_variant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_variant` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) unsigned DEFAULT NULL,
  `product_stock_id` bigint(20) unsigned DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `product_group_id` (`product_stock_id`),
  CONSTRAINT `product_variant_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `product_variant_ibfk_2` FOREIGN KEY (`product_stock_id`) REFERENCES `product_stock` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sample`
--

DROP TABLE IF EXISTS `sample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sample` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKeugkgqdrooa9puw83rae7pu6b` (`user_id`),
  CONSTRAINT `FKeugkgqdrooa9puw83rae7pu6b` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `sample_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `id` int(2) unsigned NOT NULL DEFAULT '0',
  `ad` int(1) unsigned DEFAULT '1',
  `level` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,1,NULL),(2,0,NULL),(10,1,NULL),(11,1,'noob'),(12,1,'human'),(13,1,'meta'),(20,0,NULL),(21,0,'noob'),(22,0,'human'),(23,0,'meta');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sticker`
--

DROP TABLE IF EXISTS `sticker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sticker` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) unsigned DEFAULT NULL,
  `language_id` bigint(20) unsigned DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`id`),
  KEY `language_id` (`language_id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `FKs8i9wu84kp52h36a6embq4hib` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`),
  CONSTRAINT `sticker_ibfk_1` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `template`
--

DROP TABLE IF EXISTS `template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `template` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `admin_id` bigint(20) unsigned DEFAULT NULL,
  `folder_id` bigint(20) unsigned DEFAULT NULL,
  `group_id` bigint(20) unsigned DEFAULT NULL,
  `language_id` bigint(20) unsigned DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  `description` longtext,
  `price` float unsigned DEFAULT NULL,
  `address_name` tinytext,
  `content` longtext,
  `css` longtext,
  `parameters` longtext,
  `origin` varchar(50) NOT NULL DEFAULT 'market' COMMENT 'market(default) / user / default',
  `online` timestamp NULL DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `validated` timestamp NULL DEFAULT NULL,
  `checked` timestamp NULL DEFAULT NULL,
  `updated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `language_id` (`language_id`),
  KEY `folder_id` (`folder_id`),
  KEY `group_id` (`group_id`),
  KEY `admin_id` (`admin_id`),
  CONSTRAINT `template_ibfk_3` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`),
  CONSTRAINT `template_ibfk_5` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`) ON DELETE SET NULL,
  CONSTRAINT `template_ibfk_6` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`) ON DELETE SET NULL,
  CONSTRAINT `template_ibfk_7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `template_view`
--

DROP TABLE IF EXISTS `template_view`;
/*!50001 DROP VIEW IF EXISTS `template_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `template_view` (
  `id` tinyint NOT NULL,
  `user_id` tinyint NOT NULL,
  `admin_id` tinyint NOT NULL,
  `folder_id` tinyint NOT NULL,
  `group_id` tinyint NOT NULL,
  `language_id` tinyint NOT NULL,
  `name` tinyint NOT NULL,
  `version` tinyint NOT NULL,
  `description` tinyint NOT NULL,
  `price` tinyint NOT NULL,
  `address_name` tinyint NOT NULL,
  `content` tinyint NOT NULL,
  `css` tinyint NOT NULL,
  `parameters` tinyint NOT NULL,
  `origin` tinyint NOT NULL,
  `online` tinyint NOT NULL,
  `created` tinyint NOT NULL,
  `validated` tinyint NOT NULL,
  `checked` tinyint NOT NULL,
  `updated` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `admin_id` bigint(20) unsigned DEFAULT NULL,
  `page_id` bigint(20) unsigned DEFAULT NULL,
  `module_id` bigint(20) unsigned DEFAULT NULL,
  `title` text,
  `text` longtext,
  `type` int(1) DEFAULT '1',
  `status` int(1) DEFAULT '1',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL,
  `closed` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `module_id` (`module_id`),
  KEY `admin_id` (`admin_id`),
  KEY `FKqmhvnj6apmcudsrcuv3p5d9dt` (`page_id`),
  CONSTRAINT `FKmcwcneckkvgoufl796r64dr6w` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`),
  CONSTRAINT `FKo1j0deyni1jin3dppll1ksr1s` FOREIGN KEY (`module_id`) REFERENCES `module` (`id`),
  CONSTRAINT `FKqmhvnj6apmcudsrcuv3p5d9dt` FOREIGN KEY (`page_id`) REFERENCES `page` (`id`),
  CONSTRAINT `ticket_ibfk_5` FOREIGN KEY (`module_id`) REFERENCES `module` (`id`) ON DELETE SET NULL,
  CONSTRAINT `ticket_ibfk_6` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`) ON DELETE SET NULL,
  CONSTRAINT `ticket_ibfk_7` FOREIGN KEY (`page_id`) REFERENCES `page` (`id`) ON DELETE CASCADE,
  CONSTRAINT `ticket_ibfk_8` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(22) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `web_service_url` varchar(500) NOT NULL DEFAULT 'http://www.hosting1.siteoffice.fr/index.php/',
  `hash` varchar(500) NOT NULL,
  `site_name` varchar(255) DEFAULT NULL,
  `status` int(2) unsigned NOT NULL DEFAULT '1',
  `mail` varchar(150) DEFAULT NULL,
  `login` varchar(40) DEFAULT NULL,
  `firewall` int(1) unsigned DEFAULT '0',
  `id_key` varchar(32) DEFAULT NULL,
  `id_key_gestion` varchar(32) DEFAULT NULL,
  `last_logon` timestamp NULL DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `password_gestion` varchar(255) DEFAULT NULL,
  `ws_identification` varchar(255) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `validated` timestamp NULL DEFAULT NULL,
  `updated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`),
  KEY `status` (`status`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`status`) REFERENCES `status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Sebastien','Dugène','http://www.hosting1.siteoffice.fr/websites/sebastien','http://www.hosting1.siteoffice.fr/index.php/','','sebastien',1,'sebastien@siteoffice.fr','sebastien',0,'a8e2a2afaa9761385093ee521d799257','a8e2a2afaa9761385093ee521d799257','2018-08-23 20:25:22','a8e2a2afaa9761385093ee521d799257','a8e2a2afaa9761385093ee521d799257','a8e2a2afaa9761385093ee521d799257','2015-01-16 23:00:00','2030-12-31 23:59:59','2018-08-23 22:39:56');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_blog`
--

DROP TABLE IF EXISTS `user_blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_blog` (
  `id` bigint(20) unsigned DEFAULT NULL,
  `post_page_group_id` bigint(20) unsigned DEFAULT NULL,
  `list_page_group_id` bigint(20) unsigned DEFAULT NULL,
  KEY `post_page_group_id` (`post_page_group_id`),
  CONSTRAINT `user_blog_ibfk_2` FOREIGN KEY (`post_page_group_id`) REFERENCES `page` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_blog_author`
--

DROP TABLE IF EXISTS `user_blog_author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_blog_author` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `name` text,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_blog_author_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_configuration`
--

DROP TABLE IF EXISTS `user_configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_configuration` (
  `id` bigint(20) unsigned NOT NULL,
  `google_analytics` varchar(255) DEFAULT NULL,
  `website_type` varchar(255) DEFAULT 'showcase',
  UNIQUE KEY `id` (`id`),
  CONSTRAINT `user_configuration_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_gestion`
--

DROP TABLE IF EXISTS `user_gestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_gestion` (
  `id` bigint(20) unsigned NOT NULL,
  `product` int(1) NOT NULL DEFAULT '0',
  `library` int(1) NOT NULL DEFAULT '0',
  `client` int(1) NOT NULL DEFAULT '0',
  `order` int(1) NOT NULL DEFAULT '0',
  `content` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `user_gestion_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_language`
--

DROP TABLE IF EXISTS `user_language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_language` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `language_id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `path` varchar(255) DEFAULT '',
  `default_language` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_language_id` (`user_id`,`language_id`),
  KEY `language_id` (`language_id`),
  CONSTRAINT `FK5dhe6kqh7ol7f48ker70e368r` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`),
  CONSTRAINT `user_language_ibfk_1` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_merchant`
--

DROP TABLE IF EXISTS `user_merchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_merchant` (
  `id` bigint(20) unsigned DEFAULT NULL,
  `product_page_group_id` bigint(20) unsigned DEFAULT NULL,
  `cart_page_group_id` bigint(20) unsigned DEFAULT NULL,
  `order_page_group_id` bigint(20) unsigned DEFAULT NULL,
  `mail_template_folder` int(10) unsigned DEFAULT NULL,
  `payments` text,
  `shipping` float unsigned DEFAULT NULL,
  `stock_action` varchar(255) DEFAULT NULL,
  KEY `user_id` (`id`),
  KEY `product_page_id` (`product_page_group_id`),
  KEY `mail_template_folder` (`mail_template_folder`),
  KEY `order_page_group_id` (`order_page_group_id`),
  KEY `cart_page_group_id` (`cart_page_group_id`),
  CONSTRAINT `user_merchant_ibfk_2` FOREIGN KEY (`product_page_group_id`) REFERENCES `page` (`group_id`) ON DELETE SET NULL,
  CONSTRAINT `user_merchant_ibfk_4` FOREIGN KEY (`id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_merchant_ibfk_5` FOREIGN KEY (`mail_template_folder`) REFERENCES `mail_template` (`folder`),
  CONSTRAINT `user_merchant_ibfk_8` FOREIGN KEY (`order_page_group_id`) REFERENCES `page` (`group_id`) ON DELETE SET NULL,
  CONSTRAINT `user_merchant_ibfk_9` FOREIGN KEY (`cart_page_group_id`) REFERENCES `page` (`group_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_module`
--

DROP TABLE IF EXISTS `user_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_module` (
  `id` bigint(20) unsigned DEFAULT NULL,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `group_id` bigint(20) unsigned DEFAULT NULL,
  `purchased` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `index` (`id`,`user_id`),
  UNIQUE KEY `group_id_user_id` (`group_id`,`user_id`),
  KEY `user_id` (`user_id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `user_module_ibfk_1` FOREIGN KEY (`id`) REFERENCES `module` (`id`),
  CONSTRAINT `user_module_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_module_ibfk_4` FOREIGN KEY (`group_id`) REFERENCES `module` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_module_password`
--

DROP TABLE IF EXISTS `user_module_password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_module_password` (
  `id` bigint(20) unsigned DEFAULT NULL,
  `module_password` varchar(40) DEFAULT NULL,
  KEY `id` (`id`),
  KEY `module_password` (`module_password`),
  CONSTRAINT `user_module_password_ibfk_2` FOREIGN KEY (`module_password`) REFERENCES `module` (`password`),
  CONSTRAINT `user_module_password_ibfk_3` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `user_module_view`
--

DROP TABLE IF EXISTS `user_module_view`;
/*!50001 DROP VIEW IF EXISTS `user_module_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `user_module_view` (
  `id` tinyint NOT NULL,
  `module_id` tinyint NOT NULL,
  `user_id` tinyint NOT NULL,
  `group_id` tinyint NOT NULL,
  `folder_id` tinyint NOT NULL,
  `update_id` tinyint NOT NULL,
  `update_version` tinyint NOT NULL,
  `name` tinyint NOT NULL,
  `version` tinyint NOT NULL,
  `icon` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `user_payment`
--

DROP TABLE IF EXISTS `user_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_payment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `payment_id` bigint(20) unsigned DEFAULT NULL,
  `identification` longtext,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `payment_id` (`payment_id`),
  CONSTRAINT `user_payment_ibfk_2` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`),
  CONSTRAINT `user_payment_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_template`
--

DROP TABLE IF EXISTS `user_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_template` (
  `id` bigint(20) unsigned DEFAULT NULL,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `purchased` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `index` (`id`,`user_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_template_ibfk_1` FOREIGN KEY (`id`) REFERENCES `template` (`id`),
  CONSTRAINT `user_template_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `user_template_view`
--

DROP TABLE IF EXISTS `user_template_view`;
/*!50001 DROP VIEW IF EXISTS `user_template_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `user_template_view` (
  `id` tinyint NOT NULL,
  `user_id` tinyint NOT NULL,
  `admin_id` tinyint NOT NULL,
  `folder_id` tinyint NOT NULL,
  `group_id` tinyint NOT NULL,
  `language_id` tinyint NOT NULL,
  `name` tinyint NOT NULL,
  `version` tinyint NOT NULL,
  `description` tinyint NOT NULL,
  `price` tinyint NOT NULL,
  `address_name` tinyint NOT NULL,
  `content` tinyint NOT NULL,
  `css` tinyint NOT NULL,
  `parameters` tinyint NOT NULL,
  `origin` tinyint NOT NULL,
  `online` tinyint NOT NULL,
  `created` tinyint NOT NULL,
  `validated` tinyint NOT NULL,
  `checked` tinyint NOT NULL,
  `updated` tinyint NOT NULL,
  `purchased` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `template_view`
--

/*!50001 DROP TABLE IF EXISTS `template_view`*/;
/*!50001 DROP VIEW IF EXISTS `template_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`admin`@`91.121.66.115` SQL SECURITY DEFINER */
/*!50001 VIEW `template_view` AS (select `t`.`id` AS `id`,`template`.`user_id` AS `user_id`,`t`.`admin_id` AS `admin_id`,`t`.`folder_id` AS `folder_id`,`t`.`group_id` AS `group_id`,`t`.`language_id` AS `language_id`,`t`.`name` AS `name`,`t`.`version` AS `version`,`t`.`description` AS `description`,`t`.`price` AS `price`,`t`.`address_name` AS `address_name`,`t`.`content` AS `content`,`t`.`css` AS `css`,`t`.`parameters` AS `parameters`,`t`.`origin` AS `origin`,`t`.`online` AS `online`,`t`.`created` AS `created`,`t`.`validated` AS `validated`,`t`.`checked` AS `checked`,`t`.`updated` AS `updated` from (`template` left join `template` `t` on((`t`.`origin` = 'default'))) where (`template`.`user_id` <> 'NULL') group by `template`.`user_id`) union (select `template`.`id` AS `id`,`template`.`user_id` AS `user_id`,`template`.`admin_id` AS `admin_id`,`template`.`folder_id` AS `folder_id`,`template`.`group_id` AS `group_id`,`template`.`language_id` AS `language_id`,`template`.`name` AS `name`,`template`.`version` AS `version`,`template`.`description` AS `description`,`template`.`price` AS `price`,`template`.`address_name` AS `address_name`,`template`.`content` AS `content`,`template`.`css` AS `css`,`template`.`parameters` AS `parameters`,`template`.`origin` AS `origin`,`template`.`online` AS `online`,`template`.`created` AS `created`,`template`.`validated` AS `validated`,`template`.`checked` AS `checked`,`template`.`updated` AS `updated` from `template` where (`template`.`user_id` <> 'NULL')) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `user_module_view`
--

/*!50001 DROP TABLE IF EXISTS `user_module_view`*/;
/*!50001 DROP VIEW IF EXISTS `user_module_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`admin`@`91.121.66.115` SQL SECURITY DEFINER */
/*!50001 VIEW `user_module_view` AS select `user_module`.`id` AS `id`,`user_module`.`id` AS `module_id`,`user_module`.`user_id` AS `user_id`,`user_module`.`group_id` AS `group_id`,`module`.`folder_id` AS `folder_id`,`new_module`.`id` AS `update_id`,`new_module`.`version` AS `update_version`,`module`.`name` AS `name`,`module`.`version` AS `version`,`module`.`icon` AS `icon` from ((`user_module` left join `module` on((`user_module`.`id` = `module`.`id`))) left join `module` `new_module` on(((`user_module`.`group_id` = `new_module`.`group_id`) and (`new_module`.`id` > `user_module`.`id`) and (`new_module`.`validated` is not null) and (`new_module`.`checked` is not null) and isnull(`new_module`.`deprecated`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `user_template_view`
--

/*!50001 DROP TABLE IF EXISTS `user_template_view`*/;
/*!50001 DROP VIEW IF EXISTS `user_template_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`admin`@`91.121.66.115` SQL SECURITY DEFINER */
/*!50001 VIEW `user_template_view` AS (select `template`.`id` AS `id`,`template`.`user_id` AS `user_id`,`template`.`admin_id` AS `admin_id`,`template`.`folder_id` AS `folder_id`,`template`.`group_id` AS `group_id`,`template`.`language_id` AS `language_id`,`template`.`name` AS `name`,`template`.`version` AS `version`,`template`.`description` AS `description`,`template`.`price` AS `price`,`template`.`address_name` AS `address_name`,`template`.`content` AS `content`,`template`.`css` AS `css`,`template`.`parameters` AS `parameters`,`template`.`origin` AS `origin`,`template`.`online` AS `online`,`template`.`created` AS `created`,`template`.`validated` AS `validated`,`template`.`checked` AS `checked`,`template`.`updated` AS `updated`,'NULL' AS `purchased` from `template_view` `template` where ((`template`.`validated` <> 'NULL') or (`template`.`user_id` <> 'NULL') or (`template`.`origin` = 'default')) group by `template`.`user_id`,`template`.`group_id`) union (select `user_template`.`id` AS `id`,`user_template`.`user_id` AS `user_id`,`template`.`admin_id` AS `admin_id`,`template`.`folder_id` AS `folder_id`,`template`.`group_id` AS `group_id`,`template`.`language_id` AS `language_id`,`template`.`name` AS `name`,`template`.`version` AS `version`,`template`.`description` AS `description`,`template`.`price` AS `price`,`template`.`address_name` AS `address_name`,`template`.`content` AS `content`,`template`.`css` AS `css`,`template`.`parameters` AS `parameters`,'market' AS `origin`,`template`.`online` AS `online`,`template`.`created` AS `created`,`template`.`validated` AS `validated`,`template`.`checked` AS `checked`,`template`.`updated` AS `updated`,`user_template`.`purchased` AS `purchased` from (`template` join `user_template` on((`user_template`.`id` = `template`.`id`))) where (`template`.`validated` <> 'NULL')) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-29  4:06:04
