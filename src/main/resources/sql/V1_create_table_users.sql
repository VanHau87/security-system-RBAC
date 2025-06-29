CREATE TABLE [users] (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(100) NOT NULL UNIQUE,
    email NVARCHAR(255) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    full_name NVARCHAR(255),
    phone_number NVARCHAR(20),
    avatar_url NVARCHAR(255),
    enabled BIT NOT NULL DEFAULT 1,
    account_non_locked BIT NOT NULL DEFAULT 1,
    failed_login_attempts INT DEFAULT 0,
    lock_time DATETIME2,
    last_login DATETIME2,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME()
);