plugins {
    id("java")
}

repositories {
    mavenCentral()
}

subprojects {

//    plugins {
//        id("java")
//    }

    apply plugin: "java"

    repositories {
        mavenCentral()
    }

    java {
        sourceCompatibility = JavaVersion.toVersion("17")
    }
}
