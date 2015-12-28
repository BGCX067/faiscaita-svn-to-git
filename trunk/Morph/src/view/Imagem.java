package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class Imagem extends JPanel{

	private ImageIcon imageIcon;
	private BufferedImage image;
	private int altura , largura;
	private List<Point> mesh = new ArrayList<Point>();
	private double escala = 1;
	private final int TAMANHO_PONTO =3;
	
	public Imagem(String fileName,int largura,int altura){
		this(largura, altura);
		imageIcon = new ImageIcon(fileName);
		image = new BufferedImage(largura,altura,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(imageIcon.getImage(), 0, 0, largura, altura, null);
		addMouseListener(
				new MouseInputAdapter(){
					public void mouseClicked(MouseEvent event){
						mesh.add(new Point((int)(event.getPoint().x*escala), (int)(event.getPoint().y*escala)));
						repaint();
					}
				});
	}

	public Imagem(int largura, int altura){
		this.altura = altura;
		this.largura = largura;
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image, 0, 0, getLargura(), getAltura(), null);
		g.setColor(Color.BLUE);
		for(Point p : mesh){
			g.drawOval((int)(p.x/escala),(int) (p.y/escala), TAMANHO_PONTO, TAMANHO_PONTO);
		}
	}

	public int getAltura() {
		return altura;
	}

	public int getLargura() {
		return largura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public void setLargura(int largura) {
		this.largura = largura;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public List<Point> getMesh() {
		return mesh;
	}

	public void setMesh(List<Point> mesh) {
		this.mesh = mesh;
	}

	public double getEscala() {
		return escala;
	}

	public void setEscala(double escala) {
		this.escala = escala;
	}

}
