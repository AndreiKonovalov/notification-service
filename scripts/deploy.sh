#!/usr/bin/env bash
set -e

NAMESPACE=fintech
RELEASE=notification

helm upgrade --install ${RELEASE} ./helm/notification \
  -n ${NAMESPACE}
