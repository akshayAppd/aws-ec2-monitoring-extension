package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2017_2.triggers.vcs

/**
 * @author Akshay Srivastava
 */
object AwsExtensionsCi_IntegrationTestInLinux : BuildType({
    uuid = "0aa6257c-7671-4f9c-9ceb-cae9763d353d"
    id = "AwsExtensionsCi_IntegrationTestInLinux"
    name = "IntegrationTest in Linux"

    vcs {
        root(ExtensionsTest_AWS_EC2MonitoringExtension.vcsRoots.ExtensionsTest_AWS_EC2MonitoringExtension_AkshayGithub)

    }

    steps {
        maven {
            goals = "clean install"
            mavenVersion = defaultProvidedVersion()
            jdkHome = "%env.JDK_18%"
        }
    }

    dependencies {
        dependency(ExtensionsTest_AWS_EC2MonitoringExtension_SetupInLinux) {
            snapshot {

            }
        }
    }

    triggers {
        vcs {
        }
    }
})