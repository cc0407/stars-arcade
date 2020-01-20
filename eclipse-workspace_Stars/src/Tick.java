
public class Tick {
	public int fireCooldown = 15, count, shieldCountdown = 300, shieldCooldown = 600, multiCountdown = 600, multiCooldown = 1200, boostCountdown = 600, boostCooldown = 1200,
			megaCountdown = 300, megaCooldown = 3200,
			flashCount = 0;
	public boolean healthShow = false;
	public Main m;
	public Frame frame;
	public Renderer r;
	
	public Tick(Main m) {
		this.m = m;
		this.frame = m.f;
	}
	public void update() {
		if(!m.f.paused)
		{
			count++;
			if (fireCooldown > 0)
				fireCooldown--;
	

			
			if(count == 155 && healthShow == false)
				healthShow = true;
			//if(count % 1 == 0 && m.ship.alive)
			if(count % 155 == 0 && m.ship.alive)
				m.world.spawnMeteor();
			if(count > 60000)
				count = 0;
			if(count % 2400 == 0)
				m.world.spawnAmt++;
			if(m.ship.health <= 0)
				m.ship.die();
			
			m.ship.move();
			
			if (m.ship.isFiring && m.ship.alive && !m.ship.mega.isActive()) {
				if (fireCooldown <= 0) {
					fireCooldown = 15;
					m.ship.fire();
					}
			}
			
			
			//TODO FIX ALL OF THIS
			m.ship.mega.timeLeft();
	
			m.ship.shield.timeLeft();
			
			m.ship.multi.timeLeft();
			
			if(m.ship.boost.timeLeft() == 0) {
				m.ship.speed = 10;
			}
			
			if(frame.jp.progress >= 500)
			{
				if (count % 20 == 0 && flashCount <= 4)
				{
					if(frame.jp.progressFlash)
						frame.jp.progressFlash = false;
					else
						frame.jp.progressFlash = true;
					flashCount++;
				}
				
			}
			
			
			m.world.updateBackground();
			m.ship.updateMissiles();
			m.world.updateMeteors();
			m.world.col.Check();
			frame.jp.repaint();
			
		}
	}
}
