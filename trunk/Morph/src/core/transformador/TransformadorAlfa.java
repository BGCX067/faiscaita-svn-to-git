package core.transformador;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TransformadorAlfa extends Transformador{

	public TransformadorAlfa(BufferedImage image1, BufferedImage image2) {
		super(image1, image2);
	}

	@Override
	public BufferedImage obterImagemTransformada(double passo) {
		int i, j, alfa1, alfa2;
		alfa1 = (int) ((1-passo)*255);
		alfa2 = 255 - alfa1;
		alfa1 = alfa1 << 24;
		alfa2 = alfa2 << 24;
		alfa1 = alfa1 | 0x00FFFFFF;
		alfa2 = alfa2 | 0x00FFFFFF;
		for(i=0; i< getImage1().getWidth(); i++){
			for(j=0; j<getImage1().getHeight(); j++){			
				getImage1().setRGB(i, j, (getImage1().getRGB(i, j) | 0xFF000000) & alfa1);
				getImage2().setRGB(i, j, (getImage2().getRGB(i, j) | 0xFF000000) & alfa2);			
			}
		}
		Graphics2D g2 =	getTransformada().createGraphics();
		g2.drawImage(getImage1(), 0, 0, null);
		g2.drawImage(getImage2(), 0, 0, null);
		return getTransformada();
	}

}
