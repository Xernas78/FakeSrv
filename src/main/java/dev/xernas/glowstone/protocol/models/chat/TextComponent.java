package dev.xernas.glowstone.protocol.models.chat;

public class TextComponent extends Component {

    private String text;

    public TextComponent(String text) {
        this.text = text;
    }

    public Component setText(String text) {
        this.text = text;
        return this;
    }

    public String getText() {
        return text;
    }
}
