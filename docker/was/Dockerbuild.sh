#!/bin/sh

# 변수선언
_image_name="sb_was"

echo _image_name = "${_image_name}"

# docker image delete
docker rmi "${_image_name}"

# docker build
docker build -t "${_image_name}" .

# Image tag
docker tag "${_image_name}" "${_image_name}":latest