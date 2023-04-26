plugins {
    id("java")
}

group = "org.example"

repositories {
    mavenCentral()
}

dependencies {
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
