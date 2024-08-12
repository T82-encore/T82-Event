FROM openjdk:17-slim
COPY ./build/libs/*T.jar app.jar
CMD ["java","-jar","app.jar"]
EXPOSE 8080 9090
