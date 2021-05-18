package com.keepcoding.android_planes_on_paper.utilities.gameplay_models;

public class GameplayModel {
	private Long gameID;
	private GameplayStatus gameplayStatus;
	private GameplayPlayer playerOne;
	private GameplayPlayer playerTwo;

	// constructors
	public GameplayModel() {}

	public GameplayModel(GameplayStatus gameplayStatus, GameplayPlayer playerOne) {
		this.gameplayStatus = gameplayStatus;
		this.playerOne = playerOne;
	}

	public GameplayModel(Long gameID, GameplayStatus gameplayStatus, GameplayPlayer playerOne) {
		this.gameID = gameID;
		this.gameplayStatus = gameplayStatus;
		this.playerOne = playerOne;
	}

	public GameplayModel(
			Long gameID,
			GameplayStatus gameplayStatus,
			GameplayPlayer playerOne,
			GameplayPlayer playerTwo
	) {
		this.gameID = gameID;
		this.gameplayStatus = gameplayStatus;
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}

	// toString
	@Override
	public String toString() {
		return playerOne.getPlayerNickname() + "  VS  " + playerTwo.getPlayerNickname();
	}

	// setters
	public void setGameID(Long gameID) {
		this.gameID = gameID;
	}

	public void setGameplayStatus(GameplayStatus gamePlayStatus) {
		this.gameplayStatus = gamePlayStatus;
	}

	public void setPlayerOne(GameplayPlayer playerOne) {
		this.playerOne = playerOne;
	}

	public void setPlayerTwo(GameplayPlayer playerTwo) {
		this.playerTwo = playerTwo;
	}

	// getters
	public Long getGameID() {
		return gameID;
	}

	public GameplayStatus getGameplayStatus() {
		return gameplayStatus;
	}

	public GameplayPlayer getPlayerOne() {
		return playerOne;
	}

	public GameplayPlayer getPlayerTwo() {
		return playerTwo;
	}
}
