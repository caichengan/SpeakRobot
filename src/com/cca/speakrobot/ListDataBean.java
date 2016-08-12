package com.cca.speakrobot;

import java.util.List;

public class ListDataBean
{
	public int bg;
	public int ed;
	public boolean ls;
	public int sn;	
	public List<Wsbean> ws;	
	class Wsbean{
		public int bg;
		public List<Cwbean> cw;
	}
	class Cwbean{
		public double sc;
		public String w;
	}
}
