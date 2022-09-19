#!/bin/bash

set -e

docker build -t pyrotel:latest .
docker compose rm --force --stop
docker compose up
