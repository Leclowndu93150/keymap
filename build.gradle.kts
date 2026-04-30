import dev.prism.gradle.dsl.ReleaseType

plugins {
    id("dev.prism")
}

group = "com.leclowndu93150"
version = "0.11.2"

prism {
    metadata {
        modId = "keymap"
        name = "Keymap"
        description = "Visual keybinding manager with virtual keyboard."
        license = "ISC"
        author("Einjerjar")
        author("Leclowndu93150")
    }

    version("1.20.1") {

        fabric {
            loaderVersion = "0.15.11"
            fabricApi("0.92.2+1.20.1")
        }
        forge {
            loaderVersion = "47.3.0"
            loaderVersionRange = "[4,)"
        }
    }

    version("26.1.2") {
        fabric {
            loaderVersion = "0.19.2"
            fabricApi("0.147.0+26.1.2")
        }
        neoforge {
            loaderVersion = "26.1.2.30-beta"
            loaderVersionRange = "[26.1.2,)"
        }
    }

    publishing {
        type = ReleaseType.STABLE

        changelogFile = "changelog.md"

        curseforge {
            accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
            projectId = "1263117"
        }

        modrinth {
            accessToken = providers.environmentVariable("MODRINTH_TOKEN")
            projectId = "8b8sY7tn"
        }
    }
}
