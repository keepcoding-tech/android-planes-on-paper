package com.keepcoding.android_planes_on_paper.gameplay_room;

import com.keepcoding.android_planes_on_paper.R;
import com.keepcoding.android_planes_on_paper.preparation_room.PreparationRoom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GameplayRoom extends Activity {
	// declaring game data
	private Long gameID;
	private String player;
	private String playerOneNickname;
	private String playerTwoNickname;

	// declaring layout components
	private TextView txt_playerOne;
	private TextView txt_playerOneScore;
	private TextView txt_playerTwo;
	private TextView txt_playerTwoScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameplay_room);

		// initializing layout components
		txt_playerOne = findViewById(R.id.txt_playerOne);
		txt_playerTwo = findViewById(R.id.txt_playerTow);

		// initializing game data
		Intent dataIntent = getIntent();

		gameID = dataIntent.getLongExtra(PreparationRoom.GAME_ID, 0);
		String player = dataIntent.getStringExtra(PreparationRoom.PLAYER);
		String playerOneNickname = dataIntent.getStringExtra(PreparationRoom.PLAYER_ONE_NICKNAME);
		String playerTwoNickname = dataIntent.getStringExtra(PreparationRoom.PLAYER_TWO_NICKNAME);

		txt_playerOne.setText(playerOneNickname);
		txt_playerTwo.setText(playerTwoNickname);
	}
}