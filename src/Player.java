public class Player extends Creature{
    //Stats
    int level;
    int experience = 0;

    public Player(){
        this.maxHealth = 300;
        this.currentHealth = this.maxHealth;
        this.strength = 50;
    }

    public String toString(){
        return "You";
    }
}
