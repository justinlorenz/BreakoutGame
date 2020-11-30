package breakout;

import static breakout.Game.*;

import breakout.Bricks.Brick;
import breakout.Menus.BeatLevelMenu;
import breakout.Menus.GameOverMenu;
import breakout.Menus.GameWonMenu;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Progress {
  public static final int NUMBER_OF_LEVELS = 3;
  public static final int QUESTION_X = 100;
  public static final int QUESTION_Y = 400;
  public static final String FONT = "Verdana";
  public static final int FONT_SIZE = 15;
  public static final Color FONT_COLOR = Color.GREENYELLOW;
  public static final int INPUT_WIDTH = 200;
  public static final int VBOX_SPACING = 10;
  public static final int VBOX_X = 250;
  public static final int VBOX_Y = 400;
  public static final int VBOX_PADDING = 20;
  public static final String STARTING_LEVEL_NUMBER = "1";
  public static final String QUESTION_TEXT = "Enter your name to save your score on the leaderboard (Hit Enter To Submit): ";

  private int totalScore;
  private int blocksLeft;
  private int numLives;
  private int scoreMultiplier;
  private Game currGame;
  private int currLevelNum;
  private TextField nameInput;
  private VBox layout;
  private Text question;
  private Group root;
  private List<String> allFilePaths;

  /**
   * Initializes new progress in the game by resetting the stats
   */
  public Progress(int startingLives,int startingBricks,int startingScore,Game game,int levelNum)  {
    currLevelNum = levelNum;
    currGame = game;
    numLives = startingLives;
    totalScore = startingScore;
    blocksLeft = startingBricks;
    scoreMultiplier = 1;
    allFilePaths = new ArrayList<>();
    findAllFilePaths();
  }

  /**
   * Change the score multiplier
   */
  public void updateScoreMultiplier(int offset) {
    scoreMultiplier = offset;
  }

  /**
   * Add to total score (used for cheat code)
   */
  public void addScore() {
    totalScore += scoreMultiplier;
  }

  /**
   * Adds to current score based off brick broken
   */
  public void addScore(Brick currBrick) {
    totalScore += scoreMultiplier * currBrick.getBrickStrength();
  }


  public int getLives() {
    return numLives;
  }

  /**
   * Checks to see if lost game after losing a life
   */
  public void lostLive() {
    numLives--;
    if (numLives <= 0)
      lostGame();
  }

  public void skipLevel() {
    wonLevel();
  }

  /**
   * Loads in the next level if you are still playing or ends the game if won
   */
    public void wonLevel() {
      movedUpLevel();
      if (currLevelNum > NUMBER_OF_LEVELS) {
            Scene gameWonScene = new GameWonMenu(currGame).setUpScene();
            currGame.makeMenuScene(gameWonScene);
        } else {
            String nextLevelFilePath = allFilePaths.get(currLevelNum - 1);
            loadUpNewLevel(nextLevelFilePath,currLevelNum++);
        }
    }

  /**
   * Finds all the corresponding level file paths based on the assumption noted that each level path
   * follows the same format as the STARTING_FILE_PATH inputted.
   */
  private void findAllFilePaths() {
    String fileNameStart = STARTING_LEVEL_FILEPATH.substring(0,STARTING_LEVEL_FILEPATH.indexOf(STARTING_LEVEL_NUMBER));
    String fileNameEnd = STARTING_LEVEL_FILEPATH.substring(STARTING_LEVEL_FILEPATH.indexOf(STARTING_LEVEL_NUMBER) + 1);
    for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
      String newLevelName = fileNameStart + (i + 1) + fileNameEnd;
      allFilePaths.add(newLevelName);
    }
  }

  public int getScore() {
    return totalScore;
  }

  /**
   * Ends the game and loads game over scene
   */
    public void lostGame() {
        Scene gameOverScene = new GameOverMenu(currGame).setUpScene();
        currGame.makeMenuScene(gameOverScene);
    }

  /**
   * Updates the score and number of bricks left when a brick is destroyed, checks if won level or not
   */
  public void updateNumBricks(Brick currBrick) {
    blocksLeft--;
    addScore(currBrick);
    if (blocksLeft <= 0)
      wonLevel();
  }

  /**
   * Takes in the user input from the Vbox to add to the scorefile
   */
  public void readInPlayersName(String playerName) {
    currGame.getCurrScoreFile().addScoreToScoreFile(playerName,totalScore);
  }

  public void addLives() {
    numLives++;
  }

  public int getLevelNum() {
    return currLevelNum;
  }

  /**
   * Creates the user input Vbox to be able to take in the player's name to have it stored in the scorefile
   */
  public void createUserInputField(Group root) {
    this.root = root;
    question = new Text(QUESTION_X, QUESTION_Y, QUESTION_TEXT);
    question.setFont(Font.font(FONT, FONT_SIZE));
    question.setFill(FONT_COLOR);
    nameInput = new TextField();
    nameInput.setPrefWidth(INPUT_WIDTH);
    layout = new VBox(VBOX_SPACING);
    layout.setLayoutX(VBOX_X);
    layout.setLayoutY(VBOX_Y);
    layout.setPadding(new Insets(VBOX_PADDING, VBOX_PADDING, VBOX_PADDING, VBOX_PADDING));
    layout.getChildren().addAll(nameInput);
    root.getChildren().add(layout);
    root.getChildren().add(question);
  }

  /**
   * Finds the user's entered input
   */
  public void grabUserInput() {
    String playerName = nameInput.getText();
    root.getChildren().removeAll(layout, question,nameInput);
    readInPlayersName(playerName);
  }

  public int getBlocksLeft() {
    return blocksLeft;
  }

  public void movedUpLevel() {
    currLevelNum++;
  }

  public VBox getLayout() {
    return layout;
  }

  public List<String> getAllFilePaths() {
    return allFilePaths;
  }

  /**
   * Loads up the specified new level
   */
  public void loadUpNewLevel(String newLevelName,int currLevelNum) {
    currGame.createNewLevel(newLevelName, totalScore, numLives, currLevelNum);
    currGame.resetBall();
    Scene gameWonScene = new BeatLevelMenu(currGame).setUpScene();
    currGame.makeMenuScene(gameWonScene);
    currGame.getPowerUpController().resetTotalPowerUps();
  }


}
