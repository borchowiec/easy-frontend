#!/bin/bash

set -e

ARTIFACT_NAME=`ls "/build/target/" | grep .jar | grep -v with-dependencies | sed 's/.\{4\}$//'`

/graalvm/bin/native-image \
  --no-server \
  --no-fallback \
  -jar "/build/target/$ARTIFACT_NAME-jar-with-dependencies.jar" \
  -H:Name="$ARTIFACT_NAME-$POSTFIX" \
  -H:Path="/build/target/packages" \
  --initialize-at-build-time=ch.qos.logback.core.joran.spi.DefaultClass \
  --initialize-at-build-time=ch.qos.logback.core.joran.spi.JoranException \
  --initialize-at-build-time=ch.qos.logback.core.util.OptionHelper \
  --initialize-at-build-time=ch.qos.logback.core.util.StatusPrinter \
  --initialize-at-build-time=ch.qos.logback.classic.util.LogbackMDCAdapter \
  --initialize-at-build-time=ch.qos.logback.classic.spi.LoggingEvent \
  --initialize-at-build-time=ch.qos.logback.classic.Logger \
  --initialize-at-build-time=ch.qos.logback.classic.Level \
  --initialize-at-build-time=ch.qos.logback.classic.pattern.MessageConverter \
  --initialize-at-build-time=ch.qos.logback.classic.pattern.ClassOfCallerConverter \
  --initialize-at-build-time=ch.qos.logback.classic.pattern.MethodOfCallerConverter \
  --initialize-at-build-time=ch.qos.logback.classic.pattern.LineOfCallerConverter \
  --initialize-at-build-time=ch.qos.logback.classic.pattern.FileOfCallerConverter \
  --initialize-at-build-time=ch.qos.logback.classic.pattern.LineSeparatorConverter \
  --initialize-at-build-time=ch.qos.logback.classic.pattern.ThreadConverter \
  --initialize-at-build-time=ch.qos.logback.classic.pattern.LevelConverter \
  --initialize-at-build-time=ch.qos.logback.classic.PatternLayout \
  --initialize-at-build-time=ch.qos.logback.classic.spi.ThrowableProxy \
  --initialize-at-build-time=ch.qos.logback.classic.spi.StackTraceElementProxy \
  --initialize-at-build-time=ch.qos.logback.classic.spi.LoggingEventVO \
  --initialize-at-build-time=ch.qos.logback.classic.LoggerContext \
  --initialize-at-build-time=ch.qos.logback.classic.LoggerContextListener \
  --initialize-at-build-time=ch.qos.logback.classic.util.LogbackLock \
  --initialize-at-build-time=org.slf4j.LoggerFactory
