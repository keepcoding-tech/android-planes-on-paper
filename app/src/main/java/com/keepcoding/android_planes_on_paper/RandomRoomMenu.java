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

public class RandomRoomMenu extends AppCompatActivity {
    // declaring and initializing instances
    private VerifyInput verifyInput = new VerifyInput();

    // declaring game data
    private String gameID;
    private String playerTwoNickname;
    private String playerOneNickname;

    // declaring layout components
    private EditText et_nickname;
    private Button btn_startGame;

    // declaring game data
    private static final String GAME_ID = "com.keepcoding.gameID";
    private static final String IDENTITY = "com.keepcoding.identity";
    private static final String PLAYER_ONE_NICKNAME = "com.keepcoding.playerOneNickname";
    private static final String PLAYER_TWO_NICKNAME = "com.keepcoding.playerTwoNickname";
    private static final String ALERT_DIALOG_TITLE = "com.keepcoding.alertDialogTitle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_room_menu);

        // initializing layout components
        et_nickname = findViewById(R.id.et_randomNickname);
        btn_startGame = findViewById(R.id.btn_startGame);

        btn_startGame.setOnClickListener(view -> {
            final String verifyNickname = verifyInput.verifyNickname(et_nickname.getText().toString());

            if (verifyNickname != null) {
                Toast.makeText(RandomRoomMenu.this, verifyNickname, Toast.LENGTH_SHORT).show();
            } else {
                connectToRandomRoom();
            }
        });
    }

    // connect to random preparation room
    public void connectToRandomRoom() {
        // get the player nickname
        final String playerNickname = et_nickname.getText().toString();
        Intent intent = new Intent(RandomRoomMenu.this, PreparationRoom.class);

        PreparationRoomApiData preparationRoomConnection = new PreparationRoomApiData();
        preparationRoomConnection.connectToGameplayRoom(playerNickname, null,
                new PreparationRoomApiData.PreparationRoomResponseListener() {
                    @Override
                    public void onResponse(GameplayModel apiGameModel) {
                        gameID = apiGameModel.getGameID();
                        playerOneNickname = apiGameModel.getPlayerOne().getPlayerNickname();

                        if (apiGameModel.getPlayerTwo() == null) {
                            intent.putExtra(IDENTITY, PlayerStatus.PLAYER_ONE);
                        } else {
                            playerTwoNickname = apiGameModel.getPlayerTwo().getPlayerNickname();
                            intent.putExtra(IDENTITY, PlayerStatus.PLAYER_TWO);
                            intent.putExtra(PLAYER_TWO_NICKNAME, playerTwoNickname);
                        }

                        intent.putExtra(GAME_ID, gameID);
                        intent.putExtra(PLAYER_ONE_NICKNAME, playerOneNickname);
                        intent.putExtra(ALERT_DIALOG_TITLE, "searching for second player. . .");


                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(RandomRoomMenu.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}