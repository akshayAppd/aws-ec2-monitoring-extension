package BtdAws_Ec2MonitoringExtension.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2017_2.vcs.GitVcsRoot

object BtdAws_Ec2MonitoringExtension_AkshayGit : GitVcsRoot({
    uuid = "15440bfc-4a14-11e9-8646-d663bd873d93"
    name = "AkshayGit"
    url = "git@github.com:akshayAppd/aws-ec2-monitoring-extension-ci.git"
    authMethod = uploadedKey {
        uploadedKey = "AkshayGitHub"
    }
})
