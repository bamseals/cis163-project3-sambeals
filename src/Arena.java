import java.util.Objects;
import java.util.Scanner;

public class Arena {
    //Queues of monsters
    Queue<Creature> north;
    Queue<Creature> east;
    Queue<Creature> south;
    Queue<Creature> west;

    //Player
    Player player;

    //Game states
    boolean isPlayerTurn = true;
    boolean playerQuit = false;
    final int MONSTER_CREATE_CHANCE = 25;
    final int TURNS_NEW_SPELL = 5;

    //Stats
    int turn = 0;
    int difficulty = 0;
    int monstersSlain = 0;
    int experienceThisTurn = 0;

    //Scanner for detecting user input
    Scanner scan = new Scanner(System.in);

    /**
     * Create default arena with 25 monsters per queue
     */
    public Arena(){
        this.player = new Player();
        this.north = new Queue<Creature>();
        this.east = new Queue<Creature>();
        this.south = new Queue<Creature>();
        this.west = new Queue<Creature>();
        createMonsters(this.difficulty);
    }

    /**
     * Loop through each turn until the game ends
     */
    void gameLoop(){
        while (false == isGameOver())
        {
            if (this.isPlayerTurn){
                playerTurn();
            }
            else
            {
                monsterTurn();
            }
        }
        System.out.println("Game over! You lasted " + turn + " turns and slayed " + monstersSlain + " monsters");
    }

    /**
     * Allow player to select action for their turn
     */
    void playerTurn(){
        incrementTurn();
        describeArenaState();
        //If you are frozen the turn ends right here;
        if (player.isFrozen())
        {
            System.out.println("You are frozen and spend the turn thawing out.");
            player.isFrozen -= 1;
            this.isPlayerTurn = false;
            return;
        }
        //If there are no monsters player spends the turn resting
        else if (north.size == 0 && east.size == 0 && south.size == 0 && west.size == 0)
        {
            System.out.println("With no enemies this turn you rest and recover some health.");
            
            player.currentHealth += 10;
            if (player.currentHealth < player.maxHealth)
            {
                player.currentHealth = player.maxHealth;
            }
            this.isPlayerTurn = false;
            return;
        }
        System.out.println("What would you like to do? (attack, spell, quit)");
        String s = scan.next();
        switch (s.toLowerCase())
        {
            case "attack":
                playerSelectAttack();
                break;
            case "spell":
                System.out.println("player spells go here");
                break;
            case "wait":
                System.out.println("wait to gain health");
            case "quit":
                this.playerQuit = true;
                break;
            default:
                System.out.println("Invalid action, please try again");
                turn--;
                return;
        }
        if (0 < this.experienceThisTurn){
            System.out.println("You have gain " + this.experienceThisTurn + " experience");
            if (player.gainExperience(this.experienceThisTurn))
            {
                System.out.println("You have leveled up to level " + player.level+"!");
            }
            this.experienceThisTurn = 0;
        }
        this.isPlayerTurn = false;
    }
    
    void playerSelectAttack(){
        Queue<Creature> target = null;
        while (Objects.isNull(target))
        {
            System.out.println("Which direction will you attack?");
            String s = scan.next();
            switch (s.toLowerCase()){
                case "north":
                    target = this.north;
                    break;
                case "west":
                    target = this.west;
                    break;
                case "south":
                    target = this.south;
                    break;
                case "east":
                    target = this.east;
                    break;
                default:
                    System.out.println("Please input North, South, East, or West.");
            }
        }
        Creature attacked = target.peek();
        if (Objects.isNull(attacked))
        {
            System.out.println("There are no monsters in that direction, choose again");
            playerSelectAttack();
            return;
        } 
        int damage = player.attack(attacked);
        System.out.println("You deal " + damage + " damage to " + attacked.toString() + "!");
        if (attacked.isDead()){
            System.out.println(attacked.toString() + " has been slain!");
            experienceThisTurn += attacked.strength + attacked.maxHealth;
            target.dequeue();
            monstersSlain++;
        };
    }

    

    /**
     * Determine action taken by monsters
     */
    void monsterTurn(){
        monsterAttack("North", north);
        monsterAttack("East", east);
        monsterAttack("South", south);
        monsterAttack("West", west);
        createMonsters(this.difficulty);
        this.isPlayerTurn = true;
    }

    void monsterAttack(String label, Queue<Creature> direction){
        if (direction.size > 0){
            Creature monster = direction.peek();
            int damage = monster.attack(player);
            System.out.println(monster.toString() + " does " + damage + " damage to you from the " + label);
        }
    }

    /**
     * Chance to randomly create monster in each direction
     */
    void createMonsters(int difficulty){
        for (int i = 0; i < 4; i++)
        {
            int roll = App.generateRandom(1, 100);
            if (roll <= MONSTER_CREATE_CHANCE){
                Creature c = new Creature(difficulty);
                switch (i){
                    case 0:
                        north.enqueue(c);
                        break;
                    case 1:
                        east.enqueue(c);
                        break;
                    case 2:
                        south.enqueue(c);
                        break;
                    case 3:
                        west.enqueue(c);
                        break;
                }
            }
        }
    }

    /**
     * Game ends if player dies or quits
     * @return boolean
     */
    public boolean isGameOver()
    {
        if (this.playerQuit || player.isDead())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    void incrementTurn(){
        turn++;
        difficulty = turn / 10; //Every 10 turns there is a chance for stronger monsters! (currently 5 difficulty levels);
    }

    /**
     * Describe the current state of the player and arena
     */
    void describeArenaState(){
        System.out.println("----- Turn " + this.turn + " -----");
        System.out.println("You are feeling " + ("" != player.damageDesc ? player.damageDesc : "Uninjured") + " ("+player.currentHealth+"/"+player.maxHealth+" hp), Level "+player.level+" ("+player.experience+"/"+player.experienceToNext+" xp)");
        System.out.println("To the North " + describeDirection(north));
        System.out.println("To the East " + describeDirection(east));
        System.out.println("To the South " + describeDirection(south));
        System.out.println("To the West " + describeDirection(west));
    }

    void determinePlayerSpells(){
        
    }

    /**
     * Create a string describing the state of monsters in a given direction
     * @param q
     * @return String
     */
    String describeDirection(Queue<Creature> q){
        String desc = "";
        if (q.size == 1)
        {
            desc = "there is a " + q.peek().toString();
        }
        else if (q.size > 1)
        {
            desc = "there is a " + q.peek().toString() + " with " + (q.size - 1) + " monsters behind it."; 
        }
        else
        {
            desc = "there is nothing.";
        }
        return desc;
    }
}
