plugins {
    val npmPublishVersion: String by System.getProperties()
    id("lt.petuska.npm.publish") version npmPublishVersion
}

npmPublishing {
    publications {
        publication(project.name) {
            main = "index.js"
            readme = file("README.md")
            files {
                from(projectDir) {
                    include("css/**", "img/**", "js/**", "index.js")
                }
            }
            packageJson {
                version = "0.0.1"
                description = "The npm placeholder for kvision-bootstrap module"
                keywords = jsonArray("kvision", "kotlin")
                homepage = "https://kvision.io"
                licence = "MIT"
                repository {
                    type = "git"
                    url = "git+https://github.com/rjaros/kvision.git"
                }
                author {
                    name = "Robert Jaros"
                }
                bugs {
                    url = "https://github.com/rjaros/kvision/issues"
                }
            }

            repositories {
                repository("npmjs") {
                    registry = uri("https://registry.npmjs.org")
                    authToken = System.getenv("NPM_AUTH_TOKEN")
                }
            }
        }
    }
}
