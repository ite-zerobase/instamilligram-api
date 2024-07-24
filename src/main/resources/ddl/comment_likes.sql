CREATE TABLE comment_likes (
    comment_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    liked_at TIMESTAMP DEFAULT (now() AT TIME ZONE 'Asia/Seoul'),
    PRIMARY KEY (comment_id, user_id)
);
