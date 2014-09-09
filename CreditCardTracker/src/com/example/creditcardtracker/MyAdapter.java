package com.example.creditcardtracker;





import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sv.creditcardtracker.R;

public class MyAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	CreditCardMain mCreditContext;
	
	public MyAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
		mCreditContext=(CreditCardMain) context;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return mCreditContext.mCreditData.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {

			convertView = mInflater.inflate(R.layout.adapterrow, null);

			holder = new ViewHolder();
			holder.text1 = (TextView) convertView.findViewById(R.id.txt1);
			holder.text2 = (TextView) convertView.findViewById(R.id.txt2);
			holder.text3 = (TextView) convertView.findViewById(R.id.txt3);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		
		
		
		
		 holder.text1.setText(mCreditContext.mCreditData.get(position).amount);
		 holder.text2.setText(mCreditContext.mCreditData.get(position).description);
		 holder.text3.setText(mCreditContext.mCreditData.get(position).date.substring(0, 5));
		 
		return convertView;

	}

	static class ViewHolder {
		TextView text1;
		TextView text2;
		TextView text3;
	}

}
