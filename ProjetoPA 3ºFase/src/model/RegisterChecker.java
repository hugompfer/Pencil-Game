/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegisterCheker representa a classe que faz verificações e validações ao nivel
 * de input do utilizador e a sua interação com a persistência de dados no caso
 * do menu de registo.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class RegisterChecker extends Checker {

    public RegisterChecker(PlayersManager manager) {
        super(manager);
    }

    /**
     * Permite verificar se existe o email introduzido.
     *
     * @return Valor true ou false.
     */
    public boolean findEmail(String email) {
        return getManager().getPlayersRegisted().findEmail(email);
    }

    /**
     * Permite adicionar um utilizador á lista de registos.
     *
     * @param u utilizador a adicionar.
     */
    public void insertPlayer(User u) {
        getManager().getPlayersRegisted().insertPlayer(u);
    }

    public User register(String username, String password, String email, LocalDate birthdate) throws CheckerException {
        User u = new User(username, password, email, birthdate);
        if (findUser(username) != null) {
            throw new CheckerException("UTilizador já existente");
        }
        checkEmail(email);
        insertPlayer(u);
        return u;
    }
    
    private void checkEmail(String email) throws CheckerException {
        if (findEmail(email)) {
            throw new CheckerException("Email já existente");
        }
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new CheckerException("Formato de Email inválido");
        }

    }
}
