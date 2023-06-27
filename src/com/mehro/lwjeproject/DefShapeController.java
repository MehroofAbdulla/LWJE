package com.mehro.lwjeproject;

import static org.lwjgl.opengl.GL11.*;

import com.mehro.lwjs.graphics.Shader;
import com.mehro.lwjs.level.DefShapeAbstract;
import com.mehro.lwjs.maths.Vector3f;

public class DefShapeController extends DefShapeAbstract {
	
	public DefShapeController() {
		
	}
	
	public void controller() {
		 
	}

	public void paint(float r, float g, float b) {
		Shader.DEF.setUniform3f("col", new Vector3f(r, g, b));
	}

	public int getErrors() {
		return glGetError();
	}

}
