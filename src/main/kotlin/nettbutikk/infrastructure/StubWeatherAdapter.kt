package nettbutikk.infrastructure

import nettbutikk.core.WeatherForecast
import nettbutikk.core.WeatherService

class StubWeatherAdapter : WeatherService {

    override fun getForecast(): WeatherForecast {
        return WeatherForecast(willRain = false, description = "Clear skies (stub)")
    }
}
