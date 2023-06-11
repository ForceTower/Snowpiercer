plugins {
    kotlin("jvm") version "1.8.22"
    `maven-publish`
    signing
}

val groupName = "dev.forcetower.unes"
val baseName = "snowpiercer"
val artifactVersion = "1.4.0"

group = groupName
version = artifactVersion

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of("11"))
    }
}

val sourcesJar = task<Jar> ("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar = task<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    val task = project.tasks["javadoc"] as Javadoc
    from(task.destinationDir)
    dependsOn(task)
}

artifacts {
    archives(sourcesJar)
    archives(javadocJar)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.7.1")

    testImplementation(kotlin("test-junit"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = groupName
            artifactId = baseName
            version = artifactVersion

            pom {
                name.set("Snowpiercer")
                description.set("Breaks through the snow!")
                url.set("http://github.com/ForceTower/Snowpiercer")
                from(components["java"])

                licenses {
                    license {
                        name.set("General Public License v3.0")
                        url.set("https://www.gnu.org/licenses/gpl-3.0.html")
                    }
                }

                developers {
                    developer {
                        id.set("forcetower")
                        name.set("João Paulo Sena")
                        email.set("joaopaulo761@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/ForceTower/Snowpiercer.git")
                    developerConnection.set("scm:git:ssh://github.com/ForceTower/Snowpiercer.git")
                    url.set("http://unes.forcetower.dev/snowpiercer")
                }
            }

            artifact(sourcesJar) {
                classifier = "sources"
            }

            artifact(javadocJar) {
                classifier = "javadoc"
            }
        }
    }

    repositories {
        maven {
            val sonatypeUsername = project.findProperty("sonatypeUsername") as String? ?: System.getenv("SONATYPE_USERNAME") ?: System.getenv("sonatypeUsername")
            val sonatypePassword = project.findProperty("sonatypePassword") as String? ?: System.getenv("SONATYPE_PASSWORD") ?: System.getenv("sonatypePassword")
            setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2")
            name = "maven"
            credentials {
                username = sonatypeUsername
                password = sonatypePassword
            }
        }
        maven {
            name = "GitHubPackages"
            setUrl("https://maven.pkg.github.com/ForceTower/Snowpiercer")
            credentials {
                username = "ForceTower"
                password = project.findProperty("GITHUB_TOKEN") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}
