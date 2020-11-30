package breakout.Powerups;

import static breakout.Game.PADDLE_VERTICAL_OFFSET;
import static breakout.Paddle.PADDLE_SIZE_LENGTH;

import breakout.Game;
import breakout.Paddle;
import javafx.scene.Group;

public class LongPaddle extends PowerUp {

  public static final double BIG_PADDLE_LENGTH = PADDLE_SIZE_LENGTH * 1.5;

  private final Game myGame;
  private final Group root;
  private final PowerUpMaker powerController;

  public LongPaddle(String name, Group root, Game game, PowerUpMaker controller) {
    super(name, root, game, controller);
    myGame = game;
    this.root = root;
    powerController = controller;
  }

  /**
   * Creates a longer paddle when power up activated
   */
  @Override
  public void activatePowerUp() {
    Paddle currPaddle = myGame.getMyPaddle();
    currPaddle.removePaddle(root);
    myGame.createNewPaddle(currPaddle.getPaddleRectangle().getX(), PADDLE_VERTICAL_OFFSET, root,
        BIG_PADDLE_LENGTH);
  }

  /**
   * Resets paddle back to original size
   */
  @Override
  public void deactivatePowerUp() {
    powerController.updatePowerUpHappening();
    Paddle currPaddle = myGame.getMyPaddle();
    currPaddle.removePaddle(root);
    myGame.createNewPaddle(myGame.getMyPaddle().getPaddleRectangle().getX(), PADDLE_VERTICAL_OFFSET,
        root, PADDLE_SIZE_LENGTH);
  }
}
