package AwsExtensionsCi.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot

object AwsExtensionsCi_AkshayGithub : GitVcsRoot({
    uuid = "c4bfdef0-7952-4c3a-a76f-b1523036d083"
    name = "AkshayGithub"
    url = "git@github.com:akshayAppd/aws-ec2-monitoring-extension-ci.git"
    authMethod = uploadedKey {
        uploadedKey = "AkshayGitHub"
    }
})
