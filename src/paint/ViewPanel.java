package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/*すべての絵画がここで行う*/
class ViewPanel extends JPanel implements Observer {
	protected DrawModel model;
	int showgrid;
	BufferedImage readImage = null;

	public ViewPanel(DrawModel m, DrawController c) {
		this.setBackground(Color.white);
		this.addMouseListener(c);
		this.addMouseMotionListener(c);
		model = m;
		model.addObserver(this);
	}

	// BufferedImage bf = new BufferedImage(this.getWidth(),
	// this.getHeight(),BufferedImage.TYPE_INT_BGR);

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ArrayList<Figure> fig = model.getFigures();
		ArrayList<Grid> grid = model.getGrid();
		for (int i = 0; i < fig.size(); i++) {
			Figure f = fig.get(i);
			f.draw(g);
			repaint();
		}
		g.setColor(Color.black);
		for (int i = 0; i < showgrid; i++) {
			Grid gr = grid.get(i);
			g.drawLine(gr.sx, gr.sy, gr.ex, gr.ey);
			repaint();
		}
		if (readImage != null)
			g.drawImage(readImage, 0, 0, this);
	}

	public void update(Observable o, Object arg) {
		repaint();
	}

	public void setGridYes() {
		showgrid = model.getGrid().size();
	}

	public void setGridNo() {
		showgrid = 0;
	}

	public void drawfile(File file) {
		try {
			readImage = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
			readImage = null;
		}
	}

	public RenderedImage getbufferedImage() {
		return readImage;
	}
}