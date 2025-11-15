-- ====================================================
-- Asset Management System - MySQL Database Schema
-- Ngôn ngữ: MySQL 8.0+
-- Encoding: UTF8MB4
-- ====================================================

-- ====================================================
-- 1. Tạo Database
-- ====================================================
CREATE DATABASE IF NOT EXISTS asset_management_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE asset_management_db;

-- ====================================================
-- 2. Tạo Bảng DEPARTMENTS (Phòng Ban)
-- ====================================================
CREATE TABLE IF NOT EXISTS departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL UNIQUE COMMENT 'Tên phòng ban',
    department_code VARCHAR(20) COMMENT 'Mã phòng ban',
    description VARCHAR(500) COMMENT 'Mô tả',
    is_active BOOLEAN DEFAULT true COMMENT 'Trạng thái hoạt động',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Ngày tạo',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Ngày cập nhật',
    created_by VARCHAR(50) COMMENT 'Người tạo',
    updated_by VARCHAR(50) COMMENT 'Người cập nhật',
    
    INDEX idx_department_code (department_code),
    INDEX idx_is_active (is_active),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Bảng Phòng Ban';

-- ====================================================
-- 3. Tạo Bảng USERS (Người Dùng)
-- ====================================================
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE COMMENT 'Tên đăng nhập',
    password VARCHAR(255) NOT NULL COMMENT 'Mật khẩu',
    full_name VARCHAR(100) NOT NULL COMMENT 'Họ và tên',
    email VARCHAR(100) UNIQUE COMMENT 'Email',
    phone VARCHAR(20) COMMENT 'Số điện thoại',
    role VARCHAR(50) NOT NULL COMMENT 'Vai trò: ADMIN, MANAGER, STAFF, VIEWER',
    department_id BIGINT COMMENT 'ID phòng ban',
    is_active BOOLEAN DEFAULT true COMMENT 'Trạng thái hoạt động',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Ngày tạo',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Ngày cập nhật',
    created_by VARCHAR(50) COMMENT 'Người tạo',
    updated_by VARCHAR(50) COMMENT 'Người cập nhật',
    
    CONSTRAINT fk_users_department_id FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE SET NULL,
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_role (role),
    INDEX idx_department_id (department_id),
    INDEX idx_is_active (is_active),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Bảng Người Dùng';

-- ====================================================
-- 4. Tạo Bảng ASSETS (Tài Sản Công)
-- ====================================================
CREATE TABLE IF NOT EXISTS assets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    asset_code VARCHAR(50) NOT NULL UNIQUE COMMENT 'Mã tài sản',
    asset_name VARCHAR(255) NOT NULL COMMENT 'Tên tài sản',
    description VARCHAR(500) COMMENT 'Mô tả',
    category VARCHAR(50) NOT NULL COMMENT 'Danh mục: ELECTRONICS, FURNITURE, MACHINERY, BUILDING, VEHICLE, OFFICE_SUPPLIES, OTHER',
    status VARCHAR(50) NOT NULL COMMENT 'Trạng thái: ACTIVE, MAINTENANCE, DAMAGED, DISPOSED, STORED',
    original_value DECIMAL(15, 2) NOT NULL COMMENT 'Giá trị gốc',
    current_value DECIMAL(15, 2) COMMENT 'Giá trị hiện tại',
    purchase_date DATE NOT NULL COMMENT 'Ngày mua',
    warranty_date DATE NOT NULL COMMENT 'Ngày hết bảo hành',
    serial_number VARCHAR(255) COMMENT 'Số seri',
    location VARCHAR(255) COMMENT 'Vị trí',
    department_id BIGINT NOT NULL COMMENT 'ID phòng ban',
    image_path VARCHAR(255) COMMENT 'Đường dẫn ảnh',
    is_disposed BOOLEAN DEFAULT false COMMENT 'Đã thanh lý',
    notes VARCHAR(500) COMMENT 'Ghi chú',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Ngày tạo',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Ngày cập nhật',
    created_by VARCHAR(50) COMMENT 'Người tạo',
    updated_by VARCHAR(50) COMMENT 'Người cập nhật',
    
    CONSTRAINT fk_assets_department_id FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE RESTRICT,
    INDEX idx_asset_code (asset_code),
    INDEX idx_category (category),
    INDEX idx_status (status),
    INDEX idx_department_id (department_id),
    INDEX idx_is_disposed (is_disposed),
    INDEX idx_purchase_date (purchase_date),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Bảng Tài Sản Công';

-- ====================================================
-- 5. Tạo Bảng ASSET_MAINTENANCE (Lịch Sử Bảo Trì)
-- ====================================================
CREATE TABLE IF NOT EXISTS asset_maintenance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    asset_id BIGINT NOT NULL COMMENT 'ID tài sản',
    maintenance_date DATE NOT NULL COMMENT 'Ngày bảo trì',
    description VARCHAR(500) COMMENT 'Mô tả công việc bảo trì',
    maintenance_type VARCHAR(50) NOT NULL COMMENT 'Loại bảo trì: PREVENTIVE, CORRECTIVE, EMERGENCY',
    cost DECIMAL(10, 2) COMMENT 'Chi phí bảo trì',
    maintenance_by VARCHAR(255) COMMENT 'Người thực hiện bảo trì',
    notes VARCHAR(500) COMMENT 'Ghi chú',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Ngày tạo',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Ngày cập nhật',
    created_by VARCHAR(50) COMMENT 'Người tạo',
    updated_by VARCHAR(50) COMMENT 'Người cập nhật',
    
    CONSTRAINT fk_asset_maintenance_asset_id FOREIGN KEY (asset_id) REFERENCES assets(id) ON DELETE CASCADE,
    INDEX idx_asset_id (asset_id),
    INDEX idx_maintenance_date (maintenance_date),
    INDEX idx_maintenance_type (maintenance_type),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Bảng Lịch Sử Bảo Trì Tài Sản';

-- ====================================================
-- 6. Thêm Dữ Liệu Mẫu
-- ====================================================

-- Thêm phòng ban mẫu
INSERT INTO departments (department_name, department_code, description, is_active) VALUES
('Ban Quản Lý', 'MGMT', 'Ban Quản Lý Công Ty', true),
('Phòng Kỹ Thuật', 'TECH', 'Phòng Kỹ Thuật', true),
('Phòng Hành Chính', 'ADM', 'Phòng Hành Chính', true),
('Phòng Nhân Sự', 'HR', 'Phòng Nhân Sự', true),
('Phòng Tài Chính', 'FIN', 'Phòng Tài Chính', true);

-- Thêm người dùng mẫu
INSERT INTO users (username, password, full_name, email, phone, role, department_id, is_active) VALUES
('admin', '$2a$10$slYQmyNdGzin7olVN3XBeOK1MmYkYvXW9vjW8.5.vQj5.0eKq.I5K', 'Quản Trị Viên', 'admin@company.com', '0123456789', 'ADMIN', 1, true),
('manager1', '$2a$10$slYQmyNdGzin7olVN3XBeOK1MmYkYvXW9vjW8.5.vQj5.0eKq.I5K', 'Trần Văn A', 'trana@company.com', '0987654321', 'MANAGER', 2, true),
('staff1', '$2a$10$slYQmyNdGzin7olVN3XBeOK1MmYkYvXW9vjW8.5.vQj5.0eKq.I5K', 'Nguyễn Thị B', 'thib@company.com', '0911111111', 'STAFF', 3, true),
('viewer1', '$2a$10$slYQmyNdGzin7olVN3XBeOK1MmYkYvXW9vjW8.5.vQj5.0eKq.I5K', 'Lý Văn C', 'vanc@company.com', '0922222222', 'VIEWER', 4, true);

-- Thêm tài sản mẫu
INSERT INTO assets (asset_code, asset_name, description, category, status, original_value, current_value, purchase_date, warranty_date, serial_number, location, department_id, is_disposed) VALUES
('ASSET-001', 'Máy Tính Dell XPS 13', 'Laptop Dell XPS 13 InfinityEdge', 'ELECTRONICS', 'ACTIVE', 25000000.00, 20000000.00, '2023-01-15', '2025-01-15', 'DLL-0001', 'Phòng Kỹ Thuật', 2, false),
('ASSET-002', 'Bàn Làm Việc Gỗ', 'Bàn làm việc gỗ tự nhiên', 'FURNITURE', 'ACTIVE', 5000000.00, 4500000.00, '2023-03-20', '2027-03-20', 'FUR-0001', 'Phòng Hành Chính', 3, false),
('ASSET-003', 'Máy Chủ HP ProLiant', 'Máy chủ HP ProLiant DL380 Gen10', 'MACHINERY', 'ACTIVE', 100000000.00, 85000000.00, '2022-06-10', '2024-06-10', 'HP-0001', 'Phòng Kỹ Thuật', 2, false),
('ASSET-004', 'Bàn Ghế Hội Nghị', 'Bộ bàn ghế hội nghị 12 chỗ', 'FURNITURE', 'MAINTENANCE', 30000000.00, 28000000.00, '2021-09-05', '2026-09-05', 'CONF-001', 'Phòng Hội Nghị', 1, false),
('ASSET-005', 'Ô Tô Công Vụ', 'Xe ô tô Toyota Camry 2.5V', 'VEHICLE', 'ACTIVE', 800000000.00, 650000000.00, '2020-12-01', '2025-12-01', 'TOY-0001', 'Bãi Giữ Xe', 1, false);

-- Thêm lịch sử bảo trì mẫu
INSERT INTO asset_maintenance (asset_id, maintenance_date, description, maintenance_type, cost, maintenance_by) VALUES
(1, '2024-01-10', 'Cập nhật driver, kiểm tra hệ thống', 'PREVENTIVE', 500000.00, 'Nguyễn Thị B'),
(3, '2024-02-15', 'Thay quạt làm mát', 'CORRECTIVE', 2000000.00, 'Trần Văn A'),
(4, '2024-03-20', 'Sửa chân bàn, thay đệm ghế', 'MAINTENANCE', 3000000.00, 'Lý Văn C'),
(5, '2024-04-01', 'Bảo dưỡng định kỳ 100.000km', 'PREVENTIVE', 5000000.00, 'Nguyễn Thị B');

-- ====================================================
-- 7. Views - Báo Cáo
-- ====================================================

-- View: Thống kê tài sản theo phòng ban
CREATE OR REPLACE VIEW vw_asset_stats_by_department AS
SELECT 
    d.id,
    d.department_name,
    d.department_code,
    COUNT(a.id) as total_assets,
    SUM(CASE WHEN a.status = 'ACTIVE' THEN 1 ELSE 0 END) as active_count,
    SUM(CASE WHEN a.status = 'MAINTENANCE' THEN 1 ELSE 0 END) as maintenance_count,
    SUM(CASE WHEN a.status = 'DAMAGED' THEN 1 ELSE 0 END) as damaged_count,
    SUM(CASE WHEN a.is_disposed = false THEN a.current_value ELSE 0 END) as total_value
FROM departments d
LEFT JOIN assets a ON d.id = a.department_id
GROUP BY d.id, d.department_name, d.department_code;

-- View: Tài sản cần bảo trì sắp hết bảo hành
CREATE OR REPLACE VIEW vw_assets_near_warranty_expiry AS
SELECT 
    a.id,
    a.asset_code,
    a.asset_name,
    a.warranty_date,
    DATEDIFF(a.warranty_date, CURDATE()) as days_remaining,
    d.department_name,
    a.status
FROM assets a
JOIN departments d ON a.department_id = d.id
WHERE a.is_disposed = false
AND a.warranty_date <= DATE_ADD(CURDATE(), INTERVAL 30 DAY)
AND a.warranty_date >= CURDATE()
ORDER BY a.warranty_date ASC;

-- View: Lịch sử bảo trì chi tiết
CREATE OR REPLACE VIEW vw_maintenance_history AS
SELECT 
    m.id,
    m.maintenance_date,
    a.asset_code,
    a.asset_name,
    d.department_name,
    m.maintenance_type,
    m.description,
    m.cost,
    m.maintenance_by
FROM asset_maintenance m
JOIN assets a ON m.asset_id = a.id
JOIN departments d ON a.department_id = d.id
ORDER BY m.maintenance_date DESC;

-- ====================================================
-- 8. Procedures - Hàm Hỗ Trợ
-- ====================================================

-- Procedure: Cập nhật giá trị tài sản theo khấu hao (20% mỗi năm)
DELIMITER $$

CREATE PROCEDURE sp_update_asset_depreciation()
BEGIN
    UPDATE assets
    SET current_value = CASE 
        WHEN YEAR(CURDATE()) - YEAR(purchase_date) >= 5 THEN original_value * 0.3
        WHEN YEAR(CURDATE()) - YEAR(purchase_date) = 4 THEN original_value * 0.4
        WHEN YEAR(CURDATE()) - YEAR(purchase_date) = 3 THEN original_value * 0.5
        WHEN YEAR(CURDATE()) - YEAR(purchase_date) = 2 THEN original_value * 0.6
        WHEN YEAR(CURDATE()) - YEAR(purchase_date) = 1 THEN original_value * 0.8
        ELSE original_value
    END,
    updated_at = CURRENT_TIMESTAMP
    WHERE is_disposed = false;
END$$

DELIMITER ;

-- ====================================================
-- 9. Privileges (Tùy Chọn)
-- ====================================================

-- Tạo user riêng cho application (tùy chọn)
-- CREATE USER IF NOT EXISTS 'app_user'@'localhost' IDENTIFIED BY 'secure_password';
-- GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, INDEX, EXECUTE ON asset_management_db.* TO 'app_user'@'localhost';
-- FLUSH PRIVILEGES;

-- ====================================================
-- Kết Thúc Script
-- ====================================================
COMMIT;

-- Thông tin hữu ích:
-- - Tất cả bảng sử dụng charset utf8mb4_unicode_ci
-- - Tất cả bảng có PRIMARY KEY (id)
-- - Các FOREIGN KEY được thiết lập với ON DELETE rules
-- - Các INDEX được tạo trên các cột thường được query
-- - Có 3 VIEWs hỗ trợ báo cáo
-- - Có 1 PROCEDURE để cập nhật khấu hao
