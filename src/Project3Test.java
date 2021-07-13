import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class Project3Test{

    ///// Queue Tests /////

    //Queue is initially size zero on creation
    @Test
    public void testInitSizeZero(){
        Queue<Creature> q = new Queue<Creature>();
        assertTrue(q.size == 0);
    }

    //Adding to the queue increases size property
    @Test
    public void queueIncreaseSize(){
        Queue<Creature> q = new Queue<Creature>();
        Creature c = new Creature();
        q.enqueue(c);
        assertTrue(q.size == 1);
    }

    //Dequeueing decreases size
    @Test
    public void queueDecreaseSize(){
        Queue<Creature> q = new Queue<Creature>();
        Creature c1 = new Creature();
        Creature c2 = new Creature();
        q.enqueue(c1);
        q.enqueue(c2);
        assertTrue(q.size == 2);
        q.dequeue();
        assertTrue(q.size == 1);
    }

    //Items are dequeued in the proper FIFO order
    @Test
    public void queueOrdering(){
        Queue<Creature> q = new Queue<Creature>();
        Creature c1 = new Creature();
        Creature c2 = new Creature();
        q.enqueue(c1);
        q.enqueue(c2);
        Creature dq1 = q.dequeue();
        assertTrue(dq1 == c1);
    }

    @Test
    //Dequeuing an empty queue returns null
    public void dequeueEmptyNull(){
        Queue<Creature> q = new Queue<Creature>();
        Creature dq = q.dequeue();
        assertNull(dq);
    }

    //Dequeuing an empty queue doesn't go below size zero
    @Test
    public void dequeueNoNegative(){
        Queue<Creature> q = new Queue<Creature>();
        Creature dq = q.dequeue();
        assertEquals(0,q.size);
    }

    //Peeking returns the first item in the queue
    @Test
    public void peekFirst(){
        Queue<Creature> q = new Queue<Creature>();
        Creature c = new Creature();
        q.enqueue(c);
        Creature peek = q.peek();
        assertEquals(c, peek);
    }

    //Peeking an empty queue returns null
    @Test
    public void peekEmptyNull(){
        Queue<Creature> q = new Queue<Creature>();
        Creature peek = q.peek();
        assertNull(peek);
    }

    //Peeking does not change the size of the queue
    @Test
    public void peekNoSizeChange(){
        Queue<Creature> q = new Queue<Creature>();
        Creature c = new Creature();
        q.enqueue(c);
        assertTrue(q.size == 1);
        Creature peek = q.peek();
        assertTrue(q.size == 1);
    }

    //New queue returns true for isEmpty
    @Test
    public void newIsEmpty(){
        Queue<Creature> q = new Queue<Creature>();
        assertTrue(q.isEmpty());
    }

    //Queue with items does not return true for isEmpty
    @Test
    public void isNotEmpty(){
        Queue<Creature> q = new Queue<Creature>();
        Creature c = new Creature();
        q.enqueue(c);
        assertFalse(q.isEmpty());
    }

    //Adding and item and removing it results in true for isEmpty
    @Test
    public void dequeueIsEmpty(){
        Queue<Creature> q = new Queue<Creature>();
        Creature c = new Creature();
        q.enqueue(c);
        Creature dq = q.dequeue();
        assertTrue(q.isEmpty());
    }
}