CREATE USER 'webshopapp'@'localhost' IDENTIFIED BY 'webshopapp';

GRANT ALL PRIVILEGES ON * . * TO 'webshopapp'@'localhost';

ALTER USER 'webshopapp'@'localhost' IDENTIFIED WITH mysql_native_password BY 'webshopapp';