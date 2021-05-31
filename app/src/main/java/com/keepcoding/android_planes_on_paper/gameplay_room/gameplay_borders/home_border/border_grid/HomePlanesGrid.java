package com.keepcoding.android_planes_on_paper.gameplay_room.gameplay_borders.home_border.border_grid;

import com.keepcoding.android_planes_on_paper.gameplay_room.gameplay_borders.home_border.border_cell.HomeBorderCell;

import android.content.Context;

public class HomePlanesGrid {
	private HomeBorderCell[][] HomeBorder = new HomeBorderCell[10][10];

	// attributing the right context to every cell of the border
	public HomePlanesGrid(Context context) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				HomeBorder[i][j] = new HomeBorderCell(context);
			}
		}
	}

	// setters
	// set the right value of each cell
	public void setHomePlanesGrid(int[][] homePlanesGrid) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				HomeBorder[i][j].setValue(homePlanesGrid[i][j]);
			}
		}
	}

	// getters
	public HomeBorderCell getHomeBorderItem(int position) {
		int x = position / 10;
		int y = position % 10;

		return HomeBorder[x][y];
	}
}
