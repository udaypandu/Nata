package nata.com.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import nata.com.fragments.ContactDetailsFragment;
import nata.com.fragments.LoginInformationFragment;
import nata.com.fragments.PersonalDetailsFragment;


/**
 * Created by Shankar on 1/5/2017.
 */

public class ViewProfileAdapter extends FragmentStatePagerAdapter {

    private final int PAGE_COUNT = 3;

    public ViewProfileAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = PersonalDetailsFragment.newInstance();
                break;
            case 1:
                fragment = ContactDetailsFragment.newInstance();
                break;
            case 2:
                fragment = LoginInformationFragment.newInstance();
                break;
        }
        return fragment;
    }
}