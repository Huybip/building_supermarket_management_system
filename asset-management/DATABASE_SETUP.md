# 🎯 Hệ Thống Quản Lý Tài Sản Công - Database SQL

## 📚 Các File Được Tạo

| File | Mô Tả |
|------|-------|
| `schema.sql` | SQL script tạo 4 bảng + 3 views + 1 procedure |
| `MYSQL_CREATE_TABLES.md` | Hướng dẫn chi tiết tạo database |
| `application.properties` | Config tự động chạy schema.sql |

---

## 🚀 Cách Sử Dụng Nhanh Nhất

### Bước 1: Chạy SQL Script Trực Tiếp (Được Khuyến Nghị)

```bash
# Kết nối MySQL Aiven
mysql -h mysql-xxxxx.xxx.aivencloud.com \
      -P 19xxx \
      -u avnadmin \
      -p < src/main/resources/schema.sql
```

**Hoặc** từ MySQL CLI:
```sql
mysql> source src/main/resources/schema.sql;
```

### Bước 2: Hoặc Để Spring Boot Tự Động Tạo

Khi chạy ứng dụng:
```bash
mvn spring-boot:run
```

Spring Boot sẽ:
1. Tạo bảng từ JPA Entities
2. Chạy schema.sql tự động
3. Thêm dữ liệu mẫu

---

## 📊 Bảng Tạo Ra

### 1. **departments** (Phòng Ban)
- 5 phòng ban mẫu đã được thêm
- Chuẩn bị cho users và assets

### 2. **users** (Người Dùng)
- 4 người dùng mẫu: admin, manager1, staff1, viewer1
- Roles: ADMIN, MANAGER, STAFF, VIEWER
- Password: mã hóa BCrypt

### 3. **assets** (Tài Sản Công)
- 5 tài sản mẫu
- Categories: ELECTRONICS, FURNITURE, MACHINERY, VEHICLE
- Status tracking: ACTIVE, MAINTENANCE, DAMAGED, DISPOSED

### 4. **asset_maintenance** (Lịch Sử Bảo Trì)
- 4 bản ghi bảo trì mẫu
- Loại: PREVENTIVE, CORRECTIVE, EMERGENCY

---

## 👤 Người Dùng Mẫu (Test)

| Username | Password | Role | Department |
|----------|----------|------|------------|
| admin | admin123 | ADMIN | Ban Quản Lý |
| manager1 | manager123 | MANAGER | Phòng Kỹ Thuật |
| staff1 | staff123 | STAFF | Phòng Hành Chính |
| viewer1 | viewer123 | VIEWER | Phòng Nhân Sự |

---

## 📈 Views & Reports

### vw_asset_stats_by_department
Thống kê tài sản theo phòng ban

### vw_assets_near_warranty_expiry
Tài sản sắp hết bảo hành (30 ngày)

### vw_maintenance_history
Lịch sử bảo trì chi tiết

---

## 🔧 Stored Procedure

### sp_update_asset_depreciation
Cập nhật giá trị tài sản theo khấu hao:

```sql
CALL sp_update_asset_depreciation();
```

**Công thức khấu hao (20% mỗi năm):**
- Năm 1: 80% giá trị gốc
- Năm 2: 60% giá trị gốc
- Năm 3-4: 40-50% giá trị gốc
- Năm 5+: 30% giá trị gốc

---

## 🔍 Kiểm Tra Dữ Liệu

```sql
-- Xem tất cả bảng
SHOW TABLES;

-- Đếm records
SELECT COUNT(*) FROM departments;     -- 5
SELECT COUNT(*) FROM users;           -- 4
SELECT COUNT(*) FROM assets;          -- 5
SELECT COUNT(*) FROM asset_maintenance; -- 4

-- Xem dữ liệu
SELECT * FROM vw_asset_stats_by_department;
SELECT * FROM vw_maintenance_history;
```

---

## 🎨 Schema Diagram

```
┌─────────────────────┐
│   departments       │
│  (5 phòng ban)      │
├─────────────────────┤
│ id (PK)             │
│ department_name     │
│ department_code     │
│ description         │
│ is_active           │
└──────────┬──────────┘
           │
      ┌────┴─────┬───────────┐
      │           │           │
      ▼           ▼           ▼
  ┌───────┐  ┌────────┐  ┌──────────────┐
  │ users │  │ assets │  │asset_maint.  │
  └───────┘  └────────┘  └──────────────┘
   (4 users)  (5 assets) (4 maintenance)
```

---

## ✅ Checklist

- ✅ 4 bảng chính được tạo
- ✅ Foreign keys được thiết lập
- ✅ Indexes được tạo trên các cột quan trọng
- ✅ 3 Views hỗ trợ báo cáo
- ✅ 1 Stored Procedure tính khấu hao
- ✅ 5 phòng ban mẫu
- ✅ 4 người dùng mẫu
- ✅ 5 tài sản mẫu
- ✅ 4 bản ghi bảo trì mẫu

---

## 🔗 Liên Kết Liên Quan

- Đọc `MYSQL_CREATE_TABLES.md` để xem chi tiết
- Đọc `MYSQL_AIVEN_SETUP.md` để setup Aiven
- Đọc `README.md` để xem kiến trúc hệ thống

---

## 📞 Thứ Tự Thực Hiện

1. ✅ **Setup Aiven MySQL** (`MYSQL_AIVEN_SETUP.md`)
2. ✅ **Chạy schema.sql** để tạo bảng
3. ✅ **Cấu hình application.properties** với Aiven credentials
4. ✅ **Chạy mvn spring-boot:run**
5. ✅ **Truy cập http://localhost:8080/asset-management**
6. ✅ **Đăng nhập với tài khoản test**

---

**Tất cả sẵn sàng! 🚀**
