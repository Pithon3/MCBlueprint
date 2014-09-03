package mcBlueprint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
	
	File file;
	Scanner scanner;
	PrintWriter writer;
	Window window;
	
	String code[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "`", "~", "-", "_", "=", "+", ";", ":", "{", "}", "[", "]", "<", "|", ">", "/", "?", ",", ".", "á", "é", "í", "ó", "ú", "Á", "É", "Í", "Ó"};
	String colorcode[] = {"ä", "ë", "ï", "ö", "ü", "Ä", "Ë", "Ï"};
	
	public FileReader(Window win) {
		window = win;
	}
	
	public ArrayList<Grid> readFile(String name) {
		ArrayList<Grid> grid = new ArrayList<Grid>();
		byte g[][] = new byte[500][500];
		
		file = new File(name);
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			file = new File(name + ".grd");
			try {
				scanner = new Scanner(file);
			} catch (FileNotFoundException e1) {
				grid.add(new Grid(g, window, "file", 0));
				return grid;
			}
		}
		
		String string = "";
		while (scanner.hasNext()) {
            string += scanner.nextLine();
        }
        scanner.close();
        
        boolean layernameq = false;  //layername?
        String layername = "";
        boolean loop = true;
        int place = 0;
        int digit = 2;
        int layer = 0;
        int count = 0;
       	while (loop == true) {
        		
       		String s = "";
      		try {
       			s = string.substring(0, 1);
       		} catch (StringIndexOutOfBoundsException e) {
        		loop = false;
       		}
      		
       		int i = 0;
       		if (!(s.equals("ä") | s.equals("ë") | s.equals("ï") | s.equals("ö") | s.equals("ü") | s.equals("Ä") | s.equals("Ë") | s.equals("Ï") | s.equals(" ") | s.equals("\"")) & !layernameq) {
       			try {
       				while (!(code[i].equals(s))) {
       					i++;
       				}
       				
       				place += i * (Math.pow(100, digit));
       				digit--;       				
       				
       			} catch (IndexOutOfBoundsException e) {
       				i = -1;
       			}
        	} else if (s.equals("\"")) {
        		layernameq = !layernameq;
        		
        		if (layernameq) {
        			layer++;
        			
        			if (layer > 1) {
            			grid.add(new Grid(g, window, layername, count));
            			count = 0;
            			layername = "";
            			g = new byte[500][500];
            		}
        		}
        		
        	} else if (layernameq) {
        		layername += s;
        	}
        	else {
        		int a, b;
        		
        		a = place / 500;
        		b = place % 500;
        		
        		byte j = 0;
        		while (!(colorcode[j].equals(s))) {
        			j++;
        		} if (j == 9) {
        			j = 1;
        		}
        		
        		g[a][b] = (byte) (j + 1);
        		count++;
        		
        		digit = 2;
        		place = 0;
        	}
       		
       		try {
       			string = string.substring(1, string.length());
       		} catch (StringIndexOutOfBoundsException e) {
       			loop = false;
       		}
        }
		grid.add(new Grid(g, window, layername, count));
		
		return grid;
	}
	
	public byte writeFile(String name, ArrayList<Grid> grid) {
		String s = '"' + grid.get(0).name() + '"';
		
		for (int k = 0; k < grid.size(); k++) {
			for (int i = 0; i < 500; i++) {
				for (int j = 0; j < 500; j++) {
					if (grid.get(k).getGrid()[i][j] > 0) {
						int num = (i * 500) + j;
						int x, y, z;
					
						x = num / 10000;
						num %= 10000;
						y = num / 100;
						num %= 100;
						z = num / 1;
					
						s = s + code[x] + code[y] + code[z] + colorcode[grid.get(k).getGrid()[i][j] - 1];
					}
				}
			}
			try {
				s = s + '"' + grid.get(k + 1).name() + '"';
			} catch (IndexOutOfBoundsException e) {
				
			}
		}
		
		try {
			writer = new PrintWriter(name + ".grd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		writer.write(s);
		writer.close();
		return 0;
	}
	
}
