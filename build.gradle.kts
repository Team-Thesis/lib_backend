import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
}

group = "sru.edu"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven("https://repo.spring.io/milestone")}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    // reactive web with kotlin
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    //runtimeOnly("io.r2dbc:r2dbc-mysql")
    implementation("io.r2dbc:r2dbc-spi:1.0.0.RELEASE")
    implementation("io.asyncer:r2dbc-mysql:1.1.3")
    runtimeOnly("com.mysql:mysql-connector-j")
    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
