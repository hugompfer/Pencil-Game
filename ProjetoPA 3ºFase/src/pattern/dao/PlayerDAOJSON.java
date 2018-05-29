/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import model.Machine;
import model.Player;
import model.User;

/**
 * PlayerDAOJSON representa uma persistência do tipo json de jogadores
 * 
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class PlayerDAOJSON implements PlayerDAO {

    private String basePath;
    private List<Player> players;
    private final static String filename = "user.json";
    private final static String filename2 = "machine.json";

    public PlayerDAOJSON(String basePath) {
        this.basePath = basePath;
        players = new ArrayList<>();

        loadAll();
    }

    //le o ficheio 
    private void loadAll() {

        try {
            Gson gso = new GsonBuilder().setLenient().create();
            Gson gso2 = new GsonBuilder().setLenient().create();
            BufferedReader br = new BufferedReader(new FileReader(basePath + filename));
            BufferedReader br2 = new BufferedReader(new FileReader(basePath + filename2));

            List<User> list = gso.fromJson(br, new TypeToken<List<User>>() {
            }.getType());
            List<Machine> list2 = gso2.fromJson(br2, new TypeToken<List<Machine>>() {
            }.getType());

            this.players.addAll(list2);
            this.players.addAll(list);
            Player.setQuantityOfPlayers(getLastId());
        } catch (IOException i) {
            // i.printStackTrace();
            this.players = new ArrayList<>();
        }
    }

    //grava no ficheiro
    private void saveAll() {
        FileWriter writer = null;
        FileWriter writer2 = null;
        try {
            Gson gson = new Gson();
            writer = new FileWriter(basePath + filename);
            writer2 = new FileWriter(basePath + filename2);
            gson.toJson(getUsers(), writer);
            gson.toJson(getMachines(), writer2);
            writer.close();
            writer2.close();
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }

    }

    //recebe o ultimo id introduzido
    private int getLastId() {
        int max = 0;
        for (Player p : players) {
            if (p.getId() > max) {
                max = p.getId();
            }
        }
        return max;
    }

    //devolve os utilizados
    private List<User> getUsers() {
        List<User> list = new ArrayList<>();
        for (Player p : players) {
            if (p instanceof User) {
                list.add((User) p);
            }
        }
        return list;
    }

    //devolve as machines
    private List<Machine> getMachines() {
        List<Machine> list = new ArrayList<>();
        for (Player p : players) {
            if (p instanceof Machine) {
                list.add((Machine) p);
            }
        }
        return list;
    }

    /**
     * Permite inserir um jogador no ficheiro
     *
     * @param p
     * @return true se atualizar- false o contrario
     */
    @Override
    public boolean insertPlayer(Player p) {
        if (players.contains(p)) {
            return false;
        }
        if (p instanceof User) {
            User u = (User) p;
            u.setPw(encrypt(u.getPassword()));
        }
        players.add(p);
        saveAll();
        return true;
    }

    /**
     * Permite procurar um jogador atraves do seu id
     *
     * @param id id o jogador a procurar
     * @return player se encontrado ou null não encontrado
     */
    @Override
    public Player findPlayer(int id) {
        for (Player p : players) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * Permite devolver a lista de jogadores registados
     *
     * @return lista de jogadores
     */
    @Override
    public List<Player> selectPlayers() {
        return players;
    }

    //instancia e do tipo user
    private boolean isUser(Player p) {
        return p instanceof User;
    }

    //encripta a password
    private String encrypt(String password) {
        StringBuilder sb = new StringBuilder();
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * Permite verificar se o user tem as credenciais corretas
     *
     * @param user
     * @param pw
     * @return true se tiver corretas- false o contrario
     */
    @Override
    public boolean correctCredentials(String user, String pw) {
        User u = findUser(user);
        return (u != null && u.getPassword().equals(encrypt(pw)));
    }

    /**
     * Permite descobrir pelo o username se existe um utilizador
     *
     * @param user username
     * @return User se encontrado, null o contrario
     */
    @Override
    public User findUser(String user) {
        for (Player p : players) {
            if (isUser(p)) {
                if (p.getIdentification().equals(user)) {
                    return (User)p;
                }
            }
        }
        return null;
    }

    /**
     * Permite descobrir se um email já existe
     *
     * @param email
     * @return true se encontrado,false o contario
     */
    @Override
    public boolean findEmail(String email) {
        for (Player p : players) {
            if (isUser(p)) {
                User u = (User) p;
                if (u.getEmail().equals(email)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Permite atualizar o username de um utilzador
     *
     * @param u utilizador a atualizar
     * @param name nome novo
     * @return true se efetuado com sucesso,false o contario
     */
    @Override
    public boolean updateName(User u, String name) {
        User user = findUser(u.getIdentification());
        User user2 = findUser(name);
        if (user2 == null && user != null) {
            user.setIdentification(name);
            saveAll();
            return true;
        }
        return false;
    }

    /**
     * Permite atualizar a pw de um utilzador
     *
     * @param u utilizador a atualizar
     * @param pw nova pw
     * @return true se efetuado com sucesso,false o contario
     */
    @Override
    public boolean updatePw(User u, String pw) {
        User user = findUser(u.getIdentification());
        if (user != null) {
            user.setPw(encrypt(pw));
            saveAll();
            return true;
        }
        return false;
    }

    /**
     * Permite atualizar o email de um utilzador
     *
     * @param u utilizador a atualizar
     * @param email email novo
     * @return true se efetuado com sucesso,false o contario
     */
    @Override
    public boolean updateEmail(User u, String email) {
        User user = findUser(u.getIdentification());
        boolean exist = findEmail(email);
        if (!exist && user != null) {
            user.setEmail(email);
            saveAll();
            return true;
        }
        return false;
    }

    /**
     * Permite atualizar as estatistcas de um jogador
     *
     * @param p jogar a atualizar
     * @return true se efetuado com sucesso,false o contario
     */
    @Override
    public boolean updateStatistics(Player p) {
        Player player = findPlayer(p.getId());
        if (player == null) {
            return false;
        }
        player.getIndividualStatistics().copy(p.getIndividualStatistics());
        saveAll();
        return true;
    }

    /**
     * Permite descobrir pelo o name se existe um maquina
     *
     * @param name nome da maquina
     * @return Machine se encontrado, null o contrario
     */
    @Override
    public Machine findMachine(String name) {
        for (Player p : players) {
            if (p instanceof Machine) {
                
                if (p.getIdentification().equals(name)) {
                    return (Machine)p;
                }
            }
        }
        return null;
    }

}
