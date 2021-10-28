import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        // Your code here!
        Queue<Queue<Item>> singleItemQueues = new Queue<Queue<Item>>();
        for (Item i : items) {
            Queue<Item> thisSingleItemQueue = new Queue<Item>();
            thisSingleItemQueue.enqueue(i);
            singleItemQueues.enqueue(thisSingleItemQueue);
        }
        return singleItemQueues;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        if (q1.isEmpty()) {
            return q2;
        }
        if (q2.isEmpty()) {
            return q1;
        }
        Queue<Item> result = new Queue<Item>();
        while (true) {
            if (q1.peek().compareTo(q2.peek()) < 0) {
                result.enqueue(q1.dequeue());
            } else {
                result.enqueue(q2.dequeue());
            }
            if (q1.isEmpty()) {
                for (Item i : q2) {
                    result.enqueue(i);
                }
                break;
            }
            if (q2.isEmpty()) {
                for (Item j : q1) {
                    result.enqueue(j);
                }
                break;
            }
        }
        return result;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        // Create a deep copy of items Queue to avoid changing the original one
        Queue<Item> itemsCopy = new Queue<Item>();
        for (Item j : items) {
            itemsCopy.enqueue(j);
        }
        if (itemsCopy.size() == 1 || itemsCopy.isEmpty()) { return itemsCopy; }
        if (itemsCopy.size() == 2) {
            Item item1 = itemsCopy.dequeue();
            if (item1.compareTo(itemsCopy.peek()) < 0) {
                Item item2 = itemsCopy.dequeue();
                itemsCopy.enqueue(item1);
                itemsCopy.enqueue(item2);
            } else {
                itemsCopy.enqueue(item1);
            }
            return itemsCopy;
        }
        Queue<Item> firstHalf = new Queue<Item>();
        int itemsSize = itemsCopy.size();
        for (int i = 0; i <= itemsSize / 2 - 1; i++) {
            firstHalf.enqueue(itemsCopy.dequeue());
        }
//        System.out.println("firstHalf:");
//        for (Item i : firstHalf) {
//            System.out.println(i);
//        }
//        System.out.println("itemsCopy:");
//        for (Item j : itemsCopy) {
//            System.out.println(j);
//        }
        return mergeSortedQueues(mergeSort(firstHalf), mergeSort(itemsCopy));
    }

    public static void main(String[] args) {
//        Queue<String> students = new Queue<String>();
//        students.enqueue("Alice");
//        students.enqueue("Vanessa");
//        students.enqueue("Ethan");
//        students.enqueue("Hank");
//        students.enqueue("Edmund");
//        Queue<String> studentsSorted = mergeSort(students);
//        System.out.println("------");
//        for (String i : students) {
//            System.out.println(i);
//        }
//        System.out.println("------");
//        for (String j : studentsSorted) {
//            System.out.println(j);
//        }
        Queue<Integer> students = new Queue<Integer>();
        students.enqueue(5);
        students.enqueue(666);
        students.enqueue(2);
        students.enqueue(5);
        students.enqueue(4);
        Queue<Integer> studentsSorted = mergeSort(students);
        System.out.println("------");
        for (Integer i : students) {
            System.out.println(i);
        }
        System.out.println("------");
        for (Integer j : studentsSorted) {
            System.out.println(j);
        }
//        Queue<Integer> students = new Queue<Integer>();
//        students.enqueue(1);
//        students.enqueue(4);
//        students.enqueue(6);
//        students.enqueue(8);
//        students.enqueue(8);
//        Queue<Integer> students2 = new Queue<Integer>();
//        students2.enqueue(2);
//        students2.enqueue(5);
//        students2.enqueue(8);
//        students2.enqueue(10);
//        Queue<Integer> studentsSorted = mergeSortedQueues(students, students2);
//        System.out.println("------");
//        for (Integer j : studentsSorted) {
//            System.out.println(j);
//        }
    }
}
