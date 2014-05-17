package com.example.guessnumber;

import java.util.List;

import com.example.bean.GuessBean;
import com.example.db.GuessDataSource;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.os.Build;

public class MainHighScore extends ActionBarActivity {
	ListView listView;
	private GuessDataSource db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_score);
		db = new GuessDataSource(this);
		
		listView = (ListView)findViewById(R.id.list_mainhighscore);
		List<GuessBean> listGuess  = this.db.getAllGuess();
		System.out.println("listGuess.size: "+listGuess.size());
		listView.setAdapter(new HighScoreAdapter(this, listGuess));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_high_score, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
