package org.ajar.umbrallegacy

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.ajar.umbrallegacy.model.AbilityDefinition

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AbilityUnitTest {
    @Test
    fun describeAbilities() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        var count = 0;
        AbilityDefinition.allAbilities.forEach { Log.d("Ability", "${count++} ${it.name(appContext)}: ${it.describe(appContext)}") }
    }
}
