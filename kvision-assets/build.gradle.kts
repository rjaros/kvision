plugins {
    val npmPublishVersion: String by System.getProperties()
    id("dev.petuska.npm.publish") version npmPublishVersion
}

npmPublish {
    dry.set(System.getenv("NPM_AUTH_TOKEN") == null)
    readme.set(file("README.md"))
    packages {
        register("kvision-assets") {
            files {
                from("$projectDir/src")
            }
            packageJson {
                main.set("index.js")
                version.set("8.0.6")
                description.set("The assets for the KVision framework")
                keywords.set(listOf("kvision", "kotlin"))
                homepage.set("https://kvision.io")
                license.set("MIT")
                repository {
                    type.set("git")
                    url.set("git+https://github.com/rjaros/kvision.git")
                }
                author {
                    name.set("Robert Jaros")
                }
                bugs {
                    url.set("https://github.com/rjaros/kvision/issues")
                }
            }
        }
    }
    registries {
        register("npmjs") {
            uri.set("https://registry.npmjs.org")
            authToken.set(System.getenv("NPM_AUTH_TOKEN"))
        }
    }
}

afterEvaluate {
    tasks {
        getByName("publishKvisionAssetsPackageToNpmjsRegistry") {
            dependsOn(":kotlinNodeJsSetup")
        }
    }
}
