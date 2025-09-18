plugins {
    application
}

group = "java25"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

application {
    mainClass.set("java25.Main")
    mainModule.set("main")
}

dependencies {

}
