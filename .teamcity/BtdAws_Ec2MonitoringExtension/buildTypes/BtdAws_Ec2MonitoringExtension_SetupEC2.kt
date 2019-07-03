package BtdAws_Ec2MonitoringExtension.buildTypes

import AwsExtensionsCi.buildTypes.BtdAws_Ec2MonitoringExtension_CleanBuild
import jetbrains.buildServer.configs.kotlin.v2017_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.exec

/**
 * @author Akshay Srivastava
 */
object BtdAws_Ec2MonitoringExtension_SetupEC2 : BuildType({
    uuid = "03dc024c-cf75-45d9-980d-055705aff23b"
    id = "BtdAws_Ec2MonitoringExtension_SetupEC2"
    name = "Setup EC2 Environment"

    vcs {
        root(BtdAws_Ec2MonitoringExtension.vcsRoots.BtdAws_Ec2MonitoringExtension_AkshayGit)

    }

    steps {
        exec {
            path = "make"
            arguments = "terraformApply"
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

    /*triggers {
        vcs {
        }
    }*/
})