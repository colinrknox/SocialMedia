DROP TABLE post_comments;
DROP TABLE post_likes;
DROP TABLE user_posts;
DROP TABLE user_profiles;

CREATE TABLE user_profiles (
	id SERIAL,
	email VARCHAR(254) NOT NULL UNIQUE,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	"password" BYTEA NOT NULL,
	password_salt BYTEA NOT NULL,
	about VARCHAR(500),
	photo VARCHAR(512),
	creation_date TIMESTAMP NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE user_posts (
	id SERIAL,
	author INT NOT NULL,
	"text" VARCHAR(500) NOT NULL,
	image BYTEA,
	creation_date TIMESTAMP NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (author) REFERENCES user_profiles (id) ON DELETE CASCADE
);


CREATE TABLE post_comments (
	id SERIAL,
	author INT NOT NULL,
	"text" VARCHAR(500) NOT NULL,
	post_id INT NOT NULL,
	creation_date TIMESTAMP NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (author) REFERENCES user_profiles (id),
	FOREIGN KEY (post_id) REFERENCES user_posts (id) ON DELETE CASCADE
);


CREATE TABLE post_likes (
	id SERIAL,
	profile_id INT NOT NULL,
	post_id INT NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (profile_id) REFERENCES user_profiles (id),
	FOREIGN KEY (post_id) REFERENCES user_posts (id) ON DELETE CASCADE
);


SELECT user_posts.id, post_likes.id, post_likes.profile_id
FROM user_posts 
RIGHT JOIN post_likes ON  user_posts.id =  post_likes.post_id;

SELECT user_posts.author, post_comments.id, post_comments.author, post_comments."text"
FROM user_posts
RIGHT JOIN post_comments ON  user_posts.id = post_comments.post_id;


SELECT * FROM user_profiles;
SELECT * FROM user_posts;
SELECT * FROM post_comments;
SELECT * FROM post_likes;


--optional
--direct
--global_chat

