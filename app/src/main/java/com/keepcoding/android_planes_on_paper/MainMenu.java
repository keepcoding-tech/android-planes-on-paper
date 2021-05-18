package com.keepcoding.android_planes_on_paper;

import com.keepcoding.android_planes_on_paper.preparation_room.PreparationRoom;
import com.keepcoding.android_planes_on_paper.preparation_room.api_data.PreparationRoomApiData;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.GameplayModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainMenu extends Activity {
    // declaring layout components
    private EditText et_playerNickname;
    private Button btn_startGame;

    // declaring game data
    public static final String GAME_ID = "com.keepcoding.roomID";
    public static final String PLAYER = "com.keepcoding.player";
    public static final String PLAYER_ONE_NICKNAME = "com.keepcoding.playerOneNickname";
    public static final String PLAYER_TWO_NICKNAME = "com.keepcoding.playerTwoNickname";

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
        preparationRoomConnection.connectToRandomPreparationRoom(playerNickname, new PreparationRoomApiData.PreparationRoomResponseListener() {
            @Override
            public void onResponse(GameplayModel apiGameModel, String message) {
                final Long gameID = apiGameModel.getGameID();
                final String playerOneNickname = apiGameModel.getPlayerOne().getPlayerNickname();
                final String playerTwoNickname = apiGameModel.getPlayerTwo().getPlayerNickname();

                if (!apiGameModel.getPlayerTwo().getIsConnected()) {
                    preparationRoomIntent.putExtra(PLAYER, "playerOne");
                } else {
                    preparationRoomIntent.putExtra(PLAYER, "playerTwo");
                }

                preparationRoomIntent.putExtra(GAME_ID, gameID);
                preparationRoomIntent.putExtra(PLAYER_ONE_NICKNAME, playerOneNickname);
                preparationRoomIntent.putExtra(PLAYER_TWO_NICKNAME, playerTwoNickname);

                startActivity(preparationRoomIntent);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(MainMenu.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}