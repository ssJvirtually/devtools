package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.DiffUtil;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

public class DiffToolView extends VBox {

    private final CodeArea originalArea;
    private final CodeArea revisedArea;
    private final CodeArea diffArea;

    public DiffToolView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("Text Diff Checker");
        title.getStyleClass().add("tool-title");
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Button compareBtn = new Button("Compare");
        compareBtn.setOnAction(e -> compare());
        
        header.getChildren().addAll(title, spacer, compareBtn);

        originalArea = createEditor("Original text...");
        revisedArea = createEditor("Revised text...");
        diffArea = createEditor("Diff result appears here...");
        diffArea.setEditable(false);

        SplitPane topSplit = new SplitPane();
        topSplit.getItems().addAll(
                new VirtualizedScrollPane<>(originalArea),
                new VirtualizedScrollPane<>(revisedArea)
        );
        VBox.setVgrow(topSplit, Priority.ALWAYS);

        SplitPane mainSplit = new SplitPane();
        mainSplit.setOrientation(javafx.geometry.Orientation.VERTICAL);
        mainSplit.getItems().addAll(
                topSplit,
                new VirtualizedScrollPane<>(diffArea)
        );
        VBox.setVgrow(mainSplit, Priority.ALWAYS);

        getChildren().addAll(header, mainSplit);
    }

    private CodeArea createEditor(String placeholder) {
        CodeArea area = new CodeArea();
        area.setPlaceholder(new Label(placeholder));
        area.getStyleClass().add("code-editor");
        return area;
    }

    private void compare() {
        String result = DiffUtil.computeDiff(originalArea.getText(), revisedArea.getText());
        diffArea.replaceText(result);
    }
}
