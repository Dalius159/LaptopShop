CREATE DATABASE IF NOT EXISTS Laptop_WebStore /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE Laptop_WebStore;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


DROP TABLE IF EXISTS cart_pointer;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE cart_pointer (
  id bigint NOT NULL AUTO_INCREMENT,
  quantity tinyint NOT NULL,
  cart_id bigint,
  product_id bigint,
  PRIMARY KEY (id),
  CONSTRAINT FK49lmmclnjgb7eck20lwhv0cks FOREIGN KEY (cart_id) REFERENCES cart(id),
  CONSTRAINT FKkd69a7wiulr4xgohxl0rlhth4 FOREIGN KEY (product_id) REFERENCES product(id)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
--
LOCK TABLES cart_pointer WRITE;
/*!40000 ALTER TABLE cart_pointer DISABLE KEYS */;
INSERT INTO cart_pointer VALUES (10,7,1,64),(11,1,1,54),(12,1,1,60),(13,2,1,56),(14,1,1,62),(15,1,2,88),(16,1,2,85);
/*!40000 ALTER TABLE cart_pointer ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS order_details;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE order_details(
  id bigint NOT NULL AUTO_INCREMENT,
  cost int NOT NULL,
  order_quantity tinyint NOT NULL,
  order_id bigint,
  product_id bigint,
  PRIMARY KEY (id),
  CONSTRAINT FK9wl3houbukbxpixsut6uvojhy FOREIGN KEY (order_id) REFERENCES orders(id),
  CONSTRAINT FK3ry84nmdxgoarx53qjxd671tk FOREIGN KEY (product_id) REFERENCES product(id)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
--
LOCK TABLES order_details WRITE;
/*!40000 ALTER TABLE order_details DISABLE KEYS */;
INSERT INTO order_details VALUES (1,13980000,2,32,62),(2,25990000,1,32,57),(3,22490000,1,32,54);
/*!40000 ALTER TABLE order_details ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS category;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE category (
  id bigint NOT NULL AUTO_INCREMENT,
  category_name varchar(30),
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
--
LOCK TABLES category WRITE;
/*!40000 ALTER TABLE category DISABLE KEYS */;
INSERT INTO category VALUES (1,'LAPTOP'),(2,'SYNCHRONOUS PC & GAMING PC'),(3,'AUDIOVISUAL & ENTERTAINMENT EQUIPMENT'),(4,'COMPUTER COMPONENTS'),(5,'STORAGE DEVICES'),(6,'NETWORK DEVICES'),(7,'SURVEILLANCE CAMERA'),(8,'ACCESSORIES'),(9,'OFFICE EQUIPMENT');
/*!40000 ALTER TABLE category ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS orders;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE orders (
  id bigint NOT NULL AUTO_INCREMENT,
  delivery_address varchar(100),
  note varchar(100),
  receiver varchar(20),
  order_date datetime,
  delivery_date datetime,
  received_date datetime,
  received_phone varchar(12),
  order_status varchar(15),
  total_cost int,
  orderer_id bigint,
  deliver_id bigint,
  PRIMARY KEY (id),
  CONSTRAINT FKgndcrlvetoudr3jaif9b7ay37 FOREIGN KEY (deliver_id) REFERENCES users (id),
  CONSTRAINT FKnwjiboxao1uvw1siemkvs1jb9 FOREIGN KEY (orderer_id) REFERENCES users (id)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
--
LOCK TABLES orders WRITE;
/*!40000 ALTER TABLE orders DISABLE KEYS */;
INSERT INTO orders VALUES (31,'bd','asdf','aaa','2018-12-01 14:38:26',NULL,NULL,'dsf','Waiting for approval', 16500000,NULL,NULL),(32,'fadf','asdf','aaa','2018-12-05 21:58:24',NULL,NULL,'13','created',17500000,2,NULL);
/*!40000 ALTER TABLE orders ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS cart;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE cart (
  id bigint NOT NULL AUTO_INCREMENT,
  total_cost int,
  user_id bigint,
  PRIMARY KEY (id),
  CONSTRAINT FKitverect56puwr47y7tyvy6er FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
--
LOCK TABLES cart WRITE;
/*!40000 ALTER TABLE cart DISABLE KEYS */;
INSERT INTO cart VALUES (1,NULL,2),(2,NULL,1);
/*!40000 ALTER TABLE cart ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS manufacturer;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE manufacturer (
  id bigint NOT NULL AUTO_INCREMENT,
  manufacturer_name varchar(9),
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
--
LOCK TABLES manufacturer WRITE;
/*!40000 ALTER TABLE manufacturer DISABLE KEYS */;
INSERT INTO manufacturer VALUES (2,'Apple'),(3,'Asus'),(4,'Acer'),(5,'Dell'),(6,'HP'),(7,'Lenovo'),(8,'MSI'),(9,'Masstel'),(10,'Haier');
/*!40000 ALTER TABLE manufacturer ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS contact;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE contact (
  id bigint NOT NULL AUTO_INCREMENT,
  email varchar(25),
  contact_date datetime,
  respond_date datetime,
  contact_message varchar(50),
  respond_message varchar(50),
  status_ varchar(10),
  respondent_id bigint,
  PRIMARY KEY (id),
  CONSTRAINT FK6jm47uh7f94pc3wix0duvedde FOREIGN KEY (respondent_id) REFERENCES users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
--
LOCK TABLES contact WRITE;
/*!40000 ALTER TABLE contact DISABLE KEYS */;
/*!40000 ALTER TABLE contact ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS users;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE users (
  id bigint NOT NULL AUTO_INCREMENT,
  address varchar(150),
  email varchar(25) UNIQUE,
  full_name varchar(30),
  password varchar(65),
  phone_number varchar(12),
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
--
LOCK TABLES users WRITE;
/*!40000 ALTER TABLE users DISABLE KEYS */;
INSERT INTO users VALUES (1,NULL,'admin@gmail.com','Nguyễn Xuân Nam','$2a$10$/VFMNUPBKNVRMjxPFCYKZ.lKahoLQda0EaAxdqoun1w3DqwNLa2me','123456789'),(2,NULL,'member@gmail.com',NULL,'$2a$10$j7Upgupou72GBmukz0G6pOATk3wlCAgaoFCEqAhSvLToD/V/1wlpu',NULL),(3,NULL,'shipper@gmail.com',NULL,'$2a$10$u2B29HDxuWVYY3fUJ8R2qunNzXngfxij5GpvlFAEtIz3JpK/WFXF2',NULL),(4,'Ha Noi','jvgiveup@gmail.com','Pham Tuan','$2a$10$ZCqCO9gSWt8A8HNXAWq8luqfNbJm0uG3PsUlzry0aRLwO3VHQZTmy','123456'),(5,NULL,'nhat@gmail.com','nguyen minh nhat','nhat','123456789');
/*!40000 ALTER TABLE users ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS user_role;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE user_role (
  user_id bigint NOT NULL,
  role_id bigint NOT NULL,
  PRIMARY KEY (user_id,role_id),
  CONSTRAINT FKig6jxd861mqv02a8pn68r43fr FOREIGN KEY (role_id) REFERENCES roles (id),
  CONSTRAINT FKocavcnspu1wcvp2w0s4usfgbf FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
--
LOCK TABLES user_role WRITE;
/*!40000 ALTER TABLE user_role DISABLE KEYS */;
INSERT INTO user_role VALUES (1,1),(1,2),(2,2),(4,2),(3,3);
/*!40000 ALTER TABLE user_role ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS product;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE product (
  id bigint NOT NULL AUTO_INCREMENT,
  cpu varchar(30),
  price int NOT NULL,
  sales_unit smallint NOT NULL,
  warehouse_unit smallint NOT NULL,
  battery_capacity_mAh smallint,
  operating_system varchar(30),
  screen varchar(50),
  ram varchar(30),
  product_name varchar(100),
  design varchar(255),
  warranty_infor varchar(30),
  general_infor varchar(255),
  category_id bigint,
  manufacturer_id bigint,
  PRIMARY KEY (id),
  CONSTRAINT FKchvjvgjnq8lbt9mjtyfn5pksq FOREIGN KEY (manufacturer_id) REFERENCES manufacturer (id),
  CONSTRAINT FKqss6n6gtx6lhb7flcka9un18t FOREIGN KEY (category_id) REFERENCES category (id)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
--
LOCK TABLES product WRITE;
/*!40000 ALTER TABLE product DISABLE KEYS */;
INSERT INTO product VALUES   (3,'Intel, Core i5, 1.8 Ghz',2399,0,100,5800,' Mac Os',' 13.3 inch LED-backlit','8 GB, LPDDR3, 1600 Mhz','Macbook Air 13 128GB MQD32SA/A (2017)','thiết kế không thay đổi, vỏ nhôm sang trọng, siêu mỏng và siêu nhẹ','12 tháng','hiệu năng được nâng cấp, thời lượng pin cực lâu, phù hợp cho nhu cầu làm việc văn phòng nhẹ nhàng, không cần quá chú trọng vào hiển thị của màn hình',1,2),
							  (4,' Intel, Core i5, 1.8 Ghz',2899,0,100,6000,'Mac Os','13.3 inch LED-backlit',' 8 GB, LPDDR3, 1600 Mhz','Macbook Air 13 256GB MQD42SA/A (2017)','thiết kế không thay đổi, vỏ nhôm sang trọng, siêu mỏng và siêu nhẹ','12 tháng','hiệu năng được nâng cấp, thời lượng pin cực lâu, phù hợp cho nhu cầu làm việc văn phòng nhẹ nhàng, không cần quá chú trọng vào hiển thị của màn hình',1,2),
                              (5,'Intel, Core M3, 1.2 GHz',3399,0,150,6000,'Mac Os',' 12 inch LED-backlit','8 GB, LPDDR3, 1866 MHz','Macbook 12 256GB (2017)','thiết kế không có thay đổi so với phiên bản 2016 nhưng Apple đã nâng cấp thêm bộ nhớ và giới thiệu bộ vi xử lý Intel thế hệ thứ 7','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,2),
                              (6,' Intel, Core i5, 2.3 GHz',3399,0,200,6000,'Mac Os',' 13.3 inch LED-backlit',' 8 GB, LPDDR3, 2133MHz','Macbook Pro 13 inch 128GB (2017)','thiết kế không thay đổi, vỏ nhôm sang trọng, siêu mỏng và siêu nhẹ','12 tháng','hiệu năng được nâng cấp, thời lượng pin cực lâu, phù hợp cho nhu cầu làm việc văn phòng nhẹ nhàng, không cần quá chú trọng vào hiển thị của màn hình',1,2),
                              (7,'Intel, Core i5, 2.3GHz',4499,0,100,7000,'Mac Os','13.3 inch, Retina (2560 x 1600 pixels)','8 GB, LPDDR3, 2133 MHz','Macbook Pro 13 Touch Bar 256 GB (2018)','thiết kế không thay đổi, vỏ nhôm sang trọng, siêu mỏng và siêu nhẹ','12 tháng','hiệu năng được nâng cấp, thời lượng pin cực lâu, phù hợp cho nhu cầu làm việc văn phòng nhẹ nhàng, không cần quá chú trọng vào hiển thị của màn hình',1,2),
                              (8,'Intel, Core i5, 2.3GHz',4999,0,150,7000,'Mac Os','13.3 inch, Retina (2560 x 1600 pixels)','8 GB, LPDDR3, 2133 MHz','Macbook Pro 13 Touch Bar 512 GB (2018)','thiết kế không thay đổi, vỏ nhôm sang trọng, siêu mỏng và siêu nhẹ','12 tháng','hiệu năng được nâng cấp, thời lượng pin cực lâu, phù hợp cho nhu cầu làm việc văn phòng nhẹ nhàng, không cần quá chú trọng vào hiển thị của màn hình',1,2),
                              (9,'Intel, Core i7, 2.2GHz',5999,0,200,7000,'Mac Os','15.4 inch, Retina (2880 x 1800 pix','16GB, LPDDR4, 2400MHz','Macbook Pro 15 Touch Bar 256 GB (2018)','thiết kế không thay đổi, vỏ nhôm sang trọng, siêu mỏng và siêu nhẹ','12 tháng','hiệu năng được nâng cấp, thời lượng pin cực lâu, phù hợp cho nhu cầu làm việc văn phòng nhẹ nhàng, không cần quá chú trọng vào hiển thị của màn hình',1,2),
                              (10,'Intel, Core i7, 2.2GHz',6999,0,120,7000,'Mac Os','15.4 inch, Retina (2880 x 1800 pi','16GB, LPDDR4, 2400MHz','Macbook Pro 15 Touch Bar 512 GB (2018)','thiết kế không thay đổi, vỏ nhôm sang trọng, siêu mỏng và siêu nhẹ','12 tháng','hiệu năng được nâng cấp, thời lượng pin cực lâu, phù hợp cho nhu cầu làm việc văn phòng nhẹ nhàng, không cần quá chú trọng vào hiển thị của màn hình',1,2),
                              (11,' Intel Celeron N3350',549,0,100,7000,'Windows 10','14 inch HD LED Glare',' 4 GB DDR3','Asus E402NA-GA034T','thiết kế với lớp vỏ bằng nhựa giúp cho máy trở nên nhẹ nhàng hơn','12 tháng','Bàn phím chiclet cùng Touchpad thông minh',1,3),
                              (12,'Intel Celeron N3060',549,0,200,7000,'Windows 10','14.0 inch HD Ultra Slim 200nits','2 GB DDR3L 1600 MHz','Asus Vivobook E406SA-BV001T','thiết kế với lớp vỏ bằng nhựa giúp cho máy trở nên nhẹ nhàng hơn','12 tháng','Bàn phím chiclet cùng Touchpad thông minh',1,3),
                              (13,'Intel Celeron N3060',549,0,200,7000,'Windows 10','14.0 inch HD Ultra Slim 200nits','2 GB DDR3 1600 MHz','Asus Vivobook E406SA-BV043T','thiết kế với lớp vỏ bằng nhựa giúp cho máy trở nên nhẹ nhàng hơn','12 tháng','gọn,nhẹ, độ bền cao',1,3),
                              (14,'Intel Celeron N4000',669,0,120,7000,' Windows 10','15.6 inch Anti-Glare LED Backlit',' 4 GB DDR4 2400 MHz','Asus Vivobook X507MA-BR208T/Celeron N4000','thiết kế với lớp vỏ bằng nhựa giúp cho máy trở nên nhẹ nhàng hơn','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,3),
                              (15,'Intel Pentium N4200',729,0,110,6000,'Windows 10','15.6 inch HD LED',' 4 GB, DDR3L, 1600 MHz','Asus X541NA-GO012T/Pentium N4200','thiết kế với lớp vỏ bằng nhựa giúp cho máy trở nên nhẹ nhàng hơn','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,3),
                              (16,'Intel, Core i3-6100U',899,0,130,7000,'Windows 10 Home','15.6 inch LED Backlight','4 GB DDR4 2133 MHz','Asus X541UA-XX272T/Core i3 6100U','thiết kế với lớp vỏ bằng nhựa giúp cho máy trở nên nhẹ nhàng hơn','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,3),
                              (17,' Intel, Core i3, 2.0 GHz',899,0,200,7000,' Free DOS','15.6 inchHD LED backlight','4 GB, DDR4, 2400MHz','Asus X541UA-GO1345','thiết kế với lớp vỏ bằng nhựa giúp cho máy trở nên nhẹ nhàng hơn','12 tháng','tinh tế đến từng chi tiết',1,3),
                              (18,' Intel, Core i3, 2.0 GHz',1049,0,300,7000,'Window 10',' 15.6 inchLED Backlight',' 4 GB, DDR4, 2400MHz','Asus X541UJ-DM544T/Core i3-6006U','thiết kế không thay đổi, vỏ nhôm sang trọng, siêu mỏng và siêu nhẹ','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,3),
                              (19,' Intel, Celeron, 1.10 GHz',599,0,300,7000,' Linux','5.6 inchAcer CineCrystal LED',' 4 GB DDR3L 1333 MHz','Acer AS A315-31-C8GB','thiết kế không thay đổi, vỏ nhôm sang trọng, siêu mỏng và siêu nhẹ','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,4),
                              (20,' Intel Celeron N4000',609,0,100,8000,'Linux',' 15.6 inchs HD LED Backlight','4 GB DDR4 2133 MHz','Acer A315-32-C9A4 ','thiết kế không thay đổi, vỏ nhôm sang trọng, siêu mỏng và siêu nhẹ','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,4),
                              (21,' Intel Core i3-7020U',899,0,100,8000,' Linux','14.0 inchs HD LED','4 GB DDR4 2400 MHz','Acer E5-476-33BQ','thiết kế không thay đổi, vỏ nhôm sang trọng, siêu mỏng và siêu nhẹ','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,4),
                              (22,' Intel Core i3-6006U',899,0,50,5000,'Linux','15.6 inch HD',' 4 GB DDR4 2133MHz','Acer A315-51-3932/Core i3-6006U','thiết kế không thay đổi, vỏ nhôm sang trọng, siêu mỏng và siêu nhẹ','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,4),
                              (23,'Intel Core i3-7020U',939,0,300,6000,'Linux','15.6 inchs HD LED Backlight','4 GB DDR4 2133 MHz','Acer AS A315-51-325E','thiết kế không thay đổi, vỏ nhôm sang trọng, siêu mỏng và siêu nhẹ','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,4),
                              (24,' Intel Core i3',999,0,130,5000,'Linux','15.6 inch  HD',' 4 GB DDR4 2133 MHz','Acer A315-51-364W','thiết kế không thay đổi, vỏ nhôm sang trọng, siêu mỏng và siêu nhẹ','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,4),
                              (25,' Intel, Core i3, 2.4 Ghz',1069,0,120,5000,'Free DOS',' 15.6 inchFull HD',' 4 GB, DDR4, 2133 MHz','Acer Aspire E5-575G-39QW','thiết kế không thay đổi, vỏ nhôm sang trọng, siêu mỏng và siêu nhẹ','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,4),
                              (26,' Intel Core i5-8250U',1699,0,130,6000,' Windows 10 Home','14.0 inchs FHD IPS LCD',' 4 GB DDR4 2133 MHz','Acer Swift SF314-52-55UF','thiết kế không thay đổi, vỏ nhôm sang trọng, siêu mỏng và siêu nhẹ','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,4),
                              (27,' Intel Pentium N3710',699,0,110,5000,' Ubuntu','15.6 inchs HD Truelife',' 4 GB DDR3 1600 MHz','Dell Inspiron N3552','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,5),
                              (28,' Intel Pentium N3710',999,0,100,5000,'Ubuntu','15.6 inchs HD Truelife',' 4 GB DDR3 1600 MHz','Dell Inspiron N3567C','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,5),
                              (29,'Intel, Core i3, 2.0 GHz',999,0,100,6000,'Ubuntu 16.04','14 inchHD Anti-Glare LED','4 GB, DDR4, 2400MHz','Dell Inspiron N3467','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,5),
                              (30,' Intel Core i3-6006U',1089,0,120,7000,'Linux','15.6 inchs',' 4 GB DDR4 2400 MHz','Dell Vostro 3568','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,5),
                              (31,' Intel, Core i3, 2.0 GHz',1119,0,90,6000,' Free DOS',' 15.6 inchHD LED',' 4 GB, DDR3L, 1600 Mhz','Dell Ins N3567','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,5),
                              (32,' Intel Core i3-7020U',1159,0,80,7000,' Ubuntu','15.6 inch HD Anti Glare LED','4 GB DDR4 2400 MHz','Dell Inspiron N3576','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,5),
                              (33,'Intel Core i3-7020U',1189,0,110,8000,' Windows 10 Home','15.6 inches Anti-Glare LED',' 4 GB DDR4 2400 MHz','Dell Vostro 3568','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,5),
                              (34,' Intel, Core i3, 2.40 GHz',1249,0,100,8000,'Free DOS','14 inchHD Anti-Glare LED','4 GB, DDR4, 2133 MHz','Dell Vostro V5468/i3','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,5),
                              (35,' Intel Celeron',599,0,100,5000,'Linux',' 15.6 inchs',' 4 GB DDR3L 1600 MHz','HP 15-bs644TU/Celeron-N3060','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,6),
                              (36,' Intel Celeron N4000',619,0,70,7000,' Windows 10 Home',' 15.6 inchs HD HD SVA ',' 4 GB DDR4 2400 MHz','HP 15-da0107TU/Celeron','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,6),
                              (37,' Intel Penitum N3710',699,0,120,8000,'Windows 10','15.6 inch',' 4GB DDR4','HP 15-bs648TU','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,6),
                              (38,' Intel, Core i3, 2.0 GHz',899,0,80,8000,' Free Dos','15.6 inch LED-backlit',' 4GB, DDR4, 2133 MHz','HP 15-bs555TU','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,6),
                              (39,' Intel, Core i3, 2.0 GHz',1049,0,90,7000,' Free DOS',' 15.6 inchHD SVA BrightView',' 4 GB, DDR4, 2133 MHz','HP 15-bs637TX','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,6),
                              (40,' Intel Core i3-7100U',1149,0,90,6000,' Free DOS',' 15.6 inch',' 4GB DDR4','HP Pavilion 15-cc043TU','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,6),
                              (41,' Intel, Core i5, 2.50 GHz',1219,0,100,8000,'Free DOS','15.6 inchHD SVA BrightView',' 4 GB, DDR4, 2133 MHz','HP 15-bs559TU','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,6),
                              (42,' Intel Core i3-7100U',1299,0,150,7000,'Windows 10 Home Single',' 11.6 inch HD',' 4 GB DDR4 2133 MHz','HP Pavilion X360 11','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,6),
                              (43,'Intel, Celeron, 1.10 GHz',499,0,100,6000,' Windows 10 EM',' 11.6 inchHD LED',' 2 GB, LPDDR4, 2400MHz','Lenovo Ideapad 120S-11IAP','Thiết kế gọn gàng để di chuyển','6 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,7),
                              (44,' Intel, Pentium, 1.60 GHz',549,0,120,5000,' Free DOS','14 inchHD LED backlight',' 4 GB, DDR3L, 1600 MHz','Lenovo IdeaPad 110','Thiết kế gọn gàng để di chuyển','6 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,7),
                              (45,' Intel, Pentium, 1.10 GHz',749,0,80,7000,' Windows 10',' 11.6 inchHD TN AG TOUCH',' 4 GB, DDR3L, 1600 MHz','Lenovo Yoga 310-11IAP','Thiết kế gọn gàng để di chuyển','6 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,7),
                              (46,'Intel Core i3-6006U',899,0,120,7000,'FreeDOS',' 14.0 inchs HD LED',' 4 GB DDR4 2133 MHz','Lenovo Ideapad 320-14ISK ','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,7),
                              (47,'Intel Core i5-8250U',1099,0,130,5000,' Windows 10 Home',' 14.0 inchs HD LED','4 GB DDR4 2133 MHz','Lenovo Ideapad 330-14IKBR','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,7),
                              (48,' Intel, Core i5, 2.50 GHz',1129,0,120,5000,'Linux',' 15.6 inchFull HD',' 4 GB, DDR4, 2133 MHz','Lenovo IdeaPad 310-15IKB','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,7),
                              (49,' Intel Core i5-8250U',1349,0,130,6000,'Linux',' 14.0 inchs FHD IPS','4 GB DDR4 2400 MHz','Lenovo Ideapad 330S-14IKBR','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,7),
                              (50,' Intel, Core i5, 2.50 GHz',1599,0,200,7000,'Windows 10 Single',' 15.6 inchAnti-Glare LED',' 4 GB, DDR4, 2133 MHz','Lenovo ThinkPad E570','Thiết kế gọn gàng để di chuyển','12 tháng','Thiết kế hoàn mỹ tinh tế và sang trọng',1,7),
                              (51,' Intel, Core i5, 2.50 GHz',1899,0,300,7000,' Free DOS',' 15.6 inchWide-View','4 GB, DDR4, 2133 MHz','MSI GL62M 7RDX – 1036XVN','Sỡ hữu phong cách thiết kế truyền thống của MSI','12 tháng','Lớp vỏ đen nhám được phủ cao su mềm đặc trưng đem đến trải nghiệm cầm nắm tốt hơn. Các thành phần của nắp máy bao gồm vỏ, màn hình và viền màn hình đều được ép chặt để tối ưu trọng lượng chỉ còn 2.2 kg',1,8),
                              (52,' Intel Core i5-8250U',2099,0,120,8000,'Windows 10 Home','14.0 inchs FHD IPS 72%',' 8 GB DDR4 2666 MHz','MSI PS42 8M-296VN/i5-8250U','Sỡ hữu phong cách thiết kế truyền thống của MSI','12 tháng','Lớp vỏ đen nhám được phủ cao su mềm đặc trưng đem đến trải nghiệm cầm nắm tốt hơn. Các thành phần của nắp máy bao gồm vỏ, màn hình và viền màn hình đều được ép chặt để tối ưu trọng lượng chỉ còn 2.2 kg',1,8),
                              (53,' Intel Core i5-8300H',2149,0,130,7000,' Windows 10 Home','15.6 inchs Wideview 94%NTS','8 GB DDR4 2400MHz','MSI GV62 8RC-063VN/i5-8300H','Sỡ hữu phong cách thiết kế truyền thống của MSI','12 tháng','Lớp vỏ đen nhám được phủ cao su mềm đặc trưng đem đến trải nghiệm cầm nắm tốt hơn. Các thành phần của nắp máy bao gồm vỏ, màn hình và viền màn hình đều được ép chặt để tối ưu trọng lượng chỉ còn 2.2 kg',1,8),
                              (54,' Intel Core i5-8300H',2249,0,140,7000,'Windows 10','15.6 inchs FullHD IPS 45% ',' 8 GB DDR4 2666 MHz','MSI GF63 8RC-203VN/I5-8300H','Sỡ hữu phong cách thiết kế truyền thống của MSI','12 tháng','Lớp vỏ đen nhám được phủ cao su mềm đặc trưng đem đến trải nghiệm cầm nắm tốt hơn. Các thành phần của nắp máy bao gồm vỏ, màn hình và viền màn hình đều được ép chặt để tối ưu trọng lượng chỉ còn 2.2 kg',1,8),
                              (55,' Intel, Core i7, 2.8 GHz',2399,0,150,8000,'  DOS','  17.3 inch',' 8 GB, DDR4, 2400 MHz','MSI GL72M 7REX - 1427XVN','Sỡ hữu phong cách thiết kế truyền thống của MSI','12 tháng','Lớp vỏ đen nhám được phủ cao su mềm đặc trưng đem đến trải nghiệm cầm nắm tốt hơn. Các thành phần của nắp máy bao gồm vỏ, màn hình và viền màn hình đều được ép chặt để tối ưu trọng lượng chỉ còn 2.2 kg',1,8),
                              (56,' Intel, Core i7, 2.80 GHz',2499,0,110,7000,' Free DOS',' 15.6 inchWide-View',' 8 GB, DDR4, 2400MHz','MSI GF62 7RE-1818XVN','Sỡ hữu phong cách thiết kế truyền thống của MSI','12 tháng','Lớp vỏ đen nhám được phủ cao su mềm đặc trưng đem đến trải nghiệm cầm nắm tốt hơn. Các thành phần của nắp máy bao gồm vỏ, màn hình và viền màn hình đều được ép chặt để tối ưu trọng lượng chỉ còn 2.2 kg',1,8),
                              (57,' Intel Core i7-8750H',2599,0,110,7000,'Windows 10 Home','15.6 inchs FullHD IPS 45%',' 8 GB DDR4 2666 MHz','MSI GF63 8RD-218VN/i7-8750H','Sỡ hữu phong cách thiết kế truyền thống của MSI','12 tháng','Lớp vỏ đen nhám được phủ cao su mềm đặc trưng đem đến trải nghiệm cầm nắm tốt hơn. Các thành phần của nắp máy bao gồm vỏ, màn hình và viền màn hình đều được ép chặt để tối ưu trọng lượng chỉ còn 2.2 kg',1,8),
                              (58,' Intel, Core i5, 2.50 GH',1999,0,100,8000,'Free DOS','15.6 inchWide-View',' 8 GB, DDR4, 2133 MHz','MSI GL62M 7RDX-1817XVN','Thiết kế gọn gàng để di chuyển','12 tháng','Lớp vỏ đen nhám được phủ cao su mềm đặc trưng đem đến trải nghiệm cầm nắm tốt hơn. Các thành phần của nắp máy bao gồm vỏ, màn hình và viền màn hình đều được ép chặt để tối ưu trọng lượng chỉ còn 2.2 kg',1,8),
                              (59,'Intel Celeron N3350 1.10 GHz',499,0,100,5000,'Windows 10','13.3 inch FullHD IPS','3 GB LPDDR3 1600 MHz','Masstel L133','Thiết kế gọn gàng để di chuyển','6 tháng','thiết kế để giúp bạn làm việc hiệu quả suốt cả ngày dài, kể cả khi bạn đang di chuyển. Máy tính xách tay 13.3 inchs nhỏ gọn và có mức giá hợp lý, trọng lượng chỉ 1.2 Kg và thời lượng pin bền bỉ, phù hợp với những bạn trẻ năng động',1,9),
                              (60,'Intel Celeron N3350 1.10 GHz',549,0,100,5000,'Windows 10','13.3 inch FullHD IPS','3 GB LPDDR3 1600 MHz',' Masstel L133/Celeron N3350','Thiết kế gọn gàng để di chuyển','6 tháng','thiết kế để giúp bạn làm việc hiệu quả suốt cả ngày dài, kể cả khi bạn đang di chuyển. Máy tính xách tay 13.3 inchs nhỏ gọn và có mức giá hợp lý, trọng lượng chỉ 1.2 Kg và thời lượng pin bền bỉ, phù hợp với những bạn trẻ năng động',1,9),
                              (61,' Intel Celeron N3350 1.10 GHz',599,0,100,4000,'Windows 10',' 13.3 inch FullHD IPS',' 3 GB LPDDR3 1600 MHz','Masstel L133 Celeron N3350','Thiết kế gọn gàng để di chuyển','6 tháng','thiết kế để giúp bạn làm việc hiệu quả suốt cả ngày dài, kể cả khi bạn đang di chuyển. Máy tính xách tay 13.3 inchs nhỏ gọn và có mức giá hợp lý, trọng lượng chỉ 1.2 Kg và thời lượng pin bền bỉ, phù hợp với những bạn trẻ năng động',1,9),
                              (62,'Intel Celeron N4100',699,0,50,4000,'Windows 10','13.3 inchs FullHD IPS','4 GB LPDDR4','Masstel L133 Pro/Celeron N4100','Thiết kế siêu mỏng nhẹ','6 tháng','thiết kế để giúp bạn làm việc hiệu quả suốt cả ngày dài, kể cả khi bạn đang di chuyển. Máy tính xách tay 13.3 inchs nhỏ gọn và có mức giá hợp lý, trọng lượng chỉ 1.2 Kg và thời lượng pin bền bỉ, phù hợp với những bạn trẻ năng động',1,9),
                              (63,' Intel Celeron N3350',449,0,50,5000,'FreeDOS','13.3 inchs IPS LCD',' 3 GB DDR3L 1600 MHz','Haier S1 HR-13M/Celeron N3350','Thiết kế siêu mỏng nhẹ','6 tháng','Một chiếc laptop đời mới thời trang, siêu mỏng nhẹ phục vụ tối ưu cho công việc nhưng lại trong tầm giá rất rẻ',1,10),
                              (64,' Intel Pentium N4200',749,0,60,4000,'FreeDOS','13.3 inchs IPS LCD',' 6 GB DDR3L 1600 MHz','Haier S1 HR-13MZ/Pentium N4200','Thiết kế siêu mỏng nhẹ','6 tháng','Một chiếc laptop đời mới thời trang, siêu mỏng nhẹ phục vụ tối ưu cho công việc nhưng lại trong tầm giá rất rẻ',1,10),
                              (65,NULL,1030,0,100,NULL,NULL,NULL,NULL,'PC Acer Aspire TC-780 DT.B89SV.012 i5 7400/4G/1TB/DVDRW/WL/K+M/Dos',NULL,'12 tháng','-	Bộ vi xử lý Core i5-7400 (3.00GHz,6MB) \r\n-	RAM 4 GB DDR4 2133MHz UDIMM \r\n-	Ổ cứng 1TB 3.5 inch 7200rpm \r\n-	VGA Intel HD Graphics 530',2,4),
                              (66,NULL,980,0,80,NULL,NULL,NULL,NULL,'PC Acer Aspire XC-885 DT.BAQSV.001 i3 8100/4G/1TB/DVDRW/WL/K+M/Dos',NULL,'6 tháng','-	Bộ vi xử lý : Intel Core i5-7400 (3.00 GHz/6MB) \r\n-	RAM : 4 GB DDR4 2400 MHz \r\n-	Ổ cứng : 1TB SATA 3.5\" 7200RPM \r\n-	VGA : Nvidia GT720 2GB Graphics',2,4),
                              (67,NULL,90,0,90,NULL,NULL,NULL,NULL,'PC Acer Aspire TC-780 DT.B89SV.009 G4560/4G/1TB/DVDRW/WL/K+M/Dos',NULL,'6 tháng','-	Bộ vi xử lý Pentium G4560(3.50 GHz,3MB) \r\n-	RAM 4 GB DDR4 2133MHz UDIMM \r\n-	Ổ cứng 1TB 3.5 inch 7200rpm \r\n-	VGA Intel HD Graphics',2,4),
                              (68,NULL,190,0,100,NULL,NULL,NULL,NULL,'PC Acer Aspire TC-780 DT.B89SV.008 i3 7100/4G/1TB/DVDRW/WL/K+M/Dos',NULL,'6 tháng','-	Bộ vi xử lý Core i3-7100 (3.90 GHz,3MB) \r\n-	RAM 4 GB DDR4 2133MHz UDIMM \r\n-	Ổ cứng 1TB 3.5 inch 7200rpm \r\n-	VGA Intel HD Graphics',2,4),
                              (69,NULL,1006,0,90,NULL,NULL,NULL,NULL,'PC Acer Aspire M230 UX.B1JSI.H72 i3 7100/4G/1TB/DVDRW/WL/K+M/Dos',NULL,' 6 tháng','CPU i3-7100 \r\nRAM 4GB \r\nHDD 1TB',2,4),
                              (70,NULL,1900,0,90,NULL,NULL,NULL,NULL,'PC Acer Aspire XC-885 DT.BAQSV.002 i5 8400/4G/1TB/DVDRW/WL/K+M/Dos',NULL,'6 tháng','CPU i5-8400(6*2.80) \r\nRAM 4G \r\nHDD 1TB \r\nDOS',2,4),
                              (71,NULL,1000,0,300,NULL,NULL,NULL,NULL,'PC Dell Optilex 3060SFF-8400-1TBKHDD i5 8400/4GB/1TB/DVDRW/K+M/DOS',NULL,'6 tháng','PC Dell Optilex 3060SFF-8400-1TBKHDD i5 8400/4GB/1TB/DVDRW/K+M/DOS\r\n',2,5),
                              (72,NULL,1500,0,200,NULL,NULL,NULL,NULL,'PC Dell Vostro 3670 J84NJ2 i7 8700/8GB/1TB/DVDRW/K+M/WL/DOS',NULL,'6 tháng','Intel Core i5-8400 Processor (9MB, 6T, 2.8GHz, up to 4.0, 65W) \r\nHDD 1TB 7200 rpm SATA \r\n4GB 2666 MHz DDR4',2,5),
                              (73,NULL,1300,0,100,NULL,NULL,NULL,NULL,'PC Dell Vostro 3670 J84NJ21 i7 8700/8GB/1TB/GTX1050 2G/DVDRW/K+M/WL/DOS',NULL,'6 tháng','Sức mạnh xử lý: Đảm đương khối lượng công việc của bạn một cách nhanh chóng và hiệu quả với bộ vi xử lý i7-8700 thế hệ thứ 8, bộ nhớ lên đến 8GB DDR4, và card đồ họa GTX 1050. \r\nMở rộng dễ dàng: 1TB dung lượng lưu trữ HDD ',2,5),
                              (74,NULL,1200,0,50,NULL,NULL,NULL,NULL,'PC Dell Vostro 3670 MTI79016 i7 8700/8G/1TB/GTX1050 2G/DVDRW/K+M/WL/DOS',NULL,'6 tháng','Sức mạnh xử lý: Đảm đương khối lượng công việc của bạn một cách nhanh chóng và hiệu quả với bộ vi xử lý i7-8700 thế hệ thứ 8, bộ nhớ lên đến 8GB DDR4, và card đồ họa GTX 1050. \r\nMở rộng dễ dàng: 1TB dung lượng lưu trữ HDD',2,5),
                              (75,NULL,1200,0,70,NULL,NULL,NULL,NULL,'PC Dell Optilex 3050SFF-7500-1TBKHDD i5 7500/4GB/1TB/DVDRW/K+M/DOS',NULL,'6 tháng','Intel Core i5-7500 Processor (6MB, 4T, 3.4GHz, 65W) \r\nHDD 1TB 7200 rpm SATA \r\n4GB 2400MHz DDR4',2,5),
                              (76,NULL,1250,0,70,NULL,NULL,NULL,NULL,'PC Dell All in One 3277B Pentium 4415U/4GB/1TB/21.5\" FHD/K+M/WL/Win10',NULL,'6 tháng','Intel Pentium 4415U (2.30GHz, 3MB), \r\n4GB DDR4 2400MHz, \r\nHDD 1TB, Intel HD Graphics 620 , \r\n21.5 inch Full HD IPS LED Backlit \r\nWin 10',2,5),
                              (77,NULL,1340,0,100,NULL,NULL,NULL,NULL,'PC HP Pavilion 590-p0055d 4LY13AA i5 8400/4GB/1TB/DVDRW/K+M/Win10',NULL,'6 tháng','Bộ Vi xử lý: Core i5-8400(2.80 GHz,9MB) \r\nRAM: 4GB DDR4 \r\nỔ cứng: 1TB HDD \r\nHệ điều hành: Win 10 Home 64',2,6),
                              (78,NULL,1200,0,100,NULL,NULL,NULL,NULL,'PC HP 280 G4 MT i7 8700/8GB/1TB/DVDRW/K+M/DOS ( 4LU27PA )',NULL,'6 tháng','Máy tính để bàn HP 280 G4 Microtower - 4LU27PA \r\nBộ vi xử lý Core i7-8700(3.20 GHz,12MB) \r\n8GB RAM DDR4, 1TB HDD, DVDRW \r\nĐồ họa Intel UHD Graphics, Keyboard, USB Mouse,FreeDos',2,6),
                              (79,NULL,1200,0,70,NULL,NULL,NULL,NULL,'PC HP ProDesk 400 G5 SFF i5 8500/4GB/500GB/DVDRW/K+M/DOS ( 4TT16PA )',NULL,'6 tháng','PC HP ProDesk 400 G5 SFF - 4TT16PA \r\nBộ vi xử lý Intel Core i5 8500 3.0Ghz \r\nRAM 4GB / 500GB / DVDRW',2,6),
                              (80,NULL,1450,0,80,NULL,NULL,NULL,NULL,'PC HP 280 G4 MT 4LU29PA i3 8100/4GB/1TB/DVDRW/K+M/DOS',NULL,'6 tháng','Máy tính để bàn HP 280 G4 Microtower - 4LU29PA \r\nBộ vi xử lý Intel Core i3-8100(3.60 GHz,6MB) \r\n4GB RAM DDR4, 1TB HDD, DVDRW \r\nIntel UHD Graphics, Keyboard, USB Mouse, FreeDos',2,6),
                              (81,NULL,1160,0,90,NULL,NULL,NULL,NULL,'PC HP 270-p010d i5 7400T/4GB/1TB/AMD R5 435 2GB/DVDRW/K+M/WL/Win10 (3JT58AA )',NULL,'6 tháng','Máy tính để bàn HP 280 G4 Microtower - 4LU29PA \r\nBộ vi xử lý Intel Core i3-8100(3.60 GHz,6MB) \r\n4GB RAM DDR4, 1TB HDD, DVDRW \r\nIntel UHD Graphics, Keyboard, USB Mouse, FreeDos',2,6),
                              (82,NULL,1350,0,80,NULL,NULL,NULL,NULL,'PC Lenovo ThinkCentre V520 10NKA00EVA i3 7100/4GB/1TB/DVDRW/K+M/Dos',NULL,'6 tháng','PC Lenovo V530S - 10TXA001VA \r\nBộ vi xử lý Intel Celeron G4900 3.1Ghz \r\nRAM 4GB/ HDD 500GB/ K+M/ WL/ DOS',2,7),
                              (83,NULL,1270,0,70,NULL,NULL,NULL,NULL,'PC Lenovo IdeaCentre 510s-08IKL 90GB00EVVN i3 7100/4GB/1TB/DVDRW/WL/K+M/Win10',NULL,'6 tháng','-	Bộ vi xử lý : Intel Core i3-7100 Processor (2 x 3.90GHz) \r\n-	RAM : 4GB DDR4/ 2400MHz \r\n-	Ổ cứng : 1TB SATA3 (7200rpm) \r\n-	VGA : Intel HD Graphics 630',2,7),
                              (84,NULL,1040,0,90,NULL,NULL,NULL,NULL,'PC Lenovo ThinkCentre V520 10NKA00DVA G4560 /4GB/500GB/DVDRW/K+M/Dos',NULL,'6 tháng','Bộ vi xử lý	Intel G4560 \r\nChipset	Intel B250 Chipset \r\nBộ nhớ trong	4GB DDR4 \r\nSố khe cắm	2 \r\nDung lượng tối đa	32GB \r\nVGA	Intel® HD 610 \r\nỔ cứng	500GB 7200RPM \r\nỔ quang	DVDRW',2,7),
                              (85,NULL,1500,0,80,NULL,NULL,NULL,NULL,'PC Lenovo ThinkCentre V520 10NKA00TVA i7 7700/4GB/1TB/DVDRW/K+M/Dos',NULL,'6 tháng','CPU i7-7700(3.6GHz/8MB) \r\nRAM 4GB DDR4 \r\nHDD 1TB 7200RPM, \r\nDVDRW, \r\nUSB Calliope Keyboard,USB Mouse',2,7),
                              (86,NULL,1050,0,90,NULL,NULL,NULL,NULL,'PC Lenovo IdeaCentre 510-15IKL 90G800HEVN Pentium G4560/4GB/1TB/DVDRW/WL/K+M/Win10',NULL,'6 tháng','-	Bộ vi xử lý: Intel Core i3-7100 ( 3.90 GHz / 3MB cache ) \r\n-	RAM: 4GB DDR4 2400UDIMM \r\n-	Ổ cứng: 1TB SATA 7200RPM \r\n-	VGA: Intel® HD Graphics 630',2,7),
                              (87,NULL,1050,0,90,NULL,NULL,NULL,NULL,'PC Lenovo V530S-07ICB i3 8100/4GB/1TB/K+M/WL/Win10 ( 10TXA000VN )',NULL,'6 tháng','PC Lenovo V530S-07ICB - 10TXA000VN \r\nBộ vi xử lý Intel Core i3 8100 3.6Ghz \r\nRAM 4GB/ HDD 1TB/ K+M/ WL/ Win10',2,7),
                              (88,NULL,12400,0,80,NULL,NULL,NULL,NULL,'PC Lenovo ThinkCentre V520 10NKA00TVA i7 7700/4GB/1TB/DVDRW/K+M/Dos',NULL,'6 tháng','CPU i7-7700(3.6GHz/8MB) \r\nRAM 4GB DDR4 \r\nHDD 1TB 7200RPM, \r\nDVDRW, \r\nUSB Calliope Keyboard,USB Mouse',2,7),
                              (89,NULL,280,0,100,NULL,NULL,NULL,NULL,'Tai nghe ASUS ROG Strix Fusion 300 Surround 7.1',NULL,'6 tháng','Tai nghe chơi game cao cấp từ ASUS ROG \r\nMàng loa dài 50mm \r\nGiả lập âm thanh vòm 7.1 \r\nThiết kế trùm tai cho độ thoải mái tối đa \r\nHệ thống Led màu đỏ nổi bật \r\nCó thể sử dụng 3,5mm và USB linh hoạt',3,3),
                              (90,NULL,320,0,120,NULL,NULL,NULL,NULL,'Tai nghe ASUS ROG Strix Fusion Wireless',NULL,'6 tháng','Tai nghe chơi game cao cấp từ ASUS ROG \r\nMàng loa dài 50mm \r\nGiả lập âm thanh vòm 7.1 \r\nThiết kế trùm tai cho độ thoải mái tối đa \r\nKết nối không dây No-Lag qua USB Receiver',3,3),
                              (91,NULL,1000,0,90,NULL,NULL,NULL,NULL,'Tai nghe ASUS ROG Strix Fusion 500',NULL,'6 tháng','Tai nghe chơi game cao cấp từ ASUS ROG \r\nMàng loa dài 50mm \r\nGiả lập âm thanh vòm 7.1 \r\nThiết kế trùm tai cho độ thoải mái tối đa \r\nHệ thống Led RGB 16,8 triệu màu \r\nCó thể sử dụng 3,5mm và USB linh hoạt \r\nĐiều chỉnh qua bề mặt cảm ứng của củ tai',3,3),
                              (92,NULL,420,0,100,NULL,NULL,NULL,NULL,'Tai nghe Asus STRIX 2.0- ROG Gaming Headset',NULL,'6 tháng','Tai nghe chơi game cao cấp từ ASUS ROG \r\nMàng loa dài 50mm \r\nGiả lập âm thanh vòm 7.1 \r\nThiết kế trùm tai cho độ thoải mái tối đa \r\nHệ thống Led RGB 16,8 triệu màu \r\nCó thể sử dụng 3,5mm và USB linh hoạt \r\nĐiều chỉnh qua bề mặt cảm ứng của củ tai',3,3),
                              (93,NULL,320,0,50,NULL,NULL,NULL,NULL,'Tai nghe Asus Centurion - ROG Gaming Headset',NULL,'6 tháng','Tai nghe chơi game cao cấp từ ASUS ROG \r\nMàng loa dài 50mm \r\nGiả lập âm thanh vòm 7.1 \r\nThiết kế trùm tai cho độ thoải mái tối đa \r\nKết nối không dây No-Lag qua USB Receiver',3,3),
                              (94,NULL,100,0,60,NULL,NULL,NULL,NULL,'Tai Nghe Asus CERBERUS V2 - Red / Black',NULL,'6 tháng','Tai nghe chơi game cao cấp từ ASUS ROG \r\nMàng loa dài 50mm \r\nGiả lập âm thanh vòm 7.1 \r\nThiết kế trùm tai cho độ thoải mái tối đa \r\nKết nối không dây No-Lag qua USB Receiver',3,3),
                              (95,NULL,250,0,70,NULL,NULL,NULL,NULL,'Tai nghe Asus Gaming Asus Strix Pro',NULL,'6 tháng','Tai nghe chơi game cao cấp từ ASUS ROG \r\nMàng loa dài 50mm \r\nGiả lập âm thanh vòm 7.1 \r\nThiết kế trùm tai cho độ thoải mái tối đa \r\nKết nối không dây No-Lag qua USB Receiver',3,3),
                              (96,NULL,300,0,100,NULL,NULL,NULL,NULL,'Tai nghe Asus gaming ROG Strix Wireless',NULL,'6 tháng','Tai nghe chơi game cao cấp từ ASUS ROG \r\nMàng loa dài 50mm \r\nGiả lập âm thanh vòm 7.1 \r\nThiết kế trùm tai cho độ thoải mái tối đa \r\nKết nối không dây No-Lag qua USB Receiver',3,3),
                              (97,NULL,4300,0,100,NULL,NULL,NULL,NULL,'Màn hình Acer Nitro 23.8\"VG240Y LED IPS',NULL,'6 tháng','Kích Thước Màn Hình	23.8 INCH \r\nĐộ Sáng Màn Hình	250 cd/m² \r\nTỉ Lệ Tương Phản Động MEGA	100,000,000:1 \r\nĐộ Phân Giải Màn Hình	1920*1080 \r\nThời Gian Đáp Ứng	1ms \r\nHỗ trợ màu	16.7 million colours \r\nGóc nhìn	178/178',4,4),
                              (98,NULL,5000,0,200,NULL,NULL,NULL,NULL,'Monitor Acer 21.5\"R221Q LED IPS',NULL,'6 tháng','Kích Thước Màn Hình	23.8 INCH \r\nĐộ Sáng Màn Hình	250 cd/m² \r\nTỉ Lệ Tương Phản Động MEGA	100,000,000:1 \r\nĐộ Phân Giải Màn Hình	1920*1080 \r\nThời Gian Đáp Ứng	1ms \r\nHỗ trợ màu	16.7 million colours \r\nGóc nhìn	178/178',4,4),
                              (99,NULL,520,0,100,NULL,NULL,NULL,NULL,'Màn hình Acer 19.5\"K202HQL LED',NULL,'6 tháng','Kích Thước Màn Hình	27\'\' \r\nĐộ Sáng Màn Hình	350 cd/m² \r\nĐộ Phân Giải Màn Hình	2560 x 1440 \r\nThời Gian Đáp Ứng	1ms \r\nHỗ trợ màu	16,7 Triệu Màu \r\nGóc nhìn	178°/178° \r\nTín hiệu đầu vào	HDMI, DP \r\nMức Tiêu Thụ̣ Điện	26W',4,4),
                              (100,NULL,200,0,100,NULL,NULL,NULL,NULL,'Màn hình Acer Nitro 27\"VG270 LED IPS',NULL,'6 tháng','Size	27” \r\nĐộ phân giải	Full HD (1920 x 1080) \r\nTỷ lệ khung hình	16:9 \r\nThời gian đáp ứng: 1 ms \r\nRefresh Rate	75 Hz \r\nHỗ trợ màu	16.7 million \r\nĐộ sáng	250 cd/m² \r\nBacklight	LED',4,4),
                              (101,NULL,120,0,12,NULL,NULL,NULL,NULL,'Màn hình Acer 18.5\"EB192Q LED',NULL,'6 tháng','Kích Thước Màn Hình	23.8 INCH \r\nĐộ Sáng Màn Hình	250 cd/m² \r\nTỉ Lệ Tương Phản Động MEGA	100,000,000:1 \r\nĐộ Phân Giải Màn Hình	1920*1080 \r\nThời Gian Đáp Ứng	1ms \r\nHỗ trợ màu	16.7 million colours \r\nGóc nhìn	178/178',4,4),
                              (102,NULL,1200,0,123,NULL,NULL,NULL,NULL,'Màn hình Acer 27\"KG271 LED IPS',NULL,'6 tháng','Kích Thước Màn Hình	23.8 INCH \r\nĐộ Sáng Màn Hình	250 cd/m² \r\nTỉ Lệ Tương Phản Động MEGA	100,000,000:1 \r\nĐộ Phân Giải Màn Hình	1920*1080 \r\nThời Gian Đáp Ứng	1ms \r\nHỗ trợ màu	16.7 million colours \r\nGóc nhìn	178/178',4,4),
                              (103,NULL,30,0,123,NULL,NULL,NULL,NULL,'Bàn Phím Asus ROG Claymore Ultimate RGB Aura Sync Cherry Red switch',NULL,'6 tháng','Bàn Phím Asus ROG Claymore Ultimate RGB Aura Sync Cherry Red switch',4,3),
                              (104,NULL,400,0,123,NULL,NULL,NULL,NULL,'Keyboard ASUS ROG Strix Flare Cherry Blue switch',NULL,'6 tháng','Keyboard ASUS ROG Strix Flare Cherry Blue switch',4,3),
                              (105,NULL,543,0,123,NULL,NULL,NULL,NULL,'Keyboard ASUS Strix Tactic Pro Mechanical Blue',NULL,'6 tháng','Keyboard ASUS Strix Tactic Pro Mechanical Blue',4,3),
                              (106,NULL,120,0,123,NULL,NULL,NULL,NULL,'Mouse ASUS ROG Strix Impact',NULL,'2 tháng','Mouse ASUS ROG Strix Impact',4,3),
                              (107,NULL,10,0,123,NULL,NULL,NULL,NULL,'Mouse ASUS ROG Gladius II',NULL,'2 tháng','Chuột chơi game cho game thủ chuyên nghiệp của Asus ROG \r\nHệ thống led RGB tích hợp công nghệ Aura Sync với Mainboard và VGA \r\nThiết kế Ergonomics phù hợp với kiểu cầm chuột Palm Grip và Claw Grip \r\n',4,3),
                              (108,NULL,300,0,123,NULL,NULL,NULL,NULL,'Mouse Asus ROG Spatha - Super MMO Gaming Mouse',NULL,'2 tháng','Mouse Asus ROG Spatha - Super MMO Gaming Mouse\r\n',4,3),
                              (109,NULL,20,0,1000,NULL,NULL,NULL,NULL,'MicroSDHC SILICON POWER UHS-I 32GB W/A',NULL,'2 tháng','MicroSDHC SILICON POWER UHS-I 32GB W/A',5,2),
                              (110,NULL,30000,0,100,NULL,NULL,NULL,NULL,'HDD Western Caviar Black 500GB 7200Rpm, SATA3 6Gb/s, 64MB Cache',NULL,'2 tháng','HDD Western Caviar Black 500GB 7200Rpm, SATA3 6Gb/s, 64MB Cache\r\n',5,2),
                              (111,NULL,123,0,100,NULL,NULL,NULL,NULL,'SSD Kingston Furry RGB 240GB Sata3 2.5\" (Doc 550MB/s, Ghi 480MB/s) - SHFR200/240G',NULL,'2 tháng','SSD Kingston Furry RGB 240GB Sata3 2.5\" (Doc 550MB/s, Ghi 480MB/s) - SHFR200/240G',5,3),
                              (112,NULL,123,0,1000,NULL,NULL,NULL,NULL,'SSD AVEXIR S100 White 120GB SATA3 6Gb/s 2.5\" (Read 550MB/s, Write 275MB/s)',NULL,'2 tháng','SSD AVEXIR S100 White 120GB SATA3 6Gb/s 2.5\" (Read 550MB/s, Write 275MB/s)',5,2),
                              (113,NULL,120,0,100,NULL,NULL,NULL,NULL,'Handy Kingston 32GB DATA TRAVELER DT SWIVL USB 3.0',NULL,'2 tháng','Handy Kingston 32GB DATA TRAVELER DT SWIVL USB 3.0',5,2),
                              (114,NULL,10,0,100,NULL,NULL,NULL,NULL,'Handy Kingston 16GB DATA TRAVELER DT SWIVL USB 3',NULL,'2 tháng','Handy Kingston 16GB DATA TRAVELER DT SWIVL USB 3',5,2),
                              (115,NULL,120,0,123,NULL,NULL,NULL,NULL,'USB3.0 SILICON POWER Marvel M50 16GB',NULL,'2 tháng','USB3.0 SILICON POWER Marvel M50 16GB',5,2),
                              (116,NULL,300,0,100,NULL,NULL,NULL,NULL,'Router ASUS RT-N14UHP N300 3-in-1 Wi-Fi Router / Access Point / Repeater',NULL,'2 tháng','Router ASUS RT-N14UHP N300 3-in-1 Wi-Fi Router / Access Point / Repeater',6,3),
                              (117,NULL,120,0,100,NULL,NULL,NULL,NULL,'Router ASUS RT-N12+ 3-in-1 Router/AP/Range',NULL,'2 tháng','Router ASUS RT-N12+ 3-in-1 Router/AP/Range\r\n',6,3),
                              (118,NULL,10,0,100,NULL,NULL,NULL,NULL,'Router ASUS RT-N12HP (Black Diamond) N300 3-in-1 Wi-Fi Router / Access Point / Repeater',NULL,'2 tháng','Router ASUS RT-N12HP (Black Diamond) N300 3-in-1 Wi-Fi Router / Access Point / Repeater',6,3),
                              (119,NULL,100,0,100,NULL,NULL,NULL,NULL,'Router ASUS RT-AC86U',NULL,'2 tháng','Router ASUS RT-AC86U',6,3),
                              (120,NULL,10,0,100,NULL,NULL,NULL,NULL,'Router ASUS RT-A1300UHP',NULL,'2 tháng','Router ASUS RT-A1300UHP',6,3),
                              (121,NULL,20,0,100,NULL,NULL,NULL,NULL,'Router ASUS RT-AC68U',NULL,'2 tháng','Router ASUS RT-AC68U',6,3),
                              (122,NULL,23,0,100,NULL,NULL,NULL,NULL,'Router ASUS RT-AC58U',NULL,'2 tháng','Router ASUS RT-AC58U',6,3),
                              (123,NULL,129,0,100,NULL,NULL,NULL,NULL,'Router ASUS RT-AC53',NULL,'2 tháng','Router ASUS RT-AC53',6,3),
                              (124,NULL,12,0,100,NULL,NULL,NULL,NULL,'Camera Thân IP HikVision DS-2CD1023G0-I H265+',NULL,'2 tháng','Camera Thân IP HikVision DS-2CD1023G0-I H265+',7,2),
                              (125,NULL,1200,0,100,NULL,NULL,NULL,NULL,'Camera IP Hikvision Dome HIK -HDIP2820FH 2.0M',NULL,'2 tháng','Camera IP Hikvision Dome HIK -HDIP2820FH 2.0M',7,2),
                              (126,NULL,120,0,100,NULL,NULL,NULL,NULL,'Camera Vantech FullHD HD-SDI VP-120HD',NULL,'2 tháng','Camera Vantech FullHD HD-SDI VP-120HD',7,2),
                              (127,NULL,12,0,100,NULL,NULL,NULL,NULL,'Camera Dome Vantech VP- 4224T VT 1230',NULL,'2 tháng','Camera Dome Vantech VP- 4224T',7,2),
                              (128,NULL,12,0,100,NULL,NULL,NULL,NULL,'Camera Thân TVI HikVision DS-2CE16C0T-IRP',NULL,'2 tháng','Camera Thân TVI HikVision DS-2CE16C0T-IRP',7,2),
                              (129,NULL,12,0,100,NULL,NULL,NULL,NULL,'Camera Thân TVI HikVision DS-2CE16C0T-IRP',NULL,'2 tháng','Camera Thân TVI HikVision DS-2CE16C0T-IRP',7,2),
                              (130,NULL,12,0,100,NULL,NULL,NULL,NULL,'Camera IP Vantech quay quét VT-6300C',NULL,'2 tháng','Camera IP Vantech quay quét VT-6300C',7,2),
                              (131,NULL,10,0,100,NULL,NULL,NULL,NULL,'Camera IP Thân Hikvision DS-2CD2055FWD-I   H.265+',NULL,'2 tháng','Camera IP Thân Hikvision DS-2CD2055FWD-I   H.265+',7,3),
                              (132,NULL,12,0,100,NULL,NULL,NULL,NULL,'Mouse Pad Madcatz G.L.I.D.E.7',NULL,'2 tháng','Mouse Pad Madcatz G.L.I.D.E.7',8,3),
                              (133,NULL,0,0,100,NULL,NULL,NULL,NULL,'Đế Làm Mát Laptop Cooling pad N99',NULL,'2 tháng','Đế Làm Mát Laptop Cooling pad N99',8,2),
                              (134,NULL,12,0,100,NULL,NULL,NULL,NULL,'Mouse Pad Razer Goliathus SKT T1 Edition',NULL,'2 tháng','Mouse Pad Razer Goliathus SKT T1 Edition - Soft Gaming Mouse Mat - Medium - Speed  (355mm x 254mm)',8,4),
                              (135,NULL,12,0,100,NULL,NULL,NULL,NULL,'Mouse Pad Razer Goliathus Medium 2014 (250x300mm)',NULL,'2 tháng','Mouse Pad Razer Goliathus Medium 2014 (250x300mm)',8,6),
                              (136,NULL,12,0,100,NULL,NULL,NULL,NULL,'Cable HDMI 15m Vention',NULL,'1 tháng','Cable HDMI 15m Vention version 1.4',9,2),
                              (137,NULL,12,0,100,NULL,NULL,NULL,NULL,'Cable HDMI dẹt 1.5m Vention ',NULL,'1 tháng','Cable HDMI dẹt 1.5m Vention version 1.4',9,3),
                              (138,NULL,150,0,100,NULL,NULL,NULL,NULL,'Cable màn hình VGA ',NULL,'1 tháng','Cable màn hình VGA 1m -1.5m (Từ cổng 15 đực sang 15 đực)',9,5),
                              (139,NULL,12,0,100,NULL,NULL,NULL,NULL,'Cable HDMI dẹt 10m Vention version 1.4',NULL,'2 tháng','Cable HDMI dẹt 10m Vention version 1.4',9,5),
                              (140,NULL,10,0,100,NULL,NULL,NULL,NULL,'Bộ chuyển đổi HDMI sang VGA',NULL,'1 tháng','Bộ chuyển đổi HDMI sang VGA( có Audio) Vention Black Metal Type',9,5),
                              (141,NULL,10,0,100,NULL,NULL,NULL,NULL,'Cáp chuyển HDMI sang VGA ',NULL,'3 tháng','Cáp chuyển HDMI sang VGA Hỗ trợ Full HD DHTV-C20 Orico',9,5),
                              (142,NULL,14,0,100,NULL,NULL,NULL,NULL,'Cable HDMI 1.5m',NULL,'2 tháng','Cable HDMI 1.5m Version 2.0 hỗ trợ 4K 60Mhz',9,3),
                              (143,NULL,100,0,100,NULL,NULL,NULL,NULL,'Cáp chuyển TypeC ',NULL,'2 tháng','Cáp chuyển TypeC sang 5 cổng HDMI/TypeC/2*USB 3.0 RCH3A-GD Orico',9,6);
/*!40000 ALTER TABLE product ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS roles;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE roles (
  id bigint NOT NULL AUTO_INCREMENT,
  role_name varchar(20),
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
--
LOCK TABLES roles WRITE;
/*!40000 ALTER TABLE roles DISABLE KEYS */;
INSERT INTO roles VALUES (1,'ROLE_ADMIN'),(2,'ROLE_MEMBER'),(3,'ROLE_SHIPPER');
/*!40000 ALTER TABLE roles ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


