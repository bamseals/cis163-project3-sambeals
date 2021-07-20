public class Player extends Creature{
    //Stats
    int level = 1;
    int experience = 0;
    int experienceToNext = 100;

    public Player(){
        this.maxHealth = 300;
        this.currentHealth = this.maxHealth;
        this.strength = 30;
    }

    //add experience, return true if leveled up or false if not
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

    public void levelUp(){
        this.level++;
        this.maxHealth += 50;
        this.currentHealth += 50;
        this.strength += 10;
        this.experience -= experienceToNext;
        this.experienceToNext = level * 100;
    }

    int fireballCast(Creature target){
        int singe = App.generateRandom(this.strength/2,this.strength);
        int burn = App.generateRandom(1, this.strength / 2);
        target.hurt(singe);
        target.onFire += burn;
        System.out.println(this.toString() + " singe " + target.toString() + " with a Fireball for " + 1 + " damage, and ignites it for "+burn+"!");
        return singe;
    }

    int blizzardCast(Creature target){
        int damage = App.generateRandom(1, this.strength);
        int freeze = App.generateRandom(1, this.level);
        target.hurt(damage);
        target.isFrozen += freeze;
        System.out.println(this.toString() + " freeze " + target.toString() + " with a Blizzard for " + freeze + " turn"+(1 != freeze ? "s" : "")+" and " + damage + " damage!");
        return damage;
    }

    int lightningCast(Creature target){
        int damage = App.generateRandom(this.strength, this.strength * 3);
        target.hurt(damage);
        System.out.println(this.toString() + " shock " + target.toString() + " with Lightning for " + damage + " damage!");
        return damage;
    }

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
