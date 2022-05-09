package domain;

import bootstrap.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Astar {
    private Cell start;
    private Cell dest;
    private  int size;
    private int[][] amaze;
    private List<Integer> walls;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    static Logger logger = LoggerFactory.getLogger(Astar.class);
    public Astar(int[][] maze, String[] args, List<Integer> walls) {
        this.amaze = maze;
        int startId = Integer.parseInt(Driver.getArgument(args, "start"));
        int destId = Integer.parseInt(Driver.getArgument(args, "dest"));
        this.size = Integer.parseInt(Driver.getArgument(args, "size"));
        this.walls = walls;
        this.start = new Cell(startId);
        this.dest = new Cell(destId);
    }

    public Cell findPath() {
        PriorityQueue<Cell> open = new PriorityQueue<Cell>(new CellComparator());
        HashSet<Cell> closed = new HashSet<>();
        start.setH(start.calcH(start.getId(), dest.getId(), size));
        start.setG(0);
        start.setF(start.calcH(start.getId(), dest.getId(), size));
        start.setParent(null);
        open.add(start);

        // Time Complexity O(n^3)
        while (!open.isEmpty()) {  //n+1
            Cell current = open.peek();
            logger.info("Current Cell :"+ current.getId()+ "g, h , f :"+ current.getG()+ ", "+ current.getH()+ ", "+ current.getF());
            open.remove(current);
            closed.add(current);
            if (current.getId() == dest.getId()) {
                return current;
            }

            List<Cell> neighbours = new ArrayList<>();
            neighbours = current.calcNeighbour(current, walls, size, dest.getId());

            for (Cell neighbour : neighbours) { // n*(n+1)
                logger.debug("Neighbour Cell :"+ neighbour.getId()+ "g, h , f :"+ neighbour.getG()+ ", "+ neighbour.getH()+ ", "+ neighbour.getF());
                Iterator<Cell> iterate1 = closed.iterator();
                boolean presentInClose = false;
                Cell cellInClosed = null;
                // Checking if there is same cellId in closed list
                while(iterate1.hasNext()) {  // n*n*(n+1)
                    cellInClosed = iterate1.next();
                    if(cellInClosed.getId()==neighbour.getId()){
                        presentInClose=true;
                        logger.debug("Closed set contains neighbour");
                        if(neighbour.getG()< cellInClosed.getG()){
                            logger.debug("Closed List contains neighbour and neighbour is nearer");
                            closed.remove(cellInClosed);
                            presentInClose = false;
                        }
                        break;
                    }
                }
                if(walls.contains(neighbour.getId())){
                    break;
                }
                Iterator<Cell> iterate = open.iterator();
                boolean presentInOpen = false;
                Cell cellInOpen = null;
                // checking if there is same cellId which has higher g value
                while(iterate.hasNext()) { // n*n*(n+1)
                    cellInOpen = iterate.next();
                    if(cellInOpen.getId()==neighbour.getId()){
                        logger.debug("Already present Neighbour Cell in Open list :"+ cellInOpen.getId()+ "g, h , f :"+ cellInOpen.getG()+ ", "+ cellInOpen.getH()+ ", "+ cellInOpen.getF());
                        if(neighbour.getG()<cellInOpen.getG()){
                            presentInOpen=true;
                            logger.debug("Open List contains neighbour and neighbour is nearer");
                            Iterator<Cell> iterate2 = open.iterator();

                            while(iterate2.hasNext()) {
                                Cell temp = iterate2.next();
                                if(temp.getId()==neighbour.getId()){
                                    open.remove(temp);
                                    presentInOpen=false;
                                    break;
                                }

                            }
                        }
                        break;
                    }
                }

                // If the cell is not present in closed or open list
                if(!presentInClose && !presentInOpen){
                    open.add(neighbour);
                    neighbour.setParent(current);
                }
            }
        }
        return null;
    }

    public void printPath(Cell result){
        JFrame mainFrame = new JFrame("Maze Solver");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(size, size));
        mainFrame.setLocationRelativeTo(null);
        if(result==null || walls.contains(start.getId())){
            logger.info("Invalid Path");
        }
        else{
            List<Integer> resList = new ArrayList<>();
            resList.add(result.getId());
            while(result.getParent() != null){
                result = result.getParent();
                resList.add(result.getId());
            }
            for(int i = 0 ; i<size; i++){
                for(int j=0; j<size; j++) {
                    if(walls.contains(amaze[i][j])){
                        JLabel label = makeLabel('X');
                        mainFrame.add(label);
                    }
                    else if(resList.contains(amaze[i][j])){
                        JLabel label = makeLabel('P');
                        mainFrame.add(label);
                    }
                    else{
                        JLabel label = makeLabel('A');
                        mainFrame.add(label);
                    }
                }
            }
            mainFrame.pack();
            mainFrame.setVisible(true);
        }
    }

    private JLabel makeLabel(char c) {

        JLabel label= new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(40, 40));
        switch(c) {
            case 'X':
                label.setBackground(Color.RED);
                break;
            case 'P':
                label.setBackground(Color.GREEN);
                break;
            case 'A':
                label.setBackground(Color.BLUE);
                break;
            default:
                label.setBackground(Color.WHITE);
                break;

        }
        label.setOpaque(true);
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        return label;
    }

}
