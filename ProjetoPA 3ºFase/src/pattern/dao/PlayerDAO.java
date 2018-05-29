/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.dao;

import java.util.List;
import model.Machine;
import model.Player;
import model.User;


/**
 * PlayerDAO representa uma interface de jogadores
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public interface PlayerDAO {

    public boolean insertPlayer(Player p);

    public Player findPlayer(int id);

    public boolean updateName(User u, String name);

    public boolean updatePw(User u, String pw);

    public boolean updateEmail(User u, String email);

    public boolean updateStatistics(Player p);

    public List<Player> selectPlayers();

    public boolean correctCredentials(String user, String pw);

    public User findUser(String user);
    
     public Machine findMachine(String name);

    public boolean findEmail(String email);
    
    
    

}
