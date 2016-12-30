package com.yuyh.sprintnba.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.yuyh.sprintnba.widget.LoadingDialog;

import butterknife.ButterKnife;

/**
 * <h1>懒加载Fragment</h1> 只有创建并显示的时候才会调用onCreateViewLazy方法<br>
 * <br>
 * 
 * 懒加载的原理onCreateView的时候Fragment有可能没有显示出来。<br>
 * 但是调用到setUserVisibleHint(boolean isVisibleToUser),isVisibleToUser =
 * true的时候就说明有显示出来<br>
 * 但是要考虑onCreateView和setUserVisibleHint的先后问题所以才有了下面的代码
 * 
 * 注意：<br>
 * 《1》原先的Fragment的回调方法名字后面要加个Lazy，比如Fragment的onCreateView方法， 就写成onCreateViewLazy <br>
 * 《2》使用该LazyFragment会导致多一层布局深度
 * 
 * @author LuckyJayce
 *
 */
public class BaseLazyFragment extends BaseFragment {
	private boolean isInit = false;
	private Bundle savedInstanceState;
	public static final String INTENT_BOOLEAN_LAZYLOAD = "intent_boolean_lazyLoad";
	private boolean isLazyLoad = true;
	private FrameLayout layout;

	public LoadingDialog mLoadingDialog;

	@Deprecated
	protected final void onCreateView(Bundle savedInstanceState) {
		super.onCreateView(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			isLazyLoad = bundle.getBoolean(INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
		}
		if (isLazyLoad) {
			if (getUserVisibleHint() && !isInit) {
				isInit = true;
				this.savedInstanceState = savedInstanceState;
				onCreateViewLazy(savedInstanceState);
			} else {
				layout = new FrameLayout(getApplicationContext());
				layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				super.setContentView(layout);
			}
		} else {
			isInit = true;
			onCreateViewLazy(savedInstanceState);
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser && !isInit && getContentView() != null) {
			isInit = true;
			onCreateViewLazy(savedInstanceState);
			onResumeLazy();
		}
		if (isInit && getContentView() != null) {
			if (isVisibleToUser) {
				isStart = true;
				onFragmentStartLazy();
			} else {
				isStart = false;
				onFragmentStopLazy();
			}
		}
	}

	@Deprecated
	@Override
	public final void onStart() {
		super.onStart();
		if (isInit && !isStart && getUserVisibleHint()) {
			isStart = true;
			onFragmentStartLazy();
		}
	}

	@Deprecated
	@Override
	public final void onStop() {
		super.onStop();
		if (isInit && isStart && getUserVisibleHint()) {
			isStart = false;
			onFragmentStopLazy();
		}
	}

	private boolean isStart = false;

	protected void onFragmentStartLazy() {

	}

	protected void onFragmentStopLazy() {

	}

	protected void onCreateViewLazy(Bundle savedInstanceState) {

	}

	protected void onResumeLazy() {

	}

	protected void onPauseLazy() {

	}

	protected void onDestroyViewLazy() {

	}

	@Override
	public void setContentView(int layoutResID) {
		if (isLazyLoad && getContentView() != null && getContentView().getParent() != null) {
			layout.removeAllViews();
			View view = inflater.inflate(layoutResID, layout, false);
			layout.addView(view);
		} else {
			super.setContentView(layoutResID);
		}

		ButterKnife.inject(this, getContentView());
	}

	@Override
	public void setContentView(View view) {
		if (isLazyLoad && getContentView() != null && getContentView().getParent() != null) {
			layout.removeAllViews();
			layout.addView(view);
		} else {
			super.setContentView(view);
		}
	}

	@Override
	@Deprecated
	public final void onResume() {
		super.onResume();
		if (isInit) {
			onResumeLazy();
		}
	}

	@Override
	@Deprecated
	public final void onPause() {
		super.onPause();
		if (isInit) {
			onPauseLazy();
		}
	}

	@Override
	@Deprecated
	public final void onDestroyView() {
		super.onDestroyView();
		if (isInit) {
			onDestroyViewLazy();
		}
		isInit = false;
	}

	/**
	 * 显示刷新Loadding
	 */
	public void showLoadingDialog() {
		try {
			mLoadingDialog = LoadingDialog.createDialog(mActivity);
			mLoadingDialog.setTitle(null);
			mLoadingDialog.setCancelable(false);
			mLoadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
						hideLoadingDialog();
					}
					return true;
				}
			});
			mLoadingDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 隐藏刷新Loadding
	 */
	public void hideLoadingDialog() {
		try {
			if (mLoadingDialog != null) {
				if (mLoadingDialog.animation != null) {
					mLoadingDialog.animation.reset();
				}
				mLoadingDialog.dismiss();
				mLoadingDialog = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ButterKnife.reset(this);
	}
}
