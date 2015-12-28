package view;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.JOptionPane;

import core.transformador.Transformador;
import core.transformador.mesh.TransformadorMesh;

public class Animacao extends Thread{
	
	private Imagem imagem3;
	private Desktop desktop;
	private Transformador transformador;
	private int nIteracoes;
	
	public Animacao(Desktop desktop, Imagem imagem3, int iteracoes,
			Transformador transformador) {
		super();
		this.desktop = desktop;
		this.imagem3 = imagem3;
		nIteracoes = iteracoes;
		this.transformador = transformador;
	}
	public void run(){
		try {
			double i;
			desktop.getIniciar().setEnabled(false);
			for(i=0; i<=getNIteracoes(); i++){
				System.out.println(i/nIteracoes);
				getImagem3().setImage(getTransformador().obterImagemTransformada(i/nIteracoes));
				getImagem3().repaint();
			}
		} catch (IndexOutOfBoundsException e) {
			Alerta alerta = new Alerta();
			alerta.mensagemErro();
			e.printStackTrace();
		}
		desktop.getIniciar().setEnabled(true);			
	}

	public Imagem getImagem3() {
		return imagem3;
	}

	public Desktop getDesktop() {
		return desktop;
	}

	public Transformador getTransformador() {
		return transformador;
	}

	public int getNIteracoes() {
		return nIteracoes;
	}

	public void setImagem3(Imagem imagem3) {
		this.imagem3 = imagem3;
	}

	public void setDesktop(Desktop desktop) {
		this.desktop = desktop;
	}

	public void setTransformador(Transformador transformador) {
		this.transformador = transformador;
	}

	public void setNIteracoes(int iteracoes) {
		nIteracoes = iteracoes;
	}

}
