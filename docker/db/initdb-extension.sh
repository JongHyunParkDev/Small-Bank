#!/bin/sh

set -e

# Load EXTENSION into both template_database and $POSTGRES_DB
for DB in "$POSTGRES_DB"; do
        echo "Loading extensions into $DB"
        psql -U $POSTGRES_USER -d $DB <<-'EOSQL'
                CREATE EXTENSION IF NOT EXISTS postgis;
                CREATE EXTENSION IF NOT EXISTS postgis_topology;
                CREATE EXTENSION IF NOT EXISTS fuzzystrmatch;
                CREATE EXTENSION IF NOT EXISTS postgis_tiger_geocoder;
                create extension pg_stat_statements;
                CREATE EXTENSION pg_trgm;
                CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;
EOSQL
done


# drop schema
psql -U $POSTGRES_USER -d $POSTGRES_DB <<- 'EOSQL'
        DROP SCHEMA tiger CASCADE;
        DROP SCHEMA tiger_data CASCADE;
        DROP SCHEMA topology CASCADE;