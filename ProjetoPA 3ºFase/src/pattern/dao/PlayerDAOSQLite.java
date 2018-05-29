/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.dao;

import com.google.gson.Gson;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import model.IndividualStatistics;
import model.Player;
import model.User;
import org.json.JSONObject;
import model.Machine;
import java.sql.Date;

/**
 * PlayerDAOJSON representa uma pesristencia do tipo sqlite de jogadores
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class PlayerDAOSQLite implements PlayerDAO {

    private static final String DB_FILE = "players.db";
    private static final String URL = "jdbc:sqlite:" + DB_FILE;
    private Gson gson;
    /* SQL STATEMENTS */
    private static final String STATEMENT_SELECT_ALL = "select * from player";
    private static final String STATEMENT_SELECT_IDPLAYER = "select idUser, idMachine,statistics from player where id = ?";
    private static final String STATEMENT_SELECT_IDUSER = "select username, password,email,birthdate from user where id = ?";
    private static final String STATEMENT_SELECT_IDMACHINE = "select name from machine where id = ?";
    private static final String STATEMENT_SELECT_USERTOLOG = "select id, username, password,email,birthdate from user where username = ? and password=?";
    private static final String STATEMENT_SELECT_USER = "select player.id, username, password,email,birthdate,statistics from user"
            + " inner join player on player.id=user.id "
            + "where username = ?";
    private static final String STATEMENT_SELECT_MACHINE = "select player.id, name,statistics from machine"
            + " inner join player on player.id=machine.id "
            + "where name = ?";
    private static final String STATEMENT_SELECT_EMAIL = "select id, username, password,email,birthdate from user where email = ?";
    private static final String STATEMENT_SELECT_LAST_ID = "select max(id) from player;";
    private static final String STATEMENT_INSERT_USER = "insert into user(id, username, password,email,birthdate) values(?,?,?,?,?)";
    private static final String STATEMENT_INSERT_MACHINE = "insert into machine(id, name) values(?,?)";
    private static final String STATEMENT_INSERT_PLAYER = "insert into player(id,idUser,idMachine ,statistics ) values(?,?,?,?)";
    private static final String STATEMENT_UPDATE_USERNAME = "update user set username = ? where id = ?";
    private static final String STATEMENT_UPDATE_USERPW = "update user set password = ? where id = ?";
    private static final String STATEMENT_UPDATE_USEREMAIL = "update user  set email = ? where id = ?";
    private static final String STATEMENT_UPDATE_USERSTATISTICS = "update player set statistics = ? where id = ?";

    public PlayerDAOSQLite() {
        strutureCreate();
        Player.setQuantityOfPlayers(getLastId());
        gson = new Gson();
    }

    //cria a estrutura
    private void strutureCreate() {

        String sql1;
        String sql2;
        String sql3;
        // SQL statement for creating a new table
        sql1 = "CREATE TABLE IF NOT EXISTS user (\n"
                + "	 id int PRIMARY KEY,\n"
                + "	 username text not null,\n"
                + "	 password text not null ,\n"
                + "	 email text not null ,\n"
                + "	 birthdate date  not null\n"
                + ");\n\n";

        sql2 = "CREATE TABLE IF NOT EXISTS machine (\n"
                + "	 id int PRIMARY KEY,\n"
                + "	 name text not null\n"
                + ");\n\n";

        sql3 = "CREATE TABLE IF NOT EXISTS player (\n"
                + "	 id int PRIMARY KEY,\n"
                + "	 idUser int,\n"
                + "	 idMachine int,\n"
                + "	 statistics text not null\n"
                + ");\n\n";

        try (Connection sqlConnection = connect(); Statement stmt = sqlConnection.createStatement()) {

            // create a new table
            stmt.execute(sql1);
            stmt.execute(sql2);
            stmt.execute(sql3);

        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //conexa a bd
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    //cria um objecto do tipo json object
    private JSONObject createJSONObject(IndividualStatistics statistics) {

        String jsonString = gson.toJson(statistics);
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    //insere um jogador na bd
    private boolean insertStatement(Player p) {
        Connection sqlConnection = connect();
        PreparedStatement pstmt;
        PreparedStatement pstmt2;
        try {
            if (p instanceof User) {
                User u = (User) p;
                pstmt = sqlConnection.prepareStatement(STATEMENT_INSERT_USER);
                pstmt.setInt(1, p.getId());
                pstmt.setString(2, "" + u.getIdentification());
                pstmt.setString(3, "" + encrypt(u.getPassword()));
                pstmt.setString(4, "" + u.getEmail());
                pstmt.setDate(5, Date.valueOf(u.getBithdate()));
            } else {
                pstmt = sqlConnection.prepareStatement(STATEMENT_INSERT_MACHINE);
                pstmt.setString(1, "" + p.getId());
                pstmt.setString(2, "" + p.getIdentification());
            }
            pstmt.execute();

            pstmt2 = sqlConnection.prepareStatement(STATEMENT_INSERT_PLAYER);

            pstmt2.setInt(1, p.getId());
            if (p instanceof User) {
                pstmt2.setInt(2, p.getId());
                pstmt2.setString(3, "");

            } else {
                pstmt2.setString(2, "");
                pstmt2.setInt(3, p.getId());
            }
            pstmt2.setString(4, createJSONObject(p.getIndividualStatistics()).toString());
            pstmt2.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Permite inserir um jogador no ficheiro
     *
     * @param p
     * @return true se atualizar- false o contrario
     */
    @Override
    public boolean insertPlayer(Player p) {
        if (findPlayer(p.getId()) != null) {
            return false;
        }
        return insertStatement(p);
    }

    /**
     * Permite procurar um jogador atraves do seu id
     *
     * @param id id o jogador a procurar
     * @return player se encontrado ou null não encontrado
     */
    @Override
    public Player findPlayer(int id) {
        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_SELECT_IDPLAYER)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            IndividualStatistics statistics = getStatistics(rs.getString("statistics"));
            String s = rs.getString("idUser");
            if (!"".equals(s)) {
                return getUser(id, statistics);
            } else {
                return getMachine(id, statistics);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    private IndividualStatistics getStatistics(String info) {
        IndividualStatistics statistics = null;
        statistics = gson.fromJson(info, IndividualStatistics.class);
        return statistics;
    }

    private User getUser(int id, IndividualStatistics statistics) {
        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_SELECT_IDUSER)) {
            pstmt.setString(1, "" + id);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }
            return new User(id,
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getDate("birthdate").toLocalDate(),
                    statistics
            );
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Machine getMachine(int id, IndividualStatistics statistics) {
        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_SELECT_IDMACHINE)) {
            pstmt.setString(1, "" + id);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }
            return new Machine(id, statistics,
                    rs.getString("name")
            );
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
        if (findUser(u.getIdentification()) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_USERNAME)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, u.getId());

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
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
        if (findUser(u.getIdentification()) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_USERPW)) {

            pstmt.setString(1, encrypt(pw));
            pstmt.setInt(2, u.getId());

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
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
        if (findUser(u.getIdentification()) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_USEREMAIL)) {

            pstmt.setString(1, email);
            pstmt.setInt(2, u.getId());

            pstmt.execute();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
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
        if (findPlayer(p.getId()) == null) {
            return false;
        }

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_UPDATE_USERSTATISTICS)) {

            IndividualStatistics statistics = p.getIndividualStatistics();
            pstmt.setString(1, createJSONObject(statistics).toString());
            pstmt.setInt(2, p.getId());

            pstmt.execute();

            PreparedStatement pstmt2 = sqlConnection.prepareStatement("select statistics from player where id=" + p.getId());
            ResultSet rs = pstmt2.executeQuery();
            statistics = getStatistics(rs.getString("statistics"));

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Permite devolver a lista de jogadores registados
     *
     * @return lista de jogadores
     */
    @Override
    public List<Player> selectPlayers() {
        List<Player> list = new LinkedList<>();

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_SELECT_ALL)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                IndividualStatistics statistics = getStatistics(rs.getString("statistics"));
                if (!"".equals(rs.getString("idUser"))) {
                    list.add(getUser(rs.getInt("id"), statistics));
                } else {
                    list.add(getMachine(rs.getInt("id"), statistics));
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
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
        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_SELECT_USER)) {

            pstmt.setString(1, user);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            IndividualStatistics statistics = getStatistics(rs.getString("statistics"));
            return new User(rs.getInt("id"), rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getDate("birthdate").toLocalDate(),
                    statistics);

        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
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
        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_SELECT_EMAIL)) {

            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                return false;
            }
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    //ultimo id introduzido
    private int getLastId() {

        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_SELECT_LAST_ID)) {

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("max(id)");
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Permite descobrir pelo o name se existe um maquina
     *
     * @param name nome da maquina
     * @return Machine se encontrado, null o contrario
     */
    @Override
    public Machine findMachine(String name) {
        try (Connection sqlConnection = connect(); PreparedStatement pstmt = sqlConnection.prepareStatement(STATEMENT_SELECT_MACHINE)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            IndividualStatistics statistics = getStatistics(rs.getString("statistics"));
            return new Machine(rs.getInt("id"), statistics, rs.getString("name")
            );

        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
