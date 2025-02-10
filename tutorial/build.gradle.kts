plugins {
    scala
    `java-library`
}

val scalaVersion = "2.12.3"
val catsVersion = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:$scalaVersion")

    testImplementation("org.scalatest:scalatest_2.12:3.0.5")
    testImplementation("com.github.pathikrit:better-files_2.12:3.8.0")
    testImplementation("org.typelevel:cats-mtl-core_2.12:0.2.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")

}

tasks.withType<Test> {
    useJUnitPlatform() // Use JUnit 5 platform
    testLogging {
        events("PASSED", "FAILED", "SKIPPED")
    }
}
tasks.withType<ScalaCompile> {
    scalaCompileOptions.additionalParameters = listOf("-Ypartial-unification")
}
