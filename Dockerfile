FROM eclipse-temurin:17-jdk-focal AS builder

COPY . .
RUN ./mvnw verify
RUN curl --location --output pyroscope-otel.jar https://repo1.maven.org/maven2/io/pyroscope/otel/0.10.1.3/otel-0.10.1.3.jar
RUN curl --location --output opentelemetry-javaagent.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.17.0/opentelemetry-javaagent.jar

FROM eclipse-temurin:17-focal

COPY --from=builder target/pyrotel-1.0-SNAPSHOT.jar /app/pyrotel.jar
COPY --from=builder pyroscope-otel.jar /app/pyroscope-otel.jar
COPY --from=builder opentelemetry-javaagent.jar /app/opentelemetry-javaagent.jar

ENTRYPOINT [ \
    "java", \
    "-javaagent:/app/opentelemetry-javaagent.jar", \
    "-Dotel.javaagent.extensions=/app/pyroscope-otel.jar", \
    "-Dotel.pyroscope.start.profiling=true", \
    "-Dpyroscope.application.name=mode.pyrotel", \
    "-Dpyroscope.format=jfr", \
    "-Dpyroscope.profiler.event=itimer", \
    "-Dpyroscope.server.address=http://pyroscope-flamingo.beta.local:4040", \
    "-Dotel.pyroscope.endpoint=http://localhost:4040", \
    "-jar", \
    "/app/pyrotel.jar"]
CMD ["server", "config/service.yml"]
