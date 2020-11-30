package breakout.Bricks;

import breakout.Game;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class ProjectileBrick extends Brick {

  private final Game myGame;
  private Projectile fallingSpike;

  public ProjectileBrick(int hitsToBreak, Color color, Game game) {
    super(hitsToBreak, color, game);
    myGame = game;
  }

  /**
   * Creates a projectile spike to fall when this brick is broken
   */
  @Override
  public boolean checkIfBroke(Group root) {
    super.updateBrickHits();
    updateHealthDisplay(super.getBrickStrength() - super.getBrickHits());
    if (super.getBrickHits() >= super.getBrickStrength()) {
      root.getChildren().remove(super.getBrickRectangle());
      root.getChildren().remove(super.getCurrHealthDisplay());
      fallingSpike = new Projectile(super.getXPos() + BRICK_WIDTH / 2,
          super.getYPos() + BRICK_HEIGHT / 2, myGame);
      return true;
    }
    return false;
  }


}
