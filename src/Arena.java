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
    int monsterCreateChance = 25;

    //Stats
    int turn = 0;
    int monstersSlain = 0;

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
        createMonster();
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
        System.out.println("Game over!");
    }

    /**
     * Allow player to select action for their turn
     */
    void playerTurn(){
        turn++;
        describeArenaState();
        //If you are frozen the turn ends right here;
        if (player.isFrozen())
        {
            System.out.println("You are frozen and spend the turn thawing out.");
            player.isFrozen = false;
            this.isPlayerTurn = false;
            return;
        }
        System.out.println("What would you like to do?");
        String s = scan.next();
        switch (s.toLowerCase())
        {
            case "attack":
                System.out.println("player attacks here");
                break;
            case "spell":
                System.out.println("player spells go here");
                break;
            case "quit":
                this.playerQuit = true;
                break;
            default:
                System.out.println("Invalid action, please try again");
                turn--;
                return;
        }
        this.isPlayerTurn = false;
    }

    

    /**
     * Determine action taken by monsters
     */
    void monsterTurn(){
        System.out.println("monster turn goes here");
        createMonster();
        this.isPlayerTurn = true;
    }

    /**
     * Chance to randomly create monster in each direction
     */
    void createMonster(){
        for (int i = 0; i < 4; i++)
        {
            int roll = App.generateRandom(1, 100);
            if (roll <= monsterCreateChance){
                Creature c = new Creature();
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
     * Describe the current state of the player and arena
     */
    void describeArenaState(){
        System.out.println("----- Turn " + this.turn + " -----");
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
            desc = "there is a " + q.peek().toString() + " and " + (q.size - 1) + " monsters behind it."; 
        }
        else
        {
            desc = "there is nothing.";
        }
        return desc;
    }
}
