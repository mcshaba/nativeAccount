package com.example.zexplore.helper;

import com.example.zexplore.fragment.form.AccountInfoFragment;
import com.example.zexplore.fragment.form.ContactDetailsFragment;
import com.example.zexplore.fragment.form.IdCardUploadFragment;
import com.example.zexplore.fragment.form.IdentificationFragment;
import com.example.zexplore.fragment.form.PersonalInformationFragment;
import com.example.zexplore.fragment.form.SignatoryFragment;
import com.example.zexplore.fragment.form.UploadPassportFragment;
import com.example.zexplore.fragment.form.UploadUtilityFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by Ehigiator David on 20/03/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AccountInfoFragment(); //ChildFragment1 at position 0
            case 1:
                return new PersonalInformationFragment(); //ChildFragment2 at position 1

            case 2:
                return new ContactDetailsFragment(); //ChildFragment2 at position 1

            case 3:
                return new IdentificationFragment();
            case 4:
                return new IdCardUploadFragment();

            case 5:
                return new UploadPassportFragment();
            case 6:
                return new UploadUtilityFragment();
            case 7:
                return new SignatoryFragment();
            default:
                return new AccountInfoFragment();
        }
    }

    @Override
    public int getCount() {
        return 8;
    }
}