package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.maven

object BtdAws_Ec2MonitoringExtension_CleanBuild : BuildType({
    uuid = "3190ec58-4a14-11e9-8646-d663bd873d93"
    id = "BtdAws_Ec2MonitoringExtension_CleanBuild"
    name = "Clean Build"

    vcs {
        root(BtdAws_Ec2MonitoringExtension.vcsRoots.BtdAws_Ec2MonitoringExtension_AkshayGit)
    }

    steps {
        maven {
            goals = "clean install -Pno-integration-tests"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
            mavenVersion = defaultProvidedVersion()
            jdkHome = "%env.JDK_18%"
        }
    }
})
