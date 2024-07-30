plugins {
    java
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.mariadb.jdbc:mariadb-java-client:2.7.2")
    implementation("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation ("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly ("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
