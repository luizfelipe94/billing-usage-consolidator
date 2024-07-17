FROM openjdk:17-jdk-alpine AS current
WORKDIR /app
RUN apk add libstdc++6 libstdc++
ENV APP_NAME billing-usage-consolidator
COPY . .
RUN ./mvnw clean install -DskipTests
# FROM openjdk:17-jdk-alpine AS current
# WORKDIR /app
# RUN ls /app/target
# COPY --from=build ./app/target/billing-usage-consolidator.jar  .
CMD java -jar /app/target/billing-usage-consolidator.jar

