package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2017_2.triggers.vcs

/**
 * @author Akshay Srivastava
 */
object AwsExtensionsCI_IntegrationTestInLinux : BuildType({
    uuid = "0aa6257c-7671-4f9c-9ceb-cae9763d353d"
    id = "AwsExtensionsCI_IntegrationTestInLinux"
    name = "IntegrationTest in Linux"

    vcs {
        root(AwsExtensionsCi.vcsRoots.AwsExtensionsCi_AkshayGitHub)

    }

    steps {
        maven {
            goals = "clean install"
            mavenVersion = defaultProvidedVersion()
            jdkHome = "%env.JDK_18%"
        }
    }

    dependencies {
        dependency(AwsExtensionsCi_SetupInLinux) {
            snapshot {

            }
        }
    }

    triggers {
        vcs {
        }
    }
})