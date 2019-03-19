package BtdAws_Ec2MonitoringExtension

import BtdAws_Ec2MonitoringExtension.vcsRoots.*
import BtdAws_Ec2MonitoringExtension.vcsRoots.BtdAws_Ec2MonitoringExtension_AkshayGit
import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.Project
import jetbrains.buildServer.configs.kotlin.v2018_2.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v2018_2.projectFeatures.versionedSettings

object Project : Project({
    uuid = "96927f2b-7d1c-493c-8887-449c4eba657f"
    id("BtdAws_Ec2MonitoringExtension")
    parentId("BtdAws")
    name = "EC2_Monitoring_Extension"
    description = "AWS EC2 teamcity btd test"

    vcsRoot(BtdAws_Ec2MonitoringExtension_AkshayGit)

    features {
        versionedSettings {
            id = "PROJECT_EXT_3"
            mode = VersionedSettings.Mode.ENABLED
            buildSettingsMode = VersionedSettings.BuildSettingsMode.PREFER_SETTINGS_FROM_VCS
            rootExtId = "${BtdAws_Ec2MonitoringExtension_AkshayGit.id}"
            showChanges = false
            settingsFormat = VersionedSettings.Format.KOTLIN
            storeSecureParamsOutsideOfVcs = true
        }
    }
})
