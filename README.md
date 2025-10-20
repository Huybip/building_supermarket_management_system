# building_supermarket_management_system

Run instructions (Java Spring Boot app):

This repository contains a Spring Boot app under `gs-serving-web-content-main/complete`.

Quick dev run (H2 in-memory):

1. Build

   mvn -f gs-serving-web-content-main/complete/pom.xml -DskipTests package

2. Run with H2 profile

   java -jar -Dspring.profiles.active=h2 gs-serving-web-content-main/complete/target/backend-springboot-0.0.1-SNAPSHOT.jar

   cd /workspaces/building_supermarket_management_system/gs-serving-web-content-main/complete && mvn -Dspring-boot.run.profiles=h2 spring-boot:run -e

Or run from Maven:

   mvn -f gs-serving-web-content-main/complete -Dspring-boot.run.profiles=h2 spring-boot:run

H2 console: http://localhost:8080/h2-console
JDBC URL (default): jdbc:h2:mem:supermarket
User: SA
Password: (empty)

Seeded credentials (H2 profile):

- username: admin
- password: admin123

Connecting to Aiven MySQL (production)

If you want to run the application against an Aiven-managed MySQL instance, add the datasource properties (prefer using environment variables in production):

SPRING_DATASOURCE_URL=jdbc:mysql://<HOST>:<PORT>/<DBNAME>?useSSL=true&verifyServerCertificate=true&requireSSL=true
SPRING_DATASOURCE_USERNAME=<USERNAME>
SPRING_DATASOURCE_PASSWORD=<PASSWORD>

Aiven usually requires TLS. The recommended approach is to import the Aiven CA into a Java truststore and start the JVM with the truststore properties:

keytool -import -alias aiven-ca -file aiven-ca.pem -keystore aiven-truststore.jks -storepass changeit

java -Djavax.net.ssl.trustStore=/path/to/aiven-truststore.jks -Djavax.net.ssl.trustStorePassword=changeit -jar -Dspring.profiles.active=prod path/to/jar

If you cannot configure a truststore immediately, you can use a temporary JDBC URL that relaxes cert verification (NOT recommended for production):

SPRING_DATASOURCE_URL=jdbc:mysql://<HOST>:<PORT>/<DBNAME>?useSSL=true&verifyServerCertificate=false&allowPublicKeyRetrieval=true

Flyway and migrations

This project may include Flyway migrations for production. When switching a database to MySQL for the first time, either:

- Apply the migrations to an empty database (recommended).
- Or set flyway.baselineOnMigrate=true to create a baseline if the DB already has schema.

Notes

- The Thymeleaf templates have been refactored to use a common fragment `fragments/_layout.html`, but some pages may still need small visual adjustments.
- Security: UI uses form login; APIs use JWT. Review role-based access controls if you require stricter authorization per endpoint.

If you want, I can finish converting all templates to the shared layout, add role-based restrictions, or generate a Flyway baseline migration.