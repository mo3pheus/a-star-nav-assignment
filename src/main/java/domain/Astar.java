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

        while (!open.isEmpty()) {
            Cell current = open.peek();
            logger.info("Current Cell :"+ current.getId()+ "g, h , f :"+ current.getG()+ ", "+ current.getH()+ ", "+ current.getF());
            open.remove(current);
            closed.add(current);
            if (current.getId() == dest.getId()) {
                return current;
            }

            List<Cell> neighbour = new ArrayList<>();
            neighbour = current.calcNeighbour(current, walls, size, dest.getId());

            for (Cell n : neighbour) {
                logger.debug("Neighbour Cell :"+ n.getId()+ "g, h , f :"+ n.getG()+ ", "+ n.getH()+ ", "+ n.getF());
                Iterator<Cell> iterate1 = closed.iterator();
                boolean presInClose = false;
                Cell nc = null;
                while(iterate1.hasNext()) {
                    nc = iterate1.next();
                    if(nc.getId()==n.getId()){
//                        presInClose = true;
                        presInClose=true;
                        logger.debug("Closed set contains n");
                        if(n.getG()<nc.getG()){
                            logger.debug("Closed List contains n and n is nearer");
                            closed.remove(nc);
                            presInClose = false;
                        }
                        break;
                    }
                }
                if(walls.contains(n.getId())){
                    break;
                }
                Iterator<Cell> iterate = open.iterator();
                boolean presInOpen = false;
                Cell n1 = null;
                while(iterate.hasNext()) {
                    n1 = iterate.next();
                    if(n1.getId()==n.getId()){
                        logger.debug("Already present Neighbour Cell in Open list :"+ n1.getId()+ "g, h , f :"+ n1.getG()+ ", "+ n1.getH()+ ", "+ n1.getF());
                        if(n.getG()<n1.getG()){
                            presInOpen=true;
                            logger.debug("Open List contains n and n is nearer");
                            Iterator<Cell> iterate2 = open.iterator();

                            while(iterate2.hasNext()) {
                                Cell temp = iterate2.next();
                                if(temp.getId()==n.getId()){
                                    open.remove(temp);
                                    presInOpen=false;
                                    break;
                                }

                            }
                        }
                        break;
                    }
                }


                if(!presInClose && !presInOpen){
                    open.add(n);
                    n.setParent(current);
                }
            }
//            open.remove(current);
//            closed.add(current);
        }
        return null;
    }

    public void printPath(Cell result){
        JFrame mainFrame = new JFrame("Maze Solver");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(size, size));
        mainFrame.setLocationRelativeTo(null);
        if(result==null || walls.contains(start.getId())){
            System.out.println("Invalid");
        }
        else{
            List<Integer> resList = new ArrayList<>();
            resList.add(result.getId());
            while(result.getParent() != null){
                result = result.getParent();
                resList.add(result.getId());
            }
            System.out.println("");
            for(int i = 0 ; i<size; i++){
                for(int j=0; j<size; j++) {
                    if(walls.contains(amaze[i][j])){
                        System.out.print(ANSI_RED+"X" + "\t"+ANSI_RESET);
                        JLabel label = makeLabel('X');
                        mainFrame.add(label);
                    }
                    else if(resList.contains(amaze[i][j])){
                        System.out.print(ANSI_GREEN+"P" + "\t"+ANSI_RESET);
                        JLabel label = makeLabel('P');
                        mainFrame.add(label);
                    }
                    else{
                        System.out.print(amaze[i][j] + "\t");
                        JLabel label = makeLabel('A');
                        mainFrame.add(label);
                    }
                }
                System.out.println("");
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
