
CREATE TABLE follows (
    follower_id INTEGER NOT NULL,
    following_id INTEGER NOT NULL,
    followed_at TIMESTAMP DEFAULT (now() AT TIME ZONE 'Asia/Seoul'),
    PRIMARY KEY (follower_id, following_id)
);

-- 인덱스 생성 (예시)
CREATE INDEX idx_follower_id ON follows(follower_id);
CREATE INDEX idx_following_id ON follows(following_id);
CREATE INDEX idx_followed_at ON follows(followed_at);
