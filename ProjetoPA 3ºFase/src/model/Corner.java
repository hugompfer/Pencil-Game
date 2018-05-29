/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 * Corner representa um vértice do hexágono da área de jogo.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class Corner implements Serializable{
   // variáveis de instância - substitua o exemplo abaixo pelo seu próprio
    private double posX;
    private double posY;
    /**
     * COnstrutor para objetos da classe Coordinates
     * @param posX
     * @param posY
     */
    public Corner(double posX,double posY)
    {
        // inicializa variáveis de instância
      this.posX=posX;
      this.posY=posY;
    }

    public double getPosX(){
        return posX;
    }
    
    public double getPosY(){
        return posY;
    }
    
    public void setPosX(int posX){     
        this.posX=posX;  
    }
    
    public void setPosY(int posY){  
        this.posY=posY;     
    }
    
    public boolean equals(double x,double y){
        return this.posX==x && this.posY==y;
    }
    
    
}
