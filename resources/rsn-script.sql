DROP TABLE post_comments;
DROP TABLE post_likes;
DROP TABLE user_posts;
DROP TABLE reset_tokens;
DROP TABLE user_profiles;

CREATE TABLE user_profiles (
	id SERIAL,
	email VARCHAR(254) NOT NULL UNIQUE,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	"password" VARCHAR(512) NOT NULL,
	about VARCHAR(500),
	photo VARCHAR(512),
	creation_date TIMESTAMP NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE user_posts (
	id SERIAL,
	author INT NOT NULL,
	"text" VARCHAR(500) NOT NULL,
	image VARCHAR(512),
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
	profile_id INT NOT NULL,
	post_id INT NOT NULL,
	PRIMARY KEY (profile_id, post_id),
	FOREIGN KEY (profile_id) REFERENCES user_profiles (id),
	FOREIGN KEY (post_id) REFERENCES user_posts (id) ON DELETE CASCADE
);

CREATE TABLE reset_tokens (
	uuid VARCHAR(36) PRIMARY KEY,
	profile_id INTEGER NOT NULL UNIQUE,
	FOREIGN KEY (profile_id) REFERENCES user_profiles (id) ON DELETE CASCADE
);


--SELECT * FROM user_profiles;
--SELECT * FROM user_posts;
--SELECT * FROM post_comments;
--SELECT * FROM post_likes;
--SELECT * FROM reset_tokens;

