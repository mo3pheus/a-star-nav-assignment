package domain;

import java.util.Comparator;

public class CellComparator implements Comparator<Cell> {
    @Override
    public int compare(Cell c1, Cell c2) {
        if (c1.getF() > c2.getF())
            return 1;
        else if (c1.getF() < c2.getF())
            return -1;
        else{
            if(c1.getH()> c2.getH())
                return  1;
            else if(c1.getH()<c2.getH()){
                return  -1;
            }
        }
        return 0;
    }
}
