import org.apache.tools.ant.filters.ReplaceTokens


plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.8"
}

group = "me.harry0198.infoheads"
version = "2.5.0"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
var libsBase = "me.harry0198.infoheads.libs"

repositories {
    mavenCentral()

    maven {
        url = uri("https://libraries.minecraft.net/")
    }

    maven {
        name = "paper"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        url = uri("https://jitpack.io")
    }

    maven {
        url = uri("https://repo.codemc.org/repository/maven-public")
    }

    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

dependencies {
    compileOnly("com.mojang:authlib:6.0.54")
    compileOnly("dev.folia:folia-api:1.20.6-R0.1-SNAPSHOT")

    implementation("org.jetbrains:annotations:13.0")
    implementation("org.bstats:bstats-bukkit:3.0.2")


    implementation(project(":core"))
    implementation(project(":spigot"))


    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
    shadowJar {
        archiveFileName = "InfoHeads-${project.name}-${version}.jar"
        relocate("me.harry0198.infoheads.core", "${libsBase}.core")
        relocate("org.bstats","${libsBase}.bstats")
        manifest {
            attributes["paperweight-mappings-namespace"] = "mojang"
        }
    }
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // Explicitly add Folia's plugin.yml
    from(project(":folia").file("src/main/resources")) {
        include("plugin.yml")
        filter<ReplaceTokens>("tokens" to mapOf("version" to version))
    }

    from(sourceSets.main.get().resources.srcDirs) {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        filter<ReplaceTokens>("tokens" to mapOf("version" to version))
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.build {
    dependsOn(tasks.shadowJar)
}