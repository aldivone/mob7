FROM openjdk:8-slim as pre-production
RUN  apt-get update 

FROM pre-production as base
WORKDIR /workspace/app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline 
# --no-transfer-progress 
COPY src src

FROM base as build
RUN --mount=type=cache,target=/root/.m2 ./mvnw -Dmaven.test.skip install 
# --no-transfer-progress 

FROM pre-production as production
VOLUME /app
ARG DEPENDENCY=/workspace/app/target/
COPY --from=build /workspace/app/target/*.jar /app/app.jar
RUN unlink /etc/localtime && \
    ln -s /usr/share/zoneinfo/America/Fortaleza /etc/localtime
EXPOSE 8080
ENTRYPOINT ["java","-Duser.timezone=America/Fortaleza","-jar","app/app.jar"]
