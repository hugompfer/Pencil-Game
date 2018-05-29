package pattern.observer;

import enums.TypeOfGames;
import java.util.Observable;
import java.util.Observer;
import pattern.singleton.Logger;
import pattern.singleton.LoggerConfiguration.Options;
import pattern.singleton.LoggerException;
import model.ComputerGameEasy;
import model.Game;
import model.Player;
import model.PlayersManager;
import model.SimGame;

/**
 * Classe FinishGame que implementa da interface Observer (que ja provem do
 * Java) que ira observar o jogo, a decorrer e quando for notificado de que o
 * jogo acabou ele ira adicionar ao Logger as novas informacoes.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class FinishGame implements Observer {

    private final PlayersManager playersManager;

    public FinishGame(PlayersManager playersManager) {
        this.playersManager = playersManager;
    }
     /**
     * Permite atualizar o logger e as estatisticas.
     *
     * @param o objeto observável.
     * @param arg argumento a utilizar.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("Finish")) {
            Game game = (Game) o;
            String info = "Jogo Acabou - Duração:" + game.getGameTime() + " seguntos\n";
            info += " Vencedor: " + game.getWinnerPlayer() + "\n";
            info += " Perdedor: " + game.getLoserPlayer();
            updateStatistics(game);
            try {
                Logger.getInstance().writeToLog(info, Options.GAME);
            } catch (LoggerException ex) {
                //continua
            }
        }
    }
  
    private void updateStatistics(Game game) {
        Player winner = game.getWinnerPlayer();
        Player losser = game.getLoserPlayer();
        TypeOfGames type = getType(game);
        int gameTime = game.getGameTime();
        winner.getIndividualStatistics().incrementNumberGames(type);
        winner.getIndividualStatistics().incrementNumberOfWins(type);
        winner.getIndividualStatistics().incrementTotalOfTime(type, gameTime);
        losser.getIndividualStatistics().incrementNumberOfDefeats(type);
        losser.getIndividualStatistics().incrementNumberGames(type);
        losser.getIndividualStatistics().incrementTotalOfTime(type, gameTime);
        playersManager.getPlayersRegisted().updateStatistics(losser);
        playersManager.getPlayersRegisted().updateStatistics(winner);
    }

    private TypeOfGames getType(Game g) {
        if (g instanceof SimGame) {
            return TypeOfGames.SIMGAME;
        } else if (g instanceof ComputerGameEasy) {
            return TypeOfGames.EASYCOMPUTERGAME;
        } else {
            return TypeOfGames.HARDCOMPUTERGAME;
        }
    }
}
