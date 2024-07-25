DROP TABLE follows;
CREATE TABLE follows (
    username INTEGER NOT NULL,
    followee INTEGER NOT NULL,
    followed_at TIMESTAMP DEFAULT (now() AT TIME ZONE 'Asia/Seoul'),
    PRIMARY KEY (username, followee)
);

-- 인덱스 생성 (예시)
CREATE INDEX idx_follows_username ON follows(username);
CREATE INDEX idx_follows_followee ON follows(followee);
CREATE INDEX idx_followed_at ON follows(followed_at);
