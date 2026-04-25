package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.StringUtil;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

public class StringInspectorView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;

    public StringInspectorView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("String Inspector");
        title.getStyleClass().add("tool-title");
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer);

        SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        inputArea = createEditor("Enter text to inspect...");
        inputArea.textProperty().addListener((obs, oldVal, newVal) -> inspect());
        
        outputArea = createEditor("Inspection results appear here...");
        outputArea.setEditable(false);
        
        splitPane.getItems().addAll(
                new VirtualizedScrollPane<>(inputArea),
                new VirtualizedScrollPane<>(outputArea)
        );

        getChildren().addAll(header, splitPane);
    }

    private CodeArea createEditor(String placeholder) {
        CodeArea area = new CodeArea();
        area.setPlaceholder(new Label(placeholder));
        area.getStyleClass().add("code-editor");
        return area;
    }

    private void inspect() {
        String input = inputArea.getText();
        String result = StringUtil.inspect(input);
        outputArea.replaceText(result);
    }
}
