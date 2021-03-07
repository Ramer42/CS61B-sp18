public class NBody{
	public static double readRadius(String file){
		In in = new In(file);
		in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Planet[] readPlanets(String file){
		In in = new In(file);
		int planet_num = in.readInt();
		Planet[] planets = new Planet[planet_num];
		in.readDouble();
		double xxPos;
	    double yyPos;
	    double xxVel;
	    double yyVel;
	    double mass;
	    String imgFileName;
		for (int i = 0; i < planet_num; i++){
			xxPos = in.readDouble();
			yyPos = in.readDouble();
			xxVel = in.readDouble();
			yyVel = in.readDouble();
			mass = in.readDouble();
			imgFileName = in.readString();
			planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
		}
		return planets;
	}

	public static void main(String[] args){
		double T;
		double dt;
		String filename;
		T = Double.parseDouble(args[0]);
		dt = Double.parseDouble(args[1]);
		filename = args[2];
		double radius = readRadius(filename);
		Planet[] planets = readPlanets(filename);

		StdDraw.setScale(-radius, radius);
		StdDraw.clear();
		StdDraw.picture(0, 0, "images/starfield.jpg");

		for (Planet p : planets){
			p.draw();
		}

		StdDraw.enableDoubleBuffering();

		double t = 0;
		while (t < T){
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];
			int i = 0;
			for (Planet p : planets){
			    xForces[i] = p.calcNetForceExertedByX(planets);
			    yForces[i] = p.calcNetForceExertedByY(planets);
			    i = i + 1;
		    }
		    i = 0;
		    for (Planet p : planets){
			    p.update(dt, xForces[i], yForces[i]);
			    i = i + 1;
		    }
		    StdDraw.picture(0, 0, "images/starfield.jpg");
		    for (Planet p : planets){
			    p.draw();
		    }
		    StdDraw.show();
		    StdDraw.pause(10);
		    t = t + dt;
		}

		StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
	}
}