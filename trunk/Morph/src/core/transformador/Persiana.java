package core.transformador;

import java.awt.image.BufferedImage;

public class Persiana extends Transformador{

	private final int npersianas = 40;
	
	public Persiana(BufferedImage image1, BufferedImage image2) {
		super(image1, image2);
	}

	@Override
	public BufferedImage obterImagemTransformada(double passo) {
		int tampersiana = (getImage2().getWidth()/npersianas);
		int parcela2 = (int)(passo*tampersiana);
		int i;
		int[] persiana;
		for(i=0;i<getImage2().getWidth();i++){
			if(i%tampersiana < parcela2)
				persiana = getImage2().getRGB(i, 0, 1, getImage2().getHeight(), null, 0, 1);
			else
				persiana = getImage1().getRGB(i, 0, 1, getImage2().getHeight(), null, 0, 1);			
			getTransformada().setRGB(i,	0, 1, getImage2().getHeight(), persiana, 0, 1);
		}		
		return getTransformada();
	}
	
	

}
