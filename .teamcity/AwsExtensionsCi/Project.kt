package AwsExtensionsCi

import AwsExtensionsCi.buildTypes.*
import AwsExtensionsCi.vcsRoots.AwsExtensionsCi_AkshayGithub
import jetbrains.buildServer.configs.kotlin.v2017_2.Project
import jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures.versionedSettings

object Project : Project({
    uuid = "15604d30-1c23-4a51-927f-293ea2b881a9"
    id = "AwsExtensionsCi"
    parentId = "_Root"
    name = "AWS_Extensions_Ci"

    vcsRoot(AwsExtensionsCi_AkshayGithub)

    buildType(AwsExtensionsCi_CleanBuild)
    buildType(AwsExtensionsCi_SetupInLinux)
    buildType(AwsExtensionsCi_IntegrationTestInLinux)
    buildType(AwsExtensionsCi_StopLinux)
    buildType(AwsExtensionsCi_Publish)


    features {
        versionedSettings {
            id = "PROJECT_EXT_2"
            mode = VersionedSettings.Mode.ENABLED
            buildSettingsMode = VersionedSettings.BuildSettingsMode.PREFER_SETTINGS_FROM_VCS
            rootExtId = "${AwsExtensionsCi_AkshayGithub.id}"
            showChanges = false
            settingsFormat = VersionedSettings.Format.KOTLIN
            storeSecureParamsOutsideOfVcs = true
        }
    }

    buildTypesOrder = arrayListOf(
            AwsExtensionsCi_CleanBuild,
            AwsExtensionsCi_SetupInLinux,
            AwsExtensionsCi_IntegrationTestInLinux,
            AwsExtensionsCi_StopLinux,
            AwsExtensionsCi_Publish
    )
})
