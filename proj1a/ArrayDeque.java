public class ArrayDeque<Stuff> {
    private int size = 0;
    private Stuff[] items;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque(){
        items = (Stuff[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
    }

    public ArrayDeque(Stuff x){
        items = (Stuff[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
        addFirst(x);
    }

    private void resize(int capacity){
        Stuff[] a = (Stuff[]) new Object[capacity];
        int front_index = plusOne(nextFirst);
        int back_index = minusOne(nextLast);
        if (front_index < back_index){
            System.arraycopy(items, front_index, a, 0, size);
        } else{
            System.arraycopy(items, front_index, a, 0, items.length - front_index);
            System.arraycopy(items, 0, a, items.length - front_index, back_index + 1);
        }
        nextFirst = a.length - 1;
        nextLast = size;
        items = a;
    }

    public void addFirst(Stuff item){
        if (nextFirst == nextLast){
            resize(items.length * 2);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    public void addLast(Stuff item){
        if (nextFirst == nextLast){
            resize(items.length * 2);
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    public boolean isEmpty(){
        if (size == 0){
            return true;
        } else {
            return false;
        }
    }

    public int size(){
        return size;
    }

    public Stuff removeLast(){
        nextLast = minusOne(nextLast);
        Stuff return_item = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        if ((float)size / items.length < 0.25 && items.length > 16){
            resize(items.length / 2);
        }
        return return_item;
    }

    public Stuff removeFirst(){
        nextFirst = plusOne(nextFirst);
        Stuff return_item = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        if ((float)size / items.length < 0.25 && items.length > 16){
            resize(items.length / 2);
        }
        return return_item;
    }

    private int minusOne(int index){
        index -= 1;
        if (index < 0){
            return items.length - 1;
        }
        return index;
    }

    private int plusOne(int index){
        index += 1;
        if (index > items.length - 1){
            return 0;
        }
        return index;
    }

    public void printDeque(){
        int i = plusOne(nextFirst);
        while (items[plusOne(i)] != null){
            System.out.print(items[i] + " ");
            i = plusOne(i);
        }
        System.out.println(items[i]);
    }

    public Stuff get(int index){
        int i = plusOne(nextFirst);
        if (i + index > items.length - 1){
            return items[i + index - items.length];
        }
        return items[i + index];
    }

    public int item_length(){
        return items.length;
    }

    public static void main(String[] args){
//        ArrayDeque<Integer> aq1 = new ArrayDeque<Integer>(13);
//        aq1.printDeque();
//        aq1.addFirst(45);
//        aq1.addFirst(55);
//        aq1.addLast(66);
//        for (int i = 0; i < 4; i++){
//            aq1.addLast(66);
//        }
//        aq1.printDeque();

//        ArrayDeque<Integer> aq1 = new ArrayDeque<Integer>(13);
//        aq1.addLast(45);
//        aq1.addLast(55);
//        aq1.addLast(99);
//        for (int i = 0; i < 3; i++){
//            aq1.addFirst(66);
//        }
//        aq1.addFirst(77);
//        aq1.printDeque();
//        System.out.println(aq1.get(7));
//        for (int i = 0; i < 3; i++){
//            aq1.removeFirst();
//        }
//        aq1.printDeque();
//        aq1.removeLast();
//        aq1.printDeque();
//        aq1.removeLast();
//        aq1.printDeque();

        ArrayDeque<Integer> aq1 = new ArrayDeque<Integer>(13);
        for (int i = 0; i < 40; i++){
            aq1.addFirst(i);
        }
        for (int i = 0; i < 38; i++){
            aq1.removeLast();
        }
        aq1.printDeque();
        System.out.println(aq1.item_length());
    }
}
