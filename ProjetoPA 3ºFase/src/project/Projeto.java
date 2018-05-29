package project;


import model.Machine;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.PlayersManager;
import pattern.mvp.view.TabsViewer;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hugo
 */
public class Projeto extends Application {

    @Override
    public void start(Stage primaryStage) throws UnknownHostException {

        PlayersManager pm = new PlayersManager();

        Machine m = pm.getPlayersRegisted().findMachine(InetAddress.getLocalHost().getHostName());

        if (m == null) {
            m = new Machine(InetAddress.getLocalHost().getHostName());
            pm.getPlayersRegisted().insertPlayer(m);
        }
        pm.addPlayer(m);

        TabsViewer root = new TabsViewer(1, pm);

        Scene scene = new Scene(root, 600, 550);

        

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Pencil Game!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
