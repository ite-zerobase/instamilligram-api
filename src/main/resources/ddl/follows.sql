DROP TABLE follows;
CREATE TABLE follows (
    follower VARCHAR(50) NOT NULL,
    followee VARCHAR(50) NOT NULL,
    followed_at TIMESTAMP DEFAULT (now() AT TIME ZONE 'Asia/Seoul'),
    PRIMARY KEY (follower, followee)
);

-- 인덱스 생성 (예시)
CREATE INDEX idx_follows_follower ON follows(follower);
CREATE INDEX idx_follows_followee ON follows(followee);
CREATE INDEX idx_followed_at ON follows(followed_at);
