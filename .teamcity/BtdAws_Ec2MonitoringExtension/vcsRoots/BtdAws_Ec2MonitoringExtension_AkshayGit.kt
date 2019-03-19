package BtdAws_Ec2MonitoringExtension.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot

object BtdAws_Ec2MonitoringExtension_AkshayGit : GitVcsRoot({
    uuid = "e3dbe10c-59c1-48c5-ab1a-2d71d611715b"
    name = "AkshayGit"
    url = "git@github.com:akshayAppd/aws-ec2-monitoring-extension-ci.git"
    authMethod = uploadedKey {
        uploadedKey = "AkshayGitHub"
    }
})
