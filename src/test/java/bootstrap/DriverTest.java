package bootstrap;

import domain.Astar;
import domain.Cell;
import domain.CellComparator;
import domain.GenerateMaze;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

public class DriverTest {

    @Test
    public void ValidPath() {
        List<Integer> wallsList = new ArrayList<>();
        String arg = "--log.file.path executionLogs --log.level INFO --difficulty easy --start 3 --dest 58 --size 10";
        String[] args = arg.split(" ");
        GenerateMaze gmaze = new GenerateMaze(args);
        int[][] maze = gmaze.getMaze();
        Integer[] walls = new Integer[]{81, 82, 51, 83, 52, 53, 93};
        wallsList.addAll(Arrays.asList(walls));
        int destId = Integer.parseInt(Driver.getArgument(args, "dest"));
        Astar run = new Astar(maze, args, Arrays.asList(walls));
        Cell Result = run.findPath();
        Assert.assertEquals(destId, Result.getId());
    }

    @Test
    public void InvalidPath() {
        List<Integer> wallsList = new ArrayList<>();
        String arg = "--log.file.path executionLogs --log.level INFO --difficulty easy --start 3 --dest 58 --size 10";
        String[] args = arg.split(" ");
        GenerateMaze gmaze = new GenerateMaze(args);
        int[][] maze = gmaze.getMaze();
        Integer[] walls = new Integer[]{49,48,47,57,67,68,69};
        wallsList.addAll(Arrays.asList(walls));
        int destId = Integer.parseInt(Driver.getArgument(args, "dest"));
        Astar run = new Astar(maze, args, Arrays.asList(walls));
        Cell Result = run.findPath();
        Assert.assertEquals(null, Result);
    }

    @Test(expected = NumberFormatException.class)
    public void testInvalidArgs() {
        String arg = "--log.file.path executionLogs --log.level INFO --difficulty easy --start 3 --dest 58 --size";
        String[] args = arg.split(" ");
        int size = Integer.parseInt(Driver.getArgument(args, "size"));
    }

    @Test(expected = NumberFormatException.class)
    public void testInvalidArgs1() {
        String arg = "--log.file.path executionLogs --log.level INFO --difficulty easy --start --dest 58 --size 10";
        String[] args = arg.split(" ");
        int start = Integer.parseInt(Driver.getArgument(args, "start"));
    }

    @Test
    public void testCompare() {
        Cell c1 = new Cell(1);
        Cell c2 = new Cell(2);
        Cell c3 = new Cell(3);
        c1.setF(10);
        c2.setF(5);
        c3.setF(5);
        c2.setH(20);
        c3.setH(15);
        PriorityQueue<Cell> q = new PriorityQueue<>(new CellComparator());
        q.add(c1);
        q.add(c2);
        q.add(c3);
        Assert.assertEquals(c3, q.peek());
    }

    @Test
    public void testNeighbours() {
        List<Integer> wallsList = new ArrayList<>();
        Integer[] walls = new Integer[]{2, 13};
        wallsList.addAll(Arrays.asList(walls));
        Cell parent = new Cell(3);
        int destId = 53;
        int size = 10;
        HashSet<Integer> neighbour = new HashSet<>();
        neighbour.add(4);
        neighbour.add(12);
        neighbour.add(14);
        List<Cell> result = parent.calcNeighbour(parent, wallsList, size, destId);
        boolean flag = true;
        for(Cell c: result){
            if(!neighbour.contains(c.getId())){
                flag = false;
            }
        }
        Assert.assertTrue(flag);
    }

    @Test
    public void testHeuristic() {
        Cell c = new Cell(1);
        int startId = 3;
        int destId = 53;
        int size = 10;
        Assert.assertEquals(c.calcH(3, 53, 10), 50, 0);
    }

}
