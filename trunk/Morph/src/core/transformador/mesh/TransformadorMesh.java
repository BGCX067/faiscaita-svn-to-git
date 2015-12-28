package core.transformador.mesh;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import sun.security.action.GetLongAction;
import view.Imagem;
import view.Quadrilatero;
import core.transformador.Transformador;
import core.transformador.TransformadorAlfa;

public class TransformadorMesh extends Transformador{

	private Imagem painel1, painel2, painelTransformada;
	private BufferedImage warped1, warped2;
	private List<Quadrilatero> quads;
	private TransformadorAlfa talfa;

	public TransformadorMesh(Imagem imagem1, Imagem imagem2, Imagem transformada,
			int nPontosX, int nPontosY){
		super(imagem1.getImage(),imagem2.getImage());
		painel1 = imagem1;
		painel2 = imagem2;
		if(imagem1.getMesh().size() == 0)
			imagem1.setMesh(gerarNovoMesh(nPontosX,nPontosY, imagem1.getMesh()));
		else
			if(imagem1.getMesh().get(0).x != 0 && imagem1.getMesh().get(0).y != 0)
				imagem1.setMesh(gerarNovoMesh(nPontosX,nPontosY, imagem1.getMesh()));
		if(imagem2.getMesh().size() == 0)
			imagem2.setMesh(gerarNovoMesh(nPontosX,nPontosY, imagem1.getMesh()));
		else
			if(imagem2.getMesh().get(0).x != 0 && imagem2.getMesh().get(0).y != 0)
				imagem2.setMesh(gerarNovoMesh(nPontosX,nPontosY, imagem2.getMesh()));
		quads = gerarQuadrilateros(nPontosX, nPontosY);
		painelTransformada = transformada;
		warped1 = new BufferedImage(transformada.getImage().getWidth(), transformada.getImage().getHeight(), BufferedImage.TYPE_INT_ARGB);
		warped2 = new BufferedImage(transformada.getImage().getWidth(), transformada.getImage().getHeight(), BufferedImage.TYPE_INT_ARGB);
		talfa = new TransformadorAlfa(warped1,warped2);
	}

	@Override
	public BufferedImage obterImagemTransformada(double passo) throws IndexOutOfBoundsException{
		interpolarMesh(passo);
		for(Quadrilatero quad : quads){
			if(quad.getP1() == 32){
				quad.getP1();
			}
			warpPoligonos(quad.getP3(),quad.getP4(),quad.getP1(),quad.getP2());
		}
		talfa.setImage1(warped1);
		talfa.setImage2(warped2);
		return talfa.obterImagemTransformada(passo);
	}

	private void interpolarMesh(double passo) {
		int i, x, y;
		List<Point> meshTransformada = getPainelTransformada().getMesh();
		List<Point> mesh1 = getPainel1().getMesh();
		List<Point> mesh2 = getPainel2().getMesh();

		meshTransformada.clear();
		for(i=0; i < mesh1.size(); i++){
			x = (int) ((mesh1.get(i).x*(1-passo) + mesh2.get(i).x*passo));
			y = (int) ((mesh1.get(i).y*(1-passo) + mesh2.get(i).y*passo));
			meshTransformada.add(new Point(x,y));
		}

	}	


	private void  warpPoligonos(int p1, int p2, int p3, int p4){
		Reta uMaior, uMenor;
		Point ponto1, ponto2, ponto3, ponto4;
		ponto1 = getPainelTransformada().getMesh().get(p1);
		ponto2 = getPainelTransformada().getMesh().get(p2);
		ponto3 = getPainelTransformada().getMesh().get(p3);
		ponto4 = getPainelTransformada().getMesh().get(p4);
		uMaior = new Reta(ponto1,ponto3);
		uMaior.tracarReta();
		uMenor = new Reta(ponto2,ponto4);
		uMenor.tracarReta();
		if(uMaior.getPontos().size() < uMenor.getPontos().size()){
			Reta aux = uMaior;
			uMaior = uMenor;
			uMenor = aux;
		}
		Point inicialMenor = uMenor.getPontos().get(0);
		Point inicialMaior = uMaior.getPontos().get(0);
		Point finalMenor = uMenor.getPontos().get(uMenor.getPontos().size() -1);
		Point finalMaior = uMaior.getPontos().get(uMaior.getPontos().size() -1);
		double comprimentoMaior = inicialMaior.distance(finalMaior);
		double comprimentoMenor = inicialMenor.distance(finalMenor);
		double u, v;
		Reta retaV;
		int pixel;
		ListIterator<Point> pMenorIterator = uMenor.getPontos().listIterator();
		Point pMenor = pMenorIterator.next();		
		for(Point pMaior : uMaior.getPontos()){
			u = pMaior.distance(inicialMaior)/comprimentoMaior;
			if(pMenorIterator.hasNext())
				if(Math.abs(pMenor.distance(inicialMenor)/comprimentoMenor - u) > 
				Math.abs(pMenorIterator.next().distance(inicialMenor)/comprimentoMenor - u)){
					pMenorIterator.previous();
					pMenor = pMenorIterator.next();
				}
				else
					pMenorIterator.previous();
			retaV = new Reta(pMaior, pMenor);
			retaV.tracarReta();
			for(Point p : retaV.getPontos()){
				if(inicialMaior.equals(ponto1))
					v=p.distance(pMaior)/pMaior.distance(pMenor);
				else
					v=p.distance(pMenor)/pMenor.distance(pMaior);
				pixel = getPixelImage(u, v, p1, p2, p3, p4, getPainel1());
				warped1.setRGB(p.x, p.y, pixel);
				pixel = getPixelImage(u, v, p1, p2, p3, p4, getPainel2());
				warped2.setRGB(p.x, p.y, pixel);
			}			
		}
	}

	private List<Point> gerarNovoMesh(int x, int y, List<Point> mesh){
		List<Point> novoMesh = new ArrayList<Point>();
		Point p;
		int altura = getTransformada().getHeight();
		int largura = getTransformada().getWidth();
		Iterator<Point> iterator = mesh.iterator();
		int i,j;

		p = new Point(0, 0);
		novoMesh.add(p);
		for(i = 0; i < x; i++){
			p = new Point(mesh.get(i).x,0);
			novoMesh.add(p);
		}
		p = new Point(largura-1,0);
		novoMesh.add(p);
		for(i=0;i<y;i++){
			p = new Point(0,(int) mesh.get(i*x).getY());
			novoMesh.add(p);
			for(j=0;j<x;j++){
				novoMesh.add(iterator.next());
			}
			p = new Point(largura - 1,(int) mesh.get((i+1)*x-1).getY());
			novoMesh.add(p);
		}
		p = new Point(0, altura - 1);
		novoMesh.add(p);
		for(i = 0; i < x; i++){
			p = new Point(mesh.get(x*(y-1) + i).x,altura - 1);
			novoMesh.add(p);
		}
		p = new Point(largura-1,altura-1);
		novoMesh.add(p);		
		return novoMesh;
	}

	private List<Quadrilatero> gerarQuadrilateros(int x,int y){
		int xNovo = x+2;
		int yNovo = y+2;
		Quadrilatero q;
		List<Quadrilatero> quads = new ArrayList<Quadrilatero>();

		for(int j=0;j<yNovo-1;j++){
			for(int i=0; i<xNovo-1;i++){
				q = new Quadrilatero(xNovo+i+j*xNovo,xNovo+i+1+j*xNovo,i+j*xNovo,i+1+j*xNovo);	
				quads.add(q);
			}
		}
		return quads;
	}

	private int getPixelImage(double u, double v, int p1, int p2, int p3, int p4, Imagem imagem){
		Point pto1;
		Point pto2;
		Point pto3;
		Point pto4;
		if(imagem.equals(painel1)){		
			pto1 = painel1.getMesh().get(p1);
			pto2 = painel1.getMesh().get(p2);
			pto3 = painel1.getMesh().get(p3);
			pto4 = painel1.getMesh().get(p4);
		}else{
			pto1 = painel2.getMesh().get(p1);
			pto2 = painel2.getMesh().get(p2);
			pto3 = painel2.getMesh().get(p3);
			pto4 = painel2.getMesh().get(p4);
		}
		Point aux1 = new Point((int) (pto1.x+(pto3.x-pto1.x)*u), (int) (pto1.y+(pto3.y-pto1.y)*u));		
		Point aux2 = new Point((int) (pto2.x+(pto4.x-pto2.x)*u), (int) (pto2.y+(pto4.y-pto2.y)*u));
		Point ponto = new Point((int) (aux1.x+(aux2.x-aux1.x)*v),(int) (aux1.y+(aux2.y-aux1.y)*v));
		if(imagem.equals(painel1))
			return painel1.getImage().getRGB(ponto.x,ponto.y);
		else 
			return painel2.getImage().getRGB(ponto.x,ponto.y);
	}

	public Imagem getPainel1() {
		return painel1;
	}

	public Imagem getPainel2() {
		return painel2;
	}

	public Imagem getPainelTransformada() {
		return painelTransformada;
	}

	public void setPainel1(Imagem painel1) {
		this.painel1 = painel1;
	}

	public void setPainel2(Imagem painel2) {
		this.painel2 = painel2;
	}

}
