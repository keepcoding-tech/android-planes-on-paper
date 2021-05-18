package com.keepcoding.android_planes_on_paper.utilities.api_global_req;

public class PlayerHasSurrenderedRequest {
	private Long gameID;
	private String player;

	// constructors
	public PlayerHasSurrenderedRequest() {}

	public PlayerHasSurrenderedRequest(Long gameID, String player) {
		this.gameID = gameID;
		this.player = player;
	}

	// setters
	public void setGameID(Long gameID) {
		this.gameID = gameID;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	// getters
	public Long getGameID() {
		return gameID;
	}

	public String getPlayer() {
		return player;
	}
}
