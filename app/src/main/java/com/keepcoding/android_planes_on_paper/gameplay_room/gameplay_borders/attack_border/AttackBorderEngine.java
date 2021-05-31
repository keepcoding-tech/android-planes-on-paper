package com.keepcoding.android_planes_on_paper.gameplay_room.gameplay_borders.attack_border;

import android.content.Context;
import com.keepcoding.android_planes_on_paper.gameplay_room.gameplay_borders.attack_border.border_grid.AttackPlanesGrid;

public class AttackBorderEngine {
	private static AttackBorderEngine instance;
	private AttackPlanesGrid attackPlanesGrid = null;
	private int selectedPosX = -1;
	private int selectedPosY = -1;

	// constructors
	public AttackBorderEngine() { }

	public static AttackBorderEngine getInstance() {
		if (instance == null) {
			instance = new AttackBorderEngine();
		}

		return instance;
	}

	// setters
	public void initializeGrid(Context context, int[][] attackGrid) {
		attackPlanesGrid = new AttackPlanesGrid(context);
		attackPlanesGrid.setAttackPlanesGrid(attackGrid);
	}

	public void setAttackPlanesGrid(int[][] attackGrid) {
		attackPlanesGrid.setAttackPlanesGrid(attackGrid);
	}

	public void setCellValue(int x, int y) {
		attackPlanesGrid.setCellValue(x, y);
	}

	public void setSelectedPosX(int x) {
		selectedPosX = x;
	}

	public void setSelectedPosY(int y) {
		selectedPosY = y;
	}

	// getters
	public AttackPlanesGrid getAttackPlanesGrid() {
		return attackPlanesGrid;
	}

	public int getSelectedPosX() {
		return selectedPosX;
	}

	public int getSelectedPosY() {
		return selectedPosY;
	}
}
