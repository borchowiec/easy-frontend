FROM mwaeckerlin/mingw

ARG download_url="https://download.oracle.com/graalvm/17/latest/graalvm-jdk-17_windows-x64_bin.zip"
ARG download_path="/graalvm.zip"

RUN apt-get update -y && \
    apt-get install curl unzip -y
#    apt-get install curl build-essential libz-dev zlib1g-dev -y

RUN curl -L "$download_url" -o "$download_path" && \
    unzip "$download_path" -d "/" && \
    rm "$download_path" && \
    mv /graalvm* "/graalvm" && \
    /graalvm/bin/gu.cmd install native-image

WORKDIR /build

CMD ["/build/scripts/build-native-image.sh"]