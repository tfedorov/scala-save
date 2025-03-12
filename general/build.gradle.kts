plugins {
    scala
}

val scalaVersion = "2.12.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:$scalaVersion")
    implementation("org.apache.hadoop:hadoop-common:2.7.1")
    implementation("org.apache.hadoop:hadoop-hdfs:2.7.1")
    implementation("org.apache.parquet:parquet-common:1.11.1")
    implementation("org.apache.parquet:parquet-avro:1.11.1")

    testImplementation("org.scalatest:scalatest_2.12:3.0.5")
    testImplementation("com.github.pathikrit:better-files_2.12:3.8.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.typelevel:simulacrum_2.12:1.0.0")
}

tasks.withType<ScalaCompile> {
    scalaCompileOptions.additionalParameters = listOf("-Ypartial-unification")
}


tasks.withType<Test> {
    minHeapSize = "512m"  // Minimum heap size
    maxHeapSize = "2g"    // Maximum heap size
    useJUnitPlatform() // Use JUnit 5 platform
    testLogging {
        events("PASSED", "FAILED", "SKIPPED")
    }
}