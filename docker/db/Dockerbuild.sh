#!/bin/sh

# 변수선언
_image_name="sb_db"

echo _image_name = "${_image_name}"

# docker build
docker build --network=host -t "${_image_name}" .

# Image tag
docker tag "${_image_name}" ${_image_name}":latest