FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/RevShopP1-0.0.1-SNAPSHOT.jar revshop.jar

EXPOSE 8087

ENTRYPOINT ["java", "-jar", "revshop.jar"]