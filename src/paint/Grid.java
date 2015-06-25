package paint;

import java.awt.Color;

class Grid {
	protected int sx, sy, ex, ey;
	protected Color color;

	public Grid(int sx, int sy, int ex, int ey, Color color) {
		this.sx = sx;
		this.sy = sy;
		this.ex = ex;
		this.ey = ey;
		this.color = color;
	}

}