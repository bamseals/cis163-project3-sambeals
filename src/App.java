public class App {
    public static void main(String[] args) throws Exception {
        Arena game = new Arena();
        game.gameLoop();
    }

    public static int generateRandom(int min,int max){
        return (int) ((Math.random() * (max - min)) + min);
    }
}
