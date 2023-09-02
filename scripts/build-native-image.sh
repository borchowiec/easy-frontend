#!/bin/bash

set -e

ARTIFACT_NAME=`ls "/build/target" | grep .jar | grep -v graalvm | grep -v original | sed 's/.\{4\}$//'`

/graalvm/bin/native-image \
  --no-server \
  --no-fallback \
  --language:js \
  -jar "/build/target/$ARTIFACT_NAME-graalvm.jar" \
  -H:Name="$ARTIFACT_NAME-$POSTFIX" \
  -H:Path="/build/target/packages" \
  -H:IncludeResources='.*' \
  --initialize-at-build-time=ch.qos.logback \
  -H:ReflectionConfigurationFiles=reflection-config.json
