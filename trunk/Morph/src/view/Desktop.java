package view;

import java.awt.Color;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.MouseInputAdapter;

import core.transformador.Persiana;
import core.transformador.PixelAleatorio;
import core.transformador.Transformador;
import core.transformador.TransformadorAlfa;
import core.transformador.TransformadorCores;
import core.transformador.mesh.TransformadorMesh;



public class Desktop extends JDesktopPane{

	Imagem imagem1, imagem2, imagem3;
	private final int DIM_BUFFER = 400, DIM_EXIBICAO = 200;
	private final double RAZAO_PORTFOLIO = 2;
	private final int CORES = 0, ALEATORIO = 1, PERSIANA =2, DISSOLVE =3, MESH =4;
	private final JButton iniciar = new JButton("Iniciar transformação");
	private final JButton buscarImagem1 = new JButton("Buscar Imagem 1");
	private final JButton buscarImagem2 = new JButton("Buscar Imagem 2");
	private final JButton limparPontos = new JButton("Limpar pontos");
	private final JTextField nIteracoes = new JTextField("10");
	private final JTextField nPontosX = new JTextField("0");
	private final JTextField nPontosY = new JTextField("0");
	private final List listaTransformadores = new List(4, false);

	public Desktop(){
		inicializarControles();				
	}

	private void inicializarControles() {		
		iniciar.setBounds(300, 425, 200, 30);
		iniciar.addActionListener(new ActionListener(){	
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciarMorph();				
			}
		});
		iniciar.setEnabled(false);
		this.add(iniciar);		

		final JFileChooser fc = new JFileChooser();	

		buscarImagem1.setBounds(300, 455, 200, 30);
		buscarImagem1.addActionListener(new ActionListener(){
			String imagem1;
			@Override
			public void actionPerformed(ActionEvent e) {							
				int returnVal = fc.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					imagem1 = fc.getSelectedFile().getPath();
					exibirImagem1(imagem1);			
				}			    
			}
		});	

		buscarImagem2.setBounds(300, 485, 200, 30);
		buscarImagem2.addActionListener(new ActionListener(){
			String imagem2;
			@Override
			public void actionPerformed(ActionEvent e) {							
				int returnVal = fc.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					imagem2 = fc.getSelectedFile().getPath();
					exibirImagem2(imagem2);			
				}			    
			}
		});

		limparPontos.setBounds(300, 515, 200, 30);
		limparPontos.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {							
				if(imagem1 != null){
					imagem1.getMesh().clear();
					imagem1.repaint();
				}
				if(imagem2 != null){
					imagem2.getMesh().clear();
					imagem2.repaint();
				}
			}
		});

		JLabel iteracoes = new JLabel("Número de Iterações:");
		iteracoes.setBounds(500,425,200,20);
		nIteracoes.setBounds(500, 445, 100, 20);
		nIteracoes.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				nIteracoes.selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {				
			}			
		});

		JLabel pontosX = new JLabel("Pontos em X:");
		pontosX.setBounds(500,465,100,20);
		nPontosX.setBounds(500, 485, 100, 20);
		nPontosX.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				nPontosX.selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {				
			}			
		});

		JLabel pontosY = new JLabel("Pontos em Y:");
		pontosY.setBounds(500,505,100,20);
		nPontosY.setBounds(500, 525, 100, 20);
		nPontosY.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				nPontosY.selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {				
			}			
		});

		listaTransformadores.setBounds(200,425,100,120);
		listaTransformadores.add("Cores",CORES);
		listaTransformadores.add("Aleatório",ALEATORIO);
		listaTransformadores.add("Persiana",PERSIANA);
		listaTransformadores.add("Cross-Dissolve",DISSOLVE);
		listaTransformadores.add("Mesh",MESH);
		listaTransformadores.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent event) {
				Boolean habilitar;
				if(listaTransformadores.getSelectedIndex() == MESH)
					habilitar =true;
				else
					habilitar =false;
				getLimparPontos().setEnabled(habilitar);
				getNPontosX().setEnabled(habilitar);
				getNPontosY().setEnabled(habilitar);
			}
			
		});
		listaTransformadores.select(MESH);

		this.add(listaTransformadores);
		this.add(nIteracoes);
		this.add(nPontosX);
		this.add(nPontosY);
		this.add(buscarImagem1);
		this.add(buscarImagem2);
		this.add(limparPontos);
		this.add(iteracoes);
		this.add(pontosX);
		this.add(pontosY);
		this.setBackground(Color.LIGHT_GRAY);
		this.repaint();
	}

	public JButton getLimparPontos() {
		return limparPontos;
	}

	private void iniciarMorph(){
		try {
			if(imagem1 != null && imagem2 != null){
				if(imagem3 != null)
					this.remove(imagem3);			
				imagem3 = new Imagem(DIM_EXIBICAO*2, DIM_EXIBICAO*2);
				imagem3.setBounds(220, 10, DIM_EXIBICAO*2, DIM_EXIBICAO*2);
				imagem3.setImage(imagem1.getImage());
				imagem3.setEscala((double) DIM_BUFFER/ (double)DIM_EXIBICAO/RAZAO_PORTFOLIO);
				this.add(imagem3);
				int numIteracoes = Integer.decode(getNIteracoes().getText());
				Transformador transformador = transformadorSelcionado();
				if(transformador != null){
					Animacao animacao = new Animacao(this,imagem3, numIteracoes,transformador);
					animacao.start();
				}
			}
		} catch (IndexOutOfBoundsException e) {
			Alerta alerta = new Alerta();
			alerta.mensagemErro();
			e.printStackTrace();
		}
	}

	private Transformador transformadorSelcionado() {
		Transformador transformador;
		switch (getListaTransformadores().getSelectedIndex()) {
		case MESH:
			int numPontosX, numPontosY;
			numPontosX = Integer.decode(getNPontosX().getText());
			numPontosY = Integer.decode(getNPontosY().getText());			
			return transformador = new TransformadorMesh(
					imagem1, imagem2, imagem3, numPontosX, numPontosY);		
		case CORES:
			return new TransformadorCores(imagem1.getImage(), imagem2.getImage());
		case ALEATORIO:
			return new PixelAleatorio(imagem1.getImage(), imagem2.getImage());
		case PERSIANA:
			return new Persiana(imagem1.getImage(), imagem2.getImage());
		case DISSOLVE:
			return new TransformadorAlfa(imagem1.getImage(), imagem2.getImage());
		}	
		return null;
	}

	public void exibirImagem1(String fileName){
		if(imagem1 != null){
			this.remove(imagem1);
		}
		imagem1 = new Imagem(fileName,DIM_BUFFER , DIM_BUFFER);
		imagem1.setLargura(DIM_EXIBICAO);
		imagem1.setAltura(DIM_EXIBICAO);
		imagem1.setEscala((double)DIM_BUFFER/(double)DIM_EXIBICAO);
		imagem1.setBounds(10, 100, DIM_EXIBICAO, DIM_EXIBICAO);
		this.add(imagem1);
		if(imagem2 != null){
			iniciar.setEnabled(true);
		}
		this.repaint();	
	}

	public void exibirImagem2(String fileName){
		if(imagem2 != null){
			this.remove(imagem2);
		}
		imagem2 = new Imagem(fileName,DIM_BUFFER , DIM_BUFFER);
		imagem2.setLargura(DIM_EXIBICAO);
		imagem2.setAltura(DIM_EXIBICAO);
		imagem2.setEscala((double)DIM_BUFFER/(double)DIM_EXIBICAO);
		imagem2.setBounds(630, 100, DIM_EXIBICAO, DIM_EXIBICAO);
		this.add(imagem2);
		if(imagem1 != null){
			iniciar.setEnabled(true);
		}
		this.repaint();	
	}

	public JButton getIniciar() {
		return iniciar;
	}

	public JTextField getNIteracoes() {
		return nIteracoes;
	}

	public JTextField getNPontosX() {
		return nPontosX;
	}

	public JTextField getNPontosY() {
		return nPontosY;
	}

	public List getListaTransformadores() {
		return listaTransformadores;
	}

}
