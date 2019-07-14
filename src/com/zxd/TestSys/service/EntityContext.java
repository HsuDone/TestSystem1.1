package com.zxd.TestSys.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zxd.TestSys.Util.Resources;
import com.zxd.TestSys.beans.Paper;
import com.zxd.TestSys.beans.Question;
import com.zxd.TestSys.beans.User;

public class EntityContext {
	private Map<String, User> usermap = this.getUsers();
	private User inputUser ;
	
	public Map<String, User> getUsermap() {
		return usermap;
	}
	
	public User getInputUser() {
		return inputUser;
	}
	
	public void setInputUser(String userId) {
		inputUser = this.usermap.get(userId);
	}
	
	public Map<Integer, List<Question>> getquestionsMap()  {
		Map<Integer, List<Question>> map = new HashMap<>();
		URL paperURL = Resources.corejava;
		BufferedReader br = null;
		try {
			br = new BufferedReader(
					new InputStreamReader(new FileInputStream(paperURL.getFile()), "GBK"));
			String line = null;
			while((line = br.readLine()) != null) {
				//首行直接建立对象
				Question que = getQuestion(line);
				String[] ss = new String[5];//五行题干,第一行为题目
				for(int i = 0 ; i < 5 ; i ++) 
					ss[i] = br.readLine();
				que.setChoices(ss); 
				if(map.containsKey(que.getLevel()))
					map.get(que.getLevel()).add(que);
				else {
					List<Question> newlist = new ArrayList<>();
					newlist.add(que);
					map.put(que.getLevel(),newlist);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return map;
	}
	
	private Question getQuestion(String s) {
		s.replaceFirst("@", "");
		String[] infos = s.split(",");
		for(int i = 0 ; i < infos.length ; i ++) 
			infos[i] = infos[i].substring(infos[i].indexOf('=')+1);
		
		String[] ans = infos[0].split("/");
		int[] answers = new int[ans.length];
		
		for(int i = 0 ; i < answers.length ; i ++) 
			answers[i] = Integer.valueOf(ans[i]);
		
		Question que = new Question(answers, answers.length,
				Integer.valueOf(infos[1]),Integer.valueOf(infos[2]));
		return que;
	}
	
	public Paper getPaper() {
		Paper p = new Paper();
		Map<Integer, List<Question>> map = this.getquestionsMap();
		for(int Hard : map.keySet()) {
			List<Question> list = map.get(Hard);
			boolean[] isused = new boolean[list.size()];
			for(int i = 0 ; i < 2 ; i ++) {
				int randomindex = (int)(Math.random()*list.size());
				while(isused[randomindex]) 
					randomindex = (int)(Math.random()*list.size());
				isused[randomindex] = true;
				p.getQueset().add(list.get(randomindex));
			}
		}
		return p;
	}
	
	public Map<String,User> getUsers() {
		BufferedReader br = null;
		Map<String, User> map = new HashMap<>();
		try {
			br = new BufferedReader(new InputStreamReader(Resources.useris, "GBK"));
			String line = null;
			while((line = br.readLine()) != null) {
				line = line.trim();
				if(line.contains("#")||line.equals(""))
					continue;
				String[] ss = line.split(":");
				User user = new User(ss[0], ss[1], ss[2], ss[3], ss[4]);
				map.put(ss[0], user);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return map;
	}
	public String getRules() {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(Resources.rule.getFile()),"GBK"));
			String line = null;
			while((line = br.readLine()) != null)
				sb.append(line+"\r\n");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
