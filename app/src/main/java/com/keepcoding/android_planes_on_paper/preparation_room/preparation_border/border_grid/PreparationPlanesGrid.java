package com.keepcoding.android_planes_on_paper.preparation_room.preparation_border.border_grid;

import com.keepcoding.android_planes_on_paper.preparation_room.preparation_border.border_cell.PreparationBorderCell;

import android.content.Context;

public class PreparationPlanesGrid {
	private PreparationBorderCell[][] PlanesBorder = new PreparationBorderCell[10][10];

	// attributing the right context to every cell of the border
	public PreparationPlanesGrid(Context context) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				PlanesBorder[i][j] = new PreparationBorderCell(context);
			}
		}
	}

	// setters
	// set the right value of each cell
	public void setGrid(int[][] grid) {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				PlanesBorder[i][j].setValue(grid[i][j]);
			}
		}
	}

	// cycle the values between 0, 1 and 2
	public void setItem(int x, int y) {
		if (PlanesBorder[x][y].getValue() == 0) {
			PlanesBorder[x][y].setValue(1);
		} else if (PlanesBorder[x][y].getValue() == 1) {
			PlanesBorder[x][y].setValue(2);
		} else {
			PlanesBorder[x][y].setValue(0);
		}
	}

	// getters
	public PreparationBorderCell[][] getGrid() {
		return PlanesBorder;
	}

	public PreparationBorderCell getItem(int x, int y) {
		return PlanesBorder[x][y];
	}

	public PreparationBorderCell getItem(int position) {
		int x = position / 10;
		int y = position % 10;

		return PlanesBorder[x][y];
	}
}
