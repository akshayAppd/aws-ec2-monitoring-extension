package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2017_2.triggers.vcs

/**
 * @author Akshay Srivastava
 */

object AwsExtensionsCi_Publish : BuildType({
    uuid = "7f47289a-f4ba-4cc7-95e1-3e232b46064c"
    id = "AwsExtensionsCi_Publish"
    name = "Publish build artifact"

    vcs {
        root(AwsExtensionsCi.vcsRoots.AwsExtensionsCi_AkshayGithub)
    }

    steps {
        maven {
            goals = "github-release:release"
            mavenVersion = defaultProvidedVersion()
            jdkHome = "%env.JDK_18%"
        }
    }

    dependencies {
        dependency(AwsExtensionsCi_StopLinux) {
            snapshot {

            }
        }
    }

    triggers {
        vcs {
        }
    }


    artifactRules = """
       target/ApacheMonitor-*.zip
    """.trimIndent()

})