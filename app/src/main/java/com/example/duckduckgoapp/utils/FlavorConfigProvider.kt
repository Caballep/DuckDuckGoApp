package com.example.duckduckgoapp.utils

import com.example.duckduckgoapp.BuildConfig.FLAVOR

internal object FlavorConfigProvider {

    private const val theWire = "the+wire"
    private const val simpsons = "simpsons"

    // It would be better having the endpoint for each flavor in their corresponding project level resource folders, this is just to simplify things
    fun getDuckDuckEndPoint() =
        "http://api.duckduckgo.com/?q=${if (FLAVOR == "simpsons") simpsons else theWire
        }+characters&amp;format=json"
}