package com.zxd.TestSys.beans;

import java.util.HashSet;
import java.util.Set;

public class Paper {
	private Set<Question> Queset = new HashSet<>();

	public void setQueset(Set<Question> queset) {
		Queset = queset;
	}

	public Set<Question> getQueset() {
		return Queset;
	}
	
	
}
