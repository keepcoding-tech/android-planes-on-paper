package com.keepcoding.android_planes_on_paper.utilities.planes_border.border_cell;

import android.content.Context;
import android.view.View;

public class BaseBorderCell extends View {
	private int value;

	// constructors
	public BaseBorderCell(Context context) {
		super(context);
	}

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
