package BtdAws_Ec2MonitoringExtension

import AwsExtensionsCi.buildTypes.*
import BtdAws_Ec2MonitoringExtension.buildTypes.BtdAws_Ec2MonitoringExtension_SetupEC2
import BtdAws_Ec2MonitoringExtension.vcsRoots.BtdAws_Ec2MonitoringExtension_AkshayGit
import jetbrains.buildServer.configs.kotlin.v2017_2.Project
import jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures.versionedSettings

object Project : Project({
    uuid = "7965a2f4-4a1d-11e9-8646-d663bd873d93"
    id = "BtdAws_Ec2MonitoringExtension"
    parentId = "BtdAws"
    name = "EC2_Monitoring_Extension"
    description = "AWS EC2 teamcity btd test"

    vcsRoot(BtdAws_Ec2MonitoringExtension_AkshayGit)

    buildType(BtdAws_Ec2MonitoringExtension_CleanBuild)
    buildType(BtdAws_Ec2MonitoringExtension_SetupEC2)
    buildType(BtdAws_Ec2MonitoringExtension_SetupInLinux)
    buildType(BtdAws_Ec2MonitoringExtension_IntegrationTestInLinux)
    buildType(BtdAws_Ec2MonitoringExtension_StopLinux)
    buildType(BtdAws_Ec2MonitoringExtension_Publish)

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

    buildTypesOrder = arrayListOf(
            BtdAws_Ec2MonitoringExtension_CleanBuild,
            BtdAws_Ec2MonitoringExtension_SetupEC2,
            BtdAws_Ec2MonitoringExtension_SetupInLinux,
            BtdAws_Ec2MonitoringExtension_IntegrationTestInLinux,
            BtdAws_Ec2MonitoringExtension_StopLinux,
            BtdAws_Ec2MonitoringExtension_Publish
    )
})
