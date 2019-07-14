package com.zxd.TestSys.Util;

import java.io.InputStream;
import java.net.URL;

import javax.swing.ImageIcon;

public class Resources {
	public static ImageIcon exambegin ;
	public static ImageIcon examresult;
	public static ImageIcon examrules;
	public static ImageIcon userconfig;
	public static ImageIcon exit;
	public static ImageIcon logo;
	public static ImageIcon welcome;
	public static InputStream useris;
	public static URL corejava;
	public static URL rule;
	
	static {
		logo = new ImageIcon(Resources.class.getResource("logo.png"));
		welcome = new ImageIcon(Resources.class.getResource("welcome.png"));
		exambegin = new ImageIcon(Resources.class.getResource("exam.png"));
		exit = new ImageIcon(Resources.class.getResource("exit.png"));
		examrules = new ImageIcon(Resources.class.getResource("rules.png"));
		userconfig = new ImageIcon(Resources.class.getResource("config.png"));
		examresult = new ImageIcon(Resources.class.getResource("result.png"));
		useris = Resources.class.getResourceAsStream("user.txt");
		corejava = Resources.class.getResource("corejava.txt");
		rule = Resources.class.getResource("rule.txt");
	}
	
}
