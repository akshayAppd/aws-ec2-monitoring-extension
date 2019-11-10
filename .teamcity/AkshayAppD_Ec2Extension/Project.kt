package AkshayAppD_Ec2Extension

import AkshayAppD_Ec2Extension.vcsRoots.*
import AkshayAppD_Ec2Extension.vcsRoots.AkshayAppD_Ec2Extension_Ec2extension
import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.Project
import jetbrains.buildServer.configs.kotlin.v2018_2.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v2018_2.projectFeatures.versionedSettings

object Project : Project({
    uuid = "2fddf020-0126-4098-b180-4504811d63aa"
    id("AkshayAppD_Ec2Extension")
    parentId("AkshayAppD")
    name = "EC2 Extension"

    vcsRoot(AkshayAppD_Ec2Extension_Ec2extension)

    features {
        versionedSettings {
            id = "PROJECT_EXT_4"
            mode = VersionedSettings.Mode.ENABLED
            buildSettingsMode = VersionedSettings.BuildSettingsMode.PREFER_SETTINGS_FROM_VCS
            rootExtId = "${AkshayAppD_Ec2Extension_Ec2extension.id}"
            showChanges = false
            settingsFormat = VersionedSettings.Format.KOTLIN
            storeSecureParamsOutsideOfVcs = true
        }
    }
})
