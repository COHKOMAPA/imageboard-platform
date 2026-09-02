#!/usr/bin/env bash

set -e

psql \
  -v ON_ERROR_STOP=1 \
  --username "$POSTGRES_USER" \
  --dbname "$POSTGRES_DB" \
  --set=board_db_user="$BOARD_DB_USER" \
  --set=board_db_name="$BOARD_DB_NAME" \
  --set=media_db_user="$MEDIA_DB_USER" \
  --set=media_db_name="$MEDIA_DB_NAME" <<'EOSQL'

CREATE DATABASE :"board_db_name"
    OWNER :"board_db_user";

CREATE DATABASE :"media_db_name"
    OWNER :"media_db_user";

EOSQL