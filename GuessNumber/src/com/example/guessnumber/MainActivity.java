package com.example.guessnumber;

import java.util.List;

import com.example.bean.GuessBean;
import com.example.db.GuessDataSource;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	private final String[] itemLevel = new String[] { "Easy", "Mediem", "Hard" };
	LinearLayout menuSingle;
	LinearLayout menuMuti;
	LinearLayout menuHelp;
	LinearLayout menuExit;
	LinearLayout menuHightScore;
	ImageView btnClose;
	private GuessDataSource db;
	private List<GuessBean> listGuess;
	ListView listViewData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		menuSingle = (LinearLayout) this.findViewById(R.id.btn_menu_single);
		menuSingle.setOnClickListener(this.menuSingleClick);
		menuMuti = (LinearLayout) this.findViewById(R.id.btn_menu_muti);
		menuMuti.setOnClickListener(this.menuMutiClick);
		menuHightScore = (LinearLayout) findViewById(R.id.btn_menu_hightscore);
		menuHightScore.setOnClickListener(this.menuHightScoreClick);
		menuHelp = (LinearLayout) this.findViewById(R.id.btn_menu_help);
		menuHelp.setOnClickListener(this.menuHelpClick);
		menuExit = (LinearLayout) this.findViewById(R.id.btn_menu_exit);
		menuExit.setOnClickListener(this.menuExitClick);
		db = new GuessDataSource(MainActivity.this);

	}

	private OnClickListener menuSingleClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(MainActivity.this, MainSinglePlayer.class);
			startActivity(i);
		}
	};
	private OnClickListener menuMutiClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(MainActivity.this, MainMutiPlayer.class);
			startActivity(i);
		}
	};
	private OnClickListener menuHightScoreClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			dialogHighScore();
			/*
			 * Intent i = new Intent(MainActivity.this,MainHighScore.class);
			 * MainActivity.this.startActivity(i);
			 */
		}
	};

	public void dialogHighScore() {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.list_hightscore);
		dialog.setCancelable(true);
		dialog.setTitle("Top 5 HighScore Player");

		listViewData = (ListView) dialog.findViewById(R.id.list_Score);
		btnClose = (ImageView) dialog.findViewById(R.id.imgPosition1);
		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.cancel();
			}
		});
		
		listGuess = db.getListGuessHighScoreLimtRecord(5);
		listViewData.setAdapter(new HighScoreAdapter(this, listGuess));
		dialog.show();
	}

	private OnClickListener menuHelpClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

		}
	};

	private OnClickListener menuExitClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			new AlertDialog.Builder(MainActivity.this)
					.setTitle(R.string.msg_title_exitEng)
					.setMessage(R.string.msg_content_exitEng)
					.setNegativeButton(R.string.btn_cancle,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							})
					.setPositiveButton(R.string.btn_ok,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent(
											Intent.ACTION_MAIN);
									intent.addCategory(Intent.CATEGORY_HOME);
									intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									startActivity(intent);
								}
							}).show();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
