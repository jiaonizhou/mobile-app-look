package mobile.mycloset;

import android.app.Activity;
import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;

import mobile.mycloset.model.Apparel;
import mobile.mycloset.model.Bag;
import mobile.mycloset.model.Bottom;
import mobile.mycloset.model.Closet;
import mobile.mycloset.model.Shoe;
import mobile.mycloset.model.Top;
import mobile.mycloset.model.TopBottomSuite;
import mobile.mycloset.model.WeatherParser;
import mobile.mycloset.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by xrz on 5/14/16.
 */
public class TodayFragment extends Fragment {
    private static TodayFragment fragment;

    private View view;

    public static TodayFragment getInstance() {
        if (fragment == null) {
            fragment = new TodayFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.today_view, container, false);
        initTodayView(view);
        return view;
    }

    public void initTodayView(View view) {
        final ImageView weatherImage = (ImageView)view.findViewById(R.id.weather);
        final TextView tempView = (TextView)view.findViewById(R.id.temperature);
        final ImageView bagView = (ImageView)view.findViewById(R.id.bag_placeholder);
        final ImageView shoeView = (ImageView)view.findViewById(R.id.shoe_placeholder);
        final ImageView topView = (ImageView)view.findViewById(R.id.top_placeholder);
        final ImageView bottomView = (ImageView)view.findViewById(R.id.bottom_placeholder);
        final Button favButton = (Button)view.findViewById(R.id.fav);
        final MainActivity activity = (MainActivity)getActivity();
        try {
            Call weatherInfoCall = Utils.getWeatherCall(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final Utils.WeatherInfo weatherInfo = new Utils.WeatherInfo();
                    try {
                        JSONObject resp = new JSONObject(response.body().string());
                        int weatherCode = resp.getJSONArray("weather").getJSONObject(0).getInt("id");
                        double temperature = resp.getJSONObject("main").getDouble("temp");
                        weatherInfo.weather = WeatherParser.parseWeatherCode(weatherCode);
                        weatherInfo.temperature = temperature;

                        final TopBottomSuite suite = Closet.getCloset().getTopBottomSuite(weatherInfo);
                        // Set the weather info
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                weatherImage.setImageResource(WeatherParser.weatherToDrawable(weatherInfo.weather));
                                tempView.setText(weatherInfo.temperature + " F");

                                if (suite != null && suite.isValid()) {
                                    bagView.setImageResource(suite.bag.getResourceId(getActivity()));
                                    shoeView.setImageResource(suite.shoe.getResourceId(getActivity()));
                                    topView.setImageResource(suite.top.getResourceId(getActivity()));
                                    bottomView.setImageResource(suite.bottom.getResourceId(getActivity()));

                                    bagView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ClosetListFragment fragment = ClosetListFragment.getInstance(Closet.ApprarelType.BAG,
                                                    new ClosetListFragment.OnClickItemListener() {
                                                        @Override
                                                        public void onClick(AdapterView parent, View view, Apparel apparel) {
                                                            suite.bag = (Bag)apparel;
                                                            bagView.setImageResource(suite.bag.getResourceId(getActivity()));
                                                            activity.popFragment();
                                                        }
                                                    });
                                            activity.pushFragment(R.id.fragment_container, fragment);
                                        }
                                    });
                                    topView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ClosetListFragment fragment = ClosetListFragment.getInstance(Closet.ApprarelType.TOP,
                                                    new ClosetListFragment.OnClickItemListener() {
                                                        @Override
                                                        public void onClick(AdapterView parent, View view, Apparel apparel) {
                                                            suite.top = (Top)apparel;
                                                            topView.setImageResource(suite.top.getResourceId(getActivity()));
                                                            activity.popFragment();
                                                        }
                                                    });
                                            activity.pushFragment(R.id.fragment_container, fragment);
                                        }
                                    });
                                    bottomView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ClosetListFragment fragment = ClosetListFragment.getInstance(Closet.ApprarelType.BOTTOM,
                                                    new ClosetListFragment.OnClickItemListener() {
                                                        @Override
                                                        public void onClick(AdapterView parent, View view, Apparel apparel) {
                                                            suite.bottom = (Bottom)apparel;
                                                            bottomView.setImageResource(suite.bottom.getResourceId(getActivity()));
                                                            activity.popFragment();
                                                        }
                                                    });
                                            activity.pushFragment(R.id.fragment_container, fragment);
                                        }
                                    });
                                    shoeView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ClosetListFragment fragment = ClosetListFragment.getInstance(Closet.ApprarelType.SHOES,
                                                    new ClosetListFragment.OnClickItemListener() {
                                                        @Override
                                                        public void onClick(AdapterView parent, View view, Apparel apparel) {
                                                            suite.shoe = (Shoe)apparel;
                                                            shoeView.setImageResource(suite.shoe.getResourceId(getActivity()));
                                                            activity.popFragment();
                                                        }
                                                    });
                                            activity.pushFragment(R.id.fragment_container, fragment);
                                        }
                                    });
                                } else {
                                    Log.e(getClass().toString(), "The generated suite is invalid");
                                }

                                favButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Closet.getCloset().favSuite(suite);
                                    }
                                });
                            }
                        });
                    } catch (org.json.JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            return;
        }


    }
}
