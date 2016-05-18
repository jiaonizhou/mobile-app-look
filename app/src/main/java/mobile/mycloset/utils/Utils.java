package mobile.mycloset.utils;

import org.json.JSONObject;

import java.io.IOException;

import mobile.mycloset.model.WeatherParser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xrz on 5/12/16.
 */
public class Utils {
    public static class WeatherInfo {
        public WeatherParser.Weather weather = WeatherParser.Weather.SUNNY;
        public double temperature = 75;
    }

    public static OkHttpClient client = new OkHttpClient();

    public static Call getWeatherCall(Callback callback) throws IOException {
        Request request = new Request.Builder()
                .url("http://api.openweathermap.org/data/2.5/weather?zip=95035,us&units=imperial&appid=bf5b196f34e11557392b5eebb124d9cb")
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
}
