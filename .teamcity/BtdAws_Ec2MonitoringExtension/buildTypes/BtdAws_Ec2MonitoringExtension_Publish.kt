package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2017_2.triggers.vcs

/**
 * @author Akshay Srivastava
 */

object BtdAws_Ec2MonitoringExtension_Publish : BuildType({
    uuid = "3ff8f4b6-4a14-11e9-8646-d663bd873d93"
    id = "BtdAws_Ec2MonitoringExtension_Publish"
    name = "Publish build artifact"

    vcs {
        root(BtdAws_Ec2MonitoringExtension.vcsRoots.BtdAws_Ec2MonitoringExtension_AkshayGit)
    }

    steps {
        maven {
            goals = "github-release:release"
            mavenVersion = defaultProvidedVersion()
            jdkHome = "%env.JDK_18%"
        }
    }

    dependencies {
        dependency(BtdAws_Ec2MonitoringExtension_StopLinux) {
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