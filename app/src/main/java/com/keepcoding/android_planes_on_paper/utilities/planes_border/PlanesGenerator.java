package com.keepcoding.android_planes_on_paper.utilities.planes_border;

public class PlanesGenerator {
	private static PlanesGenerator instance;

	public PlanesGenerator() {}

	public static PlanesGenerator getInstance() {
		if (instance == null) {
			instance = new PlanesGenerator();
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
