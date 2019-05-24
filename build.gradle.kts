plugins {
    `java-library`
    id("net.researchgate.release") version "2.8.0"
    id("com.techshroom.incise-blue") version "0.3.14"
}

inciseBlue {
    util {
        javaVersion = JavaVersion.VERSION_1_8
    }
    maven {
        coords("kenzierocks", "note-orius")
        projectDescription = "DAW (Digital Artist Workspace) for Java coders."
    }
    license()
    ide()
}

configurations.all {
    resolutionStrategy {
        failOnVersionConflict()

        force(
                "com.google.guava:guava:23.0",
                "com.google.code.findbugs:jsr305:3.0.1",
                "org.slf4j:slf4j-api:1.7.26"
        )
    }
}

dependencies {
    implementation(group = "org.slf4j", name = "slf4j-api", version = "1.7.26")
    implementation(group = "com.flowpowered", name = "flow-math", version = "1.0.3")

    api(group = "com.google.guava", name = "guava", version = "27.1-jre")

    compileOnly(group = "com.techshroom", name = "jsr305-plus", version = "0.0.1")

    compileOnly(group = "com.google.auto.service", name = "auto-service", version = "1.0-rc4")
    annotationProcessor(group = "com.google.auto.service", name = "auto-service", version = "1.0-rc4")
    compileOnly(group = "com.google.auto.value", name = "auto-value-annotations", version = "1.6.5")
    annotationProcessor(group = "com.google.auto.value", name = "auto-value", version = "1.6.5")

    testImplementation(group = "junit", name = "junit", version = "4.12")
    testImplementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
    testImplementation(group = "ch.qos.logback", name = "logback-core", version = "1.2.3")
}
