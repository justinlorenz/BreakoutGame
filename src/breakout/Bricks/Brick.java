package breakout.Bricks;

import breakout.Game;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Brick {

  public static final int BRICK_WIDTH = 100;
  public static final int BRICK_HEIGHT = 30;
  public static final int healthDisplayXOffset = 5;
  public static final int getHealthDisplayYOffset = 10;
  public static final int fontSize = 10;
  public static final String font = "Verdana";
  public static final Color textColor = Color.WHITE;

  private final Paint brickColor;
  private final int brickStrength;
  private final Game myGame;
  private Rectangle brickRectangle;
  private int xPos;
  private int yPos;
  private int brickHits;
  private Text currHealthDisplay;
  private int healthDisplayX;
  private int healthDisplayY;

  public Brick(int hitsToBreak, Color color, Game game) {
    myGame = game;
    brickRectangle = new Rectangle(this.xPos, this.yPos, BRICK_WIDTH, BRICK_HEIGHT);
    brickColor = color;
    brickStrength = hitsToBreak;
  }

  public Rectangle getBrickRectangle() {
    return this.brickRectangle;
  }

  public int getBrickStrength() {
    return this.brickStrength;
  }

  public int getXPos() {
    return this.xPos;
  }

  public int getYPos() {
    return this.yPos;
  }

  /**
   * Builds a brick at given x and y
   */
  public void buildThisBrick(Group root, int numOfBrick, int x, int y) {
    xPos = x;
    yPos = y;
    healthDisplayX = x + healthDisplayXOffset;
    healthDisplayY = y + getHealthDisplayYOffset;
    brickRectangle = new Rectangle(xPos, yPos, BRICK_WIDTH, BRICK_HEIGHT);
    brickRectangle.setFill(brickColor);
    brickRectangle.setId("brick" + numOfBrick);
    root.getChildren().add(brickRectangle);
    updateHealthDisplay(brickStrength);
  }

  /**
   * Changes the life countdown of brick if hit
   */
  public void updateHealthDisplay(int currHealth) {
    myGame.getRoot().getChildren().remove(currHealthDisplay);
    currHealthDisplay = new Text(healthDisplayX, healthDisplayY, String.valueOf(currHealth));
    currHealthDisplay.setFont(Font.font(font, fontSize));
    currHealthDisplay.setFill(textColor);
    myGame.getRoot().getChildren().add(currHealthDisplay);
  }

  /**
   * See if the brick has broke when ball collides
   */
  public boolean checkIfBroke(Group root) {
    updateBrickHits();
    updateHealthDisplay(brickStrength - brickHits);
    if (brickHits >= brickStrength) {
      root.getChildren().remove(brickRectangle);
      root.getChildren().remove(currHealthDisplay);
      return true;
    }
    return false;
  }

  /**
   * Clears this brick
   */
  public void clearBrick() {
    myGame.getRoot().getChildren().remove(brickRectangle);
    myGame.getRoot().getChildren().remove(currHealthDisplay);
  }

  public void updateBrickHits() {
    brickHits++;
  }

  public int getBrickHits() {
    return brickHits;
  }

  public Text getCurrHealthDisplay() {
    return currHealthDisplay;
  }

}
