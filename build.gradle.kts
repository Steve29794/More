plugins {
    id("java")
}

group = "org.steve29794"
version = "1.0.0"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.extendedclip.com/releases/") }
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("io.papermc.paper:paper-api:1.20-R0.1-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}
