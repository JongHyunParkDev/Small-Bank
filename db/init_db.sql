create role sppd with
    login
    nosuperuser
    inherit
    createdb
    nocreaterole
    noreplication
    PASSWORD 'sppd1234!';

create tablespace sppd
    owner sppd
    location 'C:\projects\sppd\tablespace';
    -- linux의 경우 경로를 변경한다.

create database sppd
    with
    owner = sppd
    encoding = 'UTF8'
    tablespace = sppd;
