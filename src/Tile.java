import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Tile {
    
    int value;
    Color color;

    //Constructor
    public Tile(int number) {
        value = number;
        setColor();
    }
    
    
//***Setter Methods***
    public void setColor() {
        switch(value) {
            case 0: color = Color.WHITE;
                    break;
            case 2: color = Color.decode("#E74C3C");
                    break;
            case 4: color = Color.decode("#9B59B6");
                    break;
            case 8: color = Color.decode("#2471A3");
                    break;
            case 16: color = Color.decode("#2E86C1");
                    break;
            case 32: color = Color.decode("#17A589");
                    break;
            case 64: color = Color.decode("#138D75");
                    break;
            case 128: color = Color.decode("#6C3483");
                    break;
            case 256: color = Color.decode("#943126  ");
                    break;
            case 512: color = Color.decode("#7D3C98");
                    break;
            case 1024: color = Color.decode("#B9770E");
                    break;
            case 2048: color = Color.decode("#641E16");
                    break;
        }
        return;
    }
    
    public void setValue(int val) {
        value = val;
        setColor();
        return;
    }
    

    public boolean equals(Tile other) {
        if (other == null) {
            return false;
        }
        return (value == other.getValue());
    }
    
    
//***Getter Methods***
    public int getValue() {
        return value;
    }    
    
    public Color getColor() {
        return color;
    }

}