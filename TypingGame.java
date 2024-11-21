import javax.swing.*;
public class TypingGame {
    public static void main(String[] args) {
        System.out.println("Welcome to the Typing Game!");

        JFrame frame = new JFrame("Typing game");
        frame.setSize(1000,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        GameLogic gameLogic = new GameLogic();
        frame.add(gameLogic);
    }
}