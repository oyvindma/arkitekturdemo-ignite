package nettbutikk.core

data class WeatherForecast(val willRain: Boolean, val description: String)

interface WeatherService {
    fun getForecast(): WeatherForecast
}
