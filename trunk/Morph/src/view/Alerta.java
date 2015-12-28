package view;

import javax.swing.JOptionPane;

public class Alerta extends JOptionPane{

	public Alerta(){
		super();
		
	}
	
	public void mensagemErro(){
		super.showMessageDialog(null, "Houve um problema durante a animação.\n" +
				"Provavelmente foi causado por um erro na entrada dos pontos do mesh.\n" +
				"Veja se os pontos das figuras estão coerentes com os valores entrados para\n" +
				"X e Y. Se o problema persistir, tente limpar os pontos e começar novamente.",
				"Erro!",
				JOptionPane.ERROR_MESSAGE);
	}
	
}
