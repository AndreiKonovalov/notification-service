#!/usr/bin/env bash
set -e

IMAGE_NAME=notification-service
IMAGE_TAG=local

docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .
