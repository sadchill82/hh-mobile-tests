plugins {
    java
    id("io.qameta.allure") version "4.0.2"
}

group = "guru.qa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val appiumVersion = "9.3.0"
val seleniumVersion = "4.25.0"
val allureVersion = "2.29.0"
val junitVersion = "5.11.4"
val ownerVersion = "1.0.12"
val lombokVersion = "1.18.34"
val slf4jVersion = "2.0.16"
val assertJVersion = "3.26.3"
val aspectJVersion = "1.9.22.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

val agent: Configuration by configurations.creating

dependencies {
    testImplementation("io.appium:java-client:$appiumVersion")
    testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumVersion")

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("io.qameta.allure:allure-junit5:$allureVersion")

    testImplementation("org.aeonbits.owner:owner:$ownerVersion")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("org.slf4j:slf4j-simple:$slf4jVersion")

    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    agent("org.aspectj:aspectjweaver:$aspectJVersion")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    val configurableProps = setOf("env", "deviceName", "platformVersion", "app", "BROWSERSTACK_USERNAME", "BROWSERSTACK_ACCESS_KEY", "BROWSERSTACK_APP_URL")
    systemProperties(
        System.getProperties()
            .filterKeys { it is String && configurableProps.contains(it as String) }
            .mapKeys { it.key.toString() }
    )

    jvmArgs("-Dfile.encoding=UTF-8")

    doFirst {
        jvmArgs("-javaagent:${agent.singleFile}")
    }

    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = false
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}
