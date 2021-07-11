public class Arena {
    Queue<Creature> north;
    Queue<Creature> east;
    Queue<Creature> south;
    Queue<Creature> west;
    Player player;

    boolean gameOver = false;
    boolean isPlayerTurn = true;
    boolean isMonsterTurn = false;

    public Arena(){

    }

    void gameLoop(){

    }

    void playerTurn(){

    }

    void monsterTurn(){

    }

    void createMonster(){

    }

    public boolean isGameOver()
    {
        return this.gameOver;
    }
}
