package model;

import java.awt.*;

/**
 * 这个类主要用于包装Color对象（java自带对象），用于Chess游戏使用。
 */
    public enum ChessColor {
    BLACK("Black", Color.BLACK), WHITE("White", Color.WHITE), NONE("No Player", Color.WHITE);

    private final String name;
    private final Color color;//color是java里面自带类，同string

    ChessColor(String name, Color color) {//调用的时候就ChessColor xx= new ChessColor（ ， ）
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
