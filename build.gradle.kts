plugins {
    java
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

dependencies {
    // Control: a plain declaration, free to move.
    implementation("org.apache.commons:commons-lang3:3.21.0")

    // Strict declaration with newer versions available.
    implementation("commons-io:commons-io") {
        version {
            strictly("2.11.0")
        }
    }

    // Plain in one configuration, strict in another.
    implementation("com.google.guava:guava:32.0.0-jre")
    testImplementation("com.google.guava:guava") {
        version {
            strictly("32.0.0-jre")
        }
    }

    // Pinned through a strict dependency constraint.
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    constraints {
        implementation("com.fasterxml.jackson.core:jackson-databind") {
            version {
                strictly("2.15.2")
            }
        }
    }

    // Strict declaration of a vulnerable version.
    implementation("org.yaml:snakeyaml") {
        version {
            strictly("1.33")
        }
    }

    // Control: a plain declaration, free to move.
    implementation("org.apache.commons:commons-text:1.15.0")

    // Strict parent of a vulnerable transitive.
    implementation("com.squareup.okhttp3:okhttp") {
        version {
            strictly("4.9.3")
        }
    }

    // Security floor declared with strictly(...), recorded in .github/dependency-floors.json.
    implementation("org.apache.commons:commons-compress") {
        version {
            strictly("1.26.0")
        }
        because("CVE-2024-25710: lowest fixed version")
    }
}

apply(from = "inactive-strict.gradle.kts")

tasks.register("resolveAll") {
    doLast {
        configurations.filter { it.isCanBeResolved }.forEach { it.resolve() }
    }
}
tasks.named("check") {
    dependsOn("resolveAll")
}
