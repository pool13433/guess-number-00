package com.example.guessnumber;

import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class DialogGuessNumber {
	public AlertDialog.Builder dialog;
	public String data;
	public Context context;
	public String[] itemList;
	public int itemSelect;
	
	public DialogGuessNumber() {
		super();
	}

	public DialogGuessNumber(Context context, String[] itemList,
			int itemSelect) {
		super();		
		this.context = context;
		this.itemList = itemList;
		this.itemSelect = itemSelect;
	}

	public String dialogLevel(){
		dialog = new AlertDialog.Builder(this.context);
		
		dialog.setTitle("Game Level");
		//dialog.setMessage("Select Game Level");
		dialog.setSingleChoiceItems(itemList, itemSelect, new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setItemSelect(which);
			}
		});
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				/*Toast.makeText(context,
						"Your Seleted : "+itemList[getItemSelect()], 
						Toast.LENGTH_LONG).show();*/
				dialog.cancel();
			}
		});
		dialog.setNegativeButton("CANCLE", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});	
		dialog.show();
		
		return itemList[getItemSelect()];
	}

	public int getItemSelect() {
		return itemSelect;
	}

	public void setItemSelect(int itemSelect) {
		this.itemSelect = itemSelect;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	

}
