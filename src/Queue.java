import java.util.LinkedList;

public class Queue <T> {

    int size = 0;

    LinkedList<T> linkedList;

    public Queue(){
        this.linkedList = new LinkedList<T>();
    }

    /**
     * Add item to the queue
     * @param item
     */
    public void enqueue(T item){
        linkedList.addLast(item);
        this.size++;
    }

    /**
     * Pop the first item from the queue
     * @return
     */
    T dequeue(){
        try {
            T first = linkedList.pop();
            this.size--;
            return first;
        }
        catch(Exception e) {
            return null;
        }
    }

    /**
     * Look at first item in the queue
     * @return
     */
    T peek(){
        T first = linkedList.peek();
        return first;
    }


    /**
     * If the queue has no items in it
     * @return
     */
    public boolean isEmpty()
    {
        return this.size <= 0;
    }
}
