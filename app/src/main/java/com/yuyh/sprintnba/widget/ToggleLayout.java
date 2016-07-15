package com.yuyh.sprintnba.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuyh.sprintnba.R;

/**
 * @author yuyh.
 * @date 2016/6/22.
 */
public class ToggleLayout extends RelativeLayout {

    private Context context;
    private RelativeLayout layout = null;
    private ImageView ivLeft, ivRight;
    private TextView tvTitle;

    private String[] item;

    private int current = 0;
    private int length;

    private OnToggleListener listener;

    public ToggleLayout(Context context) {
        this(context, null);
    }

    public ToggleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToggleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = (RelativeLayout) inflater.inflate(R.layout.layout_toggle_menu, this);
        ivLeft = (ImageView) layout.findViewById(R.id.ivLeft);
        ivRight = (ImageView) layout.findViewById(R.id.ivRight);
        tvTitle = (TextView) findViewById(R.id.tvToggleTitle);
        ivLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item != null && item.length > 0) {
                    current = (length + current - 1) % length;
                    tvTitle.setText(item[current]);
                    if (listener != null)
                        listener.toggle(current);
                }
            }
        });

        ivRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item != null && item.length > 0) {
                    current = (current + 1) % length;
                    tvTitle.setText(item[current]);
                    if (listener != null)
                        listener.toggle(current);
                }
            }
        });
    }

    public void setItem(String[] item) {
        if (item != null && item.length > 0) {
            this.item = item;
            length = item.length;
            current = 0;
            tvTitle.setText(item[0]);
        }
    }

    public String getCurrentItem() {
        if (item != null && item.length > 0) {
            return item[current];
        }
        return null;
    }

    public void setOnToggleListener(OnToggleListener listener) {
        this.listener = listener;
    }

    public interface OnToggleListener {
        void toggle(int position);
    }
}
