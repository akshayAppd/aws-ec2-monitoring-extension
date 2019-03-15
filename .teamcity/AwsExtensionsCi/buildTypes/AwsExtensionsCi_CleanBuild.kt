package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs

object AwsExtensionsCi_CleanBuild : BuildType({
    uuid = "b593b361-3cc1-45dc-ad92-ee0f6454fef8"
    name = "Clean Build"

    vcs {
        root(AwsExtensionsCi.vcsRoots.AwsExtensionsCi_HttpsGithubComAkshayAppdAwsEc2monitoringExtensionCiRefsHeadsMast)
    }

    triggers {
        vcs {
        }
    }
})
