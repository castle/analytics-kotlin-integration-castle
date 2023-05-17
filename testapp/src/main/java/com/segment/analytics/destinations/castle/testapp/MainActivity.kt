package com.segment.analytics.destinations.castle.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.segment.analytics.destinations.castle.testapp.R
import com.segment.analytics.destinations.castle.testapp.databinding.ActivityMainBinding
import com.segment.analytics.kotlin.android.Analytics
import com.segment.analytics.kotlin.core.*
import com.segment.analytics.kotlin.core.platform.plugins.logger.log
import com.segment.analytics.kotlin.destinations.castle.CastleDestination
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class MainActivity : AppCompatActivity() {
    private lateinit var analytics: Analytics
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Analytics.debugLogsEnabled = true

        analytics = Analytics(writeKey, applicationContext) {
            trackApplicationLifecycleEvents = true
            autoAddSegmentDestination = true
            flushAt = 3
        }

        val castleDestination = CastleDestination(application = application, userJwt = userJwt)
        analytics.add(plugin = castleDestination)

        binding.track.setOnClickListener {
            analytics.track(name = "Track")
            analytics.track(name = "Track With Properties", properties = properties)
        }

        binding.screen.setOnClickListener {
            analytics.screen(title = "Screen appeared")
            analytics.screen(title = "Screen With Properties", properties = properties)
        }

        binding.group.setOnClickListener {
            analytics.group(groupId = "12345-Group")
            analytics.log(message = "Started group")
        }

        binding.identify.setOnClickListener {
            analytics.identify(userId =  "X-1234567890")
        }
    }

    companion object {
        val properties = buildJsonObject {
            put("key", "value")
            put("number", 1)
            put("double", 3.2)
            put("array", buildJsonObject {
                put("first", "first_value")
                put("second", "second_value")
            })
        }

        val userJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImVjMjQ0ZjMwLTM0MzItNGJiYy04OGYxLTFlM2ZjMDFiYzFmZSIsImVtYWlsIjoidGVzdEBleGFtcGxlLmNvbSIsInJlZ2lzdGVyZWRfYXQiOiIyMDIyLTAxLTAxVDA5OjA2OjE0LjgwM1oifQ.eAwehcXZDBBrJClaE0bkO9XAr4U3vqKUpyZ-d3SxnH0"
        val writeKey = BuildConfig.WRITE_KEY
    }
}