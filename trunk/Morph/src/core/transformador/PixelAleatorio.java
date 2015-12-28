package core.transformador;

import java.awt.image.BufferedImage;
import java.util.Random;

public class PixelAleatorio extends Transformador{

	private Random aleatorio = new Random();
	
	public PixelAleatorio(BufferedImage image1, BufferedImage image2) {
		super(image1, image2);
	}

	@Override
	public BufferedImage obterImagemTransformada(double passo) {
		int i,j;
		for(i=0; i< getImage1().getWidth(); i++){
			for(j=0; j<getImage1().getHeight(); j++){
				if(aleatorio.nextDouble()<passo)
					getTransformada().setRGB(i, j, getImage2().getRGB(i, j));
				else
					getTransformada().setRGB(i, j, getImage1().getRGB(i, j));				
			}
		}		
		return getTransformada();
	}

}
