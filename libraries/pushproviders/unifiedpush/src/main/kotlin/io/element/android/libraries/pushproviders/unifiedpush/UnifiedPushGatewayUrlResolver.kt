/*
 * Copyright (c) 2025 Element Creations Ltd.
 * Copyright 2024, 2025 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.libraries.pushproviders.unifiedpush

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

interface UnifiedPushGatewayUrlResolver {
    fun resolve(
        gatewayResult: UnifiedPushGatewayResolverResult,
        instance: String,
    ): String
}

@ContributesBinding(AppScope::class)
class DefaultUnifiedPushGatewayUrlResolver(
    private val unifiedPushStore: UnifiedPushStore,
    private val defaultPushGatewayHttpUrlProvider: DefaultPushGatewayHttpUrlProvider,
) : UnifiedPushGatewayUrlResolver {
    override fun resolve(
        gatewayResult: UnifiedPushGatewayResolverResult,
        instance: String,
    ): String {
        return when (gatewayResult) {
            is UnifiedPushGatewayResolverResult.Error -> {
                if (gatewayResult.gateway.startsWith("http://")) {
                    // A cleartext endpoint is a self-hosted UnifiedPush server (every public distributor is https).
                    // Discovery usually fails here only because this app's network security policy blocks
                    // cleartext to a raw IP (e.g. http://192.168.1.10:2586 on a school LAN). The pusher URL is
                    // used by the homeserver, not by this device, so the phone never has to reach it. A
                    // self-hosted server that is also a Matrix gateway (ntfy) serves it on its own origin, and
                    // the public default gateway can never reach a LAN endpoint, so use the derived URL.
                    gatewayResult.gateway
                } else {
                    // Use previous gateway if any, or the default one
                    unifiedPushStore.getPushGateway(instance)
                        ?: defaultPushGatewayHttpUrlProvider.provide()
                }
            }
            UnifiedPushGatewayResolverResult.ErrorInvalidUrl,
            UnifiedPushGatewayResolverResult.NoMatrixGateway -> {
                defaultPushGatewayHttpUrlProvider.provide()
            }
            is UnifiedPushGatewayResolverResult.Success -> {
                gatewayResult.gateway
            }
        }
    }
}
