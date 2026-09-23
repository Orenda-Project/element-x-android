# Rumi Messenger, Android

This is a branded fork of [Element X for Android](https://github.com/element-hq/element-x-android),
built for [Rumi Messenger](https://github.com/Orenda-Project/rumi-messenger), a self-hosted
messenger for school teams with Rumi, the teaching companion, one tap away.

## Why this is a fork, and not configuration

Rumi Messenger deliberately configures Synapse and Element Web rather than forking them; the
reasoning is in [rumi-messenger's architecture doc](https://github.com/Orenda-Project/rumi-messenger/blob/main/docs/ARCHITECTURE.md#why-we-do-not-fork-element-or-synapse).
A phone app is the one exception: a teacher needs to install something already named Rumi, with our
icon, and a default server already set, and none of that reaches through a config file the way a
web app's theme does.

## What changes from upstream

Tracked plainly, so the diff against `element-hq/element-x-android` stays reviewable:

- App name, icon, and default homeserver, so a teacher signs in without typing a server address.
- Rumi's colours where the app's own theming reaches them.
- The onboarding hero: Element X looks up a drawable named `onboarding_logo` and, when it exists,
  draws it in place of its own logo, headline, subtitle and gradient. Ours carries the Rumi mark and
  the line "You're Not Teaching Alone", in light and night variants, at five densities. Zero Kotlin.
- Nothing else. Matrix protocol handling, encryption, and the rest of the app are upstream's, unmodified.

## Licence

AGPL-3.0, same as upstream. Forking a released, unmodified build and shipping it under our own name
means our modified source must stay public here. See rumi-messenger's own README for the fuller
licensing table across the whole project.

## Keeping this current

Element X for Android ships roughly every one to two weeks. See
[rumi-messenger#12](https://github.com/Orenda-Project/rumi-messenger/issues/12) for the plan to alert
on new upstream releases automatically, and merge `upstream/develop` on a schedule, not on memory.
