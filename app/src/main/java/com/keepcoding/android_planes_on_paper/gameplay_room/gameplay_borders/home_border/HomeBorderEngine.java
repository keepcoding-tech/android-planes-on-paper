package com.keepcoding.android_planes_on_paper.gameplay_room.gameplay_borders.home_border;

import com.keepcoding.android_planes_on_paper.gameplay_room.gameplay_borders.home_border.border_grid.HomePlanesGrid;

import android.content.Context;

public class HomeBorderEngine {
	private static HomeBorderEngine instance;
	private HomePlanesGrid homePlanesGrid = null;

	// constructors
	public HomeBorderEngine() {}

	public static HomeBorderEngine getInstance() {
		if (instance == null) {
			instance = new HomeBorderEngine();
		}

		return instance;
	}

	// setters
	public void initializeGrid(Context context, int[][] homeGrid) {
		homePlanesGrid = new HomePlanesGrid(context);
		homePlanesGrid.setHomePlanesGrid(homeGrid);
	}

	public void setHomePlanesGrid(int[][] homeGrid) {
		homePlanesGrid.setHomePlanesGrid(homeGrid);
	}

	// getters
	public HomePlanesGrid getHomePlanesGrid() {
		return homePlanesGrid;
	}
}
