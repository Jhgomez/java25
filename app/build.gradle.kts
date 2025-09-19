plugins {
    application
}

group = "java25"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

application {
    mainClass.set("java25.Main")
    mainModule.set("main")
}

dependencies {
    implementation(project(":testModule")               )
}
