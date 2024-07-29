import org.apache.tools.ant.filters.ReplaceTokens


plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.harry0198.infoheads"
version = "2.5.0"
java {
    sourceCompatibility = JavaVersion.VERSION_21
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
    }
}

tasks.processResources {
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