#!/usr/bin/env bash

set -e

psql \
  -v ON_ERROR_STOP=1 \
  --username "$POSTGRES_USER" \
  --dbname "$POSTGRES_DB" \
  --set=board_db_user="$BOARD_DB_USER" \
  --set=board_db_password="$BOARD_DB_PASSWORD" \
  --set=media_db_user="$MEDIA_DB_USER" \
  --set=media_db_password="$MEDIA_DB_PASSWORD" <<'EOSQL'

CREATE USER :"board_db_user"
    WITH PASSWORD :'board_db_password';

CREATE USER :"media_db_user"
    WITH PASSWORD :'media_db_password';

EOSQL