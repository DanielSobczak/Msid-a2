package com.example.msid_a2;
class Point {
	float x, y;
	int id;
	public static int ids=0;

	public Point() {
		super();
		this.id = ids;
	}

	public Point(float x, float y) {
		super();
		this.x = x;
		this.y = y;
		this.id = ids;
	}

	@Override
	public String toString() {
		return x + ", " + y + ", id: "+id;
	}
}