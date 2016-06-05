package com.yuyh.library.view.text;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;

import com.yuyh.library.R;
import com.yuyh.library.utils.DimenUtils;

/**
 * 密码输入框(默认六位 [*] [*] [*] [*] [*] [*])
 *
 * @author yuyh.
 * @date 16/4/10.
 */
public class PasswordEditText extends EditText {

    private Context mContext;
    /**
     * 密码框输入间隔
     */
    private float mSpacingWidth;
    /**
     * 默认密码位数
     */
    private int mMaxCharact = 6;
    private Bitmap mPwdImg, inputLeft, inputMiddle, inputRight, inputBox;
    private Paint mPaint = new Paint();

    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mSpacingWidth = DimenUtils.dpToPx(12);
        setBackgroundColor(Color.TRANSPARENT);
        setMaxCharacter(mMaxCharact);
        setSingleLine(true);
        setCursorVisible(false);
        setLongClickable(false);
        setInputType(InputType.TYPE_CLASS_NUMBER);
        mPwdImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.ui_pw_is_set);
        inputLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.ui_input_left);
        inputMiddle = BitmapFactory.decodeResource(context.getResources(), R.drawable.ui_input_middle);
        inputRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.ui_input_right);
        inputBox = BitmapFactory.decodeResource(context.getResources(), R.drawable.ui_input_box);
    }

    public void setSpacingWidth(float mSpacingLen) {
        this.mSpacingWidth = DimenUtils.dpToPx(mSpacingLen);
    }

    public void setMaxCharacter(int maxCharacter) {
        this.mMaxCharact = maxCharacter;
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCharacter)});
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float disWidth = getWidth();
        float disHeight = getHeight();
        int spacingCount = mMaxCharact - 1;//间隔数n-1
        float pwdWidth = (disWidth - mSpacingWidth * spacingCount) / 6;//密码框的宽度
        int textLen = getText().toString().trim().length();
        float cacheWidth = 0;
        boolean isHaveSpace = false;
        if (mSpacingWidth > 0) {
            isHaveSpace = true;
        }

        for (int i = 0; i < mMaxCharact; i++) {
            //绘制密码方格
            RectF rectRim = new RectF(cacheWidth, 0, cacheWidth + pwdWidth, disHeight);
            mPaint.setColor(Color.parseColor("#d3e0e3"));
            if (isHaveSpace) {
                canvas.drawBitmap(inputBox, null, rectRim, mPaint);
            } else {
                if (i == 0) {
                    canvas.drawBitmap(inputLeft, null, rectRim, mPaint);
                } else if (i == mMaxCharact - 1) {
                    canvas.drawBitmap(inputRight, null, rectRim, mPaint);
                } else {
                    canvas.drawBitmap(inputMiddle, null, rectRim, mPaint);
                }
            }
            if (i < textLen) {
                mPaint.reset();
                canvas.drawBitmap(mPwdImg, pwdWidth / 2 - mPwdImg.getWidth() / 2 + cacheWidth,
                        disHeight / 2 - mPwdImg.getHeight() / 2, mPaint);
            }
            cacheWidth += (pwdWidth + mSpacingWidth);
        }
    }
}
