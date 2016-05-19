package mobile.mycloset;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

public class Closet extends AppCompatActivity  implements AdapterView.OnItemClickListener, View.OnClickListener{
    GridView gridView;
    Integer[] buttonList = {R.drawable.top_icon, R.drawable.bottom_icon, R.drawable.dress_icon, R.drawable.shoes_icon, R.drawable.ac_icon, R.drawable.look_icon};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ClosetAdapter(this, buttonList));
        gridView.setOnItemClickListener(this);

        // for test
        Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(this);



    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,AddCloth.class);
        startActivity(intent);

    }




}
