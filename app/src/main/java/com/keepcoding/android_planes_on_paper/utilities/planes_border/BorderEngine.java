package com.keepcoding.android_planes_on_paper.utilities.planes_border;

import com.keepcoding.android_planes_on_paper.utilities.planes_border.border_grid.PlanesGrid;

import android.content.Context;

public class BorderEngine {
	private static BorderEngine instance;
	private PlanesGrid grid = null;

	// constructors
	public BorderEngine() {}

	public static BorderEngine getInstance() {
		if (instance == null) {
			instance = new BorderEngine();
		}
		return instance;
	}

	public void createGrid(Context context) {
		int[][] PlanesBorder = PlanesGenerator.getInstance().generateGrid();
		grid = new PlanesGrid(context);
		grid.setGrid(PlanesBorder);
	}

	public void setNumber(int posX, int posY) {
		grid.setItem(posX, posY);
	}

	public PlanesGrid getGrid() {
		return grid;
	}

	public void printBorder() {
		System.out.println("{");
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.print("[" + grid.getItem(i, j).getValue() + "]");
			}
			System.out.println();
		}
		System.out.println("}");
	}
}
