package mobile.mycloset.model;


import android.app.Activity;
import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by xrz on 5/12/16.
 */
public class Apparel {
    public String imageName;
    public String id;
    public List<WeatherParser.Weather> weathers;
    public double minTemp;
    public double maxTemp;

    public boolean isSuitable(WeatherParser.Weather weather, double temperature) {
        return weathers.contains(weather) && temperature >= minTemp &&
                temperature <= maxTemp;
    }

    public int getResourceId(Activity context) {
        return context.getResources()
                .getIdentifier(imageName, "drawable", context.getPackageName());
    }

    public boolean isEqual(Apparel a) {
        return id == a.id;
    }
}

