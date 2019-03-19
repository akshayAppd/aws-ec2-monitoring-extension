package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2017_2.triggers.vcs

/**
 * @author Akshay Srivastava
 */
object BtdAws_Ec2MonitoringExtension_IntegrationTestInLinux : BuildType({
    uuid = "37148194-4a14-11e9-8646-d663bd873d93"
    id = "BtdAws_Ec2MonitoringExtension_IntegrationTestInLinux"
    name = "IntegrationTest in Linux"

    vcs {
        root(BtdAws_Ec2MonitoringExtension.vcsRoots.BtdAws_Ec2MonitoringExtension_AkshayGit)

    }


    steps {
        maven {
            goals = "clean install"
            mavenVersion = defaultProvidedVersion()
            jdkHome = "%env.JDK_18%"
        }
    }

    dependencies {
        dependency(BtdAws_Ec2MonitoringExtension_SetupInLinux) {
            snapshot {

            }
        }
    }

    triggers {
        vcs {
        }
    }
})