# Asset Management System - Hệ Thống Quản Lý Tài Sản Công

## 📋 Mô Tả

Hệ thống quản lý tài sản công được xây dựng bằng **Spring Boot 3.3.0** với **Java 17**, tuân thủ các nguyên tắc **OOP (Object-Oriented Programming)** và **Design Patterns** chuẩn công nghiệp.

## 🎯 Tính Năng Chính

✅ Quản lý tài sản công (tạo, cập nhật, xóa, tìm kiếm)
✅ Quản lý phòng ban và người dùng
✅ Theo dõi trạng thái tài sản
✅ Lịch sử bảo trì tài sản
✅ Tính toán khấu hao tài sản
✅ Báo cáo giá trị tài sản theo phòng ban
✅ Hệ thống phân quyền người dùng

## 🏗️ Kiến Trúc

### Project Structure
```
asset-management/
├── src/
│   ├── main/
│   │   ├── java/com/company/assetmanagement/
│   │   │   ├── model/          # JPA Entities (tuân thủ OOP - Encapsulation)
│   │   │   ├── repository/     # Data Access Layer (Abstraction)
│   │   │   ├── service/        # Business Logic Layer (Single Responsibility)
│   │   │   ├── controller/     # REST API Endpoints (MVC Pattern)
│   │   │   ├── config/         # Configuration Classes (Configuration Pattern)
│   │   │   └── AssetManagementApplication.java
│   │   └── resources/
│   │       ├── templates/      # HTML Templates (Thymeleaf)
│   │       └── static/
│   │           ├── css/        # Stylesheets
│   │           ├── js/         # Frontend JavaScript
│   │           └── images/     # Images
│   └── test/
```

## 🗂️ Các Lớp Model (OOP - Encapsulation & Inheritance)

### BaseEntity
- Lớp cha cho tất cả entities
- Chứa các trường chung: id, createdAt, updatedAt, createdBy, updatedBy
- Sử dụng `@MappedSuperclass` để kế thừa

### Department (Phòng Ban)
- Quản lý thông tin phòng ban
- Có mối quan hệ 1-N với Asset và User

### User (Người Dùng)
- Quản lý người dùng hệ thống
- Có các vai trò: ADMIN, MANAGER, STAFF, VIEWER
- Liên kết với Department

### Asset (Tài Sản)
- Thông tin chi tiết của tài sản công
- Enums cho AssetCategory và AssetStatus
- Tính năng: mã tài sản, giá trị, ngày mua, bảo hành

### AssetMaintenance (Bảo Trì Tài Sản)
- Lịch sử bảo trì của từng tài sản
- Enums cho MaintenanceType
- Truy vết chi phí bảo trì

## 🔗 Repository Layer (Data Access - Abstraction)

- **DepartmentRepository**: CRUD + Custom queries cho Department
- **UserRepository**: Tìm kiếm theo username, email, role
- **AssetRepository**: Tìm kiếm phức tạp, tính toán giá trị
- **AssetMaintenanceRepository**: Query lịch sử bảo trì

## 💼 Service Layer (Business Logic - Single Responsibility)

### IDepartmentService Interface
- Define các phương thức interface (Contract)

### DepartmentService Implementation
- Xử lý logic nghiệp vụ cho Department
- Validation và error handling
- Transaction management

### IAssetService Interface & AssetService
- Quản lý tài sản, tính toán khấu hao
- Method: `calculateDepreciation()` - tính giá trị khấu hao

## 🌐 Controller Layer (REST API - MVC Pattern)

### DepartmentController
```
POST   /api/departments             - Tạo phòng ban
GET    /api/departments/{id}       - Lấy phòng ban theo ID
GET    /api/departments/code/{code} - Lấy phòng ban theo code
GET    /api/departments            - Lấy tất cả phòng ban hoạt động
PUT    /api/departments/{id}       - Cập nhật phòng ban
DELETE /api/departments/{id}       - Xóa phòng ban (soft delete)
```

### AssetController
```
POST   /api/assets                      - Tạo tài sản
GET    /api/assets/{id}                - Lấy tài sản theo ID
GET    /api/assets/code/{code}         - Lấy tài sản theo code
GET    /api/assets                     - Lấy tất cả tài sản
GET    /api/assets/active              - Lấy tài sản đang hoạt động
GET    /api/assets/department/{id}     - Lấy tài sản theo phòng ban
GET    /api/assets/status/{status}     - Lấy tài sản theo trạng thái
GET    /api/assets/category/{category} - Lấy tài sản theo danh mục
PUT    /api/assets/{id}                - Cập nhật tài sản
PUT    /api/assets/{id}/dispose        - Thanh lý tài sản
GET    /api/assets/department/{id}/count - Đếm tài sản theo phòng ban
GET    /api/assets/department/{id}/totalvalue - Tổng giá trị tài sản
```

## 🗄️ Database Configuration

### Database: PostgreSQL
**Tại sao PostgreSQL?**
- ✅ ACID transactions: Đảm bảo tính toàn vẹn dữ liệu tài sản
- ✅ Complex queries: Hỗ trợ báo cáo phức tạp
- ✅ JSON support: Lưu metadata tài sản linh hoạt
- ✅ Full-text search: Tìm kiếm tài sản hiệu quả
- ✅ Open source & miễn phí

### Kết Nối Database (application.properties)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/asset_management_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Khởi Tạo Database PostgreSQL
```bash
# Tạo database
createdb asset_management_db

# Hoặc từ PostgreSQL CLI
psql -U postgres -c "CREATE DATABASE asset_management_db;"
```

## 🎨 Frontend (Giao Diện & Hình Ảnh)

### Vị Trí File
- **HTML Templates**: `src/main/resources/templates/` (Thymeleaf)
- **CSS Stylesheets**: `src/main/resources/static/css/`
- **JavaScript**: `src/main/resources/static/js/`
- **Images**: `src/main/resources/static/images/`

### Cấu Trúc HTML
```
src/main/resources/
├── templates/
│   ├── index.html          # Dashboard chính
│   ├── assets.html         # Trang quản lý tài sản
│   ├── departments.html    # Trang quản lý phòng ban
│   └── reports.html        # Trang báo cáo
├── static/
│   ├── css/
│   │   ├── style.css       # Styles chính
│   │   └── responsive.css  # Responsive design
│   ├── js/
│   │   ├── app.js          # Frontend logic chính
│   │   └── api.js          # API calls
│   └── images/
│       ├── logo.png
│       ├── icon-asset.svg
│       └── backgrounds/
```

## 🛠️ Dependencies (Maven pom.xml)

```xml
<!-- Spring Boot -->
spring-boot-starter-web
spring-boot-starter-data-jpa

<!-- Database -->
postgresql driver

<!-- Utilities -->
lombok (reduce boilerplate)

<!-- Testing -->
spring-boot-starter-test
```

## 🚀 Chạy Ứng Dụng

### Yêu Cầu
- Java 17+
- Maven 3.6+
- PostgreSQL 12+

### Các Bước

1. **Chuẩn Bị Database**
```bash
createdb asset_management_db
```

2. **Cấu Hình Database** (application.properties)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/asset_management_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```

3. **Build Project**
```bash
mvn clean install
```

4. **Chạy Ứng Dụng**
```bash
mvn spring-boot:run
```

5. **Truy Cập**
```
http://localhost:8080/asset-management
```

## 📊 Nguyên Tắc OOP Được Áp Dụng

| Nguyên Tắc | Ứng Dụng |
|-----------|---------|
| **Encapsulation** | Model: private fields, public getters/setters (Lombok @Data) |
| **Inheritance** | BaseEntity: lớp cha cho tất cả entities |
| **Abstraction** | Repository: JpaRepository interfaces; Service: Interface-based design |
| **Polymorphism** | Service interfaces với multiple implementations |
| **Single Responsibility** | Mỗi class một trách nhiệm (Controller, Service, Repository) |
| **Dependency Injection** | @Autowired, Spring Container quản lý beans |
| **Interface Segregation** | Tách biệt interfaces nhỏ (IDepartmentService, IAssetService) |

## 🔐 Bảo Mật

- Validation input trên Service layer
- Transactional management với @Transactional
- Soft delete pattern (is_active flag)
- User roles (ADMIN, MANAGER, STAFF, VIEWER)
- CORS configuration cho API

## 📝 License

MIT License

## 👨‍💻 Developer

Designed and implemented with OOP best practices and clean code principles.
