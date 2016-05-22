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

import java.util.List;

import mobile.mycloset.model.Apparel;
import mobile.mycloset.model.Closet;

/**
 * Created by xrz on 5/19/16.
 */
public class ClosetListFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static abstract class OnClickItemListener<VH extends Apparel> {
        public abstract void onClick(AdapterView<?> parent, View view, VH apparel);
    }

    private static ClosetListFragment fragment;

    private View view;
    private Closet.ApprarelType type;
    private OnClickItemListener listener;

    public static ClosetListFragment getInstance(Closet.ApprarelType type, OnClickItemListener listener) {
        fragment = new ClosetListFragment();
        fragment.type = type;
        fragment.listener = listener;
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
        if (listener == null) {
            return;
        }
        Apparel apparel = Closet.getCloset().getAllApparel(type).get(position);
        listener.onClick(parent, view, apparel);
    }

}
