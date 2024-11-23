import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GameLogic extends JPanel implements KeyListener {
    private String[] wordList = {"edamame", "chess", "fairylight", "bufanda", "hotshower", "blush"};
    private ArrayList<JLabel> activeWords;
    private ArrayList<Integer> wordPositions; // x positions
    private int wordY = 200;// y will always be 200 for simplicity now
    private Timer timer;
    private String userInput = "";
    private int score = 0;
    private JLabel scoreLabel;

    public GameLogic() {
        activeWords = new ArrayList<>();
        wordPositions = new ArrayList<>();
        
        //to disable the default layout manager of jpanel
        this.setLayout(null);
        this.setBackground(Color.PINK);
        //for the panel to capture user's keyboard input
        this.setFocusable(true);
        //to listen to keyboard presses
        this.addKeyListener(this);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(20,20,200,30);
        this.add(scoreLabel);

        displayWord();
        displayWord();


        timer = new Timer(30, e -> moveWords());
        timer.start();
    }
    private String getRandomWord(){
        Random random = new Random();
        return wordList[random.nextInt(wordList.length)];
    }
    private void moveWords(){
        if (isGameOver || activeWords.isEmpty()) {
            gameOver();
            return; 
        }

        for(int i= 0; i < activeWords.size(); i++){
            int newX = wordPositions.get(i) -5;
            wordPositions.set(i, newX);

            JLabel wordLabel = activeWords.get(i);
            wordLabel.setBounds(newX, wordLabel.getY(), 200,30);
            
            //check if word went offscreen
            if (newX + wordLabel.getWidth()<0){
                gameOver();
                return;
            }
        }

        if (activeWords.size() < 3) { //this displays new words on screen
        displayWord();
    }

    }
    private void displayWord() {
    String randomWord = getRandomWord();
    JLabel wordLabel = new JLabel(randomWord);
    wordLabel.setFont(wordLabel.getFont().deriveFont(18f)); 
    wordLabel.setBounds(900, wordY, 200, 30); 
    activeWords.add(wordLabel); 
    wordPositions.add(900); 
    this.add(wordLabel); 

    wordY += 50;
    if (wordY > 250) {
        wordY = 100; 
    }
    }

   private boolean isGameOver = false;

   private void gameOver() {
        if (isGameOver) return; 
        isGameOver = true;
        timer.stop();
        System.out.println("Game overrrrr, final score: " + score);
        // display the game over message on screen
        JLabel gameOverLabel = new JLabel("Game Over! Final Score: " + score);
        gameOverLabel.setFont(gameOverLabel.getFont().deriveFont(20f));
        gameOverLabel.setBounds(350, 150, 300, 50);
        this.add(gameOverLabel);
        this.revalidate();
        this.repaint();
    }


    private boolean matchesAnyWord(String input) {
        for (JLabel wordLabel : activeWords) {
            if (wordLabel.getText().startsWith(input)) {
                return true; 
            }
        }
        return false; 
}

   @Override
public void keyTyped(KeyEvent e) {
    if (isGameOver) return; 

    char typedChar = e.getKeyChar();
    userInput += typedChar;

    for (int i = 0; i < activeWords.size(); i++) {
        JLabel wordLabel = activeWords.get(i);

        if (wordLabel.getText().equals(userInput)) {
            this.remove(wordLabel); 
            activeWords.remove(i); 
            wordPositions.remove(i); 
            userInput = ""; 
            score++;
            scoreLabel.setText("Score: " + score); 
            return; 
        }
    }

    if (!matchesAnyWord(userInput)) {
        gameOver();
    }



  
}
    @Override
    public void keyPressed(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }


    
    
}
