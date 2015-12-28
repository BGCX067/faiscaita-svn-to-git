package core.transformador;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class TransformadorCores extends Transformador{

	public TransformadorCores(BufferedImage image1, BufferedImage image2) {
		super(image1, image2);
	}

	public BufferedImage obterImagemTransformada(double passo) {
		int i, j, cor1, cor2, corTransf1, corTransf2, corResult;
		double parcela1 = 1 - passo, parcela2 = passo;
		for(i=0; i< getImage1().getWidth(); i++){
			for(j=0; j<getImage1().getHeight(); j++){
				cor1 = getImage1().getRGB(i, j);
				cor2 = getImage2().getRGB(i, j);
				//Adiciona o azul da cor1
				corTransf1 = (int) ((cor1%256)*parcela1);
				corTransf1 = (int) ((cor1/256%256)*parcela1*256+corTransf1);
				corTransf1 = (int) ((cor1/65536%65536)*parcela1*65536+corTransf1);
				corTransf2 = (int) ((cor2%256)*parcela2);
				corTransf2 = (int) ((cor2/256%256)*parcela2*256+corTransf2);
				corTransf2 = (int) ((cor2/65536%65536)*parcela2*65536+corTransf2);
				corResult = corTransf1 + corTransf2;
				getTransformada().setRGB(i, j, corResult);
			}
		}	
		return getTransformada();
	}
	

}
