package com.keepcoding.android_planes_on_paper.gameplay_room.gameplay_borders.home_border.border_cell;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class HomeBorderCell extends BaseHomeBorderCell {
	private Paint mPaint;

	public HomeBorderCell(Context context) {
		super(context);
		mPaint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawNumber(canvas);
		drawLines(canvas);
	}

	// draw the attribute number to each cell
	private void drawNumber(Canvas canvas) {
		mPaint.setColor(Color.BLACK);
		mPaint.setTextSize(50);
		mPaint.setStyle(Paint.Style.FILL);

		Rect bounds = new Rect();
		mPaint.getTextBounds(String.valueOf(getValue()), 0, String.valueOf(getValue()).length(), bounds);

		if (getValue() != 0) {
			canvas.drawText(String.valueOf(getValue()), (getWidth() - bounds.width()) / 2, (getHeight() + bounds.height()) / 2, mPaint);
		}
	}

	public void drawLines(Canvas canvas) {
		mPaint.setColor(Color.BLACK);
		mPaint.setStrokeWidth(5);
		mPaint.setStyle(Paint.Style.STROKE);

		canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
	}
}
