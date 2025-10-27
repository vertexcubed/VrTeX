
VrTeX
=======

Library mod for use in mod development. Mostly undocunmented as of now, but documentation WIP.

## Features
- Async Task scheduler system (intended for animating widgets in screens)
- Easy use Widget creation
- Cumulative, flexible Screenshake system
- Simple state machine available for screens

## Installation
Add the Jitpack repository:
```groovy
maven { 
    url "https://jitpack.io" 
    content { includeGroup "com.github.vertexcubed" }
}
```

Then add the dependency:
```groovy
dependencies {
    implementation "com.github.vertexcubed:VrTeX:${minecraft_version}-${vrtex_version}"
}
