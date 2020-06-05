package com.cookandroid.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final  String[] CHANNELS = new String[]{"장바구니", "냉장고", "레시피"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private ViewPager mViewPager;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    NewPagerAdapter newPagerAdapter;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newPagerAdapter =  new NewPagerAdapter(getSupportFragmentManager(), mDataList);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.draw_layout);

        // 툴바 추가
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // 홈버튼 활성화
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //홈버튼 이미지 변경
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu2);
        //타이틀 텍스트
        getSupportActionBar().setTitle("냉장고를 비워줘!");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();


                int id = menuItem.getItemId();

                switch (id){
                    case R.id.navigation_item_favorite:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(),Toast.LENGTH_LONG).show();
                        break;
                    case R.id.navigation_item_recent:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(),Toast.LENGTH_LONG).show();
                        break;
                    case R.id.navigation_item_setting:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(),Toast.LENGTH_LONG).show();
                        break;
                }


                return true;
            }
        });


        for (int i=0;i<mDataList.size();i++){
            switch (i){
                case 0:
                    FragCART fragCART = new FragCART();
                    newPagerAdapter.addItem(fragCART);
                case 1:
                    FragREF fragREF = new FragREF();
                    newPagerAdapter.addItem(fragREF);
                case 2:
                    FragREC fragREC = new FragREC();
                    newPagerAdapter.addItem(fragREC);
            }
        }
        newPagerAdapter.notifyDataSetChanged();


        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(newPagerAdapter);

        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#979797"));
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });


                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#79D9CF"));
                return indicator;
            }

        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator

        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding(UIUtil.dip2px(this, 15));
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_spliter));
        ViewPagerHelper.bind(magicIndicator, mViewPager);
        mViewPager.setCurrentItem(1);





    }
}