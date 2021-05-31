package com.keepcoding.android_planes_on_paper.server_api_data;

import com.keepcoding.android_planes_on_paper.server_api_data.requests.ConnectToGameRequest;
import com.keepcoding.android_planes_on_paper.server_api_data.requests.SetReadyRequest;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.GameplayModel;
import com.keepcoding.android_planes_on_paper.utilities.gameplay_models.PlayerStatus;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PreparationRoomApiData {
	// declaring Server API response interface
	public interface PreparationRoomResponseListener {
		void onResponse(GameplayModel apiGameModel);
		void onFailure(String message);
	}

	public interface SendingDataResponseListener {
		void onResponse(String message);
		void onFailure(String message);
	}

	// declaring and initialing basics data
	private final String LINK = "https://planes-on-paper.loca.lt/api/game/";
	private final Retrofit apiClient = new Retrofit.Builder().baseUrl(LINK).addConverterFactory(GsonConverterFactory.create()).build();
	private final ServerApiInterface apiInterface = apiClient.create(ServerApiInterface.class);

	public void connectToGameplayRoom(String playerNickname, String accessToken, PreparationRoomResponseListener preparationRoomResponseListener) {
		final ConnectToGameRequest request = new ConnectToGameRequest(playerNickname, accessToken);
		Call<GameplayModel> call = apiInterface.connectToGameplayRoom(request);

		call.enqueue(new Callback<GameplayModel>() {
			@Override
			public void onResponse(Call<GameplayModel> call, Response<GameplayModel> response) {
				if (response.isSuccessful() && response.body() != null) {
					final GameplayModel apiGameModel = response.body();
					preparationRoomResponseListener.onResponse(apiGameModel);
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

	public void setReady(String gameID, PlayerStatus identity, int[][] planesBorder, SendingDataResponseListener responseListener) {
		final SetReadyRequest request = new SetReadyRequest(gameID, identity, planesBorder);

		Call<SetReadyRequest> call = apiInterface.setReady(request);
		call.enqueue(new Callback<SetReadyRequest>() {
			@Override
			public void onResponse(Call<SetReadyRequest> call, Response<SetReadyRequest> response) {
				responseListener.onFailure("invalid planes border");
			}

			@Override
			public void onFailure(Call<SetReadyRequest> call, Throwable t) {
				responseListener.onResponse("ready to go");
			}
		});
	}

	public void deletePreparationRoom(String gameID) {
		Call<String> call = apiInterface.deleteGameplayRoom(gameID);
		call.enqueue(new Callback<String>() {
			@Override
			public void onResponse(Call<String> call, Response<String> response) { }

			@Override
			public void onFailure(Call<String> call, Throwable t) { }
		});
	}
}
