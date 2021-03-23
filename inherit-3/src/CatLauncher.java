public class CatLauncher {
    public static void main(String[] args) {
        Cat c1 = new Cat("John", 15);
        Cat c2 = new Cat("Mike", 12);
        Cat c3 = new Cat("Franklin", 17);
        Cat[] cats = new Cat[]{c1, c2, c3};

        Cat c = (Cat) Maximizer.max(cats);
        System.out.println(c.name);
    }
}

