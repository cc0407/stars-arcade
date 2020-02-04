import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Events{
	private Main m;
	public final ActionListener play;
	public final ActionListener quit;
	public final ActionListener restart;
	public final ActionListener backtoMenu;
	public final ActionListener openEquip;
	public Events(Main m) {
		this.m = m;
		
		play =new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	m.f.initGame();
		    }
		};
		
		quit = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	System.exit(0);
		    }
		};
		
		restart = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	m.f.initMenu();
		    }
		};
		
		backtoMenu = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	m.f.showMain();
		    }
		};
		
		openEquip = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	m.f.showEquip();
		    }
		};
		
		
		
	}
}
