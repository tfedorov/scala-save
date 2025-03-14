plugins {
    id ("idea")
    scala
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
