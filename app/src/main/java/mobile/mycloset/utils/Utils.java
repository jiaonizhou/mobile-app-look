package mobile.mycloset.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.view.View;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import mobile.mycloset.model.WeatherParser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    public static File captureView(View view,String filename){
        //Create a Bitmap with the same dimensions
        Bitmap image = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(),
                Bitmap.Config.RGB_565);
        //Draw the view inside the Bitmap
        view.draw(new Canvas(image));

        //Store to sdcard
        try {
            String path = Environment.getExternalStorageDirectory().toString();
            File myFile = new File(path,filename);
            FileOutputStream out = new FileOutputStream(myFile);

            image.compress(Bitmap.CompressFormat.PNG, 90, out); //Output
            return myFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
