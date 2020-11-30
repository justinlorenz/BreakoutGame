package breakout.Powerups;

import breakout.Game;
import javafx.scene.Group;

public class ScoreMultiplier extends PowerUp {

  public static final int NORMAL_SCORE_COUNTER = 1;
  public static final int SCORE_MULTIPLIED_COUNTER = 3;

  private final Game myGame;
  private final PowerUpMaker powerController;


  public ScoreMultiplier(String name, Group root, Game game, PowerUpMaker controller) {
    super(name, root, game, controller);
    myGame = game;
    powerController = controller;
  }

  /**
   * Gives a x3 score multiplier
   */
  @Override
  public void activatePowerUp() {
    myGame.getStatusDisplay().getMyProgress().updateScoreMultiplier(SCORE_MULTIPLIED_COUNTER);
  }

  /**
   * Resets the score multiplier back to 1
   */
  @Override
  public void deactivatePowerUp() {
    powerController.updatePowerUpHappening();
    myGame.getStatusDisplay().getMyProgress().updateScoreMultiplier(NORMAL_SCORE_COUNTER);
  }
}
