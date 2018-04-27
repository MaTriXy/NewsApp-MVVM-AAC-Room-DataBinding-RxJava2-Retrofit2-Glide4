package com.nuhkoca.mvvmrxjavaretrofitdatabindingdemo.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.nuhkoca.mvvmrxjavaretrofitdatabindingdemo.R;
import com.nuhkoca.mvvmrxjavaretrofitdatabindingdemo.callback.IRecyclerViewScrollListener;
import com.nuhkoca.mvvmrxjavaretrofitdatabindingdemo.databinding.ActivityNewsBinding;
import com.nuhkoca.mvvmrxjavaretrofitdatabindingdemo.helper.Constants;
import com.nuhkoca.mvvmrxjavaretrofitdatabindingdemo.ui.news.NewsFragment;

public class NewsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, IRecyclerViewScrollListener {

    private ActivityNewsBinding mActivityNewsBinding;
    private MenuItem mPrevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityNewsBinding = DataBindingUtil.setContentView(this, R.layout.activity_news);

        setupViewPager();
    }

    private void setupViewPager() {
        mActivityNewsBinding.vpNews.setAdapter(new ViewPagerInflater(getSupportFragmentManager()));
        mActivityNewsBinding.vpNews.setOffscreenPageLimit(Constants.VIEW_PAGER_FRAGMENT_COUNT);

        mActivityNewsBinding.bnvNews.setOnNavigationItemSelectedListener(this);

        mActivityNewsBinding.vpNews.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mPrevMenuItem != null) {
                    mPrevMenuItem.setChecked(false);
                } else {
                    mActivityNewsBinding.bnvNews.getMenu().getItem(0).setChecked(false);
                }
                mActivityNewsBinding.bnvNews.getMenu().getItem(position).setChecked(true);
                mPrevMenuItem = mActivityNewsBinding.bnvNews.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bottom_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemThatWasClicked;

        if (item.getItemId() < 0) {
            itemThatWasClicked = 0;
        } else {
            itemThatWasClicked = item.getItemId();
        }

        switch (itemThatWasClicked) {
            case R.id.top_headlines:
                mActivityNewsBinding.vpNews.setCurrentItem(0);
                return true;

            case R.id.everything:
                mActivityNewsBinding.vpNews.setCurrentItem(1);
                return true;

            case R.id.sources:
                mActivityNewsBinding.vpNews.setCurrentItem(2);
                return true;

            default:
                break;
        }

        return false;
    }

    @Override
    public void onHid() {
        mActivityNewsBinding.bnvNews.animate().translationY(
                mActivityNewsBinding.bnvNews.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    @Override
    public void onShown() {
        mActivityNewsBinding.bnvNews.animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator(2)).start();
    }

    private class ViewPagerInflater extends FragmentStatePagerAdapter {
        ViewPagerInflater(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.top_headlines_header);

                case 1:
                    return getString(R.string.everything_header);

                case 2:
                    return getString(R.string.sources_header);

                default:
                    break;
            }

            return null;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            if (object instanceof NewsFragment) {
                return POSITION_UNCHANGED;
            } else {
                return POSITION_NONE;
            }
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return NewsFragment.getInstance(NewsActivity.this);

                case 1:
                    return NewsFragment.getInstance(NewsActivity.this);

                case 2:
                    return NewsFragment.getInstance(NewsActivity.this);

                default:
                    break;
            }

            return null;
        }

        @Override
        public int getCount() {
            return Constants.VIEW_PAGER_FRAGMENT_COUNT;
        }
    }
}