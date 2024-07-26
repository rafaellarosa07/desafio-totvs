# Etapa 1: Construção
FROM maven:3.9.8-amazoncorretto-21 AS build

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo pom.xml e baixe as dependências do Maven
COPY pom.xml .
RUN mvn dependency:go-offline

# Copie o código fonte para o contêiner
COPY src ./src

# Compile e empacote o aplicativo
RUN mvn package -DskipTests

# Etapa 2: Execução
FROM amazoncorretto:21

# Defina o diretório de trabalho
WORKDIR /app

# Copie o JAR da etapa de construção para o contêiner
COPY --from=build /app/target/*.jar /app/app.jar

# Defina o comando de entrada
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Exponha a porta padrão do Spring Boot
EXPOSE 8080