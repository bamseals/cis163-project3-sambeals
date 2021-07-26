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
        if (player.isOnFire()){
            System.out.println("You are on fire and burn for "+player.burn()+"damage");
        }
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
            playerWait();
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
                if (player.canCastSpell()){
                    playerSelectSpell();
                }
                else{
                    System.out.println("You do not have any spells right now, select another action");
                    turn--;
                    return;
                }
                break;
            case "wait":
                System.out.println("You wait the turn and regain a small amount of health");
                playerWait();
                break;
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

    /**
     * Player skips turn and gains a little health
     */
    void playerWait(){
        player.currentHealth += 10;
        if (player.currentHealth > player.maxHealth)
        {
            player.currentHealth = player.maxHealth;
        }
        this.isPlayerTurn = false;
    }
    
    /**
     * Player selects which queue/monster to attack
     */
    void playerSelectAttack(){
        Queue<Creature> target = selectDirection("attack");
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
            monsterDie(target);
        };
    }
    
    /**
     * Player selects which queue/monster to select ready spell on
     */
    void playerSelectSpell(){
        int spell = player.availableSpells[player.knownSpells];
        if (3 == spell) //no need to select direction if healing
        {
            player.healCast();
        }
        else
        {
            Queue<Creature> target = selectDirection("cast "+player.spells[spell]);
            Creature attacked = target.peek();
            switch(spell){
                case 0:
                    player.fireballCast(attacked);
                    break;
                case 1:
                    player.blizzardCast(attacked);
                    break;
                case 2:
                    player.lightningCast(attacked);
                    break;
            }
            if (attacked.isDead()){
                monsterDie(target);
            }
        }
        player.knownSpells--;
    }

    /**
     * Player selects a direction queue
     * @param label
     * @return
     */
    Queue<Creature> selectDirection(String label){
        Queue<Creature> target = null;
        while (Objects.isNull(target))
        {
            System.out.println("Which direction will you "+label+"?");
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
        return target;
    }

    /**
     * Handle the removal of creature form queue and gaining experience
     * @param direction
     */
    void monsterDie(Queue<Creature> direction){
        Creature monster = direction.peek();
        System.out.println(monster.toString() + " has been slain!");
        experienceThisTurn += monster.strength + monster.maxHealth;
        direction.dequeue();
        monstersSlain++;
    }

    /**
     * Determine action taken by monsters on their turn
     */
    void monsterTurn(){
        monsterLogic("North", north);
        monsterLogic("East", east);
        monsterLogic("South", south);
        monsterLogic("West", west);
        createMonsters(this.difficulty);
        this.isPlayerTurn = true;
    }

    /**
     * Determine what the front monster of each queue is up to this turn
     * @param label
     * @param direction
     */
    void monsterLogic(String label, Queue<Creature> direction){
        if (direction.size > 0){
            Creature monster = direction.peek();
            if (monster.isOnFire()) //If monster is burnt handle burn damage
            {
                System.out.println(monster.toString() + " is on fire and burns for " + monster.burn() + " damage");
                if (monster.isDead())
                {
                    monsterDie(direction);
                    return;
                }
            }
            if (monster.isFrozen()) //If monster is frozen skip its actions
            {
                System.out.println(monster.toString() + " is frozen and spends the turn thawing");
                monster.isFrozen--;
            }
            else if (monster.canCastSpell()){ //If monster has a spell it will cast it
                int spell = monster.availableSpells[monster.knownSpells];
                if ("Heal" == monster.spells[spell] && monster.currentHealth >= monster.maxHealth) //If monster can heal but has no damage just attack
                {
                    System.out.println(monster.toString() + " attacks you for " + monster.attack(player) + " damage from the " + label);
                }
                else
                {
                    monster.knownSpells--;
                    monster.castSpell(spell, player);
                }
                
            }
            else //Otherwise it will attack
            {
                System.out.println(monster.toString() + " attacks you for " + monster.attack(player) + " damage from the " + label);
            }
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

    /**
     * Counts turns and handles events that happen based on turn
     */
    void incrementTurn(){
        turn++;
        difficulty = turn / 10; //Every 10 turns there is a chance for stronger monsters! (currently 5 difficulty levels);
        if (turn % TURNS_NEW_SPELL == 0 && player.knownSpells < player.availableSpells.length){
            System.out.println("This turn you ready a "+player.spells[player.learnSpell()] + " spell");
        }
    }

    /**
     * Describe the current state of the player and arena
     */
    void describeArenaState(){
        System.out.println("----- Turn " + this.turn + " -----");
        System.out.println("You are feeling " + ("" != player.damageDesc ? player.damageDesc : "Uninjured") + " ("+player.currentHealth+"/"+player.maxHealth+" hp), Level "+player.level+" ("+player.experience+"/"+player.experienceToNext+" xp)");
        if (player.canCastSpell()){
            System.out.println("You are prepared to cast " + player.spells[player.availableSpells[player.knownSpells]]);
        }
        System.out.println("To the North " + describeDirection(north));
        System.out.println("To the East " + describeDirection(east));
        System.out.println("To the South " + describeDirection(south));
        System.out.println("To the West " + describeDirection(west));
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
