package com.keepcoding.android_planes_on_paper.utilities.gameplay_models;

public class GameplayPlayer {
	private String playerNickname;
	private boolean isConnected;
	private boolean hasSurrendered;
	private boolean isReady;
	private int destroyedPlanes;
	private int[][] planesBorder;

	// constructors
	public GameplayPlayer() {}

	public GameplayPlayer(
			String playerNickname,
			boolean isConnected,
			boolean hasSurrendered,
			boolean isReady,
			int destroyedPlanes,
			int[][] planesBorder
	) {
		this.playerNickname = playerNickname;
		this.isConnected = isConnected;
		this.hasSurrendered = hasSurrendered;
		this.isReady = isReady;
		this.destroyedPlanes = destroyedPlanes;
		this.planesBorder = planesBorder;
	}

	// setters
	public void setPlayerNickname(String playerNickname) {
		this.playerNickname = playerNickname;
	}

	public void setIsConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	public void setHasSurrendered(boolean isConnected) {
		this.hasSurrendered = isConnected;
	}

	public void setReady(boolean ready) {
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

	public boolean getIsConnected() {
		return isConnected;
	}

	public boolean getHasSurrendered() {
		return hasSurrendered;
	}

	public boolean isReady() {
		return isReady;
	}

	public int getDestroyedPlanes() {
		return destroyedPlanes;
	}

	public int[][] getPlanesBorder() {
		return planesBorder;
	}
}
