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
    implementation("org.controlsfx:controlsfx:11.2.1")
    implementation("com.dlsc.formsfx:formsfx-core:11.6.0")
    implementation("net.synedra:validatorfx:0.5.0")
    implementation("org.kordamp.ikonli:ikonli-javafx:12.3.1")
    implementation("org.kordamp.bootstrapfx:bootstrapfx-core:0.4.0")
    implementation("eu.hansolo:tilesfx:${hansloVersion}")
    implementation("com.github.almasb:fxgl:21.1")

    implementation("eu.hansolo.fx:countries:${hansloVersion}")
    implementation("eu.hansolo.fx:heatmap:${hansloVersion}")
    implementation("eu.hansolo:toolbox:${hansloVersion}")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks {
    test {
        useJUnitPlatform()
    }

//    create<Zip>("jlinkZip") {
//        group = "distribution"
//    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}

//configure<org.beryx.jlink.data.JlinkExtension> {
//    imageZip.set(project.file("${buildDir}/distributions/app-${project.extensions.getByType(org.openjfx.gradle.JavaFXOptions::class.java).platformClassifier.get()}.zip"))
//    options = listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
//    launcher {
//        name = "app"
//    }
//}