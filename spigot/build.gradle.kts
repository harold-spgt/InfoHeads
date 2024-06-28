import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.harry0198.infoheads"
version = "2.5.0"
java.sourceCompatibility = JavaVersion.VERSION_17
var libsBase = "me.harry0198.infoheads.libs"

repositories {
    mavenCentral()
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://jitpack.io")
    }

    maven {
        url = uri("https://repo.codemc.org/repository/maven-public")
    }

    maven {
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }

    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.6-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.1")
    compileOnly("com.github.badbones69:Block-Particles:1.11.1")

    implementation("org.jetbrains:annotations:13.0")
    implementation("org.bstats:bstats-bukkit:3.0.2")

    implementation(project(":core"))


    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
    shadowJar {
        archiveFileName = "${project.name}.jar"
        relocate("me.harry0198.infoheads.core", "${libsBase}.mf")
        relocate("org.bstats","${libsBase}.bstats")
    }
}


tasks.test {
    useJUnitPlatform()
}