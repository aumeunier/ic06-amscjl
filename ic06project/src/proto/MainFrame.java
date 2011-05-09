package proto;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame{
	final static int GAMELOCWIDTH = 200;
	final static int GAMELOCHEIGHT = 0;

	private MainFrame() {
		super("Nom de notre jeu");
		setLocation(GAMELOCWIDTH, GAMELOCHEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		//pack();
		setVisible(true);
		MainPanel mp = new MainPanel();
		this.add(mp);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
		});
	}
}
