import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.github.cs125-illinois"
version = "2022.6.2"

plugins {
    kotlin("jvm") version "1.7.0"
    `maven-publish`
    id("org.jmailen.kotlinter") version "3.11.1"
    id("com.github.ben-manes.versions") version "0.42.0"
    id("io.gitlab.arturbosch.detekt") version "1.20.0"
}
repositories {
    mavenCentral()
}
dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:5.3.2")
    testImplementation("org.slf4j:slf4j-simple:1.7.36")
}
tasks.dependencyUpdates {
    fun String.isNonStable() = !(
        listOf("RELEASE", "FINAL", "GA").any { toUpperCase().contains(it) }
            || "^[0-9,.v-]+(-r)?$".toRegex().matches(this)
        )
    rejectVersionIf { candidate.version.isNonStable() }
    gradleReleaseChannel = "current"
}
publishing {
    publications {
        create<MavenPublication>("libcs1") {
            from(components["java"])
        }
    }
}
detekt {
    buildUponDefaultConfig = true
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
    enableAssertions = true
    jvmArgs("-ea", "-Xmx1G", "-Xss256k", "-Dfile.encoding=UTF-8")
}
