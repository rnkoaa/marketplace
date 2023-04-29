plugins {
    id("java")
}

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java") // needed to publish jar files

    repositories {
        mavenCentral()
    }

    tasks.withType<Test>{
        useJUnitPlatform()
    }

    java {
        sourceCompatibility = JavaVersion.toVersion("20")
    }
}
