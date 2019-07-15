package com.zxd.TestSys.service;


import java.util.Vector;

import javax.swing.JOptionPane;

import com.zxd.TestSys.beans.User;
import com.zxd.TestSys.ui.ExamFrame;
import com.zxd.TestSys.ui.InfosFrame;
import com.zxd.TestSys.ui.LoginFrame;
import com.zxd.TestSys.ui.MenuFrame;
import com.zxd.TestSys.ui.ResultFrame;
import com.zxd.TestSys.ui.RulesFrame;

public class ClientContext {
	private LoginFrame loginf;
	private MenuFrame menuf;
	private ExamFrame examf;
	private RulesFrame rulesf;
	private InfosFrame infosf;
	private ResultFrame	resultf;
	private ExamService backControl;
	
	public LoginFrame getLoginf() {
		return loginf;
	}
	
	public void setLoginf(LoginFrame loginf) {
		this.loginf = loginf;
	}

	public MenuFrame getMenuf() {
		return menuf;
	}

	public void setMenuf(MenuFrame menuf) {
		this.menuf = menuf;
	}

	public ExamFrame getExamf() {
		return examf;
	}

	public void setExamf(ExamFrame examf) {
		this.examf = examf;
	}

	public ClientContext() {
	}
	
	public static ClientContext generateUiController(LoginFrame loginf, MenuFrame menuf, ExamFrame examf,
			RulesFrame rulesf,ResultFrame resultf,InfosFrame infosf) {
		ClientContext uiController = new ClientContext();
		uiController.loginf = loginf;
		uiController.menuf = menuf;
		uiController.examf = examf;
		uiController.rulesf = rulesf;
		uiController.infosf = infosf;
		uiController.resultf = resultf;
		return uiController;
	}
	public void login(String inputId,String inputPsw) {
		try {
			User u = backControl.getLegalUser(inputId, inputPsw);
			menuf.updateUserName(u.getName());
			menuf.setController(this);
			menuf.setVisible(true);
			loginf.updateMessage("欢迎您,"+backControl.getUserName()+",正在为您加载资源......");
			Thread.sleep(1000);
			loginf.setVisible(false);
		} catch (IdOrPswException e) {
			loginf.updateMessage(e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void showExam() {
		int result = JOptionPane.showConfirmDialog(null, "直接开始考试?", "确认",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if(result == JOptionPane.OK_OPTION) {
			examf.UpdateUserName(backControl.getUserName());
			examf.UpdateUserId(backControl.getUser().getUserId());
			examf.setController(this);
			examf.setVisible(true);
		}
	}
	public void showRules() {
		this.rulesf.setVisible(true);
		this.rulesf.updateRules(backControl.getRules());
	}
	
	public void showResults(int points) {
		JOptionPane.showMessageDialog(null, "您的得分为："+points);
		examf.setVisible(false);
	}
	public void showResults() {
		resultf.setVisible(true);
		resultf.updateResult(backControl.getResults());
	}
	public void startWork() {
		examf.setUiController(this);
		menuf.setUiController(this);
		loginf.setUiController(this);
		rulesf.setUiController(this);
		loginf.setVisible(true);
	}

	public void setBackControl(ExamService examHelper) {
		this.backControl = examHelper;
	}
	
	public void showLastQuestion() {
		backControl.showLastQuestion();
	}
	
	public void showChoices() {
		examf.enableChoices();
	}
	public void closeChoices() {
		examf.disableChoices();
	}
	public void showNextQuestion() {
		backControl.showNextQuestion();
	}
	public void showNewQuestion(String description) {
		examf.updateQuestion(description);
	}

	public void initSelection() {
		examf.initSelection();
	}

	public void showSelected(int ans) {
		examf.showSelected(ans);
	}

	public boolean[] getSelections() {
		return examf.getSelections();
	}

	public void showTimer(String updateTimer) {
		examf.updateTimer(updateTimer);
	}

	public void showScore() {
		backControl.examOver();
	}

	public void showMyInfos() {
		infosf.setVisible(true);
		Vector<String> columsName = new Vector<>();
		columsName.add("A");
		columsName.add("B");
		infosf.updateInfos(columsName,backControl.getRowData());
		infosf.initTableFormat();
	}
}
