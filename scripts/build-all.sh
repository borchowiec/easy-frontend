#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BASE_DIR="$SCRIPT_DIR/.."
DOCKER_DIR="$BASE_DIR/docker"
TARGET_DIR="$BASE_DIR/target"
PACKAGES_DIR="$TARGET_DIR/packages"

cd "$BASE_DIR"

echo "#############################################################"
echo "#### Building jar"
echo "#############################################################"
mvn clean package
mkdir -p "$PACKAGES_DIR"
ARTIFACT_NAME=`ls "${TARGET_DIR}" | grep .jar | grep -v with-dependencies | sed 's/.\{4\}$//'`
cp "$TARGET_DIR/${ARTIFACT_NAME}-jar-with-dependencies.jar" "$PACKAGES_DIR/${ARTIFACT_NAME}.jar"

echo "#############################################################"
echo "#### Building native image for linux x64"
echo "#############################################################"
docker build --load -t easy-frontend-build-linux-x64 "$DOCKER_DIR/linux/x64"
docker run --rm -v "$BASE_DIR:/build" -e POSTFIX=linux-x64 easy-frontend-build-linux-x64
