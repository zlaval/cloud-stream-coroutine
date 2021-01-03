import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.4.21"
    id("idea")
    id("org.jmailen.kotlinter") version "3.3.0"
    id("org.springframework.boot") version "2.5.0-SNAPSHOT"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("com.adarshr.test-logger") version "2.1.1"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
}

kotlinter {
    ignoreFailures = false
    disabledRules = arrayOf("import-ordering", "filename")
}

group = "com.zlrx.cloud"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

extra["springCloudVersion"] = "2020.0.1-SNAPSHOT"

configurations.forEach {
    it.exclude(module = "mockito-core")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.12.0")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework.cloud:spring-cloud-stream")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-rabbit")
    implementation("org.springframework.cloud:spring-cloud-function-kotlin")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("io.mockk:mockk:1.10.4")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
    testImplementation("org.awaitility:awaitility:4.0.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testImplementation("org.testcontainers:testcontainers:1.15.1")
    testImplementation("org.testcontainers:junit-jupiter:1.15.1")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileKotlin { dependsOn(tasks.lintKotlin) }
tasks.lintKotlin { dependsOn(tasks.formatKotlin) }