package view;

import java.awt.Point;

import javax.swing.JFrame;

public class Morph{
	
	public static void main(String args[]){
		Desktop desktop = new Desktop();
		JFrame janela = new JFrame();
		janela.setSize(850, 580);			
		janela.add(desktop);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.repaint();
		janela.setLocationRelativeTo(null);
		janela.setVisible(true);	
	}
	

}
