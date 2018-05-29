/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

import java.io.Serializable;
import javafx.scene.paint.Color;

/**
 * Colour representa uma das várias cores de jogo possiveis.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public enum Colour implements Serializable{
    RED,BLUE,BLACK;
    
    public Color getColor(){
     switch (this) {
            case RED:
                return Color.RED;
            case BLUE:
                return Color.BLUE;
            default:
                return Color.BLACK;
        }
    }
}
