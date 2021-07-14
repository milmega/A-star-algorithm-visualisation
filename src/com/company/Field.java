package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class Field extends JLabel implements Comparable<Field>{

    public int x, y;
    public Field predecessor = null;
    public double g;
    public double f;
    public ArrayList<Field> neighbours = new ArrayList<>();

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
        this.setBackground(Color.WHITE);
        this.setVisible(true);
        this.setOpaque(true);
        this.setSize(15, 15);
        this.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
    }
    @Override
    public int compareTo(Field field){
        return Double.compare(this.f,field.f);
    }

    public double getH(Field target){
        return Math.sqrt(Math.pow(target.x-this.x,2) + Math.pow(target.y-this.y,2));
    }
}
