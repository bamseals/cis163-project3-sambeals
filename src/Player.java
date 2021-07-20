public class Player extends Creature{
    //Stats
    int level = 1;
    int experience = 0;
    int experienceToNext = 100;

    private final int HEALTH_PER_LEVEL = 50;
    private final int DAMAGE_PER_LEVEL = 10;
    private final int EXP_PER_LEVEL = 100;

    public Player(){
        this.maxHealth = 300;
        this.currentHealth = this.maxHealth;
        this.strength = 30;
    }

    /**
     * Add experience, return true if leveled up or false if not
     * @param xp
     * @return
     */
    public boolean gainExperience(int xp){
        this.experience += xp;
        if (this.experience >= experienceToNext){
            this.levelUp();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Handle calculations of leveling up
     */
    public void levelUp(){
        this.level++;
        this.maxHealth += HEALTH_PER_LEVEL;
        this.currentHealth += HEALTH_PER_LEVEL;
        this.strength += DAMAGE_PER_LEVEL;
        this.experience -= experienceToNext;
        this.experienceToNext = level * EXP_PER_LEVEL;
    }

    /**
     * Player cast fireball spell
     */
    int fireballCast(Creature target){
        int singe = App.generateRandom(this.strength/2,this.strength);
        int burn = App.generateRandom(1, this.strength / 2);
        target.hurt(singe);
        target.onFire += burn;
        System.out.println(this.toString() + " singe " + target.toString() + " with a Fireball for " + 1 + " damage, and ignites it for "+burn+"!");
        return singe;
    }

    /**
     * Player cast blizzard spell
     */
    int blizzardCast(Creature target){
        int damage = App.generateRandom(1, this.strength);
        int freeze = App.generateRandom(1, this.level);
        target.hurt(damage);
        target.isFrozen += freeze;
        System.out.println(this.toString() + " freeze " + target.toString() + " with a Blizzard for " + freeze + " turn"+(1 != freeze ? "s" : "")+" and " + damage + " damage!");
        return damage;
    }

    /**
     * Player cast lightning spell
     */
    int lightningCast(Creature target){
        int damage = App.generateRandom(this.strength, this.strength * 3);
        target.hurt(damage);
        System.out.println(this.toString() + " shock " + target.toString() + " with Lightning for " + damage + " damage!");
        return damage;
    }

    /**
     * Player cast heal spell
     */
    int healCast(){
        int heal = App.generateRandom(this.strength/2, this.strength);
        this.currentHealth += heal;
        if (this.maxHealth < this.currentHealth){
            this.currentHealth = this.maxHealth;
        }
        System.out.println(this.toString() + " heal yourself for " + heal + " health!");
        return heal;
    }

    public String toString(){
        return "You";
    }
}
