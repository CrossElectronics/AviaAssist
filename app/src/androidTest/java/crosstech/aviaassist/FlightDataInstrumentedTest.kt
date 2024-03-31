package crosstech.aviaassist

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import crosstech.aviaassist.ui.FlightViewModel

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FlightDataInstrumentedTest {
    @Test fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("crosstech.aviaassist", appContext.packageName)
    }

    @Test fun flightData_Initialization_Successful(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val flightVM = FlightViewModel()
        flightVM.initFlightDataAndAirportMap(context)
        Log.d("TEST", flightVM.flightDataList.size.toString())
        assertNotEquals(0, flightVM.flightDataList.size)
    }

    @Test fun airports_Initialization_Successful(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val flightVM = FlightViewModel()
        flightVM.initFlightDataAndAirportMap(context)
        Log.d("TEST", flightVM.airportMap.size.toString())
        assertNotEquals(0, flightVM.airportMap.size)
    }
}