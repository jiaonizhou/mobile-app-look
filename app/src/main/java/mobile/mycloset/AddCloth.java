package mobile.mycloset;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.content.DialogInterface;
import android.app.Fragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import mobile.mycloset.model.Bag;
import mobile.mycloset.model.Bottom;
import mobile.mycloset.model.Closet;
import mobile.mycloset.model.OnePiece;
import mobile.mycloset.model.Top;
import mobile.mycloset.model.Shoe;
import mobile.mycloset.model.WeatherParser;

public class AddCloth extends Fragment implements AdapterView.OnItemSelectedListener, NumberPicker.OnValueChangeListener {

    String[] type = {"Top", "Bottom", "Dress","Accessory", "Shoes", "Bag"};
    String[] min_range = {"30","40","50"};
    String[] max_range = {"50","60","70","80","90"};
    String fileName;
    Bag bag;
    Bottom bottom;
    OnePiece onePiece;
    Shoe shoes;
    Top top;
        //Accessory accs;
    int selectedType = 0;
    int selectedMin = 30;
    int selectedMax = 50;
    List<WeatherParser.Weather> selectedWeather = new ArrayList<>();

    private static AddCloth fragment;

    private View view;

    public static AddCloth getInstance() {
        if (fragment == null) {
            fragment = new AddCloth();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_add_cloth, container, false);
        initView();
        return view;
    }

    protected void initView() {
        Log.i("getfilepath: ", fileName);
        //fileName = fileName.substring(8);

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        imageView.setImageURI(Uri.parse(fileName));

        Button bTakePic = (Button)view.findViewById(R.id.button);
        bTakePic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String sfile = fileName.substring(7);
                File file = new File(sfile);
                boolean suc = file.delete();
                Log.i("file", Boolean.toString(suc));
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileName = getOutputFileName();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(fileName));
                startActivityForResult(intent,1234);
            }
        });


        Button bNext = (Button)view.findViewById(R.id.next);
        bNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                typePicker() ;
            }
        });
    }

    public void typePicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final NumberPicker np_tppe = new NumberPicker(getContext());
        //NumberPicker np = (NumberPicker) findViewById(R.id.numberPicker1);
        np_tppe.setMaxValue(type.length - 1);
        np_tppe.setMinValue(0);
        np_tppe.setDisplayedValues(type);
        np_tppe.setWrapSelectorWheel(false);

        builder.setTitle("Choose Cloth Type")
                .setView(np_tppe);

        np_tppe.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                selectedType = np_tppe.getValue();
                Log.i("type",Integer.toString(selectedType));
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do something with value!

                weatherPicker();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Cancel.
            }
        });

        builder.create().show();
    }

    public void weatherPicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View npView = inflater.inflate(R.layout.weather_picker, null);

        // SUNNY
        final CheckBox chkbox1 = (CheckBox) npView.findViewById(R.id.checkBox);
        // CLOUDY
        final CheckBox chkbox2 = (CheckBox) npView.findViewById(R.id.checkBox2);
        // RAINY
        final CheckBox chkbox3 = (CheckBox) npView.findViewById(R.id.checkBox3);
        // WINDY
        final CheckBox chkbox4 = (CheckBox) npView.findViewById(R.id.checkBox4);
        // SNOWY
        final CheckBox chkbox5 = (CheckBox) npView.findViewById(R.id.checkBox5);

        builder.setTitle("Cloth is good for")
                .setView(npView);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (chkbox1.isChecked()){
                    selectedWeather.add(WeatherParser.Weather.SUNNY);
                }
                if (chkbox2.isChecked()){
                    selectedWeather.add(WeatherParser.Weather.CLOUDY);
                }
                if (chkbox3.isChecked()){
                    selectedWeather.add(WeatherParser.Weather.RAINY);
                }
                if (chkbox4.isChecked()){
                    selectedWeather.add(WeatherParser.Weather.WINDY);
                }
                if (chkbox5.isChecked()){
                    selectedWeather.add(WeatherParser.Weather.SNOWY);
                }

                // Do something with value!

                warmthPicker();
            }
        });

        builder.setNeutralButton("Back", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do something with value!
                typePicker();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Cancel.
            }
        });

        builder.create().show();
    }


    public void warmthPicker() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View npView = inflater.inflate(R.layout.warmth_picker, null);

        final NumberPicker min_np = (NumberPicker) npView.findViewById(R.id.minPicker);
        min_np.setMaxValue(min_range.length-1);
        min_np.setMinValue(0);
        min_np.setDisplayedValues(min_range);
        min_np.setWrapSelectorWheel(true);

        final NumberPicker max_np = (NumberPicker) npView.findViewById(R.id.maxPicker);
        max_np.setMaxValue(max_range.length - 1);
        max_np.setMinValue(0);
        max_np.setDisplayedValues(max_range);
        max_np.setWrapSelectorWheel(true);

        min_np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                selectedMin = Integer.parseInt(min_range[min_np.getValue()]);
                Log.i("min",min_range[min_np.getValue()]);
            }
        });

        max_np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                selectedMax = Integer.parseInt(max_range[max_np.getValue()]);
            }
        });

        builder.setTitle("Choose Warmth")
                .setView(npView);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do something with value!
                switch (selectedType){
                    case 0: top = new Top();
                        top.id = UUID.randomUUID().toString();
                        top.weathers = selectedWeather;
                        top.minTemp = selectedMin;
                        top.maxTemp = selectedMax;
                        top.filePath = fileName;
                        Closet.getCloset().tops.add(top);
                        break;
                    case 1: bottom = new Bottom();
                        bottom.id = UUID.randomUUID().toString();
                        bottom.weathers = selectedWeather;
                        bottom.minTemp = selectedMin;
                        bottom.maxTemp = selectedMax;
                        bottom.filePath = fileName;
                        Closet.getCloset().bottoms.add(bottom);
                        break;
                    case 2:  onePiece = new OnePiece();
                        onePiece.id = UUID.randomUUID().toString();
                        onePiece.weathers = selectedWeather;
                        onePiece.minTemp = selectedMin;
                        onePiece.maxTemp = selectedMax;
                        onePiece.filePath = fileName;
                        Closet.getCloset().onePieces.add(onePiece);
                        break;
                    case 4: shoes = new Shoe();
                        shoes.id = UUID.randomUUID().toString();
                        shoes.weathers = selectedWeather;
                        shoes.minTemp = selectedMin;
                        shoes.maxTemp = selectedMax;
                        shoes.filePath = fileName;
                        Closet.getCloset().shoes.add(shoes);
                        break;
                    case 5: bag = new Bag();
                        bag.id = UUID.randomUUID().toString();
                        bag.weathers = selectedWeather;
                        bag.minTemp = selectedMin;
                        bag.maxTemp = selectedMax;
                        bag.filePath = fileName;
                        Closet.getCloset().bags.add(bag);
                        break;
                }

                Closet.getCloset().save(getActivity());

            }
        });

        builder.setNeutralButton("Back", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do something with value!
                weatherPicker();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Cancel.
            }
        });

        builder.create().show();

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 1234) return;

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        imageView.setImageURI(Uri.parse(fileName));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }
}

