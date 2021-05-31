package com.keepcoding.android_planes_on_paper.server_api_data.requests;

import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.PlayerStatus;

public class SetSurrenderedRequest {
	private String gameID;
	private PlayerStatus identity;

	// constructors
	public SetSurrenderedRequest() {}

	public SetSurrenderedRequest(String gameID, PlayerStatus identity) {
		this.gameID = gameID;
		this.identity = identity;
	}

	// setters
	public void setGameID(String gameID) {
		this.gameID = gameID;
	}

	public void setIdentity(PlayerStatus identity) {
		this.identity = identity;
	}

	// getters
	public String getGameID() {
		return gameID;
	}

	public PlayerStatus getIdentity() {
		return identity;
	}
}
