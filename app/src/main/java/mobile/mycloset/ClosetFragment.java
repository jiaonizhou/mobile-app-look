package mobile.mycloset;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mobile.mycloset.model.*;

/**
 * Created by xrz on 5/18/16.
 */
public class ClosetFragment extends Fragment {
    private static ClosetFragment fragment;

    private View view;

    public static ClosetFragment getInstance() {
        if (fragment == null) {
            fragment = new ClosetFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.closet_view, container, false);
        initClosetView(view);
        return view;
    }

    public void initClosetView(View view) {
        final Button topButton = (Button)view.findViewById(R.id.topCloset);
        final Button bottomButton = (Button)view.findViewById(R.id.bottomCloset);
        final Button dressButton = (Button)view.findViewById(R.id.dressCloset);
        final Button shoesButton = (Button)view.findViewById(R.id.shoeCloset);
        final Button accessaryButton = (Button)view.findViewById(R.id.accessaryCloset);
        final Button bagButton = (Button)view.findViewById(R.id.bagCloset);

        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClosetListFragment fragment = ClosetListFragment.getInstance(Closet.ApprarelType.TOP);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });
        bottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClosetListFragment fragment = ClosetListFragment.getInstance(Closet.ApprarelType.BOTTOM);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });
        dressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClosetListFragment fragment = ClosetListFragment.getInstance(Closet.ApprarelType.DRESS);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });
        bagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClosetListFragment fragment = ClosetListFragment.getInstance(Closet.ApprarelType.BAG);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClosetListFragment fragment = ClosetListFragment.getInstance(Closet.ApprarelType.TOP);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });

    }

}
