package mobile.mycloset;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;


import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

import mobile.mycloset.model.Apparel;
import mobile.mycloset.model.Closet;
import mobile.mycloset.model.WeatherParser;
import mobile.mycloset.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppFragmentActivity {

    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private AHBottomNavigation bottomNavigation;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Closet.getCloset().load(this).initCloset(this);
        initUI();
        acquireRunTimePermissions();
    }

    private void initUI() {

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);


        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.notification_background, R.color.color_tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.notification_background, R.color.color_tab_2);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.notification_background, R.color.color_tab_3);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_4, R.drawable.notification_background, R.color.color_tab_4);


        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);

        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                if (!wasSelected) {
                    Fragment fragment = null;
                    if (position == 0) {
                        fragment = TodayFragment.getInstance();
                        newFragment(R.id.fragment_container, fragment);
                    } else if (position == 1) {
                        fragment = ClosetFragment.getInstance();
                        newFragment(R.id.fragment_container, fragment);
                    } else if (position == 3) {
                        fragment = FavFragment.getInstance();
                        newFragment(R.id.fragment_container, fragment);
                    } else if (position == 2){
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileName = getOutputFileName();
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(fileName));

                        startActivityForResult(intent,1234);
                    }
                    //newFragment(R.id.fragment_container, fragment);
                }
            }
        });

        newFragment(R.id.fragment_container, TodayFragment.getInstance());
    }
    /**
     * check camera result
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Intent intent = new Intent(this, AddCloth.class);
            intent.putExtra("filename", fileName);
            startActivity(intent);
        }
    }

    /**
     * get piture file name
     * @return filename
     */
    private String getOutputFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename =
                "file://"
                        + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + "/JPEG_"
                        + timeStamp
                        + ".jpg";
        Log.i("filepath: ", filename);
        return filename;
    }

    /**
     * Update the bottom navigation colored param
     */
    public void updateBottomNavigationColor(boolean isColored) {
        bottomNavigation.setColored(isColored);
    }

    /**
     * Return if the bottom navigation is colored
     */
    public boolean isBottomNavigationColored() {
        return bottomNavigation.isColored();
    }

    /**
     * Return the number of items in the bottom navigation
     */
    public int getBottomNavigationNbItems() {
        return bottomNavigation.getItemsCount();
    }

    private void acquireRunTimePermissions() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    111);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode != 111) return;
        if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(getApplicationContext(), "Great! We have the permission!", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(getApplicationContext(), "Cannot write to external storage! App will not work properly!", Toast.LENGTH_SHORT).show();
        }
    }

}
