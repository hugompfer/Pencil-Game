package pattern.observer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import pattern.singleton.Logger;
import pattern.singleton.LoggerConfiguration;
import pattern.singleton.LoggerConfiguration.Options;
import pattern.singleton.LoggerException;
import model.Game;
import model.SimGame;

/**
 * Classe StartGame que implementa da interface Observer (que ja provem do Java)
 * que ira observar o jogo, a decorrer e quando for notificado de que o jogo
 * acabou ele ira adicionar ao Logger as novas informacoes.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class StartGame implements Observer {

    @Override
     /**
     * Permite atualizar o logger e as estatisticas.
     *
     * @param o objeto observável.
     * @param arg argumento a utilizar.
     */
    public void update(Observable o, Object arg) {
        if (arg.equals("Start")) {
            Game game = (Game) o;
            String gameType = (game instanceof SimGame) ? " Jogo SimGame( " + game.getPlayers()[0] + " vs " + game.getPlayers()[1]
                    + " )" : " jogo ComputerGame( " + game.getPlayers()[0] + " vs " + game.getPlayers()[1] + " ) \n";
            SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date dataAtual = new Date(System.currentTimeMillis());
            String info = "Inicio de Jogo " + sd.format(dataAtual) + " Tipo: " + gameType;
            try {
                Logger.getInstance().writeToLog(info, Options.GAME);
            } catch (LoggerException ex) {
                //continua mesmo assim
            }
        }
    }
}
