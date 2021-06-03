package com.keepcoding.android_planes_on_paper.utilities.verifiy_input;

public class VerifyInput {
	public String verifyNickname(String nickname) {
		if (nickname.isEmpty()) {
			return "please insert a nickname";
		} else if (nickname.length() > 20) {
			return "nickname can be at max 20 characters";
		}

		return null;
	}

	public String verifyAccessToken(String accessToken) {
		if (accessToken.isEmpty()) {
			return "please insert a access token";
		} else if (accessToken.length() != 6) {
			return "access token has to be 6 characters";
		}

		return null;
	}
}
