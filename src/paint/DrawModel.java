package paint;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

/*新たな図形を作り、保存するためのクラス*/
class DrawModel extends Observable implements Serializable {
	protected ArrayList<Figure> fig;
	protected Figure drawingFigure;
	protected ArrayList<Grid> grid;

	protected Color currentColor;
	protected int n = 1;

	public DrawModel() {
		fig = new ArrayList<Figure>();
		grid = new ArrayList<Grid>();
		drawingFigure = null;
		currentColor = Color.red;
	}

	public ArrayList<Figure> getFigures() {
		return fig;
	}

	public ArrayList<Grid> getGrid() {
		return grid;
	}

	public void removeFirstFig() {
		if (fig.size() > 0)
			fig.remove(fig.size() - 1);
		else
			return;
	}

	public Figure getFigure(int idx) {
		return fig.get(idx);
	}

	int head = 0;

	public void createFigure(int x, int y, int n) {
		if (n == 1) {
			Figure f = new RectangleFigure(x, y, 0, 0, currentColor);
			fig.add(f);
			drawingFigure = f;
		} else if (n == 2) {
			Figure f = new Circle(x, y, 0, 0, currentColor);
			fig.add(f);
			drawingFigure = f;
		} else if (n == 3) {
			Figure f = new Line(x, y, x, y, currentColor);
			fig.add(f);
			drawingFigure = f;
		} else if (n == 4) {
			Figure f = new RectangleSoild(x, y, 0, 0, currentColor);
			fig.add(f);
			drawingFigure = f;
		} else if (n == 5) {
			Figure f = new CircleSoild(x, y, 0, 0, currentColor);
			fig.add(f);
			drawingFigure = f;
		}

		setChanged();
		notifyObservers();
	}

	public void createGrid() {
		for (int i = 0; i < 12; i++) {
			Grid f = new Grid(i * 60, 0, i * 60, 600, Color.black);
			grid.add(f);
		}
		for (int i = 0; i < 10; i++) {
			Grid f = new Grid(0, i * 60, 800, i * 60, Color.black);
			grid.add(f);
		}
	}

	public void reshapeFigure(int x1, int y1, int x2, int y2) {
		if (drawingFigure != null) {
			if (n != 3) {
				drawingFigure.reshape(x1, y1, x2, y2);
				setChanged();
				notifyObservers();
			} else if (n == 3) {
				drawingFigure.reshapeline(x1, y1, x2, y2);
				setChanged();
				notifyObservers();
			}
		}
	}

	Figure p = null;

	public Figure Pick(int x, int y) {
		ArrayList<Figure> fig = this.getFigures();
		for (int i = 0; i < fig.size(); i++) {
			if (hit(fig.get(i), x, y)) {
				p = fig.get(i);
			}
		}
		return p;
	}

	public boolean hit(Figure fig, int x, int y) {
		String name = fig.getClass().getName();
		if (name == "paint.RectangleFigure" || name == "paint.RectangleSoild"
				|| name == "paint.CircleSoild" || name == "paint.Circle") {
			return fig.getXpos() <= x && x <= fig.getXpos() + fig.getWidth()
					&& fig.getYpos() <= y
					&& y <= fig.getYpos() + fig.getHeight();
		} else
			return false;
	}

	public void dragrect(int x, int y) {
		p.setLocation(x, y);
	}

	public void clearp() {
		p = null;
	}

	public void setcolor(Color color) {
		currentColor = color;
	}
}