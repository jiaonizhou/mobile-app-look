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
import mobile.mycloset.utils.InternalStorage;
import mobile.mycloset.utils.Utils;

/**
 * Created by xrz on 5/15/16.
 */
public class Closet {

    private static Closet closet;

    public List<Top> tops = new ArrayList<>();
    public List<Bottom> bottoms = new ArrayList<>();
    public List<Shoe> shoes = new ArrayList<>();
    public List<Bag> bags = new ArrayList<>();

    public List<TopBottomSuite> topBottomSuites = new ArrayList<>();

    public static Closet getCloset() {
        if (closet == null) {
            closet = new Closet();
        }

        return closet;
    }

    public void initCloset(AppCompatActivity activity) {
        if (tops.size() > 0 || bottoms.size() > 0 || shoes.size() > 0 || bags.size() > 0) {
            return;
        }

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
                String id = jobj.getString("id");
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
    public Closet favSuite(TopBottomSuite suite) {
        // Check duplicates
        for (TopBottomSuite s: topBottomSuites) {
             if (s.isEqual(suite)) {
                 return this;
             }
        }
        topBottomSuites.add(suite);
        return this;
    }

    public List<? extends Apparel> getAllApparel(Apparel.ApprarelType type) {
        switch (type) {
            case TOP:
                return tops;
            case BOTTOM:
                return bottoms;
            case BAG:
                return bags;
            case SHOES:
                return shoes;
        }
        return new ArrayList<Top>();
    }

    public Closet save(AppCompatActivity activity) {
        try {
            InternalStorage.writeObject(activity, "ALL_TOPS", tops);
            InternalStorage.writeObject(activity, "ALL_BOTTOMS", bottoms);
            InternalStorage.writeObject(activity, "ALL_SHOES", shoes);
            InternalStorage.writeObject(activity, "ALL_BAGS", bags);
            InternalStorage.writeObject(activity, "ALL_SUITES", topBottomSuites);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public Closet load(AppCompatActivity activity) {
        try {
            tops = (List<Top>)InternalStorage.readObject(activity, "ALL_TOPS");
            bottoms = (List<Bottom>)InternalStorage.readObject(activity, "ALL_BOTTOMS");
            shoes = (List<Shoe>)InternalStorage.readObject(activity, "ALL_SHOES");
            bags = (List<Bag>)InternalStorage.readObject(activity, "ALL_BAGS");
            topBottomSuites = (List<TopBottomSuite>)InternalStorage.readObject(activity, "ALL_SUITES");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this;
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
