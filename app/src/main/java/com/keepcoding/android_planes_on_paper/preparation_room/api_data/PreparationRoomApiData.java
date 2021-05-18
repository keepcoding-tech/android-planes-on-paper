package com.keepcoding.android_planes_on_paper.preparation_room.api_data;

import com.keepcoding.android_planes_on_paper.preparation_room.api_data.api_local_req.PlayerIsReadyRequest;
import com.keepcoding.android_planes_on_paper.utilities.api_global_req.PlayerHasSurrenderedRequest;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.GameplayModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreparationRoomApiData {
	// declaring preparation room API response interface
	public interface PreparationRoomResponseListener {
		void onResponse(GameplayModel apiGameModel, String message);
		void onFailure(String message);
	}

	// declaring and initialing basics data
	private final String LINK = "https://8cbcc474bd82.ngrok.io/api/game/";
	private final Retrofit apiClient = new Retrofit.Builder().baseUrl(LINK).addConverterFactory(GsonConverterFactory.create()).build();
	private final PreparationRoomApiInterface apiInterface = apiClient.create(PreparationRoomApiInterface.class);

	public void connectToRandomPreparationRoom(String playerNickname, PreparationRoomResponseListener preparationRoomResponseListener) {
		Call<GameplayModel> call = apiInterface.connectToRandomPreparationRoom(playerNickname);

		call.enqueue(new Callback<GameplayModel>() {
			@Override
			public void onResponse(Call<GameplayModel> call, Response<GameplayModel> response) {
				if (response.isSuccessful() && response.body() != null) {
					final GameplayModel apiGameModel = response.body();
					preparationRoomResponseListener.onResponse(apiGameModel, "successfully connected to preparation room");
				} else {
					preparationRoomResponseListener.onFailure("could not connect to preparation room");
				}
			}

			@Override
			public void onFailure(Call<GameplayModel> call, Throwable t) {
				preparationRoomResponseListener.onFailure("could not connect to preparation room");
			}
		});
	}

	public void getCurrentPreparationRoomData(Long roomID, PreparationRoomResponseListener preparationRoomResponseListener) {
		Call<GameplayModel> call = apiInterface.getCurrentPreparationRoomData(roomID.toString());

		call.enqueue(new Callback<GameplayModel>() {
			@Override
			public void onResponse(Call<GameplayModel> call, Response<GameplayModel> response) {
				if (response.isSuccessful() && response.body() != null) {
					final GameplayModel apiGameModel = response.body();
					preparationRoomResponseListener.onResponse(apiGameModel, "connected successfully");
				} else {
					preparationRoomResponseListener.onFailure("could not retrieve data");
				}
			}

			@Override
			public void onFailure(Call<GameplayModel> call, Throwable t) {
				preparationRoomResponseListener.onFailure("could not retrieve data");
			}
		});
	}

	public void updatePlayerIsReady(Long gameID, String player, int[][] planesBorder) {
		final PlayerIsReadyRequest request = new PlayerIsReadyRequest(gameID, player, planesBorder);

		Call<GameplayModel> call = apiInterface.updatePlayerIsReady(request);
		call.enqueue(new Callback<GameplayModel>() {
			@Override
			public void onResponse(Call<GameplayModel> call, Response<GameplayModel> response) { }

			@Override
			public void onFailure(Call<GameplayModel> call, Throwable t) { }
		});
	}

	public void leavePreparationRoom(Long gameID, String player) {
		final PlayerHasSurrenderedRequest request = new PlayerHasSurrenderedRequest(gameID, player);

		Call<GameplayModel> call = apiInterface.leavePreparationRoom(request);
		call.enqueue(new Callback<GameplayModel>() {
			@Override
			public void onResponse(Call<GameplayModel> call, Response<GameplayModel> response) { }

			@Override
			public void onFailure(Call<GameplayModel> call, Throwable t) { }
		});
	}

	public void deleteGameplay(Long gameID) {
		Call<GameplayModel> call = apiInterface.deleteGameplay(gameID.toString());
		call.enqueue(new Callback<GameplayModel>() {
			@Override
			public void onResponse(Call<GameplayModel> call, Response<GameplayModel> response) { }

			@Override
			public void onFailure(Call<GameplayModel> call, Throwable t) { }
		});
	}
}
