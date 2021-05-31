package com.keepcoding.android_planes_on_paper.gameplay_room.gameplay_borders.home_border.border_grid;

import com.keepcoding.android_planes_on_paper.gameplay_room.gameplay_borders.home_border.HomeBorderEngine;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class HomeBorderGridView extends GridView {
	private final Context context;

	// constructors
	public HomeBorderGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		HomeBorderGridViewAdaptor gridViewAdaptor = new HomeBorderGridViewAdaptor();
		setAdapter(gridViewAdaptor);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}

	static class HomeBorderGridViewAdaptor extends BaseAdapter {
		@Override
		public int getCount() {
			return 100;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return HomeBorderEngine.getInstance().getHomePlanesGrid().getHomeBorderItem(position);
		}
	}
}
