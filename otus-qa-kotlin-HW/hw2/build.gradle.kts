plugins {
    application
    kotlin("jvm")
    id("io.qameta.allure") version "2.11.2"
}

val allureVersion = "2.22.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.21")

    testImplementation("io.qameta.allure:allure-kotlin-model:2.2.4")
    testImplementation("io.qameta.allure:allure-kotlin-commons:2.4.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    implementation("org.slf4j:slf4j-simple:2.0.7")
}

tasks.test {
    useJUnitPlatform()
//    finalizedBy("allureReport")
}

application {
    mainClass.set("MainKt")
}