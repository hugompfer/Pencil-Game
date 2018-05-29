/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

/**
 *
 * @author Tiago
 */
public class MyTab extends Tab {

    public MyTab(String label, Node node) {

        setClosable(false);

        Label lab = new Label(label);

        setGraphic(lab);

        setOnSelectionChanged(e -> {
            setStyle("-fx-background-color: black; -fx-opacity: 0.7;");
        });

        setContent(node);
    }
}
