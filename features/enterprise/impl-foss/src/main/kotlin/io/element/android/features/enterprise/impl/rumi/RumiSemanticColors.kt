/*
 * Copyright (c) 2025 Element Creations Ltd.
 * Copyright 2024, 2025 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.features.enterprise.impl.rumi

import androidx.compose.ui.graphics.Color
import io.element.android.compound.colors.SemanticColorsLightDark
import io.element.android.compound.tokens.generated.compoundColorsDark
import io.element.android.compound.tokens.generated.compoundColorsLight

/**
 * Rumi's navy/coral applied to the Compound design system, [SemanticColorsLightDark.default]'s
 * generated [io.element.android.compound.tokens.generated.SemanticColors] tokens, overriding
 * only two token groups and leaving every other generated token (surfaces, text, destructive,
 * success, gradients, etc.) exactly as upstream Element ships it.
 *
 * ## Where this reaches
 *
 * - `bgActionPrimary*` (rest/hovered/pressed; `disabled` stays upstream grey, disabled state
 *   should not read as branded) -- Compound's primary-action background.
 *   [io.element.android.compound.theme.MaterialColorSchemeLight] and
 *   [io.element.android.compound.theme.MaterialColorSchemeDark] both map Material3's
 *   `ColorScheme.primary` straight from `bgActionPrimaryRest`, so this is also what tints the
 *   send button, FABs, primary CTAs, and (via `enableEdgeToEdge`) the status bar scrim.
 *   Navy in light mode; the product design system's own dark-mode "lift" (`#4F74E3`, see
 *   rumi-brand skill's reference/color-tokens.md section 3) in dark mode, since flat navy reads as
 *   near-black and loses contrast against Compound's dark canvas.
 * - `bgAccent*` / `iconAccentPrimary` / `iconAccentTertiary` / `borderAccentPrimary` /
 *   `borderAccentSubtle` / `textActionAccent` -- Compound's generic "accent" role: selected
 *   radio/checkbox/switch state, the join-room button, the send-button icon tint, the location
 *   share puck. Verified none of these carry a fixed semantic meaning (e.g. this is not the
 *   "user is online" indicator) before recolouring, so coral is a safe fit for "the one
 *   sanctioned pop of colour" the brand calls for. Coral is unchanged between light/dark per
 *   the same reference doc ("Accent coral stays 15 85% 60%").
 *
 * ## Where this does NOT reach (the mechanism's real boundary)
 *
 * - Material3's `secondary`/`tertiary` roles are mapped from `textSecondary` (a grey body-text
 *   colour), not from an accent background token -- recolouring `textSecondary` to coral would
 *   wreck secondary-text contrast app-wide, so those two Material3 roles stay upstream grey.
 *   This means a component that reads `MaterialTheme.colorScheme.secondary` directly (rather
 *   than through Compound's own `ElementTheme.colors.*`) will not show Rumi's coral.
 * - `background` / `surface` (`bgCanvasDefault`) are deliberately left white/near-black, matching
 *   the product web app's own choice to keep `--background` neutral and use `--primary` for the
 *   one navy surface -- painting the whole canvas navy was considered and rejected as off-brief.
 * - High-contrast variants (`compoundColorsHcLight`/`compoundColorsHcDark`, used only when the
 *   OS accessibility "increase contrast" setting is on) are intentionally left as upstream
 *   Element's, since hand-picked brand hues are exactly what that accessibility mode exists to
 *   override back out.
 */
private const val RUMI_NAVY_LIGHT_REST = 0xFF0E2058
private const val RUMI_NAVY_LIGHT_HOVERED = 0xFF122A72
private const val RUMI_NAVY_LIGHT_PRESSED = 0xFF0A163E

// Dark-mode "lift": product design system's own dark-mode primary (reference/color-tokens.md section 3),
// used instead of flat navy because flat navy is close to invisible against a near-black canvas.
private const val RUMI_NAVY_DARK_REST = 0xFF4F74E3
private const val RUMI_NAVY_DARK_HOVERED = 0xFF6989E7
private const val RUMI_NAVY_DARK_PRESSED = 0xFF355FDF

private const val RUMI_CORAL_REST = 0xFFF06E42
private const val RUMI_CORAL_HOVERED = 0xFFEE5826
private const val RUMI_CORAL_PRESSED = 0xFFE34712
private const val RUMI_CORAL_SUBTLE = 0xFFFBDBD0

// Gradients. Compound's "subtle" gradient is what Modifier.gradientBackground() paints behind
// screen headers (the chat list's top bar), and stock it is Element's signature green. Held
// next to stock, that green header alone identifies the app as Element, so it must go.
// Light: white into the faintest navy tint, so the header reads as paper, not a colour.
// Dark: the canvas into a deep navy, the same "lift" logic as the dark primary above.
// "Action" stops feed SuperButton and GradientFloatingActionButton; navy shades keep any
// gradient button in the brand and off Element green.
private val RUMI_GRADIENT_SUBTLE_LIGHT = listOf(0xFFFFFFFF, 0xFFF7F8FC, 0xFFEEF1F8, 0xFFE8ECF7, 0xFFF3F5FA, 0xFFFFFFFF)
private val RUMI_GRADIENT_SUBTLE_DARK  = listOf(0xFF0B1226, 0xFF0E1A3C, 0xFF12224E, 0xFF162A60, 0xFF101E44, 0xFF0B1226)
private val RUMI_GRADIENT_ACTION_LIGHT = listOf(0xFF0E2058, 0xFF16307C, 0xFF1F3F9E, 0xFF2B52C4)
private val RUMI_GRADIENT_ACTION_DARK  = listOf(0xFF355FDF, 0xFF4F74E3, 0xFF6989E7, 0xFF86A0EB)

val rumiSemanticColors: SemanticColorsLightDark = SemanticColorsLightDark(
    light = compoundColorsLight.copy(
        bgActionPrimaryRest = Color(RUMI_NAVY_LIGHT_REST),
        bgActionPrimaryHovered = Color(RUMI_NAVY_LIGHT_HOVERED),
        bgActionPrimaryPressed = Color(RUMI_NAVY_LIGHT_PRESSED),
        bgAccentRest = Color(RUMI_CORAL_REST),
        bgAccentHovered = Color(RUMI_CORAL_HOVERED),
        bgAccentPressed = Color(RUMI_CORAL_PRESSED),
        bgAccentSubtle = Color(RUMI_CORAL_SUBTLE),
        iconAccentPrimary = Color(RUMI_CORAL_REST),
        iconAccentTertiary = Color(RUMI_CORAL_HOVERED),
        borderAccentPrimary = Color(RUMI_CORAL_REST),
        borderAccentSubtle = Color(RUMI_CORAL_HOVERED),
        textActionAccent = Color(RUMI_CORAL_PRESSED),
        gradientSubtleStop1 = Color(RUMI_GRADIENT_SUBTLE_LIGHT[0]),
        gradientSubtleStop2 = Color(RUMI_GRADIENT_SUBTLE_LIGHT[1]),
        gradientSubtleStop3 = Color(RUMI_GRADIENT_SUBTLE_LIGHT[2]),
        gradientSubtleStop4 = Color(RUMI_GRADIENT_SUBTLE_LIGHT[3]),
        gradientSubtleStop5 = Color(RUMI_GRADIENT_SUBTLE_LIGHT[4]),
        gradientSubtleStop6 = Color(RUMI_GRADIENT_SUBTLE_LIGHT[5]),
        gradientActionStop1 = Color(RUMI_GRADIENT_ACTION_LIGHT[0]),
        gradientActionStop2 = Color(RUMI_GRADIENT_ACTION_LIGHT[1]),
        gradientActionStop3 = Color(RUMI_GRADIENT_ACTION_LIGHT[2]),
        gradientActionStop4 = Color(RUMI_GRADIENT_ACTION_LIGHT[3]),
    ),
    dark = compoundColorsDark.copy(
        bgActionPrimaryRest = Color(RUMI_NAVY_DARK_REST),
        bgActionPrimaryHovered = Color(RUMI_NAVY_DARK_HOVERED),
        bgActionPrimaryPressed = Color(RUMI_NAVY_DARK_PRESSED),
        bgAccentRest = Color(RUMI_CORAL_REST),
        bgAccentHovered = Color(RUMI_CORAL_HOVERED),
        bgAccentPressed = Color(RUMI_CORAL_PRESSED),
        bgAccentSubtle = Color(RUMI_CORAL_SUBTLE),
        iconAccentPrimary = Color(RUMI_CORAL_REST),
        iconAccentTertiary = Color(RUMI_CORAL_HOVERED),
        borderAccentPrimary = Color(RUMI_CORAL_REST),
        borderAccentSubtle = Color(RUMI_CORAL_HOVERED),
        textActionAccent = Color(RUMI_CORAL_PRESSED),
        gradientSubtleStop1 = Color(RUMI_GRADIENT_SUBTLE_DARK[0]),
        gradientSubtleStop2 = Color(RUMI_GRADIENT_SUBTLE_DARK[1]),
        gradientSubtleStop3 = Color(RUMI_GRADIENT_SUBTLE_DARK[2]),
        gradientSubtleStop4 = Color(RUMI_GRADIENT_SUBTLE_DARK[3]),
        gradientSubtleStop5 = Color(RUMI_GRADIENT_SUBTLE_DARK[4]),
        gradientSubtleStop6 = Color(RUMI_GRADIENT_SUBTLE_DARK[5]),
        gradientActionStop1 = Color(RUMI_GRADIENT_ACTION_DARK[0]),
        gradientActionStop2 = Color(RUMI_GRADIENT_ACTION_DARK[1]),
        gradientActionStop3 = Color(RUMI_GRADIENT_ACTION_DARK[2]),
        gradientActionStop4 = Color(RUMI_GRADIENT_ACTION_DARK[3]),
    ),
)
