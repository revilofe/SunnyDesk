/***
Copyright (c) 2021 Razeware LLC

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or
sell copies of the Software, and to permit persons to whom
the Software is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

Notwithstanding the foregoing, you may not use, copy, modify,
merge, publish, distribute, sublicense, create a derivative work,
and/or sell copies of the Software in any work that is designed,
intended, or marketed for pedagogical or instructional purposes
related to programming, coding, application development, or
information technology. Permission for such use, copying,
modification, merger, publication, distribution, sublicensing,
creation of derivative works, or sale is expressly withheld.

This project and source code may use libraries or frameworks
that are released under various Open-Source licenses. Use of
those libraries and frameworks are governed by their own
individual licenses.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
DEALINGS IN THE SOFTWARE.
 ***/

class WeatherTransformer {
  fun transform(response: WeatherResponse): WeatherResults {
    val current = extractCurrentWeatherFrom(response)
    val forecast = extractForecastWeatherFrom(response)

    return WeatherResults(
      currentWeather = current,
      forecast = forecast,
    )
  }

  private fun extractCurrentWeatherFrom(response: WeatherResponse): WeatherCard {
    return WeatherCard(
      condition = response.current.condition.text,
      iconUrl = "https:" + response.current.condition.icon.replace("64x64", "128x128"),
      temperature = response.current.tempC,
      feelsLike = response.current.feelslikeC,
    )
  }

  private fun extractForecastWeatherFrom(response: WeatherResponse): List<WeatherCard> {
    return response.forecast.forecastday.map { forecastDay ->
      WeatherCard(
        condition = forecastDay.day.condition.text,
        iconUrl = "https:" + forecastDay.day.condition.icon,
        temperature = forecastDay.day.avgtempC,
        feelsLike = avgFeelsLike(forecastDay),
        chanceOfRain = avgChanceOfRain(forecastDay),
      )
    }
  }

  private fun avgFeelsLike(forecastDay: Forecastday): Double =
    forecastDay.hour.map(Hour::feelslikeC).average()

  private fun avgChanceOfRain(forecastDay: Forecastday): Double =
    forecastDay.hour.map(Hour::chanceOfRain).average()
}
