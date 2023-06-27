package com.mehro.lwjs.level;

import static org.lwjgl.input.Keyboard.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lwjgl.input.Mouse;

import com.mehro.lwjs.graphics.Shader;
import com.mehro.lwjs.graphics.Texture;
import com.mehro.lwjs.graphics.VertexArray;
import com.mehro.lwjs.input.Input;
import com.mehro.lwjs.maths.Matrix4f;
import com.mehro.lwjs.maths.Vector3f;

public class Level implements ChangeListener {

	private JFrame mlwje = new JFrame("More Info About LWJE");
	private DefShape ds;
	
	private VertexArray bg;
	private Texture bgdef;	
	private Texture world;
	private Texture tcat;
	private Texture ground;
	
	public static Vector3f color = new Vector3f(0.0f, 0.0f, 0.0f);
	
	private int texture = 1;
	
	private float amount = 2.0f;
	
	private boolean isInit = false;
	private boolean isFail = false;
	
	public Level() {
		if (texture == 1) {
			glActiveTexture(GL_TEXTURE1);
		}
		if (texture == 2) {
			glActiveTexture(GL_TEXTURE2);
		}
		if (texture == 3) {
			glActiveTexture(GL_TEXTURE3);
		}
		if (texture == 4) {
			glActiveTexture(GL_TEXTURE4);
		}

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		Shader.loadAll();
		
		Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f);
		Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BG.setUniform1i("tex", texture);
		
		Shader.DEF.setUniformMat4f("pr_matrix", pr_matrix);
		
		float[] vertices = new float[] {
			-10.0f, -10.0f * 9.0f / 16.0f, 0.0f,
			-10.0f,  10.0f * 9.0f / 16.0f, 0.0f,
			 10.0f,  10.0f * 9.0f / 16.0f, 0.0f,
			 10.0f, -10.0f * 9.0f / 16.0f, 0.0f
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
		
		bg = new VertexArray(vertices, indices, tcs);
		bgdef = new Texture("res/bg.jpeg");
		world = new Texture("res/world.jpeg");
		tcat = new Texture("res/tabbycat.jpg");
		ground = new Texture("res/ground.png");
	}
	
	public void update() {
		if (Input.isKeyDown(KEY_1)) {
			texture = 1;
		}
		if (Input.isKeyDown(KEY_2)) {
			texture = 2;
		}
		if (Input.isKeyDown(KEY_3)) {
			texture = 3;
		}
		if (Input.isKeyDown(KEY_4)) {
			texture = 4;
		}
		if (Input.isKeyDown(KEY_A) && Input.isKeyDown(KEY_R)) {
			amount = 2.0f;
		}
		if (Input.isKeyDown(KEY_N)) {
			ds = new DefShape();
			isInit = true;
		}
		if (Input.isKeyDown(KEY_L)) {
			isFail = true;
		}
	}
	
	public void showPanel() {
        try {
			UIManager.setLookAndFeel(
			UIManager.getSystemLookAndFeelClassName());
			JLabel controls = new JLabel("<html>Controls:<br><br>Use the slider to brighten the image and A + R to reset brightness"
					+ ".<br>Use the 1, 2, 3 and 4 keys, to select a image.<br>"
					+ "Remember to create a class <i>ONLY </i> in the package 'com.mehro.lwjeproject', and call your methods inside the"
					+ " 'com.mehro.lwjeproject.MehtodCaller'." + "</html>");
			JSlider lightslider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) amount);  
			lightslider.addChangeListener(this);
			lightslider.setMajorTickSpacing(10);
			lightslider.setMinorTickSpacing(5);
			lightslider.setPaintTicks(true);
			lightslider.setPaintLabels(true); 
			JPanel panel = new JPanel(); 
			panel.add(controls);
			panel.add(lightslider);
			mlwje.add(panel);
			mlwje.setSize(800, 600);  
			mlwje.setLocationRelativeTo(null);  
			mlwje.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
			mlwje.setVisible(true);  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isGameOver() {
		if (isFail) {
			return true;
		} else {
			return false;
		}
	}
	
	public void render() {
		if (texture == 1) bgdef.bind();
		if (texture == 2) world.bind();
		if (texture == 3) tcat.bind();
		if (texture == 4) ground.bind();
		Shader.BG.enable();
		bg.bind();
		Shader.BG.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(0.0f, 0.0f, 0.0f)));
		Shader.BG.setUniform1f("amount", amount);
		Shader.BG.setUniform2f("cursor", Mouse.getDX(), Mouse.getDY());
		bg.draw();
		bg.unbind();
		Shader.BG.disable();
		if (texture == 1) bgdef.unbind();
		if (texture == 2) world.unbind();
		if (texture == 3) tcat.unbind();
		if (texture == 4) ground.unbind();
		if (isInit == true) ds.render();
	}

	public void stateChanged(ChangeEvent e) {
		amount++;
	}
	
	public void dispose() {
	}
	
}
