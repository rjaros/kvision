plugins {
    id("lt.petuska.npm.publish") version "0.1.0"
}

npmPublishing {
    publications {
        publication(project.name) {
            main = "index.js"
            readme = file("README.md")
            files {
                from(projectDir) {
                    include("css/**", "img/**", "js/**", "index.js", "package.json")
                }
            }
        }
    }

    repositories {
        repository("npmjs") {
            registry = uri("https://registry.npmjs.org")
            authToken = System.getenv("NPM_AUTH_TOKEN")
        }
    }
}
