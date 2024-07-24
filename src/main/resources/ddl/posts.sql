
CREATE TABLE posts (
    post_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    caption TEXT,
    created_at TIMESTAMP DEFAULT (now() AT TIME ZONE 'Asia/Seoul'),
    updated_at TIMESTAMP DEFAULT (now() AT TIME ZONE 'Asia/Seoul'),
    likes_count INTEGER DEFAULT 0,
    comments_count INTEGER DEFAULT 0,
    place TEXT,
    tagged_users TEXT[] -- 배열 형태로 태그된 사용자 목록 저장
);

-- 인덱스 생성 (예시)
CREATE INDEX idx_user_id ON posts(user_id);
CREATE INDEX idx_created_at ON posts(created_at);
