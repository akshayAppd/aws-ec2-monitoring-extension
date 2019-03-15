package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.dockerCompose
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs

object AwsExtensionsCi_CleanBuild : BuildType({
    uuid = "b593b361-3cc1-45dc-ad92-ee0f6454fef8"
    name = "Clean Build"

    vcs {
        root(AwsExtensionsCi.vcsRoots.AwsExtensionsCi_HttpsGithubComAkshayAppdAwsEc2monitoringExtensionCiRefsHeadsMast)
    }

    steps {
        dockerCompose {
            file = "docker-compose.yml"
        }
        maven {
            goals = "clean test"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
            mavenVersion = defaultProvidedVersion()
            jdkHome = "%env.JDK_18%"
        }
        dockerCommand {
            commandType = build {
                source = path {
                    path = "Dockerfile"
                }
            }
        }
    }

    triggers {
        vcs {
        }
    }
})
