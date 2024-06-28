-- for 온프레미스 start
create role sb_db with
    login
    nosuperuser
    inherit
    createdb
    nocreaterole
    noreplication
    PASSWORD 'sb1234!';

create tablespace sb_db
    owner sb_db
    location 'C:\projects\sb_db\tablespace';
    -- linux의 경우 경로를 변경한다.

create database sb_db
    with
    owner = sb_db
    encoding = 'UTF8'
    tablespace = sb_db;
-- for 온프레미스 end

CREATE TABLE tm_user (
	id bigserial NOT NULL,
	birthday varchar(255) NULL,
	email varchar(255) NULL unique,
	"name" varchar(255) NULL,
	"password" varchar(255) NULL,
	phone varchar(255) NULL,
	profile_img varchar(255) NULL default 'https://ssl.pstatic.net/static/pwe/address/img_profile.png',
	"role" varchar(255) NULL,
	user_id varchar(255) NOT NULL,
	CONSTRAINT tm_user_pkey PRIMARY KEY (id)
);

CREATE TABLE tm_account (
	id bigserial NOT NULL,
	category varchar(255) NOT NULL,
	created_date timestamp(6) NULL,
	"date" date NOT NULL,
	memo varchar(255) NOT NULL,
	modified_date timestamp(6) NULL,
	"money" int8 NOT NULL,
	"time" varchar(255) NOT NULL,
	"type" varchar(255) NOT NULL,
	user_id int4 NOT NULL,
	CONSTRAINT tm_account_pkey PRIMARY KEY (id)
);
