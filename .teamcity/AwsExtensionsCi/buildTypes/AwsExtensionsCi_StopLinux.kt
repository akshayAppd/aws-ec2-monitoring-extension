package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.exec
import jetbrains.buildServer.configs.kotlin.v2017_2.triggers.vcs

/**
 * @author Akshay Srivastava
 */
object AwsExtensionsCi_StopLinux : BuildType({
    uuid = "e9cefadd-4c23-4fea-961c-f76b9af201e9"
    id = "AwsExtensionsCi_StopLinux"
    name = "Stop Linux docker"

    vcs {
        root(AwsExtensionsCi.vcsRoots.AwsExtensionsCi_AkshayGitHub)

    }

    steps {
        exec {
            path = "make"
            arguments = "dockerStop"
        }
    }

    /*dependencies {
        dependency(AwsExtensionsCi_IntegrationTestInLinux) {
            snapshot {

            }
        }
    }*/

    triggers {
        vcs {
        }
    }
})