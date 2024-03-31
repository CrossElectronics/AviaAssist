package crosstech.aviaassist.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import crosstech.aviaassist.R
import crosstech.aviaassist.model.FlightData
import crosstech.aviaassist.model.FlightMission
import crosstech.aviaassist.model.FlightUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class FlightViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(FlightUiState())
    val uiState: StateFlow<FlightUiState> = _uiState.asStateFlow()
    lateinit var flightDataList: List<FlightData>
    lateinit var airportMap: Map<String, String>

    fun uploadMissionsGroupedByDate(missionsText: String){
        val missions: List<FlightMission> = FlightMission.parseListFromString(missionsText)
        val groupedMission = missions.groupBy { it.takeoffDateTime.toLocalDate() }
        _uiState.update {
            it.copy(missionsByDate = groupedMission)
        }
    }

    fun initFlightDataAndAirportMap(context: Context){
        val documentStream = context.resources.openRawResource(R.raw.flight_data)
        val bufferedReader = BufferedReader(InputStreamReader(documentStream))

        val flightDataMutableList = mutableListOf<FlightData>()
        val airports = mutableMapOf<String, String>()
        try {
            var line: String? = bufferedReader.readLine()
            while (line != null) {
                val fltDatum = FlightData.parseFromString(line)
                if (fltDatum != null) {
                    flightDataMutableList.add(fltDatum)
                }
                val airportPair = FlightData.parseAirports(line)
                if (!airports.containsKey(airportPair?.first?.first)){
                    if (airportPair != null) {
                        airports[airportPair.first.first] = airportPair.first.second
                    }
                }
                if (!airports.containsKey(airportPair?.second?.first)){
                    if (airportPair != null) {
                        airports[airportPair.second.first] = airportPair.second.second
                    }
                }
                // Next line
                line = bufferedReader.readLine()
            }
        } catch (e: IOException) {
            // Handle any potential IO exceptions gracefully
            Log.e("FlightData", "Error reading flight data file: ${e.message}")
        } finally {
            // Ensure resource closure to prevent leaks
            try {
                bufferedReader.close()
                documentStream.close()
            } catch (e: IOException) {
                Log.e("FlightData", "Error closing resources: ${e.message}")
            }
        }
        flightDataList = flightDataMutableList
        airportMap = airports
    }
}