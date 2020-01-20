import java.util.ArrayList;
import java.util.Random;

public class World {
	private ArrayList<Meteor> meteors = new ArrayList<Meteor>();
	public Random rand = new Random();
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
	public Frame frame;
	public Collisions col;

	
	public World(Main m) {
		this.m = m;
		this.frame = m.f;
		m.ship = new Ship(m);
		this.col = new Collisions(m);
		
		for (int i = 0; i < 600; i++) {
			particles[particleCount][0] = 14 * rand.nextInt(5);
			particles[particleCount][1] = 2 * rand.nextInt(4);
			particles[particleCount][2] = rand.nextInt(1000) + frame.WIDTH;
			particles[particleCount][3] = rand.nextInt(frame.HEIGHT);
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
	
	
	
	public void spawnMeteor() {
		for(int i = 0; i < spawnAmt; i ++)
		{
			x = rand.nextInt(2000) + frame.WIDTH;
			y =  rand.nextInt(frame.HEIGHT/2);
			meteors.add(new Meteor(x, y));
			
			y =  rand.nextInt(frame.HEIGHT/2) + frame.HEIGHT/2 - 160;
			meteors.add(new Meteor(x, y));
		}
	}
	
	public void clearMeteors() {
		this.meteors.clear();
	}
	
	
	public void updateMeteors() {

		for (int i = 0; i < this.meteors.size(); i++) {

			Meteor meteor = this.meteors.get(i);

			if (meteor.getX() > -60)
				meteor.move();
			else
			{
				this.meteors.remove(i);
				m.ship.health-=10;
			}
		}
	}
	public ArrayList<Meteor> getMeteors() {
		return meteors;
	}
	
	public void updateBackground() {
		for (int i = 0; i < 600; i++) {
			particles[i][2] -= particles[i][4];
			if (particles[i][2] < -100) {

				if (i <= 400) {
					particles[i][2] = rand.nextInt(500) + frame.WIDTH;
					particles[i][3] = rand.nextInt(frame.HEIGHT);
					particles[i][4] = rand.nextInt(10)+1;
				} else {
					particles[i][2] = rand.nextInt(1000) + frame.WIDTH;
					particles[i][3] = rand.nextInt(frame.HEIGHT);
					particles[i][4] = rand.nextInt(10) + 11;
				}
			}
		}
	}

}
