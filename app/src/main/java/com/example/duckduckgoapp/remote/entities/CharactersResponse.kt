package com.example.duckduckgoapp.remote.entities

import com.example.duckduckgoapp.local.models.Character
import com.example.duckduckgoapp.remote.utils.NetworkEndPointHelper
import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    val abstract: String?,
    val abstractSource: String?,
    val abstractText: String?,
    val abstractURL: String?,
    val answer: String?,
    val answerType: String?,
    val definition: String?,
    val definitionSource: String?,
    val definitionURL: String?,
    val entity: String?,
    val heading: String?,
    val image: String?,
    val imageHeight: Long?,
    val imageIsLogo: Long?,
    val imageWidth: Long?,
    val infobox: String?,
    val redirect: String?,
    @SerializedName("RelatedTopics")
    val relatedTopics: List<RelatedTopic>?,
    val results: List<Any?>?,
    val type: String?,
    val meta: Meta?
) {
    data class Meta(
        val attribution: Any? = null,
        val blockgroup: Any? = null,
        val createdDate: Any? = null,
        val description: String?,
        val designer: Any? = null,
        val devDate: Any? = null,
        val devMilestone: String?,
        val developer: List<Developer>?,
        val exampleQuery: String?,
        val id: String?,
        val isStackexchange: Any? = null,
        val jsCallbackName: String?,
        val liveDate: Any? = null,
        val maintainer: Maintainer?,
        val name: String?,
        val perlModule: String?,
        val producer: Any? = null,
        val productionState: String?,
        val repo: String?,
        val signalFrom: String?,
        val srcDomain: String?,
        val srcID: Long?,
        val srcName: String?,
        val srcOptions: SrcOptions?,
        val srcURL: Any? = null,
        val status: String?,
        val tab: String?,
        val topic: List<String>?,
        val unsafe: Long?
    ) {
        data class Developer(
            val name: String?,
            val type: String?,
            val url: String?
        )

        data class Maintainer(
            val github: String?
        )

        data class SrcOptions(
            val directory: String?,
            val isFanon: Long?,
            val isMediawiki: Long?,
            val isWikipedia: Long?,
            val language: String?,
            val minAbstractLength: String?,
            val skipAbstract: Long?,
            val skipAbstractParen: Long?,
            val skipEnd: String?,
            val skipIcon: Long?,
            val skipImageName: Long?,
            val skipQr: String?,
            val sourceSkip: String?,
            val srcInfo: String?
        )
    }

    data class RelatedTopic(
        @SerializedName("FirstURL")
        val firstURL: String?, // Name is here
        @SerializedName("Icon")
        val icon: Icon?,
        val result: String?,
        @SerializedName("Text")
        val text: String? // Description is here
    ) {
        data class Icon(
            val height: String?,
            @SerializedName("URL")
            val url: String?, // Image is here
            val width: String?
        )
    }

    fun toCharacterList(): List<Character> {
        return relatedTopics?.map { characterDetails ->

            val characterName =
                characterDetails.firstURL
                    ?.removePrefix("https://duckduckgo.com/")
                    ?.replace("_", " ")
                    // TODO: The following 3 substrings may be caused by a bad parsing from GSON/Retrofit
                    ?.replace("%22", "")
                    ?.replace("%2C", "")
                    ?.replace("%26", "")
            val characterImageUrl =
                "${NetworkEndPointHelper.duckDuckGoBaseURL}${characterDetails.icon?.url}"

            Character(
                name = characterName ?: "Unknown",
                description = characterDetails.text ?: "No description",
                imageURL = characterImageUrl,
                duckDuckLink = characterDetails.firstURL ?: ""
            )
        } ?: emptyList()
    }
}
