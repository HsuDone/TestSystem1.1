package com.zxd.TestSys.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.zxd.TestSys.Util.Resources;
import com.zxd.TestSys.beans.User;
import com.zxd.TestSys.service.ClientContext;
import com.zxd.TestSys.service.EntityContext;

public class MenuFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel buttonsPanel, labelsPanel,otherPanel;
	private JButton examButton, resultButton, rulesButton, exitButton,configButton;
	private JLabel iconlabel, namelabel,welcomelabel;
	private ClientContext uiController;
	private EntityContext database;
	
	public MenuFrame() {
		buttonsPanel = new JPanel();
		labelsPanel = new JPanel();
		otherPanel = new JPanel();
		iconlabel = new JLabel(Resources.logo,Label.LEFT);
		namelabel = new JLabel(Resources.welcome);
		welcomelabel = new JLabel("xx同学您好，欢迎使用本系统",JLabel.CENTER);
		Font f = new Font("楷体", Font.BOLD, 18);
		welcomelabel.setFont(new Font("楷体", Font.BOLD, 25));
		examButton = new JButton("开始考试",Resources.exambegin);
		examButton.setBackground(Color.WHITE);
		examButton.setFont(f);
		examButton.setHorizontalTextPosition(SwingConstants.CENTER);
		examButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		resultButton = new JButton("考试结果",Resources.examresult);
		resultButton.setFont(f);
		resultButton.setHorizontalTextPosition(SwingConstants.CENTER);
		resultButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		resultButton.setBackground(Color.WHITE);
		
		rulesButton = new JButton("考试规则",Resources.examrules);
		rulesButton.setFont(f);
		rulesButton.setHorizontalTextPosition(SwingConstants.CENTER);
		rulesButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		rulesButton.setBackground(Color.WHITE);
		
		exitButton = new JButton("退出系统",Resources.exit);
		exitButton.setFont(f);
		exitButton.setHorizontalTextPosition(SwingConstants.CENTER);
		exitButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		exitButton.setBackground(Color.WHITE);
		
		configButton = new JButton("用户配置",Resources.userconfig);
		configButton.setFont(f);
		configButton.setHorizontalTextPosition(SwingConstants.CENTER);
		configButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		configButton.setBackground(Color.WHITE);
		
		initGUI();
		this.setSize(600, 650);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void updateUserName(String name) {
		this.welcomelabel.setText(name+"用户您好，欢迎使用本系统");
	}
	
	public void initGUI() {
		buttonsPanel.setLayout(new FlowLayout());
		examButton.setSize(100,100);
		resultButton.setSize(100,100);
		rulesButton.setSize(100,100);
		exitButton.setSize(100,100);
		buttonsPanel.add(examButton);
		buttonsPanel.add(resultButton);
		buttonsPanel.add(rulesButton);
		buttonsPanel.add(exitButton);
		buttonsPanel.add(configButton);
		labelsPanel.setLayout(new GridLayout(3, 1));
		labelsPanel.add(iconlabel);
		labelsPanel.add(namelabel);
		labelsPanel.add(welcomelabel);
		this.setLayout(new GridLayout(3, 1));
		labelsPanel.setBackground(Color.WHITE);
		buttonsPanel.setBackground(Color.WHITE);
		otherPanel.setBackground(Color.WHITE);
		
		this.add(labelsPanel);
		this.add(buttonsPanel);
		this.add(otherPanel);
		this.ActionsListen();
	}
	
	
	public void setController(ClientContext clientContext) {
		this.uiController = clientContext;
	}
	
	public void ActionsListen() {
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "确认退出?", "确认",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if(result == JOptionPane.OK_OPTION){
                    System.exit(0);
                }
			}
		});
		examButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				uiController.examStart(database.getInputUser());
			}
		});
		resultButton.addMouseListener(new MouseAdapter() {
			
		});
		rulesButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				uiController.showRules();
			}
		});
	}
	
	public ClientContext getUiController() {
		return uiController;
	}

	public void setUiController(ClientContext uiController) {
		this.uiController = uiController;
	}

	public EntityContext getDatabase() {
		return database;
	}

	public void setDatabase(EntityContext database) {
		this.database = database;
	}

	public static void main(String[] args) {
		MenuFrame frame = new MenuFrame();
		frame.setVisible(true);
	}

	
}
