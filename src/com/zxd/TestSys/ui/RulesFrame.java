package com.zxd.TestSys.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.zxd.TestSys.service.ClientContext;

public class RulesFrame extends JFrame{
	private TextArea rulesArea ;
	private JPanel	textPanel;
	private JPanel buttonPanel;
	private JButton confirmButton;
	private ClientContext uiController;
	public RulesFrame() {
		rulesArea = new TextArea(12, 70);
		buttonPanel = new JPanel();
		textPanel = new JPanel();
		confirmButton = new JButton("确定");
		this.setSize(700, 300);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.GUIinit();
	}
	public void GUIinit() {
		this.setLayout(new BorderLayout());
		buttonPanel.setLayout(new FlowLayout());
		textPanel.setLayout(new FlowLayout());
		Font	f = new Font("宋体", Font.PLAIN, 16);
		rulesArea.setFont(f);
		confirmButton.setFont(f);
		textPanel.add(rulesArea);
		buttonPanel.add(confirmButton);
		this.add(textPanel,BorderLayout.CENTER);
		this.add(buttonPanel,BorderLayout.SOUTH);
		ActionListen();
	}
	public void ActionListen() {
		confirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
	}
	public void updateRules(String rules) {
		this.rulesArea.setText(rules);
	}
	public void setUiController(ClientContext cc) {
		this.uiController = cc;
	}
	public static void main(String[] args) {
		new RulesFrame().setVisible(true);
	}
}
