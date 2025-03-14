plugins {
    application
    scala
}

val scalaVersion = "2.12.3"

repositories {
    mavenCentral()
}

dependencies {
    // Scala standard library
    implementation("org.scala-lang:scala-library:$scalaVersion")

    // Hadoop core libraries (for working with HDFS and distributed file systems)
    implementation("org.apache.hadoop:hadoop-common:2.7.1")
    implementation("org.apache.hadoop:hadoop-hdfs:2.7.1")

    // Parquet libraries (for handling columnar storage format)
    implementation("org.apache.parquet:parquet-common:1.11.1")
    implementation("org.apache.parquet:parquet-avro:1.11.1")

    // Testing dependencies
    testImplementation("org.scalatest:scalatest_2.12:3.0.5")
    testImplementation("com.github.pathikrit:better-files_2.12:3.8.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.typelevel:simulacrum_2.12:1.0.0")
}

// Customize Scala compiler options
tasks.withType<ScalaCompile> {
    //The -Ypartial-unification flag is a Scala compiler option introduced in Scala 2.12
    //Allows Functional Programming (FP) Libraries like Cats, Scalaz, and Shapeless to work more smoothly.
    //Improves Type Inference when dealing with monads, functors, and type constructors.
    //Removes Boilerplate by reducing the need for manual type annotations.
    scalaCompileOptions.additionalParameters = listOf("-Ypartial-unification")
}

// Configure the JAR task to include Git commit metadata in the manifest file
tasks.jar {
    manifest {
        attributes(
                "git_last_commit" to "git rev-parse HEAD".runCommand(), // Store last Git commit hash
                "git_last_message" to "git log -1 --pretty=%B".runCommand().replace("\n", "") // Store last commit message
        )
    }
}

// Helper function to run shell commands and return output as a string
fun String.runCommand(): String = ProcessBuilder("bash", "-c", this).start().inputStream.bufferedReader().readText().trim()

// Configure test task settings
tasks.withType<Test> {
    minHeapSize = "512m"  // Minimum heap size
    maxHeapSize = "2g"    // Maximum heap size
    useJUnitPlatform() // Use JUnit 5 platform
    testLogging {
        events("PASSED", "FAILED", "SKIPPED")
    }
}

application {
    mainClass.set("com.tfedorov.PrintManifestApp") // Specify your main class
}