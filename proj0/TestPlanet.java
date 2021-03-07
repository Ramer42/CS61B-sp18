public class TestPlanet{
	public static void main(String[] args){
		Planet sun = new Planet(0, 1, 0, 3, 20, "Sun.gif");
		Planet earth = new Planet(4, 6, 1, 1, 5, "Earth.gif");
		System.out.println(earth.calcForceExertedByX(sun));
		Planet[] allPlanets = {sun, earth};
		System.out.println(earth.calcNetForceExertedByX(allPlanets));
	}
}