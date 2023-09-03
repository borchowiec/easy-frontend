#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BASE_DIR="$SCRIPT_DIR/.."
README_FILE="$BASE_DIR/README.md"

cd "$BASE_DIR" && mvn clean package
JAR_NAME=`ls "$BASE_DIR/target" | grep '.jar$' | grep -v original | grep -v graalvm`
java -jar "$BASE_DIR/target/$JAR_NAME" build
#pandoc -s "$BASE_DIR/target/build/index.html" -o "$README_FILE"
html2markdown "$BASE_DIR/target/build/index.html" > "$README_FILE"
