# 🗄️ Hướng Dẫn Kết Nối MySQL Aiven

## 📋 Chuẩn Bị

### 1. Tạo Tài Khoản Aiven
1. Truy cập https://aiven.io
2. Đăng ký hoặc đăng nhập
3. Tạo một MySQL Service mới

### 2. Lấy Thông Tin Kết Nối

Sau khi tạo MySQL Service, bạn sẽ nhận được các thông tin:
- **Host**: mysql-xxxxx.xxx.aivencloud.com
- **Port**: 19xxx (thường là 19xxx)
- **Username**: avnadmin (hoặc tên khác)
- **Password**: Password được tạo
- **Database**: tạo database tên `asset_management_db`

### 3. Cấu Hình application.properties

Sửa file `src/main/resources/application.properties`:

```properties
# MySQL Database Configuration (Aiven)
spring.datasource.url=jdbc:mysql://username:password@host:port/asset_management_db?useSSL=true&requireSSL=true
spring.datasource.username=avnadmin
spring.datasource.password=your_aiven_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Ví dụ:
spring.datasource.url=jdbc:mysql://avnadmin:mypassword@mysql-xxxxx.xxx.aivencloud.com:19xxx/asset_management_db?useSSL=true&requireSSL=true
spring.datasource.username=avnadmin
spring.datasource.password=mypassword
```

### 4. Đặc Biệt Với Aiven

⚠️ **Aiven yêu cầu SSL**, nên URL chứa `?useSSL=true&requireSSL=true`

Nếu gặp lỗi SSL:
```properties
# Thêm CA certificate validation
spring.datasource.url=jdbc:mysql://host:port/asset_management_db?useSSL=true&requireSSL=true&serverTimezone=UTC
```

## 🔌 Kết Nối Qua Terminal (Test)

Kiểm tra kết nối MySQL trước:
```bash
# Cài đặt mysql-client nếu chưa có
sudo apt-get install default-mysql-client

# Kết nối
mysql -h mysql-xxxxx.xxx.aivencloud.com -P 19xxx -u avnadmin -p

# Nhập password khi được hỏi
```

Sau khi kết nối, tạo database:
```sql
CREATE DATABASE asset_management_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
\q
```

## 🚀 Chạy Ứng Dụng

```bash
cd asset-management
mvn clean install
mvn spring-boot:run
```

## ✅ Xác Thực Kết Nối

Logs sẽ hiển thị:
```
Hibernate: create table departments (...)
Hibernate: create table users (...)
Hibernate: create table assets (...)
Hibernate: create table asset_maintenance (...)
```

## 🆘 Xử Lý Sự Cố

### Lỗi: "Can't get a connection, pool error"
- Kiểm tra host, port, username, password
- Đảm bảo SSL được bật

### Lỗi: "Access denied for user"
- Xác nhận password đúng
- Kiểm tra user permissions trên Aiven dashboard

### Lỗi: "Unknown database"
- Tạo database trước: `CREATE DATABASE asset_management_db;`

## 📝 Ghi Chú

- ✅ Tất cả bảng sẽ tự động tạo từ Entities
- ✅ `spring.jpa.hibernate.ddl-auto=update` sẽ cập nhật schema tự động
- ✅ Dữ liệu sẽ được lưu trữ trên Aiven cloud
