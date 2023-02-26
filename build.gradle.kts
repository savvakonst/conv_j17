plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"
var jacksonXmlVersion = "2.14.2"
var lombokVersion = "1.18.26"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonXmlVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    compileOnly("org.projectlombok:lombok:$lombokVersion")
}

tasks.test {
    useJUnitPlatform()
}