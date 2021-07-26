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
    private final int CHANCE_HAS_SPELL = 10;
    String[] spells = {"Fireball","Blizzard","Lightning","Heal"};
    int[] availableSpells = new int[10]; //Creatures and player can have a maximum of 10 spells
    int knownSpells = 0;
    
    //Difficulty level options
    
    private final int[] difficultyHealth = {40,70,100,200,350};
    private final int[] difficultyStrength = {20,35,65,125,200};

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
        int difficultyRoll = App.generateRandom(0, difficulty); //Roll for difficulty so that as turns go monsters have a higher chance of being difficult
        if (difficultyRoll > 4)
        {
            difficultyRoll = 4; //4 is currently the maximum difficulty
        }
        //Set stats
        this.difficulty = App.generateRandom(0, difficultyRoll); //Monsters can be generated from lowest level to highest input difficulty
        this.maxHealth = App.generateRandom(1, difficultyHealth[this.difficulty]);
        this.currentHealth = this.maxHealth;
        this.strength = App.generateRandom(1, difficultyStrength[this.difficulty]);
        //Set Descriptions
        this.monsterType = App.generateRandom(0, monsterTypes[this.difficulty].length-1);
        this.strengthDesc = determineDescByPct(this.strength, difficultyStrength[this.difficulty], strengthDescs, false);
        this.healthDesc = determineDescByPct(this.maxHealth, difficultyHealth[this.difficulty], healthDescs, false);
        //Generate Spells
        int spellChance = (1+difficulty) * CHANCE_HAS_SPELL;
        int spellRoll = App.generateRandom(1, 100);
        if (spellChance > spellRoll){
            this.learnSpell();
        }
    }

    /**
     * Create a creature with manually set health and strength for testing purposes
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

    /**
     * Determine the damage description for a monster based on its health
     */
    public void setDamageDesc(){
        this.damageDesc = determineDescByPct(this.currentHealth, this.maxHealth, damageDescs, true);
    }
    
    /**
     * Select the designated description from a given array given current/max value
     * @param actual
     * @param max
     * @param descsArray
     * @param damageDesc
     * @return
     */
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

    /**
     * Deal damage to another creature
     * @param target
     * @return
     */
    public int attack(Creature target){
        int damage = this.rollDamage();
        target.hurt(damage);
        return damage;
    }

    /**
     * Randomly generate damage based on creature strength
     * @return
     */
    private int rollDamage(){
        return App.generateRandom(1, this.strength);
    }

    /**
     * Take a certain amount of damage
     * @param damage
     */
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
        return 0 < this.knownSpells;
    }

    /**
     * Handle burn damage
     * @return
     */
    int burn(){
        int damage = this.onFire;
        this.hurt(damage);
        this.onFire = this.onFire / 2;
        if (this.onFire < 2){
            this.onFire = 0;
        }
        return damage;
    }

    /**
     * Learn a random spell from the list of spells
     * @return
     */
    int learnSpell(){
        if (this.knownSpells >= this.availableSpells.length){
            return -1;
        }
        int spell = App.generateRandom(0, this.spells.length-1);
        this.knownSpells += 1;;
        this.availableSpells[this.knownSpells] = spell;
        return spell;
    }
   
    /**
     * Cast a spell
     * @param spell
     * @param target
     * @return
     */
    int castSpell(int spell, Creature target){
        switch(spell){
            case 0: //fireball
                this.fireballCast(target);
                break;
            case 1: //blizzard
                this.blizzardCast(target);
                break;
            case 2: //lightning
                this.lightningCast(target);
                break;
            case 3: //heal
                this.healCast();
                break;
        }
        return 0;
    }

    /**
     * Creature cast fireball spell
     * @param target
     * @return
     */
    int fireballCast(Creature target){
        int singe = App.generateRandom(1, this.strength);
        int burn = App.generateRandom(1, this.strength / 2);
        target.hurt(singe);
        target.onFire += burn;
        System.out.println(this.toString() + " singes you with a Fireball for " + singe + " damage, and ignites you for "+burn+"!");
        return singe;
    }

    /**
     * Creature cast the blizzard spell
     * @param target
     * @return
     */
    int blizzardCast(Creature target){
        int damage = App.generateRandom(1, this.strength);
        target.hurt(damage);
        target.isFrozen++;
        System.out.println(this.toString() + " freezes you with a Blizzard for a turn and " + damage + " damage!");
        return damage;
    }

    /**
     * Creature cast the lightning spell
     * @param target
     * @return
     */
    int lightningCast(Creature target){
        int damage = App.generateRandom(this.strength/2, this.strength+10);
        System.out.println(this.toString() + " shocks you with Lightning for " + damage + " damage!");
        return damage;
    }

    /**
     * Creature cast the heal spell
     * @return
     */
    int healCast(){
        int heal = App.generateRandom(this.strength/2, this.strength);
        this.currentHealth += heal;
        if (this.maxHealth < this.currentHealth){
            this.currentHealth = this.maxHealth;
        }
        System.out.println(this.toString() + " heals itself for " + heal + " health!");
        return heal;
    }

    /**
     * Describe the creature
     */
    public String toString(){
        return this.damageDesc + (this.damageDesc.equals("") ? "" : " ") //Only add a space if the damage description is not empty
        + this.healthDesc + " "
        + this.strengthDesc + " "
        + this.monsterTypes[this.difficulty][this.monsterType];
    }
}
