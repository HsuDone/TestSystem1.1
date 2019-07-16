package com.zxd.TestSys.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.zxd.TestSys.beans.Question;
import com.zxd.TestSys.beans.User;

public class ExamService {
	private ClientContext uiController;
	private boolean isBegin;
	private boolean isOver;
	private int quesIndex = -1;
	private List<Question> queslist;
	private Map<Integer , List<Integer>> myAnswer;
	private EntityContext database;
	private StringBuilder result = new StringBuilder("请先完成考试！");
	public ExamService() {
		myAnswer = new HashMap<>();
	}
	
	public static ExamService generateExamService(ClientContext cc,EntityContext enc) {
		ExamService es = new ExamService();
		es.queslist = new ArrayList<>(enc.getPaper().getQueset());
		es.setuiController(cc);
		es.setDatabase(enc);
		return es;
	}
	
	public void setuiController(ClientContext Controller) {
		this.uiController = Controller;
	}
	
	public void setDatabase(EntityContext ec) {
		this.database = ec;
	}
	
	public EntityContext getDatebase() {
		return this.database;
	}
	
	public String getUserName() {
		return database.getInputUser().getName();
	}
	
	public User getUser() {
		return database.getInputUser();
	}
	
	public String getRules() {
		return database.getRules();
	}
	public String getResults() {
		return result.toString();
	}
	public User getLegalUser(String inputId,String inputPsw) throws IdOrPswException {
		User userlogin = null;
		User inputUser = database.getUsermap().get(inputId);
		if(inputId.equals("") || 
				inputPsw.equals("")){
			throw new IdOrPswException("用户名和密码不能为空");
		}else if(inputUser == null) {
			throw new IdOrPswException("用户名不存在,请检查或注册");
		}
		else if(inputUser.getUserPsw().equals(inputPsw)){
			userlogin = inputUser;
			database.setInputUser(inputUser);
		}else 
			throw new IdOrPswException("密码错误");
		return userlogin;
	}
	
	public void showNextQuestion() {
		if(! isBegin) 
		{
			int result = JOptionPane.showConfirmDialog(null, "准备好开始考试了吗?", "确认",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(result == JOptionPane.OK_OPTION) 
				{
					uiController.showChoices();
					initMyAnswer();
					isOver = false;
					isBegin = true;
					configTimer();
					++quesIndex;
				}
			}else {
				saveAnswers(quesIndex);
				++ quesIndex;
				if(quesIndex < 0 || quesIndex >= queslist.size()) 
				{
					-- quesIndex;
					return;
				}
				showAnswers(quesIndex);
			}
		uiController.showNewQuestion((quesIndex+1)+"."+queslist.get(quesIndex).printQuestion());
	}
	
	public void showLastQuestion() {
		saveAnswers(quesIndex);
		-- quesIndex;
		if(quesIndex < 0 || quesIndex >= queslist.size()) {
			++ quesIndex;
			return;
		}
		uiController.showNewQuestion((quesIndex+1)+"."+queslist.get(quesIndex).printQuestion());
		showAnswers(quesIndex);
	}
	
	public void showAnswers(int pos) {
		uiController.initSelection();
		for(int ans : getMyAnswer().get(pos)) {
			uiController.showSelected(ans);
		}
	}
	
	public void saveAnswers(int pos) {
		boolean[] isSelected = uiController.getSelections();
		List<Integer> answer = new ArrayList<>();
		for(int i = 0 ;i < isSelected.length ; i ++)
			if(isSelected[i])
				answer.add(i);
		myAnswer.put(pos, answer);
	}
	
	public void configTimer() {
		int mins = 60;
		long end=System.currentTimeMillis()+mins*1000*60;
		Timer timer=new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				long leftms=end-System.currentTimeMillis();
				if(leftms<0 || isOver)
				{
					this.cancel();
					return;
				}
				uiController.showTimer(parseTime2Str(leftms));
			}
		},0,1000);
	}
	
	private String parseTime2Str(long sub){
		 int h=(int)(sub/1000/60/60);
		 int m=(int)(sub/1000/60%60);
		 int s=(int)(sub/1000%60);
		 String str="剩余时间: "+h+":"+m+":"+s;
		 return str;
	}
	
	public void examOver() {
		saveAnswers(quesIndex);
		int points = getSumPoints(queslist, myAnswer);
		isOver = true;
		isBegin = false;
		quesIndex = -1;
		uiController.showResults(points);
		uiController.closeChoices();
	}
	
	private void initMyAnswer() {
		for(int i = 0 ; i < queslist.size() ; i ++) {
			myAnswer.put(i, new ArrayList<Integer>());
		}
	}
	
	public Map<Integer, List<Integer>> getMyAnswer() {
		return myAnswer;
	}
	
	public List<Question> getQuesList() {
		return this.queslist;
	}
	
	public void startWork() {
		uiController.setBackControl(this);
	}
	
	public int getSumPoints(List<Question> ques,Map<Integer , List<Integer>> answers) 
	{
		int sum = 0;
		result = new StringBuilder("用户:"+database.getInputUser().getName()+"\r\n本次考试成绩如下:\r\n");
		for(int i = 0 ;i < ques.size() ; i ++) {
			result.append("NO."+(i+1));
			Question q = ques.get(i);
			boolean isRight = true;
			List<Integer> myAns = answers.get(i);
			int[] rightAnswer = q.getAns();
			Integer[] myanswer = myAns.toArray(new Integer[myAns.size()]); 
			if(rightAnswer.length == myanswer.length)
			{
				for(int j = 0 ; j < rightAnswer.length ; j ++)
				{
					if(rightAnswer[j] != myanswer[j] + 1) {
						isRight = false;
						break;
					}
				}
				
				if(isRight) {
					sum += q.getScore();
					result.append(" ✔ "+getAnswerStr(rightAnswer)+"\r\n");
				}else
					result.append(" × 正确答案: "+getAnswerStr(rightAnswer)+"\r\n");
			}else
				result.append(" × 正确答案: "+getAnswerStr(rightAnswer)+"\r\n");
		}
		result.append("最终总评: "+sum+"\r\n");
		return sum;
	}
	private String getAnswerStr(int[] ans) {
		StringBuilder sb = new StringBuilder("[");
		for(int n : ans)
			switch (n) {
			case 1:
				sb.append("A");
				break;
			case 2:
				sb.append("B");
				break;
			case 3:
				sb.append("C");
				break;
			case 4:
				sb.append("D");
				break;
			
		}sb.append("]");
		return sb.toString();
	}
	public Vector<Vector<String>> getRowData() {
		Vector<Vector<String>> result = new Vector<>();
		
		User userlogin = database.getInputUser();
		Vector<String> row1 = new Vector<String>();
		row1.add("用户名");
		row1.add(userlogin.getName());
		result.add(row1);
		Vector<String> row2 = new Vector<String>();
		row2.add("账号");
		row2.add(userlogin.getUserId());
		result.add(row2);
		Vector<String> row3 = new Vector<String>();
		row3.add("电话号码");
		row3.add(userlogin.getPhone());
		result.add(row3);
		Vector<String> row4 = new Vector<String>();
		row4.add("电子邮箱");
		row4.add(userlogin.getE_mail());
		result.add(row4);
		
		return result;
	}
}
