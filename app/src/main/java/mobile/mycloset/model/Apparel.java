package mobile.mycloset.model;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xrz on 5/12/16.
 */
public class Apparel implements Serializable {

    public enum ApprarelType {
        TOP, BOTTOM, SHOES, BAG, DRESS, ACCESSORY;
    }

    public String imageName;
    public String filePath;
    public String id;
    public List<WeatherParser.Weather> weathers;
    public double minTemp;
    public double maxTemp;
    public ApprarelType type;

    public boolean isSuitable(WeatherParser.Weather weather, double temperature) {
        return weathers.contains(weather) && temperature >= minTemp &&
                temperature <= maxTemp;
    }

    public Bitmap getBitmap(Context context) {
        return BitmapFactory.decodeResource(context.getResources(),
                context.getResources().getIdentifier(imageName, "drawable", context.getPackageName()));
    }

    public boolean isEqual(Apparel a) {
        return id == a.id;
    }
}

