package com.zlrx.cloud.reactivespringcloud

import org.junit.ClassRule
import org.testcontainers.containers.DockerComposeContainer
import java.io.File

private const val DOCKER_FILE = "containers/docker-compose.yml"

open class TestcontainersBase {

    class Containers(file: File) : DockerComposeContainer<Containers>(file)

    companion object {
        @ClassRule
        private val environment = Containers(File(DOCKER_FILE))
            .withLocalCompose(true)
            .start()
    }
}
