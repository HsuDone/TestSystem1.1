package com.zxd.TestSys.beans;

public class Question {
	private int ansnum ;
	private int[] ans ;
	private int score ;
	private int level;
	private String[] choices = new String[5];
	public Question() {}
	public Question(int[] ans,int num,int score,int level) {
		this.ans = ans;
		this.ansnum = num;
		this.score = score;
		this.level = level;
	}
	@Override
	public String toString() {
		return "Question [ansnum=" + ansnum + ", score=" + score + ", level=" + level + "]";
	}
	public int getAnsnum() {
		return ansnum;
	}
	public void setAnsnum(int ansnum) {
		this.ansnum = ansnum;
	}
	public int[] getAns() {
		return ans;
	}
	public void setAns(int[] ans) {
		this.ans = ans;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String[] getChoices() {
		return choices;
	}
	public void setChoices(String[] choices) {
		this.choices = choices;
	}
	public String printQuestion() {
		int index = 0;
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < choices.length ;i++) 
			if(i == 0)
				sb.append(choices[i]+"\r\n");
			else
				sb.append((char)('A'+ index++)+choices[i]+"\r\n");
		return sb.toString();
	}
	
	
}
