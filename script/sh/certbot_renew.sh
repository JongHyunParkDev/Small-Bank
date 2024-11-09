#!/bin/bash

PREV_CERT_DATE=$(openssl x509 -in /etc/letsencrypt/live/smallbank.kro.kr/fullchain.pem -noout -enddate)

certbot renew

NEW_CERT_DATE=$(openssl x509 -in /etc/letsencrypt/live/smallbank.kro.kr/fullchain.pem -noout -enddate)

if [ "$PREV_CERT_DATE" != "$NEW_CERT_DATE" ]; then
    cd /etc/letsencrypt/live/smallbank.kro.kr
    openssl pkcs12 -export -inkey privkey.pem  -in fullchain.pem -out keystore.p12 -name tomcat -CAfile chain.pem -caname root -passout pass:sb1234!

    cd /home/docker_admin/docker/small_bank
    docker-compose up -d
fi
