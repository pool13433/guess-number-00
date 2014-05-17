package com.example.guessnumber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.example.bean.GuessBean;
import com.example.db.GuessDataSource;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainGame extends ActionBarActivity {

	Button btnCheck;
	EditText txtInputKey;
	TextView txt_player1;
	TextView txt_no;
	TextView txt_score;
	ImageView imgPosition1;
	ImageView imgPosition2;
	ImageView imgPosition3;
	ImageView imgPosition4;
	TextView txtPosition;
	TextView txtNumber;
	TextView txtPositionNumber;
	TextView txtLastInput;
	ListView listViewData;
	TextView txtWin;
	TextView txtLose;
	ImageView imgClose;
	ImageView imgZoom;
	ImageView imgNewGame;
	ImageView imgAnswer;

	// จำนวนตัวเลขที่ ถูก มีเหมือน คำตอบ ถูกตำแหน่ง
	int position = 0;
	// จำนวนตัวเลขที่ ถูก มีเหมือน คำตอบ แต่อาจไม่ถูกตำแหน่ง
	int number = 0;
	// จำนวนครั้งที่เล่น เริ่มเล่น =0
	int no = 0;
	// จำนวนที่เล่นได้สูงสุด ตาม level
	int noLimit = 0;
	// ตัวเลขที่ กรอกล่าสุด
	String no_lastinput = "";
	// จำนวน ครั้งที่ชนะ
	int no_win = 0;
	int no_lose = 0;
	int score = 0;
	// Id Player1
	Integer player1 = null;
	Integer player2 = null;

	// คำตอบ
	private String answer = "";
	// ระดับความยาก 0 = easy,1= medium,2=hard
	private int levelIndex = 1;
	// รูป ตัวเลข 0-9
	static final int[] pic = new int[] { R.drawable.icon00_48x48,
			R.drawable.icon01_48x48, R.drawable.icon02_48x48,
			R.drawable.icon03_48x48, R.drawable.icon04_48x48,
			R.drawable.icon05_48x48, R.drawable.icon06_48x48,
			R.drawable.icon07_48x48, R.drawable.icon08_48x48,
			R.drawable.icon09_48x48, };

	static final int ANSWER_DIGIT = 4;

	List<String> listItem = new ArrayList<String>();
	private GuessDataSource db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		// connect database
		this.db = new GuessDataSource(MainGame.this);

		//
		btnCheck = (Button) findViewById(R.id.btn_check);
		btnCheck.setOnClickListener(this.btnCheckAnswer);
		txtInputKey = (EditText) findViewById(R.id.txt_inputKey);
		txtInputKey.addTextChangedListener(this.textOnChang);
		imgPosition1 = (ImageView) findViewById(R.id.imgDialogClose);
		imgPosition2 = (ImageView) findViewById(R.id.imageView2);
		imgPosition3 = (ImageView) findViewById(R.id.imageView3);
		imgPosition4 = (ImageView) findViewById(R.id.imageView4);
		txtNumber = (TextView) findViewById(R.id.txt_nember);
		txtLastInput = (TextView) findViewById(R.id.txt_no_old_input);
		imgZoom = (ImageView) findViewById(R.id.imgZoom);
		imgZoom.setOnClickListener(this.listViewClick);
		txtWin = (TextView) findViewById(R.id.txt_you_win);
		txtLose = (TextView) findViewById(R.id.txt_you_lose);
		txtPosition = (TextView) findViewById(R.id.txt_position);
		imgClose = (ImageView) findViewById(R.id.imgClose);
		imgClose.setOnClickListener(this.dialogClose);
		txt_player1 = (TextView) findViewById(R.id.txt_result_single);
		txt_no = (TextView) findViewById(R.id.txt_result_no);
		txt_score = (TextView) findViewById(R.id.txt_result_score);
		imgNewGame = (ImageView) findViewById(R.id.imgNewGame);
		imgNewGame.setOnClickListener(this.btnNewGame);
		imgAnswer = (ImageView)findViewById(R.id.imgAnswer);
		imgAnswer.setOnClickListener(this.btnAnswer);
		// get data form MainSinglePlayer.Class
		Intent i = getIntent();

		txt_player1.setText(String.valueOf(i.getStringExtra("player")));

		this.levelIndex = i.getIntExtra("level", 1);

		this.setLabel();
		this.startGame();
		this.setGameLevel();
	}

	private OnClickListener btnNewGame = new OnClickListener() {
		@Override
		public void onClick(View v) {

			reStartGame();
			no_lose ++;
		}
	};
	private OnClickListener btnAnswer = new OnClickListener() {
		@Override
		public void onClick(View v) {
			dialogEndGame("End Game", "Answer: "+answer+"\n Your Selected Menu ");
		}
	};
	private OnClickListener btnCheckAnswer = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			if (txtInputKey.getText().length() == ANSWER_DIGIT) {
				position = 0;
				number = 0;
				Integer a1, a2, a3, a4, i1, i2, i3, i4;

				a1 = Integer.parseInt(answer.subSequence(0, 1).toString());
				a2 = Integer.parseInt(answer.subSequence(1, 2).toString());
				a3 = Integer.parseInt(answer.subSequence(2, 3).toString());
				a4 = Integer.parseInt(answer.subSequence(3, 4).toString());

				i1 = Integer.parseInt(txtInputKey.getText().subSequence(0, 1)
						.toString());
				i2 = Integer.parseInt(txtInputKey.getText().subSequence(1, 2)
						.toString());
				i3 = Integer.parseInt(txtInputKey.getText().subSequence(2, 3)
						.toString());
				i4 = Integer.parseInt(txtInputKey.getText().subSequence(3, 4)
						.toString());

				if (a1 == i1) {
					position++;
				} else if (a1 == i1 || a1 == i2 || a1 == i3 || a1 == i4) {
					number++;
				}

				if (a2 == i2) {
					position++;
				} else if (a2 == i2 || a2 == i1 || a2 == i3 || a2 == i4) {
					number++;
				}

				if (a3 == i3) {
					position++;
				} else if (a3 == i3 || a3 == i1 || a3 == i2 || a3 == i4) {
					number++;
				}

				if (a4 == i4) {
					position++;
				} else if (a4 == i4 || a4 == i1 || a4 == i2 || a4 == i3) {
					number++;
				}

				// get String Form EditText
				no_lastinput = String.valueOf(txtInputKey.getText());
				
				// ListView Show Add Item Data
				listItem.add(Integer.valueOf((listItem.size() + 1))
						+ ": [ "
						+ new String(no_lastinput)
						+ " ] "
						+ getApplicationContext().getString(
								R.string.label_position)
						+ " [ "
						+ String.valueOf(txtPosition.getText())
						+ " ] "
						+ getApplicationContext().getString(
								R.string.label_number) + " ["
						+ String.valueOf(txtNumber.getText()) + " ]");
				// ครั้งที่เล่น + 1
				no++;
				
				// load View
				setLabel();
				
				// Clear data InputKey
				txtInputKey.setText("");

				// ตรวจสอบจำนวนครั้ง มากกว่า เลือกระดับหรือเปล่า
				if (no >= noLimit) {
					dialogEndGame("Your Lose !",
							"Yor Lose The Game ANSWER IS: " + answer);
					no_lose++;
					// save data to database
					saveDataPlayGame();
					reStartGame();
					setLabel();

					score = calScore();
					txt_score.setText(String.valueOf(score));
				}

				// ตรวจสอบ ตำแหน่งที่ถูก มากกว่า ที่ตั้งไว้ แสดงว่า ชนะ
				if (position >= ANSWER_DIGIT) {
					dialogEndGame("Your Win..", "Your Win ANSWER IS: " + answer);
					no_win++;
					// save data to database
					saveDataPlayGame();
					reStartGame();
					setLabel();
					score = calScore();
					txt_score.setText(String.valueOf(score));
				}
			} else {
				Toast.makeText(MainGame.this, "Please Input data 4 digit",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	@SuppressWarnings("finally")
	private void saveDataPlayGame() {
		try {
			if (player1 == null) {

				System.out.println("Save Insert");
				GuessBean bean = db.createGuess(this.getGuessData());
				player1 = (int) bean.getId();

			} else {
				System.out.println("Save Update");
				GuessBean bean = this.getGuessData();
				bean.setId(player1);
				player1 = db.updateGuess(bean);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	private void setLabel() {
		txtNumber.setText(String.valueOf(number));
		txtPosition.setText(String.valueOf(position));
		txt_score.setText(String.valueOf(score));
		txtLastInput.setText(String.valueOf(no_lastinput));
		txt_no.setText(String.valueOf(no));
		txtWin.setText(String.valueOf(no_win));
		txtLose.setText(String.valueOf(no_lose));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_game, menu);
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

	private Dialog dialogListData() {
		Dialog dialog = new Dialog(MainGame.this);
		dialog.setContentView(R.layout.list_input_data);
		dialog.setTitle("Data All");
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		listViewData = (ListView) dialog.findViewById(R.id.list_data);

		ArrayAdapter adapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, listItem);
		listViewData.setAdapter(adapter);

		dialog.show();
		return dialog;
	}

	@SuppressLint("NewApi")
	private Integer[] randommNumberFixdigit() {
		Random rng = new Random(); // Ideally just create one instance globally
		// Note: use LinkedHashSet to maintain insertion order
		Set<Integer> generated = new LinkedHashSet<Integer>();
		while (generated.size() < 4) {
			Integer next = rng.nextInt(9) + 1;
			// As we're adding to a set, this will automatically do a
			// containment check
			generated.add(next);
		}
		Object[] answer = generated.toArray();
		Integer[] integerArray = Arrays.copyOf(answer, answer.length,
				Integer[].class);
		return integerArray;
	}

	// method level
	private void levelEasy() {
		// จำนวนครั้งในการเล่น 20 ครั้ง
		// จะมีตัเลข เฉลยให้ 1 ตัว
		this.noLimit = 20;
	}

	private void levelMedium() {
		// จำนวนครั้งในการเล่น 15 ครั้ง
		this.noLimit = 15;
	}

	private void levelHard() {
		// จำนวนครั้งในการเล่น 10 ครั้ง
		//
		this.noLimit = 10;

	}

	private void setGameLevel() {

		System.out.println("levelIndex: " + levelIndex);

		switch (levelIndex) {
		case 0: // easy
			this.levelEasy();
			break;
		case 1: // medium
			this.levelMedium();
			break;
		case 2: // hard
			this.levelHard();
			break;

		default:
			System.out.println("default -- levelIndex: " + levelIndex);
			break;
		}
	}

	private TextWatcher textOnChang = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			String position1 = "", position2 = "", position3 = "", position4 = "";
			String msg = "";

			switch (txtInputKey.getText().length()) {
			case 1:
				position1 = txtInputKey.getText().subSequence(0, 1).toString();
				imgPosition1.setImageResource(pic[Integer.parseInt(position1)]);
				break;
			case 2:
				position1 = txtInputKey.getText().subSequence(0, 1).toString();
				position2 = txtInputKey.getText().subSequence(1, 2).toString();

				imgPosition1.setImageResource(pic[Integer.parseInt(position1)]);
				imgPosition2.setImageResource(pic[Integer.parseInt(position2)]);

				break;
			case 3:
				position1 = txtInputKey.getText().subSequence(0, 1).toString();
				position2 = txtInputKey.getText().subSequence(1, 2).toString();
				position3 = txtInputKey.getText().subSequence(2, 3).toString();

				imgPosition1.setImageResource(pic[Integer.parseInt(position1)]);
				imgPosition2.setImageResource(pic[Integer.parseInt(position2)]);
				imgPosition3.setImageResource(pic[Integer.parseInt(position3)]);
				break;
			case 4:
				position1 = txtInputKey.getText().subSequence(0, 1).toString();
				position2 = txtInputKey.getText().subSequence(1, 2).toString();
				position3 = txtInputKey.getText().subSequence(2, 3).toString();
				position4 = txtInputKey.getText().subSequence(3, 4).toString();
				imgPosition1.setImageResource(pic[Integer.parseInt(position1)]);
				imgPosition2.setImageResource(pic[Integer.parseInt(position2)]);
				imgPosition3.setImageResource(pic[Integer.parseInt(position3)]);
				imgPosition4.setImageResource(pic[Integer.parseInt(position4)]);
				break;
			default:
				break;
			}
			msg = "position 1 : " + position1;
			msg += " \n postion 2 : " + position2;
			msg += " \n postion 3 : " + position3;
			msg += " \n postion 4 : " + position4;

			// Toast.makeText(MainGame.this, msg, Toast.LENGTH_LONG).show();
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			txtInputKey.getText().length();

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
	};

	private void reStartGame() {
		no = 0;
		position = 0;
		number = 0;
		no_lastinput = "";
		listItem.clear();
		this.startGame();
	}

	private int calScore() {
		return (noLimit - no) + score;
	}

	private void startGame() {
		if (no == 0) {
			Integer[] arrayAnswer = randommNumberFixdigit();
			int a4 = arrayAnswer[0];
			int a3 = arrayAnswer[1];
			int a2 = arrayAnswer[2];
			int a1 = arrayAnswer[3];

			/*
			 * Toast.makeText( MainGame.this, "Random a1 :" + a1 + "\n a2: " +
			 * a2 + "\n a3: " + a3 + "\n a4: " + a4, Toast.LENGTH_SHORT).show();
			 */
			this.answer = String.valueOf(a1) + String.valueOf(a2)
					+ String.valueOf(a3) + String.valueOf(a4);

			System.out.println("ANSWER : " + this.answer);

			Toast.makeText(MainGame.this, "ANSWER: " + this.answer,
					Toast.LENGTH_LONG).show();
		}
	}

	private AlertDialog.Builder dialogEndGame(String title, String message) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(MainGame.this);
		dialog.setTitle(title);
		dialog.setCancelable(false);
		dialog.setMessage(message);
		dialog.setPositiveButton(R.string.btn_main_menu,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent i = new Intent(MainGame.this, MainActivity.class);
						MainGame.this.startActivity(i);
						dialog.dismiss();
					}
				});
		dialog.setNegativeButton(R.string.btn_new_game,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});
		dialog.show();
		return dialog;

	}

	/*private AlertDialog.Builder dialogAnswer(String title, String message) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(MainGame.this);
		dialog.setTitle(title);
		dialog.setCancelable(false);
		dialog.setMessage(message);
		dialog.setPositiveButton(R.string.btn_main_menu,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent i = new Intent(MainGame.this, MainActivity.class);
						MainGame.this.startActivity(i);
						dialog.dismiss();
					}
				});
		dialog.setNegativeButton(R.string.btn_new_game,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});
		dialog.show();
		return dialog;

	}*/

	private OnClickListener dialogClose = new OnClickListener() {
		@Override
		public void onClick(View v) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(MainGame.this);
			dialog.setTitle("Exit Game");
			dialog.setIcon(R.drawable.icon_close32x32);
			dialog.setCancelable(false);
			dialog.setMessage("Stop Game Now Back To Main Game ");
			dialog.setPositiveButton(R.string.btn_ok,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent i = new Intent(MainGame.this,
									MainActivity.class);
							MainGame.this.startActivity(i);
						}
					});
			dialog.setNegativeButton(R.string.btn_cancle,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			dialog.create().show();
		}
	};
	private OnClickListener listViewClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			dialogListData();
		}
	};

	private GuessBean getGuessData() {
		GuessBean bean = new GuessBean();
		bean.setPlayerName(txt_player1.getText().toString());
		bean.setLevelPlay(levelIndex);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		bean.setDateCreate(dateFormat.format(new Date()));
		bean.setScorePlay(Integer.parseInt(txt_score.getText().toString()));
		bean.setWinner(Integer.parseInt(txtWin.getText().toString()));
		bean.setLose(Integer.parseInt(txtLose.getText().toString()));
		return bean;
	}

}
