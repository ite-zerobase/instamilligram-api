DROP TABLE posts;
CREATE TABLE posts (
    post_id SERIAL PRIMARY KEY,
    caption TEXT NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT (now() AT TIME ZONE 'Asia/Seoul'),
    updated_at TIMESTAMP DEFAULT (now() AT TIME ZONE 'Asia/Seoul'),
    like_count INTEGER DEFAULT 0,
    comment_count INTEGER DEFAULT 0,
    place TEXT,
    tagged_users TEXT[] -- 배열 형태로 태그된 사용자 목록 저장
);

-- 인덱스 생성 (예시)
CREATE INDEX idx_created_by ON posts(created_by);
CREATE INDEX idx_created_at ON posts(created_at);
