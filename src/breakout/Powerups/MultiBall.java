package breakout.Powerups;

import static breakout.Game.STARTING_BALL_RADIUS;

import breakout.Ball;
import breakout.Game;
import javafx.scene.Group;
import javafx.scene.shape.Circle;

public class MultiBall extends PowerUp {

  public static final int EXTRA_BALL_INDEX = 1;
  public static final int EXTRA_BALL_XOFFSET_POS = 10;
  public static final int EXTRA_BALL_XOFFSET_NEG = -10;
  public static final int EXTRA_BALL_TAG_ONE = 1;
  public static final int EXTRA_BALL_TAG_TWO = 2;


  private final Game myGame;
  private final Group root;
  private final PowerUpMaker powerController;

  public MultiBall(String name, Group root, Game game, PowerUpMaker controller) {
    super(name, root, game, controller);
    myGame = game;
    this.root = root;
    powerController = controller;
  }

  /**
   * Activates the multiball powerup when powerup hit paddle
   */
  @Override
  public void activatePowerUp() {
    createMultiBall(myGame.getAllBalls().get(FIRST_BALL_INDEX));
  }

  /**
   * Clears the two created balls
   */
  @Override
  public void deactivatePowerUp() {
    powerController.updatePowerUpHappening();
    myGame.removeBall(EXTRA_BALL_INDEX);
    myGame.removeBall(EXTRA_BALL_INDEX);

  }

  /**
   * Creates 2 more identical balls that are offset from the original ball
   */
  public void createMultiBall(Ball currBall) {
    Circle ballCircle = currBall.getBallCircle();
    double currXSpeed = currBall.getXSpeed();
    double currYSpeed = currBall.getYSpeed();
    myGame.getAllBalls().add(myGame
        .createNewBall(STARTING_BALL_RADIUS, ballCircle.getCenterX() + EXTRA_BALL_XOFFSET_POS,
            ballCircle.getCenterY(), root,
            currXSpeed, currYSpeed, EXTRA_BALL_TAG_ONE, CREATED_BALL));
    myGame.getAllBalls().add(myGame
        .createNewBall(STARTING_BALL_RADIUS, ballCircle.getCenterX() + EXTRA_BALL_XOFFSET_NEG,
            ballCircle.getCenterY(), root,
            currXSpeed, currYSpeed, EXTRA_BALL_TAG_TWO, CREATED_BALL));
  }

}
