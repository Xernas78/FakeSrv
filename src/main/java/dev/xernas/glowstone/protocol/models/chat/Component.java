package dev.xernas.glowstone.protocol.models.chat;


import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public class Component {
    List<Component> extra;
    Boolean bold;
    Boolean italic;
    Boolean underlined;
    Boolean strikethrough;
    Boolean obfuscated;
    String color;

    public void addComponent(Component component) {
        if (extra == null) {
            extra = new ArrayList<>();
        }
        extra.add(component);
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }
    public void setItalic(boolean italic) {
        this.italic = italic;
    }
    public void setUnderlined(boolean underlined) {
        this.underlined = underlined;
    }
    public void setStrikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
    }
    public void setObfuscated(boolean obfuscated) {
        this.obfuscated = obfuscated;
    }
    public void setColor(Color color) {
        this.color = color.toString().toLowerCase();
    }

    public List<Component> getExtra() {
        return extra;
    }

    public boolean hasFormatting() {
        return color != null
                || bold != null
                || italic != null
                || underlined != null
                || strikethrough != null
                || obfuscated != null
                || (extra != null && !extra.isEmpty());
    }

}