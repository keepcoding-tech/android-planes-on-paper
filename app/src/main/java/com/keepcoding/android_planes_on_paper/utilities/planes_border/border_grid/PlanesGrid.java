package com.keepcoding.android_planes_on_paper.utilities.planes_border.border_grid;

import com.keepcoding.android_planes_on_paper.utilities.planes_border.border_cell.BorderCell;

import android.content.Context;

public class PlanesGrid {
	private BorderCell[][] PlanesBorder = new BorderCell[10][10];

	public PlanesGrid(Context context) {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				PlanesBorder[x][y] = new BorderCell(context);
			}
		}
	}

	public void setGrid(int[][] grid) {
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 10; y++) {
				PlanesBorder[x][y].setValue(grid[x][y]);
			}
		}
	}

	public BorderCell[][] getGrid() {
		return PlanesBorder;
	}

	public BorderCell getItem(int x, int y) {
		return PlanesBorder[x][y];
	}

	public BorderCell getItem(int position) {
		int x = position / 10;
		int y = position % 10;

		return PlanesBorder[x][y];
	}

	public void setItem(int posX, int posY) {
		if (PlanesBorder[posX][posY].getValue() == 0) {
			PlanesBorder[posX][posY].setValue(1);
		} else if (PlanesBorder[posX][posY].getValue() == 1) {
			PlanesBorder[posX][posY].setValue(2);
		} else {
			PlanesBorder[posX][posY].setValue(0);
		}
	}
}
