plugins {
    id("java")
}

group = "me.harry0198.infoheads.core"
version = "2.5.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:2.1.0")
}


tasks.test {
    useJUnitPlatform()
    jvmArgs( "--add-opens", "java.base/java.lang=ALL-UNNAMED")
}