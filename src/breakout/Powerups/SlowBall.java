package breakout.Powerups;

import static breakout.Game.STARTING_BALL_RADIUS;

import breakout.Game;
import javafx.scene.Group;

public class SlowBall extends PowerUp {

  public static final double SPEED_FACTOR = .5;
  private final Game myGame;
  private final PowerUpMaker powerController;

  public SlowBall(String name, Group root, Game game, PowerUpMaker controller) {
    super(name, root, game, controller);
    myGame = game;
    powerController = controller;
  }

  /**
   * Makes a new ball at SPEED_FACTOR * current velocity
   */
  @Override
  public void activatePowerUp() {
    makeANewBall(SPEED_FACTOR, STARTING_BALL_RADIUS, myGame.getAllBalls().get(0));
  }

  /**
   * Resets the ball's speed to what it was before by doing the inverse speed factor
   */
  @Override
  public void deactivatePowerUp() {
    powerController.updatePowerUpHappening();
    makeANewBall(1 / SPEED_FACTOR, STARTING_BALL_RADIUS, myGame.getAllBalls().get(0));
  }
}

