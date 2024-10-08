import org.apache.tools.ant.filters.ReplaceTokens


plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("se.solrike.sonarlint") version "2.1.0"
}

group = "me.harry0198.infoheads"
version = "2.5.0"
java {
    sourceCompatibility = JavaVersion.VERSION_17
}
var libsBase = "me.harry0198.infoheads.libs"

repositories {
    mavenCentral()
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://libraries.minecraft.net/")
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

val spigotVersion = "1.21-R0.1-SNAPSHOT"
val papiVersion = "2.11.1"
val authLibVersion = "6.0.54"
val annotationsVersion = "13.0"
val bstatsVersion = "3.0.2"

dependencies {
    compileOnly("org.spigotmc:spigot-api:$spigotVersion")
    compileOnly("me.clip:placeholderapi:$papiVersion")
    compileOnly("com.mojang:authlib:$authLibVersion")

    implementation("org.jetbrains:annotations:$annotationsVersion")
    implementation("org.bstats:bstats-bukkit:$bstatsVersion")


    implementation(project(":core"))
    implementation(project(":legacy-converter"))


    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
    shadowJar {
        archiveFileName = "InfoHeads-${project.name}-${version}.jar"
        relocate("me.harry0198.infoheads.core", "${libsBase}.core")
        relocate("org.bstats","${libsBase}.bstats")
    }
}

tasks.processResources {
    from(sourceSets.main.get().resources.srcDirs) {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        filter<ReplaceTokens>("tokens" to mapOf("version" to version))
    }
}

tasks.sonarlintMain {
    dependencies {
        sonarlintPlugins("org.sonarsource.java:sonar-java-plugin:7.30.1.34514")
    }
    reports {
        create("xml") {
            enabled.set(true)
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.build {
    dependsOn(tasks.shadowJar)
}