# Analytics-Kotlin Castle

Add Castle support to your applications via this plugin for [Analytics-Kotlin](https://github.com/segmentio/analytics-kotlin)

## Adding the dependency
To install the Segment-Castle integration, simply add this line to your gradle file:

```
implementation 'com.segment.analytics.kotlin.destinations:castle:<latest_version>'
```
Or the following for Kotlin DSL
```
implementation("com.segment.analytics.kotlin.destinations:castle:<latest_version>")
```

## Using the Plugin in your App

Open the file where you setup and configure the Analytics-Kotlin library. Add this plugin to the list of imports.

```
import com.segment.analytics.kotlin.destinations.castle.CastleDestination
```

Just under your Analytics-Kotlin library setup, call `analytics.add(plugin = ...)` to add an instance of the plugin to the Analytics timeline.

```
    analytics = Analytics("<YOUR WRITE KEY>", applicationContext) {
        this.flushAt = 3
        this.trackApplicationLifecycleEvents = true
    }
    analytics.add(plugin = CastleDestination(userJwt = "<USER_JWT>"))
```

Your events will now be automatically sent to Castle.