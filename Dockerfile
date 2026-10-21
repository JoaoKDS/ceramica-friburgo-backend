# ===== Etapa 1: compila o projeto (gera o .jar) =====
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Garante que o mvnw funcione mesmo que tenha sido salvo com quebra de linha do Windows
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw
# Baixa as dependências numa etapa própria (deixa os próximos deploys mais rápidos)
RUN ./mvnw -q -B dependency:go-offline

COPY src ./src
RUN ./mvnw -q -B -DskipTests package

# ===== Etapa 2: imagem final, só com o necessário para rodar =====
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
# MaxRAMPercentage evita que o Java use mais memória do que o servidor tem
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
