plugins {
    scala
    `java-library`
    id ("idea")
}

val scalaVersion = "2.12.3"
val catsVersion = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:$scalaVersion")
    implementation("org.apache.hadoop:hadoop-common:2.7.1")
    implementation("org.apache.hadoop:hadoop-hdfs:2.7.1")
    implementation("org.apache.parquet:parquet-common:1.11.1")
    implementation("org.apache.parquet:parquet-avro:1.11.1")

    testImplementation("org.typelevel:cats-effect_2.12:3.4.8")
    testImplementation("org.scalatest:scalatest_2.12:3.0.5")
    testImplementation("com.github.pathikrit:better-files_2.12:3.8.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0-RC1")
    testImplementation("org.typelevel:cats-core_2.12:$catsVersion")
    testImplementation("org.typelevel:cats-free_2.12:$catsVersion")
    testImplementation("org.typelevel:cats-mtl-core_2.12:0.2.1")
    testImplementation("org.typelevel:simulacrum_2.12:1.0.0")
}

tasks.withType<ScalaCompile> {
    scalaCompileOptions.additionalParameters = listOf("-Ypartial-unification")
}

tasks.jar {
    manifest {
        attributes(
                "git_last_commit" to "git rev-parse HEAD".runCommand(),
                "git_last_message" to "git log -1 --pretty=%B".runCommand().replace("\n", "")
        )
    }
}

fun String.runCommand(): String =
        ProcessBuilder("bash", "-c", this).start().inputStream.bufferedReader().readText().trim()
