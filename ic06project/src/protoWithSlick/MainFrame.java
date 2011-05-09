package protoWithSlick;

import javax.swing.JFrame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

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
		//MainPanel mp = new MainPanel();
		//this.add(mp);
	}
	
	public static void main(String[] args)
			throws SlickException
    {
		AppGameContainer app = new AppGameContainer(new MainPanel());
		app.setDisplayMode(800, 600, false);
		app.start();
		app.setShowFPS(false);
	}
}
