import javafx.geometry.Rectangle2D;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

class TextFeld {
    private TextArea textArea = new TextArea();
    private DerClient client;

    TextFeld(DerClient client, Pane root, Rectangle2D size) {
        double width = size.getWidth();
        double height = size.getHeight();

        this.client = client;
        textArea.setEditable(false);
        textArea.setLayoutX(width / 20);
        textArea.setLayoutY(height / 20 * 17);
        textArea.setPrefRowCount(4);

        TextField textField = new TextField("deine Nachricht");
        textField.setLayoutX(width / 20);
        textField.setLayoutY(height / 40 * 39);

        textField.setOnAction((ae) -> {
            senden(textField.getText());
            textField.setText("");
        });

        root.getChildren().add(textArea);
        root.getChildren().add(textField);
    }

    private void senden(String text) {
        client.nachrichtSenden(text);
    }

    void empfangen(String text, String name) {
        textArea.appendText("\n" + name + ":\t" + text);
    }
}
