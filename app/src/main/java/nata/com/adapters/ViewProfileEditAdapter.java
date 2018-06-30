package nata.com.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import nata.com.fragments.ContactDetailsNewFragment;
import nata.com.fragments.LoginInformationFragmentNew;
import nata.com.fragments.PersonalDetailsNewFragment;
import nata.com.models.PersonalDetailsModel;


/**
 * Created by Shankar on 1/5/2017.
 */

public class ViewProfileEditAdapter extends FragmentStatePagerAdapter {

    private final int PAGE_COUNT = 3;
    private PersonalDetailsModel mPersonalDetailsModel;

    public ViewProfileEditAdapter(FragmentManager fm,PersonalDetailsModel mPersonalDetailsModel) {
        super(fm);
        this.mPersonalDetailsModel = mPersonalDetailsModel;
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
                fragment = PersonalDetailsNewFragment.newInstance(mPersonalDetailsModel);
                break;
            case 1:
                fragment = ContactDetailsNewFragment.newInstance(mPersonalDetailsModel);
                break;
            case 2:
                fragment = LoginInformationFragmentNew.newInstance();
                break;
        }
        return fragment;
    }
}