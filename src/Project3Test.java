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

    ///// Arena Tests /////

    //Difficulty increases with enough turns
    @Test
    public void difficultyIncrement(){
        Arena a = new Arena();
        for (int i = 0; i < 20; i++){
            a.incrementTurn();
        }
        assertTrue(a.difficulty == 2);
    }

    //Game over is true when player takes more damage than max health
    @Test
    public void deadGameOver(){
        Arena a = new Arena();
        a.player.hurt(999);
        assertTrue(a.isGameOver());
    }

    ///// Creature Tests /////

    //Make sure a damage description is being set for an injured creature
    @Test
    public void checkDamageDesc(){
        Creature c = new Creature();
        c.currentHealth = 1;
        c.setDamageDesc();
        assertTrue(c.damageDesc == "Near-Death");
    }

    //If a creature is dead the damage description reflects this
    @Test
    public void checkDeathDesc(){
        Creature c = new Creature();
        c.currentHealth = 0;
        c.setDamageDesc();
        assertTrue(c.damageDesc == "Dead");
    }

    //Make sure damage stays witin the expected range
    @Test
    public void damageRange(){
        Creature c = new Creature(0);
        Creature punchingBag = new Creature();
        int maxDamage = 0;
        for (int i = 0; i < 100; i++){
            int damage = c.attack(punchingBag);
            if (damage > maxDamage){
                maxDamage = damage;
            }
        }
        assertTrue(maxDamage <= c.strength);

    }

    //Taking damage changes current health
    @Test
    public void getHurt(){
        Creature c = new Creature();
        int damage = c.maxHealth-1;
        c.hurt(damage);
        assertTrue(c.currentHealth == 1);
    }

    //Taking too much damage causes a creature to die
    @Test
    public void creatureDies(){
        Creature c = new Creature();
        c.hurt(999);
    }

    ///// Player Tests /////

    //Player can not level up immediately
    @Test
    public void noLevelOnStart(){
        Player p = new Player();
        assertFalse(p.gainExperience(0));
    }

    //Player can and does level up with enough experience
    @Test
    public void levelUp(){
        Player p = new Player();
        assertTrue(p.gainExperience(100));
    }

    //Make sure experience rolls over after level up
    @Test
    public void consecutiveLevel(){
        Player p = new Player();
        assertTrue(p.gainExperience(300));
        assertTrue(p.gainExperience(0));
    }

    ///// /////
    @Test
    public void placeholder(){
        assertFalse(1 == 1);
    }
}