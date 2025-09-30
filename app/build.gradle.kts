plugins {
    application
}

group = "java25"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

val run by tasks.getting(JavaExec::class) {
    standardInput = System.`in`
}

application {
    mainClass.set("java25.Client")
    mainModule.set("main")
}

dependencies {
    implementation(project(":testModule")               )
}
