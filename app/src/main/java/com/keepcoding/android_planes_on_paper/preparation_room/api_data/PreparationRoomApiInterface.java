package com.keepcoding.android_planes_on_paper.preparation_room.api_data;

import com.keepcoding.android_planes_on_paper.preparation_room.api_data.api_local_req.PlayerIsReadyRequest;
import com.keepcoding.android_planes_on_paper.utilities.api_global_req.PlayerHasSurrenderedRequest;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.GameplayModel;

import retrofit2.Call;
import retrofit2.http.*;

public interface PreparationRoomApiInterface {
	@HTTP(method = "POST", path = "random-game/connect-to-random-game", hasBody = true)
//	@POST("random-game/connect-to-random-game")
	Call<GameplayModel> connectToRandomPreparationRoom(@Body String playerNickname);

	@HTTP(method = "POST", path = "random-game/data", hasBody = true)
//	@POST("random-game/data")
	Call<GameplayModel> getCurrentPreparationRoomData(@Body String gameID);

	@HTTP(method = "PUT", path = "random-game/player-is-ready", hasBody = true)
//	@PUT("random-game/player-is-ready")
	Call<GameplayModel> updatePlayerIsReady(@Body PlayerIsReadyRequest request);

	@HTTP(method = "PUT", path = "random-game/surrender", hasBody = true)
//	@PUT("random-game/surrender")
	Call<GameplayModel> leavePreparationRoom(@Body PlayerHasSurrenderedRequest playerHasSurrenderedRequest);

	@HTTP(method = "DELETE", path = "random-game/delete", hasBody = true)
	Call<GameplayModel> deleteGameplay(@Body String gameID);
}
