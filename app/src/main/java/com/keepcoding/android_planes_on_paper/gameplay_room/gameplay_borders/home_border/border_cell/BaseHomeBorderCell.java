package com.keepcoding.android_planes_on_paper.gameplay_room.gameplay_borders.home_border.border_cell;

import android.content.Context;
import android.view.View;

public class BaseHomeBorderCell extends View {
	private int value;

	// constructors
	public BaseHomeBorderCell(Context context) {
		super(context);
	}

	// instantiate the width length to the height length, to make a perfect square
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}

	// setters
	public void setValue(int value) {
		this.value = value;
		invalidate();
	}

	// getters
	public int getValue() {
		return value;
	}
}
