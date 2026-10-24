// Optional SDK-free geometry smoke harness: ./scripts/check-pure-kotlin.sh
import com.oxygen.weather.timeline.data.SampleForecast
import com.oxygen.weather.timeline.presentation.ElasticTimeAxis
import com.oxygen.weather.timeline.presentation.ForecastProjection
import kotlin.math.abs

private fun near(actual: Double, expected: Double) {
    check(abs(actual - expected) < 1e-7) { "Expected $expected, got $actual" }
}

fun main() {
    check(SampleForecast.hours.size == 192)
    check(SampleForecast.days.size == 8)
    check(SampleForecast.hours.zipWithNext().all { (a, b) -> a.time.isBefore(b.time) })
    for (focus in listOf(null, 0, 1, 4, 7)) {
        for (hour in listOf(0.0, 1.0, 23.7, 24.0, 35.25, 47.99, 72.0, 190.0)) {
            val x = ElasticTimeAxis.axis(hour, focus, 55.0, 16.0)
            near(ElasticTimeAxis.inverse(x, focus, 55.0, 16.0), hour)
            near(ElasticTimeAxis.xForHour(hour, hour, 180.0, focus, 55.0, 16.0), 180.0)
        }
    }
    near(ElasticTimeAxis.scrub(36.0, -48.0, 1, 55.0, 16.0, 191.0), 39.0)
    check(ForecastProjection.fromHourOffset(36.0, false).timeLabel == "DAY OVERVIEW")
    check(ForecastProjection.fromHourOffset(36.0, true).timeLabel.contains("PM"))
    println("PASS: pure Kotlin fixture, projection, round-trip geometry, stable playhead, drag direction")
}
