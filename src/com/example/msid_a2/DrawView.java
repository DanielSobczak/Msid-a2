package com.example.msid_a2;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawView extends View implements OnTouchListener {
	private static final String TAG = "DrawView";

	List<Point> points = new ArrayList<Point>();
	Paint paint = new Paint();

	public DrawView(Context context) {
		super(context);
		setFocusable(true);
		setFocusableInTouchMode(true);

		this.setOnTouchListener(this);

		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);
	}

	public void onDraw(Canvas canvas) {
		Path path = new Path();
		boolean first = true;
		int tmp = 0;
		for(Point point : points){
			if(point.id!=tmp) {first=true; tmp=point.id;}
			if(first){
				first = false;
				path.moveTo(point.x, point.y);
			}
			else{
				path.lineTo(point.x, point.y);
			}

		}
		canvas.drawPath(path, paint);
	}

	public boolean onTouch(View view, MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			return true;
		case MotionEvent.ACTION_MOVE:
			Point point = new Point();
			point.x = event.getX();
			point.y = event.getY();
			points.add(point);
			invalidate();
			Log.d(TAG, "point: " + point);
			return true;
		case MotionEvent.ACTION_UP:
			Point.ids++;
			return true;

		default:
			return true;
		}

	}


}
