FROM openjdk:19-jdk-alpine as build

WORKDIR /back_end

COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw clean install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:19-jdk-alpine

ARG DEPENDENCY=/back_end/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /back_end/lib
COPY --from=build ${DEPENDENCY}/META-INF /back_end/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /back_end

ENTRYPOINT ["java", "-cp", "back_end:back_end/lib/*", "com.utcn.ds2022_30643_moldovan_andrei_1_backend.Ds202230643MoldovanAndrei1BackendApplication", "CREATE_SEED"]

#docker run -it -p 8080:8080 spring-back-end /sh
#winpty !!