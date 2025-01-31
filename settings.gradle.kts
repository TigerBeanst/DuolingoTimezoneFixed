pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS);
    repositories {
        google();
        mavenCentral();
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://api.xposed.info/") }
        maven { url = uri("https://s01.oss.sonatype.org/content/repositories/releases/") }
    };
}

rootProject.name = "DuolingoRegret";
include(":app");

rootProject.name = "DuolingoRegret"
include(":app")
 