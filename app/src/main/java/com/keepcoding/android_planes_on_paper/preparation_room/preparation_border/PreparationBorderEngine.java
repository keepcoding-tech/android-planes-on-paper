package com.keepcoding.android_planes_on_paper.preparation_room.preparation_border;

import com.keepcoding.android_planes_on_paper.preparation_room.preparation_border.border_grid.PreparationPlanesGrid;

import android.content.Context;

public class PreparationBorderEngine {
	private static PreparationBorderEngine instance;
	private PreparationPlanesGrid grid = null;

	// constructors
	public PreparationBorderEngine() {}

	public static PreparationBorderEngine getInstance() {
		if (instance == null) {
			instance = new PreparationBorderEngine();
		}
		return instance;
	}

	// setters
	public void createGrid(Context context) {
		int[][] PlanesBorder = PreparationPlanesGenerator.getInstance().generateGrid();
		grid = new PreparationPlanesGrid(context);
		grid.setGrid(PlanesBorder);
	}

	public void setNumber(int x, int y) {
		grid.setItem(x, y);
	}

	// getters
	public PreparationPlanesGrid getGrid() {
		return grid;
	}
}
