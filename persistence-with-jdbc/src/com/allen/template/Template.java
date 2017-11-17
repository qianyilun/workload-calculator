package com.allen.template;

/**
 * Class holding information on a template.
 * 
 * @author Allen Qian
 */

public class Template {
	private int id;
	
	private int nw;
	private int ms;
	private int sm;
	private int dsm;
	private int fc;
	private int lod;
	private int pcm;
	private int sa;
	private int rtc;
	
	private int total;
	
	private int sum;
	
	private String name;
	private boolean hide; 
	private String iNumber;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public boolean isHide() {
		return hide;
	}
	public void setHide(boolean hide) {
		this.hide = hide;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNw() {
		return nw;
	}
	public void setNw(int nw) {
		this.nw = nw;
	}
	public int getMs() {
		return ms;
	}
	public void setMs(int ms) {
		this.ms = ms;
	}
	public int getSm() {
		return sm;
	}
	public void setSm(int sm) {
		this.sm = sm;
	}
	public int getDsm() {
		return dsm;
	}
	public void setDsm(int dsm) {
		this.dsm = dsm;
	}
	public int getFc() {
		return fc;
	}
	public void setFc(int fc) {
		this.fc = fc;
	}
	public int getLod() {
		return lod;
	}
	public void setLod(int lod) {
		this.lod = lod;
	}
	public int getPcm() {
		return pcm;
	}
	public void setPcm(int pcm) {
		this.pcm = pcm;
	}
	public int getSa() {
		return sa;
	}
	public void setSa(int sa) {
		this.sa = sa;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public String getiNumber() {
		return iNumber;
	}
	public void setiNumber(String iNumber) {
		this.iNumber = iNumber;
	}
	public void generateINumber() {
		if (this.name.equals("Alex")) {
			iNumber = "I819350";
		} 
		if (this.name.equals("Allen")) {
			iNumber = "I860745";
		}
		if (this.name.equals("April")) {
			iNumber = "I817290";
		}
		if (this.name.equals("Graham")) {
			iNumber = "I819047";
		}
		if (this.name.equals("Hitomi")) {
			iNumber = "I819718";
		}
		if (this.name.equals("John H")) {
			iNumber = "I817231";
		}
		if (this.name.equals("John L")) {
			iNumber = "I818799";
		}
		if (this.name.equals("Julie")) {
			iNumber = "I037162";
		}
		if (this.name.equals("Leila")) {
			iNumber = "I815070";
		}
		if (this.name.equals("Marc")) {
			iNumber = "I819935";
		}
		if (this.name.equals("Pedro")) {
			iNumber = "I829115";
		}
		if (this.name.equals("Stefan")) {
			iNumber = "I819795";
		}
		if (this.name.equals("Yvonne")) {
			iNumber = "I819958";
		}
		if (this.name.equals("Ahaan")) {
			iNumber = "I864363";
		}
	}
	public int getRtc() {
		return rtc;
	}
	public void setRtc(int rtc) {
		this.rtc = rtc;
	}
}
