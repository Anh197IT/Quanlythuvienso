CREATE DATABASE IF NOT EXISTS library2;

USE library2;

-- Bảng Quản lý Người dùng
CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role ENUM('Admin', 'Lecturer', 'Student') NOT NULL
) ENGINE=InnoDB;

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    published_date DATE,             -- Sử dụng DATE cho ngày xuất bản
    isbn VARCHAR(255) UNIQUE,        -- ISBN là duy nhất
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Tự động lấy thời gian hiện tại khi tạo mới bản ghi
);


CREATE TABLE borrower (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    book_title VARCHAR(255) NOT NULL,  -- Lưu tên sách thay vì book_id
    borrow_date DATE NOT NULL,
    return_date DATE NOT NULL
);


CREATE TABLE danh_muc (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ten_danh_muc VARCHAR(255) NOT NULL
);

