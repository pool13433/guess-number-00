package com.example.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.bean.GuessBean;
import com.example.guessnumber.R;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.AndroidException;
import android.widget.RemoteViews.ActionException;
import android.widget.Toast;

public class GuessDataSource {
	Context context;
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COL_ID,
			MySQLiteHelper.COL_PLAYER, MySQLiteHelper.COL_LEVEL,
			MySQLiteHelper.COL_SCORE, MySQLiteHelper.COL_DATE_CREATE,
			MySQLiteHelper.COL_WINNER, MySQLiteHelper.COL_LOSE };

	public GuessDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
		this.context = context;
	}

	public void openDatabase() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void closeDatabase() {
		dbHelper.close();
	}

	@SuppressWarnings("finally")
	public GuessBean createGuess(GuessBean bean) throws AndroidException {
		Cursor cursor = null;
		GuessBean newBean = null;
		try {
			this.openDatabase();
			ContentValues v = new ContentValues();
			v.put(MySQLiteHelper.COL_PLAYER, bean.getPlayerName());
			v.put(MySQLiteHelper.COL_LEVEL, bean.getLevelPlay());
			v.put(MySQLiteHelper.COL_SCORE, bean.getScorePlay());
			v.put(MySQLiteHelper.COL_DATE_CREATE, bean.getDateCreate()
					.toString());
			v.put(MySQLiteHelper.COL_WINNER, bean.getWinner());
			v.put(MySQLiteHelper.COL_LOSE, bean.getLose());

			long newId = database.insert(MySQLiteHelper.TABLE_GUESS, null, v);

			cursor = database.query(MySQLiteHelper.TABLE_GUESS,
					allColumns, MySQLiteHelper.COL_ID + " = " + newId, null,
					null, null, null);
			cursor.moveToFirst();
			newBean = cursorToGuessBean(cursor);
		}catch(Exception e){
			e.printStackTrace();
			Toast.makeText(context,  "Exception: "+e.toString(), Toast.LENGTH_LONG).show();
		} finally {
			cursor.close();
			this.closeDatabase();
			return newBean;
		}

	}

	public void deleteGuess() {

	}

	@SuppressWarnings("finally")
	public int updateGuess(GuessBean bean) throws AndroidException{
		Integer id = null;
		try {
			ContentValues values = new ContentValues();
			values.put(MySQLiteHelper.COL_PLAYER, bean.getPlayerName());
			values.put(MySQLiteHelper.COL_LEVEL, bean.getLevelPlay());
			values.put(MySQLiteHelper.COL_SCORE, bean.getScorePlay());
			values.put(MySQLiteHelper.COL_DATE_CREATE, bean.getDateCreate()
					.toString());
			values.put(MySQLiteHelper.COL_WINNER, bean.getWinner());
			values.put(MySQLiteHelper.COL_LOSE, bean.getLose());

			id =  database.update(MySQLiteHelper.TABLE_GUESS, values,
					MySQLiteHelper.COL_ID + " = ?",
					new String[] { String.valueOf(bean.getId()) });
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context, "Exception: "+e.toString(), Toast.LENGTH_LONG).show();
		}finally{
			return id;
		}
	}

	@SuppressWarnings("finally")
	public GuessBean getSingleGuess(int id)throws AndroidException {
		GuessBean newBean = null;
		Cursor cursor = null;
		try {
			cursor = database.query(MySQLiteHelper.TABLE_GUESS, allColumns,
					MySQLiteHelper.COL_ID + " = " + id, null, null, null, null);
			cursor.moveToFirst();
			newBean = cursorToGuessBean(cursor);			
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context, "Exception: "+e.toString(), Toast.LENGTH_LONG).show();
		}finally{
			cursor.close();
			return newBean;
		}
		
	}

	@SuppressWarnings("finally")
	public List<GuessBean> getAllGuess() {
		Cursor cursor = null;
		List<GuessBean> listHighScore = null;
		try {
			this.openDatabase();
			
			listHighScore = new ArrayList<>();

			cursor = database.query(MySQLiteHelper.TABLE_GUESS, allColumns,
					null, null, null, null, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				GuessBean bean = cursorToGuessBean(cursor);

				listHighScore.add(bean);

				cursor.moveToNext();
			}

		} catch (ActionException e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			this.closeDatabase();
			return listHighScore;
		}
	}

	@SuppressWarnings("finally")
	public List<GuessBean> getListGuessHighScoreLimtRecord(int limit) {
		Cursor cursor = null;
		List<GuessBean> listHighScore = null;
		try {
			this.openDatabase();
			
			cursor = database.query(MySQLiteHelper.TABLE_GUESS,
					allColumns,
					null,null,null, null, MySQLiteHelper.COL_SCORE+" DESC", "0,5");
			listHighScore = new ArrayList<GuessBean>();
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				GuessBean bean = cursorToGuessBean(cursor);

				listHighScore.add(bean);

				cursor.moveToNext();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context, "Exception: "+e.toString(), Toast.LENGTH_LONG).show();			
		} finally {
			cursor.close();
			this.closeDatabase();
			return listHighScore;
		}
	}

	private GuessBean cursorToGuessBean(Cursor cursor) {
		GuessBean bean = null;
		try {
			bean = new GuessBean();

			bean.setId(cursor.getLong(0)); // id
			bean.setPlayerName(cursor.getString(1)); // play
			bean.setLevelPlay(cursor.getInt(2)); // level
			bean.setScorePlay(cursor.getInt(3)); // score
			bean.setDateCreate(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COL_DATE_CREATE))); // date
			bean.setWinner(cursor.getInt(5)); // win
			bean.setLose(cursor.getInt(6)); // win

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
}
