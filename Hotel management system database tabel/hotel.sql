-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 09, 2026 at 04:41 PM
-- Server version: 5.7.24
-- PHP Version: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotel`
--

-- --------------------------------------------------------

--
-- Table structure for table `addfood`
--

DROP TABLE IF EXISTS `addfood`;
CREATE TABLE IF NOT EXISTS `addfood` (
  `autoId` bigint(10) NOT NULL AUTO_INCREMENT,
  `AddFoodName` varchar(20) NOT NULL,
  `AddFooodCategory` varchar(10) NOT NULL,
  `foodvariety` text NOT NULL,
  `AddFoodType` varchar(10) NOT NULL,
  `AddFoodPrice` bigint(10) NOT NULL,
  PRIMARY KEY (`autoId`),
  UNIQUE KEY `autoId` (`autoId`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `addfood`
--

INSERT INTO `addfood` (`autoId`, `AddFoodName`, `AddFooodCategory`, `foodvariety`, `AddFoodType`, `AddFoodPrice`) VALUES
(3, 'Samosha', 'Veg', '01 Samosha', 'Snacks', 10),
(5, 'Chai', 'Veg', 'Tea', 'Drinks', 10),
(6, 'Chicken', 'Non Veg', '1 leg', 'Meal', 120);

-- --------------------------------------------------------

--
-- Table structure for table `addroom`
--

DROP TABLE IF EXISTS `addroom`;
CREATE TABLE IF NOT EXISTS `addroom` (
  `autoId` bigint(5) NOT NULL AUTO_INCREMENT,
  `AddRoomNo` bigint(5) NOT NULL,
  `AddRoomCategory` varchar(10) DEFAULT NULL,
  `AddRoomType` varchar(10) DEFAULT NULL,
  `AddRoomPrice` bigint(5) DEFAULT NULL,
  PRIMARY KEY (`autoId`),
  UNIQUE KEY `autoId` (`autoId`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `addroom`
--

INSERT INTO `addroom` (`autoId`, `AddRoomNo`, `AddRoomCategory`, `AddRoomType`, `AddRoomPrice`) VALUES
(2, 100, 'Non Ac', 'Single bed', 300),
(3, 101, 'Ac', 'Single bed', 450),
(4, 102, 'Non Ac', 'Dauble bed', 500),
(5, 103, 'Ac', 'Dauble bed', 750),
(6, 104, 'Non Ac', 'Delux', 900),
(7, 105, 'Ac', 'Delux', 1000),
(8, 106, 'Non Ac', 'Suit', 1200),
(9, 107, 'Ac', 'Suit', 105);

-- --------------------------------------------------------

--
-- Table structure for table `attendence`
--

DROP TABLE IF EXISTS `attendence`;
CREATE TABLE IF NOT EXISTS `attendence` (
  `autoId` bigint(10) NOT NULL AUTO_INCREMENT,
  `Employee_Name` varchar(50) NOT NULL,
  `Employee_Id` varchar(10) NOT NULL,
  `Attendance` varchar(15) NOT NULL,
  `leave_Reason` text,
  `Date` varchar(12) NOT NULL,
  `Time` varchar(15) NOT NULL,
  PRIMARY KEY (`autoId`),
  UNIQUE KEY `autoId` (`autoId`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `attendence`
--

INSERT INTO `attendence` (`autoId`, `Employee_Name`, `Employee_Id`, `Attendance`, `leave_Reason`, `Date`, `Time`) VALUES
(12, 'RAM', '410', 'Present', '', '  25-08-2025', ' 12:33:56 am'),
(13, 'raj', '420', 'Leave', 'Work At Home', '  25-08-2025', ' 12:18:59 am'),
(14, 'AJAY', '415', 'Absent', '', '  25-08-2025', ' 12:17:40 am');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `autoId` bigint(10) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(30) NOT NULL,
  `dob` date NOT NULL,
  `contactno` bigint(12) NOT NULL,
  `documtype` varchar(16) NOT NULL,
  `documentno` varchar(15) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `address` varchar(40) NOT NULL,
  `roomcategory` varchar(30) NOT NULL,
  `Roomtype` varchar(15) NOT NULL,
  `CheakinDate` date NOT NULL,
  `CheakoutDate` date NOT NULL,
  `Price` bigint(20) NOT NULL,
  PRIMARY KEY (`autoId`),
  UNIQUE KEY `autoId` (`autoId`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`autoId`, `fullname`, `dob`, `contactno`, `documtype`, `documentno`, `gender`, `address`, `roomcategory`, `Roomtype`, `CheakinDate`, `CheakoutDate`, `Price`) VALUES
(5, 'Gajendra thakur', '2002-05-05', 1234567890, 'Aadhar card', '9638520147159', 'Male', 'Damoh Teen gulli damoh 470661', 'Ac', 'Single bed', '2025-09-01', '2025-09-02', 1000),
(6, 'Lakhan Singh', '2002-04-09', 7896541302, 'Pan Card', 'sg25dsfd', 'Other', 'srthsztgb sfbgsbsr sbtsgb 5456...', 'Non Ac', 'Triple bed', '2025-08-08', '2025-08-09', 1200),
(7, 'hello', '2001-01-01', 1234, 'Voter Id', '56789', 'Male', 'damoh', 'Ac', 'Suit', '2025-03-06', '2025-03-07', 1300),
(8, 'sir ji', '2000-01-01', 98765, 'Aadhar card', '123012301230', 'Female', 'dd ddd ddd ddd 4569', 'Ac', 'Dauble bed', '2024-04-04', '2024-04-05', 900);

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `autoid` bigint(10) NOT NULL AUTO_INCREMENT,
  `id` varchar(10) NOT NULL,
  `name` varchar(30) NOT NULL,
  `fname` varchar(30) NOT NULL,
  `gender` varchar(12) NOT NULL,
  `dob` date NOT NULL,
  `contact` bigint(15) NOT NULL,
  `Document_Type` varchar(15) NOT NULL,
  `Document_Num` bigint(15) NOT NULL,
  `full_Addres` varchar(50) NOT NULL,
  `joining_Date` date NOT NULL,
  `position` varchar(15) NOT NULL,
  `salary` bigint(10) NOT NULL,
  PRIMARY KEY (`autoid`),
  UNIQUE KEY `autoid` (`autoid`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`autoid`, `id`, `name`, `fname`, `gender`, `dob`, `contact`, `Document_Type`, `Document_Num`, `full_Addres`, `joining_Date`, `position`, `salary`) VALUES
(4, 'ABC1', 'ROHIT', 'RAM', 'Male', '2002-08-08', 1234567890, 'Aadhar Card', 9876543211011, 'DAMOH DAMOH DAMOH MP34', '2025-01-01', 'WATCHMAN', 15000),
(5, 'ABC2', 'RAJ', 'RAJA', 'Male', '2001-01-02', 1234567891, 'Aadhar Card', 963852741078945, 'AAAA BBBB CCCC DDDD 456123', '2024-08-04', 'I DONT KNOW', 1000);

-- --------------------------------------------------------

--
-- Table structure for table `signup`
--

DROP TABLE IF EXISTS `signup`;
CREATE TABLE IF NOT EXISTS `signup` (
  `name` varchar(50) NOT NULL,
  `email` varchar(30) NOT NULL,
  `contact` bigint(10) NOT NULL,
  `userName` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `repassword` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `signup`
--

INSERT INTO `signup` (`name`, `email`, `contact`, `userName`, `password`, `repassword`) VALUES
('Vinay Singh Rajpoot', 'VSR@gmail.com', 9754888478, 'Prinevsr', '12345', '12345'),
('PRincesrr', 'vsr0091@gmail.com', 1023456789, 'Princessrr', '8462', '8462');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
