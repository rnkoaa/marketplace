plugins {
    id("java")
    id("application")
}

group = "com.richard.marketplace"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":resilience"))
    implementation("org.atteo.classindex:classindex:3.13")
    annotationProcessor("org.atteo.classindex:classindex:3.13") // for gradle 5.0+

    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}
java {
    sourceCompatibility = JavaVersion.VERSION_20
    targetCompatibility = JavaVersion.VERSION_20
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("--enable-preview")
}
tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("--enable-preview")
}
