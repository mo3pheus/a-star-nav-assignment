package domain;

import bootstrap.Driver;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private int id;
    private double g ;
    private double h;
    private double f;
    private Cell parent;
    private List<Cell> neighbour = new ArrayList<>();

    public Cell(int id) {
        this.id = id;
    }

    public List<Cell> calcNeighbour(Cell parent, List<Integer> walls, int size, int destId){
        int parentId = parent.getId();
        int row = parentId / size;
        int col = parentId % size;
        // Loop to get all the 8 neighbours of Cell
        for(int i = -1; i<2; i++ ){
            for(int j=-1; j<2; j++){
                int newId = (row+i)*size+(col+j);
                if(newId<0 || newId>size*size){
                    continue;
                }
                if((col == 0 && j==-1) || (col==(size-1) && j==1)){
                    continue;
                }
                if(row<0 || row>=size || col<0 || col>=size || (i==0 && j==0)){
                    continue;
                }
                else if(walls.contains(newId)){
                    continue;
                }
                else{
                    Cell c = new Cell(newId) ;
                    c.id = newId;
                    c.g = calcH(parentId, c.id, size)+parent.getG();
                    c.h = calcH(c.id , destId, size);
                    c.f = c.g + c.h;
                    c.setParent(parent);
                    neighbour.add(c);
                }

            }
        }
        this.neighbour= neighbour;
        return neighbour;
    }

    public double calcH(int cId, int destId, int size) {
        int cX = cId/size;
        int cY = cId%size;
        int  dX = destId/size;
        int dY = destId%size;
        int x = Math.abs(dX-cX);
        int y = Math.abs(dY-cY);
        return (10*(x+y)-6*(Math.min(x,y)));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cell getParent() {
        return parent;
    }

    public void setParent(Cell parent) {
        this.parent = parent;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public List<Cell> getNeighbour() {
        return neighbour;
    }

    public void setNeighbour(List<Cell> neighbour) {
        this.neighbour = neighbour;
    }
}
