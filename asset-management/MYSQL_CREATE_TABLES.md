# 🗄️ Hướng Dẫn Tạo Database MySQL

## 📋 Các Bảng Sẽ Được Tạo

1. **departments** - Phòng ban
2. **users** - Người dùng hệ thống
3. **assets** - Tài sản công
4. **asset_maintenance** - Lịch sử bảo trì tài sản

Plus: 3 Views và 1 Stored Procedure

---

## 🚀 Cách 1: Chạy SQL Script Trực Tiếp (Nhanh Nhất)

### Bước 1: Kết Nối MySQL Aiven
```bash
mysql -h mysql-xxxxx.xxx.aivencloud.com -P 19xxx -u avnadmin -p
```

### Bước 2: Chạy Script
```bash
# Từ terminal (thay thế thông tin Aiven)
mysql -h mysql-xxxxx.xxx.aivencloud.com \
      -P 19xxx \
      -u avnadmin \
      -p < src/main/resources/schema.sql
```

**Hoặc** nhập password rồi execute:
```sql
mysql> source src/main/resources/schema.sql;
```

---

## 🔄 Cách 2: Chạy Qua Spring Boot (Tự Động)

### Bước 1: Cấu hình application.properties
```properties
spring.jpa.hibernate.ddl-auto=update
spring.datasource.initialization-mode=always
spring.datasource.continue-on-error=false
```

### Bước 2: Chạy Ứng Dụng
```bash
mvn spring-boot:run
```

**Kết quả:** 
- Spring Boot tự động tạo bảng từ Entities
- File `schema.sql` sẽ được execute tự động

---

## 🔑 Cách 3: Tạo Thủ Công Qua MySQL Workbench

1. **Mở MySQL Workbench**
2. **Kết nối đến Aiven Server**
3. **File → Open SQL Script** → Chọn `schema.sql`
4. **Execute** ⚡

---

## 📊 Chi Tiết Các Bảng

### 1. DEPARTMENTS (Phòng Ban)
```
- id: BIGINT (Primary Key)
- department_name: VARCHAR(100) - UNIQUE
- department_code: VARCHAR(20)
- description: VARCHAR(500)
- is_active: BOOLEAN (default true)
- created_at, updated_at, created_by, updated_by
```

**Dữ liệu mẫu:**
- Ban Quản Lý
- Phòng Kỹ Thuật
- Phòng Hành Chính
- Phòng Nhân Sự
- Phòng Tài Chính

---

### 2. USERS (Người Dùng)
```
- id: BIGINT (Primary Key)
- username: VARCHAR(100) - UNIQUE
- password: VARCHAR(255)
- full_name: VARCHAR(100)
- email: VARCHAR(100) - UNIQUE
- phone: VARCHAR(20)
- role: VARCHAR(50) - ADMIN, MANAGER, STAFF, VIEWER
- department_id: BIGINT (Foreign Key)
- is_active: BOOLEAN (default true)
- created_at, updated_at, created_by, updated_by
```

**Người dùng mẫu:**
- admin / admin123
- manager1 / manager123
- staff1 / staff123
- viewer1 / viewer123

---

### 3. ASSETS (Tài Sản Công)
```
- id: BIGINT (Primary Key)
- asset_code: VARCHAR(50) - UNIQUE
- asset_name: VARCHAR(255)
- description: VARCHAR(500)
- category: VARCHAR(50) - ELECTRONICS, FURNITURE, MACHINERY, BUILDING, VEHICLE, OFFICE_SUPPLIES, OTHER
- status: VARCHAR(50) - ACTIVE, MAINTENANCE, DAMAGED, DISPOSED, STORED
- original_value: DECIMAL(15, 2)
- current_value: DECIMAL(15, 2)
- purchase_date: DATE
- warranty_date: DATE
- serial_number: VARCHAR(255)
- location: VARCHAR(255)
- department_id: BIGINT (Foreign Key)
- image_path: VARCHAR(255)
- is_disposed: BOOLEAN (default false)
- notes: VARCHAR(500)
- created_at, updated_at, created_by, updated_by
```

**Tài sản mẫu:**
- ASSET-001: Máy Tính Dell XPS 13
- ASSET-002: Bàn Làm Việc Gỗ
- ASSET-003: Máy Chủ HP ProLiant
- ASSET-004: Bàn Ghế Hội Nghị
- ASSET-005: Ô Tô Công Vụ

---

### 4. ASSET_MAINTENANCE (Lịch Sử Bảo Trì)
```
- id: BIGINT (Primary Key)
- asset_id: BIGINT (Foreign Key)
- maintenance_date: DATE
- description: VARCHAR(500)
- maintenance_type: VARCHAR(50) - PREVENTIVE, CORRECTIVE, EMERGENCY
- cost: DECIMAL(10, 2)
- maintenance_by: VARCHAR(255)
- notes: VARCHAR(500)
- created_at, updated_at, created_by, updated_by
```

---

## 📈 VIEWs (Báo Cáo)

### 1. vw_asset_stats_by_department
Thống kê tài sản theo phòng ban:
```sql
SELECT * FROM vw_asset_stats_by_department;
```

**Kết quả:**
- department_name
- total_assets
- active_count
- maintenance_count
- damaged_count
- total_value

### 2. vw_assets_near_warranty_expiry
Tài sản sắp hết bảo hành (30 ngày tới):
```sql
SELECT * FROM vw_assets_near_warranty_expiry;
```

### 3. vw_maintenance_history
Lịch sử bảo trì chi tiết:
```sql
SELECT * FROM vw_maintenance_history;
```

---

## 🔧 STORED PROCEDURE

### sp_update_asset_depreciation
Cập nhật giá trị tài sản theo khấu hao (20% mỗi năm):

```sql
CALL sp_update_asset_depreciation();
```

**Công thức:**
- Năm 1: 80% giá trị gốc
- Năm 2: 60% giá trị gốc
- Năm 3: 50% giá trị gốc
- Năm 4: 40% giá trị gốc
- Năm 5+: 30% giá trị gốc

---

## ✅ Xác Thực Tạo Thành Công

Sau khi chạy script, kiểm tra:

```sql
-- Kiểm tra bảng
SHOW TABLES;

-- Đếm records
SELECT COUNT(*) FROM departments;
SELECT COUNT(*) FROM users;
SELECT COUNT(*) FROM assets;
SELECT COUNT(*) FROM asset_maintenance;

-- Xem dữ liệu mẫu
SELECT * FROM departments;
SELECT * FROM users;
SELECT * FROM assets;

-- Kiểm tra views
SELECT * FROM vw_asset_stats_by_department;
SELECT * FROM vw_maintenance_history;
```

---

## 🆘 Xử Lý Sự Cố

### Lỗi: "Table already exists"
```sql
-- Xóa database cũ (nếu cần)
DROP DATABASE IF EXISTS asset_management_db;

-- Rồi chạy lại script
```

### Lỗi: "Foreign key constraint"
- Đảm bảo parent table (departments) được tạo trước
- SQL script đã đảm bảo thứ tự này

### Lỗi: "Permission denied"
- Kiểm tra user Aiven có quyền CREATE, INSERT
- Hoặc dùng admin user

---

## 📝 Ghi Chú Quan Trọng

✅ **Charset:** UTF8MB4 (hỗ trợ emoji, ký tự đặc biệt)
✅ **Engine:** InnoDB (hỗ trợ transactions, foreign keys)
✅ **Indexes:** Được tạo trên các cột thường query
✅ **Foreign Keys:** Bảo vệ tính toàn vẹn dữ liệu
✅ **Default Values:** Timestamps tự động
✅ **Comments:** Mô tả chi tiết mỗi cột

---

## 🔐 Bảo Mật

Người dùng mẫu sử dụng password mã hóa BCrypt:
- Không dùng plain text
- Mật khẩu thực tế phải được hash trước khi lưu

Để tạo hash password BCrypt:
```bash
# Online tool hoặc:
echo -n "your_password" | openssl passwd -apr1
```

---

## 🚀 Tiếp Theo

1. ✅ Chạy SQL script để tạo database
2. ✅ Cấu hình `application.properties` với thông tin Aiven
3. ✅ Chạy Spring Boot
4. ✅ Truy cập http://localhost:8080/asset-management
5. ✅ Đăng nhập với user mẫu (admin/admin)

**Ready to go!** 🎉
