FROM ubuntu:bionic

ARG download_url="https://download.oracle.com/graalvm/17/latest/graalvm-jdk-17_linux-x64_bin.tar.gz"
ARG download_path="/graalvm.tar.gz"

RUN apt-get update -y && \
    apt-get install curl build-essential libz-dev zlib1g-dev -y

RUN curl -L "$download_url" -o "$download_path" && \
    tar -xf "$download_path" -C "/" && \
    rm "$download_path" && \
    mv /graalvm* "/graalvm"

RUN /graalvm/bin/gu install native-image && \
    /graalvm/bin/gu install js

WORKDIR /build

CMD ["/build/scripts/build-native-image.sh"]