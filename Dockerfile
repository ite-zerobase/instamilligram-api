# Base image로 OpenJDK 17 선택
FROM openjdk:17-jdk-alpine

# JAR 파일을 컨테이너로 복사할 위치 지정
ARG JAR_FILE=src/build/libs/instamilligram-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar

# 애플리케이션 포트 열기 (필요에 따라 수정 가능)
EXPOSE 8080

# JAR 파일을 실행하도록 ENTRYPOINT 설정
ENTRYPOINT ["java", "-jar", "/app.jar"]
