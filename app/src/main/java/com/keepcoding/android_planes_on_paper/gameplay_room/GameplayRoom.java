package com.keepcoding.android_planes_on_paper.gameplay_room;

import com.keepcoding.android_planes_on_paper.server_api_data.GameplayRoomApiData;
import com.keepcoding.android_planes_on_paper.gameplay_room.gameplay_borders.attack_border.AttackBorderEngine;
import com.keepcoding.android_planes_on_paper.gameplay_room.gameplay_borders.home_border.HomeBorderEngine;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.GameplayStatus;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.PlayerModel;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.GameplayModel;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.PlayerStatus;
import com.keepcoding.android_planes_on_paper.R;

import android.app.AlertDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import androidx.core.app.NavUtils;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class GameplayRoom extends Activity {
	// declaring instances
	private final GameplayRoomApiData gameplayRoomApiData = new GameplayRoomApiData();

	// declaring game data
	private String gameID;
	private PlayerStatus identity;
	private String playerOneNickname;
	private String playerTwoNickname;

	private int playerOneScore = 0;
	private int playerTwoScore = 0;
	private int[][] homeBorder = new int[10][10];
	private int[][] attackBorder = new int[10][10];

	private PlayerStatus playerTurn = PlayerStatus.PLAYER_ONE;

	private PlayerModel playerOne;
	private PlayerModel playerTwo;
	private AlertDialog.Builder surrenderAlertDialog;
	private AlertDialog.Builder leaveRoomAlertDialog;
	private AlertDialog dismissAlertDialog;
	private Timer checkForUpdatesTimer = new Timer();

	// declaring layout components
	private TextView txt_playerOne;
	private TextView txt_playerOneScore;
	private TextView txt_playerTwo;
	private TextView txt_playerTwoScore;
	private Button btn_attack;
	private Button btn_surrender;

	// received preparation room data
	private static final String GAME_ID = "com.keepcoding.gameID";
	private static final String IDENTITY = "com.keepcoding.identity";
	private static final String PLAYER_ONE_NICKNAME = "com.keepcoding.playerOneNickname";
	private static final String PLAYER_TWO_NICKNAME = "com.keepcoding.playerTwoNickname";
	private static final String HOME_BORDER = "com.keepcoding.homeBorder";
	private static final String ATTACK_BORDER = "com.keepcoding.attackBorder";

	@Override
	protected void onDestroy() {
		super.onDestroy();
		checkForUpdatesTimer.cancel();
		dismissAlertDialog.dismiss();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameplay_room);

		// initializing layout components
		txt_playerOne = findViewById(R.id.txt_playerOne);
		txt_playerTwo = findViewById(R.id.txt_playerTow);
		txt_playerOneScore = findViewById(R.id.txt_playerOneScore);
		txt_playerTwoScore = findViewById(R.id.txt_playerTwoScore);
		btn_attack = findViewById(R.id.btn_attack);
		btn_surrender = findViewById(R.id.btn_surrender);

		// initializing gameplay room data
		Intent dataIntent = getIntent();
		Bundle bundle = dataIntent.getExtras();

		gameID = dataIntent.getStringExtra(GAME_ID);
		identity = (PlayerStatus) dataIntent.getSerializableExtra(IDENTITY);
		playerOneNickname = dataIntent.getStringExtra(PLAYER_ONE_NICKNAME);
		playerTwoNickname = dataIntent.getStringExtra(PLAYER_TWO_NICKNAME);

		// create home and attack planes border grid
		homeBorder = (int[][]) bundle.getSerializable(HOME_BORDER);
		attackBorder = (int[][]) bundle.getSerializable(ATTACK_BORDER);
		HomeBorderEngine.getInstance().initializeGrid(GameplayRoom.this, homeBorder);
		AttackBorderEngine.getInstance().initializeGrid(GameplayRoom.this, attackBorder);

		txt_playerOne.setText(playerOneNickname);
		txt_playerTwo.setText(playerTwoNickname);
		txt_playerOneScore.setText(String.valueOf(playerOneScore));
		txt_playerTwoScore.setText(String.valueOf(playerTwoScore));

		// initializing surrender alert dialog
		surrenderAlertDialog = new AlertDialog.Builder(GameplayRoom.this);
		surrenderAlertDialog.setTitle("are you sure you want to leave?");
		surrenderAlertDialog.setPositiveButton("LEAVE", ((dialog, which) -> setSurrendered()));
		surrenderAlertDialog.setNegativeButton("CANCEL", ((dialog, which) -> dialog.dismiss()));
		surrenderAlertDialog.setCancelable(false);

		// initializing leave room alert dialog
		leaveRoomAlertDialog = new AlertDialog.Builder(GameplayRoom.this);
		leaveRoomAlertDialog.setPositiveButton("GO BACK", ((dialog, which) -> setSurrendered()));
		leaveRoomAlertDialog.setCancelable(false);
		dismissAlertDialog = leaveRoomAlertDialog.create();

		// initializing on click listener to all buttons
		btn_attack.setOnClickListener(view -> attackEnemyPlanes());

		// ask the player if they are sure they wanna leave the game
		btn_surrender.setOnClickListener(view -> surrenderAlertDialog.create().show());

		// initializing scheduled task
		TimerTask checkForUpdatesTask = new TimerTask() {
			@Override
			public void run() {
				checkForUpdates();
			}
		};

		checkForUpdatesTimer.scheduleAtFixedRate(checkForUpdatesTask, 0, 6000);
	}

	// check if player turn in true and make a "attack" request to the server
	private void attackEnemyPlanes() {
		int posX = AttackBorderEngine.getInstance().getSelectedPosX();
		int posY = AttackBorderEngine.getInstance().getSelectedPosY();

		if (identity.equals(playerTurn)) {
			AttackBorderEngine.getInstance().setCellValue(posX, posY);
			gameplayRoomApiData.attackEnemy(gameID, identity, posX, posY);
			playerTurn = identity.equals(PlayerStatus.PLAYER_ONE) ? PlayerStatus.PLAYER_TWO : PlayerStatus.PLAYER_ONE;
		} else {
			Toast.makeText(GameplayRoom.this, "wait for your turn", Toast.LENGTH_SHORT).show();
		}
	}

	// change the status to not connected in the API and return to the MainMenu screen
	private void setSurrendered() {
		gameplayRoomApiData.setSurrendered(gameID, identity);
		NavUtils.navigateUpFromSameTask(this);
		checkForUpdatesTimer.cancel();
	}

	// ask the API for any data changes
	private void checkForUpdates() {
		gameplayRoomApiData.getCurrentGameplayRoomData(gameID, new GameplayRoomApiData.GameplayRoomResponseListener() {
			@Override
			public void onResponse(GameplayModel apiGameplayModel) {
				final GameplayStatus gameplayStatus = apiGameplayModel.getGameStatus();
				playerOne = apiGameplayModel.getPlayerOne();
				playerTwo = apiGameplayModel.getPlayerTwo();

				// update player turn
				playerTurn = apiGameplayModel.getPlayerStatus();

				// check if the other player left the room and notify the player
				final boolean hasSurrendered = identity.equals(PlayerStatus.PLAYER_ONE) ?
						playerTwo.getHasSurrendered() : playerOne.getHasSurrendered();
				checkPlayerConnectivity(hasSurrendered);

				// check if the score has increased and update
				checkScoreChanges(playerOne.getDestroyedPlanes(), playerTwo.getDestroyedPlanes());

				// update home border after any changes
				final int[][] homePlanesBorder = identity.equals(PlayerStatus.PLAYER_ONE) ?
						playerOne.getPlanesBorder() : playerTwo.getPlanesBorder();
				updateHomeBorder(homePlanesBorder);

				// check if the game is finished
				checkWinner(gameplayStatus);
			}

			@Override
			public void onFailure(String message) {
				Toast.makeText(GameplayRoom.this, message, Toast.LENGTH_SHORT).show();
			}
		});
	}

	// check if the other player left the room and notify the player
	private void checkPlayerConnectivity(boolean hasSurrendered) {
		if (hasSurrendered) {
			dismissAlertDialog.setTitle("the other player left the room");
			dismissAlertDialog.show();
		}
	}

	// update score
	private void checkScoreChanges(int playerOneScore, int playerTwoScore) {
		if (this.playerOneScore != playerOneScore) {
			txt_playerOneScore.setText(String.valueOf(playerOneScore));
		}
		if (this.playerTwoScore != playerTwoScore) {
			txt_playerTwoScore.setText(String.valueOf(playerTwoScore));
		}
	}

	// update any changes from server
	private void updateHomeBorder(int[][] homePlanesBorder) {
		if (!Arrays.deepEquals(homePlanesBorder, homeBorder)) {
			HomeBorderEngine.getInstance().setHomePlanesGrid(homePlanesBorder);
			homeBorder = homePlanesBorder;
		}
	}

	// check winner
	private void checkWinner(GameplayStatus gameplayStatus) {
		if (gameplayStatus.equals(GameplayStatus.FINISHED)) {
			dismissAlertDialog.setTitle(playerTurn.equals(PlayerStatus.PLAYER_ONE) ?
					playerOneNickname + " won" : playerTwoNickname + " won");
			dismissAlertDialog.show();
		}
	}
}