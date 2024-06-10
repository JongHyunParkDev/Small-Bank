FROM postgres:15.1

# init DB
ENV POSTGRES_DB=sb_db
ENV POSTGRES_USER=sb_db
ENV POSTGRES_PASSWORD=smallbank1234!

# 한글 지원
ENV POSTGRES_INITDB_ARGS="--encoding=UTF-8 --lc-collate=C"

RUN apt-get update \
        && apt-get install -y --no-install-recommends \
                postgresql-15-postgis-3 \
                postgresql-15-postgis-3-scripts \
        && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /docker-entypoint-initdb.d

# EXTENSION 추가작업
COPY ./initdb-extension.sh /docker-entrypoint-initdb.d/extension.sh