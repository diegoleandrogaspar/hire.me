FROM openjdk:17-jdk

COPY target/bemobi-1.0-SNAPSHOT.jar /app/desafio.jar

CMD ["java", "-jar", "/app/desafio.jar"]