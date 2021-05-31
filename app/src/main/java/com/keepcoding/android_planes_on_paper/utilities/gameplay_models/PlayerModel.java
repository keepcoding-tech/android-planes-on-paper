package com.keepcoding.android_planes_on_paper.utilities.gameplay_models;

public class PlayerModel {
	private String playerNickname;
	private boolean hasSurrendered;
	private boolean isReady;
	private int destroyedPlanes;
	private int[][] planesBorder;

	// constructors
	public PlayerModel() {}

	public PlayerModel(
			String playerNickname,
			boolean hasSurrendered,
			boolean isReady,
			int destroyedPlanes,
			int[][] planesBorder
	) {
		this.playerNickname = playerNickname;
		this.hasSurrendered = hasSurrendered;
		this.isReady = isReady;
		this.destroyedPlanes = destroyedPlanes;
		this.planesBorder = planesBorder;
	}

	// setters
	public void setPlayerNickname(String playerNickname) {
		this.playerNickname = playerNickname;
	}

	public void setHasSurrendered(boolean isConnected) {
		this.hasSurrendered = isConnected;
	}

	public void setIsReady(boolean ready) {
		isReady = ready;
	}

	public void setDestroyedPlanes(int destroyedPlanes) {
		this.destroyedPlanes = destroyedPlanes;
	}

	public void setPlanesBorder(int[][] planesBorder) {
		this.planesBorder = planesBorder;
	}

	// getters
	public String getPlayerNickname() {
		return playerNickname;
	}

	public boolean getHasSurrendered() {
		return hasSurrendered;
	}

	public boolean getIsReady() {
		return isReady;
	}

	public int getDestroyedPlanes() {
		return destroyedPlanes;
	}

	public int[][] getPlanesBorder() {
		return planesBorder;
	}
}
