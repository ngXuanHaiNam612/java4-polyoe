# ASM2-PolyOE - Video Management System

## Mô tả
Hệ thống quản lý video với các chức năng: User, Video, Favorite, Share, ViewHistory.

## Công nghệ sử dụng
- Spring Boot 3.x
- Java 17+
- Gradle
- Lombok
- MyBatis
- SQL Server

## Cấu trúc dự án
```
src/main/java/com/java4/asm2polyoe/
├── controller/          # REST Controllers
├── service/            # Service interfaces
├── service/impl/       # Service implementations
├── entity/             # Entity classes
├── mapper/             # MyBatis mappers
├── dto/                # Data Transfer Objects
├── enums/              # Enumerations
└── exception/          # Exception handling
```

## Database Configuration

### SQL Server Setup
- Database: PolyOE
- Server: localhost:1433
- Username: sa
- Password: songlong

### Database Tables
Cần tạo các bảng sau trong database PolyOE:

```sql
CREATE DATABASE PolyOE;
USE PolyOE;

CREATE TABLE Users (
    Id NVARCHAR(50) NOT NULL,
    Password NVARCHAR(50) NOT NULL,
    Fullname NVARCHAR(50) NOT NULL,
    Email NVARCHAR(50) NOT NULL,
    Admin BIT NOT NULL,
    PRIMARY KEY(Id)
);

CREATE TABLE Video (
    Id VARCHAR(50) PRIMARY KEY,
    Title NVARCHAR(255),
    Poster NVARCHAR(250),
    [Views] INT,
    Description NVARCHAR(255),
    Active BIT
);

CREATE TABLE Favorite (
    Id BIGINT PRIMARY KEY,
    UserId NVARCHAR(50),
    VideoId VARCHAR(50),
    LikeDate DATE,
    FOREIGN KEY (UserId) REFERENCES [Users](Id),
    FOREIGN KEY (VideoId) REFERENCES Video(Id),
    CONSTRAINT UQ_User_Video UNIQUE (UserId, VideoId)
);

CREATE TABLE Share (
    Id BIGINT PRIMARY KEY,
    UserId NVARCHAR(50),
    VideoId VARCHAR(50),
    Emails VARCHAR(200) UNIQUE,
    ShareDate DATE,
    FOREIGN KEY (UserId) REFERENCES [Users](Id),
    FOREIGN KEY (VideoId) REFERENCES Video(Id)
);

CREATE TABLE ViewHistory (
    Id BIGINT PRIMARY KEY,
    UserId NVARCHAR(50),
    VideoId VARCHAR(50),
    ViewDate DATE,
    FOREIGN KEY (UserId) REFERENCES [Users](Id),
    FOREIGN KEY (VideoId) REFERENCES Video(Id)
);
```

### Sample Data
```sql
INSERT INTO Users (Id, Password, Fullname, Email, Admin)
VALUES 
('user001', 'pass123', N'Nguyễn Văn A', 'a@example.com', 1),
('user002', '12345678', N'Trần Thị B', 'b@example.com', 0),
('user003', 'qwertyui', N'Lê Văn C', 'c@example.com', 0),
('user004', 'adminpwd', N'Phạm Thị D', 'd@example.com', 1),
('user005', 'hello123', N'Hoàng Văn E', 'e@example.com', 0);

INSERT INTO Video (Id, Title, Poster, Views, Description, Active) VALUES
('v001', N'Intro to SQL', 'poster1.jpg', 1000, N'Learn SQL basics.', 1),
('v002', N'Advanced Joins', 'poster2.jpg', 850, N'Deep dive into JOINs.', 1),
('v003', N'Indexing Strategy', 'poster3.jpg', 900, N'Improve performance.', 1),
('v004', N'Stored Procedures', 'poster4.jpg', 450, N'Reusing logic.', 1),
('v005', N'Triggers Explained', 'poster5.jpg', 1200, N'Automate actions.', 1);

INSERT INTO Favorite (Id, UserId, VideoId, LikeDate) VALUES
(1, 'user001', 'v001', '2025-01-01'),
(2, 'user002', 'v002', '2025-01-02'),
(3, 'user003', 'v003', '2025-01-03');

INSERT INTO Share (Id, UserId, VideoId, Emails, ShareDate) VALUES
(1, 'user001', 'v001', 'friend1@example.com', '2025-02-01'),
(2, 'user002', 'v002', 'friend2@example.com', '2025-02-02'),
(3, 'user003', 'v003', 'friend3@example.com', '2025-02-03');

INSERT INTO ViewHistory (Id, UserId, VideoId, ViewDate) VALUES
(1, 'user001', 'v001', '2025-03-01'),
(2, 'user002', 'v002', '2025-03-02'),
(3, 'user003', 'v003', '2025-03-03');
```

## API Endpoints

### Users
- `GET /api/users` - Lấy danh sách tất cả users
- `GET /api/users/{id}` - Lấy user theo ID (String)
- `POST /api/users` - Tạo user mới
- `PUT /api/users/{id}` - Cập nhật user
- `DELETE /api/users/{id}` - Xóa user

### Videos
- `GET /api/videos` - Lấy danh sách tất cả videos
- `GET /api/videos/{id}` - Lấy video theo ID (String)
- `POST /api/videos` - Tạo video mới
- `PUT /api/videos/{id}` - Cập nhật video
- `DELETE /api/videos/{id}` - Xóa video

### Favorites
- `GET /api/favorites` - Lấy danh sách tất cả favorites
- `GET /api/favorites/{id}` - Lấy favorite theo ID (Long)
- `POST /api/favorites` - Tạo favorite mới
- `PUT /api/favorites/{id}` - Cập nhật favorite
- `DELETE /api/favorites/{id}` - Xóa favorite

### Shares
- `GET /api/shares` - Lấy danh sách tất cả shares
- `GET /api/shares/{id}` - Lấy share theo ID (Long)
- `POST /api/shares` - Tạo share mới
- `PUT /api/shares/{id}` - Cập nhật share
- `DELETE /api/shares/{id}` - Xóa share

### View Histories
- `GET /api/viewhistories` - Lấy danh sách tất cả view histories
- `GET /api/viewhistories/{id}` - Lấy view history theo ID (Long)
- `POST /api/viewhistories` - Tạo view history mới
- `PUT /api/viewhistories/{id}` - Cập nhật view history
- `DELETE /api/viewhistories/{id}` - Xóa view history

## Error Handling

Hệ thống sử dụng `AppException` với các error codes:

### User Errors (1000-1999)
- `1000` - List user is empty
- `1001` - Password incorrect
- `1002` - User not found

### Video Errors (2000-2999)
- `2000` - List video is empty
- `2001` - Video not found
- `2002` - Video already exists

### Favorite Errors (3000-3999)
- `3000` - List favorite is empty
- `3001` - Favorite not found
- `3002` - Favorite already exists

### Share Errors (4000-4999)
- `4000` - List share is empty
- `4001` - Share not found
- `4002` - Share already exists

### ViewHistory Errors (5000-5999)
- `5000` - List view history is empty
- `5001` - View history not found
- `5002` - View history already exists

### System Errors
- `500` - Bad SQL
- `501` - Uncatch exception
- `503` - Null pointer exception

## Response Format

Tất cả API responses đều có format:

```json
{
  "status": 200,
  "success": true,
  "data": {...},
  "message": "Success message"
}
```

## Chạy ứng dụng

### Prerequisites
1. SQL Server đã được cài đặt và chạy
2. Database PolyOE đã được tạo
3. Các bảng đã được tạo theo script SQL ở trên

### Steps
1. Clone repository
2. Đảm bảo SQL Server đang chạy và có thể kết nối
3. Chạy lệnh: `./gradlew bootRun`
4. Ứng dụng sẽ chạy tại: `http://localhost:1212`

## Testing

Sử dụng Postman hoặc curl để test các API endpoints.

Ví dụ:
```bash
# Lấy danh sách users
curl -X GET http://localhost:1212/api/users

# Tạo user mới
curl -X POST http://localhost:1212/api/users \
  -H "Content-Type: application/json" \
  -d '{"id":"user006","fullname":"Test User","email":"test@example.com","password":"password123","admin":false}'

# Tạo video mới
curl -X POST http://localhost:1212/api/videos \
  -H "Content-Type: application/json" \
  -d '{"id":"v006","title":"Test Video","poster":"test.jpg","description":"Test description","active":true}'

# Tạo favorite mới
curl -X POST http://localhost:1212/api/favorites \
  -H "Content-Type: application/json" \
  -d '{"id":11,"userId":"user001","videoId":"v001","likeDate":"2025-01-15"}'
```

## Architecture

### Service Layer
- **UserServiceImpl**: Sử dụng UserMapper để tương tác với database
- **VideoServiceImpl**: Sử dụng VideoMapper để tương tác với database
- **FavoriteServiceImpl**: Sử dụng FavoriteMapper để tương tác với database
- **ShareServiceImpl**: Sử dụng ShareMapper để tương tác với database
- **ViewHistoryServiceImpl**: Sử dụng ViewHistoryMapper để tương tác với database

### Data Access Layer
- **MyBatis Mappers**: Định nghĩa các SQL queries
- **XML Mappers**: Chứa các SQL statements
- **Entity Classes**: Đại diện cho database tables

### Exception Handling
- **AppException**: Custom exception với ErrorCode
- **GlobalExceptionHandling**: Xử lý tất cả exceptions globally

## Data Types

### Entity ID Types
- **User**: String id (NVARCHAR(50))
- **Video**: String id (VARCHAR(50))
- **Favorite**: Long id (BIGINT)
- **Share**: Long id (BIGINT)
- **ViewHistory**: Long id (BIGINT)

### Foreign Key Relationships
- **Favorite**: UserId (String) → Users.Id, VideoId (String) → Video.Id
- **Share**: UserId (String) → Users.Id, VideoId (String) → Video.Id
- **ViewHistory**: UserId (String) → Users.Id, VideoId (String) → Video.Id 