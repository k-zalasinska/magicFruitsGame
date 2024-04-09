plugins {
    id("java")
    id("application")
    id("org.javamodularity.moduleplugin") version "1.8.15"
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.beryx.jlink") version "3.0.1"
}

group = "com.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

application {
    mainModule.set("com.example.magicfruitsgame")
    mainClass.set("com.example.magicfruitsgame.HelloApplication")
}

javafx {
    version = "21.0.2"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.web", "javafx.media")
}

val junitVersion = "5.9.2"
val hansloVersion = "21.0.3"

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

}

dependencies {
    implementation ("org.slf4j:jul-to-slf4j:2.1.0-alpha1")
    implementation ("org.slf4j:jul-to-slf4j:2.1.0-alpha1")
}


tasks {
    test {
        useJUnitPlatform()
    }
    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/resources")
        }
    }
}


