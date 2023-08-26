#!/bin/bash

set -e

ARTIFACT_NAME=`ls "/build/target/" | grep .jar | grep -v with-dependencies | sed 's/.\{4\}$//'`

/graalvm/bin/native-image \
  --no-server \
  --no-fallback \
  -jar "/build/target/$ARTIFACT_NAME-jar-with-dependencies.jar" \
  -H:Name="$ARTIFACT_NAME-$POSTFIX" \
  -H:Path="/build/target/packages" \
  -H:IncludeResources='.*' \
  --initialize-at-build-time=ch.qos.logback \
  -H:ReflectionConfigurationFiles=reflection-config.json
