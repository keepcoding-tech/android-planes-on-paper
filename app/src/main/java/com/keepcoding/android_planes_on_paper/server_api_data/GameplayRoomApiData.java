package com.keepcoding.android_planes_on_paper.server_api_data;

import com.keepcoding.android_planes_on_paper.server_api_data.requests.AttackEnemyRequest;
import com.keepcoding.android_planes_on_paper.server_api_data.requests.SetSurrenderedRequest;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.GameplayModel;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.PlayerStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameplayRoomApiData {
	// declaring gameplay room API response interface
	public interface GameplayRoomResponseListener {
		void onResponse(GameplayModel apiGameplayModel);
		void onFailure(String message);
	}

	// declaring and initializing basic data
	private final String LINK = "https://planes-on-paper.loca.lt/api/game/";
	private final Retrofit apiClient = new Retrofit.Builder().baseUrl(LINK).addConverterFactory(GsonConverterFactory.create()).build();
	private final ServerApiInterface apiInterface = apiClient.create(ServerApiInterface.class);

	public void getCurrentGameplayRoomData(String gameID, GameplayRoomResponseListener responseListener) {
		Call<GameplayModel> call = apiInterface.getCurrentGameplayRoomData(gameID);

		call.enqueue(new Callback<GameplayModel>() {
			@Override
			public void onResponse(Call<GameplayModel> call, Response<GameplayModel> response) {
				if (response.isSuccessful() && response.body() != null) {
					final GameplayModel apiGameplayModel = response.body();
					responseListener.onResponse(apiGameplayModel);
				} else {
					responseListener.onFailure("problems with connection");
				}
			}

			@Override
			public void onFailure(Call<GameplayModel> call, Throwable t) {
				responseListener.onFailure("problems with connection");
			}
		});
	}

	public void attackEnemy(String gameID, PlayerStatus identity, int posX, int posY) {
		final AttackEnemyRequest request = new AttackEnemyRequest(gameID, identity, posX, posY);

		Call<AttackEnemyRequest> call = apiInterface.attackEnemy(request);
		call.enqueue(new Callback<AttackEnemyRequest>() {
			@Override
			public void onResponse(Call<AttackEnemyRequest> call, Response<AttackEnemyRequest> response) { }

			@Override
			public void onFailure(Call<AttackEnemyRequest> call, Throwable t) { }
		});
	}

	public void setSurrendered(String gameID, PlayerStatus identity) {
		final SetSurrenderedRequest request = new SetSurrenderedRequest(gameID, identity);

		Call<SetSurrenderedRequest> call = apiInterface.setSurrendered(request);
		call.enqueue(new Callback<SetSurrenderedRequest>() {
			@Override
			public void onResponse(Call<SetSurrenderedRequest> call, Response<SetSurrenderedRequest> response) { }

			@Override
			public void onFailure(Call<SetSurrenderedRequest> call, Throwable t) { }
		});
	}
}
