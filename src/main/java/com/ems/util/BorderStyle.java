package com.ems.util;

public class BorderStyle {
    public char topLeft;
    public char topRight;
    public char bottomLeft;
    public char bottomRight;
    public char horizontal;
    public char vertical;
    public char cross;
    public char topJoin;
    public char bottomJoin;
    public char leftJoin;
    public char rightJoin;
    
    public static final BorderStyle UNICODE_ROUND_BOX_WIDE = new BorderStyle(
        '╭', '╮', '╰', '╯', '─', '│', '┼', '┬', '┴', '├', '┤'
    );
    
    public static final BorderStyle ASCII = new BorderStyle(
        '+', '+', '+', '+', '-', '|', '+', '+', '+', '+', '+'
    );
    
    private BorderStyle(char topLeft, char topRight, char bottomLeft, char bottomRight,
                       char horizontal, char vertical, char cross, char topJoin,
                       char bottomJoin, char leftJoin, char rightJoin) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.cross = cross;
        this.topJoin = topJoin;
        this.bottomJoin = bottomJoin;
        this.leftJoin = leftJoin;
        this.rightJoin = rightJoin;
    }
}
