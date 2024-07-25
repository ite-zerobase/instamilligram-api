DROP TABLE post_likes;
CREATE TABLE post_likes (
    post_id INTEGER NOT NULL,
    username VARCHAR(50) NOT NULL,
    liked_at TIMESTAMP DEFAULT (now() AT TIME ZONE 'Asia/Seoul'),
    PRIMARY KEY (post_id, username)
);