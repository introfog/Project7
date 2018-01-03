package com.introfog;

import static java.awt.Toolkit.getDefaultToolkit;


public abstract class GameSystem{
	public static final String NAME_JAR_ARCHIVE = "project7.jar";
	public static final boolean FULL_SCREEN = true;
	//public static final boolean FULL_SCREEN = false;
	public static final float SCREEN_W = getDefaultToolkit ().getScreenSize ().width;
	public static final float SCREEN_H = getDefaultToolkit ().getScreenSize ().height;
	//public static final float SCREEN_W = 1280;
	//public static final float SCREEN_H = 1024;
	public static float INDENT_BETWEEN_SCREEN_LEVEL = 0;
	
	public static boolean GAME_OVER = false;
	public static boolean IS_FIRST_GAME_START = true;
	public static int NUM_PASSED_LEVELS = 0;
	public static int CURRENT_LEVEL = 0;
	public static int NUM_LEVELS = 0;
}