public class Creature {
    //Stats
    int maxHealth = 0;
    int currentHealth = 0;
    int strength = 0;
    int difficulty = 0;

    //Statuses
    int isFrozen = 0;
    int onFire = 0;

    //Spells
    String[] spellNames = {"Fireball","Blizzard","Lightning","Heal"};
    int[] availableSpells = new int[10]; //Creatures and player can have a maximum of 10 spells
    int knownSpells = 0;

    //Difficulty level options
    private int[] difficultyHealth = {40,70,100,200,350};
    private int[] difficultyStrength = {20,35,65,125,200};

    //Descriptions
    String[][] monsterTypes = {
        {"Slime","Rat","Boar","Hornet","Spider"},
        {"Zombie","Skeleton","Wolf","Condor","Crocodile"},
        {"Bandit","Bear","Shark","Pirate","Ghoul"},
        {"Ogre","Vampire","Warlock","Werewolf","Knight"},
        {"Dragon","Giant","Kraken","Lich","Phoenix"}
    };
    int monsterType = 0;

    String[] strengthDescs = {"Sickly","Weak","Regular","Strong","Scary"};
    String[] healthDescs = {"Starving","Hungry","Skittish","Stout","Vivacious"};
    String[] damageDescs= {"Near-Death","Badly-Injured","Weakened","Slightly-Injured",""};
    String healthDesc = "";
    String strengthDesc = "";
    String damageDesc = "";
    

    /**
     * Create a random creature with random stats
     */
    public Creature(){
        this(0);
    }

    /**
     * Create a creature that has a higher chance of being more difficult the higher the turncount is
     * @param turn
     */
    public Creature(int difficulty){
        if (difficulty > 4)
        {
            difficulty = 4; //4 is currently the maximum difficulty
        }
        //Set stats
        this.difficulty = App.generateRandom(0, difficulty); //Monsters can be generated from lowest level to highest input difficulty
        this.maxHealth = App.generateRandom(1, difficultyHealth[this.difficulty]);
        this.currentHealth = this.maxHealth;
        this.strength = App.generateRandom(1, difficultyStrength[this.difficulty]);
        //Set Descriptions
        this.monsterType = App.generateRandom(1, monsterTypes[this.difficulty].length - 1);
        this.strengthDesc = determineDescByPct(this.strength, difficultyStrength[this.difficulty], strengthDescs, false);
        this.healthDesc = determineDescByPct(this.maxHealth, difficultyHealth[this.difficulty], healthDescs, false);
    }

    /**
     * Create a creature with manually set health and strength
     * @param health
     * @param strength
     */
    public Creature(int difficulty, int health, int strength)
    {
        this(difficulty);
        this.maxHealth = health;
        this.currentHealth = health;
        this.strength = strength;
    }

    public void setDamageDesc(){
        this.damageDesc = determineDescByPct(this.currentHealth, this.maxHealth, damageDescs, true);
    }
    
    private String determineDescByPct(int actual, int max, String[] descsArray,boolean damageDesc){
        String desc = "";
        float pct = (float) actual / max;
        if (damageDesc && actual <= 0)
        {
            return "Dead";
        }
        //damage descriptions are determined at different percentage breakpoints than the other descriptions
        if ((damageDesc && pct < 0.25) || (!damageDesc && pct < 0.2)){
            desc = descsArray[0];
        }
        else if ((damageDesc && pct < 0.5) || (!damageDesc && pct < 0.4)){
            desc = descsArray[1];
        }
        else if ((damageDesc && pct < 0.75) || (!damageDesc && pct < 0.6)){
            desc = descsArray[2];
        }
        else if ((damageDesc && pct < 1.0) || (!damageDesc && pct < 0.8)){
            desc = descsArray[3];
        }
        else{
            desc = descsArray[4];
        }
        return desc;
    }

    public int attack(Creature target){
        int damage = this.rollDamage();
        target.hurt(damage);
        return damage;
    }

    private int rollDamage(){
        return App.generateRandom(1, this.strength);
    }

    void hurt(int damage)
    {
        this.currentHealth -= damage;
        this.setDamageDesc();
    }

    boolean isDead()
    {
        return this.currentHealth <= 0;
    }

    boolean isFrozen()
    {
        return 0 < this.isFrozen;
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
        return this.damageDesc + (this.damageDesc.equals("") ? "" : " ") //Only add a space if the damage description is not empty
        + this.healthDesc + " "
        + this.strengthDesc + " "
        + this.monsterTypes[this.difficulty][this.monsterType];
    }
}
