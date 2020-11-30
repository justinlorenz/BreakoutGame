package breakout;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StatusDisplay {

  public static final String TITLE = "Breakout";
  public static final int scoreDispX = 25;
  public static final int scoreDispY = 25;
  public static final int livesDispX = 650;
  public static final int livesDispY = 25;
  public static final int powerDispX = 25;
  public static final int powerDispY = 50;
  public static final int levelNumDispX = 650;
  public static final int levelNumDispY = 50;
  public static final int highestScoreDispX = 200;
  public static final int getHighestScoreDispY = 20;
  public static final String font = "Verdana";
  public static final int biggerText = 20;
  public static final int smallerText = 15;
  public static final Color redColor = Color.RED;
  private final Game game;
  private Progress myProgress;
  private Text scoreDisplay;
  private Text livesDisplay;
  private Text powerUpDisplay;
  private Text levelNumDisplay;
  private Text highestScoreDisplay;

  public StatusDisplay(Game game) {
    this.game = game;
    scoreDisplay = new Text();
    livesDisplay = new Text();
    powerUpDisplay = new Text();
    levelNumDisplay = new Text();
    highestScoreDisplay = new Text();
  }


  public void initializeProgress(int lives, int numBricks, int startingScore, int currLevel) {
    myProgress = new Progress(lives, numBricks, startingScore, game, currLevel);
  }

  public Progress getMyProgress() {
    return myProgress;
  }

  /**
   * Creates the new text display given all the information about where the text should be and what
   * it should look like/hold
   */
  public Text setTextForScene(int x, int y, String family, int size, Color color,
      String displayText, Text currDisplay) {
    game.getRoot().getChildren().remove(currDisplay);
    Text newDisplay;
    newDisplay = new Text(x, y, displayText);
    newDisplay.setFont(Font.font(family, size));
    newDisplay.setFill(color);
    game.getRoot().getChildren().add(newDisplay);
    return newDisplay;
  }

  /**
   * Displays and updates all the indicators that are shown during the game
   */
  public void displayProgress() {
    if (myProgress == null) {
      return;
    }
    scoreDisplay = setTextForScene(scoreDispX, scoreDispY, font, biggerText, redColor,
        "Score: " + myProgress.getScore(), scoreDisplay);
    livesDisplay = setTextForScene(livesDispX, livesDispY, font, biggerText, redColor,
        "Lives Left: " + myProgress.getLives(), livesDisplay);
    powerUpDisplay = setTextForScene(powerDispX, powerDispY, font, smallerText, redColor,
        "Current Power: " + game.getCurrPowerUpName(), powerUpDisplay);
    levelNumDisplay = setTextForScene(levelNumDispX, levelNumDispY, font, smallerText, redColor,
        "Level Number: " + myProgress.getLevelNum(), levelNumDisplay);
    highestScoreDisplay = setTextForScene(highestScoreDispX, getHighestScoreDispY, font,
        smallerText, redColor,
        "Current Highscore: " + game.getCurrScoreFile().getCurrHighestScore(), highestScoreDisplay);
  }

  public Text getScoreDisplay() {
    return scoreDisplay;
  }

  public Text getLivesDisplay() {
    return livesDisplay;
  }

  public Text getPowerUpDisplay() {
    return powerUpDisplay;
  }

  public Text getLevelNumDisplay() {
    return levelNumDisplay;
  }

  public Text getHighestScoreDisplay() {
    return highestScoreDisplay;
  }
}
