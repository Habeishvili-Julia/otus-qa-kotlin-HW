plugins {
    kotlin("jvm") version "1.8.21"
    application
    jacoco
    id("io.qameta.allure") version "2.11.2"
}

group = "org.otus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

}

dependencies {
    testImplementation(kotlin("test"))
   // val kotestVersion = "5.6.2"
    runtimeOnly("io.kotest:kotest-framework-api-jvm:5.6.2")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")

//    implementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion") эта библиотека Вами не используетс
//    implementation("io.kotest:kotest-assertions-core:$kotestVersion") эта библиотека Вами не используетс
}

tasks.test {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport", "allureReport")
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}
