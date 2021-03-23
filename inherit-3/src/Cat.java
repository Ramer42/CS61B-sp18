public class Cat implements Comparable<Cat>{
    public String name;
    private int weight;

    public Cat(String n, int w) {
        name = n;
        weight = w;
    }

    @Override
    public int compareTo(Cat anotherCat) {
        return this.weight - anotherCat.weight;
    }
}
