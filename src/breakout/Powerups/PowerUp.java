package breakout.Powerups;

import static breakout.Bricks.Brick.BRICK_HEIGHT;
import static breakout.Game.SIZE;

import breakout.Ball;
import breakout.Game;
import breakout.Paddle;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class PowerUp {

  public static final int POWER_UP_BALL_RADIUS = 5;
  public static final Paint POWER_UP_BALL_COLOR = Color.ORANGE;
  public static final double POWER_UP_DROP_SPEED = 4;
  public static final boolean CREATED_BALL = true;
  public static final int FIRST_BALL_INDEX = 0;

  private final String powerUpName;
  private final Game myGame;
  private final Group root;
  private final PowerUpMaker powerController;
  private Circle powerUpBall;


  public PowerUp(String name, Group root, Game game, PowerUpMaker controller) {
    myGame = game;
    powerUpName = name;
    this.root = root;
    powerController = controller;
  }

  /**
   * Creates a new orange powerup ball at the given x and y to fall down
   */
  public void createPowerUpBall(int centerX, int centerY) {
    powerUpBall = new Circle();
    powerUpBall.setRadius(POWER_UP_BALL_RADIUS);
    powerUpBall.setCenterX(centerX);
    powerUpBall.setCenterY(centerY + BRICK_HEIGHT);
    powerUpBall.setFill(POWER_UP_BALL_COLOR);
    powerUpBall.setId("powerUpBall");
    root.getChildren().add(powerUpBall);
  }

  /**
   * Initializes that a new power up is needed
   */
  public void newPowerUp(int centerX, int centerY) {
    powerController.updatePowerUpFalling();
    powerController.updateTotalPowerUps();
    createPowerUpBall(centerX, centerY);
  }

  /**
   * Moves the power up ball down and checks to see if collides with paddle, if does activate else
   * destroy power up
   */
  public void movePowerUpBall() {
    Paddle currPaddle = myGame.getMyPaddle();
    Shape intersection = Shape.intersect(currPaddle.getPaddleRectangle(), powerUpBall);
    if (intersection.getBoundsInLocal().getWidth() != -1) {
      powerController.updatePowerUpHappening();
      activatePowerUp();
      destroyPowerUp();
    } else if (powerUpBall.getCenterY() + POWER_UP_BALL_RADIUS >= SIZE) {
      destroyPowerUp();
    }
    powerUpBall.setCenterY(powerUpBall.getCenterY() + POWER_UP_DROP_SPEED);
  }

  /**
   * Gets rid of the falling power up ball
   */
  public void destroyPowerUp() {
    root.getChildren().remove(powerUpBall);
    powerController.updatePowerUpFalling();
  }

  public Circle getPowerUpBall() {
    return powerUpBall;
  }

  public void activatePowerUp() {
  }

  public void deactivatePowerUp() {
  }

  public String getPowerUpName() {
    return powerUpName;
  }

  /**
   * Creates a new ball in the game
   */
  public void makeANewBall(double speedFactor, double newRadius, Ball currBall) {
    Circle ballCircle = myGame.getAllBalls().get(FIRST_BALL_INDEX).getBallCircle();
    double currXSpeed = currBall.getXSpeed();
    double currYSpeed = currBall.getYSpeed();
    myGame.removeBall(FIRST_BALL_INDEX);
    myGame.getAllBalls().add(myGame
        .createNewBall(newRadius, ballCircle.getCenterX(), ballCircle.getCenterY(), root,
            currXSpeed * speedFactor, currYSpeed * speedFactor, FIRST_BALL_INDEX, !CREATED_BALL));
  }
}
