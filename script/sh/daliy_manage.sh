#!/bin/bash
DATE=$(date '+%Y%m%d')

echo 'certbot_renew'
/home/script/sh/certbot_renew.sh >> /home/script/log/certbot_renew_${DATE}.log 2>&1
