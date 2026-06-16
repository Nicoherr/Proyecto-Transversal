# Usa Java 21 en versión Alpine (más liviana y rápida)
FROM eclipse-temurin:21-jre-alpine

# Carpeta de trabajo dentro del contenedor
WORKDIR /app

# Copia el jar compilado al contenedor
# El * agarra el jar sin importar el nombre exacto
COPY target/*.jar app.jar

# Puerto que expone el contenedor — mismo que tiene carrito
EXPOSE 8081

# Comando para correr la app con perfil dev
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]