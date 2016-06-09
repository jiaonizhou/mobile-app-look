package mobile.mycloset;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

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

    public void initCalenderFragment(View view) {
        CalendarView calendar = (CalendarView) view.findViewById(R.id.calendarView);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                //dateDisplay.setText("Date: " + i2 + " / " + i1 + " / " + i);
                Toast.makeText(getContext(), "Selected Date:\n" + "Day = " + i2 + "\n" + "Month = " + i1 + "\n" + "Year = " + i, Toast.LENGTH_LONG).show();
            }
        });
    }
}
