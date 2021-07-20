import java.util.Random;
public class App {
    public static void main(String[] args) throws Exception {
        Arena game = new Arena();
        game.gameLoop();
    }

    public static int generateRandom(int min,int max){
        Random rand = new Random();
        return (min + rand.nextInt((max - min) + 1));
    }
}
