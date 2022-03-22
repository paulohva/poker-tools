FROM maven:3.8.4-openjdk-8
COPY . /usr/src/poker-tools
WORKDIR /usr/src/poker-tools

RUN mvn install