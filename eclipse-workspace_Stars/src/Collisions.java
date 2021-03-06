import java.util.ArrayList;

public class Collisions {
	
	public Main m;
	
	public Collisions(Main m) {
		this.m = m;
	}

	public void Check() {
		
		ArrayList<Missile> ms = m.ship.getMissiles();
		ArrayList<Meteor> mt = m.world.getMeteors();
		
		for(Missile missile : ms) {
			for(Meteor mm : mt) {
				if(Skill.get("MEGA").isActive() && missile.hitbox.intersects(mm.hitbox) && missile.isFollowing())
				{
					m.world.removeMeteor(mm);
					m.world.increaseScore(100);
					
				}
				else if(missile.hitbox.intersects(mm.hitbox))
				{

					try
					{
						m.ship.removeMissile(missile);
						m.world.removeMeteor(mm);
						m.world.increaseScore(100);
						if(missile.getType().equals(type.mine)) {
							m.ship.newMine(missile.getWidth() / 2, missile.getHeight() / 2, missile.getX(), missile.getY());
						}
					}
					catch(IndexOutOfBoundsException g)
					{
						g.printStackTrace();
					}
				}
			}
			
		}
		if(m.ship.alive) {
			for(Meteor mm : mt) {
				if(m.ship.alive && m.ship.intersects(mm.hitbox))
				{
					m.world.removeMeteor(mm);
					
					if(!Skill.get("SHIELD").isActive())
						m.ship.changeHealth(-10);
					else
						m.world.increaseScore(100);
					
				}
			}
		}

	}
}
