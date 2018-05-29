/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * AccountCheker representa a classe que faz verificações e validações ao nivel
 * de input do utilizador e a sua interação com a persistência de dados no caso
 * do menu de alteração dos dados de conta.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class AccountChecker extends RegisterChecker {

    public AccountChecker(PlayersManager manager) {
        super(manager);
    }

    /**
     * Permite obter o primeiro utilizador autenticado.
     *
     * @return utilizador em causa.
     */
    public User getFirstUser() {
        return this.getManager().getFirstUser();
    }

    /**
     * Permite obter o segundo utilizador autenticado.
     *
     * @return utilizador em causa.
     */
    public User getSecondUser() {
        return this.getManager().getSecondUser();
    }

    public boolean changedPassword(String username, String password, String newPassword) {
        User u = (User) findUser(username);
        return (!u.getPassword().equals(password)
                || !u.getPassword().equals(newPassword));
    }

    public boolean changedEmail(String username, String email) {
        User u = (User) findUser(username);
        return !u.getEmail().equals(email);
    }

    public boolean noChanges(String username, String email, String password, String oldPassword) {
        return !changedEmail(username, email) && !changedPassword(username, password, oldPassword);
    }

    public void update(String username, String email, String password, String oldPassword) throws CheckerException {
        if (noChanges(username, email, password, oldPassword)) {
            throw new CheckerException("Nenhuma alteração efetuada!");
        } else {
            User u = findUser(username);

            if (changedEmail(username, email)) {
                getManager().getPlayersRegisted().updateEmail(u, email);
            }

            if (changedPassword(username, oldPassword, password)) {
                getManager().getPlayersRegisted().updatePw(u, password);
            }
            //atualiza o user la lista de logados
            getManager().updateUser(u);
        }
    }
}
