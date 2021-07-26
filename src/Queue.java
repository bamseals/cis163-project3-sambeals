public class Queue <T> {

    Node head; //Node at front of queue
    Node tail; //Node at end of queue
    int size = 0;

    class Node{
        Node next;
        T data;
    }

    public Queue(){
        this.head = null;
        this.tail = null;
    }

    /**
     * Add item to the queue
     * @param item
     */
    public void enqueue(T item){
        Node newNode = new Node();
        newNode.data = item;
        if (this.size == 0)
        {
            this.head = newNode;
            this.tail = newNode;
        }
        else
        {
            this.tail.next = newNode;
            this.tail = newNode;
        }
        this.size++;
    }

    /**
     * Pop the first item from the queue
     * @return
     */
    T dequeue(){
        if (this.size == 0)
        {
            return null;
        }
        else
        {
            T data = this.head.data;
            if (this.head.next == null) //If there are no more nodes after the first
            {
                this.head = null;
                this.tail = null;
            }
            else //If there is a node after the head, set it to the new head
            {
                this.head = this.head.next;
            }
            this.size--;
            return data;
        }
    }

    /**
     * Look at first item in the queue
     * @return
     */
    T peek(){
        if (this.size == 0){
            return null;
        }
        else{
            return this.head.data;
        }
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
