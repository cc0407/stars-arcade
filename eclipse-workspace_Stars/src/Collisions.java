import java.util.ArrayList;

public class Collisions {
	
	public Main m;
	
	public Collisions(Main m) {
		this.m = m;
	}

	public void Check() {
		
		ArrayList<Missile> ms = m.ship.getMissiles();
		for (int i = 0; i < ms.size(); i++) {
			Missile missile = ms.get(i);
			
			ArrayList<Meteor> mt = m.world.getMeteors();
			for (int j = 0; j < mt.size(); j++) {
				
				Meteor mm = mt.get(j);
				if(missile.hitbox.intersects(mm.hitbox))
				{
					try
					{
						ms.remove(i);
					}
					catch(IndexOutOfBoundsException g)
					{
					}
					mt.remove(j);
					m.world.increaseScore(100);
				}
			}
		}
		
		if(m.ship.alive)
		{
			ArrayList<Meteor> mt = m.world.getMeteors();
			for (int i = 0; i < mt.size(); i++) {
				
				Meteor mm = mt.get(i);
				if(m.ship.hitbox.intersects(mm.hitbox))
				{
					mt.remove(i);
					
					if(!m.ship.shield.isActive)
						m.ship.health-=10;
					else
						m.world.increaseScore(100);
					
				}
			}
		}
		
		if(m.ship.mega.isActive())
		{
			ArrayList<Meteor> mt = m.world.getMeteors();
			for (int i = 0; i < mt.size(); i++) {
				
				Meteor mm = mt.get(i);
				if(m.ship.mega.intersects(mm.hitbox))
				{
					mt.remove(i);
					m.world.increaseScore(100);
					
				}
			}
		}
	}
}
