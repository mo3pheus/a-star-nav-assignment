package domain;

import bootstrap.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    static Logger logger = LoggerFactory.getLogger(GenerateMaze.class);

    public GenerateMaze(String[] args){
        this.configValues = args;
        this.size = Integer.parseInt(Driver.getArgument(configValues, "size"));
        String wallsString = Driver.getArgument(configValues, "walls");
        for (String wallId: wallsString.split(",")) {
            this.walls.add(Integer.parseInt(wallId));
        }
        int startId = Integer.parseInt(Driver.getArgument(configValues, "start"));
        int destId = Integer.parseInt(Driver.getArgument(configValues, "dest"));
        int[][] maze = new int[size][size];
        for(int i = 0 ; i<size; i++){
            for(int j=0; j<size; j++){
                maze[i][j]= id;
                id++;
            }
        }
        this.maze = maze;

        if(walls.contains(startId) || walls.contains(destId)){
            logger.info("Enter correct start and destination");
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
