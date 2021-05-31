package com.keepcoding.android_planes_on_paper.preparation_room.preparation_border;

public class PreparationPlanesGenerator {
	private static PreparationPlanesGenerator instance;

	public PreparationPlanesGenerator() {}

	public static PreparationPlanesGenerator getInstance() {
		if (instance == null) {
			instance = new PreparationPlanesGenerator();
		}
		return instance;
	}

	public int[][] generateGrid() {
		return new int[][]{
				{0,0,2,0,0,0,0,0,0,0},
				{1,1,1,1,1,0,0,0,1,0},
				{0,0,1,0,0,0,1,0,1,0},
				{0,1,1,1,0,0,1,1,1,2},
				{0,0,0,0,0,0,1,0,1,0},
				{0,0,0,0,1,0,0,0,1,0},
				{0,0,0,0,1,0,1,0,0,0},
				{0,0,0,2,1,1,1,0,0,0},
				{0,0,0,0,1,0,1,0,0,0},
				{0,0,0,0,1,0,0,0,0,0},
		};
	}
}
