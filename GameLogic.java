import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class GameLogic extends JPanel implements KeyListener {
    private String[] wordList = {"edamame", "chess", "fairylight", "bufanda", "hotshower", "blush"};
    private ArrayList<JLabel> activeWords;
    private ArrayList<Integer> wordPositions; // x positions
    private int wordY = 200;// y will always be 200 for simplicity now
    private Timer timer;
    private String userInput = "";
    private int score = 0;
    private JLabel scoreLabel;
    private JLabel userInputLabel;
    private int wordSpawnCounter = 0;

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
        scoreLabel.setFont(new Font("Monospaced", Font.PLAIN, 24));

        this.add(scoreLabel);

        userInputLabel = new JLabel("Typed: ");
        userInputLabel.setBounds(20, 60, 200, 30); // Adjust position and size
        userInputLabel.setOpaque(true); // this makes the label background visible
        userInputLabel.setBackground(Color.WHITE);
        userInputLabel.setBorder(new LineBorder(Color.decode("#202a45"), 2, true)); 
         userInputLabel.setForeground(Color.decode("#202a45"));
        userInputLabel.setFont(new Font("Monospaced", Font.PLAIN, 24));
        this.add(userInputLabel);


        displayWord();
        displayWord();


        timer = new Timer(50, e -> moveWords());
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
            int newX = wordPositions.get(i) -2;
            wordPositions.set(i, newX);

            JLabel wordLabel = activeWords.get(i);
            wordLabel.setBounds(newX, wordLabel.getY(), 200,30);
            
            //check if word went offscreen
            if (newX + wordLabel.getWidth()<0){
                gameOver();
                return;
            }
            // lets add a new word every 150 timer ticks
            wordSpawnCounter++;
            if (wordSpawnCounter % 150 == 0) {
                displayWord();
    }
        }

        if (activeWords.size() < 3) { //this displays new words on screen
        displayWord();
    }

    }
    private void displayWord() {
    String randomWord = getRandomWord();
    JLabel wordLabel = new JLabel(randomWord);
    wordLabel.setFont(new Font("Monospaced", Font.BOLD, 20));  
    wordLabel.setBounds(900, wordY, 200, 30);
    wordLabel.setForeground(Color.decode("#202a45")); 
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
        System.out.println("Game over, final score: " + score);
        
   
        // display the game over message on screen
        JLabel gameOverLabel = new JLabel("Game over :( final score: " + score );
        gameOverLabel.setFont(new Font("Courier New", Font.BOLD, 30));
        gameOverLabel.setBounds(200, 150, 600, 50); ;
        this.add(gameOverLabel);
        this.revalidate();
        this.repaint(); 
        
        // Remove all active words when the gameover message shows
        for (JLabel wordLabel : activeWords) {
            this.remove(wordLabel);
        }
        activeWords.clear();
        wordPositions.clear();
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
    userInputLabel.setText(userInput); // Update label


    for (int i = 0; i < activeWords.size(); i++) {
        JLabel wordLabel = activeWords.get(i);

        if (wordLabel.getText().equals(userInput)) {
            System.out.println("Word matched: " + userInput);
            this.remove(wordLabel); 
            activeWords.remove(i); 
            wordPositions.remove(i); 
            userInput = ""; 
            score++;
            scoreLabel.setText("Score: " + score); 
            this.revalidate(); // Ensure panel updates
            this.repaint(); // Refresh panel
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
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 

        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        Color startColor = Color.decode("#f9d76c");
        Color endColor = Color.decode("#ffb4bb");

        GradientPaint gradient = new GradientPaint(
        0, 0, startColor, 
        width, height, endColor 
    );

        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }


    
    
}
