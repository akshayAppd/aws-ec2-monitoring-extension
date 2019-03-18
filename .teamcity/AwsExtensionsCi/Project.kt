package AwsExtensionsCi

import AwsExtensionsCi.buildTypes.*
import AwsExtensionsCi.vcsRoots.*
import AwsExtensionsCi.vcsRoots.AwsExtensionsCi_AkshayGithub
import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.Project
import jetbrains.buildServer.configs.kotlin.v2018_2.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v2018_2.projectFeatures.versionedSettings

object Project : Project({
    uuid = "15604d30-1c23-4a51-927f-293ea2b881a9"
    id("AwsExtensionsCI")
    parentId("_Root")
    name = "AWS_Extensions_CI"

    vcsRoot(AwsExtensionsCi_AkshayGithub)

    buildType(AwsExtensionsCI_CleanBuild)
    buildType(AwsExtensionsCI_SetupInLinux)
    buildType(AwsExtensionsCI_IntegrationTestInLinux)
    buildType(AwsExtensionsCI_StopLinux)
    buildType(AwsExtensionsCI_Publish)

    params {
        password("env.APPDYNAMICS_AGENT_ACCOUNT_ACCESS_KEY", "credentialsJSON:9a2e4645-ba0b-4431-89a9-02f31f720a92", label = "Controller access key", readOnly = true)
        param("env.APPDYNAMICS_CONTROLLER_PORT", "8090")
        param("env.APPDYNAMICS_CONTROLLER_HOST_NAME", "osxltasriv.local")
        password("env.AWS_ACCESS_KEY", "credentialsJSON:9426a2f2-03c8-4f39-9a9e-60666c1696c7", label = "AWS Access Key", readOnly = true)
        password("env.AWS_SECRET_KEY", "credentialsJSON:02569e80-9e6f-4886-a338-5af1976390a7", label = "AWS Secret Key", readOnly = true)
        param("env.APPDYNAMICS_AGENT_ACCOUNT_NAME", "customer1")
        param("env.APPDYNAMICS_CONTROLLER_SSL_ENABLED", "false")
    }

    features {
        versionedSettings {
            id = "PROJECT_EXT_2"
            mode = VersionedSettings.Mode.ENABLED
            buildSettingsMode = VersionedSettings.BuildSettingsMode.PREFER_SETTINGS_FROM_VCS
            rootExtId = "${AwsExtensionsCI_AkshayGithub.id}"
            showChanges = false
            settingsFormat = VersionedSettings.Format.KOTLIN
            storeSecureParamsOutsideOfVcs = true
        }
    }

    buildTypesOrder = arrayListOf(
            AwsExtensionsCI_CleanBuild,
            AwsExtensionsCI_SetupInLinux,
            AwsExtensionsCI_IntegrationTestInLinux,
            AwsExtensionsCI_StopLinux,
            AwsExtensionsCI_Publish
    )
})
