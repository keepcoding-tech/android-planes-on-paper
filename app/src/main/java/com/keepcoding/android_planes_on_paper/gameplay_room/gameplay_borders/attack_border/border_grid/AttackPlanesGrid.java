package com.keepcoding.android_planes_on_paper.gameplay_room.gameplay_borders.attack_border.border_grid;

import com.keepcoding.android_planes_on_paper.gameplay_room.gameplay_borders.attack_border.border_cell.AttackBorderCell;

import android.content.Context;

public class AttackPlanesGrid {
	private AttackBorderCell[][] AttackBorder = new AttackBorderCell[10][10];

	// attributing the right context to every cell of the border
	public AttackPlanesGrid(Context context) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				AttackBorder[i][j] = new AttackBorderCell(context);
			}
		}
	}

	// setters
	// set the right value of each cell
	public void setAttackPlanesGrid(int[][] attackPlanesBorder) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				AttackBorder[i][j].setValue(attackPlanesBorder[i][j]);
			}
		}
	}

	public void setCellValue(int x, int y) {
		AttackBorder[x][y].setValue(AttackBorder[x][y].getValue() == 0 ? 3 : 4);
	}

	// getters
	public AttackBorderCell[][] getAttackPlanesGrid() {
		return AttackBorder;
	}

	public AttackBorderCell getAttackPlanesItem(int x, int y) {
		return AttackBorder[x][y];
	}

	public AttackBorderCell getAttackPlanesItem(int position) {
		int x = position / 10;
		int y = position % 10;

		return AttackBorder[x][y];
	}
}
