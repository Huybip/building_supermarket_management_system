-- Database: supermarket
CREATE DATABASE IF NOT EXISTS supermarket;
USE supermarket;

-- Table SanPham
CREATE TABLE IF NOT EXISTS SanPham (
  id INT AUTO_INCREMENT PRIMARY KEY,
  ma VARCHAR(50) UNIQUE,
  ten VARCHAR(255),
  mo_ta TEXT,
  don_gia DECIMAL(10,2),
  ton_kho INT DEFAULT 0,
  ngay_san_xuat DATE,
  ngay_het_han DATE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table KhachHang
CREATE TABLE IF NOT EXISTS KhachHang (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) UNIQUE,
  mat_khau VARCHAR(255),
  ho_ten VARCHAR(255),
  dien_thoai VARCHAR(50),
  diem_tich_luy INT DEFAULT 0,
  the_thanh_vien VARCHAR(50),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table NhanVien
CREATE TABLE IF NOT EXISTS NhanVien (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) UNIQUE,
  mat_khau VARCHAR(255),
  ho_ten VARCHAR(255),
  role ENUM('admin','staff') DEFAULT 'staff',
  ca_lam VARCHAR(100),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table NhaCungCap
CREATE TABLE IF NOT EXISTS NhaCungCap (
  id INT AUTO_INCREMENT PRIMARY KEY,
  ten VARCHAR(255),
  dia_chi VARCHAR(255),
  dien_thoai VARCHAR(50),
  cong_no DECIMAL(12,2) DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table HoaDon
CREATE TABLE IF NOT EXISTS HoaDon (
  id INT AUTO_INCREMENT PRIMARY KEY,
  ma VARCHAR(100) UNIQUE,
  khach_hang_id INT,
  nhan_vien_id INT,
  tong_tien DECIMAL(12,2),
  phuong_thuc_thanh_toan ENUM('tienmat','the','vi') DEFAULT 'tienmat',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (khach_hang_id) REFERENCES KhachHang(id),
  FOREIGN KEY (nhan_vien_id) REFERENCES NhanVien(id)
);

-- Table ChiTietHoaDon
CREATE TABLE IF NOT EXISTS ChiTietHoaDon (
  id INT AUTO_INCREMENT PRIMARY KEY,
  hoadon_id INT,
  sanpham_id INT,
  so_luong INT,
  don_gia DECIMAL(12,2),
  thanh_tien DECIMAL(12,2),
  FOREIGN KEY (hoadon_id) REFERENCES HoaDon(id) ON DELETE CASCADE,
  FOREIGN KEY (sanpham_id) REFERENCES SanPham(id)
);

-- Table NhapHang
CREATE TABLE IF NOT EXISTS NhapHang (
  id INT AUTO_INCREMENT PRIMARY KEY,
  ncc_id INT,
  nhan_vien_id INT,
  tong_tien DECIMAL(12,2),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (ncc_id) REFERENCES NhaCungCap(id),
  FOREIGN KEY (nhan_vien_id) REFERENCES NhanVien(id)
);

-- Table ThongKe (tối giản, có thể gia tăng)
CREATE TABLE IF NOT EXISTS ThongKe (
  id INT AUTO_INCREMENT PRIMARY KEY,
  loai VARCHAR(100),
  gia_tri TEXT,
  ngay TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Trigger: sau khi thêm ChiTietHoaDon -> trừ tồn kho
DELIMITER //
CREATE TRIGGER trg_after_insert_chitiethoadon
AFTER INSERT ON ChiTietHoaDon
FOR EACH ROW
BEGIN
  UPDATE SanPham SET ton_kho = ton_kho - NEW.so_luong WHERE id = NEW.sanpham_id;
END;
//
DELIMITER ;

-- Trigger: sau khi thêm NhapHang và chi tiết (nên có chi tiết nhập - ở ví dụ này, cập nhật công nợ mẫu)
-- (Chi tiết nhập có thể được mở rộng, đơn giản hoá ở đây)