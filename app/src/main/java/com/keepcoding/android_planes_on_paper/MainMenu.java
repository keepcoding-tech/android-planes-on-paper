package com.keepcoding.android_planes_on_paper;

import com.keepcoding.android_planes_on_paper.preparation_room.PreparationRoom;
import com.keepcoding.android_planes_on_paper.server_api_data.PreparationRoomApiData;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.GameplayModel;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.PlayerStatus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainMenu extends Activity {
    // declaring game data
    private String gameID;
    private String playerOneNickname;
    private String playerTwoNickname;

    // declaring layout components
    private EditText et_playerNickname;
    private Button btn_startGame;

    // declaring game data
    private static final String GAME_ID = "com.keepcoding.gameID";
    private static final String IDENTITY = "com.keepcoding.identity";
    private static final String PLAYER_ONE_NICKNAME = "com.keepcoding.playerOneNickname";
    private static final String PLAYER_TWO_NICKNAME = "com.keepcoding.playerTwoNickname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        // initializing layout components
        btn_startGame = findViewById(R.id.btn_startGame);
        et_playerNickname = findViewById(R.id.et_playerNickname);

        btn_startGame.setOnClickListener(view -> connectToRandomPreparationRoom());
    }

    // connect to random preparation room
    public void connectToRandomPreparationRoom() {
        // get the player nickname
        final String playerNickname = et_playerNickname.getText().toString();
        Intent preparationRoomIntent = new Intent(MainMenu.this, PreparationRoom.class);

        PreparationRoomApiData preparationRoomConnection = new PreparationRoomApiData();
        preparationRoomConnection.connectToGameplayRoom(playerNickname, null, new PreparationRoomApiData.PreparationRoomResponseListener() {
            @Override
            public void onResponse(GameplayModel apiGameModel) {
                gameID = apiGameModel.getGameID();
                playerOneNickname = apiGameModel.getPlayerOne().getPlayerNickname();

                if (apiGameModel.getPlayerTwo() == null) {
                    preparationRoomIntent.putExtra(IDENTITY, PlayerStatus.PLAYER_ONE);
                } else {
                    playerTwoNickname = apiGameModel.getPlayerTwo().getPlayerNickname();
                    preparationRoomIntent.putExtra(IDENTITY, PlayerStatus.PLAYER_TWO);
                    preparationRoomIntent.putExtra(PLAYER_TWO_NICKNAME, playerTwoNickname);
                }

                preparationRoomIntent.putExtra(GAME_ID, gameID);
                preparationRoomIntent.putExtra(PLAYER_ONE_NICKNAME, playerOneNickname);

                startActivity(preparationRoomIntent);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(MainMenu.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}