package mobile.mycloset.model;

import mobile.mycloset.R;

public class WeatherParser {
    public enum Weather {
        SUNNY, RAINY, WINDY, CLOUDY, SNOWY
    }

    public static Weather parseWeatherCode(int code) {
        if (code >= 900) {
            return Weather.WINDY;
        } else if (code > 800) {
            return Weather.CLOUDY;
        } else if (code == 800) {
            return Weather.SUNNY;
        } else if (code >= 700) {
            return Weather.CLOUDY;
        } else if (code >= 600) {
            return Weather.SNOWY;
        } else if (code >= 500) {
            return Weather.RAINY;
        } else if (code >= 300) {
            return Weather.RAINY;
        } else if (code >= 200) {
            return Weather.RAINY;
        }
        return Weather.SUNNY;
    }

    public static int weatherToDrawable(Weather weather) {
        switch (weather) {
            case SUNNY:
                return R.drawable.sunny;
            case RAINY:
                return R.drawable.rainy;
            case WINDY:
                return R.drawable.windy;
            case CLOUDY:
                return R.drawable.cloudy;
            case SNOWY:
                return R.drawable.snowy;
            default:
                return R.drawable.sunny;
        }
    }
}