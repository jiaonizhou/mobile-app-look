package mobile.mycloset;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.view.View.OnClickListener;
import android.content.DialogInterface;

public class AddCloth extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NumberPicker.OnValueChangeListener {


    String[] category = {"Work", "Casual", "School", "Night"};
    String[] type = {"Top", "Bottom", "Dress","Accessory", "Shoes", "Bag"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cloth);

//        Spinner s_warmth = (Spinner) findViewById(R.id.warmth);
//        s_warmth.setAdapter(new ArrayAdapter<String>(
//                this, android.R.layout.simple_spinner_dropdown_item, warmth)
//        );
//        s_warmth.setOnItemSelectedListener(this);

        Spinner s_category = (Spinner) findViewById(R.id.category);
        s_category.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, category)
        );
        s_category.setOnItemSelectedListener(this);

        Spinner s_type = (Spinner) findViewById(R.id.type);
        s_type.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, type)
        );
        s_type.setOnItemSelectedListener(this);


        Button bNext = (Button) findViewById(R.id.next);
        bNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                warmthPicker();
            }
        });





    }
    public void warmthPicker() {
        final String[] warmth = new String[]{"Cold", "Cool", "Warm", "Hot"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddCloth.this);
        NumberPicker np = new NumberPicker(AddCloth.this);
        //NumberPicker np = (NumberPicker) findViewById(R.id.numberPicker1);
        np.setMaxValue(warmth.length-1);
        np.setMinValue(0);
        np.setDisplayedValues(warmth);
        np.setWrapSelectorWheel(false);

        builder.setTitle("Choose Warmth")
                .setView(np);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do something with value!

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Cancel.
            }
        });

        builder.create().show();


//        final Dialog dialog = new Dialog(AddCloth.this);
//        dialog.setTitle("Choose Warmth");
//        dialog.setContentView(R.layout.warmth_picker);
//        Button b1 = (Button) dialog.findViewById(R.id.button1);
//        Button b2 = (Button) dialog.findViewById(R.id.button2);
//        NumberPicker np = (NumberPicker) dialog.findViewById(R.id.numberPicker1);
//        np.setMaxValue(warmth.length-1);
//        np.setMinValue(0);
//        np.setDisplayedValues(warmth);
//        np.setWrapSelectorWheel(true);
//        b1.setOnClickListener(new OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                //tv.setText(String.valueOf(np.getValue()));
//                dialog.dismiss();
//            }
//        });
//        b2.setOnClickListener(new OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//        np.setFormatter(new NumberPicker.Formatter(){
//
//            @Override
//            public String format(int value) {
//                return warmth.(value);
//            }
//        });



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

