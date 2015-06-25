package paint;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/*Mouseのイベントを検測、処理するためのクラス*/
class DrawController implements MouseListener, MouseMotionListener {
	protected DrawModel model;
	Figure f;
	protected int dragStartX, dragStartY;

	public DrawController(DrawModel a) {
		model = a;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		dragStartX = e.getX();
		dragStartY = e.getY();
		f = model.Pick(dragStartX, dragStartY);
		if (f == null)
			model.createFigure(dragStartX, dragStartY, model.n);

	}

	public void mouseDragged(MouseEvent e) {
		if (f == null)
			model.reshapeFigure(dragStartX, dragStartY, e.getX(), e.getY());
		if (f != null)
			model.dragrect(e.getX(), e.getY());

	}

	public void mouseReleased(MouseEvent e) {
		model.clearp();
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public int dragstartX() {
		return dragStartX;
	}

	public int dragstartY() {
		return dragStartY;
	}
}