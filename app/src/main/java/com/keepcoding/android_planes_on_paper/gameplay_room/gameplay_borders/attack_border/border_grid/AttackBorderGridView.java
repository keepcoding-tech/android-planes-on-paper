package com.keepcoding.android_planes_on_paper.gameplay_room.gameplay_borders.attack_border.border_grid;

import com.keepcoding.android_planes_on_paper.gameplay_room.GameplayRoom;
import com.keepcoding.android_planes_on_paper.gameplay_room.gameplay_borders.attack_border.AttackBorderEngine;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class AttackBorderGridView extends GridView {
	private final Context context;

	// constructors
	public AttackBorderGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		final GameplayRoom gameplayRoom = new GameplayRoom();
		AttackBorderGridAdaptor gridAdaptor = new AttackBorderGridAdaptor();
		setAdapter(gridAdaptor);

		setOnItemClickListener((parent, view, position, id) -> {
			int x = position / 10;
			int y = position % 10;

			AttackBorderEngine.getInstance().setSelectedPosX(x);
			AttackBorderEngine.getInstance().setSelectedPosY(y);
		});
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}

	static class AttackBorderGridAdaptor extends BaseAdapter {
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
			return AttackBorderEngine.getInstance().getAttackPlanesGrid().getAttackPlanesItem(position);
		}
	}
}
