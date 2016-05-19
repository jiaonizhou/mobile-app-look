package mobile.mycloset;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import mobile.mycloset.model.Closet;

/**
 * Created by xrz on 5/19/16.
 */
public class ClosetListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static ClosetListFragment fragment;

    private View view;
    private Closet.ApprarelType type;

    public static ClosetListFragment getInstance(Closet.ApprarelType type) {
        fragment = new ClosetListFragment();
        fragment.type = type;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.closet_list_view, container, false);
        initClosetListView(view);
        return view;
    }

    public void initClosetListView(View view) {
        final GridView gridView = (GridView)view.findViewById(R.id.gridView2);
        ClosetAdapter adapter = new ClosetAdapter(getContext(), Closet.getCloset().getAllApparel(type));
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}
