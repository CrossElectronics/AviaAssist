package crosstech.aviaassist

import crosstech.aviaassist.model.FlightData
import org.junit.Test
import org.junit.Assert.*

class FlightDataUnitTest {
    val raw = buildString {
        appendLine("杭州,HGH,北京,PEK,319,1.20,136.00")
        appendLine("杭州,HGH,北京,PEK,320,1.20,136.00")
        appendLine("杭州,HGH,北京,PEK,737,1.20,136.00")
        appendLine("杭州,HGH,北京,PEK,767,1.20,130.00")
        appendLine("杭州,HGH,广州,CAN,319,1.20,124.00")
        appendLine("杭州,HGH,广州,CAN,320,1.20,124.00")
        appendLine("杭州,HGH,贵阳,KWE,319,1.20,147.00")
        appendLine("杭州,HGH,贵阳,KWE,320,1.20,147.00")
        appendLine("杭州,HGH,哈尔滨,HRB,319,1.00,185.00")
        appendLine("杭州,HGH,海口,HAK,319,1.00,168.00")
        appendLine("杭州,HGH,昆明,KMG,320,1.20,175.00")
        appendLine("杭州,HGH,北京,PEK,330,1.20,140.00")
        appendLine("杭州,HGH,深圳,SZX,320,1.20,140.00")
        appendLine("杭州,HGH,西安,XIY,320,1.00,152.00")
        appendLine("杭州,HGH,成都,CTU,320,1.00,195.00")
        append("杭州,HGH,重庆,CKG,320,1.00,165.00")
    }// 11 airports here

    @Test
    fun flightData_Init_Success() {
        val rawList = raw.split("\n")
        val data = mutableListOf<FlightData>()
        for (str in rawList) {
            FlightData.parseFromString(str)?.let { data.add(it) }
        }
        assertEquals(rawList.size, data.size)
    }

    @Test fun airports_Init_Success() {
        val rawList = raw.split("\n")
        val data = mutableMapOf<String, String>()
        for (str in rawList) {
            FlightData.parseAirports(str)?.let {
                if (!data.containsKey(it.first.first)) {
                    data[it.first.first] = it.first.second
                }
                if (!data.containsKey(it.second.first)) {
                    data[it.second.first] = it.second.second
                }
            }
        }
        assertEquals(11, data.size)
    }
}