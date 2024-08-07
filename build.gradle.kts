plugins {
    id("java")
    id("application") // Provides the application:run Gradle target
}

group = "net.mythoclast"
version = "7.7"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    sourceCompatibility = JavaVersion.VERSION_22
}

application {
    mainClass = "net.mythoclast.tooltime.ToolTime"
}

tasks {
    // The only thing going on in here is pre-setting compiler and/or JVM arguments to enable language
    // preview features without complaining. This is so someone not initiated with the codebase can build right away
    compileJava {
        options.compilerArgs.add("--enable-preview")
    }

    compileTestJava {
        options.compilerArgs.add("--enable-preview")
    }

    application {
        run.get().jvmArgs("--enable-preview")
    }

    javadoc {
        (options as CoreJavadocOptions).run {
            addStringOption("source", "22")
            addStringOption("Xdoclint:none", "-quiet")
            addBooleanOption("-enable-preview", true)
        }
    }

    test {
        jvmArgs("--enable-preview")
        useJUnitPlatform()
    }
}
