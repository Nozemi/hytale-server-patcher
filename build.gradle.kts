plugins {
    java
}

group = "io.nozemi.hytale"
version = "1.0-SNAPSHOT"

val gameBuild: String by project
val patchLine: String by project

val serverRunDir = file("${rootProject.projectDir}/dev-server")
val gameBuildDir = file(System.getProperty("user.home") + "/AppData/Roaming/Hytale/install/$patchLine/package/game/$gameBuild")
val serverJar = gameBuildDir.resolve("Server").resolve("HytaleServer.jar")

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(files(serverJar))
    implementation("org.ow2.asm:asm:9.5")
    implementation("org.ow2.asm:asm-commons:9.5")
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}