package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.PriorityQueue;

import static javax.swing.JOptionPane.showMessageDialog;

public class MyFrame extends JFrame implements KeyListener, ActionListener {

    public static double SQRT = 1.4142;

    public static Field[][] squares;
    private PriorityQueue<Field> open;
    private PriorityQueue<Field> closed;
    private Field start;
    private Field end;
    private double totalG;
    private Timer timer;
    private Boolean found = false;
    private Field temp;

    public MyFrame(){
        this.setTitle("A* algorithm visualisation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(785,810);
        this.setResizable(false);

        Grid grid = new Grid();
        squares = Grid.getGrid();

        this.add(grid);
        this.addKeyListener(this);
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 32){ //click space to start
            start = Grid.getStart();
            end = Grid.getEnd();

            if(Grid.getStage() == 2) {
                Grid.updateStage();
                search();
            }
        }
        if(e.getKeyCode() == 82){ //click r to reset
            reset();
        }
    }

    private void search(){

        open = new PriorityQueue<>();
        closed = new PriorityQueue<>();
        timer = new Timer(10, this);

        start.g = 0.0;
        start.f = start.g + start.getH(end);
        open.add(start);
        timer.start();
    }

    private void update(){

        if(open.isEmpty()) {
            showMessageDialog(this, "No path");
            timer.stop();
            return;
        }
        Field currCell = open.peek();

        if (currCell == end) {
            System.out.println("DONE!");
            found = true;
            temp = end.predecessor;
            return;
        }

        for(int i = 0; i < currCell.neighbours.size(); i++){
            Field currNeighbour = currCell.neighbours.get(i);

            if(currNeighbour.x != currCell.x && currNeighbour.y != currCell.y)
                totalG = currCell.g + 1.4141;
            else
                totalG = currCell.g + 1;

            Color c = currNeighbour.getBackground();
            if(c != Color.BLACK && c != Color.CYAN){
                if (!open.contains(currNeighbour) && !closed.contains(currNeighbour)) {
                    currNeighbour.predecessor = currCell;
                    currNeighbour.g = totalG;
                    currNeighbour.f = totalG + currNeighbour.getH(end);
                    open.add(currNeighbour);
                    if(currNeighbour.getBackground() != Color.ORANGE)
                        currNeighbour.setBackground(Color.GREEN);
                }else if(totalG < currNeighbour.g){
                    currNeighbour.predecessor = currCell;
                    currNeighbour.g = totalG;
                    currNeighbour.f = totalG + currNeighbour.getH(end); //getH(end) vs .h

                    if(closed.contains(currNeighbour)) {
                        closed.remove(currNeighbour);
                        open.add(currNeighbour);
                    }
                }
            }
        }

        open.remove(currCell);
        closed.add(currCell);

        Color c = currCell.getBackground();
        if (c != Color.BLACK && c != Color.CYAN)
            currCell.setBackground(Color.red);
    }

    private void paintPath(){
        if(temp == start){
            timer.stop();
        }else{
            temp.setBackground(Color.MAGENTA);
            temp = temp.predecessor;
        }
    }

    private void reset(){
        Main.window = new MyFrame();
        Grid.stage = 0;
        dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!found)
            update();
        else{
            end.setBackground(Color.orange);
            paintPath();
        }
    }
}
