public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	public static final double G = 6.67e-11;
	public Planet(double xP, double yP, double xV,
              double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}
	public Planet(Planet p){
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p){
		double xxPos_2 = p.xxPos;
		double yyPos_2 = p.yyPos;
		double distance = Math.sqrt(Math.pow(xxPos - xxPos_2, 2) + Math.pow(yyPos - yyPos_2, 2));
		return distance;
	}

	public double calcForceExertedBy(Planet p){
		double distance = calcDistance(p);
		double force = G * p.mass * this.mass / Math.pow(distance, 2);
		return force;
	}

	public double calcForceExertedByX(Planet p){
		double distance = calcDistance(p);
		double force = calcForceExertedBy(p);
		double force_x = force * (p.xxPos - this.xxPos) / distance;
		return force_x;
	}

	public double calcForceExertedByY(Planet p){
		double distance = calcDistance(p);
		double force = calcForceExertedBy(p);
		double force_y = force * (p.yyPos - this.yyPos) / distance;
		return force_y;
	}

	public boolean equals(Planet p){
		boolean d = false;
        if (p.xxPos == this.xxPos && p.yyPos == this.yyPos){
        	d = true;
        }
		return d;
	}

	public double calcNetForceExertedByX(Planet[] ps){
		double force_x = 0;
		for (int i = 0; i < ps.length; i++){
			if (this.equals(ps[i])){
				continue;
			}
			force_x = force_x + this.calcForceExertedByX(ps[i]);
		}
		return force_x;
	}

	public double calcNetForceExertedByY(Planet[] ps){
		double force_y = 0;
		for (int i = 0; i < ps.length; i++){
			if (this.equals(ps[i])){
				continue;
			}
			force_y = force_y + this.calcForceExertedByY(ps[i]);
		}
		return force_y;
	}

	public void update(double dt, double fX, double fY){
		double a_x = fX / this.mass;
		double a_y = fY / this.mass;
		this.xxVel = this.xxVel + dt * a_x;
		this.yyVel = this.yyVel + dt * a_y;
		this.xxPos = this.xxPos + dt * this.xxVel;
		this.yyPos = this.yyPos + dt * this.yyVel;
	}

	public void draw(){
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}
}