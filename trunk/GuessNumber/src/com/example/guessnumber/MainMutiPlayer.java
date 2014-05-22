package com.example.guessnumber;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Build;

public class MainMutiPlayer extends ActionBarActivity {
	int levelSelected = 1;
	private final String[] itemLevels = new String[] {"Easy","Medium","Hard"}; 
	EditText txt_player1;
	EditText txt_player2;
	ImageView btnPlay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_muti_player);
		
		txt_player1 = (EditText)findViewById(R.id.txt_play11);
		txt_player2 = (EditText)findViewById(R.id.txt_play_22);
		btnPlay = (ImageView)findViewById(R.id.imgMutiPlay);
		btnPlay.setOnClickListener(this.levelClick);
	}
	private OnClickListener levelClick = new OnClickListener() {		
		@Override
		public void onClick(View v) {
			
			if (txt_player1.getText().length() >= 1 && txt_player1.getText()!=null && 
					txt_player2.getText().length()>=1 && txt_player2.getText()!=null) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(MainMutiPlayer.this);
				dialog.setTitle("Game Level");
				dialog.setSingleChoiceItems(itemLevels, levelSelected, new DialogInterface.OnClickListener() {			
					@Override
					public void onClick(DialogInterface dialog, int which) {
						levelSelected = which;
					}
				});
				dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {			
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainMutiPlayer.this,
								"Your Seleted : "+itemLevels[levelSelected], 
								Toast.LENGTH_SHORT).show();
						dialog.cancel();
						
						Intent intent = new Intent(MainMutiPlayer.this, MainGame.class);
						System.out.println("txt_player1.getText().toString(): "+txt_player1.getText().toString());
						intent.putExtra("player1", txt_player1.getText().toString());
						intent.putExtra("player2", txt_player2.getText().toString());
						intent.putExtra("level",levelSelected);
						startActivity(intent);
					} 
				});
				dialog.setNegativeButton("CANCLE", new DialogInterface.OnClickListener() {			
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				dialog.show();
			} else {
				Toast.makeText(MainMutiPlayer.this, "Please Input YourName ?", Toast.LENGTH_LONG).show();
			}
		
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_muti_player, menu);
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
