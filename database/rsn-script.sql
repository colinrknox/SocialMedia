DROP TABLE post_comments;
DROP TABLE post_likes;
DROP TABLE user_posts;
DROP TABLE user_profiles;
DROP EXTENSION pgcrypto;

CREATE EXTENSION pgcrypto;

CREATE TABLE user_profiles (
	up_id SERIAL,
	up_email VARCHAR(254) NOT NULL UNIQUE,
	up_first_name VARCHAR(50) NOT NULL,
	up_last_name VARCHAR(50) NOT NULL,
	up_pwd VARCHAR NOT NULL,
	up_pwd_salt bytea NOT NULL,
	up_about VARCHAR(500),
	up_photo BYTEA,
	up_creation_date TIMESTAMP,
	PRIMARY KEY (up_id)
);


CREATE TABLE user_posts (
	post_id SERIAL,
	post_up_id INT NOT NULL,
	post_text VARCHAR(500),
	post_image BYTEA,
	PRIMARY KEY (post_id),
	FOREIGN KEY (post_up_id) REFERENCES user_profiles (up_id)
);


CREATE TABLE post_comments (
	post_comment_id SERIAL,
	post_comment_author INT NOT NULL,
	post_comment_text VARCHAR(500) NOT NULL,
	post_comment_post_id INT NOT NULL,
	PRIMARY KEY (post_comment_id),
	FOREIGN KEY (post_comment_author) REFERENCES user_profiles (up_id),
	FOREIGN KEY (post_comment_post_id) REFERENCES user_posts (post_id) ON DELETE CASCADE
);


CREATE TABLE post_likes (
	like_id SERIAL,
	like_up_id INT NOT NULL,
	like_p_id INT NOT NULL,
	PRIMARY KEY (like_id),
	FOREIGN KEY (like_up_id) REFERENCES user_profiles (up_id),
	FOREIGN KEY (like_p_id) REFERENCES user_posts (post_id) ON DELETE CASCADE
);


SELECT user_posts.post_id, post_likes.like_id, post_likes.like_up_id
FROM user_posts 
RIGHT JOIN post_likes ON  user_posts.post_id =  post_likes.like_p_id;

SELECT user_posts.post_up_id, post_comments.post_comment_id, post_comments.post_comment_author, post_comments.post_comment_text
FROM user_posts
RIGHT JOIN post_comments ON  user_posts.post_id = post_comments.post_comment_post_id;


--INSERT INTO user_profile VALUES (DEFAULT, 'user_profile_username',  crypt('user_profile_password', gen_salt('bf')), 'user_profile_name', 'user_profile_about', DEFAULT);
--INSERT INTO user_post VALUES (DEFAULT, 2, 'user_post_text ', DEFAULT, DEFAULT);
--INSERT INTO post_comment VALUES(DEFAULT, 2, 'post_comment_text ', 2);
--INSERT INTO post_comment VALUES(DEFAULT, 2, 'post_comment_text ', 2);


SELECT * FROM user_profiles;
SELECT * FROM user_posts;
SELECT * FROM post_comments;
SELECT * FROM post_likes;


--optional
--direct
--global_chat



