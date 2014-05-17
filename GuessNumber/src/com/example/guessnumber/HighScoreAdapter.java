package com.example.guessnumber;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bean.GuessBean;

public class HighScoreAdapter extends BaseAdapter {
	Context context;
	List<GuessBean> listGuess;
	private static LayoutInflater inflater = null;
	

	public HighScoreAdapter(Context context, List<GuessBean> listGuess) {
		this.context = context;
		this.listGuess = listGuess;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listGuess.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public class Holder {
		TextView tvName;
		TextView tvScore;
		TextView tvDate;
		TextView tvWin;
		TextView tvLose;	
	}
	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		Holder holder = new Holder();
		View rowView;

		rowView = inflater.inflate(R.layout.item_highscore, null);
		
			holder.tvName = (TextView)rowView.findViewById(R.id.txt_high_name);
			holder.tvName.setText(String.valueOf(listGuess.get(index).getPlayerName()));
			
			holder.tvScore = (TextView)rowView.findViewById(R.id.txt_high_score);
			holder.tvScore.setText(String.valueOf(listGuess.get(index).getScorePlay()));
			
			holder.tvDate = (TextView)rowView.findViewById(R.id.txt_high_date);
			holder.tvDate.setText(String.valueOf(listGuess.get(index).getDateCreate()));
			
			holder.tvWin = (TextView)rowView.findViewById(R.id.txt_high_win);
			holder.tvWin.setText(String.valueOf(listGuess.get(index).getWinner()));
			
			holder.tvLose = (TextView)rowView.findViewById(R.id.txt_high_lose);
			holder.tvLose.setText(String.valueOf(listGuess.get(index).getLose()));		
		return rowView;
	}

}
