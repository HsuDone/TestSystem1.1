package com.zxd.TestSys.ui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.zxd.TestSys.beans.User;
import com.zxd.TestSys.service.ClientContext;
import com.zxd.TestSys.service.EntityContext;
import com.zxd.TestSys.service.ExamService;

public class ExamFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JPanel infoPanel;
	private JPanel questionPanel;
	private JPanel operatePanel;
	private TextArea questArea;
	private JLabel namelabel;
	private JLabel idlabel;
	private JLabel sumtimelabel;
	private JLabel timerlabel;
	private JButton uploadbutton;
	private JButton nextquebutton;
	private JButton lastquebutton;
	private JCheckBox choiceA ;
	private JCheckBox choiceB ;
	private JCheckBox choiceC ;
	private JCheckBox choiceD ;
	private ClientContext uiController;
	private ExamService backController;
	private EntityContext database;
	public ExamFrame() {
		infoPanel = new JPanel();
		questionPanel = new JPanel();
		operatePanel = new JPanel();
		questArea = new TextArea(7, 65);
		questArea.setFont(new Font("楷体", Font.PLAIN, 18));
		Font f = new Font("楷体", Font.PLAIN, 25);
		namelabel = new JLabel("姓名:xxx");
		namelabel.setFont(f);
		timerlabel = new JLabel("",JLabel.RIGHT);
		idlabel = new JLabel("编号:xxx");
		idlabel.setFont(f);
		sumtimelabel = new JLabel("时间:60分钟");
		sumtimelabel.setFont(f);
		uploadbutton = new JButton("交卷");
		lastquebutton = new JButton("上一题");
		nextquebutton = new JButton("开始考试");
		choiceA = new JCheckBox("A");
		choiceB = new JCheckBox("B");
		choiceC = new JCheckBox("C");
		choiceD = new JCheckBox("D");
		initGUI();
	}
	
	public void UpdateUserName(String name) {
		this.namelabel.setText("姓名:"+ name);
	}
	public void UpdateUserId(String id) {
		this.idlabel.setText("编号:"+ id);
	}
	public void initGUI() {
		this.setLayout(new GridLayout(3, 1));
		infoPanel.setLayout(new FlowLayout());
		infoPanel.add(idlabel);
		infoPanel.add(namelabel);
		infoPanel.add(sumtimelabel);
		questionPanel.setLayout(new FlowLayout());
		questionPanel.add(questArea);
		questionPanel.add(choiceA);
		questionPanel.add(choiceB);
		questionPanel.add(choiceC);
		questionPanel.add(choiceD);
		operatePanel.setLayout(new FlowLayout());
		operatePanel.add(lastquebutton);
		operatePanel.add(nextquebutton);
		operatePanel.add(uploadbutton);
		operatePanel.add(timerlabel);
		this.add(infoPanel);
		this.add(questionPanel);
		this.add(operatePanel);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(700, 650);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		ActionListen();
	}
	
	public void ActionListen() {
		this.lastquebutton.setEnabled(false);
		this.choiceA.setEnabled(false);
		this.choiceB.setEnabled(false);
		this.choiceC.setEnabled(false);
		this.choiceD.setEnabled(false);
		this.nextquebutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				backController.showNextQuestion();
			}
		});
		this.lastquebutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				backController.showLastQuestion();
			}
		});
		this.uploadbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				backController.examOver();
			}
		});
	}
	
	public void enableChoices() {
		lastquebutton.setEnabled(true);
		choiceA.setEnabled(true);
		choiceB.setEnabled(true);
		choiceC.setEnabled(true);
		choiceD.setEnabled(true);
		nextquebutton.setText("下一题");
	}
	
	public void disableChoices() {
		choiceA.setSelected(false);
		choiceB.setSelected(false);
		choiceC.setSelected(false);
		choiceD.setSelected(false);
	}
	
	public void changeQuestionText(String que_description) {
		questArea.setText(que_description);
	}
	public void showSelected(int ans) {
		switch(ans) {
		case 0 : 
			choiceA.setSelected(true);
			break;
		case 1 :
			choiceB.setSelected(true);
			break;
		case 2 : 
			choiceC.setSelected(true);
			break;
		case 3 :
			choiceD.setSelected(true);
			break;
		}
	}
	
	 
	public ClientContext getUiController() {
		return uiController;
	}

	public void setUiController(ClientContext uiController) {
		this.uiController = uiController;
	}

	public ExamService getExamController() {
		return backController;
	}

	public void setExamController(ExamService examController) {
		this.backController = examController;
	}

	public EntityContext getDatabase() {
		return database;
	}

	public void setDatabase(EntityContext database) {
		this.database = database;
	}

	public void updateTimer(String leftTime) {
		this.timerlabel.setText(leftTime);
	}	 
	public static void main(String[] args) {
		ExamFrame ef = new ExamFrame();
		ExamService es = new ExamService();
		es.setExamFrame(ef);
		ef.setBackController(es);
		ef.setVisible(true);
	}
	public void setController(ClientContext clientContext) {
		this.uiController = clientContext;
	}
	public void setBackController(ExamService es) {
		this.backController = es;
	}
	public boolean[] getSelections() {
		boolean[] isSelected = {choiceA.isSelected(),choiceB.isSelected(),
				choiceC.isSelected(),choiceD.isSelected()};
		return isSelected;
	}
	
}
