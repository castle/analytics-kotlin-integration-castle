package com.segment.analytics.kotlin.destinations.castle

import android.app.Application
import com.segment.analytics.kotlin.core.BaseEvent
import com.segment.analytics.kotlin.core.ScreenEvent
import com.segment.analytics.kotlin.core.Settings
import com.segment.analytics.kotlin.core.TrackEvent
import com.segment.analytics.kotlin.core.platform.DestinationPlugin
import com.segment.analytics.kotlin.core.platform.Plugin
import com.segment.analytics.kotlin.core.utilities.toContent
import io.castle.android.Castle
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

class CastleDestination(private val application: Application, private val userJwt: String) : DestinationPlugin() {
    companion object {
        private const val CASTLE_FULL_KEY = "Castle"
    }

    private var castleSettings: CastleSettings? = null
    override val key: String = CASTLE_FULL_KEY

    override fun update(settings: Settings, type: Plugin.UpdateType) {
        super.update(settings, type)
        if (settings.hasIntegrationSettings(this)) {
            this.castleSettings = settings.destinationSettings(key, CastleSettings.serializer())
            if (type == Plugin.UpdateType.Initial) {
                Castle.configure(application, castleSettings!!.publishableKey)
                Castle.userJwt(userJwt)
            }
        }
    }

    override fun screen(payload: ScreenEvent): BaseEvent {
        val screenName = payload.name

        Castle.screen(screenName)

        return payload
    }

    override fun track(payload: TrackEvent): BaseEvent {
        val event = payload.event

        val properties: Map<String, String> = payload.properties.asStringMap()

        Castle.custom(event, properties)

        return payload
    }
}

/**
 * Castle Settings data class.
 */
@Serializable
data class CastleSettings(
    // Castle Publishable Key
    var publishableKey: String,
)

private fun JsonObject.asStringMap(): Map<String, String> = this.mapValues { (_, value) ->
    value.toContent().toString()
}