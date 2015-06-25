package paint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/*図形を保存するためのクラス*/
class Figure {
	protected int x, y, width, height;
	protected Color color;

	public Figure(int x, int y, int w, int h, Color c) {
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		color = c;
	}

	public void setSize(int w, int h) {
		width = w;
		height = h;
	}

	public int getXpos() {
		return x;
	}

	public int getYpos() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Color getcolor() {
		return color;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void reshape(int x1, int y1, int x2, int y2) {
		int newx = Math.min(x1, x2);
		int newy = Math.min(y1, y2);
		int neww = Math.abs(x1 - x2);
		int newh = Math.abs(y1 - y2);
		setLocation(newx, newy);
		setSize(neww, newh);
	}

	public void reshapeline(int x1, int y1, int x2, int y2) {
		setLocation(x1, y1);
		setSize(x2, y2);
	}

	public void draw(Graphics g) {
	}

}

/* 四角を生成するためのクラス */
class RectangleFigure extends Figure {
	public RectangleFigure(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.drawRect(x, y, super.width, super.height);
	}
}

/* 円を生成するためのクラス */
class Circle extends Figure {
	public Circle(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.drawOval(x, y, width, height);
	}
}

/* 塗りつぶし四角を生成するためのクラス */
class RectangleSoild extends Figure {
	public RectangleSoild(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
}

/* 塗りつぶし円を生成するためのクラス */
class CircleSoild extends Figure {
	public CircleSoild(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, width, height);
	}
}

/* 直線を生成するためのクラス */
class Line extends Figure {

	public Line(int x1, int y1, int x2, int y2, Color c) {
		super(x1, y1, x2, y2, c);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.drawLine(x, y, width, height);
	}
}

/* JFrameを生成し、主画面や部品を絵画するためのクラス */
class DrawFrame extends JFrame implements ActionListener {
	DrawModel model;
	ViewPanel view;
	DrawController cont;
	Figure figure;
	BufferedImage buf = null;

	JPanel p1 = new JPanel(), p2 = new JPanel(), p3 = new JPanel();
	JButton b1 = new JButton("Color"), b2 = new JButton("Rectangle");
	JButton b3 = new JButton("Circle"), b4 = new JButton("Line");
	JButton b5 = new JButton("Soild"), b6 = new JButton("hollow");
	JButton b7 = new JButton("Undo"), b8 = new JButton("Grid"),
			b9 = new JButton("NoGrid");

	JMenuBar menubar = new JMenuBar();
	JMenu m1 = new JMenu("File");
	JMenuItem mi1 = new JMenuItem("Quit");
	JMenuItem mi2 = new JMenuItem("Save as");
	JMenuItem mi3 = new JMenuItem("Open");

	public DrawFrame() {
		menubar.add(m1);
		m1.add(mi2);
		m1.add(mi1);
		m1.add(mi3);
		mi1.addActionListener(this);
		mi2.addActionListener(this);
		mi3.addActionListener(this);
		setJMenuBar(menubar);
		p1.setLayout(new GridLayout(3, 1));
		p1.add(p3);
		p3.setLayout(new BoxLayout(p3, BoxLayout.PAGE_AXIS));
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		b8.addActionListener(this);
		b9.addActionListener(this);

		p3.add(b1);
		p3.add(b2);
		p3.add(b3);
		p3.add(b4);
		p3.add(b5);
		p3.add(b6);
		p3.add(b7);
		p3.add(b8);
		p3.add(b9);
		this.add(p3, BorderLayout.WEST);
		model = new DrawModel();
		cont = new DrawController(model);
		view = new ViewPanel(model, cont);
		this.setBackground(Color.white);
		this.setTitle("Draw Editor");
		this.setSize(800, 600);
		this.add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String argv[]) {
		new DrawFrame();
	}

	/* 部品による発生したイベントはここで処理する */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) {
			model.createGrid();
			JColorChooser colorchooser = new JColorChooser();
			Color color = colorchooser.showDialog(this, "Choose color",
					Color.red);
			if (color != null) {
				model.setcolor(color);
			}
		} else if (e.getSource() == b2) {
			if (model.n == 2 || model.n == 3)
				model.n = 1;
			else if (model.n == 5)
				model.n = 4;
		} else if (e.getSource() == b3) {
			if (model.n == 1 || model.n == 3)
				model.n = 2;
			else if (model.n == 4)
				model.n = 5;
		} else if (e.getSource() == mi1) {
			System.exit(0);
		} else if (e.getSource() == b4) {
			model.n = 3;
		} else if (e.getSource() == b5) {
			if (model.n == 1)
				model.n = 4;
			else if (model.n == 2)
				model.n = 5;
		} else if (e.getSource() == b6) {
			if (model.n == 4)
				model.n = 1;
			else if (model.n == 5)
				model.n = 2;
		} else if (e.getSource() == b7) {
			model.removeFirstFig();
		} else if (e.getSource() == b8) {
			model.createGrid();
			view.setGridYes();
		} else if (e.getSource() == b9) {
			view.setGridNo();
		}

		else if (e.getSource() == mi2) {
			JFileChooser fc = new JFileChooser();
			int selected = fc.showSaveDialog(this);
			if (selected == JFileChooser.APPROVE_OPTION) {
			}

			buf = new BufferedImage(getWidth(), getHeight(),
					BufferedImage.TYPE_INT_RGB);

			boolean result;

			try {
				result = ImageIO.write(view.getbufferedImage(), "jpeg",
						new File("sample.jpeg"));
			} catch (Exception e1) {
				e1.printStackTrace();
				result = false;
			}

		}

		else if (e.getSource() == mi3) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"JPG & GIF Images", "jpg", "gif");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(null);
			File file = chooser.getSelectedFile();
			if (file == null)
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					view.drawfile(file);
				}
		}
	}
}


