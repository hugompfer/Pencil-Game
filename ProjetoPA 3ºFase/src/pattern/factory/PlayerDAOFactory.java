/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.factory;

import pattern.dao.PlayerDAOJSON;
import pattern.dao.PlayerDAOSQLite;
import pattern.dao.PlayerDAOSerializable;
import enums.Persistance;
import pattern.dao.PlayerDAO;

/**
 * PlayerDAOFactory representa a classe que constrói um determinado tipo de
 * persistência para armazenar os dados.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class PlayerDAOFactory {

    /**
     * método criador do jogo de acordo com as especificações.
     *
     * @param p tipo de persistência a utilizar.
     * @return Classe de persistência criado.
     */
    public static PlayerDAO PlayerDAOFactory(Persistance p) {
        switch (p) {
            case JSON:
                return new PlayerDAOJSON("");
            case SERIALIZABLE:
                return new PlayerDAOSerializable("");
            case SQLITE:
                return new PlayerDAOSQLite();
            default:
                return new PlayerDAOSerializable("");
        }
    }
}
