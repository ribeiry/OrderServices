# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy AS ORDERSERVICE

# Diretório de trabalho no contêiner
WORKDIR /app

# Copie o arquivo JAR do seu aplicativo para o contêiner
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:resolve

COPY src ./src

EXPOSE 8081

# Comando para executar o aplicativo Java (substitua com sua classe principal)
CMD ["./mvnw", "spring-boot:run"]
