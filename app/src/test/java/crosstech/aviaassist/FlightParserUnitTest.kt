package crosstech.aviaassist

import crosstech.aviaassist.model.FlightMission
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class FlightParserUnitTest {
    @Test fun flightParser_SingleFlightNoHeader_Success(){
        val testString = "【2024-3-24】\nCA1553/B-6792  WNZ15:25-18:00PEK"
        val result = FlightMission.parseListFromString(testString)
        assertEquals(1, result.size)
        val flt = result[0]
        assertEquals("CA1553", flt.flightNumber)
        assertEquals("WNZ", flt.originAirport)
    }
    @Test fun flightParser_MultipleFlightsWithHeader_Success(){
        val testString = "万益豪【七日机组排班,仅供参考】\n" +
                "【2024-3-23】\n" +
                "CA1575/B-6326  PEK10:30-11:45TAO\n" +
                "CA1576/B-6326  TAO12:55-14:20PEK\n" +
                "CA1544/B-1877  PEK16:15-18:35WNZ\n" +
                "【2024-3-24】\n" +
                "CA1553/B-6792  WNZ15:25-18:00PEK\n" +
                "【2024-3-25】\n" +
                "CA1537/B-6327  PEK08:00-10:35KHN\n" +
                "CA1538/B-6327  KHN12:00-14:05PEK\n" +
                "CA1724/B-30FU  PEK17:45-19:45HGH\n" +
                "【2024-3-28】\n" +
                "CA1739/B-1876  HGH21:25-00:25CTU\n" +
                "【2024-3-29】\n" +
                "CA1740/B-1876  CTU09:15-11:55HGH"
        val result = FlightMission.parseListFromString(testString)
        assertEquals(9, result.size)
    }

    @Test fun flightParser_MultipleFlightsWithHeader_OvernightCorrect(){
        val testString = "万益豪【七日机组排班,仅供参考】\n" +
                "【2024-3-28】\n" +
                "CA1739/B-1876  HGH21:25-00:25CTU\n" +
                "【2024-3-29】\n" +
                "CA1740/B-1876  CTU09:15-11:55HGH\n" +
                "【2024-3-30】\n" +
                "CA1743/B-6216  HGH06:30-08:55XIY\n" +
                "CA1744/B-6216  XIY10:05-12:25HGH"
        val result = FlightMission.parseListFromString(testString)
        assertEquals(4, result.size)
        val flt = result[0]
        assertEquals(28, flt.takeoffDateTime.dayOfMonth)
        assertEquals(29, flt.landingDateTime.dayOfMonth)
    }
}