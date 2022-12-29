plugins {
    val npmPublishVersion: String by System.getProperties()
    id("dev.petuska.npm.publish") version npmPublishVersion
}

npmPublish {
    packages {
        register(project.name) {
            readme.set(file("README.md"))
            files {
                from(projectDir.resolve("src"))
            }
            packageJson {
                main.set("index.js")
                version.set("7.0.4")
                description.set("The assets for the KVision framework")
                keywords.addAll("kvision", "kotlin")
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
        npmjs {
            authToken.set(System.getenv("NPM_AUTH_TOKEN"))
        }
    }
}
