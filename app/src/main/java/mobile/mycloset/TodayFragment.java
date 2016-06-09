package mobile.mycloset;

import android.app.Activity;
import android.app.Fragment;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
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
        final GridLayout suiteView = (GridLayout)view.findViewById(R.id.include);
        final ImageView weatherImage = (ImageView)view.findViewById(R.id.weather);
        final TextView tempView = (TextView)view.findViewById(R.id.temperature);
        final ImageView bagView = (ImageView)view.findViewById(R.id.bag_placeholder);
        final ImageView shoeView = (ImageView)view.findViewById(R.id.shoe_placeholder);
        final ImageView topView = (ImageView)view.findViewById(R.id.top_placeholder);
        final ImageView bottomView = (ImageView)view.findViewById(R.id.bottom_placeholder);
        final Button favButton = (Button)view.findViewById(R.id.fav);
        final Button shareButton = (Button)view.findViewById(R.id.share);
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
                                    bagView.setImageBitmap(suite.bag.getBitmap(getActivity()));
                                    shoeView.setImageBitmap(suite.shoe.getBitmap(getActivity()));
                                    topView.setImageBitmap(suite.top.getBitmap(getActivity()));
                                    bottomView.setImageBitmap(suite.bottom.getBitmap(getActivity()));

                                    bagView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ClosetListFragment fragment = ClosetListFragment.getInstance(Apparel.ApprarelType.BAG,
                                                    new ClosetListFragment.OnClickItemListener() {
                                                        @Override
                                                        public void onClick(AdapterView parent, View view, Apparel apparel) {
                                                            suite.bag = (Bag)apparel;
                                                            bagView.setImageBitmap(suite.bag.getBitmap(getActivity()));
                                                            activity.popFragment();
                                                        }
                                                    });
                                            activity.pushFragment(R.id.fragment_container, fragment);
                                        }
                                    });
                                    topView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ClosetListFragment fragment = ClosetListFragment.getInstance(Apparel.ApprarelType.TOP,
                                                    new ClosetListFragment.OnClickItemListener() {
                                                        @Override
                                                        public void onClick(AdapterView parent, View view, Apparel apparel) {
                                                            suite.top = (Top)apparel;
                                                            topView.setImageBitmap(suite.top.getBitmap(getActivity()));
                                                            activity.popFragment();
                                                        }
                                                    });
                                            activity.pushFragment(R.id.fragment_container, fragment);
                                        }
                                    });
                                    bottomView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ClosetListFragment fragment = ClosetListFragment.getInstance(Apparel.ApprarelType.BOTTOM,
                                                    new ClosetListFragment.OnClickItemListener() {
                                                        @Override
                                                        public void onClick(AdapterView parent, View view, Apparel apparel) {
                                                            suite.bottom = (Bottom)apparel;
                                                            bottomView.setImageBitmap(suite.bottom.getBitmap(getActivity()));
                                                            activity.popFragment();
                                                        }
                                                    });
                                            activity.pushFragment(R.id.fragment_container, fragment);
                                        }
                                    });
                                    shoeView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ClosetListFragment fragment = ClosetListFragment.getInstance(Apparel.ApprarelType.SHOES,
                                                    new ClosetListFragment.OnClickItemListener() {
                                                        @Override
                                                        public void onClick(AdapterView parent, View view, Apparel apparel) {
                                                            suite.shoe = (Shoe)apparel;
                                                            shoeView.setImageBitmap(suite.shoe.getBitmap(getActivity()));
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
                                        Closet.getCloset().favSuite(suite).save(activity);

                                    }
                                });

                                shareButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Create the new Intent using the 'Send' action.
                                        Intent share = new Intent(Intent.ACTION_SEND);

                                        // Set the MIME type
                                        share.setType("image/*");

                                        // Create the URI from the media
                                        File suiteImageFile = Utils.captureView(suiteView, "today_suite");
                                        Uri uri = Uri.fromFile(suiteImageFile);

                                        // Add the URI to the Intent.
                                        share.putExtra(Intent.EXTRA_STREAM, uri);

                                        // Broadcast the Intent.
                                        startActivity(Intent.createChooser(share, "Share to"));
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
