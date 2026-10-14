val inactiveStrict by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

val strictJoda = providers.gradleProperty("fixture.strictJoda")
    .map { it.toBooleanStrict() }
    .orElse(false)

dependencies {
    if (strictJoda.get()) {
        add(inactiveStrict.name, "joda-time:joda-time") {
            version {
                strictly("2.12.5")
            }
        }
    } else {
        add(inactiveStrict.name, "joda-time:joda-time:2.15.0")
    }
}
