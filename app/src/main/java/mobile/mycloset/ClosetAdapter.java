package mobile.mycloset;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

import mobile.mycloset.model.Apparel;

public class ClosetAdapter extends BaseAdapter {
    private Context context;
    List<? extends Apparel> buttonList;

    public ClosetAdapter(Context callingActivityContext,
                         List<? extends Apparel> buttonList) {
        context = callingActivityContext;
        this.buttonList = buttonList;
    }
    @Override
    public int getCount() {
        return buttonList.size();
    }

    @Override
    public Object getItem(int position) {
        return buttonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null){
            GridView grid = (GridView)parent;
            int size = grid.getColumnWidth();

            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(size, size));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(20, 10, 10, 10);
            imageView.setMaxWidth(40);
            imageView.setMaxHeight(50);
        }
        else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(buttonList.get(position).getBitmap(context));
        return imageView;
    }
}
