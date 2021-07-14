package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Grid extends JPanel {

    public static Field[][] squares = new Field[51][51];
    public static int stage = 0; //0 = start, 1 = finish, 2 = walls, 3 = visualisation
    public static Field start = null;
    public static Field end = null;
    ClickListener clickListener;
    DragListener dragListener;

    public Grid(){
        this.setLayout(new GridLayout(51,51,-1,-1));

        clickListener = new ClickListener();
        dragListener = new DragListener();
        this.addMouseMotionListener(dragListener);
        this.addMouseListener(clickListener);
        this.setBackground(Color.LIGHT_GRAY);

        for(int y = 0; y < 51; y++){
            for(int x = 0; x < 51; x++) {
                Field field = new Field(x, y);
                field.setLocation(15*x,15*y);

                if (x == 0 || y == 0 || x == 50 || y == 50) {
                    field.setBackground(Color.BLACK);
                    field.setEnabled(false);
                }
                squares[x][y] = field;
                this.add(field);
            }
        }
        makeNeighbours();
    }

    public void makeNeighbours(){
        for(int i = 0; i < 51; i++){
            for(int j = 0; j < 51; j++){
                if(j > 0){
                    squares[j][i].neighbours.add(squares[j-1][i]);
                    if(i > 0)
                        squares[j][i].neighbours.add(squares[j-1][i-1]);
                    if(i < 50)
                        squares[j][i].neighbours.add(squares[j-1][i+1]);
                }
                if(j < 50){
                    squares[j][i].neighbours.add(squares[j+1][i]);
                    if(i > 0)
                        squares[j][i].neighbours.add(squares[j+1][i-1]);
                    if(i < 50)
                        squares[j][i].neighbours.add(squares[j+1][i+1]);
                }
                if(i > 0){
                    squares[j][i].neighbours.add(squares[j][i-1]);
                }
                if(i < 50){
                    squares[j][i].neighbours.add(squares[j][i+1]);
                }
            }
        }
    }

    public static Field[][] getGrid(){
        return squares;
    }

    public static int getStage(){
        return stage;
    }

    public static void updateStage(){
        stage++;
    }

    public static Field getStart(){
        return start;
    }

    public static Field getEnd(){
        return end;
    }


    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            int x = ((int) e.getPoint().getX()) / 15;
            int y = ((int) e.getPoint().getY()) / 15;

            if (x < 51 && y < 51 && squares[x][y].getBackground() == Color.WHITE) {
                if (stage == 0) {
                    squares[x][y].setBackground(Color.CYAN);
                    start = squares[x][y];
                    stage++;
                } else if (stage == 1) {
                    squares[x][y].setBackground(Color.orange);
                    end = squares[x][y];
                    stage++;
                } else if (stage == 2) {
                    squares[x][y].setBackground(Color.BLACK);
                }
            }
        }
    }

    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e){
            int x = ((int)e.getX())/15;
            int y = ((int)e.getY())/15;
            if(x < 51 && y < 51 && squares[x][y].getBackground() == Color.WHITE && stage == 2)
                squares[x][y].setBackground(Color.black);
        }
    }
}
