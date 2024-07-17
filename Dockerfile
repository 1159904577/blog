FROM openjdk:17
EXPOSE 8080
ADD blog.jar blog.jar
ADD application.properties application.properties
ENTRYPOINT ["java","-jar","/blog.jar"]
