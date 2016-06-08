package mobile.mycloset.model;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Apparel implements Serializable {

    public enum ApprarelType {
        TOP, BOTTOM, SHOES, BAG, DRESS, ACCESSORY;
    }

    public String imageName;
    public String filePath;
    public String id;
    public List<WeatherParser.Weather> weathers = new ArrayList<>();
    public double minTemp;
    public double maxTemp;
    public ApprarelType type;

    public boolean isSuitable(WeatherParser.Weather weather, double temperature) {
        return weathers.contains(weather) && temperature >= minTemp &&
                temperature <= maxTemp;
    }

    public Bitmap getBitmap(Context context) {
        if (imageName != null) {
            return BitmapFactory.decodeResource(context.getResources(),
                    context.getResources().getIdentifier(imageName, "drawable", context.getPackageName()));
        } else if (filePath != null) {
            return BitmapFactory.decodeFile(filePath.substring(7));
        }
        return null;
    }

    public boolean isEqual(Apparel a) {
        return id == a.id;
    }

    public static String apparelString(ApprarelType type) {
        switch (type) {
            case TOP:
                return "Top";
            case BOTTOM:
                return "Bottom";
            case SHOES:
                return "Shoes";
            case BAG:
                return "Bag";
            case DRESS:
                return "Dress";
            case ACCESSORY:
                return "Accessory";
            default:
                return null;
        }
    }
}

