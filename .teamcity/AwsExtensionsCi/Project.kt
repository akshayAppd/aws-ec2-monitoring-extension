package AwsExtensionsCi

import AwsExtensionsCi.vcsRoots.*
import AwsExtensionsCi.vcsRoots.AwsExtensionsCi_AkshayGithub
import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.Project
import jetbrains.buildServer.configs.kotlin.v2018_2.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v2018_2.projectFeatures.versionedSettings

object Project : Project({
    uuid = "15604d30-1c23-4a51-927f-293ea2b881a9"
    id("AwsExtensionsCi")
    parentId("_Root")
    name = "AWS_Extensions_CI"

    vcsRoot(AwsExtensionsCi_AkshayGithub)

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
})
