package com.mehro.lwjs.level;

import com.mehro.lwjeproject.MethodCaller;
import com.mehro.lwjs.graphics.Shader;
import com.mehro.lwjs.graphics.VertexArray;
import com.mehro.lwjs.maths.Matrix4f;
import com.mehro.lwjs.maths.Vector3f;

public class DefShape {

	private VertexArray mesh;
	
	private float SIZE = 1.56f;
	 
	public static Vector3f position = new Vector3f();
	
	private MethodCaller caller = new MethodCaller();
	
	public DefShape() {
		float[] vertices = new float[] {
			-SIZE / 2.0f, -SIZE / 2.0f, 0.2f,
			-SIZE / 2.0f,  SIZE / 2.0f, 0.2f,
			 SIZE / 2.0f,  SIZE / 2.0f, 0.2f,
			 SIZE / 2.0f, -SIZE / 2.0f, 0.2f
		};
			
		byte[] indices = new byte[] {
			0, 1, 2,
			2, 3, 0
		};
		
		float[] tcs = new float[] {
			0, 1,
			0, 0,
			1, 0,
			1, 1
		};
		
		mesh = new VertexArray(vertices, indices, tcs);
	}
	
	public void update() {
		caller.controller();
	}
	
	public void render() {
		Shader.DEF.enable();
		mesh.bind();
		Shader.DEF.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(0.5f, 0.0f, 0.0f)));
		Shader.DEF.setUniformMat4f("ml_matrix", Matrix4f.translate(position));
		caller.paint();
		mesh.draw();
		mesh.unbind();
		Shader.DEF.disable();
		caller.getErrors();
	}
	
}
