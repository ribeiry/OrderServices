# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy AS ORDERSERVICE

# Diretório de trabalho no contêiner
WORKDIR /app

ENV JAVA_OPTS = "-Xmx2G -Xms512m -XX:+UseCGroupMemoryLimitForHeap+UseG1GC"

# Copie o arquivo JAR do seu aplicativo para o contêiner
COPY /app/.mvn/ .mvn
COPY /app/mvnw ./
COPY /app/pom.xml ./

RUN ./mvnw dependency:resolve

COPY ./app/src ./app/src

EXPOSE 8081

# Comando para executar o aplicativo Java (substitua com sua classe principal)
CMD ["./mvnw", "spring-boot:run"]