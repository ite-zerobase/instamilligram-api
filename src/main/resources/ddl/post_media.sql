CREATE TABLE post_media (
    media_seq INTEGER NOT NULL,
    post_id INTEGER NOT NULL,
    media_url TEXT NOT NULL,
    media_type VARCHAR(10), -- 'image' 또는 'video'와 같은 값
    created_at TIMESTAMP DEFAULT (now() AT TIME ZONE 'Asia/Seoul')
);

CREATE INDEX idx_post_id ON post_media(post_id);
CREATE INDEX idx_media_url ON post_media(media_url);
