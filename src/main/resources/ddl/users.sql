DROP TABLE USERS;
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    profile_picture_url TEXT,
    bio TEXT,
    created_at TIMESTAMP DEFAULT (now() AT TIME ZONE 'Asia/Seoul'),
    updated_at TIMESTAMP DEFAULT (now() AT TIME ZONE 'Asia/Seoul')
);

-- 인덱스 생성 (예시)
CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_email ON users(email);
