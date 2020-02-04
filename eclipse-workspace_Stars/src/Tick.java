
public class Tick {
	public int fireCooldown = 15, count, flashCount = 0;
	public boolean healthShow = false;
	public Main m;
	public Frame frame;
	public Renderer r;
	
	public Tick(Main m) {
		this.m = m;
		this.frame = m.f;
	}
	public void update() {
		if(!m.f.paused && m.ship.alive)
		{
			count++;
			if (fireCooldown > 0)
				fireCooldown--;
	

			
			if(count == 155 && healthShow == false)
				healthShow = true;
//			if(count % 1 == 0)
			if(count % 155 == 0 && m.ship.alive)
				m.world.spawnMeteor();
			if(count == Integer.MAX_VALUE)
				count = 0;
			if(count % 2400 == 0)
				m.world.spawnAmt++;
			
			m.ship.move();
			
			if (m.ship.isFiring && !Skill.MEGA.isActive()) {
				if (fireCooldown <= 0) {
					fireCooldown = 15;
					m.ship.fire();
					}
			}
					
			
			if(frame.jp.progress >= 1)
			{
				if (count % 20 == 0 && flashCount <= 4)
				{
					frame.jp.progressFlash = !frame.jp.progressFlash;
					flashCount++;
				}
				
			}
			
			m.world.updateBackground();
			m.ship.advanceSkills();
			m.ship.updateMissiles();
			m.world.updateMeteors();
			m.world.col.Check();		
			
		}
		frame.repaint();
	}
}
