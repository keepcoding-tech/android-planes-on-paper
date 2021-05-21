package com.keepcoding.android_planes_on_paper.preparation_room;

import com.keepcoding.android_planes_on_paper.MainMenu;
import com.keepcoding.android_planes_on_paper.gameplay_room.GameplayRoom;
import com.keepcoding.android_planes_on_paper.preparation_room.api_data.PreparationRoomApiData;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.GameplayModel;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.GameplayPlayer;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.GameplayStatus;
import com.keepcoding.android_planes_on_paper.utilities.planes_border.BorderEngine;
import com.keepcoding.android_planes_on_paper.utilities.planes_border.border_grid.PlanesGrid;
import com.keepcoding.android_planes_on_paper.R;

import android.app.AlertDialog;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NavUtils;

import android.app.Activity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class PreparationRoom extends Activity {
	// declaring instances
	private final PreparationRoomApiData preparationRoomApiData = new PreparationRoomApiData();

	// declaring preparation room data
	private Long gameID;
	private String player;
	private String playerOneNickname;
	private String playerTwoNickname;
	private AlertDialog.Builder alertDialog;
	private AlertDialog dismissAlertDialog;
	private Timer timer = new Timer();

	// declaring layout components
	private TextView txt_playerOneNickname;
	private TextView txt_playerTwoNickname;
	private Button btn_playerIsReady;
	private Button btn_playerLeftGame;

	// declaring sending preparation room data
	public static final String GAME_ID = "com.keepcoding.gameID";
	public static final String PLAYER = "com.keepcoding.player";
	public static final String PLAYER_ONE_NICKNAME = "com.keepcoding.playerOneNickname";
	public static final String PLAYER_TWO_NICKNAME = "com.keepcoding.playerTwoNickname";

	// cancel timer when leaving
	@Override
	protected void onDestroy() {
		super.onDestroy();
		timer.cancel();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preparation_room);

		// create planes border grid
		BorderEngine.getInstance().createGrid(PreparationRoom.this);

		// initializing layout components
		txt_playerOneNickname = findViewById(R.id.txt_playerOneNickname);
		txt_playerTwoNickname = findViewById(R.id.txt_playerTwoNickname);
		btn_playerIsReady = findViewById(R.id.btn_playerIsReady);
		btn_playerLeftGame = findViewById(R.id.btn_playerLeftGame);

		// initializing preparation room data
		final Intent dataIntent = getIntent();

		gameID = dataIntent.getLongExtra(MainMenu.GAME_ID, 0);
		player = dataIntent.getStringExtra(MainMenu.PLAYER);
		playerOneNickname = dataIntent.getStringExtra(MainMenu.PLAYER_ONE_NICKNAME);
		playerTwoNickname = dataIntent.getStringExtra(MainMenu.PLAYER_TWO_NICKNAME);

		txt_playerOneNickname.setText(playerOneNickname);
		txt_playerTwoNickname.setText(playerTwoNickname);

		// initializing alert dialog
		alertDialog = new AlertDialog.Builder(PreparationRoom.this);
		alertDialog.setCancelable(false);
		alertDialog.setTitle("searching for players. . .");
		alertDialog.setPositiveButton("LEAVE", (dialog, which) -> {
			playerHasSurrendered();
			deleteGameplay();
		});

		dismissAlertDialog = alertDialog.create();
		dismissAlertDialog.show();

		// initializing on click listener to all buttons
		btn_playerIsReady.setOnClickListener(view -> playerIsReady());

		btn_playerLeftGame.setOnClickListener(view -> {
			AlertDialog.Builder leaveTheRoomDialog = new AlertDialog.Builder(PreparationRoom.this);
			leaveTheRoomDialog.setTitle("are you sure?");
			leaveTheRoomDialog.setPositiveButton("LEAVE", (dialog, which) -> playerHasSurrendered());
			leaveTheRoomDialog.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
			leaveTheRoomDialog.create().show();
		});

		// initializing scheduled task
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				checkForUpdates();
			}
		};

		timer.scheduleAtFixedRate(task, 0, 6000);
	}

	// change the status to not connected in the API
	public void leavePreparationRoom() {
		preparationRoomApiData.leavePreparationRoom(gameID, player);
	}

	// delete gameplay if player leave the room
	// before the second players is connected
	public void deleteGameplay() {
		preparationRoomApiData.deleteGameplay(gameID);
	}

	public void playerIsReady() {
		int[][] planesBorder = new int[10][10];
		PlanesGrid planesGrid = BorderEngine.getInstance().getGrid();
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 10; y++) {
				planesBorder[x][y] = planesGrid.getItem(x, y).getValue();
			}
		}

		preparationRoomApiData.updatePlayerIsReady(gameID, player, planesBorder, new PreparationRoomApiData.SendingDataResponseListener() {
			@Override
			public void onResponse(String message) {
				Toast.makeText(PreparationRoom.this, message, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(String message) {
				Toast.makeText(PreparationRoom.this, message, Toast.LENGTH_SHORT).show();
			}
		});
		BorderEngine.getInstance().printBorder();
	}

	// change the status to not connected and return to the MainMenu screen
	public void playerHasSurrendered() {
		leavePreparationRoom();
		NavUtils.navigateUpFromSameTask(this);
		timer.cancel();
	}

	// ask the API for any data changes
	public void checkForUpdates() {
		preparationRoomApiData.getCurrentPreparationRoomData(gameID, new PreparationRoomApiData.PreparationRoomResponseListener() {
			@Override
			public void onResponse(GameplayModel apiGameModel, String message) {
				final GameplayPlayer playerOne = apiGameModel.getPlayerOne();
				final GameplayPlayer playerTwo = apiGameModel.getPlayerTwo();
				final GameplayStatus inProgress = apiGameModel.getGameplayStatus();

				// if the second player is connected, cancel alert dialog and get playerTwo nickname
				searchForSecondPlayer(playerTwo);

				// check if the other player left the room and notify the player
				checkPlayerConnectivity(playerOne, playerTwo);

				// if both players are ready to start the game, go to GameplayRoom activity
				readyForGame(inProgress);
			}

			@Override
			public void onFailure(String message) {
				Toast.makeText(PreparationRoom.this, message, Toast.LENGTH_SHORT).show();
			}
		});
	}

	// if the second player is connected, cancel alert dialog and get playerTwo nickname
	public void searchForSecondPlayer(GameplayPlayer playerTwo) {
		if (playerTwo.getIsConnected()) {
			dismissAlertDialog.dismiss();
		}
		if (playerTwoNickname.equals("-")) {
			playerTwoNickname = playerTwo.getPlayerNickname();
			txt_playerTwoNickname.setText(playerTwoNickname);
		}
	}

	// check if the other player left the room and notify the player
	public void checkPlayerConnectivity(GameplayPlayer playerOne, GameplayPlayer playerTwo) {
		if (player.equals("playerOne")) {
			if (playerTwo.getHasSurrendered()) {
				alertDialog.setTitle("the other player left the room");
				alertDialog.create().show();
			}
		} else {
			if (playerOne.getHasSurrendered()) {
				alertDialog.setTitle("the other player left the room");
				alertDialog.create().show();
			}
		}
	}

	// if both players are ready to start the game, go to GameplayRoom activity
	public void readyForGame(GameplayStatus inProgress) {
		final Intent gameplayIntent = new Intent(PreparationRoom.this, GameplayRoom.class);

		if (inProgress == GameplayStatus.IN_PROGRESS) {
			gameplayIntent.putExtra(GAME_ID, gameID);
			gameplayIntent.putExtra(PLAYER, player);
			gameplayIntent.putExtra(PLAYER_ONE_NICKNAME, playerOneNickname);
			gameplayIntent.putExtra(PLAYER_TWO_NICKNAME, playerTwoNickname);

			startActivity(gameplayIntent);

			timer.cancel();
		}
	}
}