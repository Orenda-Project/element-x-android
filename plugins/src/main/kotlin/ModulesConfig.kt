/*
 * Copyright (c) 2025 Element Creations Ltd.
 * Copyright 2024, 2025 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

import config.AnalyticsConfig
import config.BuildTimeConfig
import config.PushProvidersConfig

object ModulesConfig {
    val pushProvidersConfig = PushProvidersConfig(
        includeFirebase = BuildTimeConfig.PUSH_CONFIG_INCLUDE_FIREBASE,
        includeUnifiedPush = BuildTimeConfig.PUSH_CONFIG_INCLUDE_UNIFIED_PUSH,
    )

    // Rumi fork: gate analytics on real keys for EVERY build, not only enterprise ones. Upstream's
    // non-enterprise branch always bundles Posthog + Sentry because Element's own endpoints are
    // resolved at runtime (PosthogEndpointConfigProvider.isElement()), but this fork's
    // applicationId is not Element's, so those endpoints resolve to null and nothing is ever sent.
    // Bundling them anyway made the FTUE ask teachers to consent to analytics that do not exist
    // (android-run3 05-analytics-optin.png). With no keys, the noop AnalyticsService reports
    // consent as already asked, so the opt-in screen and the Settings > Analytics row both vanish.
    val analyticsConfig: AnalyticsConfig = run {
        // Is Posthog configuration available?
        val withPosthog = BuildTimeConfig.SERVICES_POSTHOG_APIKEY.isNullOrEmpty().not() &&
            BuildTimeConfig.SERVICES_POSTHOG_HOST.isNullOrEmpty().not()
        // Is Sentry configuration available?
        val withSentry = BuildTimeConfig.SERVICES_SENTRY_DSN.isNullOrEmpty().not()
        if (withPosthog || withSentry) {
            println("Analytics enabled with Posthog: $withPosthog, Sentry: $withSentry")
            AnalyticsConfig.Enabled(
                withPosthog = withPosthog,
                withSentry = withSentry,
            )
        } else {
            println("Analytics disabled")
            AnalyticsConfig.Disabled
        }
    }
}
