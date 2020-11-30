package breakout;

import static breakout.Game.FRAMES_PER_SECOND;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class Paddle {

  public static final Paint PADDLE_COLOR = Color.MAROON;
  public static final double PADDLE_SIZE_THICKNESS = 10;
  public static final double PADDLE_SIZE_LENGTH = 120;
  public static final double ENDING_ANGLE_FOR_MAP = 160;
  public static final double MINIMUM_ANGLE_OFFSET = 20;
  public static final int EXPONENT = 2;
  public static final int MOVE_OFF_WALL_OFFSET = 1;

  private final Rectangle paddleRectangle;
  private final double thisPaddleLength;
  private double changingPaddleSpeed = 0;

  /**
   * Creates a new paddle object and adds it to the game
   */
  public Paddle(double startingX, int verticalOffset, Group root, double paddleSizeLength) {
    // make the paddle shape ((0,0) represents the top left corner. x axis increases to right, y axis increases downwards)
    paddleRectangle = new Rectangle(startingX, verticalOffset,
        paddleSizeLength, PADDLE_SIZE_THICKNESS);
    thisPaddleLength = paddleSizeLength;
    paddleRectangle.setFill(PADDLE_COLOR);
    paddleRectangle.setId("paddle");
    // order added to the group is the order in which they are drawn (so last one is on top)
    root.getChildren().add(paddleRectangle);
  }

  public Rectangle getPaddleRectangle() {
    return this.paddleRectangle;
  }

  /**
   * Moves paddle according to the keyinput
   */
  public void movePaddle() {
    paddleRectangle.setX(paddleRectangle.getX() + changingPaddleSpeed / FRAMES_PER_SECOND);
  }

  public void setVel(double velocity) {
    changingPaddleSpeed = velocity;
  }

  /**
   * If the paddle hits a edge wall, make sure to stop it
   */
  public void checkPaddleWallCollision(int SIZE) {
    if (paddleRectangle.getX() <= 0) {
      paddleRectangle.setX(0);
    } else if (paddleRectangle.getX() + thisPaddleLength >= SIZE) {
      paddleRectangle.setX(SIZE - thisPaddleLength - MOVE_OFF_WALL_OFFSET);
    }
  }

  /**
   * If the ball hits the paddle, the paddle reflects the ball according to an angle map that was
   * created. As you hit more and more from the center, the ball reflects at smaller angle
   */
  public void checkPaddleBallCollision(Ball myBall) {
    // with shapes, can check precisely
    Circle ballCircle = myBall.getBallCircle();
    Rectangle collisionBox = myBall.getCollisionBox();
    Shape intersection = Shape.intersect(paddleRectangle, collisionBox);
    if (intersection.getBoundsInLocal().getWidth() != -1) {
      double angleMap = Math.toRadians(findAngleOffPaddle(ballCircle));
      double magnitudeSpeed = Math
          .sqrt(Math.pow(myBall.getXSpeed(), EXPONENT) + Math.pow(myBall.getYSpeed(), EXPONENT));
      myBall.changeXSpeedTo(magnitudeSpeed * Math.cos(angleMap));
      myBall.changeYSpeedTo(magnitudeSpeed * -1 * Math.sin(angleMap));
    }
  }

  /**
   * Maps the ball's current position to an angle that will be used to find the new x and y
   * velocity
   */
  public double findAngleOffPaddle(Circle ball) {
    double angleStep = (ENDING_ANGLE_FOR_MAP - MINIMUM_ANGLE_OFFSET) / thisPaddleLength;
    double xPosBall = paddleRectangle.getX() + thisPaddleLength - ball.getCenterX();
    return (xPosBall * angleStep) + MINIMUM_ANGLE_OFFSET;
  }

  public void removePaddle(Group root) {
    root.getChildren().remove(paddleRectangle);
  }

  public double getThisPaddleLength() {
    return thisPaddleLength;
  }
}
