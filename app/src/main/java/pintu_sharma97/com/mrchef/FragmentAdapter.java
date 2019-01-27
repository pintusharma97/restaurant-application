package pintu_sharma97.com.mrchef;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    int tabCount;

    public FragmentAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:{
                BreakFast fast = new BreakFast();
                return fast;
            }
            case 1:{
                Lunch lunch = new Lunch();
                return lunch;
            }
            case 2:{
                Dinner dinner = new Dinner();
                return dinner;
            }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
