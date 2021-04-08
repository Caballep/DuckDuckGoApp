package com.example.duckduckgoapp.utils

import com.example.duckduckgoapp.BuildConfig.FLAVOR

internal object FlavorConfigProvider {

    private const val simpsons_FLAVOR = "simpsons"
    private const val thewire_FLAVOR = "thewire"

    private const val theWireQueryParam = "the+wire+characters"
    private const val simpsonsQueryParam = "simpsons+characters"

    // Another approach is by having the values in a res xml file for each independent flavor
    fun getCharacterServiceQParameter(): String {
        return when(FLAVOR) {
            simpsons_FLAVOR -> simpsonsQueryParam
            thewire_FLAVOR -> theWireQueryParam
            else -> simpsons_FLAVOR // Simpson as default if there is a third flavor not listed
        }
    }
}
