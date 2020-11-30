package breakout;

import static breakout.Bricks.Brick.BRICK_HEIGHT;
import static breakout.Bricks.Brick.BRICK_WIDTH;
import static breakout.Game.STARTING_BALL_XSPEED;
import static breakout.Game.STARTING_BALL_YSPEED;
import static breakout.Paddle.PADDLE_SIZE_LENGTH;

import breakout.Bricks.Brick;
import breakout.Powerups.PowerUpMaker;
import java.util.Iterator;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Ball {

  public static final double DISTANCE_TO_CENTER_LINE = 10.3;
  public static final double TOP_BOTTOM_COLLISION_OFFSET = 9.5;
  public static final Paint BALL_COLOR = Color.WHITE;
  public static final Paint EXTRA_BALL_COLOR = Color.LIMEGREEN;
  public static final boolean DONT_AUTO_CREATE = false;
  public static final int SPEED_SMOOTHNESS_MULTIPLIER = 60;

  private final Circle ballCircle;
  private final Rectangle collisionBox;
  private final double boxLength;
  private final Group root;
  private final Game myGame;
  private final boolean isCreated;
  private double xSpeed;
  private double ySpeed;

  /**
   * Creates a new Ball object specified as well as created a collision box that hovers over the ball for collisions
   */
  public Ball(double radius, double x, double y, Group root, double startingXSpeed,
      double startingYSpeed, int numTag, Game currGame, boolean created) {
    isCreated = created;
    myGame = currGame;
    ballCircle = new Circle();
    ballCircle.setRadius(radius);
    ballCircle.setCenterX(x);
    ballCircle.setCenterY(y);
    boxLength = 2 * radius;
    collisionBox = new Rectangle(boxLength, boxLength);
    if (isCreated) {
      ballCircle.setFill(EXTRA_BALL_COLOR);
    } else {
      ballCircle.setFill(BALL_COLOR);
    }
    ballCircle.setId("ball" + numTag);
    xSpeed = startingXSpeed;
    ySpeed = startingYSpeed;
    this.root = root;
    root.getChildren().add(ballCircle);
  }

  public Circle getBallCircle() {
    return ballCircle;
  }

  public double getXSpeed() {
    return xSpeed;
  }

  public double getYSpeed() {
    return ySpeed;
  }

  public void changeXSpeedTo(double newSpeed) {
    this.xSpeed = newSpeed;
  }

  public void changeYSpeedTo(double newSpeed) {
    this.ySpeed = newSpeed;
  }

  /**
   * Makes the ball move with current x and y velocity and tracks the collision box to the ball
   */
  public void moveBall() {
    ballCircle.setCenterY(
        ballCircle.getCenterY() + (ySpeed * SPEED_SMOOTHNESS_MULTIPLIER / Game.FRAMES_PER_SECOND));
    ballCircle.setCenterX(
        ballCircle.getCenterX() + (xSpeed * SPEED_SMOOTHNESS_MULTIPLIER / Game.FRAMES_PER_SECOND));
    collisionBox.setX(ballCircle.getCenterX() - ballCircle.getRadius());
    collisionBox.setY(ballCircle.getCenterY() - ballCircle.getRadius());
  }

  public Rectangle getCollisionBox() {
    return collisionBox;
  }

  /**
   * Checks if ball hits top wall
   */
  public void checkBallTopWallCollision() {
    if (collisionBox.getY() <= 0) {
      bounceOffCollision("Top");
    }
  }

  /**
   * Checks if hits left or right wall
   */
  public void checkBallSideWallCollision(int SIZE) {
    if (collisionBox.getX() <= 0) {
      bounceOffCollision("WallSide");
    } else if (collisionBox.getX() + boxLength >= SIZE) {
      bounceOffCollision("WallSide");
    }
  }

  /**
   * If ball hits bottom of screen, loses life and resets
   */
  public void checkIfBallOutBottom(int SIZE, int STARTING_BALL_Y, Progress currentProgress) {
    if (collisionBox.getY() + boxLength >= SIZE) {
      if (!isCreated) {
        currentProgress.lostLive();
      }
      ballCircle.setCenterY(STARTING_BALL_Y);
      ballCircle
          .setCenterX(myGame.getMyPaddle().getPaddleRectangle().getX() + PADDLE_SIZE_LENGTH / 2);
      xSpeed = STARTING_BALL_XSPEED;
      ySpeed = STARTING_BALL_YSPEED;
    }
  }


  /**
   * Checks if the ball has collided with any bricks. Updates all features based off result such as
   * creating a new powerup, ticking down the counter on a brick, update score if brick breaks, etc.
   */
  public void checkBrickCollision(LevelReader currentSetUp, Progress currentProgress,
      PowerUpMaker powerUp) {
    if (currentSetUp == null) {
      return;
    }
    List<Brick> collisionBricks = currentSetUp.returnBrickList();
    Iterator<Brick> brickIterator = collisionBricks.iterator();
    boolean hitBrick = false;
    while (brickIterator.hasNext()) {
      Brick currBrick = brickIterator.next();
      Shape intersection = Shape.intersect(collisionBox, currBrick.getBrickRectangle());
      if (intersection.getBoundsInLocal().getWidth() != -1) {
        if (currBrick.checkIfBroke(root)) {
          powerUp.createAPowerUp(currBrick.getXPos() + BRICK_WIDTH / 2,
              currBrick.getYPos() + BRICK_HEIGHT / 2, DONT_AUTO_CREATE);
          currentProgress.updateNumBricks(currBrick);
          brickIterator.remove();
        }
        if (!hitBrick) {
          hitBrick = true;
          checkTypeCollision(currBrick);
        }
      }
    }
  }

  /**
   * Changes the ball's current velocities based off the type of collision
   */
  public void bounceOffCollision(String collisionType) {
    switch (collisionType) {
      case "BrickSideLeft", "WallSide", "BrickSideRight" -> xSpeed *= -1;
      case "Top", "Bottom" -> ySpeed *= -1;
    }
  }


  /**
   * If there is a collision, finds the type of collision that has happened with brick using
   * the distance to the center of the brick as a measurement
   */
  public void checkTypeCollision(Brick collidedBrick) {
    double currBoxY = collisionBox.getY();
    double currBoxX = collisionBox.getX();
    double currBrickX = collidedBrick.getXPos();
    double currBrickY = collidedBrick.getYPos();
    double distance = Math.abs((currBoxY + boxLength / 2) - (currBrickY + BRICK_HEIGHT / 2));
    if (distance < DISTANCE_TO_CENTER_LINE) {
      if (currBoxX < currBrickX + BRICK_WIDTH / 2) {
        bounceOffCollision("BrickSideLeft");
      } else {
        bounceOffCollision("BrickSideRight");
      }
    } else if (currBoxY + TOP_BOTTOM_COLLISION_OFFSET > currBrickY + BRICK_HEIGHT) {
      bounceOffCollision("Bottom");
    } else if (currBoxY - TOP_BOTTOM_COLLISION_OFFSET < currBrickY) {
      bounceOffCollision("Top");
    }
  }
}
