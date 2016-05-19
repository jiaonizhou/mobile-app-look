package mobile.mycloset;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Cindy on 5/18/16.
 */
public class ClosetAdapter extends BaseAdapter {
    private Context context;
    Integer[] buttonList;

    public ClosetAdapter(Context callingActivityContext,
                         Integer[] buttonList) {
        context = callingActivityContext;
        this.buttonList = buttonList;
    }
    @Override
    public int getCount() {
        return buttonList.length;
    }

    @Override
    public Object getItem(int position) {
        return buttonList[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(20, 10, 10, 10);
            imageView.setMaxWidth(40);
            imageView.setMaxHeight(50);
        }
        else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(buttonList[position]);
        return imageView;
    }
}
