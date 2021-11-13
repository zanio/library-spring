DROP DATABASE IF EXISTS  books_test_db;
CREATE DATABASE books_test_db;
DROP USER IF EXISTS books_user;
CREATE USER books_user WITH PASSWORD 'books__test_123';
GRANT ALL PRIVILEGES ON DATABASE
"books_test_db" to books_user;
