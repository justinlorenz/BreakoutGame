package breakout;


import static breakout.Bricks.Brick.BRICK_HEIGHT;
import static breakout.Bricks.Brick.BRICK_WIDTH;

import breakout.Bricks.Brick;
import breakout.Bricks.ProjectileBrick;
import breakout.Bricks.UnbreakableBrick;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class LevelReader {

  public static final int BRICK_STARTING_VERTICAL_OFFSET = 50;
  public static final int BRICK_STARTING_HORIZONTAL_OFFSET = 50;
  public static final Color UNBREAKABLE_COLOR = Color.GRAY;
  public static final Color PROJECTILE_BRICK_COLOR = Color.RED;
  public static final int UNBREAKABLE_STRENGTH = 1;
  public static final int PROJECTILE_BRICK_STRENGTH = 2;
  public static final int BRICK_TYPE_INDEX = 0;
  public static final int BRICK_COLOR_INDEX = 1;
  public static final int REMOVE_BRICK_AT_INDEX = 0;
  private final Game myGame;
  private List<Brick> builtBricks;
  private List<List<Brick>> gameBricks;
  private int numOfImpossibles;
  private int numOfProjectiles;

  /**
   * Builds the level that is described by the given filepath
   */
  public LevelReader(Group root, String filePath, Game game) {
    myGame = game;
    readInLevelFile(filePath);
    buildTheScene(root);
  }

  /**
   * Loads in filepath to read level from
   */
  private Scanner makeScanner(String filePath) {
    File levelFile = new File(filePath);
    try {
      return new Scanner(levelFile);
    } catch (FileNotFoundException e) {
      System.out.println("That is not a valid file");
      return null;
    }
  }

  /**
   * Reads in all the strings that are present in the level file
   */
  private void readInLevelFile(String filePath) {
    Scanner scan = makeScanner(filePath);
    gameBricks = new ArrayList<>();
    while (scan != null && scan.hasNextLine()) {
      ArrayList<Brick> rowOfBricks = new ArrayList<>();
      String[] rowOfBrickStrings = scan.nextLine().split(" ");
      for (String brickString : rowOfBrickStrings) {
        Brick thisBrick = getBrickType(brickString);
        if (thisBrick != null) {
          rowOfBricks.add(thisBrick);
        }
      }
      gameBricks.add(rowOfBricks);
    }
  }

  /**
   * Takes in the read in level.txt input and finds the type of brick to be created
   */
  public Brick getBrickType(String brickString) {
    if (brickString.equals("")) {
      return null;
    }
    String[] brickData = brickString.split(",");
    switch (brickData[BRICK_TYPE_INDEX]) {
      case "unbreakable" -> {
        numOfImpossibles++;
        return new UnbreakableBrick(UNBREAKABLE_STRENGTH, UNBREAKABLE_COLOR, myGame);
      }
      case "projectile" -> {
        numOfProjectiles++;
        return new ProjectileBrick(PROJECTILE_BRICK_STRENGTH, PROJECTILE_BRICK_COLOR,
            myGame);
      }
      default -> {
        int brickStrength = Integer.parseInt(brickData[BRICK_TYPE_INDEX]);
        Color brickColor = Color.valueOf(brickData[BRICK_COLOR_INDEX]);
        return new Brick(brickStrength, brickColor, myGame);
      }
    }
  }

  /**
   * Clears all bricks in the current level
   */
  public void clearAllBricks() {
    for (Brick builtBrick : builtBricks) {
      builtBrick.clearBrick();
    }
  }

  /**
   * Clears a single brick, clears 1st brick ever created, 2nd, 3rd, so on in that order
   */
  public void clearSingleBrick() {
    builtBricks.remove(REMOVE_BRICK_AT_INDEX).clearBrick();
    if (builtBricks.size() <= 0) {
      myGame.getStatusDisplay().getMyProgress().wonLevel();
    }
  }

  public int getNumOfImpossibles() {
    return numOfImpossibles;
  }

  /**
   * Builds the bricks into the scene while also creating an arraylist of Brick objects
   */
  private void buildTheScene(Group root) {
    builtBricks = new ArrayList<>();
    for (int i = 0; i < gameBricks.size(); i++) {
      for (int j = 0; j < gameBricks.get(i).size(); j++) {
        Brick thisBrick = gameBricks.get(i).get(j);
        if (thisBrick.getBrickStrength() != 0) {
          int xPos = BRICK_WIDTH * j + BRICK_STARTING_HORIZONTAL_OFFSET;
          int yPos = BRICK_HEIGHT * i + BRICK_STARTING_VERTICAL_OFFSET;
          thisBrick.buildThisBrick(root, i * gameBricks.size() + j, xPos, yPos);
          builtBricks.add(thisBrick);
        }
      }
    }
  }

  public List<Brick> returnBrickList() {
    return builtBricks;
  }

  public int getNumOfProjectiles() {
    return numOfProjectiles;
  }


}
