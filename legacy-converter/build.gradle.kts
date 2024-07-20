plugins {
    id("java")
}

group = "me.harry0198.infoheads"
version = "1.0"
java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.google.code.gson:gson:2.11.0")
    testImplementation(project(":core"))
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")

    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    compileOnly("com.google.code.gson:gson:2.11.0")

    implementation(project(":core"))
}

tasks.test {
    useJUnitPlatform()
}