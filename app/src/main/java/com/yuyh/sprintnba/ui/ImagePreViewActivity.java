package com.yuyh.sprintnba.ui;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseSwipeBackCompatActivity;
import com.yuyh.sprintnba.ui.adapter.VPImagePreViewAdapter;
import com.yuyh.sprintnba.ui.presenter.impl.ImagePreViewPresenter;
import com.yuyh.sprintnba.ui.view.ImagePreView;

import java.util.List;

import butterknife.InjectView;

public class ImagePreViewActivity extends BaseSwipeBackCompatActivity implements ImagePreView, ViewPager.OnPageChangeListener {

    public static final String INTENT_URLS = "extraPics";
    public static final String INTENT_URL = "extraPic";

    @InjectView(R.id.viewPager)
    ViewPager viewPager;

    private ImagePreViewPresenter presenter;
    private VPImagePreViewAdapter mImageViewAdapter;
    private int mCurrentItem = 0;

    private List<String> extraPics;
    private String extraPic;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_image_pre_view;
    }

    @Override
    protected void initViewsAndEvents() {
        mToolbar.setBackgroundColor(Color.TRANSPARENT);
        presenter = new ImagePreViewPresenter(this, this);

        extraPics = getIntent().getStringArrayListExtra(INTENT_URLS);
        extraPic = getIntent().getStringExtra(INTENT_URL);

        initViewPager();
        initCurrentItem();
    }

    private void initViewPager() {
        mImageViewAdapter = new VPImagePreViewAdapter(getSupportFragmentManager(), extraPics);
        viewPager.setAdapter(mImageViewAdapter);
        viewPager.setOnPageChangeListener(this);
    }

    void initCurrentItem() {
        mCurrentItem = extraPics.indexOf(extraPic);
        if (mCurrentItem < 0) {
            mCurrentItem = 0;
        }
        viewPager.setCurrentItem(mCurrentItem);
        setTitle((mCurrentItem + 1) + "/" + extraPics.size());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCurrentItem = position;
        setTitle((position + 1) + "/" + mImageViewAdapter.getCount());
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.savePicture) {
            presenter.saveImage(extraPics.get(viewPager.getCurrentItem()));
        }
        return true;
    }
}
