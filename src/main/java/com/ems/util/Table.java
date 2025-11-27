package com.ems.util;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private int columns;
    private BorderStyle borderStyle;
    private List<String> cells;
    private int currentCell = 0;
    
    public Table(int columns, BorderStyle borderStyle) {
        this.columns = columns;
        this.borderStyle = borderStyle;
        this.cells = new ArrayList<>();
    }
    
    public void addCell(String content) {
        cells.add(content != null ? content : "");
    }
    
    public String render() {
        if (cells.isEmpty()) {
            return "";
        }
        
        // Calculate column widths
        int[] widths = new int[columns];
        for (int i = 0; i < cells.size(); i++) {
            int col = i % columns;
            widths[col] = Math.max(widths[col], cells.get(i).length());
        }
        
        StringBuilder sb = new StringBuilder();
        
        // Top border
        sb.append(borderStyle.topLeft);
        for (int i = 0; i < columns; i++) {
            sb.append(repeatChar(borderStyle.horizontal, widths[i] + 2));
            if (i < columns - 1) {
                sb.append(borderStyle.topJoin);
            }
        }
        sb.append(borderStyle.topRight).append("\n");
        
        // Rows
        int rows = (int) Math.ceil((double) cells.size() / columns);
        for (int row = 0; row < rows; row++) {
            sb.append(borderStyle.vertical);
            for (int col = 0; col < columns; col++) {
                int index = row * columns + col;
                String content = index < cells.size() ? cells.get(index) : "";
                sb.append(" ").append(padRight(content, widths[col])).append(" ");
                sb.append(borderStyle.vertical);
            }
            sb.append("\n");
            
            // Middle border after header row
            if (row == 0) {
                sb.append(borderStyle.leftJoin);
                for (int i = 0; i < columns; i++) {
                    sb.append(repeatChar(borderStyle.horizontal, widths[i] + 2));
                    if (i < columns - 1) {
                        sb.append(borderStyle.cross);
                    }
                }
                sb.append(borderStyle.rightJoin).append("\n");
            }
        }
        
        // Bottom border
        sb.append(borderStyle.bottomLeft);
        for (int i = 0; i < columns; i++) {
            sb.append(repeatChar(borderStyle.horizontal, widths[i] + 2));
            if (i < columns - 1) {
                sb.append(borderStyle.bottomJoin);
            }
        }
        sb.append(borderStyle.bottomRight);
        
        return sb.toString();
    }
    
    private String padRight(String s, int length) {
        return String.format("%-" + length + "s", s);
    }
    
    private String repeatChar(char ch, int count) {
        return String.valueOf(ch).repeat(Math.max(0, count));
    }
}
