# Use an OpenJDK 17 base image
FROM openjdk:17 AS builder

# Get build arguments
ARG TZ
ARG VERSION
ARG SERVICE_NAME=authService

# Set timezone
ENV TZ=${TZ}

# Set the working directory
WORKDIR /app

# Copy JAR file
COPY build/libs/authService-${VERSION}.jar /app/app.jar

# Generate the class list
RUN java -Xshare:off -XX:DumpLoadedClassList=classes.lst -jar app.jar --spring.main.lazy-initialization=true || true

# Create the AppCDS archive with a unique name
RUN java -Xshare:dump -XX:SharedClassListFile=classes.lst -XX:SharedArchiveFile=${SERVICE_NAME}-cds.jsa -jar app.jar || true

# Use a clean JDK runtime for the final image
FROM openjdk:17

WORKDIR /app

# Get build arguments
ARG TZ
ARG VERSION
ARG SERVICE_NAME=authService

# Set timezone
ENV TZ=${TZ}

# Copy JAR file and AppCDS archive from the builder stage
COPY --from=builder /app/app.jar /app/app.jar
COPY --from=builder /app/${SERVICE_NAME}-cds.jsa /app/${SERVICE_NAME}-cds.jsa

# Expose the service port
EXPOSE 8081

# Set minimum and maximum heap memory and enable AppCDS
ENV JAVA_OPTS="-Xms512m -Xmx2g -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -Xshare:on -XX:SharedArchiveFile=/app/${SERVICE_NAME}-cds.jsa -Duser.timezone=Asia/Singapore"

# Start the application with AppCDS optimization
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]