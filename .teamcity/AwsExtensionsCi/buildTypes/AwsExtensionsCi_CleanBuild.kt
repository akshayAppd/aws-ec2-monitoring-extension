package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.*
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.maven

object AwsExtensionsCi_CleanBuild : BuildType({
    uuid = "61af96cf-8f4a-498e-ac76-a0ed419d1054"
    name = "Clean Build"

    vcs {
        root(AwsExtensionsCi.vcsRoots.AwsExtensionsCi_AkshayGithub)
    }

    steps {
        maven {
            goals = "clean test"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
            mavenVersion = defaultProvidedVersion()
            jdkHome = "%env.JDK_18%"
        }
    }
})
