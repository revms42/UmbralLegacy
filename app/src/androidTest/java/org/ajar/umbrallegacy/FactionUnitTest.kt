package org.ajar.umbrallegacy

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.ajar.umbrallegacy.model.Faction
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FactionUnitTest {
    @Test
    fun describeFactions() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        Faction.setup()

        Faction.values().forEach { Log.d("Faction", it.debugDescription(appContext)) }
    }
}