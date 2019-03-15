package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.dockerCompose

object AwsExtensionsCi_SetupLinuxEnvironment : BuildType({
    uuid = "be445f3f-f3de-4caa-9921-050f4360c464"
    name = "Setup Linux Environment"

    vcs {
        root(AwsExtensionsCi.vcsRoots.AwsExtensionsCi_HttpsGithubComAkshayAppdAwsEc2monitoringExtensionCiRefsHeadsMast)
    }

    steps {
        dockerCompose {
            file = "docker-compose.yml"
        }
        dockerCommand {
            commandType = build {
                source = path {
                    path = "Dockerfile"
                }
            }
        }
    }
})
