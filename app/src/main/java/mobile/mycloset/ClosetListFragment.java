package mobile.mycloset;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;


import java.util.List;

import mobile.mycloset.model.Apparel;
import mobile.mycloset.model.Closet;

public class ClosetListFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static abstract class OnClickItemListener<VH extends Apparel> {
        public abstract void onClick(AdapterView<?> parent, View view, VH apparel);
    }

    private static ClosetListFragment fragment;

    private View view;
    private Apparel.ApprarelType type;
    private OnClickItemListener listener;
    private Context context;

    public static ClosetListFragment getInstance(Apparel.ApprarelType type, OnClickItemListener listener) {
        fragment = new ClosetListFragment();
        fragment.type = type;
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.closet_list_view, container, false);
        initClosetListView(this.view);
        return view;
    }

    public void initClosetListView(View view) {
        final GridView gridView = (GridView)view.findViewById(R.id.gridView2);
        ClosetAdapter adapter = new ClosetAdapter(getContext(), Closet.getCloset().getAllApparel(type));
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        final TextView titleView = (TextView)view.findViewById(R.id.title);
        titleView.setText(Apparel.apparelString(type));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final GridView gridView = (GridView)this.view.findViewById(R.id.gridView2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final int finalPos = position;
        builder.setTitle("Look")
                .setMessage("delete picture?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which
                    ) {
                        Apparel apparel = Closet.getCloset().getAllApparel(type).get(finalPos);
                        Closet.getCloset().getAllApparel(type).remove(apparel);
                        Closet.getCloset().save(getActivity());
                        gridView.invalidateViews();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setCancelable(false)
        ;
        builder.create().show();
    }

}
