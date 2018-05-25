@file: JvmName("DaggerInjection")

package com.mpaani.goodfeed.core.injection

lateinit var dependencyComponent: DependencyComponent

/**
 * Initialise the Dependency Component to be used for Injection.
 */
fun initialise() {
    dependencyComponent = buildDependencyComponent()
}

private fun buildDependencyComponent() = DaggerDependencyComponent
        .builder()
        .dependencyProvider(DependencyProvider)
        .build()
