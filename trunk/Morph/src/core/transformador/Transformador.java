package core.transformador;

import java.awt.image.BufferedImage;

import view.Imagem;

public abstract class Transformador{

	private BufferedImage image1, image2;
	private BufferedImage transformada;

	public Transformador(BufferedImage image1, BufferedImage image2) {
		this.image1 = new BufferedImage(image1.getWidth(), image1.getHeight(), image1.getType());
		this.image1.createGraphics().drawImage(image1, 0, 0, null);
		this.image2 =  new BufferedImage(image2.getWidth(), image2.getHeight(), image2.getType());
		this.image2.createGraphics().drawImage(image2, 0, 0, null);
		transformada = new BufferedImage(image1.getWidth(), image1.getHeight(), image1.getType());
	}
	

	
	public abstract BufferedImage obterImagemTransformada(double passo);

	
	public BufferedImage getImage1() {
		return image1;
	}

	public BufferedImage getImage2() {
		return image2;
	}

	public void setImage1(BufferedImage image1) {
		this.image1 = image1;
	}

	public void setImage2(BufferedImage image2) {
		this.image2 = image2;
	}
	
	public BufferedImage getTransformada() {
		return transformada;
	}

	public void setTransformada(BufferedImage transformada) {
		this.transformada = transformada;
	}
		
}
