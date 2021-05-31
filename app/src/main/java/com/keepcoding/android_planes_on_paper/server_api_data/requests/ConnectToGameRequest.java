package com.keepcoding.android_planes_on_paper.server_api_data.requests;

public class ConnectToGameRequest {
	private String playerNickname;
	private String accessToken;

	// constructors
	public ConnectToGameRequest() {}

	public ConnectToGameRequest(String playerNickname, String accessToken) {
		this.playerNickname = playerNickname;
		this.accessToken = accessToken;
	}

	// setters
	public void setPlayerNickname(String playerNickname) {
		this.playerNickname = playerNickname;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	// getters
	public String getPlayerNickname() {
		return playerNickname;
	}

	public String getAccessToken() {
		return accessToken;
	}
}
