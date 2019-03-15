package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.dockerCommand

object AwsExtensionsCi_SetupLinuxEnvironment : BuildType({
    uuid = "be445f3f-f3de-4caa-9921-050f4360c464"
    name = "Setup Linux Environment"

    vcs {
        root(AwsExtensionsCi.vcsRoots.AwsExtensionsCi_HttpsGithubComAkshayAppdAwsEc2monitoringExtensionCiRefsHeadsMast)
    }

    steps {
        dockerCommand {
            commandType = build {
                source = path {
                    path = "Dockerfile"
                }
            }
        }
    }
})
