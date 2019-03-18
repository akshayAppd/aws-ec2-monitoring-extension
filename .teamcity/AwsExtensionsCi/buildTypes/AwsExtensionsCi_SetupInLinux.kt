package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.exec
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2017_2.triggers.vcs

/**
 * @author Akshay Srivastava
 */
object AwsExtensionsCi_SetupInLinux : BuildType({
    uuid = "e931478d-93ee-469b-9b33-edeb583e874e"
    id = "AwsExtensionsCi_SetupInLinux"
    name = "Setup Linux Environment"

    vcs {
        root(AwsExtensionsCi.vcsRoots.AwsExtensionsCi_AkshayGitHub)

    }

    steps {
        exec {
            path = "make"
            arguments = "dockerRun"
        }

        //Waits for 5 minutes to send metrics to the controller
        exec {
            path = "make"
            arguments = "sleep"
        }
    }

    dependencies {
        dependency(AwsExtensionsCi_CleanBuild) {
            snapshot {

            }
        }
    }

    triggers {
        vcs {
        }
    }
})