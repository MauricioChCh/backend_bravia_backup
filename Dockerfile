FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

ARG JAR_FILE=build/libs/*.jar
# Copia el archivo JAR a ese directorio
COPY ${JAR_FILE} app.jar

# Expone el puerto que usará la app
EXPOSE 8080

# Ejecuta la app
CMD ["java", "-jar", "app.jar"]
