package com.example.alarm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.alarm.utils.UIUtils;
import com.example.alarm.viewpager.FragmentFactory;

/**
 * 顶部上划
 * 谷歌应用市场 069 Appbar 折叠布局
 */
public class TopMoveActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topmove);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // 注意：需要在setSupportActionBar调用之前设置好Toolbar的配置

        // 使用toolbar代替actionbar
        setSupportActionBar(toolbar);

        // 初始化DrawerLayout
        // 将Toolbar与DrawerLayout整合s
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawerLayout);
        ActionBarDrawerToggle barDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        barDrawerToggle.syncState();
        // 如果想看到菜单滑动过程中，Toolbar的一些细节变化（菜单按钮）
        drawerLayout.addDrawerListener(barDrawerToggle);

        // 处理菜单项点击
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // 与ViewPager绑定，通过PageAdapter的getPageTitle(int position)可以填充选项卡内容
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(viewPager);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        drawerLayout.closeDrawers();
        return false;
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final String[] titles;

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            titles = UIUtils.getStrings(R.array.content_title);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
