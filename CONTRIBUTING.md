# Contributing to KVision

:+1::tada: Thanks for taking the time to contribute! :tada::+1:

## How to contribute

- Use KVision in your applications to test the framework :smile:
- Report bugs or feature requests by creating GitHub issues.
- Fix existing bugs.
- Implement new features and modules.

## How to develop KVision

1. You need JDK 8 or later installed on your system. 
2. Clone the code repository (create a fork if you want to make pull requests with your changes).
3. Change the `version` property in `gradle.properties` file to a number ending with the `-SNAPSHOT` suffix (e.g. `3.10.0-SNAPSHOT`).
4. Change the version number at the bottom of `kvision-tools/kvision-gradle-plugin/src/main/kotlin/pl/treksoft/kvision/gradle/KVisionGradleSubplugin.kt` file to the same number and suffix (keep those version numbers always in sync). 
5. Make your changes to KVision code.
6. Run tests with `./gradlew test` command to check if everything is OK.
7. Compile and publish artifacts to your local Maven repository with `./gradlew publishToMavenLocal` command.
8. Use your version of KVision by specifying the correct version number in your application (in `gradle.properties` file). Remember to clean your project every time you publish new version of the framework.

## How to create new module

1. Copy one of exising module directories with new name (choose the module most similar to the one you create).
2. Add new module name to the list in the `settings.gradle.kts` file and to the `dokka` task in the `build.gradle.kts` file.
3. Remove copied code and start writing your own.

## Where to get help

Ask questions on Kotlin Slack [KVision channel](https://kotlinlang.slack.com/?redir=%2Fmessages%2FCL4C1SLKC).
