package mobile.mycloset;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.Stack;

public class AppFragmentActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getFragmentManager();
    private Stack<Fragment> fragmentStack = new Stack<Fragment>();

    public void pushFragment(int resource, Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.add(resource, fragment);
        if (fragmentStack.size() > 0) {
            fragmentStack.lastElement().onPause();
            ft.hide(fragmentStack.lastElement());
        }
        fragmentStack.push(fragment);
        ft.commit();
    }

    public void popFragment() {
        if (numFragments() < 2) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        fragmentStack.peek().onPause();
        ft.remove(fragmentStack.pop());
        fragmentStack.peek().onResume();
        ft.show(fragmentStack.peek());
        ft.commit();
    }

    public void newFragment(int resource, Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();

        while(numFragments() > 0) {
            fragmentStack.peek().onPause();
            ft.remove(fragmentStack.pop());
        }

        ft.add(resource, fragment);
        fragmentStack.push(fragment);
        ft.commit();
    }

    public Fragment currentFragment() {
        if (fragmentStack.size() > 0) {
            return fragmentStack.peek();
        } else {
            return null;
        }
    }

    public int numFragments() {
        return fragmentStack.size();
    }


    @Override
    public void onBackPressed() {
        if (numFragments() >= 2) {
            popFragment();
        } else {
            super.onBackPressed();
        }
    }
}
