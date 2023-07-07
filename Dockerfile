FROM openjdk:17-alpine AS build
COPY ./ /temp/
RUN sh /temp/gradlew build


FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
COPY --from=build /temp/gateway-service-core/build/libs/*.jar /temp/
CMD export JAR_FILENAME=$(ls /temp | grep .jar); \
    echo "---------------"; \
    echo $JAR_FILENAME; \
    echo "---------------"; \
    /bin/sh -c 'java -jar /temp/$JAR_FILENAME';