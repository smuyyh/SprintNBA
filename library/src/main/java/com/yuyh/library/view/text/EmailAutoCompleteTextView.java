package com.yuyh.library.view.text;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyh.library.R;

/**
 * 自动匹配邮箱输入框
 */
public class EmailAutoCompleteTextView extends AutoCompleteTextView {

    private static final String TAG = "EmailAutoCompleteTextView";

	private String[] emailSufixs = new String[] {
            "@qq.com",
            "@163.com",
            "@126.com",
            "@gmail.com",
            "@sina.com",
            "@hotmail.com",
            "@yahoo.cn",
            "@sohu.com",
            "@foxmail.com",
            "@139.com",
            "@yeah.net",
            "@vip.qq.com",
            "@vip.sina.com"};

	public EmailAutoCompleteTextView(Context context) {
		super(context);
		init(context);
	}

	public EmailAutoCompleteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public EmailAutoCompleteTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	public void setAdapterString(String[] es) {
		if(es != null && es.length > 0)
			this.emailSufixs = es;
	}
	
	private void init(final Context context) {
		//adapter中使用默认的emailSufixs中的数据，可以通过setAdapterString来更改
		this.setAdapter(new EmailAutoCompleteAdapter(context, R.layout.auto_complete_item, emailSufixs));
		
		//使得在输入1个字符之后便开启自动完成
		this.setThreshold(1);

		this.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					String text = EmailAutoCompleteTextView.this.getText().toString();
					//当该文本域重新获得焦点后，重启自动完成
					if(!"".equals(text))
						performFiltering(text, 0);
				} else {
					//当文本域丢失焦点后，检查输入email地址的格式
					EmailAutoCompleteTextView ev = (EmailAutoCompleteTextView) v;
					String text = ev.getText().toString();

                    if (text == null || text.equals(""))
                        return;

                    if(!text.matches("^[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$")) {
                        Toast toast = Toast.makeText(context, "邮箱地址错误", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.TOP, 0, 50);
						toast.show();
					}
				}
			}
		});
	}
	
	@Override
	protected void replaceText(CharSequence text) {
		//当我们在下拉框中选择一项时，android会默认使用AutoCompleteTextView中Adapter里的文本来填充文本域
		//因为这里Adapter中只是存了常用email的后缀
		//因此要重新replace逻辑，将用户输入的部分与后缀合并
		String t = this.getText().toString();
		int index = t.indexOf("@");
		if(index != -1)
			t = t.substring(0, index);
		super.replaceText(t + text);
	}

	@Override
	protected void performFiltering(CharSequence text, int keyCode) {
		//该方法会在用户输入文本之后调用，将已输入的文本与adapter中的数据对比，若它匹配
		//adapter中数据的前半部分，那么adapter中的这条数据将会在下拉框中出现
		String t = text.toString();
		
		//因为用户输入邮箱时，都是以字母，数字开始，而我们的adapter中只会提供以类似于"@163.com"
		//的邮箱后缀，因此在调用super.performFiltering时，传入的一定是以"@"开头的字符串
		int index = t.indexOf("@");
		if(index == -1) {
			if(t.matches("^[a-zA-Z0-9_]+$")) {
				super.performFiltering("@", keyCode);
			}
			else
				this.dismissDropDown();//当用户中途输入非法字符时，关闭下拉提示框
		} else {
			super.performFiltering(t.substring(index), keyCode);
		}
	}

	
	private class EmailAutoCompleteAdapter extends ArrayAdapter<String> {

		public EmailAutoCompleteAdapter(Context context, int textViewResourceId, String[] email_s) {
			super(context, textViewResourceId, email_s);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null)
				v = LayoutInflater.from(getContext()).inflate(
						R.layout.auto_complete_item, null);
			TextView tv = (TextView) v.findViewById(R.id.tv);
			
			String t = EmailAutoCompleteTextView.this.getText().toString();
			int index = t.indexOf("@");
			if(index != -1)
				t = t.substring(0, index);
			//将用户输入的文本与adapter中的email后缀拼接后，在下拉框中显示
			tv.setText(t + getItem(position));
			return v;
		}
	}
}
