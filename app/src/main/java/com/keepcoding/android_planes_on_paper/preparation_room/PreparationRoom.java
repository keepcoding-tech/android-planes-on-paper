package com.keepcoding.android_planes_on_paper.preparation_room;

import com.keepcoding.android_planes_on_paper.gameplay_room.GameplayRoom;
import com.keepcoding.android_planes_on_paper.server_api_data.GameplayRoomApiData;
import com.keepcoding.android_planes_on_paper.server_api_data.PreparationRoomApiData;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.GameplayModel;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.PlayerModel;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.GameplayStatus;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.PlayerStatus;
import com.keepcoding.android_planes_on_paper.preparation_room.preparation_border.PreparationBorderEngine;
import com.keepcoding.android_planes_on_paper.preparation_room.preparation_border.border_grid.PreparationPlanesGrid;
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
	private final GameplayRoomApiData gameplayRoomApiData = new GameplayRoomApiData();

	// declaring preparation room data
	private String gameID;
	private PlayerStatus identity;
	private String playerOneNickname;
	private String playerTwoNickname;
	private int[][] homeBorder = new int[10][10];

	private AlertDialog.Builder leaveAlertDialog;
	private AlertDialog dismissAlertDialog;
	private Timer searchForPlayerTimer = new Timer();
	private Timer checkForUpdatesTimer = new Timer();

	// initializing scheduled task
	private final TimerTask checkForUpdatesTask = new TimerTask() {
		@Override
		public void run() {
			checkForUpdates();
		}
	};

	// declaring layout components
	private TextView txt_playerOneNickname;
	private TextView txt_playerTwoNickname;
	private Button btn_playerIsReady;
	private Button btn_playerLeftGame;

	// declaring sending preparation room data
	private static final String GAME_ID = "com.keepcoding.gameID";
	private static final String IDENTITY = "com.keepcoding.identity";
	private static final String PLAYER_ONE_NICKNAME = "com.keepcoding.playerOneNickname";
	private static final String PLAYER_TWO_NICKNAME = "com.keepcoding.playerTwoNickname";
	private static final String HOME_BORDER = "com.keepcoding.homeBorder";
	private static final String ATTACK_BORDER = "com.keepcoding.attackBorder";

	// cancel timer when leaving
	@Override
	protected void onDestroy() {
		super.onDestroy();
		checkForUpdatesTimer.cancel();
		searchForPlayerTimer.cancel();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preparation_room);

		// create planes border grid
		PreparationBorderEngine.getInstance().createGrid(PreparationRoom.this);

		// initializing layout components
		txt_playerOneNickname = findViewById(R.id.txt_playerOneNickname);
		txt_playerTwoNickname = findViewById(R.id.txt_playerTwoNickname);
		btn_playerIsReady = findViewById(R.id.btn_playerIsReady);
		btn_playerLeftGame = findViewById(R.id.btn_playerLeftGame);

		// initializing preparation room data
		final Intent dataIntent = getIntent();

		gameID = dataIntent.getStringExtra(GAME_ID);
		identity = (PlayerStatus) dataIntent.getSerializableExtra(IDENTITY);
		playerOneNickname = dataIntent.getStringExtra(PLAYER_ONE_NICKNAME);
		playerTwoNickname = dataIntent.getStringExtra(PLAYER_TWO_NICKNAME);

		txt_playerOneNickname.setText(playerOneNickname);
		txt_playerTwoNickname.setText(playerTwoNickname);

		// initializing alert dialog
		leaveAlertDialog = new AlertDialog.Builder(PreparationRoom.this);
		leaveAlertDialog.setCancelable(false);
		leaveAlertDialog.setTitle("searching for players. . .");
		leaveAlertDialog.setPositiveButton("LEAVE", (dialog, which) -> {
			setSurrendered();
			deleteGameplayRoom();
		});

		dismissAlertDialog = leaveAlertDialog.create();
		dismissAlertDialog.show();

		// initializing on click listener to all buttons
		btn_playerIsReady.setOnClickListener(view -> setReady());

		btn_playerLeftGame.setOnClickListener(view -> {
			AlertDialog.Builder leaveTheRoomDialog = new AlertDialog.Builder(PreparationRoom.this);
			leaveTheRoomDialog.setTitle("are you sure?");
			leaveTheRoomDialog.setPositiveButton("LEAVE", (dialog, which) -> setSurrendered());
			leaveTheRoomDialog.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
			leaveTheRoomDialog.create().show();
		});

		// initializing scheduled task to search for player
		TimerTask searchForPlayerTask = new TimerTask() {
			@Override
			public void run() {
				searchForSecondPlayer();
			}
		};
		searchForPlayerTimer.scheduleAtFixedRate(searchForPlayerTask, 0, 5000);
	}

	// delete gameplay if player leave the room
	// before the second players is connected
	private void deleteGameplayRoom() {
		preparationRoomApiData.deletePreparationRoom(gameID);
	}

	// change "isReady" player status and initialize "planesBorder"
	private void setReady() {
		homeBorder = new int[10][10];
		PreparationPlanesGrid preparationPlanesGrid = PreparationBorderEngine.getInstance().getGrid();
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 10; y++) {
				homeBorder[x][y] = preparationPlanesGrid.getItem(x, y).getValue();
			}
		}

		// notify the player if the planes border is valid or not
		preparationRoomApiData.setReady(gameID, identity, homeBorder, new PreparationRoomApiData.SendingDataResponseListener() {
			@Override
			public void onResponse(String message) {
				Toast.makeText(PreparationRoom.this, message, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(String message) {
				Toast.makeText(PreparationRoom.this, message, Toast.LENGTH_SHORT).show();
			}
		});
	}

	// change the status to not connected in the API and return to the MainMenu screen
	private void setSurrendered() {
		gameplayRoomApiData.setSurrendered(gameID, identity);
		NavUtils.navigateUpFromSameTask(this);
		checkForUpdatesTimer.cancel();
	}

	// if the second player is connected, cancel alert dialog and get playerTwo nickname
	private void searchForSecondPlayer() {
		gameplayRoomApiData.getCurrentGameplayRoomData(gameID, new GameplayRoomApiData.GameplayRoomResponseListener() {
			@Override
			public void onResponse(GameplayModel apiGameplayModel) {
				if (apiGameplayModel.getPlayerTwo() != null) {
					playerTwoNickname = apiGameplayModel.getPlayerTwo().getPlayerNickname();
					txt_playerTwoNickname.setText(playerTwoNickname);
					dismissAlertDialog.dismiss();

					searchForPlayerTimer.cancel();
					checkForUpdatesTimer.scheduleAtFixedRate(checkForUpdatesTask, 0, 5000);
				}
			}

			@Override
			public void onFailure(String message) {
				Toast.makeText(PreparationRoom.this, message, Toast.LENGTH_SHORT).show();
			}
		});
	}

	// ask the API for any data changes
	private void checkForUpdates() {
		gameplayRoomApiData.getCurrentGameplayRoomData(gameID, new GameplayRoomApiData.GameplayRoomResponseListener() {
			@Override
			public void onResponse(GameplayModel apiGameplayModel) {
				final PlayerModel playerOne = apiGameplayModel.getPlayerOne();
				final PlayerModel playerTwo = apiGameplayModel.getPlayerTwo();
				final GameplayStatus inProgress = apiGameplayModel.getGameStatus();

				// check if the other player left the room and notify the player
				final boolean hasSurrendered = identity.equals(PlayerStatus.PLAYER_ONE) ?
						playerTwo.getHasSurrendered() : playerOne.getHasSurrendered();
				checkPlayerConnectivity(hasSurrendered);

				// if both players are ready to start the game, go to GameplayRoom activity
				final int[][] attackBorder = identity.equals(PlayerStatus.PLAYER_ONE) ?
						playerTwo.getPlanesBorder() : playerOne.getPlanesBorder();
				readyForGame(inProgress, attackBorder);
			}

			@Override
			public void onFailure(String message) {
				Toast.makeText(PreparationRoom.this, message, Toast.LENGTH_SHORT).show();
			}
		});
	}

	// check if the other player left the room and notify the player
	private void checkPlayerConnectivity(boolean hasSurrendered) {
		if (hasSurrendered) {
			leaveAlertDialog.setTitle("the other player left the room");
			leaveAlertDialog.create().show();
		}
	}

	// if both players are ready to start the game, go to GameplayRoom activity
	private void readyForGame(GameplayStatus inProgress, int[][] attackBorder) {
		final Intent gameplayIntent = new Intent(PreparationRoom.this, GameplayRoom.class);

		if (inProgress == GameplayStatus.IN_PROGRESS) {
			gameplayIntent.putExtra(GAME_ID, gameID);
			gameplayIntent.putExtra(IDENTITY, identity);
			gameplayIntent.putExtra(PLAYER_ONE_NICKNAME, playerOneNickname);
			gameplayIntent.putExtra(PLAYER_TWO_NICKNAME, playerTwoNickname);
			gameplayIntent.putExtra(HOME_BORDER, homeBorder);
			gameplayIntent.putExtra(ATTACK_BORDER, attackBorder);

			startActivity(gameplayIntent);

			checkForUpdatesTimer.cancel();
		}
	}
}