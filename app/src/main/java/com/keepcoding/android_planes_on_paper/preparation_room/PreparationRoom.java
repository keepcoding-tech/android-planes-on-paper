package com.keepcoding.android_planes_on_paper.preparation_room;

import com.keepcoding.android_planes_on_paper.utilities.planes_border.BorderEngine;
import com.keepcoding.android_planes_on_paper.R;

import android.app.Activity;
import android.os.Bundle;

public class PreparationRoom extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preparation_room);
		BorderEngine.getInstance().createGrid(PreparationRoom.this);
	}
}