DROP TABLE post_comment;
DROP TABLE post_like;
DROP TABLE user_post;
DROP TABLE user_profile;
DROP EXTENSION pgcrypto;

CREATE EXTENSION pgcrypto;

CREATE TABLE user_profile (
user_profile_id SERIAL,
user_profile_username VARCHAR(30) NOT NULL UNIQUE,
user_profile_password VARCHAR NOT NULL,
user_profile_name VARCHAR(30) NOT NULL UNIQUE,
user_profile_about VARCHAR(500),
user_profile_photo BYTEA,
PRIMARY KEY (user_profile_id)
);


CREATE TABLE user_post (
user_post_id SERIAL,
user_post_author INT NOT NULL,
user_post_text VARCHAR(500),
user_post_image BYTEA,
user_post_video_link VARCHAR(500),
PRIMARY KEY (user_post_id )
);

CREATE TABLE post_comment (
post_comment_id SERIAL,
post_comment_author INT NOT NULL,
post_comment_text VARCHAR(500) NOT NULL,
post_comment_post_id INT NOT NULL,
PRIMARY KEY (post_comment_id),
CONSTRAINT post_comment_author FOREIGN KEY (post_comment_author)
REFERENCES user_profile (user_profile_id),
CONSTRAINT post_comment_post_id FOREIGN KEY (post_comment_post_id)
REFERENCES user_post (user_post_id) ON DELETE CASCADE
);

CREATE TABLE post_like (
post_like_id SERIAL,
post_like_author INT NOT NULL,
post_like_post_id INT NOT NULL,
PRIMARY KEY (post_like_id),
CONSTRAINT post_like_author FOREIGN KEY (post_like_author)
REFERENCES user_profile (user_profile_id),
CONSTRAINT post_like_post_id FOREIGN KEY (post_like_post_id)
REFERENCES user_post (user_post_id) ON DELETE CASCADE
);


SELECT user_post.user_post_id, post_like.post_like_id, post_like.post_like_author
FROM user_post 
RIGHT JOIN post_like ON  user_post.user_post_id =  post_like.post_like_post_id;

SELECT user_post.user_post_id, post_comment.post_comment_id, post_comment.post_comment_author, post_comment.post_comment_text
FROM user_post 
RIGHT JOIN post_comment ON  user_post.user_post_id = post_comment.post_comment_post_id;


--INSERT INTO user_profile VALUES (DEFAULT, 'user_profile_username',  crypt('user_profile_password', gen_salt('bf')), 'user_profile_name', 'user_profile_about', DEFAULT);
--INSERT INTO user_post VALUES (DEFAULT, 2, 'user_post_text ', DEFAULT, DEFAULT);
--INSERT INTO post_comment VALUES(DEFAULT, 2, 'post_comment_text ', 2);
--INSERT INTO post_comment VALUES(DEFAULT, 2, 'post_comment_text ', 2);



SELECT * FROM user_profile;
SELECT * FROM user_post;
SELECT * FROM post_comment;
SELECT * FROM post_like;




--optional
--direct
--global_chat



