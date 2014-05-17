package com.example.bean;

import java.util.Date;

public class GuessBean {
	private long id;
	private String playerName;
	private int levelPlay;
	private int scorePlay;
	private String dateCreate;
	private int winner;
	private int lose;
	
	
	public GuessBean() {
		super();
	}
	public GuessBean(long id) {
		super();
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public int getLevelPlay() {
		return levelPlay;
	}
	public void setLevelPlay(int levelPlay) {
		this.levelPlay = levelPlay;
	}
	public int getScorePlay() {
		return scorePlay;
	}
	public void setScorePlay(int scorePlay) {
		this.scorePlay = scorePlay;
	}
	
	
	public String getDateCreate() {
		return dateCreate;
	}
	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}
	public int getWinner() {
		return winner;
	}
	public void setWinner(int winner) {
		this.winner = winner;
	}
	public int getLose() {
		return lose;
	}
	public void setLose(int lose) {
		this.lose = lose;
	}
	
}
