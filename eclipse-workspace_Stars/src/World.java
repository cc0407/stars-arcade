import java.util.ArrayList;
import java.util.Random;

public class World {
	private ArrayList<Meteor> meteors = new ArrayList<Meteor>();
	public Random rand = new Random();
	public final int firstBossScore = 5000;
	private int x;
	private int y;
	public int spawnAmt = 2;
	private int score;
	public int[][] particles = new int[1500][6];
	public int particleCount = 0;
	// 0 = sizex
	// 1 = sizey
	// 2 = x
	// 3 = y
	// 4 = speed
	// 5 = opacity

	public Main m;
	public Collisions col;

	
	public World(Main m) {
		this.m = m;
		this.col = new Collisions(m);
		
		for (int i = 0; i < 600; i++) {
			particles[particleCount][0] = 14 * rand.nextInt(5);
			particles[particleCount][1] = 2 * rand.nextInt(4);
			particles[particleCount][2] = rand.nextInt(1000) + m.f.WIDTH;
			particles[particleCount][3] = rand.nextInt(m.f.HEIGHT);
			particles[particleCount][4] = rand.nextInt(19) + 1;
			particles[particleCount][5] = rand.nextInt(50) + 100;
			particleCount++;
		}

	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int increaseScore(int score) {
		this.score += score;
		return this.score;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void reset(){
		Sound.clearAll();
		this.clearMeteors();
		this.setScore(0);
		this.spawnAmt = 2;
		
	}
	
	public void spawnMeteor() {
		for(int i = 0; i < spawnAmt; i ++)
		{
			x = rand.nextInt(2000) + m.f.WIDTH;
			y =  rand.nextInt(m.f.HEIGHT/2);
			meteors.add(new Meteor(x, y, m.f.jp.percentY(6), m.f.jp.percentY(6), m.f.jp.percentY(0.2)));
			
			y =  rand.nextInt(m.f.HEIGHT/2) + m.f.HEIGHT/2 - 160;
			meteors.add(new Meteor(x, y, m.f.jp.percentY(6), m.f.jp.percentY(6), m.f.jp.percentY(0.2)));
		}
	}
	
	public void clearMeteors() {
		this.meteors.clear();
	}
	
	
	public void updateMeteors() {
		for(Meteor meteor : getMeteors()) {

			if (meteor.getX() + meteor.getWidth() > 0)
				meteor.move();
			else
			{
				this.removeMeteor(meteor);
				m.ship.changeHealth(-10);
			}
		}
	}
	
	public ArrayList<Meteor> getMeteors() {
		return new ArrayList<>(meteors);
	}
	
	public void removeMeteor(Meteor m) {
		this.meteors.remove(m);
	}
	
	public void updateBackground() {
		for (int i = 0; i < 600; i++) {
			particles[i][2] -= particles[i][4];
			if (particles[i][2] < -100) {

				if (i <= 400) {
					particles[i][2] = rand.nextInt(500) + m.f.WIDTH;
					particles[i][3] = rand.nextInt(m.f.HEIGHT);
					particles[i][4] = rand.nextInt(10)+1;
				} else {
					particles[i][2] = rand.nextInt(1000) + m.f.WIDTH;
					particles[i][3] = rand.nextInt(m.f.HEIGHT);
					particles[i][4] = rand.nextInt(10) + 11;
				}
			}
		}
	}

}
