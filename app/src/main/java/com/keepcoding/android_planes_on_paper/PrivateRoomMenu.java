package com.keepcoding.android_planes_on_paper;

import com.keepcoding.android_planes_on_paper.preparation_room.PreparationRoom;
import com.keepcoding.android_planes_on_paper.server_api_data.PreparationRoomApiData;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.GameplayModel;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.PlayerStatus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.keepcoding.android_planes_on_paper.utilities.verifiy_input.VerifyInput;

public class PrivateRoomMenu extends AppCompatActivity {
	// declaring and initializing instances
	private PreparationRoomApiData preparationRoomConnection = new PreparationRoomApiData();
	private VerifyInput verifyInput = new VerifyInput();

	// declaring game data
	private String gameID;
	private String token;
	private String playerTwoNickname;
	private String playerOneNickname;

	// declaring layout components
	private EditText et_nickname;
	private EditText et_accessToken;
	private Button btn_joinGame;
	private Button btn_createGame;

	// declaring game data
	private static final String GAME_ID = "com.keepcoding.gameID";
	private static final String IDENTITY = "com.keepcoding.identity";
	private static final String PLAYER_ONE_NICKNAME = "com.keepcoding.playerOneNickname";
	private static final String PLAYER_TWO_NICKNAME = "com.keepcoding.playerTwoNickname";
	private static final String ALERT_DIALOG_TITLE = "com.keepcoding.alertDialogTitle";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.private_room_menu);

		// initializing layout components
		et_nickname = findViewById(R.id.et_privateNickname);
		et_accessToken = findViewById(R.id.et_accessToken);
		btn_joinGame = findViewById(R.id.btn_joinGame);
		btn_createGame = findViewById(R.id.btn_createGame);

		btn_joinGame.setOnClickListener(view -> {
			final String verifyNickname = verifyInput.verifyNickname(et_nickname.getText().toString());
			final String verifyAccessToken = verifyInput.verifyAccessToken(et_accessToken.getText().toString());

			if (verifyNickname != null) {
				Toast.makeText(PrivateRoomMenu.this, verifyNickname, Toast.LENGTH_SHORT).show();
			} else if (verifyAccessToken != null) {
				Toast.makeText(PrivateRoomMenu.this, verifyAccessToken, Toast.LENGTH_SHORT).show();
			} else {
				connectToPrivateRoom();
			}
		});

		btn_createGame.setOnClickListener(view -> {
			final String verifyNickname = verifyInput.verifyNickname(et_nickname.getText().toString());

			if (verifyNickname != null) {
				Toast.makeText(PrivateRoomMenu.this, verifyNickname, Toast.LENGTH_SHORT).show();
			} else {
				createPrivateGameRoom();
			}
		});
	}

	// connect to private game room
	public void connectToPrivateRoom() {
		// get access token and player nickname
		final String accessToken = et_accessToken.getText().toString();
		final String playerNickname = et_nickname.getText().toString();
		Intent intent = new Intent(PrivateRoomMenu.this, PreparationRoom.class);

		preparationRoomConnection.connectToGameplayRoom(playerNickname, accessToken,
				new PreparationRoomApiData.PreparationRoomResponseListener() {
					@Override
					public void onResponse(GameplayModel apiGameModel) {
						gameID = apiGameModel.getGameID();
						playerOneNickname = apiGameModel.getPlayerOne().getPlayerNickname();
						playerTwoNickname = apiGameModel.getPlayerTwo().getPlayerNickname();

						intent.putExtra(GAME_ID, gameID);
						intent.putExtra(IDENTITY, PlayerStatus.PLAYER_TWO);
						intent.putExtra(PLAYER_TWO_NICKNAME, playerTwoNickname);
						intent.putExtra(PLAYER_ONE_NICKNAME, playerOneNickname);

						startActivity(intent);
					}

					@Override
					public void onFailure(String message) {
						Toast.makeText(PrivateRoomMenu.this, message, Toast.LENGTH_SHORT).show();
					}
				});
	}

	// create new private game room
	public void createPrivateGameRoom() {
		// get the player nickname
		final String playerNickname = et_nickname.getText().toString();
		Intent intent = new Intent(PrivateRoomMenu.this, PreparationRoom.class);

		preparationRoomConnection.createNewGameplayRoom(playerNickname,
				new PreparationRoomApiData.PreparationRoomResponseListener() {
					@Override
					public void onResponse(GameplayModel apiGameModel) {
						gameID = apiGameModel.getGameID();
						token = apiGameModel.getAccessToken();

						intent.putExtra(GAME_ID, gameID);
						intent.putExtra(IDENTITY, PlayerStatus.PLAYER_ONE);
						intent.putExtra(PLAYER_ONE_NICKNAME, playerNickname);
						intent.putExtra(ALERT_DIALOG_TITLE, "waiting for second player\nTOKEN: " + token);

						startActivity(intent);
					}

					@Override
					public void onFailure(String message) {
						Toast.makeText(PrivateRoomMenu.this, message, Toast.LENGTH_SHORT).show();
					}
				});
	}
}