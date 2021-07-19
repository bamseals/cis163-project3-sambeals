public class Player extends Creature{
    //Stats
    int level = 1;
    int experience = 0;
    int experienceToNext = 100;

    public Player(){
        this.maxHealth = 300;
        this.currentHealth = this.maxHealth;
        this.strength = 50;
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
        this.maxHealth += 25;
        this.currentHealth += 25;
        this.strength += 10;
        this.experience -= experienceToNext;
        this.experienceToNext = level * 100;
    }

    public String toString(){
        return "You";
    }
}
