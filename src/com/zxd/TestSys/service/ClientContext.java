package com.zxd.TestSys.service;

import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.zxd.TestSys.beans.Question;
import com.zxd.TestSys.beans.User;
import com.zxd.TestSys.ui.ExamFrame;
import com.zxd.TestSys.ui.LoginFrame;
import com.zxd.TestSys.ui.MenuFrame;
import com.zxd.TestSys.ui.RulesFrame;

public class ClientContext {
	private LoginFrame loginf;
	private MenuFrame menuf;
	private ExamFrame examf;
	private RulesFrame rulesf;
	private ExamService backControl;
	private EntityContext database;
	
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
	
//	public ClientContext(LoginFrame loginf, MenuFrame menuf, ExamFrame examf,
//			RulesFrame rulesf,EntityContext ec) 
//	{
//		super();
//		this.loginf = loginf;
//		this.menuf = menuf;
//		this.examf = examf;
//		this.rulesf = rulesf;
//		this.database = ec;
//	}
	public static ClientContext generateUiController(LoginFrame loginf, MenuFrame menuf, ExamFrame examf,
			RulesFrame rulesf,EntityContext ec) {
		ClientContext uiController = new ClientContext();
		uiController.loginf = loginf;
		uiController.menuf = menuf;
		uiController.examf = examf;
		uiController.rulesf = rulesf;
		uiController.database = ec;
		return uiController;
	}
	public void login(String inputId,String inputPsw) {
		User u =backControl.getLegalUser(database.getInputUser(), inputId, inputPsw);
		if(u != null) {
			menuf.updateUserName(database.getInputUser().getName());
			menuf.setController(this);
			menuf.setVisible(true);
			loginf.setVisible(false);
		}
	}
	
	public void examStart(User inputUser) {
		int result = JOptionPane.showConfirmDialog(null, "直接开始考试?", "确认",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if(result == JOptionPane.OK_OPTION) {
			examf.UpdateUserName(database.getInputUser().getName());
			examf.UpdateUserId(database.getInputUser().getUserId());
			examf.setController(this);
			examf.setVisible(true);
		}
	}
	public void showRules() {
		this.rulesf.setVisible(true);
		String rules = database.getRules();
		this.rulesf.updateRules(rules);
	}
	
	public void showResults(int points) {
		JOptionPane.showMessageDialog(null, "您的得分为："+points);
		examf.setVisible(false);
	}
	public void startWork() {
		examf.setUiController(this);
		menuf.setUiController(this);
		loginf.setUiController(this);
//		rulesf.setUiController(this);
		loginf.setVisible(true);
	}

	public void setBackControl(ExamService examHelper) {
		this.backControl = examHelper;
	}
	
	
}
