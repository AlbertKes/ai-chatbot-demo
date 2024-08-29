plugins {
    java
}

apply(plugin = "project")

subprojects {
    group = "com.test"
    version = "1.0.0"

    repositories {
        maven {
            url = uri("https://neowu.github.io/maven-repo/")
            content {
                includeGroupByRegex("core\\.framework.*")
            }
        }
        maven {
            url = uri("https://maven.codelibs.org/")
            content {
                includeGroup("org.codelibs.elasticsearch.module")
            }
        }
        maven {
            url = uri("https://maven.google.com/")
            content {
                includeGroup("androidx.annotation")
            }
        }
    }

    if (childProjects.isEmpty()) {
        sourceSets {
            create("dev") {
                java.srcDir("src/dev/java")
                compileClasspath += sourceSets["main"].runtimeClasspath
                runtimeClasspath += sourceSets["main"].runtimeClasspath
            }
        }
    }
}

val coreNGVersion = "9.0.8"
val mysqlVersion = "8.0.33"
val hsqlVersion = "2.7.2"

configure(subprojects.filter { it.name.endsWith("-db-migration") }) {
    apply(plugin = "db-migration")
    dependencies {
        runtimeOnly("com.mysql:mysql-connector-j:${mysqlVersion}")
    }
}

configure(subprojects.filter { it.name.endsWith("-mongo-migration") }) {
    apply(plugin = "app")
    dependencies {
        implementation("core.framework:core-ng:${coreNGVersion}")
        implementation("core.framework:core-ng-mongo:${coreNGVersion}")
    }
    tasks.register("mongoMigrate") {
        dependsOn("run")
    }
}

configure(subprojects.filter { it.name.endsWith("-service-interface") || it.name.endsWith("-api-interface") || it.name.endsWith("-site-interface") }) {
    dependencies {
        implementation("core.framework:core-ng-api:${coreNGVersion}")
        implementation(project(":backend:common-interface"))
    }
}

configure(subprojects.filter { it.name.endsWith("-service") || it.name.endsWith("-api") || it.name.endsWith("-site") }) {
    apply(plugin = "app")
    dependencies {
        implementation("core.framework:core-ng:${coreNGVersion}")
        testImplementation("core.framework:core-ng-test:${coreNGVersion}")
    }
}

configure(subprojects.filter { it.name.endsWith("-library") }) {
    dependencies {
        implementation("core.framework:core-ng:${coreNGVersion}")
        testImplementation("core.framework:core-ng-test:${coreNGVersion}")
    }
}
