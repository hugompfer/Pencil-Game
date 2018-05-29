/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * User representa um utilizador inscrito no jogo.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
  * @version 2.0 (08/01/2018)
 */
public class User extends Player implements Serializable{

    private String password;
    private String email;
    private final LocalDate bithdate;

    /**
     * Cria um novo utilizador com as repetivas informações.
     *
     * @param id Número identificador.
     * @param username Username para Login.
     * @param password Password para Login.
     * @param email E-mail do utilizador.
     * @param localDate Data de nascimento do utilizador.
     */
    public User(int id, String username, String password, String email, LocalDate localDate) {
        super(id,username);
        this.password = password;
        this.email = email;
        this.bithdate = localDate;
    }
    
    public User(int id, String username, String password, String email, LocalDate localDate,IndividualStatistics individualStatistics) {
        super(id,individualStatistics,username);
        this.password = password;
        this.email = email;
        this.bithdate = localDate;
    }

    public User(String username, String password, String email, LocalDate localDate) {
        super(username);
        this.password = password;
        this.email = email;
        this.bithdate = localDate;
    }
    

    /**
     * Permite obter a Password do utilizador.
     *
     * @return Password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Permite obter o E-mail do utilizador.
     *
     * @return E-mail.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Permite obter a Data de Nascimento do utilizador.
     *
     * @return Data de Nascimento.
     */
    public LocalDate getBithdate() {
        return bithdate;
    }

    
     public void setPw(String pw) {
        this.password = pw;
    }
     
      public void setEmail(String email) {
        this.email = email;
    }

    @Override
    /**
     * Permite obter as informações do utilizador em formato de leitura.
     *
     * @return Informações.
     */
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd / LLLL / yyyy");
        String birth = bithdate.format(formatter);
        return super.toString() + "\n User: " + super.getIdentification() + "\n Email: " + email + "\n Data de Nascimento:" + birth;
    }
}
