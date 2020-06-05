package com.cookandroid.myapplication;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewPagerAdapter extends FragmentPagerAdapter {
    private List<String> mDataList;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public NewPagerAdapter(@NonNull FragmentManager fm, List<String> datalist) {
        super(fm);
        mDataList = datalist;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    void addItem(Fragment fragment){
        fragments.add(fragment);
    }
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View) object);
//    }
    @Override
    public int getItemPosition(Object object) {
        TextView textView = (TextView) object;
        String text = textView.getText().toString();
        int index = mDataList.indexOf(text);
        if (index >= 0) {
            return index;
        }
        return POSITION_NONE;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mDataList.get(position);
    }

}
