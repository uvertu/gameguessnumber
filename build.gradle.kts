// Root project: aggregates frontend (JavaFX) and backend (Spring Boot) subprojects.

allprojects {
    group = "com.game"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    // Keep consistent encoding across modules
    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }
}
