package mobile.mycloset.model;

import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import mobile.mycloset.R;
import mobile.mycloset.utils.Utils;

/**
 * Created by xrz on 5/15/16.
 */
public class Closet {

    private static Closet closet;

    List<Top> tops = new ArrayList<>();
    List<Bottom> bottoms = new ArrayList<>();
    List<Shoe> shoes = new ArrayList<>();
    List<Bag> bags = new ArrayList<>();

    List<TopBottomSuite> topBottomSuites = new ArrayList<>();


    public static Closet getCloset() {
        if (closet == null) {
            closet = new Closet();
        }

        return closet;
    }

    public void initCloset(AppCompatActivity activity) {
        try {
            List<Apparel> apparel = initApparel(activity, R.raw.bag, Class.forName("mobile.mycloset.model.Bag"));
            for (Apparel a : apparel) {
                bags.add((Bag)a);
            }

            apparel = initApparel(activity, R.raw.bottom, Class.forName("mobile.mycloset.model.Bottom"));
            for (Apparel a : apparel) {
                bottoms.add((Bottom)a);
            }

            apparel = initApparel(activity, R.raw.shoe, Class.forName("mobile.mycloset.model.Shoe"));
            for (Apparel a : apparel) {
                shoes.add((Shoe)a);
            }

            apparel = initApparel(activity, R.raw.top, Class.forName("mobile.mycloset.model.Top"));
            for (Apparel a : apparel) {
                tops.add((Top)a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Apparel> initApparel(AppCompatActivity activity, int res, Class<?> klass) {
        InputStream inputStream = activity.getResources().openRawResource(res);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Apparel> apparels = new ArrayList<>();

        try {
            JSONArray jArray = new JSONArray(byteArrayOutputStream.toString());

            for (int i = 0; i < jArray.length(); ++i) {
                JSONObject jobj = jArray.getJSONObject(i);

                String imageName = jobj.getString("imageName");
                String id = UUID.randomUUID().toString();
                double minTemp = jobj.getDouble("minTemp");
                double maxTemp = jobj.getDouble("maxTemp");
                List<WeatherParser.Weather> weatherList = new ArrayList<WeatherParser.Weather>();

                for (int j = 0; j < jobj.getJSONArray("weather").length(); ++j) {
                    weatherList.add(WeatherParser.Weather.valueOf(jobj.getJSONArray("weather").getString(j)));
                }

                Constructor<?> ctor = klass.getConstructor();
                Apparel apparel = (Apparel)ctor.newInstance();
                apparel.imageName = imageName;
                apparel.id = id;
                apparel.weathers = weatherList;
                apparel.minTemp = minTemp;
                apparel.maxTemp = maxTemp;
                apparels.add(apparel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return apparels;
    }

    // Randomly get a suite based on current weather condition
    public TopBottomSuite getTopBottomSuite(Utils.WeatherInfo weatherInfo) {
        TopBottomSuite suite = new TopBottomSuite();
        suite.top = (Top)randomSelect(tops, weatherInfo);
        suite.bottom = (Bottom)randomSelect(bottoms, weatherInfo);
        suite.bag = (Bag)randomSelect(bags, weatherInfo);
        suite.shoe = (Shoe)randomSelect(shoes, weatherInfo);
        return suite;
    }

    // fav a suite
    public void favSuite(TopBottomSuite suite) {
        // Check duplicates
        for (TopBottomSuite s: topBottomSuites) {
             if (s.isEqual(suite)) {
                 return;
             }
        }
        topBottomSuites.add(suite);
    }

    private Apparel randomSelect(List<? extends Apparel> candidates, Utils.WeatherInfo weatherInfo) {
        List<Apparel> filtered = new ArrayList<Apparel>();
        for (Apparel apparel : candidates) {
            if (apparel.isSuitable(weatherInfo.weather, weatherInfo.temperature)) {
                filtered.add(apparel);
            }
        }
        if (filtered.size() == 0) {
            return null;
        }
        Random randomizer = new Random();
        return filtered.get(randomizer.nextInt(filtered.size()));
    }
}
