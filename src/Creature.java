public class Creature {
    //Stats
    int maxHealth = 0;
    int currentHealth = 0;
    int strength = 0;

    //Statuses
    boolean isFrozen = false;
    int onFire = 0;

    //Descriptions
    String[] monsterTypes = {"Goblin","Slime","Skeleton","Boar","Squid","Rat","Spider","Bandit","Ghost"};
    String[] strengthDescs = {"Weak","Regular","Strong","Scary"};
    String[] healthDescs= {"Near-Death","Badly-Injured","Weakened","Healthy"};
    int monsterType;

    /**
     * Create a random creature with random stats
     */
    public Creature(){
        this.monsterType = App.generateRandom(1, monsterTypes.length-1);
        this.maxHealth = App.generateRandom(1, 100);
        this.currentHealth = this.maxHealth;
        this.strength = App.generateRandom(1, 20);
    }

    /**
     * Create a creature that has a higher chance of being more difficult the higher the turncount is
     * @param turn
     */
    public Creature(int turn){
        this.monsterType = App.generateRandom(1, monsterTypes.length-1);
        this.maxHealth = App.generateRandom(1, 100);
        this.currentHealth = this.maxHealth;
        this.strength = App.generateRandom(1, 20);
    }

    /**
     * Create a creature with manually set health and strength
     * @param health
     * @param strength
     */
    public Creature(int turn, int health, int strength)
    {
        this.monsterType = App.generateRandom(1, monsterTypes.length-1);
        this.maxHealth = health;
        this.currentHealth = health;
        this.strength = strength;
    }

    int attack(){
        return strength;
    }

    void hurt(int damage)
    {
        this.currentHealth -= damage;
    }

    boolean isDead()
    {
        return this.currentHealth <= 0;
    }

    boolean isFrozen()
    {
        return this.isFrozen;
    }

    boolean isOnFire()
    {
        return 0 < this.onFire;
    }

    boolean canCastSpell()
    {
        return false;
    }

    public String toString(){
        return this.monsterTypes[this.monsterType];
    }
}
