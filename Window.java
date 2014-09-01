package mcBlueprint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Window extends JPanel implements ActionListener {
	
	private Mouse mouse;
	private Grid grid;
	private FileReader reader;
	Graphics2D G;
	private static final long serialVersionUID = 1L;
	
	private String nameword = "";
	private String filename = "file";
	private int count;
	private int width;
	private int height;
	private short xshift = 1500;
	private short yshift = 1500;
	private byte saving;
	private byte uploading;
	private byte savingas;
	private byte color;
	private boolean menu = false;
	private boolean typing = false;
	private boolean tools = false;
	
	public Window() {
		setBackground(Color.BLACK);
		setFocusable(true);
		setDoubleBuffered(true);
		
		mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		KAdapter adapt = new KAdapter();
		addKeyListener(adapt);
		
		grid = new Grid(this);
		reader = new FileReader(this);
		color = 1;
		
		Timer timer = new Timer(20, this);
		timer.start();
		
	}  
	
	
	public void paint(Graphics g) {
		super.paint(g);
		G = (Graphics2D) g;
		
		
		if (menu) {
			G.setColor(new Color(25, 25, 25));
		} else {
			G.setColor(new Color(50, 50, 50));
		} for (int i = 0; i < 500; i++) {
			G.drawLine(i * 10 - xshift, -yshift, i * 10 - xshift, 5000 - yshift);
			G.drawLine(-xshift, i * 10 - yshift, 5000 - xshift, i * 10 - yshift);
		}
		
		
		if (menu) {
			G.setColor(new Color(50, 50, 50));
		} else {
			G.setColor(new Color(100, 100, 100));
		} for (int i = 0; i < grid.getGrid().length; i++) {
			for (int j = 0; j < grid.getGrid()[i].length; j++) {
				byte[][] Grid = grid.getGrid();
				
				if (Grid[i][j] > 0) {
					G.setColor(grid.getColor(Grid[i][j]).darker());
					G.fillRect(i * 10 - xshift, j * 10 - yshift, 11, 11);
					G.setColor(grid.getColor(Grid[i][j]));
					G.fillRect(i * 10 - xshift, j * 10 - yshift, 10, 10);
				}
			}
		}
		
		
		G.setColor(new Color(120, 120, 120));
		if (count < 10) {
			G.fillRect(0, 0, 91, 11);
		} else if (count < 100) {
			G.fillRect(0, 0, 100, 11);
		} else if (count < 10000) {
			G.fillRect(0, 0, 111, 11);
		} else if (count < 10000) {
			G.fillRect(0, 0, 121, 11);
		} else {
			G.fillRect(0, 0, 131, 11);
		} 
		
		
		if (menu == true) {
			G.setColor(new Color(0, 0, 0));
			G.fillRect(width - 100, height - 200, 100, 200);
			
			G.setColor(new Color(255, 255, 255));
			G.drawString("> Upload Grid <", width - 95, height - 180);
			G.drawString("> Save Grid <", width - 95, height - 160);
			G.drawString("> Save as <", width - 95, height - 140);
			if (saving > 0) {
				G.drawString("Please wait,", width - 95, height - 35);
				G.drawString("Saving...", width - 95, height - 20);
				saving = 2;
			} 
			if (uploading == 1) {
				savingas = 0;
				G.drawString("Type the name of the file here: " + nameword + "|", 10, height - 10);
				typing = true;
			} else if (uploading > 1) {
				typing = false;
				G.drawString("Please wait,", width - 95, height - 35);
				G.drawString("Uploading...", width - 95, height - 20);
				uploading = 3;
			}
			if (savingas == 1) {
				uploading = 0;
				G.drawString("Type the name for the new file here: " + nameword + "|", 10, height - 10);
				typing = true;
			} else if (savingas > 1) {
				typing = false;
				G.drawString("Please wait,", width - 95, height - 35);
				G.drawString("Saving...", width - 95, height - 20);
				savingas = 3;
			}
		}
		
		
		if (tools == true) {
			G.setColor(new Color(0, 0, 0));
			G.fillRect(width - 45, 0, 45, 190);
			
			for (byte i = 1; i < 9; i++) {
				G.setColor(grid.getColor(i));
				G.fillRect(width - 30, (i * 20) + 5, 15, 15);
			}
		}
		
		
		G.setColor(new Color(255, 255, 255));
		G.drawString("Total Blocks: " + count, 10, 10);
		G.drawString("  Menu  ", width - 38, height - 1);
		G.drawString("  Tools  ", width - 43, 11);
		
		Toolkit.getDefaultToolkit().sync();
		G.dispose();
	}

	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		width = (int) this.getSize().getWidth();
		height = (int) this.getSize().getHeight();
		
		repaint();
		
		if (saving == 2) {
			saving = reader.writeFile(filename, grid.getGrid());
		} else if (uploading == 3) {
			grid.setGridTo(reader.readFile(filename));
			uploading = 0;
		} else if (savingas == 3) {
			reader.writeFile(filename, grid.getGrid());
			savingas = 0;
		}
	}
	
	
	
	private class Mouse extends MouseAdapter{
		
	    public void mouseClicked(MouseEvent e) {
	    	int x = e.getX();
	    	int y = e.getY();
	    	if (x > width - 50 & y > height - 13) {
	    		menu = !menu;
	    		resetMenu();
	    	} else if (x > width - 50 & y < 13 & !menu) {
	    		tools = !tools;
	    	} else if (!menu & !tools) {
	    		
	    		if (e.getButton() == MouseEvent.BUTTON1) {	
	    			grid.fillBlock((x + xshift) / 10, (y + yshift) / 10, color);
	    		} else if (e.getButton() == MouseEvent.BUTTON3) {
	    			grid.clearBlock((x + xshift) / 10, (y + yshift) / 10);
	    		}
	    	} else if (menu) {
	    		if (x > width - 100 & y < height - 180 & y > height - 200) {
	    			upload();
	    		} if (x > width - 100 & y < height - 160 & y > height - 180) {
	    			save();
	    		} if (x > width - 100 & y < height - 140 & y > height - 160) {
	    			saveAs();
	    		} if (x < width - 100 | y < height - 200) {
	    			menu = false;
	    		}
	    	} else if (tools) {
	    		for (byte i = 1; i < 9; i++) {					
					if (x > width - 30 & x < width - 15 & y > (i * 20) + 5 & y < (i * 20) + 20) {
						color = i;
					}
				} if (x < width - 50 | y > 190) {
					tools = false;
				}
	    	}
	    }

		public void mouseDragged(MouseEvent e) {
	    	int x = e.getX();
	    	int y = e.getY();
	    	if (!menu & !tools) {
	    		if (SwingUtilities.isLeftMouseButton(e)) {
	    			grid.fillBlock((x + xshift) / 10, (y + yshift) / 10, color);
	    		} else if (SwingUtilities.isRightMouseButton(e)) {
	    			grid.clearBlock((x + xshift) / 10, (y + yshift) / 10);
	    		}
	    	} else if (menu) {
	    		if (x > width - 100 & y < height - 180 & y > height - 200) {
	    			upload();
	    		} if (x > width - 100 & y < height - 160 & y > height - 180) {
	    			save();
	    		} if (x > width - 100 & y < height - 140 & y > height - 160) {
	    			saveAs();
	    		} if (x < width - 100 | y < height - 200) {
	    			menu = false;
	    		}
	    	} else if (tools) {
	    		for (byte i = 1; i < 9; i++) {					
					if (x > width - 30 & x < width - 15 & y > (i * 20) + 5 & y < (i * 20) + 20) {
						color = i;
					}
				} if (x < width - 50 | y > 190) {
					tools = false;
				}
	    	}
	    }
	}
	
	
	
	private class KAdapter extends KeyAdapter {
		
		public void keyTyped(KeyEvent e) {
			if (typing) {
				char c = e.getKeyChar();
				if (c == '') {
					if (nameword.length() > 0){
						nameword = nameword.substring(0, nameword.length() - 1);
					}
				} else if (c == '\n') {
					filename = nameword;
					nameword = "";
					uploadOrSave();
				} else {
					nameword += c;
				}
			} else if (e.getKeyChar() == 'w' & yshift > 0) {
				yshift -= 10;
			} else if (e.getKeyChar() == 'a' & xshift > 0) {
				xshift -= 10;
			} else if (e.getKeyChar() == 's' & yshift < 5000 - height) {
				yshift += 10;
			} else if (e.getKeyChar() == 'd' & xshift < 5000 - width) {
				xshift += 10;
			}
		}
		
	}
	
	
	
	public void countUp() {
		count++;
	}
	
	public void countDown() {
		count--;
	}
	
	private void upload() {
		count = 0;
		nameword = "";
		uploading = 1;
	}
	
	private void save() {
		saving = 1;
	}
	
	private void saveAs() {
		savingas = 1;
	}
	
	public void uploadOrSave() {
		if (uploading > 0) {
			uploading = 2;
		} else if (savingas > 0) {
			savingas = 2;
		}
	}
	
	private void resetMenu() {
		typing = false;
		savingas = 0;
		uploading = 0;
		saving = 0;
		
	}
	
}
