FROM eclipse-temurin:21-jre-alpine

# Carpeta de trabajo dentro del contenedor
WORKDIR /app

# Copia el jar compilado al contenedor
COPY target/*.jar app.jar

# Puerto que expone el contenedor — mismo que tiene inventario
EXPOSE 8083

# Comando para correr la app con perfil dev
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]