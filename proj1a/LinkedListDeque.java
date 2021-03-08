public class LinkedListDeque<LochNess> {
    private class StuffNode {
        public LochNess item;
        public StuffNode next;
        public StuffNode prev;

        public StuffNode(LochNess i, StuffNode n, StuffNode p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    private StuffNode sentinel;
    private int size;
    public LinkedListDeque(){
        sentinel = new StuffNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public LinkedListDeque(LochNess x) {
        sentinel = new StuffNode(null, null, null);
        sentinel.next = new StuffNode(x, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    public void addFirst(LochNess x) {
        sentinel.next.prev = new StuffNode(x, sentinel.next, sentinel);
        sentinel.next = sentinel.next.prev;
        size += 1;
    }

    public void addLast(LochNess x) {
        sentinel.prev.next = new StuffNode(x, sentinel, sentinel.prev);
        sentinel.prev = sentinel.prev.next;
        size += 1;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        if (size == 0){
            return true;
        } else {
            return false;
        }
    }

    public void printDeque(){
        StuffNode l = sentinel.next;
        while (l.next.item != null){
            System.out.print(l.item + " ");
            l = l.next;
        }
        System.out.println(l.item);
    }

    public LochNess removeFirst(){
        if (sentinel.next == null){
            return null;
        }
        LochNess it = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel = sentinel.next.prev;
        size -= 1;
        return it;
    }

    public LochNess removeLast(){
        if (sentinel.next == null){
            return null;
        }
        LochNess it = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel = sentinel.prev.next;
        size -= 1;
        return it;
    }

    public LochNess get(int index){
        if (index > size -1){
            return null;
        }
        StuffNode p = sentinel;
        for (int i = 0; i <= index; i++){
            p = p.next;
        }
        return p.item;
    }

    public static void main(String[] args) {
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>("ws");
        lld1.addFirst("dd");
        lld1.addLast("ff");
        lld1.addLast("jj");
        lld1.printDeque();
        lld1.removeFirst();
        lld1.printDeque();
        System.out.println(lld1.size());
        LinkedListDeque<Integer> lld2 = new LinkedListDeque<Integer>();
        System.out.println(lld2.isEmpty());
        lld2.addFirst(10);
        System.out.println(lld2.isEmpty());
        lld1.removeFirst();
        System.out.println(lld2.isEmpty());
    }

}
