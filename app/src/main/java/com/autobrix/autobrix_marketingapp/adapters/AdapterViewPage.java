package com.autobrix.autobrix_marketingapp.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class AdapterViewPage extends FragmentPagerAdapter
{

    ArrayList<Fragment> fragmentArrayList=new ArrayList<>();
    ArrayList<String> list=new ArrayList<>();
    public AdapterViewPage(@NonNull  FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public  void addfragment(Fragment fragment,String title)
    {
        fragmentArrayList.add(fragment);
        list.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return list.get(position);
    }
}
