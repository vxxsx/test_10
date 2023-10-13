pluginManagement {
    repositories {
        maven { url =uri("https://jitpack.io")}
        maven { url =uri("https://maven.aliyun.com/repository/google") }
        maven { url =uri("https://maven.aliyun.com/repository/gradle-plugin") }
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url =uri("https://jitpack.io")}
        maven { url =uri("https://maven.aliyun.com/repository/google") }
        maven { url =uri("https://maven.aliyun.com/repository/gradle-plugin") }
        google()
        mavenCentral()
    }
}

rootProject.name = "HelloWorld"
include(":app")
