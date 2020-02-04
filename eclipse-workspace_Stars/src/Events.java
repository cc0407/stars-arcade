import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Events{
	private Main m;
	public ActionListener play;
	public Events(Main m) {
		this.m = m;
		
		play =new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	m.f.initGame();
		    }
		};
		
		
	}
}
