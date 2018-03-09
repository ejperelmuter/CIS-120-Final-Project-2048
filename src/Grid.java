import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
public class Grid extends JPanel{
    
    
    private Tile[][] boardState;
    private int numTiles;
    private int score;
    private int turn;
    private Map<Integer, int[]> turnToScore;
    
    public boolean playing = false; // whether the game is running 
    private JLabel status; // Current status text, i.e. "Running..."
    
    // Game constants
    public static final int GRID_WIDTH = 550;
    public static final int GRID_HEIGHT = 550;
    public static final int TILE_WIDTH = 100;
    public static final int TILE_HEIGHT = 100;
    public static final int TILE_SPACING = 30;
    

    
    //Constructor
    public Grid(JLabel status) {
        //Initialize boardstate
        boardState = new Tile[4][4];
        numTiles = 0;
        score = 0;
        turn = 0;
        //Initialize turnToScore map
        turnToScore = new TreeMap<Integer, int[]>();
        
        //Add key listeners to slide the board
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                //Add the current boardstate and turn to map before updating board
                turnToScore.put(turn, getBoard());
                turn++;
                if (e.getKeyCode() == KeyEvent.VK_LEFT && playing) {
                    slideLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && playing) {
                    slideRight();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && playing) {
                    slideDown();
                } else if (e.getKeyCode() == KeyEvent.VK_UP && playing) {
                    slideUp();
                }
                status.setText("Score: " + getScore() + "   Turn: " + turn);
                addRandomTile();
            }
        });
        
        // Creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.LIGHT_GRAY);
                
        setFocusable(true);
        this.status = status;
    }
    
    
    public void initializeBoard(){
        //Initialize the board to all 0 tiles
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                boardState[i][j] = new Tile(0);
            }
        }
        addRandomTile();
        addRandomTile();
        repaint();
        status.setText("Score: " + getScore());
    }
    
    public void resetBoard() {
        numTiles = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                boardState[i][j].setValue(0);
            }
        }
        addRandomTile();
        addRandomTile();
        requestFocusInWindow();
    }
    
    //Reset game to a board of 0 tiles
    public void reset() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++){
                boardState[i][j] = null;
            }
        }
        numTiles = 0;
        score = 0;
        turn = 0;
        turnToScore = new TreeMap<Integer, int[]>();
        initializeBoard();
        playing = true;
        status.setText("Welcome to 2048! Good luck!");

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    
    //Return an int[] with the current values on the board and score
    public int[] getBoard() {
        int[] currentBoard = new int[17];
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                currentBoard[count] = boardState[i][j].getValue();
                count++;
            }
        }
        //Place the current score at the end of the array
        currentBoard[16] = getScore();
        return currentBoard;
    }
    
    //Set the tile values and score from a given int[]
    public void setBoard(int[] tileValues) {
        if (tileValues == null) {
            throw new NullPointerException();
        }
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                boardState[i][j].setValue(tileValues[count]);
                count++;
            }
        }
        score = tileValues[16];
        
        //Get the number of tiles >0 to avoid ending game prematurely
        count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(boardState[i][j].getValue() > 0) {
                    count++;
                }
            }
        }
        numTiles = count;
        repaint();
    }
    
    
    // **Undo**
    // Method to invoke when the undo button is clicked
    public void undo() {
        if (turn < 2) {
            return;
        }
        setBoard(turnToScore.get(turn - 1));
        turn--;
        status.setText("Score: " + getScore() + "   Turn: " + turn);
    }
    
    
    public void drawHighScores(Graphics g) {
        //Print a box on the screen over the grid to display the scores on
        g.setColor(Color.RED);
        g.fillRect(120, 50, 350, 400);
        g.setColor(Color.BLACK);
        g.drawRect(120, 50, 350, 400);
        // Prompt the user for their name
        //  Then print the scores on the screen
        Scoreboard sb = new Scoreboard("files/scores.txt");
        System.out.print("What is your name?\n > ");
        try {
            Scanner scanner = new Scanner(System.in);
            String name = scanner.nextLine();
            scanner.close();
            sb.addScore(name, score);
        } catch (NoSuchElementException e) {}
        String[] highScores = sb.getTopFive();
        g.setColor(Color.BLACK);
        g.setFont(new Font("Plain", Font.PLAIN, 35));
        g.drawString("High Scores", 165, 120);  
        int yOffset = 0;
        for (int i = 0; i < 5; i++) {
            String nameAndScore = highScores[i];
            nameAndScore = Integer.toString(i+1) + ") " + nameAndScore;
            g.drawString(nameAndScore, 140, 170 + yOffset);   
            yOffset = yOffset + 45;
        }
        g.setFont(new Font("Plain", Font.PLAIN, 25));
        g.drawString("Press Reset to Play Again!", 140, 170 + yOffset);
    }
    
    public void addRandomTile() {
        if (numTiles == 16 && playing) {
            playing = false;
            status.setText("Board is full. You lose! Type name in console to save score.");
            repaint();
            return;
        }
        int x = (int) (Math.random() * 4);
        int y = (int) (Math.random() * 4);
        //Make sure the tile is in an unoccupied space
        while (boardState[x][y].getValue() != 0 && numTiles < 16) {
            x = (int) (Math.random() * 4);
            y = (int) (Math.random() * 4);
        }
        //Decide whether its a 2 or a 4
        int random = (int) (Math.random() * 2);
        int val = 2;
        if (random == 1) {
            val = 4;
        }
        boardState[x][y].setValue(val);
        numTiles++;
    }
    
    
    
//***Methods for sliding the board***
    public void slideRight() {
        for (int j = 0; j < 4; j++) {
            for (int i = 2; i >= 0; i--) {
                int position = i;
                while (position+1 <= 3 && boardState[j][position + 1].getValue() == 0) {
                    int val = boardState[j][position].getValue();
                    boardState[j][position + 1].setValue(val);
                    boardState[j][position].setValue(0);
                    position++;
                }
                if (position <= 2 && boardState[j][position].getValue() == 
                    boardState[j][position + 1].getValue() &&
                    boardState[j][position + 1].getValue() != 0) {
                    int value = boardState[j][position].getValue();
                    boardState[j][position + 1].setValue(2*value); 
                    boardState[j][position].setValue(0);
                    //Decreases tile counter by 1 if two tiles collide
                    score = score + value;
                    numTiles--;
                }
          }
        }
        repaint();
    }
    
    public void slideUp() {
        rotateClockwise();
        slideRight();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
    }
    
    public void slideDown() {
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        slideRight();
        rotateClockwise();
    }

    public void slideLeft() {
        rotateClockwise();
        rotateClockwise();
        slideRight();
        rotateClockwise();
        rotateClockwise();
        
    }
    
    public void rotateClockwise() {
        Tile[][] copy = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++){
                copy[i][j] = boardState[j][i];
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++){
                boardState[i][j] = copy[i][3-j];
            }
        }
        repaint();
    }


    public void drawTiles(Graphics g) {
        int offsetX = 0;
        int offsetY = 0;
        int val;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //Draw the tile
                Tile curr = boardState[i][j];
                g.setColor(curr.getColor());
                val = curr.getValue();              
                g.fillRoundRect(30 + offsetX, 30 + offsetY, TILE_WIDTH, TILE_HEIGHT, 10, 10);
                //Draw the integer value of the tile if non-zero
                if (val != 0) {
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Bold", Font.BOLD, 40));
                    if (val < 10) {
                        g.drawString(Integer.toString(val), 65 + offsetX, 90 + offsetY);
                    }
                    else if (val > 10 && val < 100) {
                        g.drawString(Integer.toString(val), 55 + offsetX, 90 + offsetY);
                    }
                    else if (val >= 100 && val < 1000) {
                        g.drawString(Integer.toString(val), 40 + offsetX, 90 + offsetY);
                    }
                    else if (val >= 1000) {
                        g.setFont(new Font("Bold", Font.BOLD, 30));
                        g.drawString(Integer.toString(val), 37 + offsetX, 88 + offsetY);
                    }

                }
               
                offsetX = offsetX + TILE_WIDTH + TILE_SPACING;
            }
            offsetX = 0;
            offsetY = offsetY + TILE_HEIGHT + TILE_SPACING;
        }
    }
    
    public int getScore() {
        return score;
    }
    
    public int getTileValue(int x, int y) {
        return boardState[x][y].getValue();
    }
    
    private void drawInstructions(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(25, 50, 500, 475);
        g.setColor(Color.WHITE);
        g.drawRect(25, 50, 500, 475);
        g.setFont(new Font("Plain", Font.BOLD, 30));
        g.drawString("Welcome to 2048!", 135, 100);
        g.setFont(new Font("Plain", Font.PLAIN, 25));
        g.drawString("How to play: ", 35, 140);
        g.drawString("-Use your arrow keys to slide the tiles.", 35, 170);
        g.drawString("-When two equal tiles collide,", 35, 210);
        g.drawString("    they merge into one!", 35, 240);
        g.drawString("-Win by getting a 2048 Tile!", 35, 280);
        g.drawString("-If you fill the board before reaching,", 35, 320);
        g.drawString("    2048, you lose!", 35, 350);
        g.drawString("Reset Button: Resets board", 35, 390);
        g.drawString("Undo Button: Sets board back 1 move", 35, 425);
        g.setFont(new Font("Plain", Font.BOLD, 30));
        g.drawString("Good luck!", 170, 485);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTiles(g);
        if (turn == 0) {
            drawInstructions(g);
        }
        if (!playing) {
            status.setText("Board is full. You lose! Type name in console to save score.");
            drawHighScores(g);
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GRID_WIDTH, GRID_HEIGHT);
    }
    
    
}