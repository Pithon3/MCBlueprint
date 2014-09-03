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

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Window extends JPanel implements ActionListener {
	
	private Mouse mouse;
	private ArrayList<Grid> grid = new ArrayList<Grid>();
	private FileReader reader;
	Graphics2D G;
	private static final long serialVersionUID = 1L;
	
	private String nameword = "";
	private String filename = "file";
	
	private int width;
	private int height;
	
	private short xshift = 1500;
	private short yshift = 1500;
	
	private byte grids = 0;
	private byte saving;
	private byte uploading;
	private byte savingas;
	private byte color;
	
	private boolean newlayer;
	private boolean gridmenu = false;
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
		
		grid.add(new Grid(this, filename));
		reader = new FileReader(this);
		color = 1;
		
		Timer timer = new Timer(20, this);
		timer.start();
	}  
	
	
	//Paint Method
	public void paint(Graphics graph) {
		super.paint(graph);
		G = (Graphics2D) graph;
		
		Grid g = grid.get(grids);
		
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
		} for (int i = 0; i < g.getGrid().length; i++) {
			for (int j = 0; j < g.getGrid()[i].length; j++) {
				byte[][] Grid = g.getGrid();
				
				if (Grid[i][j] > 0) {
					G.setColor(grid.get(grids).getColor(Grid[i][j]).darker());
					G.fillRect(i * 10 - xshift, j * 10 - yshift, 11, 11);
					G.setColor(grid.get(grids).getColor(Grid[i][j]));
					G.fillRect(i * 10 - xshift, j * 10 - yshift, 10, 10);
				}
			}
		}
		
		
		G.setColor(new Color(120, 120, 120));
		if (g.count() < 10) {
			G.fillRect(0, 0, 91, 11);
		} else if (g.count() < 100) {
			G.fillRect(0, 0, 100, 11);
		} else if (g.count() < 10000) {
			G.fillRect(0, 0, 111, 11);
		} else if (g.count() < 10000) {
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
			G.drawString("> Switch Layer <", width - 95, height - 120);
			G.drawString("> New Layer <", width - 95, height - 100);
			
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
			} else if (newlayer) {
				G.drawString("Type the name of the new layer here: " + nameword + "|", 10, height - 10);
				typing = true;
			}
			
			
			if (gridmenu) {
				G.setColor(new Color(0, 0, 0));
				G.fillRect(width - 200, height - 140, 100, 140);
				
				G.setColor(new Color(25, 25, 25));
				G.drawLine(width - 100, height - 140, width - 100, height);
				
				G.setColor(new Color(255, 255, 255));
				for (int i = 0; i < grid.size(); i++) {
					G.drawString("> " + grid.get(i).name() + " <", width - 195, height - 140 + (20 * (i + 1)));
				}
				
				
			}
		}
		
		
		if (tools == true) {
			G.setColor(new Color(0, 0, 0));
			G.fillRect(width - 45, 0, 45, 190);
			
			for (byte i = 1; i < 9; i++) {
				G.setColor(grid.get(grids).getColor(i));
				G.fillRect(width - 30, (i * 20) + 5, 15, 15);
			}
		}
		
		
		G.setColor(new Color(255, 255, 255));
		G.drawString("Total Blocks: " + g.count(), 10, 10);
		G.drawString("  Menu  ", width - 38, height - 1);
		G.drawString("  Tools  ", width - 43, 11);
		
		Toolkit.getDefaultToolkit().sync();
		G.dispose();
	}

	
	//Action Performed Method
	@Override
	public void actionPerformed(ActionEvent arg0) {
		width = (int) this.getSize().getWidth();
		height = (int) this.getSize().getHeight();
		
		repaint();
		
		if (saving == 2) {
			saving = reader.writeFile(filename, grid);
		} else if (uploading == 3) {
			grid = reader.readFile(filename);
			uploading = 0;
		} else if (savingas == 3) {
			reader.writeFile(filename, grid);
			savingas = 0;
		}
	}
	
	
	//Mouse Class
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
	    			grid.get(grids).fillBlock((x + xshift) / 10, (y + yshift) / 10, color);
	    		} else if (e.getButton() == MouseEvent.BUTTON3) {
	    			grid.get(grids).clearBlock((x + xshift) / 10, (y + yshift) / 10);
	    		}
	    	}
	    	
	    	else if (menu) {
	    		if (x > width - 100 & y < height - 180 & y > height - 200) {
	    			upload();
	    		} if (x > width - 100 & y < height - 160 & y > height - 180) {
	    			save();
	    		} if (x > width - 100 & y < height - 140 & y > height - 160) {
	    			saveAs();
	    		} if (x < width - 100 | y < height - 200) {
	    			if (!gridmenu) {
	    				menu = false;
	    			}
	    		} if (x > width - 100 & y < height - 120 & y > height - 140) {
	    			gridmenu = !gridmenu;
	    		} if (x > width - 100 & y < height - 100 & y > height - 120) {
	    			newlayer = true;
	    		}
	    		
	    		if (gridmenu) {
	    			for (int i = 0; i < grid.size(); i++) {
	    				if (x > width - 200 & x < width - 100 & y < height - 140 + (20 * (i + 1)) & y > height - 140 + (20 * i)) {
	    					grids = (byte) i;
	    				}
	    			}
	    		}
	    	}
	    	
	    	else if (tools) {
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
	    			grid.get(grids).fillBlock((x + xshift) / 10, (y + yshift) / 10, color);
	    		} else if (SwingUtilities.isRightMouseButton(e)) {
	    			grid.get(grids).clearBlock((x + xshift) / 10, (y + yshift) / 10);
	    		}
	    	} else if (menu) {
	    		if (x > width - 100 & y < height - 180 & y > height - 200) {
	    			upload();
	    		} if (x > width - 100 & y < height - 160 & y > height - 180) {
	    			save();
	    		} if (x > width - 100 & y < height - 140 & y > height - 160) {
	    			saveAs();
	    		} if (x < width - 100 | y < height - 200) {
	    			if (!gridmenu) {
	    				menu = false;
	    			}
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
	
	
	//Keystroke class
	private class KAdapter extends KeyAdapter {
		
		public void keyTyped(KeyEvent e) {
			if (typing) {
				char c = e.getKeyChar();
				if (c == '') {
					if (nameword.length() > 0){
						nameword = nameword.substring(0, nameword.length() - 1);
					}
				} else if (c == '\n') {
					uploadOrSave();
					nameword = "";
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
	
	
	//Other Methods	
	private void upload() {
		grid.get(grids).resetCount();
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
			filename = nameword;
			uploading = 2;
		} else if (savingas > 0) {
			filename = nameword;
			savingas = 2;
		} else if (newlayer = true) {
			grid.add(new Grid(new byte[500][500], this, nameword, 0));
			newlayer = false;
			grids++;
		}
	}
	
	private void resetMenu() {
		typing = false;
		savingas = 0;
		uploading = 0;
		saving = 0;
		gridmenu  = false;
		newlayer = false;
	}
	
}
