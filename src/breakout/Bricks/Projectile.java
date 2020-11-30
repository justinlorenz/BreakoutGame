package breakout.Bricks;

import static breakout.Game.SIZE;
import static breakout.Bricks.Brick.*;


import breakout.Game;
import breakout.Paddle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

public class Projectile {
  public static final int RADIUS_X = 4;
  public static final int RADIUS_Y = 10;
  public static final Color PROJECTILE_COLOR = Color.FIREBRICK;
  public static final int PROJECTILE_SPEED = 11;
  private Ellipse projectile;
  private Game myGame;

  /**
   * Creates a new projectile and adds to the game
   */
  public Projectile(int centerX, int centerY,Game game) {
    myGame = game;
    projectile = new Ellipse();
    projectile.setRadiusX(RADIUS_X);
    projectile.setRadiusY(RADIUS_Y);
    projectile.setCenterX(centerX);
    projectile.setCenterY(centerY + BRICK_HEIGHT);
    projectile.setFill(PROJECTILE_COLOR);
    projectile.setId("fallingProjectile");
    myGame.getRoot().getChildren().add(projectile);
    myGame.getAllProjectiles().add(this);
  }

  /**
   * Makes the falling spike fall towards the bottom of the screen
   */
  public void moveFallingSpike() {
    projectile.setCenterY(projectile.getCenterY() + PROJECTILE_SPEED);
  }

  /**
   * Checks to see if the projectile hit the paddle which would cause a life to be lost, else just destroys
   */
  public void checkProjectileCollision() {
    Paddle currPaddle = myGame.getMyPaddle();
    Shape intersection = Shape.intersect(currPaddle.getPaddleRectangle(), projectile);
    if (intersection.getBoundsInLocal().getWidth() != -1) {
      myGame.getStatusDisplay().getMyProgress().lostLive();
      destroySpike();
    } else if (projectile.getCenterY() + projectile.getRadiusY() >= SIZE) {
      destroySpike();
    }
  }

  /**
   * Destroys the spike from the game
   */
  public void destroySpike() {
    myGame.getRoot().getChildren().remove(projectile);
    myGame.getAllProjectiles().remove(this);
  }

  public Ellipse getProjectile() {
    return projectile;
  }
}
