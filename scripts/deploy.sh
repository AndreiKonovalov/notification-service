#!/usr/bin/env bash
set -e

NAMESPACE=fintech
IMAGE_NAME=notification-service
IMAGE_TAG=local

echo "===> Build application"
./gradlew clean build

echo "===> Build docker image"
docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .

echo "===> Ensure namespace exists"
kubectl get ns ${NAMESPACE} >/dev/null 2>&1 || kubectl create ns ${NAMESPACE}

echo "===> Install Strimzi Operator (local manifest)"
kubectl apply -n ${NAMESPACE} -f infra/strimzi/install.yaml

echo "===> Wait for Strimzi CRDs"
kubectl wait crd/kafkas.kafka.strimzi.io \
  --for=condition=Established \
  --timeout=120s

echo "===> Deploy Kafka"
kubectl apply -n ${NAMESPACE} -f infra/kafka/kafka.yaml
kubectl apply -n ${NAMESPACE} -f infra/kafka/nodepool.yaml
kubectl apply -n ${NAMESPACE} -f infra/kafka/topics/notification-events.yaml

echo "===> Wait for Kafka Ready"
kubectl wait kafka/fintech-kafka -n ${NAMESPACE} \
  --for=condition=Ready \
  --timeout=180s

echo "===> Add Helm repos (Kafka UI)"
helm repo add kafka-ui https://provectus.github.io/kafka-ui-charts || true
helm repo update

echo "===> Deploy Kafka UI"
helm upgrade --install kafka-ui kafka-ui/kafka-ui \
  -n ${NAMESPACE} \
  -f infra/kafka-ui/values.yaml

echo "===> Deploy notification service"
helm upgrade --install notification ./helm \
  -n ${NAMESPACE} \
  --set image.repository=${IMAGE_NAME} \
  --set image.tag=${IMAGE_TAG}

echo "===> Done"
kubectl get pods -n ${NAMESPACE}
