---
layout: post
title:  "Getting started"
date:   2018-03-01 14:50:53 +0100
categories: kvision kotlin
---

#### Development

1. Download [KVision examples](https://github.com/rjaros/kvision-examples) from GitHub:

        git clone https://github.com/rjaros/kvision-examples.git
        
2. Enter one of the examples directory:

        cd kvision-examples/showcase                        (on Linux)
        cd kvision-examples\showcase                        (on Windows)

3. Run Gradle incremental build with:

        ./gradlew -t run                                    (on Linux)
        gradlew.bat -t run                                  (on Windows)
        
4. Open [http://localhost:8088/](http://localhost:8088/) in your browser.

5. Play with the code and see your changes immediately in the browser.

#### Production

To build complete application optimized for production run:

        ./gradlew -Pprod=true distZip                       (on Linux)
        gradlew.bat -Pprod=true distZip                     (on Windows)
        
Application package will be saved as build/distributions/showcase.zip.
