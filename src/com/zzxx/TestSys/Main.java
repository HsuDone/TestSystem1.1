package com.zzxx.TestSys;

import com.zxd.TestSys.service.ClientContext;
import com.zxd.TestSys.service.EntityContext;
import com.zxd.TestSys.service.ExamService;
import com.zxd.TestSys.ui.ExamFrame;
import com.zxd.TestSys.ui.LoginFrame;
import com.zxd.TestSys.ui.MenuFrame;
import com.zxd.TestSys.ui.RulesFrame;

public class Main {
	public static void main(String[] args) {
			LoginFrame loginFrame = new LoginFrame();
			MenuFrame menuFrame = new MenuFrame();
			ExamFrame examFrame = new ExamFrame();
			EntityContext dataBase = new EntityContext();
			RulesFrame rulesFrame = new RulesFrame();
			//为服务注入依赖
			ClientContext uiController = ClientContext.generateUiController(loginFrame,menuFrame,examFrame,rulesFrame,dataBase);
			ExamService backController = ExamService.generateExamService(examFrame, uiController,dataBase);
			/*让窗口都持有数据库*/
			loginFrame.setDatabase(dataBase);
			examFrame.setDatabase(dataBase);
			menuFrame.setDatabase(dataBase);
			rulesFrame.setDatabase(dataBase);
			//服务启动：为服务的窗口注入依赖
			uiController.startWork();
			backController.startWork();
	}
}
