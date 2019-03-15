package AwsExtensionsCi.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_2.*

object AwsExtensionsCi_CleanBuild : BuildType({
    uuid = "61af96cf-8f4a-498e-ac76-a0ed419d1054"
    name = "Clean Build"

    vcs {
        root(AwsExtensionsCi.vcsRoots.AwsExtensionsCi_HttpsGithubComAkshayAppdAwsEc2monitoringExtensionCiRefsHeadsMast)
    }
})
