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
('v005', N'Triggers Explained', 'poster5.jpg', 1200, N'Automate actions.', 1),
('v006', N'Normalization', 'poster6.jpg', 650, N'Design efficient schema.', 1),
('v007', N'Subqueries', 'poster7.jpg', 500, N'Nested queries.', 1),
('v008', N'Window Functions', 'poster8.jpg', 700, N'Powerful analytics.', 1),
('v009', N'Transactions', 'poster9.jpg', 300, N'ACID principles.', 1),
('v010', N'SQL Server Tips', 'poster10.jpg', 1100, N'Work smarter.', 1);


INSERT INTO Favorite (Id, UserId, VideoId, LikeDate) VALUES
(1, 'user001', 'v001', '2025-01-01'),
(2, 'user002', 'v002', '2025-01-02'),
(3, 'user003', 'v003', '2025-01-03'),
(4, 'user004', 'v004', '2025-01-04'),
(5, 'user005', 'v005', '2025-01-05'),
(6, 'user001', 'v006', '2025-01-06'),
(7, 'user002', 'v007', '2025-01-07'),
(8, 'user003', 'v008', '2025-01-08'),
(9, 'user004', 'v009', '2025-01-09'),
(10, 'user005', 'v010', '2025-01-10');

INSERT INTO Share (Id, UserId, VideoId, Emails, ShareDate) VALUES
(1, 'user001', 'v001', 'friend1@example.com', '2025-02-01'),
(2, 'user002', 'v002', 'friend2@example.com', '2025-02-02'),
(3, 'user003', 'v003', 'friend3@example.com', '2025-02-03'),
(4, 'user004', 'v004', 'friend4@example.com', '2025-02-04'),
(5, 'user005', 'v005', 'friend5@example.com', '2025-02-05'),
(6, 'user001', 'v006', 'friend6@example.com', '2025-02-06'),
(7, 'user002', 'v007', 'friend7@example.com', '2025-02-07'),
(8, 'user003', 'v008', 'friend8@example.com', '2025-02-08'),
(9, 'user004', 'v009', 'friend9@example.com', '2025-02-09'),
(10, 'user005', 'v010', 'friend10@example.com', '2025-02-10');

-- Insert sample data for ViewHistory
INSERT INTO ViewHistory (Id, UserId, VideoId, ViewDate) VALUES
(1, 'user001', 'v001', '2025-03-01'),
(2, 'user002', 'v002', '2025-03-02'),
(3, 'user003', 'v003', '2025-03-03'),
(4, 'user004', 'v004', '2025-03-04'),
(5, 'user005', 'v005', '2025-03-05'),
(6, 'user001', 'v006', '2025-03-06'),
(7, 'user002', 'v007', '2025-03-07'),
(8, 'user003', 'v008', '2025-03-08'),
(9, 'user004', 'v009', '2025-03-09'),
(10, 'user005', 'v010', '2025-03-10');