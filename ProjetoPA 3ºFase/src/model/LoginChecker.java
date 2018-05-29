/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * LoginCheker representa a classe que faz verificações e validações ao nivel de
 * input do utilizador e a sua interação com a persistência de dados no caso do
 * menu de Login.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class LoginChecker extends Checker {

    public LoginChecker(PlayersManager manager) {
        super(manager);
    }

    /**
     * Permite verificar se determinado username corresponde a determinada
     * password.
     *
     * @param username username procurado.
     * @param password password do utilizador.
     * @return Valor true ou false.
     */
    public boolean correctCredentials(String username, String password) {
        return getManager().getPlayersRegisted().correctCredentials(username, password);
    }

    /**
     * Permite verificar se determinado utilizador já está autenticado.
     *
     * @param u utilizador a verificar.
     * @return Valor true ou false.
     */
    public boolean alreadyLogged(User u) {
        return getManager().alreadyLogged(u);
    }

    /**
     * Permite remover o primeiro utilizador autenticado.
     */
    public void removeFirstLoggedPlayer() {
        getManager().removeFirstLoggedPlayer();
    }

    /**
     * Permite remover o segundo utilizador autenticado.
     */
    public void removeSecondLoggedPlayer() {
        getManager().removeSecondLoggedPlayer();
    }

    /**
     * Permite verificar se existe um utilizador autenticado.
     *
     * @return Valor true ou false.
     */
    public boolean has1UserLogged() {
        return getManager().has1UserLogged();
    }

    /**
     * Permite verificar se existem um dois utilizadores autenticados.
     *
     * @return Valor true ou false.
     */
    public boolean has2UsersLogged() {
        return getManager().has2UsersLogged();
    }

    /**
     * Permite adicionar um utilizador autenticado á lista.
     *
     * @param username
     * @param password
     */
    public void log(String username, String password) throws CheckerException {
        User user = (User) findUser(username);
        if (user == null) {
            throw new CheckerException("Username não encontrado");
        }
        if (!correctCredentials(username, password)) {
            throw new CheckerException("Password incorreta");
        }
        isPossibleToLog(user);
        getManager().addPlayer(user);
        
    }
    
    private void isPossibleToLog(User user) throws CheckerException{
        if (alreadyLogged(user)) {
            throw new CheckerException("Utilizador já autenticado!");
        } else if (!alreadyLogged(user) && has2UsersLogged()) {
            throw new CheckerException("Já foi atingido o máximo de 2 utilizadores autenticados!");
        }
     
    }

}


