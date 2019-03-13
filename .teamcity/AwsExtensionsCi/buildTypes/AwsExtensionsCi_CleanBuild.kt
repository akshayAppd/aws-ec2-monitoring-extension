package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2017_2.triggers.vcs

/**
 * @author Akshay Srivastava
 */
object AwsExtensionsCi_CleanBuild : BuildType({
    uuid = "b2cef89d-4a7c-46b8-b7f3-86126bd845dc5"
    id = "AwsExtensionsCi_CleanBuild"
    name = "CleanBuild"

    vcs {
        root(ExtensionsTest_AWS_EC2MonitoringExtension.vcsRoots.ExtensionsTest_AWS_EC2MonitoringExtension_AkshayGithub)
    }

    steps {
        maven {
            goals = "clean install -Pno-integration-tests"
            mavenVersion = defaultProvidedVersion()
            jdkHome = "%env.JDK_18%"
        }
    }

    triggers {
        vcs {
        }
    }
})
