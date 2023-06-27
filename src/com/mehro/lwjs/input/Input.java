package com.mehro.lwjs.input;

import org.lwjgl.input.Keyboard;

public class Input {

	public static boolean isKeyDown(int key) {
		return Keyboard.isKeyDown(key);
	}
	
	public static boolean isKeyHeld(int key) {
		return Keyboard.isKeyDown(key) == Keyboard.isRepeatEvent();
	}
	
}
