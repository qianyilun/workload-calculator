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
	
	private int total;
	
	private int sum;
	
	private String name;
	private boolean hide; 
	
	
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
}
