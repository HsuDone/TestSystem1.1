package com.zxd.TestSys.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import com.zxd.TestSys.beans.Question;
import com.zxd.TestSys.beans.User;
import com.zxd.TestSys.ui.ExamFrame;

public class ExamService {
	private ExamFrame examf ;
	private ClientContext uiController;
	private boolean isBegin;
	private boolean isOver;
	private int quesIndex = -1;
	private List<Question> queslist;
	private Map<Integer , List<Integer>> myAnswer;
	private EntityContext database;
	public ExamService() {
		myAnswer = new HashMap<>();
	}
	public static ExamService generateExamService(ExamFrame ef,ClientContext cc,EntityContext enc) {
		ExamService es = new ExamService();
		es.queslist = new ArrayList<>(enc.getPaper().getQueset());
		es.setuiController(cc);
		es.setExamFrame(ef);
		es.setDatabase(enc);
		return es;
	}
	public void setExamFrame(ExamFrame ef) {
		examf = ef;
	}
	public void setuiController(ClientContext Controller) {
		this.uiController = Controller;
	}
	public void setDatabase(EntityContext ec) {
		this.database = ec;
	}
	public User getLegalUser(User inputUser,String inputId,String inputPsw) {
		User userlogin = null;
		if(inputId.equals("") || 
				inputPsw.equals("")){
			JOptionPane.showMessageDialog(null,"用户名和密码不能为空");
		}else if(inputUser == null) {
			JOptionPane.showMessageDialog(null,"用户名不存在");
		}
		else if(inputUser.getUserPsw().equals(inputPsw)){
			userlogin = inputUser;
		}else {
			JOptionPane.showMessageDialog(null,"密码错误");
		}
		return userlogin;
	}
	public void showNextQuestion() {
		if(! isBegin) {
			int result = JOptionPane.showConfirmDialog(null, "准备好开始考试了吗?", "确认",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(result == JOptionPane.OK_OPTION) {
					initMyAnswer();
					isBegin = true;
					examf.enableChoices();
					configTimer();
					++quesIndex;
				}
			}else {
				saveAnswers(quesIndex);
				++ quesIndex;
				if(quesIndex < 0 || quesIndex >= queslist.size()) {
					-- quesIndex;
					return;
				}
				showAnswers(quesIndex);
			}
		examf.changeQuestionText((quesIndex+1)+"."+queslist.get(quesIndex).printQuestion());
	}
	
	public void showLastQuestion() {
		saveAnswers(quesIndex);
		-- quesIndex;
		if(quesIndex < 0 || quesIndex >= queslist.size()) {
			++ quesIndex;
			return;
		}
		examf.changeQuestionText((quesIndex+1)+"."+queslist.get(quesIndex).printQuestion());
		showAnswers(quesIndex);
	}
	
	public void showAnswers(int pos) {
		examf.disableChoices();
		for(int ans : getMyAnswer().get(pos)) {
			examf.showSelected(ans);
		}
	}
	
	public void saveAnswers(int pos) {
		boolean[] isSelected = examf.getSelections();
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
					return;
				updateTimer(leftms);
			}
		},0,1000);
	}
	
	private void updateTimer(long sub){
		 int h=(int)(sub/1000/60/60);
		 int m=(int)(sub/1000/60%60);
		 int s=(int)(sub/1000%60);
		 String str=h+":"+m+":"+s;
		  //将String类型转换成Date类型的格式
		 SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		 Date date=new Date();
		 try{
			 date=sdf.parse(str);
		 }catch(Exception e){
		 e.printStackTrace();
		 }
		 //将Date类型的数设置成想要显示的时间格式,并写入JLable中
		 examf.updateTimer(sdf.format(date));
	}
	public void examOver() {
		saveAnswers(quesIndex);
		int points = getSumPoints(queslist, myAnswer);
		isOver = true;
		uiController.showResults(points);
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
		examf.setBackController(this);
		uiController.setBackControl(this);
	}
	public int getSumPoints(List<Question> ques,Map<Integer , List<Integer>> answers) 
	{
		int sum = 0;
		for(int i = 0 ;i < ques.size() ; i ++) {
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
				if(isRight)
					sum += q.getScore();
			}
		}
		return sum;
	}
}
