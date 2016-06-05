package com.yuyh.library.view.text;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自动调整TextView字体大小以适应文字长度
 *
 * @author yuyh.
 * @date 16/4/10.
 */
public class AdaptiveTextView extends TextView {

    private static float DEFAULT_MIN_TEXT_SIZE = 3; // 最小的字体大小
    private static float DEFAULT_MAX_TEXT_SIZE = 20;// 验证大部分手机情况下无效值

    // Attributes
    private Paint testPaint;
    private float minTextSize, maxTextSize;

    public AdaptiveTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    private void initialise() {
        testPaint = new Paint();
        testPaint.set(this.getPaint()); // 获取模拟的paint

        maxTextSize = this.getTextSize();// 获取单个字体的像素
        if (maxTextSize <= DEFAULT_MIN_TEXT_SIZE) {
            maxTextSize = DEFAULT_MAX_TEXT_SIZE;
        }
        minTextSize = DEFAULT_MIN_TEXT_SIZE;
    }

    ;

    /**
     * 重新调整字体大小
     */
    private void refitText(String text, int textWidth) {

        if (textWidth > 0) {
            int availableWidth = textWidth - this.getPaddingLeft()
                    - this.getPaddingRight();// 获取改TextView的画布可用大小        
            float trySize = maxTextSize;
            float scaled = getContext().getResources().getDisplayMetrics().scaledDensity;
            testPaint.setTextSize(trySize * scaled);// 模拟注意乘以scaled
            while ((trySize > minTextSize)
                    && (testPaint.measureText(text) > availableWidth)) {
                trySize -= 2;
                Paint.FontMetrics fm = testPaint.getFontMetrics();
                float scaled1 = (float) (this.getHeight() / (Math
                        .ceil(fm.descent - fm.top) + 2));
                float scaled2 = (testPaint.measureText(text) / availableWidth);
                if (scaled1 >= 1.75 & scaled1 >= scaled2) {// 注意1.75是三星s4 小米3的适合数值
                    break;
                }
                if (trySize <= minTextSize) {
                    trySize = minTextSize;
                    break;
                }
                testPaint.setTextSize(trySize * scaled);
            }
            this.setTextSize(trySize);// 等同于this.getPaint().set(trySize*scaled);
        }
    }

    ;

    @Override
    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        refitText(text.toString(), this.getWidth());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw) {
            refitText(this.getText().toString(), w);
        }
    }
}
