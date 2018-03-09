import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


import org.junit.Test;

public class GameTest {

//***Grid Set/Get Tests***
  
    @Test
    public void testSetBoardNullBoard() {
        try {
            JLabel status = new JLabel("Testing...");
            Grid g = new Grid(status);
            g.initializeBoard();
            g.setBoard(null);
        } catch (NullPointerException e) {
            return;
        }
        fail();
    }
    
    @Test
    public void testSetBoard() {
        JLabel status = new JLabel("Testing...");
        Grid g = new Grid(status);
        g.initializeBoard();
        int[] x = {0, 1, 2, 3,
                   4, 5, 6, 7,
                   8, 9, 10, 11,
                   12, 13, 14, 15, 0};
        g.setBoard(x);
        assertEquals(g.getTileValue(1, 1), x[5]);
        assertEquals(g.getTileValue(3, 3), x[15]);
    }
    
    @Test
    public void testGetBoard() {
        JLabel status = new JLabel("Testing...");
        Grid g = new Grid(status);
        g.initializeBoard();
        int[] x = {0, 1, 2, 3,
                   4, 5, 6, 7,
                   8, 9, 10, 11,
                   12, 13, 14, 15, 0};
        g.setBoard(x);
        for (int i = 0; i < 16; i++) {
             assertEquals(x[i], g.getBoard()[i]);
        }
       
    }
    
//***Sliding tests***

    @Test
    public void testRotateClockise() {
        JLabel status = new JLabel("Testing...");
        Grid g = new Grid(status);
        g.initializeBoard();
        int[] x = {0, 0, 2, 0,
                   0, 0, 0, 0,
                   0, 0, 0, 0,
                   0, 0, 0, 2, 0};
        g.setBoard(x);
        g.rotateClockwise();
        int[] y = {0, 0, 0, 0,
                   0, 0, 0, 0,
                   0, 0, 0, 2,
                   2, 0, 0, 0};
         for (int i = 0; i < 16; i++) {
             assertEquals(y[i], g.getBoard()[i]);
        }
    }
    
    @Test
    public void testRotateCounterClockise() {
        JLabel status = new JLabel("Testing...");
        Grid g = new Grid(status);
        g.initializeBoard();
        int[] x = {0, 0, 2, 0,
                   0, 0, 0, 0,
                   0, 0, 0, 0,
                   0, 0, 0, 2, 0};
        g.setBoard(x);
        g.rotateClockwise();
        g.rotateClockwise();
        g.rotateClockwise();
        int[] y = {0, 0, 0, 2,
                   2, 0, 0, 0,
                   0, 0, 0, 0,
                   0, 0, 0, 0};
         for (int i = 0; i < 16; i++) {
             assertEquals(y[i], g.getBoard()[i]);
        }
    }
    
    
    @Test
    public void testSlideRight() {
        JLabel status = new JLabel("Testing...");
        Grid g = new Grid(status);
        g.initializeBoard();
        int[] x = {0, 0, 2, 2,
                   0, 0, 0, 0,
                   0, 0, 0, 0,
                   0, 0, 0, 2, 0};
        g.setBoard(x);
        g.slideRight();
        int[] y = {0, 0, 0, 4,
                   0, 0, 0, 0,
                   0, 0, 0, 0,
                   0, 0, 0, 2};
        for (int i = 0; i < 16; i++) {
             assertEquals(y[i], g.getBoard()[i]);
        }
    }
    
    @Test
    public void testSlideUp() {
        JLabel status = new JLabel("Testing...");
        Grid g = new Grid(status);
        g.initializeBoard();
        int[] x = {0, 0, 2, 2,
                   0, 0, 0, 0,
                   0, 0, 0, 0,
                   0, 0, 0, 2, 0};
        g.setBoard(x);
        g.slideUp();
        int[] y = {0, 0, 2, 4,
                   0, 0, 0, 0,
                   0, 0, 0, 0,
                   0, 0, 0, 0};
        for (int i = 0; i < 16; i++) {
             assertEquals(y[i], g.getBoard()[i]);
        }
    }
    
    @Test
    public void testSlideLeft() {
        JLabel status = new JLabel("Testing...");
        Grid g = new Grid(status);
        g.initializeBoard();
        int[] x = {0, 0, 2, 2,
                   0, 0, 0, 0,
                   0, 0, 0, 0,
                   0, 0, 4, 4, 0};
        g.setBoard(x);
        g.slideLeft();
        int[] y = {4, 0, 0, 0,
                   0, 0, 0, 0,
                   0, 0, 0, 0,
                   8, 0, 0, 0};
        for (int i = 0; i < 16; i++) {
             assertEquals(y[i], g.getBoard()[i]);
        }
    }
    
    
    @Test
    public void testSlideDown() {
        JLabel status = new JLabel("Testing...");
        Grid g = new Grid(status);
        g.initializeBoard();
        int[] x = {2, 2, 2, 2,
                   0, 0, 0, 0,
                   4, 8, 0, 0,
                   0, 8, 0, 2, 0};
        g.setBoard(x);
        g.slideDown();
        int[] y = {0, 0, 0, 0,
                   0, 0, 0, 0,
                   2, 2, 0, 0,
                   4, 16, 2, 4};
        for (int i = 0; i < 16; i++) {
             assertEquals(y[i], g.getBoard()[i]);
        }
    }
    

    @Test
    public void testScoreKeeping() {
        JLabel status = new JLabel("Testing...");
        Grid g = new Grid(status);
        g.initializeBoard();
        int[] x = {0, 0, 2, 2,
                   0, 0, 0, 0,
                   0, 0, 0, 0,
                   0, 0, 4, 4, 0};
        g.setBoard(x);
        g.slideLeft();
        assertEquals(g.getScore(), 6);
    }
    
    @Test
    public void testCorrectCollision() {
       JLabel status = new JLabel("Testing...");
        Grid g = new Grid(status);
        g.initializeBoard();
        int[] x = {0, 2, 2, 2, // Should combine the rightmost 2s
                   0, 0, 0, 0,
                   0, 0, 0, 0,
                   0, 4, 4, 4, 0};  //Should combine the rightmost 4s
        g.setBoard(x);
        g.slideRight();
        
        int[] y = {0, 0, 2, 4,
                   0, 0, 0, 0,
                   0, 0, 0, 0,
                   0, 0, 4, 8};
        for (int i = 0; i < 16; i++) {
             assertEquals(y[i], g.getBoard()[i]);
        }
    } 
    
//***Scoreboard Tests***
    
    
    @Test
    public void testNullFile() {
        try {
            Scoreboard sb = new Scoreboard(null);
        } catch (NullPointerException e) {
            return;
        }
        fail();
    }
    
    @Test
    public void addNullScore() {
        try {
            Scoreboard sb = new Scoreboard("files/scores.txt");
            sb.addScore(null, 100);
        } catch (NullPointerException e) {
            return;
        }
        fail();
    }
    

    
}