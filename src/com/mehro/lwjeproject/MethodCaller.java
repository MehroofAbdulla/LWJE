package com.mehro.lwjeproject;

public class MethodCaller {
	private DefShapeController ds = new DefShapeController();

	public MethodCaller() {
		
	}
	
	public void controller() {
		ds.controller();
	}
	
	public void paint() {
		ds.paint(0.8f, 0.3f, 0.2f);
	}

	public void getErrors() {
		System.out.println(ds.getErrors());
	}
	
}
