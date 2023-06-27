package com.mehro.lwjs;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.mehro.lwjs.level.Level;

public class Main implements Runnable {

	private int width = 1280;
	private int height = 720;
	
	private String title = "Lightweight Java Engine For Java 17 And LWJGL Developers";
	
	private Thread thread;
	private boolean running = false;
	
	private Level level;
	
	public void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}
	
	private void init() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle("Loading LWJE..");
			Display.setResizable(true);
			Display.setVSyncEnabled(true);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		System.out.println("OpenGL version: " + glGetString(GL_VERSION));
		
		glClearColor(Level.color.x, Level.color.y, Level.color.z, 1.0f);
		glEnable(GL_DEPTH_TEST);
		
		level = new Level();
		level.showPanel();
	}
	
	public void run() {
		init();
		long lastTime = System.nanoTime();
		double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		long timer = System.currentTimeMillis();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				Display.setTitle(title + " | " + updates + " ups, " + frames + " fps");
				frames = 0;
				updates = 0;
			}
			if (Display.isCloseRequested())
				running = false;
		}
		
		glDisable(GL_LIGHTING);
		level.dispose();
		Display.destroy();
		System.exit(0);
	}
	
	private void update() {
		if (Display.wasResized())
			glViewport(0, 0, Display.getWidth(), Display.getHeight());
		Display.update();
		Display.sync(60);
		level.update();
		if (level.isGameOver())
			level = new Level();
	}
	
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		level.render();
	}
	
	public static void main(String[] args) {
		new Main().start();
	}
	
}
