package mcBlueprint;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Main() {
		add(new Window());
		
		BufferedImage BufImage = null;
		try {
			BufImage = ImageIO.read(getClass().getResource("/mcBlueprint/Logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (BufImage != null) {
    	    ImageIcon ii = new ImageIcon(BufImage);
    	    Image image = ii.getImage();
    	    setIconImage(image);
    	}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(720, 480);
		setLocationRelativeTo(null);
		setTitle("Minecraft Blueprint 1.1.5");
		setVisible(true);
		setResizable(true);
		setVisible(true);

	}

	public static void main(String args[]) {
		new Main();
	}
	
	public static void start() {
		new Main();
	}
	
}
