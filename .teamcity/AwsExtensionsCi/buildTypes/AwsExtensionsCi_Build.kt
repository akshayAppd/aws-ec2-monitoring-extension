package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs

object AwsExtensionsCi_Build : BuildType({
    uuid = "f1086e45-96cd-4996-9ec2-43d9d6b6534e"
    name = "Build"

    vcs {
        root(AwsExtensionsCi.vcsRoots.AwsExtensionsCi_HttpsGithubComAkshayAppdAwsEc2monitoringExtensionCiRefsHeadsMast)
    }

    triggers {
        vcs {
        }
    }
})
