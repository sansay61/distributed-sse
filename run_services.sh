#!/bin/bash
export KEYCLOAK_USER=admin
export KEYCLOAK_PASSWORD=password
export KEYCLOAK_DATABASE_NAME=keyloak_db
export KEYCLOAK_DATABASE_USER=keycloak_admin
export KEYCLOAK_DATABASE_PASSWORD=keycloak_password
export REDIS_PASSWORD=redis_password

docker-compose -f docker-compose.yml up -d
