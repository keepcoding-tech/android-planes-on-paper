package com.keepcoding.android_planes_on_paper.server_api_data;

import com.keepcoding.android_planes_on_paper.server_api_data.requests.AttackEnemyRequest;
import com.keepcoding.android_planes_on_paper.server_api_data.requests.ConnectToGameRequest;
import com.keepcoding.android_planes_on_paper.server_api_data.requests.SetSurrenderedRequest;
import com.keepcoding.android_planes_on_paper.server_api_data.requests.SetReadyRequest;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.GameplayModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;

public interface ServerApiInterface {
	@HTTP(method = "POST", path = "data", hasBody = true)
	Call<GameplayModel> getCurrentGameplayRoomData(@Body String gameID);

	@HTTP(method = "POST", path = "connect-to-game", hasBody = true)
	Call<GameplayModel> connectToGameplayRoom(@Body ConnectToGameRequest request);

	@HTTP(method = "POST", path = "create-game", hasBody = true)
	Call<GameplayModel> createNewGameplayRoom(@Body String playerNickname);

	@HTTP(method = "PUT", path = "set-ready", hasBody = true)
	Call<SetReadyRequest> setReady(@Body SetReadyRequest request);

	@HTTP(method = "PUT", path = "attack", hasBody = true)
	Call<AttackEnemyRequest> attackEnemy(@Body AttackEnemyRequest request);

	@HTTP(method = "PUT", path = "surrender", hasBody = true)
	Call<SetSurrenderedRequest> setSurrendered(@Body SetSurrenderedRequest request);

	@HTTP(method = "DELETE", path = "delete", hasBody = true)
	Call<String> deleteGameplayRoom(@Body String gameID);
}
