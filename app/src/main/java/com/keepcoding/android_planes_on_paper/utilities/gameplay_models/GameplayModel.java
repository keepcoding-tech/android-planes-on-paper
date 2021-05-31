package com.keepcoding.android_planes_on_paper.utilities.gameplay_models;

public class GameplayModel {
	private String gameID;
	private GameplayStatus gameStatus;
	private PlayerStatus playerStatus;
	private String accessToken;
	private PlayerModel playerOne;
	private PlayerModel playerTwo;

	// constructors
	public GameplayModel() {}

	public GameplayModel(
			GameplayStatus gameStatus,
			PlayerStatus playerStatus,
			PlayerModel playerOne,
			PlayerModel playerTwo
	) {
		this.gameStatus = gameStatus;
		this.playerStatus = playerStatus;
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}

	public GameplayModel(
			GameplayStatus gameStatus,
			PlayerStatus playerStatus,
			String accessToken,
			PlayerModel playerOne,
			PlayerModel playerTwo
	) {
		this.gameStatus = gameStatus;
		this.accessToken = accessToken;
		this.playerStatus = playerStatus;
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}

	// setters
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setGameStatus(GameplayStatus gamePlayStatus) {
		this.gameStatus = gamePlayStatus;
	}

	public void setPlayerStatus(PlayerStatus playerStatus) {
		this.playerStatus = playerStatus;
	}

	public void setPlayerOne(PlayerModel playerOne) {
		this.playerOne = playerOne;
	}

	public void setPlayerTwo(PlayerModel playerTwo) {
		this.playerTwo = playerTwo;
	}

	// getters
	public String getGameID() {
		return gameID;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public GameplayStatus getGameStatus() {
		return gameStatus;
	}

	public PlayerStatus getPlayerStatus() {
		return playerStatus;
	}

	public PlayerModel getPlayerOne() {
		return playerOne;
	}

	public PlayerModel getPlayerTwo() {
		return playerTwo;
	}
}
