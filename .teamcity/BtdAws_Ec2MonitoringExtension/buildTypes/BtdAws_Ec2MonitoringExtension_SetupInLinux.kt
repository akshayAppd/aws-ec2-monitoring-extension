package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.exec
import jetbrains.buildServer.configs.kotlin.v2017_2.triggers.vcs

/**
 * @author Akshay Srivastava
 */
object BtdAws_Ec2MonitoringExtension_SetupInLinux : BuildType({
    uuid = "3ff8f4b6-4a14-11e9-8646-d663bd873d93"
    id = "BtdAws_Ec2MonitoringExtension_SetupInLinux"
    name = "Setup Linux Environment"

    vcs {
        root(BtdAws_Ec2MonitoringExtension.vcsRoots.BtdAws_Ec2MonitoringExtension_AkshayGit)

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
        dependency(BtdAws_Ec2MonitoringExtension_CleanBuild) {
            snapshot {

            }
        }
    }

    triggers {
        vcs {
        }
    }
})