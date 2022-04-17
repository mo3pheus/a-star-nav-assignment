package domain;

import bootstrap.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GenerateMaze {
    private int id=0;
    private String[] configValues;
    private int[][] maze;
    private String difficulty;
    private int size;
    private List<Integer> walls = new ArrayList<>();

    public GenerateMaze(String[] args){
        this.configValues = args;
        this.difficulty = Driver.getArgument(configValues, "difficulty");
        this.size = Integer.parseInt(Driver.getArgument(configValues, "size"));
        int startId = Integer.parseInt(Driver.getArgument(configValues, "start"));
        int destId = Integer.parseInt(Driver.getArgument(configValues, "dest"));
        int[][] maze = new int[size][size];
        for(int i = 0 ; i<size; i++){
            for(int j=0; j<size; j++){
                maze[i][j]= id;
                id++;
            }
        }
        if(this.difficulty.equals("easy")){
            Random rand = new Random();
            int r = 0;
            for(int i=0; i<20; i++){
                r = rand.nextInt(size*size) ;
                this.walls.add(r);
            }
        }
        else if(this.difficulty.equals("medium")){
            Random rand = new Random();
            int r = 0;
            for(int i=0; i<30; i++){
                r = rand.nextInt(size*size) ;
                this.walls.add(r);
            }
        }
        else if(this.difficulty.equals("hard")){
            Random rand = new Random();
            int r = 0;
            for(int i=0; i<40; i++){
                r = rand.nextInt(size*size) ;
                this.walls.add(r);
            }
        }
        else if(this.difficulty.equals("selfgenerate")){
            Scanner sc = new Scanner(System.in);
            while (!false) {
                System.out.println("Enter walls, press -1 to stop");
                int n = sc.nextInt();
                walls.add(n);
                if(n==-1){
                    break;
                }
            }
        }
        else{
            System.out.println("Select correct difficulty");
        }
        this.maze = maze;

        for(int i = 0 ; i<size; i++){
            for(int j=0; j<size; j++) {
                if(walls.contains(maze[i][j])){
                    System.out.print("X" + "\t");
                }
                else{
                    System.out.print(maze[i][j] + "\t");
                }
            }
            System.out.println("");
        }
        if(walls.contains(startId) || walls.contains(destId)){
            System.out.println("Enter correct start and destination");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getConfigValues() {
        return configValues;
    }

    public void setConfigValues(String[] configValues) {
        this.configValues = configValues;
    }

    public int[][] getMaze() {
        return maze;
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Integer> getWalls() {
        return walls;
    }

    public void setWalls(List<Integer> walls) {
        this.walls = walls;
    }
}
