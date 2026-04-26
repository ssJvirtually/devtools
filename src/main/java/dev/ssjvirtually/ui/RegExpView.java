package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.RegExpUtil;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import java.util.List;
import java.util.regex.Pattern;

public class RegExpView extends VBox {

    private final TextField regexField;
    private final CodeArea testTextArea;
    private final CodeArea resultsArea;
    private final Label errorLabel;
    
    private final CheckBox caseInsensitiveBox;
    private final CheckBox multilineBox;
    private final CheckBox dotAllBox;

    public RegExpView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("RegExp Tester");
        title.getStyleClass().add("tool-title");
        header.getChildren().add(title);

        VBox config = new VBox(10);
        Label regexLabel = new Label("Regular Expression:");
        regexField = new TextField();
        regexField.setPromptText("e.g. ([a-z]+)");
        regexField.textProperty().addListener((obs, oldVal, newVal) -> testRegex());

        HBox options = new HBox(15);
        caseInsensitiveBox = new CheckBox("Case Insensitive");
        multilineBox = new CheckBox("Multiline");
        dotAllBox = new CheckBox("DotAll");
        
        caseInsensitiveBox.setOnAction(e -> testRegex());
        multilineBox.setOnAction(e -> testRegex());
        dotAllBox.setOnAction(e -> testRegex());
        
        options.getChildren().addAll(caseInsensitiveBox, multilineBox, dotAllBox);
        config.getChildren().addAll(regexLabel, regexField, options);

        SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        
        testTextArea = new CodeArea();
        testTextArea.setPlaceholder(new Label("Enter text to test against..."));
        testTextArea.getStyleClass().add("code-editor");
        testTextArea.textProperty().addListener((obs, oldVal, newVal) -> testRegex());

        resultsArea = new CodeArea();
        resultsArea.setPlaceholder(new Label("Matches will appear here..."));
        resultsArea.getStyleClass().add("code-editor");
        resultsArea.setEditable(false);

        splitPane.getItems().addAll(
                new VirtualizedScrollPane<>(testTextArea),
                new VirtualizedScrollPane<>(resultsArea)
        );

        errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        getChildren().addAll(header, config, splitPane, errorLabel);
    }

    private void testRegex() {
        String regex = regexField.getText();
        String text = testTextArea.getText();

        if (regex == null || regex.isEmpty()) {
            resultsArea.replaceText("");
            clearError();
            return;
        }

        int flags = 0;
        if (caseInsensitiveBox.isSelected()) flags |= Pattern.CASE_INSENSITIVE;
        if (multilineBox.isSelected()) flags |= Pattern.MULTILINE;
        if (dotAllBox.isSelected()) flags |= Pattern.DOTALL;

        try {
            List<RegExpUtil.MatchResult> matches = RegExpUtil.findMatches(regex, text, flags);
            StringBuilder sb = new StringBuilder();
            sb.append("Found ").append(matches.size()).append(" matches:\n\n");
            
            for (int i = 0; i < matches.size(); i++) {
                RegExpUtil.MatchResult m = matches.get(i);
                sb.append("Match ").append(i + 1).append(" (").append(m.getStart()).append("-").append(m.getEnd()).append("):\n");
                sb.append("  Full: ").append(m.getContent()).append("\n");
                if (m.getGroups().size() > 1) {
                    for (int g = 1; g < m.getGroups().size(); g++) {
                        sb.append("  Group ").append(g).append(": ").append(m.getGroups().get(g)).append("\n");
                    }
                }
                sb.append("\n");
            }
            resultsArea.replaceText(sb.toString());
            clearError();
        } catch (Exception ex) {
            resultsArea.replaceText("");
            showError(ex.getMessage());
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    private void clearError() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }
}
