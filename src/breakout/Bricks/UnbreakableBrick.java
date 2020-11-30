package breakout.Bricks;

import breakout.Game;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class UnbreakableBrick extends Brick {

  public UnbreakableBrick(int hitsToBreak, Color color, Game game) {
    super(hitsToBreak, color, game);
  }

  @Override
  public void updateHealthDisplay(int currHealth) {
  }

  /**
   * Makes sure the unbreakable brick never breaks
   */
  public boolean checkIfBroke(Group root) {
    return false;
  }
}
