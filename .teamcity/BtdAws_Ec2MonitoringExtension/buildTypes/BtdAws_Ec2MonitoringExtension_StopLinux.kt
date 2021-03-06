package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.exec
import jetbrains.buildServer.configs.kotlin.v2017_2.triggers.vcs

/**
 * @author Akshay Srivastava
 */
object BtdAws_Ec2MonitoringExtension_StopLinux : BuildType({
    uuid = "64e86b68-4a1d-11e9-8646-d663bd873d93"
    id = "BtdAws_Ec2MonitoringExtension_StopLinux"
    name = "Stop Linux docker"

    vcs {
        root(BtdAws_Ec2MonitoringExtension.vcsRoots.BtdAws_Ec2MonitoringExtension_AkshayGit)

    }

    steps {
        exec {
            path = "make"
            arguments = "dockerStop"
        }
        exec {
            path = "make"
            arguments = "terraformDestroy"
        }
    }

    dependencies {
        dependency(BtdAws_Ec2MonitoringExtension_IntegrationTestInLinux) {
            snapshot {

            }
        }
    }

    triggers {
        vcs {
        }
    }
})