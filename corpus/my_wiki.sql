-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 17, 2015 at 04:50 PM
-- Server version: 5.5.41-0ubuntu0.14.04.1
-- PHP Version: 5.6.5-1+deb.sury.org~trusty+1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `my_wiki`
--
CREATE DATABASE IF NOT EXISTS `my_wiki` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `my_wiki`;

-- --------------------------------------------------------

--
-- Table structure for table `archive`
--

CREATE TABLE IF NOT EXISTS `archive` (
  `ar_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ar_namespace` int(11) NOT NULL DEFAULT '0',
  `ar_title` varbinary(255) NOT NULL DEFAULT '',
  `ar_text` mediumblob NOT NULL,
  `ar_comment` tinyblob NOT NULL,
  `ar_user` int(10) unsigned NOT NULL DEFAULT '0',
  `ar_user_text` varbinary(255) NOT NULL,
  `ar_timestamp` binary(14) NOT NULL DEFAULT '\0\0\0\0\0\0\0\0\0\0\0\0\0\0',
  `ar_minor_edit` tinyint(4) NOT NULL DEFAULT '0',
  `ar_flags` tinyblob NOT NULL,
  `ar_rev_id` int(10) unsigned DEFAULT NULL,
  `ar_text_id` int(10) unsigned DEFAULT NULL,
  `ar_deleted` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `ar_len` int(10) unsigned DEFAULT NULL,
  `ar_page_id` int(10) unsigned DEFAULT NULL,
  `ar_parent_id` int(10) unsigned DEFAULT NULL,
  `ar_sha1` varbinary(32) NOT NULL DEFAULT '',
  `ar_content_model` varbinary(32) DEFAULT NULL,
  `ar_content_format` varbinary(64) DEFAULT NULL,
  PRIMARY KEY (`ar_id`),
  KEY `name_title_timestamp` (`ar_namespace`,`ar_title`,`ar_timestamp`),
  KEY `usertext_timestamp` (`ar_user_text`,`ar_timestamp`),
  KEY `ar_revid` (`ar_rev_id`)
) ENGINE=InnoDB DEFAULT CHARSET=binary AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE IF NOT EXISTS `category` (
  `cat_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cat_title` varbinary(255) NOT NULL,
  `cat_pages` int(11) NOT NULL DEFAULT '0',
  `cat_subcats` int(11) NOT NULL DEFAULT '0',
  `cat_files` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`cat_id`),
  UNIQUE KEY `cat_title` (`cat_title`),
  KEY `cat_pages` (`cat_pages`)
) ENGINE=InnoDB DEFAULT CHARSET=binary AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `categorylinks`
--

CREATE TABLE IF NOT EXISTS `categorylinks` (
  `cl_from` int(10) unsigned NOT NULL DEFAULT '0',
  `cl_to` varbinary(255) NOT NULL DEFAULT '',
  `cl_sortkey` varbinary(230) NOT NULL DEFAULT '',
  `cl_sortkey_prefix` varbinary(255) NOT NULL DEFAULT '',
  `cl_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `cl_collation` varbinary(32) NOT NULL DEFAULT '',
  `cl_type` enum('page','subcat','file') NOT NULL DEFAULT 'page',
  UNIQUE KEY `cl_from` (`cl_from`,`cl_to`),
  KEY `cl_sortkey` (`cl_to`,`cl_type`,`cl_sortkey`,`cl_from`),
  KEY `cl_timestamp` (`cl_to`,`cl_timestamp`),
  KEY `cl_collation` (`cl_collation`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `change_tag`
--

CREATE TABLE IF NOT EXISTS `change_tag` (
  `ct_rc_id` int(11) DEFAULT NULL,
  `ct_log_id` int(11) DEFAULT NULL,
  `ct_rev_id` int(11) DEFAULT NULL,
  `ct_tag` varbinary(255) NOT NULL,
  `ct_params` blob,
  UNIQUE KEY `change_tag_rc_tag` (`ct_rc_id`,`ct_tag`),
  UNIQUE KEY `change_tag_log_tag` (`ct_log_id`,`ct_tag`),
  UNIQUE KEY `change_tag_rev_tag` (`ct_rev_id`,`ct_tag`),
  KEY `change_tag_tag_id` (`ct_tag`,`ct_rc_id`,`ct_rev_id`,`ct_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `externallinks`
--

CREATE TABLE IF NOT EXISTS `externallinks` (
  `el_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `el_from` int(10) unsigned NOT NULL DEFAULT '0',
  `el_to` blob NOT NULL,
  `el_index` blob NOT NULL,
  PRIMARY KEY (`el_id`),
  KEY `el_from` (`el_from`,`el_to`(40)),
  KEY `el_to` (`el_to`(60),`el_from`),
  KEY `el_index` (`el_index`(60))
) ENGINE=InnoDB  DEFAULT CHARSET=binary AUTO_INCREMENT=10 ;

--
-- Dumping data for table `externallinks`
--

INSERT INTO `externallinks` (`el_id`, `el_from`, `el_to`, `el_index`) VALUES
(1, 1, 0x2f2f6d6574612e77696b696d656469612e6f72672f77696b692f48656c703a436f6e74656e7473, 0x687474703a2f2f6f72672e77696b696d656469612e6d6574612e2f77696b692f48656c703a436f6e74656e7473),
(2, 1, 0x2f2f6d6574612e77696b696d656469612e6f72672f77696b692f48656c703a436f6e74656e7473, 0x68747470733a2f2f6f72672e77696b696d656469612e6d6574612e2f77696b692f48656c703a436f6e74656e7473),
(3, 1, 0x2f2f7777772e6d6564696177696b692e6f72672f77696b692f5370656369616c3a4d794c616e67756167652f4d616e75616c3a436f6e66696775726174696f6e5f73657474696e6773, 0x687474703a2f2f6f72672e6d6564696177696b692e7777772e2f77696b692f5370656369616c3a4d794c616e67756167652f4d616e75616c3a436f6e66696775726174696f6e5f73657474696e6773),
(4, 1, 0x2f2f7777772e6d6564696177696b692e6f72672f77696b692f5370656369616c3a4d794c616e67756167652f4d616e75616c3a436f6e66696775726174696f6e5f73657474696e6773, 0x68747470733a2f2f6f72672e6d6564696177696b692e7777772e2f77696b692f5370656369616c3a4d794c616e67756167652f4d616e75616c3a436f6e66696775726174696f6e5f73657474696e6773),
(5, 1, 0x2f2f7777772e6d6564696177696b692e6f72672f77696b692f5370656369616c3a4d794c616e67756167652f4d616e75616c3a464151, 0x687474703a2f2f6f72672e6d6564696177696b692e7777772e2f77696b692f5370656369616c3a4d794c616e67756167652f4d616e75616c3a464151),
(6, 1, 0x2f2f7777772e6d6564696177696b692e6f72672f77696b692f5370656369616c3a4d794c616e67756167652f4d616e75616c3a464151, 0x68747470733a2f2f6f72672e6d6564696177696b692e7777772e2f77696b692f5370656369616c3a4d794c616e67756167652f4d616e75616c3a464151),
(7, 1, 0x68747470733a2f2f6c697374732e77696b696d656469612e6f72672f6d61696c6d616e2f6c697374696e666f2f6d6564696177696b692d616e6e6f756e6365, 0x68747470733a2f2f6f72672e77696b696d656469612e6c697374732e2f6d61696c6d616e2f6c697374696e666f2f6d6564696177696b692d616e6e6f756e6365),
(8, 1, 0x2f2f7777772e6d6564696177696b692e6f72672f77696b692f5370656369616c3a4d794c616e67756167652f4c6f63616c69736174696f6e235472616e736c6174696f6e5f7265736f7572636573, 0x687474703a2f2f6f72672e6d6564696177696b692e7777772e2f77696b692f5370656369616c3a4d794c616e67756167652f4c6f63616c69736174696f6e235472616e736c6174696f6e5f7265736f7572636573),
(9, 1, 0x2f2f7777772e6d6564696177696b692e6f72672f77696b692f5370656369616c3a4d794c616e67756167652f4c6f63616c69736174696f6e235472616e736c6174696f6e5f7265736f7572636573, 0x68747470733a2f2f6f72672e6d6564696177696b692e7777772e2f77696b692f5370656369616c3a4d794c616e67756167652f4c6f63616c69736174696f6e235472616e736c6174696f6e5f7265736f7572636573);

-- --------------------------------------------------------

--
-- Table structure for table `filearchive`
--

CREATE TABLE IF NOT EXISTS `filearchive` (
  `fa_id` int(11) NOT NULL AUTO_INCREMENT,
  `fa_name` varbinary(255) NOT NULL DEFAULT '',
  `fa_archive_name` varbinary(255) DEFAULT '',
  `fa_storage_group` varbinary(16) DEFAULT NULL,
  `fa_storage_key` varbinary(64) DEFAULT '',
  `fa_deleted_user` int(11) DEFAULT NULL,
  `fa_deleted_timestamp` binary(14) DEFAULT '\0\0\0\0\0\0\0\0\0\0\0\0\0\0',
  `fa_deleted_reason` blob,
  `fa_size` int(10) unsigned DEFAULT '0',
  `fa_width` int(11) DEFAULT '0',
  `fa_height` int(11) DEFAULT '0',
  `fa_metadata` mediumblob,
  `fa_bits` int(11) DEFAULT '0',
  `fa_media_type` enum('UNKNOWN','BITMAP','DRAWING','AUDIO','VIDEO','MULTIMEDIA','OFFICE','TEXT','EXECUTABLE','ARCHIVE') DEFAULT NULL,
  `fa_major_mime` enum('unknown','application','audio','image','text','video','message','model','multipart','chemical') DEFAULT 'unknown',
  `fa_minor_mime` varbinary(100) DEFAULT 'unknown',
  `fa_description` tinyblob,
  `fa_user` int(10) unsigned DEFAULT '0',
  `fa_user_text` varbinary(255) DEFAULT NULL,
  `fa_timestamp` binary(14) DEFAULT '\0\0\0\0\0\0\0\0\0\0\0\0\0\0',
  `fa_deleted` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `fa_sha1` varbinary(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`fa_id`),
  KEY `fa_name` (`fa_name`,`fa_timestamp`),
  KEY `fa_storage_group` (`fa_storage_group`,`fa_storage_key`),
  KEY `fa_deleted_timestamp` (`fa_deleted_timestamp`),
  KEY `fa_user_timestamp` (`fa_user_text`,`fa_timestamp`),
  KEY `fa_sha1` (`fa_sha1`(10))
) ENGINE=InnoDB DEFAULT CHARSET=binary AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `image`
--

CREATE TABLE IF NOT EXISTS `image` (
  `img_name` varbinary(255) NOT NULL DEFAULT '',
  `img_size` int(10) unsigned NOT NULL DEFAULT '0',
  `img_width` int(11) NOT NULL DEFAULT '0',
  `img_height` int(11) NOT NULL DEFAULT '0',
  `img_metadata` mediumblob NOT NULL,
  `img_bits` int(11) NOT NULL DEFAULT '0',
  `img_media_type` enum('UNKNOWN','BITMAP','DRAWING','AUDIO','VIDEO','MULTIMEDIA','OFFICE','TEXT','EXECUTABLE','ARCHIVE') DEFAULT NULL,
  `img_major_mime` enum('unknown','application','audio','image','text','video','message','model','multipart','chemical') NOT NULL DEFAULT 'unknown',
  `img_minor_mime` varbinary(100) NOT NULL DEFAULT 'unknown',
  `img_description` tinyblob NOT NULL,
  `img_user` int(10) unsigned NOT NULL DEFAULT '0',
  `img_user_text` varbinary(255) NOT NULL,
  `img_timestamp` varbinary(14) NOT NULL DEFAULT '',
  `img_sha1` varbinary(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`img_name`),
  KEY `img_usertext_timestamp` (`img_user_text`,`img_timestamp`),
  KEY `img_size` (`img_size`),
  KEY `img_timestamp` (`img_timestamp`),
  KEY `img_sha1` (`img_sha1`(10)),
  KEY `img_media_mime` (`img_media_type`,`img_major_mime`,`img_minor_mime`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `imagelinks`
--

CREATE TABLE IF NOT EXISTS `imagelinks` (
  `il_from` int(10) unsigned NOT NULL DEFAULT '0',
  `il_from_namespace` int(11) NOT NULL DEFAULT '0',
  `il_to` varbinary(255) NOT NULL DEFAULT '',
  UNIQUE KEY `il_from` (`il_from`,`il_to`),
  KEY `il_to` (`il_to`,`il_from`),
  KEY `il_backlinks_namespace` (`il_to`,`il_from_namespace`,`il_from`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `interwiki`
--

CREATE TABLE IF NOT EXISTS `interwiki` (
  `iw_prefix` varbinary(32) NOT NULL,
  `iw_url` blob NOT NULL,
  `iw_api` blob NOT NULL,
  `iw_wikiid` varbinary(64) NOT NULL,
  `iw_local` tinyint(1) NOT NULL,
  `iw_trans` tinyint(4) NOT NULL DEFAULT '0',
  UNIQUE KEY `iw_prefix` (`iw_prefix`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

--
-- Dumping data for table `interwiki`
--

INSERT INTO `interwiki` (`iw_prefix`, `iw_url`, `iw_api`, `iw_wikiid`, `iw_local`, `iw_trans`) VALUES
('acronym', 0x687474703a2f2f7777772e6163726f6e796d66696e6465722e636f6d2f7e2f7365617263682f61662e617370783f737472696e673d6578616374264163726f6e796d3d2431, '', '', 0, 0),
('advogato', 0x687474703a2f2f7777772e6164766f6761746f2e6f72672f2431, '', '', 0, 0),
('arxiv', 0x687474703a2f2f7777772e61727869762e6f72672f6162732f2431, '', '', 0, 0),
('c2find', 0x687474703a2f2f63322e636f6d2f6367692f77696b693f46696e64506167652676616c75653d2431, '', '', 0, 0),
('cache', 0x687474703a2f2f7777772e676f6f676c652e636f6d2f7365617263683f713d63616368653a2431, '', '', 0, 0),
('commons', 0x68747470733a2f2f636f6d6d6f6e732e77696b696d656469612e6f72672f77696b692f2431, 0x68747470733a2f2f636f6d6d6f6e732e77696b696d656469612e6f72672f772f6170692e706870, '', 0, 0),
('dictionary', 0x687474703a2f2f7777772e646963742e6f72672f62696e2f446963743f44617461626173653d2a26466f726d3d44696374312653747261746567793d2a2651756572793d2431, '', '', 0, 0),
('docbook', 0x687474703a2f2f77696b692e646f63626f6f6b2e6f72672f2431, '', '', 0, 0),
('doi', 0x687474703a2f2f64782e646f692e6f72672f2431, '', '', 0, 0),
('drumcorpswiki', 0x687474703a2f2f7777772e6472756d636f72707377696b692e636f6d2f2431, 0x687474703a2f2f6472756d636f72707377696b692e636f6d2f6170692e706870, '', 0, 0),
('dwjwiki', 0x687474703a2f2f7777772e737562657269632e6e65742f6367692d62696e2f64776a2f77696b692e6367693f2431, '', '', 0, 0),
('elibre', 0x687474703a2f2f656e6369636c6f70656469612e75732e65732f696e6465782e7068702f2431, 0x687474703a2f2f656e6369636c6f70656469612e75732e65732f6170692e706870, '', 0, 0),
('emacswiki', 0x687474703a2f2f7777772e656d61637377696b692e6f72672f6367692d62696e2f77696b692e706c3f2431, '', '', 0, 0),
('foldoc', 0x687474703a2f2f666f6c646f632e6f72672f3f2431, '', '', 0, 0),
('foxwiki', 0x687474703a2f2f666f782e77696b69732e636f6d2f77632e646c6c3f57696b697e2431, '', '', 0, 0),
('freebsdman', 0x687474703a2f2f7777772e467265654253442e6f72672f6367692f6d616e2e6367693f6170726f706f733d312671756572793d2431, '', '', 0, 0),
('gej', 0x687474703a2f2f7777772e6573706572616e746f2e64652f64656a2e6d616c6e6f76612f616b746976696b696f2e706c3f2431, '', '', 0, 0),
('gentoo-wiki', 0x687474703a2f2f67656e746f6f2d77696b692e636f6d2f2431, '', '', 0, 0),
('google', 0x687474703a2f2f7777772e676f6f676c652e636f6d2f7365617263683f713d2431, '', '', 0, 0),
('googlegroups', 0x687474703a2f2f67726f7570732e676f6f676c652e636f6d2f67726f7570733f713d2431, '', '', 0, 0),
('hammondwiki', 0x687474703a2f2f7777772e64616972696b692e6f72672f48616d6d6f6e6457696b692f2431, '', '', 0, 0),
('hrwiki', 0x687474703a2f2f7777772e687277696b692e6f72672f77696b692f2431, 0x687474703a2f2f7777772e687277696b692e6f72672f772f6170692e706870, '', 0, 0),
('imdb', 0x687474703a2f2f7777772e696d64622e636f6d2f66696e643f713d24312674743d6f6e, '', '', 0, 0),
('jargonfile', 0x687474703a2f2f73756e69722e6f72672f617070732f6d6574612e706c3f77696b693d4a6172676f6e46696c652672656469726563743d2431, '', '', 0, 0),
('kmwiki', 0x687474703a2f2f6b6d77696b692e77696b697370616365732e636f6d2f2431, '', '', 0, 0),
('linuxwiki', 0x687474703a2f2f6c696e757877696b692e64652f2431, '', '', 0, 0),
('lojban', 0x687474703a2f2f7777772e6c6f6a62616e2e6f72672f74696b692f74696b692d696e6465782e7068703f706167653d2431, '', '', 0, 0),
('lqwiki', 0x687474703a2f2f77696b692e6c696e75787175657374696f6e732e6f72672f77696b692f2431, '', '', 0, 0),
('lugkr', 0x687474703a2f2f7777772e6c75672d6b722e64652f77696b692f2431, '', '', 0, 0),
('meatball', 0x687474703a2f2f7777772e7573656d6f642e636f6d2f6367692d62696e2f6d622e706c3f2431, '', '', 0, 0),
('mediawikiwiki', 0x68747470733a2f2f7777772e6d6564696177696b692e6f72672f77696b692f2431, 0x68747470733a2f2f7777772e6d6564696177696b692e6f72672f772f6170692e706870, '', 0, 0),
('mediazilla', 0x68747470733a2f2f6275677a696c6c612e77696b696d656469612e6f72672f2431, '', '', 0, 0),
('memoryalpha', 0x687474703a2f2f656e2e6d656d6f72792d616c7068612e6f72672f77696b692f2431, 0x687474703a2f2f656e2e6d656d6f72792d616c7068612e6f72672f6170692e706870, '', 0, 0),
('metawiki', 0x687474703a2f2f73756e69722e6f72672f617070732f6d6574612e706c3f2431, '', '', 0, 0),
('metawikimedia', 0x68747470733a2f2f6d6574612e77696b696d656469612e6f72672f77696b692f2431, 0x68747470733a2f2f6d6574612e77696b696d656469612e6f72672f772f6170692e706870, '', 0, 0),
('mozillawiki', 0x687474703a2f2f77696b692e6d6f7a696c6c612e6f72672f2431, 0x68747470733a2f2f77696b692e6d6f7a696c6c612e6f72672f6170692e706870, '', 0, 0),
('mw', 0x68747470733a2f2f7777772e6d6564696177696b692e6f72672f77696b692f2431, 0x68747470733a2f2f7777772e6d6564696177696b692e6f72672f772f6170692e706870, '', 0, 0),
('oeis', 0x687474703a2f2f6f6569732e6f72672f2431, '', '', 0, 0),
('openwiki', 0x687474703a2f2f6f70656e77696b692e636f6d2f6f772e6173703f2431, '', '', 0, 0),
('ppr', 0x687474703a2f2f63322e636f6d2f6367692f77696b693f2431, '', '', 0, 0),
('pythoninfo', 0x687474703a2f2f77696b692e707974686f6e2e6f72672f6d6f696e2f2431, '', '', 0, 0),
('rfc', 0x687474703a2f2f7777772e7266632d656469746f722e6f72672f7266632f72666324312e747874, '', '', 0, 0),
('s23wiki', 0x687474703a2f2f7332332e6f72672f77696b692f2431, 0x687474703a2f2f7332332e6f72672f772f6170692e706870, '', 0, 0),
('seattlewireless', 0x687474703a2f2f73656174746c65776972656c6573732e6e65742f2431, '', '', 0, 0),
('senseislibrary', 0x687474703a2f2f73656e736569732e786d702e6e65742f3f2431, '', '', 0, 0),
('shoutwiki', 0x687474703a2f2f7777772e73686f757477696b692e636f6d2f77696b692f2431, 0x687474703a2f2f7777772e73686f757477696b692e636f6d2f772f6170692e706870, '', 0, 0),
('sourceforge', 0x687474703a2f2f736f75726365666f7267652e6e65742f2431, '', '', 0, 0),
('sourcewatch', 0x687474703a2f2f7777772e736f7572636577617463682e6f72672f696e6465782e7068703f7469746c653d2431, 0x687474703a2f2f7777772e736f7572636577617463682e6f72672f6170692e706870, '', 0, 0),
('squeak', 0x687474703a2f2f77696b692e73717565616b2e6f72672f73717565616b2f2431, '', '', 0, 0),
('tejo', 0x687474703a2f2f7777772e74656a6f2e6f72672f76696b696f2f2431, '', '', 0, 0),
('theopedia', 0x687474703a2f2f7777772e7468656f70656469612e636f6d2f2431, '', '', 0, 0),
('tmbw', 0x687474703a2f2f7777772e746d62772e6e65742f77696b692f2431, 0x687474703a2f2f746d62772e6e65742f77696b692f6170692e706870, '', 0, 0),
('tmnet', 0x687474703a2f2f7777772e746563686e6f6d616e69666573746f732e6e65742f3f2431, '', '', 0, 0),
('twiki', 0x687474703a2f2f7477696b692e6f72672f6367692d62696e2f766965772f2431, '', '', 0, 0),
('uea', 0x687474703a2f2f7565612e6f72672f76696b696f2f696e6465782e7068702f2431, 0x687474703a2f2f7565612e6f72672f76696b696f2f6170692e706870, '', 0, 0),
('uncyclopedia', 0x687474703a2f2f656e2e756e6379636c6f70656469612e636f2f77696b692f2431, 0x687474703a2f2f656e2e756e6379636c6f70656469612e636f2f772f6170692e706870, '', 0, 0),
('unreal', 0x687474703a2f2f77696b692e6265796f6e64756e7265616c2e636f6d2f2431, 0x687474703a2f2f77696b692e6265796f6e64756e7265616c2e636f6d2f772f6170692e706870, '', 0, 0),
('usemod', 0x687474703a2f2f7777772e7573656d6f642e636f6d2f6367692d62696e2f77696b692e706c3f2431, '', '', 0, 0),
('webseitzwiki', 0x687474703a2f2f776562736569747a2e666c7578656e742e636f6d2f77696b692f2431, '', '', 0, 0),
('wiki', 0x687474703a2f2f63322e636f6d2f6367692f77696b693f2431, '', '', 0, 0),
('wikia', 0x687474703a2f2f7777772e77696b69612e636f6d2f77696b692f2431, '', '', 0, 0),
('wikibooks', 0x68747470733a2f2f656e2e77696b69626f6f6b732e6f72672f77696b692f2431, 0x68747470733a2f2f656e2e77696b69626f6f6b732e6f72672f772f6170692e706870, '', 0, 0),
('wikif1', 0x687474703a2f2f7777772e77696b6966312e6f72672f2431, '', '', 0, 0),
('wikihow', 0x687474703a2f2f7777772e77696b69686f772e636f6d2f2431, 0x687474703a2f2f7777772e77696b69686f772e636f6d2f6170692e706870, '', 0, 0),
('wikimedia', 0x68747470733a2f2f77696b696d65646961666f756e646174696f6e2e6f72672f77696b692f2431, 0x68747470733a2f2f77696b696d65646961666f756e646174696f6e2e6f72672f772f6170692e706870, '', 0, 0),
('wikinews', 0x68747470733a2f2f656e2e77696b696e6577732e6f72672f77696b692f2431, 0x68747470733a2f2f656e2e77696b696e6577732e6f72672f772f6170692e706870, '', 0, 0),
('wikinfo', 0x687474703a2f2f77696b696e666f2e636f2f456e676c6973682f696e6465782e7068702f2431, '', '', 0, 0),
('wikipedia', 0x68747470733a2f2f656e2e77696b6970656469612e6f72672f77696b692f2431, 0x68747470733a2f2f656e2e77696b6970656469612e6f72672f772f6170692e706870, '', 0, 0),
('wikiquote', 0x68747470733a2f2f656e2e77696b6971756f74652e6f72672f77696b692f2431, 0x68747470733a2f2f656e2e77696b6971756f74652e6f72672f772f6170692e706870, '', 0, 0),
('wikisource', 0x68747470733a2f2f77696b69736f757263652e6f72672f77696b692f2431, 0x68747470733a2f2f77696b69736f757263652e6f72672f772f6170692e706870, '', 0, 0),
('wikispecies', 0x68747470733a2f2f737065636965732e77696b696d656469612e6f72672f77696b692f2431, 0x68747470733a2f2f737065636965732e77696b696d656469612e6f72672f772f6170692e706870, '', 0, 0),
('wikiversity', 0x68747470733a2f2f656e2e77696b69766572736974792e6f72672f77696b692f2431, 0x68747470733a2f2f656e2e77696b69766572736974792e6f72672f772f6170692e706870, '', 0, 0),
('wikivoyage', 0x68747470733a2f2f656e2e77696b69766f796167652e6f72672f77696b692f2431, 0x68747470733a2f2f656e2e77696b69766f796167652e6f72672f772f6170692e706870, '', 0, 0),
('wikt', 0x68747470733a2f2f656e2e77696b74696f6e6172792e6f72672f77696b692f2431, 0x68747470733a2f2f656e2e77696b74696f6e6172792e6f72672f772f6170692e706870, '', 0, 0),
('wiktionary', 0x68747470733a2f2f656e2e77696b74696f6e6172792e6f72672f77696b692f2431, 0x68747470733a2f2f656e2e77696b74696f6e6172792e6f72672f772f6170692e706870, '', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `ipblocks`
--

CREATE TABLE IF NOT EXISTS `ipblocks` (
  `ipb_id` int(11) NOT NULL AUTO_INCREMENT,
  `ipb_address` tinyblob NOT NULL,
  `ipb_user` int(10) unsigned NOT NULL DEFAULT '0',
  `ipb_by` int(10) unsigned NOT NULL DEFAULT '0',
  `ipb_by_text` varbinary(255) NOT NULL DEFAULT '',
  `ipb_reason` tinyblob NOT NULL,
  `ipb_timestamp` binary(14) NOT NULL DEFAULT '\0\0\0\0\0\0\0\0\0\0\0\0\0\0',
  `ipb_auto` tinyint(1) NOT NULL DEFAULT '0',
  `ipb_anon_only` tinyint(1) NOT NULL DEFAULT '0',
  `ipb_create_account` tinyint(1) NOT NULL DEFAULT '1',
  `ipb_enable_autoblock` tinyint(1) NOT NULL DEFAULT '1',
  `ipb_expiry` varbinary(14) NOT NULL DEFAULT '',
  `ipb_range_start` tinyblob NOT NULL,
  `ipb_range_end` tinyblob NOT NULL,
  `ipb_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `ipb_block_email` tinyint(1) NOT NULL DEFAULT '0',
  `ipb_allow_usertalk` tinyint(1) NOT NULL DEFAULT '0',
  `ipb_parent_block_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`ipb_id`),
  UNIQUE KEY `ipb_address` (`ipb_address`(255),`ipb_user`,`ipb_auto`,`ipb_anon_only`),
  KEY `ipb_user` (`ipb_user`),
  KEY `ipb_range` (`ipb_range_start`(8),`ipb_range_end`(8)),
  KEY `ipb_timestamp` (`ipb_timestamp`),
  KEY `ipb_expiry` (`ipb_expiry`),
  KEY `ipb_parent_block_id` (`ipb_parent_block_id`)
) ENGINE=InnoDB DEFAULT CHARSET=binary AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `iwlinks`
--

CREATE TABLE IF NOT EXISTS `iwlinks` (
  `iwl_from` int(10) unsigned NOT NULL DEFAULT '0',
  `iwl_prefix` varbinary(20) NOT NULL DEFAULT '',
  `iwl_title` varbinary(255) NOT NULL DEFAULT '',
  UNIQUE KEY `iwl_from` (`iwl_from`,`iwl_prefix`,`iwl_title`),
  KEY `iwl_prefix_title_from` (`iwl_prefix`,`iwl_title`,`iwl_from`),
  KEY `iwl_prefix_from_title` (`iwl_prefix`,`iwl_from`,`iwl_title`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `job`
--

CREATE TABLE IF NOT EXISTS `job` (
  `job_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `job_cmd` varbinary(60) NOT NULL DEFAULT '',
  `job_namespace` int(11) NOT NULL,
  `job_title` varbinary(255) NOT NULL,
  `job_timestamp` varbinary(14) DEFAULT NULL,
  `job_params` blob NOT NULL,
  `job_random` int(10) unsigned NOT NULL DEFAULT '0',
  `job_attempts` int(10) unsigned NOT NULL DEFAULT '0',
  `job_token` varbinary(32) NOT NULL DEFAULT '',
  `job_token_timestamp` varbinary(14) DEFAULT NULL,
  `job_sha1` varbinary(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`job_id`),
  KEY `job_sha1` (`job_sha1`),
  KEY `job_cmd_token` (`job_cmd`,`job_token`,`job_random`),
  KEY `job_cmd_token_id` (`job_cmd`,`job_token`,`job_id`),
  KEY `job_cmd` (`job_cmd`,`job_namespace`,`job_title`,`job_params`(128)),
  KEY `job_timestamp` (`job_timestamp`)
) ENGINE=InnoDB  DEFAULT CHARSET=binary AUTO_INCREMENT=2 ;

--
-- Dumping data for table `job`
--

INSERT INTO `job` (`job_id`, `job_cmd`, `job_namespace`, `job_title`, `job_timestamp`, `job_params`, `job_random`, `job_attempts`, `job_token`, `job_token_timestamp`, `job_sha1`) VALUES
(1, 'recentChangesUpdate', -1, 'RecentChanges', '20150217154446', 0x613a313a7b733a343a2274797065223b733a353a227075726765223b7d, 958179554, 0, '', NULL, '8ferf1wspb3h3lzdb9i3fqnhvidfu9q');

-- --------------------------------------------------------

--
-- Table structure for table `l10n_cache`
--

CREATE TABLE IF NOT EXISTS `l10n_cache` (
  `lc_lang` varbinary(32) NOT NULL,
  `lc_key` varbinary(255) NOT NULL,
  `lc_value` mediumblob NOT NULL,
  KEY `lc_lang_key` (`lc_lang`,`lc_key`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `langlinks`
--

CREATE TABLE IF NOT EXISTS `langlinks` (
  `ll_from` int(10) unsigned NOT NULL DEFAULT '0',
  `ll_lang` varbinary(20) NOT NULL DEFAULT '',
  `ll_title` varbinary(255) NOT NULL DEFAULT '',
  UNIQUE KEY `ll_from` (`ll_from`,`ll_lang`),
  KEY `ll_lang` (`ll_lang`,`ll_title`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `logging`
--

CREATE TABLE IF NOT EXISTS `logging` (
  `log_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `log_type` varbinary(32) NOT NULL DEFAULT '',
  `log_action` varbinary(32) NOT NULL DEFAULT '',
  `log_timestamp` binary(14) NOT NULL DEFAULT '19700101000000',
  `log_user` int(10) unsigned NOT NULL DEFAULT '0',
  `log_user_text` varbinary(255) NOT NULL DEFAULT '',
  `log_namespace` int(11) NOT NULL DEFAULT '0',
  `log_title` varbinary(255) NOT NULL DEFAULT '',
  `log_page` int(10) unsigned DEFAULT NULL,
  `log_comment` varbinary(255) NOT NULL DEFAULT '',
  `log_params` blob NOT NULL,
  `log_deleted` tinyint(3) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`log_id`),
  KEY `type_time` (`log_type`,`log_timestamp`),
  KEY `user_time` (`log_user`,`log_timestamp`),
  KEY `page_time` (`log_namespace`,`log_title`,`log_timestamp`),
  KEY `times` (`log_timestamp`),
  KEY `log_user_type_time` (`log_user`,`log_type`,`log_timestamp`),
  KEY `log_page_id_time` (`log_page`,`log_timestamp`),
  KEY `type_action` (`log_type`,`log_action`,`log_timestamp`),
  KEY `log_user_text_type_time` (`log_user_text`,`log_type`,`log_timestamp`),
  KEY `log_user_text_time` (`log_user_text`,`log_timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=binary AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `log_search`
--

CREATE TABLE IF NOT EXISTS `log_search` (
  `ls_field` varbinary(32) NOT NULL,
  `ls_value` varbinary(255) NOT NULL,
  `ls_log_id` int(10) unsigned NOT NULL DEFAULT '0',
  UNIQUE KEY `ls_field_val` (`ls_field`,`ls_value`,`ls_log_id`),
  KEY `ls_log_id` (`ls_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `module_deps`
--

CREATE TABLE IF NOT EXISTS `module_deps` (
  `md_module` varbinary(255) NOT NULL,
  `md_skin` varbinary(32) NOT NULL,
  `md_deps` mediumblob NOT NULL,
  UNIQUE KEY `md_module_skin` (`md_module`,`md_skin`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `msg_resource`
--

CREATE TABLE IF NOT EXISTS `msg_resource` (
  `mr_resource` varbinary(255) NOT NULL,
  `mr_lang` varbinary(32) NOT NULL,
  `mr_blob` mediumblob NOT NULL,
  `mr_timestamp` binary(14) NOT NULL,
  UNIQUE KEY `mr_resource_lang` (`mr_resource`,`mr_lang`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `msg_resource_links`
--

CREATE TABLE IF NOT EXISTS `msg_resource_links` (
  `mrl_resource` varbinary(255) NOT NULL,
  `mrl_message` varbinary(255) NOT NULL,
  UNIQUE KEY `mrl_message_resource` (`mrl_message`,`mrl_resource`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `objectcache`
--

CREATE TABLE IF NOT EXISTS `objectcache` (
  `keyname` varbinary(255) NOT NULL DEFAULT '',
  `value` mediumblob,
  `exptime` datetime DEFAULT NULL,
  PRIMARY KEY (`keyname`),
  KEY `exptime` (`exptime`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

--
-- Dumping data for table `objectcache`
--

INSERT INTO `objectcache` (`keyname`, `value`, `exptime`) VALUES
('my_wiki:pcache:idhash:1-0!*!*!*!*!*!*', 0xbd56eb6edb3614ee6f3f05ab61d805b575b19da48a63a0f0d22643dc788dd3fe0c1889b6b948a4405271bca2c01e61cfb827d93914e5487606b4eb50254e2c9e0bbf733f977118c5de8c2acdd465698ad278f1208c3fea78187bf99c3d18ef58c7e14114c6dea8188fb451522cc7539672fa81df71b2a29adc3226882e938469bd28b36c43b8d08666194b7b23df8974463ec817e38914bacc0c312b46469428969d78422e6496c9b5470c554b664ebc9bdb8c8a3b8f2419d5fac4031c4c099a118388c84ab1c589e7fb3933b4b7061839e2e949b5f4f1cd3f635911c3458609a3bdf13518f783266f4a9eb2914fc76421154084bf39355c0a02bfa5e6626941a106a2e5c2aca9623d0bbb335a45607b41450d285f77578ca61917cc233c3df1de306340c30dd8ad0c4bbdb13b20ee00fc00e2e351be8e01abd12cb1371774c94ebc29e582cce0ab471ce1c40b9f50d1161e8f7c80d51995d97894f1317af3cbdcb75eaf7bd67368f2a3f7ae0a96709ac5d3cd0515cb1260f9532a4a3801972ef8b254d66b37ba82070e6e9d93fa9c645c1bf4f7c807789d6f84f1f5abdfbc467ec2eb3e84af48ba9531858e7d1f6dd33bb997539ee554581aa697bf05dea542c85224ac090d5030aa1941310cf3b775d7854c28dc6843f6dd5c51a1b32aac8a69592aa8656fec781879048da5b3013ac99ca22de2910f89d8e98c9e77bba4f396ad6733b028e706ec2ca4329dc9ec9a18f016941a88c524e805e110f35d8a5477de31b4a54dee3f9267a044496c3070ff3dd71cea8108993292805f4d4cfa7e18d8a7cdba6482415aee301f3d324b6dfefef32ff600c599424f48b212d834ff0311f851f0f2301c46e4766398eecc595e808f1881889439b4967fe13be3cb15d38658a51a2b22658559c524f20741e7f4a160707acfa0f6b1eb9205e485ad1b072e40709d6e77eb4b1b1c0066553d2ab5ceaa7c4b7efcfe45ae5f40b032fdc238983f75404f2f08be27f0e09780d827245dd23512fa73e3922b7acfd0fe1a544213db0acd8adcb10dc9373798477161cf639e42db5fc5613778fef3e30f4117222ae857794122086f1085e098c16030b444c53074809da724ec10bcde8e9701cc9a3a312fb8b8d3de318d83f8e327a0c1e0c92760ce522ace7609e722e50935523509014e2e6e32568faf9771a3c3c2fb015eb7738d15729e6b9e478df3f3b44139c4eb73d0d8e43e82c3d73c63578caa647559605c9b7434f4d455ee16c110c76d1f407eee403be67108661cf6e3ffb1893ba5c3c17f528a5dd7693800585fdb256b0b8fbe0ccc67f434ab194331b4e903a140654f251db4b0ab6ad07ac7b77170ec64ce6089788284f9f356bec1a5476d5aa767b0279c435136f4639a4c655a666c3739aad3ab44f1c23469fd479ad9b4c5865bd2147ade4e42a2dcafba0af97baa7633bb5af9cea46c5a0f99987fa04a5469d13c7546db535812391849c1988fd62223938cddb36c9b49b1e7dee1b2d88bf03f98685726e7cc9d05c7d5a628f35ba69c58e855aab848d943f30cf02c94cc0d167aa3c86fea2247df633f968b05e4b80575d8af2ea0225949b50362bbb91d7faa2bf914762d67f25cde31b4fbd69a66f363a664c194693525949a5f4ecee6d30b540f106a28d0915c63ac3b5eab3f1e78b55a103f15f4364324d56d11407ed65cd19f61df630f3399f164d3b8a71fec31beb24b394bf75a517f5fe9a4d446e6bf5043af8b14dadd04e7113a0e53393adae3c7466647118a3434efa3b8c03de09d1d5535afcd9af025e603d0aa31d64d801bdce4426e1704fc1e056db63554599baf5ff11db4f98ac26d0a38fbaded5195b43ceedb24854fb5076047400bdbd2dbe5615ffe685fbedfdf9187d5a2da2bdc5a81db424b49e094b8f5c12a89da4aea615eaf1c9fa363d70ddb95c1ae212de9c8490f2a0b067b82d5aa52af284995114f5c1fa20f9c8a9de0c33e760fc82790ac77bfd3e40e6a6ddb1fb1075d37d3f3ed715544ef99d2b6bdda0887bd83de605b4913dc41e62efe7b956433c12ab67cb06e716cc8a838c4998d5314fa84beccd2295df2648be5a01679e7b694f3d44d8b7f00, '2015-02-18 15:44:46'),
('my_wiki:pcache:idoptions:1', 0x458ecb0ac2301045ff653ea0644292d6c9525cb89082a8fbd8061db00f3a4594d27fb7291477c339f75ea6a41dc13e54cf78e12602399a84501334578975d98fdcb5023e90a269162a16718b832c14bc9025c0cc6526dda816f75f4ac41068855669ccd11a63ec4af5963b7c7a1ebee0990a67944a2e4fae6bc7c0ad94affa141e5c81bfd32add563cc737a7178e752aa39f7f, '2015-02-18 15:44:46');

-- --------------------------------------------------------

--
-- Table structure for table `oldimage`
--

CREATE TABLE IF NOT EXISTS `oldimage` (
  `oi_name` varbinary(255) NOT NULL DEFAULT '',
  `oi_archive_name` varbinary(255) NOT NULL DEFAULT '',
  `oi_size` int(10) unsigned NOT NULL DEFAULT '0',
  `oi_width` int(11) NOT NULL DEFAULT '0',
  `oi_height` int(11) NOT NULL DEFAULT '0',
  `oi_bits` int(11) NOT NULL DEFAULT '0',
  `oi_description` tinyblob NOT NULL,
  `oi_user` int(10) unsigned NOT NULL DEFAULT '0',
  `oi_user_text` varbinary(255) NOT NULL,
  `oi_timestamp` binary(14) NOT NULL DEFAULT '\0\0\0\0\0\0\0\0\0\0\0\0\0\0',
  `oi_metadata` mediumblob NOT NULL,
  `oi_media_type` enum('UNKNOWN','BITMAP','DRAWING','AUDIO','VIDEO','MULTIMEDIA','OFFICE','TEXT','EXECUTABLE','ARCHIVE') DEFAULT NULL,
  `oi_major_mime` enum('unknown','application','audio','image','text','video','message','model','multipart','chemical') NOT NULL DEFAULT 'unknown',
  `oi_minor_mime` varbinary(100) NOT NULL DEFAULT 'unknown',
  `oi_deleted` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `oi_sha1` varbinary(32) NOT NULL DEFAULT '',
  KEY `oi_usertext_timestamp` (`oi_user_text`,`oi_timestamp`),
  KEY `oi_name_timestamp` (`oi_name`,`oi_timestamp`),
  KEY `oi_name_archive_name` (`oi_name`,`oi_archive_name`(14)),
  KEY `oi_sha1` (`oi_sha1`(10))
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `page`
--

CREATE TABLE IF NOT EXISTS `page` (
  `page_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `page_namespace` int(11) NOT NULL,
  `page_title` varbinary(255) NOT NULL,
  `page_restrictions` tinyblob NOT NULL,
  `page_is_redirect` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `page_is_new` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `page_random` double unsigned NOT NULL,
  `page_touched` binary(14) NOT NULL DEFAULT '\0\0\0\0\0\0\0\0\0\0\0\0\0\0',
  `page_links_updated` varbinary(14) DEFAULT NULL,
  `page_latest` int(10) unsigned NOT NULL,
  `page_len` int(10) unsigned NOT NULL,
  `page_content_model` varbinary(32) DEFAULT NULL,
  `page_lang` varbinary(35) DEFAULT NULL,
  PRIMARY KEY (`page_id`),
  UNIQUE KEY `name_title` (`page_namespace`,`page_title`),
  KEY `page_random` (`page_random`),
  KEY `page_len` (`page_len`),
  KEY `page_redirect_namespace_len` (`page_is_redirect`,`page_namespace`,`page_len`)
) ENGINE=InnoDB  DEFAULT CHARSET=binary AUTO_INCREMENT=3 ;

--
-- Dumping data for table `page`
--

INSERT INTO `page` (`page_id`, `page_namespace`, `page_title`, `page_restrictions`, `page_is_redirect`, `page_is_new`, `page_random`, `page_touched`, `page_links_updated`, `page_latest`, `page_len`, `page_content_model`, `page_lang`) VALUES
(1, 0, 'Main_Page', '', 0, 1, 0.846956492816, '20150217154446', '20150217154445', 1, 592, 'wikitext', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `pagelinks`
--

CREATE TABLE IF NOT EXISTS `pagelinks` (
  `pl_from` int(10) unsigned NOT NULL DEFAULT '0',
  `pl_from_namespace` int(11) NOT NULL DEFAULT '0',
  `pl_namespace` int(11) NOT NULL DEFAULT '0',
  `pl_title` varbinary(255) NOT NULL DEFAULT '',
  UNIQUE KEY `pl_from` (`pl_from`,`pl_namespace`,`pl_title`),
  KEY `pl_namespace` (`pl_namespace`,`pl_title`,`pl_from`),
  KEY `pl_backlinks_namespace` (`pl_namespace`,`pl_title`,`pl_from_namespace`,`pl_from`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `page_props`
--

CREATE TABLE IF NOT EXISTS `page_props` (
  `pp_page` int(11) NOT NULL,
  `pp_propname` varbinary(60) NOT NULL,
  `pp_value` blob NOT NULL,
  `pp_sortkey` float DEFAULT NULL,
  UNIQUE KEY `pp_page_propname` (`pp_page`,`pp_propname`),
  UNIQUE KEY `pp_propname_page` (`pp_propname`,`pp_page`),
  UNIQUE KEY `pp_propname_sortkey_page` (`pp_propname`,`pp_sortkey`,`pp_page`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `page_restrictions`
--

CREATE TABLE IF NOT EXISTS `page_restrictions` (
  `pr_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pr_page` int(11) NOT NULL,
  `pr_type` varbinary(60) NOT NULL,
  `pr_level` varbinary(60) NOT NULL,
  `pr_cascade` tinyint(4) NOT NULL,
  `pr_user` int(11) DEFAULT NULL,
  `pr_expiry` varbinary(14) DEFAULT NULL,
  PRIMARY KEY (`pr_id`),
  UNIQUE KEY `pr_pagetype` (`pr_page`,`pr_type`),
  KEY `pr_typelevel` (`pr_type`,`pr_level`),
  KEY `pr_level` (`pr_level`),
  KEY `pr_cascade` (`pr_cascade`)
) ENGINE=InnoDB DEFAULT CHARSET=binary AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `protected_titles`
--

CREATE TABLE IF NOT EXISTS `protected_titles` (
  `pt_namespace` int(11) NOT NULL,
  `pt_title` varbinary(255) NOT NULL,
  `pt_user` int(10) unsigned NOT NULL,
  `pt_reason` tinyblob,
  `pt_timestamp` binary(14) NOT NULL,
  `pt_expiry` varbinary(14) NOT NULL DEFAULT '',
  `pt_create_perm` varbinary(60) NOT NULL,
  UNIQUE KEY `pt_namespace_title` (`pt_namespace`,`pt_title`),
  KEY `pt_timestamp` (`pt_timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `querycache`
--

CREATE TABLE IF NOT EXISTS `querycache` (
  `qc_type` varbinary(32) NOT NULL,
  `qc_value` int(10) unsigned NOT NULL DEFAULT '0',
  `qc_namespace` int(11) NOT NULL DEFAULT '0',
  `qc_title` varbinary(255) NOT NULL DEFAULT '',
  KEY `qc_type` (`qc_type`,`qc_value`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `querycachetwo`
--

CREATE TABLE IF NOT EXISTS `querycachetwo` (
  `qcc_type` varbinary(32) NOT NULL,
  `qcc_value` int(10) unsigned NOT NULL DEFAULT '0',
  `qcc_namespace` int(11) NOT NULL DEFAULT '0',
  `qcc_title` varbinary(255) NOT NULL DEFAULT '',
  `qcc_namespacetwo` int(11) NOT NULL DEFAULT '0',
  `qcc_titletwo` varbinary(255) NOT NULL DEFAULT '',
  KEY `qcc_type` (`qcc_type`,`qcc_value`),
  KEY `qcc_title` (`qcc_type`,`qcc_namespace`,`qcc_title`),
  KEY `qcc_titletwo` (`qcc_type`,`qcc_namespacetwo`,`qcc_titletwo`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `querycache_info`
--

CREATE TABLE IF NOT EXISTS `querycache_info` (
  `qci_type` varbinary(32) NOT NULL DEFAULT '',
  `qci_timestamp` binary(14) NOT NULL DEFAULT '19700101000000',
  UNIQUE KEY `qci_type` (`qci_type`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `recentchanges`
--

CREATE TABLE IF NOT EXISTS `recentchanges` (
  `rc_id` int(11) NOT NULL AUTO_INCREMENT,
  `rc_timestamp` varbinary(14) NOT NULL DEFAULT '',
  `rc_user` int(10) unsigned NOT NULL DEFAULT '0',
  `rc_user_text` varbinary(255) NOT NULL,
  `rc_namespace` int(11) NOT NULL DEFAULT '0',
  `rc_title` varbinary(255) NOT NULL DEFAULT '',
  `rc_comment` varbinary(255) NOT NULL DEFAULT '',
  `rc_minor` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `rc_bot` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `rc_new` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `rc_cur_id` int(10) unsigned NOT NULL DEFAULT '0',
  `rc_this_oldid` int(10) unsigned NOT NULL DEFAULT '0',
  `rc_last_oldid` int(10) unsigned NOT NULL DEFAULT '0',
  `rc_type` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `rc_source` varbinary(16) NOT NULL DEFAULT '',
  `rc_patrolled` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `rc_ip` varbinary(40) NOT NULL DEFAULT '',
  `rc_old_len` int(11) DEFAULT NULL,
  `rc_new_len` int(11) DEFAULT NULL,
  `rc_deleted` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `rc_logid` int(10) unsigned NOT NULL DEFAULT '0',
  `rc_log_type` varbinary(255) DEFAULT NULL,
  `rc_log_action` varbinary(255) DEFAULT NULL,
  `rc_params` blob,
  PRIMARY KEY (`rc_id`),
  KEY `rc_timestamp` (`rc_timestamp`),
  KEY `rc_namespace_title` (`rc_namespace`,`rc_title`),
  KEY `rc_cur_id` (`rc_cur_id`),
  KEY `new_name_timestamp` (`rc_new`,`rc_namespace`,`rc_timestamp`),
  KEY `rc_ip` (`rc_ip`),
  KEY `rc_ns_usertext` (`rc_namespace`,`rc_user_text`),
  KEY `rc_user_text` (`rc_user_text`,`rc_timestamp`)
) ENGINE=InnoDB  DEFAULT CHARSET=binary AUTO_INCREMENT=2 ;

--
-- Dumping data for table `recentchanges`
--

INSERT INTO `recentchanges` (`rc_id`, `rc_timestamp`, `rc_user`, `rc_user_text`, `rc_namespace`, `rc_title`, `rc_comment`, `rc_minor`, `rc_bot`, `rc_new`, `rc_cur_id`, `rc_this_oldid`, `rc_last_oldid`, `rc_type`, `rc_source`, `rc_patrolled`, `rc_ip`, `rc_old_len`, `rc_new_len`, `rc_deleted`, `rc_logid`, `rc_log_type`, `rc_log_action`, `rc_params`) VALUES
(1, '20150217154446', 0, 'MediaWiki default', 0, 'Main_Page', '', 0, 0, 1, 1, 1, 0, 1, 'mw.new', 0, '127.0.0.1', 0, 592, 0, 0, NULL, '', '');

-- --------------------------------------------------------

--
-- Table structure for table `redirect`
--

CREATE TABLE IF NOT EXISTS `redirect` (
  `rd_from` int(10) unsigned NOT NULL DEFAULT '0',
  `rd_namespace` int(11) NOT NULL DEFAULT '0',
  `rd_title` varbinary(255) NOT NULL DEFAULT '',
  `rd_interwiki` varbinary(32) DEFAULT NULL,
  `rd_fragment` varbinary(255) DEFAULT NULL,
  PRIMARY KEY (`rd_from`),
  KEY `rd_ns_title` (`rd_namespace`,`rd_title`,`rd_from`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `revision`
--

CREATE TABLE IF NOT EXISTS `revision` (
  `rev_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `rev_page` int(10) unsigned NOT NULL,
  `rev_text_id` int(10) unsigned NOT NULL,
  `rev_comment` tinyblob NOT NULL,
  `rev_user` int(10) unsigned NOT NULL DEFAULT '0',
  `rev_user_text` varbinary(255) NOT NULL DEFAULT '',
  `rev_timestamp` binary(14) NOT NULL DEFAULT '\0\0\0\0\0\0\0\0\0\0\0\0\0\0',
  `rev_minor_edit` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `rev_deleted` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `rev_len` int(10) unsigned DEFAULT NULL,
  `rev_parent_id` int(10) unsigned DEFAULT NULL,
  `rev_sha1` varbinary(32) NOT NULL DEFAULT '',
  `rev_content_model` varbinary(32) DEFAULT NULL,
  `rev_content_format` varbinary(64) DEFAULT NULL,
  PRIMARY KEY (`rev_id`),
  UNIQUE KEY `rev_page_id` (`rev_page`,`rev_id`),
  KEY `rev_timestamp` (`rev_timestamp`),
  KEY `page_timestamp` (`rev_page`,`rev_timestamp`),
  KEY `user_timestamp` (`rev_user`,`rev_timestamp`),
  KEY `usertext_timestamp` (`rev_user_text`,`rev_timestamp`),
  KEY `page_user_timestamp` (`rev_page`,`rev_user`,`rev_timestamp`)
) ENGINE=InnoDB  DEFAULT CHARSET=binary MAX_ROWS=10000000 AVG_ROW_LENGTH=1024 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `revision`
--

INSERT INTO `revision` (`rev_id`, `rev_page`, `rev_text_id`, `rev_comment`, `rev_user`, `rev_user_text`, `rev_timestamp`, `rev_minor_edit`, `rev_deleted`, `rev_len`, `rev_parent_id`, `rev_sha1`, `rev_content_model`, `rev_content_format`) VALUES
(1, 1, 1, '', 0, 'MediaWiki default', '20150217154446', 0, 0, 592, 0, 'glba3g2evzm40dqnqxegze66eqibkvb', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `searchindex`
--

CREATE TABLE IF NOT EXISTS `searchindex` (
  `si_page` int(10) unsigned NOT NULL,
  `si_title` varchar(255) NOT NULL DEFAULT '',
  `si_text` mediumtext NOT NULL,
  UNIQUE KEY `si_page` (`si_page`),
  FULLTEXT KEY `si_title` (`si_title`),
  FULLTEXT KEY `si_text` (`si_text`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `sites`
--

CREATE TABLE IF NOT EXISTS `sites` (
  `site_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `site_global_key` varbinary(32) NOT NULL,
  `site_type` varbinary(32) NOT NULL,
  `site_group` varbinary(32) NOT NULL,
  `site_source` varbinary(32) NOT NULL,
  `site_language` varbinary(32) NOT NULL,
  `site_protocol` varbinary(32) NOT NULL,
  `site_domain` varbinary(255) NOT NULL,
  `site_data` blob NOT NULL,
  `site_forward` tinyint(1) NOT NULL,
  `site_config` blob NOT NULL,
  PRIMARY KEY (`site_id`),
  UNIQUE KEY `sites_global_key` (`site_global_key`),
  KEY `sites_type` (`site_type`),
  KEY `sites_group` (`site_group`),
  KEY `sites_source` (`site_source`),
  KEY `sites_language` (`site_language`),
  KEY `sites_protocol` (`site_protocol`),
  KEY `sites_domain` (`site_domain`),
  KEY `sites_forward` (`site_forward`)
) ENGINE=InnoDB DEFAULT CHARSET=binary AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `site_identifiers`
--

CREATE TABLE IF NOT EXISTS `site_identifiers` (
  `si_site` int(10) unsigned NOT NULL,
  `si_type` varbinary(32) NOT NULL,
  `si_key` varbinary(32) NOT NULL,
  UNIQUE KEY `site_ids_type` (`si_type`,`si_key`),
  KEY `site_ids_site` (`si_site`),
  KEY `site_ids_key` (`si_key`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `site_stats`
--

CREATE TABLE IF NOT EXISTS `site_stats` (
  `ss_row_id` int(10) unsigned NOT NULL,
  `ss_total_edits` bigint(20) unsigned DEFAULT '0',
  `ss_good_articles` bigint(20) unsigned DEFAULT '0',
  `ss_total_pages` bigint(20) DEFAULT '-1',
  `ss_users` bigint(20) DEFAULT '-1',
  `ss_active_users` bigint(20) DEFAULT '-1',
  `ss_images` int(11) DEFAULT '0',
  UNIQUE KEY `ss_row_id` (`ss_row_id`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

--
-- Dumping data for table `site_stats`
--

INSERT INTO `site_stats` (`ss_row_id`, `ss_total_edits`, `ss_good_articles`, `ss_total_pages`, `ss_users`, `ss_active_users`, `ss_images`) VALUES
(1, 0, 0, 0, 1, -1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tag_summary`
--

CREATE TABLE IF NOT EXISTS `tag_summary` (
  `ts_rc_id` int(11) DEFAULT NULL,
  `ts_log_id` int(11) DEFAULT NULL,
  `ts_rev_id` int(11) DEFAULT NULL,
  `ts_tags` blob NOT NULL,
  UNIQUE KEY `tag_summary_rc_id` (`ts_rc_id`),
  UNIQUE KEY `tag_summary_log_id` (`ts_log_id`),
  UNIQUE KEY `tag_summary_rev_id` (`ts_rev_id`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `templatelinks`
--

CREATE TABLE IF NOT EXISTS `templatelinks` (
  `tl_from` int(10) unsigned NOT NULL DEFAULT '0',
  `tl_from_namespace` int(11) NOT NULL DEFAULT '0',
  `tl_namespace` int(11) NOT NULL DEFAULT '0',
  `tl_title` varbinary(255) NOT NULL DEFAULT '',
  UNIQUE KEY `tl_from` (`tl_from`,`tl_namespace`,`tl_title`),
  KEY `tl_namespace` (`tl_namespace`,`tl_title`,`tl_from`),
  KEY `tl_backlinks_namespace` (`tl_namespace`,`tl_title`,`tl_from_namespace`,`tl_from`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `text`
--

CREATE TABLE IF NOT EXISTS `text` (
  `old_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `old_text` mediumblob NOT NULL,
  `old_flags` tinyblob NOT NULL,
  PRIMARY KEY (`old_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=binary MAX_ROWS=10000000 AVG_ROW_LENGTH=10240 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `text`
--

INSERT INTO `text` (`old_id`, `old_text`, `old_flags`) VALUES
(1, 0x3c7374726f6e673e4d6564696157696b6920686173206265656e207375636365737366756c6c7920696e7374616c6c65642e3c2f7374726f6e673e0a0a436f6e73756c7420746865205b2f2f6d6574612e77696b696d656469612e6f72672f77696b692f48656c703a436f6e74656e7473205573657227732047756964655d20666f7220696e666f726d6174696f6e206f6e207573696e67207468652077696b6920736f6674776172652e0a0a3d3d2047657474696e672073746172746564203d3d0a2a205b2f2f7777772e6d6564696177696b692e6f72672f77696b692f5370656369616c3a4d794c616e67756167652f4d616e75616c3a436f6e66696775726174696f6e5f73657474696e677320436f6e66696775726174696f6e2073657474696e6773206c6973745d0a2a205b2f2f7777772e6d6564696177696b692e6f72672f77696b692f5370656369616c3a4d794c616e67756167652f4d616e75616c3a464151204d6564696157696b69204641515d0a2a205b68747470733a2f2f6c697374732e77696b696d656469612e6f72672f6d61696c6d616e2f6c697374696e666f2f6d6564696177696b692d616e6e6f756e6365204d6564696157696b692072656c65617365206d61696c696e67206c6973745d0a2a205b2f2f7777772e6d6564696177696b692e6f72672f77696b692f5370656369616c3a4d794c616e67756167652f4c6f63616c69736174696f6e235472616e736c6174696f6e5f7265736f7572636573204c6f63616c697365204d6564696157696b6920666f7220796f7572206c616e67756167655d, 0x7574662d38);

-- --------------------------------------------------------

--
-- Table structure for table `transcache`
--

CREATE TABLE IF NOT EXISTS `transcache` (
  `tc_url` varbinary(255) NOT NULL,
  `tc_contents` blob,
  `tc_time` binary(14) NOT NULL,
  UNIQUE KEY `tc_url_idx` (`tc_url`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `updatelog`
--

CREATE TABLE IF NOT EXISTS `updatelog` (
  `ul_key` varbinary(255) NOT NULL,
  `ul_value` blob,
  PRIMARY KEY (`ul_key`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

--
-- Dumping data for table `updatelog`
--

INSERT INTO `updatelog` (`ul_key`, `ul_value`) VALUES
('filearchive-fa_major_mime-patch-fa_major_mime-chemical.sql', NULL),
('image-img_major_mime-patch-img_major_mime-chemical.sql', NULL),
('oldimage-oi_major_mime-patch-oi_major_mime-chemical.sql', NULL),
('user_former_groups-ufg_group-patch-ufg_group-length-increase-255.sql', NULL),
('user_groups-ug_group-patch-ug_group-length-increase-255.sql', NULL),
('user_properties-up_property-patch-up_property.sql', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `uploadstash`
--

CREATE TABLE IF NOT EXISTS `uploadstash` (
  `us_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `us_user` int(10) unsigned NOT NULL,
  `us_key` varbinary(255) NOT NULL,
  `us_orig_path` varbinary(255) NOT NULL,
  `us_path` varbinary(255) NOT NULL,
  `us_source_type` varbinary(50) DEFAULT NULL,
  `us_timestamp` varbinary(14) NOT NULL,
  `us_status` varbinary(50) NOT NULL,
  `us_chunk_inx` int(10) unsigned DEFAULT NULL,
  `us_props` blob,
  `us_size` int(10) unsigned NOT NULL,
  `us_sha1` varbinary(31) NOT NULL,
  `us_mime` varbinary(255) DEFAULT NULL,
  `us_media_type` enum('UNKNOWN','BITMAP','DRAWING','AUDIO','VIDEO','MULTIMEDIA','OFFICE','TEXT','EXECUTABLE','ARCHIVE') DEFAULT NULL,
  `us_image_width` int(10) unsigned DEFAULT NULL,
  `us_image_height` int(10) unsigned DEFAULT NULL,
  `us_image_bits` smallint(5) unsigned DEFAULT NULL,
  PRIMARY KEY (`us_id`),
  UNIQUE KEY `us_key` (`us_key`),
  KEY `us_user` (`us_user`),
  KEY `us_timestamp` (`us_timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=binary AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varbinary(255) NOT NULL DEFAULT '',
  `user_real_name` varbinary(255) NOT NULL DEFAULT '',
  `user_password` tinyblob NOT NULL,
  `user_newpassword` tinyblob NOT NULL,
  `user_newpass_time` binary(14) DEFAULT NULL,
  `user_email` tinyblob NOT NULL,
  `user_touched` binary(14) NOT NULL DEFAULT '\0\0\0\0\0\0\0\0\0\0\0\0\0\0',
  `user_token` binary(32) NOT NULL DEFAULT '\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0',
  `user_email_authenticated` binary(14) DEFAULT NULL,
  `user_email_token` binary(32) DEFAULT NULL,
  `user_email_token_expires` binary(14) DEFAULT NULL,
  `user_registration` binary(14) DEFAULT NULL,
  `user_editcount` int(11) DEFAULT NULL,
  `user_password_expires` varbinary(14) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`),
  KEY `user_email_token` (`user_email_token`),
  KEY `user_email` (`user_email`(50))
) ENGINE=InnoDB  DEFAULT CHARSET=binary AUTO_INCREMENT=2 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `user_real_name`, `user_password`, `user_newpassword`, `user_newpass_time`, `user_email`, `user_touched`, `user_token`, `user_email_authenticated`, `user_email_token`, `user_email_token_expires`, `user_registration`, `user_editcount`, `user_password_expires`) VALUES
(1, 'Root', '', 0x3a70626b6466323a7368613235363a31303030303a3132383a784d556b6a6c6b554b636b3037625151524c6c5144773d3d3a6e522f5a6e366777436d6b49654b6c464c6277686a53787564556d4b6e37684e664c4a4b4f74706d714872754d704a304b707354444d36616d67332b7152614a2b7557664269317966636b39762b5047353041574367774c794c617348496c7135743257517a7a586d702f4c4b5076694d463765304c4638554c5879343373464f69633830384650412f3078326a4674534256776a454a3955384736727133444e694f56303539745553553d, '', NULL, '', '20150217154450', '7fbc17e6146854ea3102c6e5406522a9', NULL, '\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0', NULL, '20150217154445', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_former_groups`
--

CREATE TABLE IF NOT EXISTS `user_former_groups` (
  `ufg_user` int(10) unsigned NOT NULL DEFAULT '0',
  `ufg_group` varbinary(255) NOT NULL DEFAULT '',
  UNIQUE KEY `ufg_user_group` (`ufg_user`,`ufg_group`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `user_groups`
--

CREATE TABLE IF NOT EXISTS `user_groups` (
  `ug_user` int(10) unsigned NOT NULL DEFAULT '0',
  `ug_group` varbinary(255) NOT NULL DEFAULT '',
  UNIQUE KEY `ug_user_group` (`ug_user`,`ug_group`),
  KEY `ug_group` (`ug_group`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

--
-- Dumping data for table `user_groups`
--

INSERT INTO `user_groups` (`ug_user`, `ug_group`) VALUES
(1, 'bureaucrat'),
(1, 'sysop');

-- --------------------------------------------------------

--
-- Table structure for table `user_newtalk`
--

CREATE TABLE IF NOT EXISTS `user_newtalk` (
  `user_id` int(11) NOT NULL DEFAULT '0',
  `user_ip` varbinary(40) NOT NULL DEFAULT '',
  `user_last_timestamp` varbinary(14) DEFAULT NULL,
  KEY `user_id` (`user_id`),
  KEY `user_ip` (`user_ip`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `user_properties`
--

CREATE TABLE IF NOT EXISTS `user_properties` (
  `up_user` int(11) NOT NULL,
  `up_property` varbinary(255) NOT NULL,
  `up_value` blob,
  UNIQUE KEY `user_properties_user_property` (`up_user`,`up_property`),
  KEY `user_properties_property` (`up_property`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `valid_tag`
--

CREATE TABLE IF NOT EXISTS `valid_tag` (
  `vt_tag` varbinary(255) NOT NULL,
  PRIMARY KEY (`vt_tag`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

-- --------------------------------------------------------

--
-- Table structure for table `watchlist`
--

CREATE TABLE IF NOT EXISTS `watchlist` (
  `wl_user` int(10) unsigned NOT NULL,
  `wl_namespace` int(11) NOT NULL DEFAULT '0',
  `wl_title` varbinary(255) NOT NULL DEFAULT '',
  `wl_notificationtimestamp` varbinary(14) DEFAULT NULL,
  UNIQUE KEY `wl_user` (`wl_user`,`wl_namespace`,`wl_title`),
  KEY `namespace_title` (`wl_namespace`,`wl_title`),
  KEY `wl_user_notificationtimestamp` (`wl_user`,`wl_notificationtimestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=binary;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
