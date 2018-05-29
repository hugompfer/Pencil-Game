package tads;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Representa a exceçao de fora de limite de uma linkedlisAdapter
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
  * @version 2.0 (08/01/2018)
 *
 */
public class OutOfBoundsException extends RuntimeException {


    public OutOfBoundsException(int rank) {
        super("Rank " + rank + " não existe");
        
    }
    
}
