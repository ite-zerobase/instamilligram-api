DROP TABLE comment_likes;
CREATE TABLE comment_likes (
    comment_id INTEGER NOT NULL,
    username VARCHAR(50) NOT NULL,
    liked_at TIMESTAMP DEFAULT (now() AT TIME ZONE 'Asia/Seoul'),
    PRIMARY KEY (comment_id, username)
);
