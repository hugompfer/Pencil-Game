/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tads;




/**
 * Representa um comportamento de ranking 
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 *
 */
public interface Ranking<E>  extends Iterable<E>{


    public int size();
    

    public void add(E element)throws OutOfBoundsException;
    

    public E get(int rank)throws OutOfBoundsException;

    
    
    
}
