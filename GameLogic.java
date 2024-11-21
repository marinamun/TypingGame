import java.awt.Color;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GameLogic extends JPanel {
    private String[] wordList = {"edamame", "chess", "fairylight", "bufanda", "hotshower", "blush"};
    private String currentWord;
    private int wordX; //start position of word
    private int wordY;
    private JLabel wordLabel; //we display the moving word hier
    private Timer timer;

    public GameLogic(){
        wordX=900;
        wordY=200;
        currentWord = getRandomWord();
        wordLabel = new JLabel(currentWord);
        this.add(wordLabel);
        wordLabel.setBounds(wordX, wordY, 200, 30); //tell label to track word's position

        this.setBackground(Color.PINK);

        timer = new Timer(30, e -> moveWord());
        timer.start();
    }
    private String getRandomWord(){
        Random random = new Random();
        return wordList[random.nextInt(wordList.length)];
    }
    private void moveWord(){
        wordX -= 5;
        wordLabel.setBounds(wordX, wordY, 200, 30);

    }
}
