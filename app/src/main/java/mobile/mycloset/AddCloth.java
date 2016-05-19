package mobile.mycloset;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddCloth extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] warmth = {"Cold", "Cool", "Warm", "Hot"};
    String[] category = {"Work", "Casual", "School", "Night"};
    String[] type = {"Top", "Bottom", "Dress","Accessory", "Shoes", "Bag"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cloth);

        Spinner s_warmth = (Spinner) findViewById(R.id.warmth);
        s_warmth.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, warmth)
        );
        s_warmth.setOnItemSelectedListener(this);

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





    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

