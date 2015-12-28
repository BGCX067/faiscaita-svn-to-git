package core.transformador.mesh;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class Reta {

	private int xi,yi,xf,yf;
	private double dx,dy;
	private List<Point> pontos = new ArrayList<Point>();

	public List<Point> getPontos() {
		return pontos;
	}

	public int getXi() {
		return xi;
	}

	public int getYi() {
		return yi;
	}

	public int getXf() {
		return xf;
	}

	public int getYf() {
		return yf;
	}

	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}
	
	public Reta(int xi, int yi, int xf, int yf){
		this.xi = xi;
		this.xf = xf;
		this.yi = yi;
		this.yf = yf;
		dx = xf-xi; //delta x em Bresenham
		dy = yf-yi;	//delta y em Bresenham	
	}

	public Reta(Point ponto1, Point ponto2){
		xi = ponto1.x;
		yi = ponto1.y;
		xf = ponto2.x;
		yf = ponto2.y;
		dx = xf-xi; //delta x em Bresenham
		dy = yf-yi;	//delta y em Bresenham	
	}
	
	//Usando Bresenham
	public void tracarReta() {
		int y0,y1,x1,x0, aux, deltax, deltay, erro, passoy, y,x;
		boolean ymaior;
		pontos.clear();
		
		y0=this.yi;
		y1=this.yf;
		x0=this.xi;
		x1=this.xf;

		ymaior = Math.abs(y1 - y0) > Math.abs(x1 - x0);

		if (ymaior){ // reflete a reta caso o passo em y seja maior
			aux = x0;
			x0 = y0;
			y0 = aux;
			aux = x1;
			x1 = y1;
			y1 = aux;
		}
		if (x0 > x1){ //inverte o termo inicial, caso o ponto final esteja a esquerda
			aux = x0;
			x0 = x1;
			x1 = aux;
			aux = y0;
			y0 = y1;
			y1 = aux;
		}
		deltax = x1 - x0;
		deltay = Math.abs(y1 - y0);
		erro = deltax / 2;		
		y = y0;
		if (y0 < y1)
			passoy = 1;
		else 
			passoy = -1;
		for(x=x0;x<=x1;x++){
			if (ymaior) 
				pontos.add(new Point(y,x));
			else 
				pontos.add(new Point(x,y));
			erro = erro - deltay;
			if (erro < 0){
				y = y + passoy;
				erro = erro + deltax;
			}
		}
		if(pontos.get(0).equals(new Point(xf,yf))){
			List<Point> auxList = new ArrayList<Point>();
			ListIterator<Point> iterator = pontos.listIterator(pontos.size());
			while(iterator.hasPrevious()){
				auxList.add(iterator.previous());
			}
			pontos = auxList;
		}
	}	

}
