FROM openjdk:17
ADD ./docker-multimart.jar docker-multimart.jar
ENTRYPOINT ["java","jar","docker-multimart.jar"]