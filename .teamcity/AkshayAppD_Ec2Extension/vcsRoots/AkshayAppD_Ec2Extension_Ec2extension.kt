package AkshayAppD_Ec2Extension.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot

object AkshayAppD_Ec2Extension_Ec2extension : GitVcsRoot({
    uuid = "cc474284-cd5a-4f93-86bc-9e46a1fbc622"
    name = "ec2extension"
    url = "git@github.com:akshayAppd/aws-ec2-monitoring-extension-ci.git"
    authMethod = uploadedKey {
        uploadedKey = "Local TeamCity"
    }
})
