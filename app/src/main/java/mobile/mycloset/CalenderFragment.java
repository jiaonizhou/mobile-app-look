package mobile.mycloset;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Cindy on 6/8/16.
 */
public class CalenderFragment extends Fragment {
    private static CalenderFragment fragment;

    private View view;

    public static CalenderFragment getInstance() {
        if (fragment == null) {
            fragment = new CalenderFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_calendar, container, false);
        initCalenderFragment(view);
        return view;
    }

    public void initCalenderFragment(View view) {}
}
