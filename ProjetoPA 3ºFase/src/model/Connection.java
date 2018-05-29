/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import enums.Colour;
import java.io.Serializable;
import javafx.scene.paint.Color;


/**
 * Connection representa uma aresta do hexágono da área de jogo.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
  * @version 2.0 (08/01/2018)
 */
public class Connection implements Serializable{
    private int id;
    private static int numberOfConnection;
    private Player seletorUser;
    private Colour color;
     /**
     * Cria um nova aresta sem seleção feita por algum utilizador.
     */
    public Connection(){
        this(null,Colour.BLACK);
    }
    
     public Connection(Player seletorUser, Colour color){
        this.seletorUser=seletorUser;
        this.color=color;
        id=++numberOfConnection;
    }
    
     public int getId(){
         return id;
     }
     
     
     /**
     * Permite definir o utilizador que selecionou a aresta
     *
     * @param user Jogador que fez a seleção
     */
    public void setSelectorUser(Player user){
        this.seletorUser=user;
    }
    
     /**
     * Permite definir o a cor da aresta
     *
     * @param c
     */
    public void setColor(Colour c){
        this.color=c;
    }
    
     /**
     * Permite receber a cor da aresta
     *
     * @return a cor atual
     */
    public Colour getColor(){
        return this.color;
    }
    
    /**
     * Permite saber se a aresta ja foi selecionado por algum utilizador;
     *
     * @return true/false Se existe ou não seleção
     */
    public boolean isSelected(){
        return seletorUser!=null;
    }
    
    
    /**
     * Permite obter o utilizador que fez a seleção da aresta.
     *
     * @return Player utilizador que fez a seleção.
     */
    public Player getSeletorUser(){
        return seletorUser;
    }

    
    public Color getColorToPaint() {
        return color.getColor();
    }
    
    
    /**
     * Permite fazer a comparação entre duas arestas.
     *
     * @param choosenConnection Aresta a comparar
     * 
     * @return 0/outro valor caso as arestas sejam iguais ou diferentes.
     */
    int compareTo(Connection choosenConnection) {
        return this.id-choosenConnection.getId();
        
    }
            
}
