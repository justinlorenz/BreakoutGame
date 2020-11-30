package breakout;

import static breakout.Paddle.PADDLE_SIZE_LENGTH;

import breakout.Bricks.Projectile;
import breakout.Menus.Menu;
import breakout.Menus.StartMenu;
import breakout.Powerups.PowerUp;
import breakout.Powerups.PowerUpMaker;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Game extends Application {

  public static final int SIZE = 800;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final Paint BACKGROUND = Color.BLACK;
  public static final int PADDLE_VERTICAL_OFFSET = SIZE - 50;
  public static final int STARTING_BALL_X = SIZE - 400;
  public static final int STARTING_BALL_Y = SIZE - 400;
  public static final int STARTING_BALL_RADIUS = 5;
  public static final double STARTING_BALL_XSPEED = 0;
  public static final double STARTING_BALL_YSPEED = 10;
  public static final double PADDLE_SPEED = 600;
  public static final int STARTING_LIVES = 3;
  public static final int POWER_UP_LENGTH_MILLIS = 8000;
  public static final double PADDLE_STARTING_X = SIZE / 2 - PADDLE_SIZE_LENGTH / 2;
  public static final int STARTING_SCORE = 0;
  public static final int STARTING_LEVEL_NUMBER = 1;
  public static final String STARTING_LEVEL_FILEPATH = "blockLevels/level1.txt/";
  public static final boolean AUTO_CREATE = true;
  public static final int FIRST_BALL_INDEX = 0;
  public static final int FIRST_LEVEL_INDEX = 0;
  public static final int SECOND_LEVEL_INDEX = 1;
  public static final int THIRD_LEVEL_INDEX = 2;


  private final StatusDisplay statusDisplay = new StatusDisplay(this);
  private Paddle myPaddle;
  private List<Ball> allBalls;
  private List<Projectile> allProjectiles;
  private Group root;
  private LevelReader myLevel;
  private Timeline animation;
  private Boolean isPaused = true;
  private PowerUpMaker powerUpController;
  private PowerUp currPowerUp;
  private long currTimeSegment;
  private long startingPowerUpTime;
  private boolean timeSegmentRunning;
  private HighScoreFile currScoreFile;
  private Scene gameScene;
  private Stage myStage;
  private Menu myMenu;


  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    myStage = stage;
    myMenu = new StartMenu(this);
    makeMenuScene(myMenu.setUpScene());
  }


  public void makeMenuScene(Scene menuScene) {
    myStage.setScene(menuScene);
    myStage.setTitle(StatusDisplay.TITLE);
    myStage.show();
  }

  public void makeGameScene() {
    gameScene = setUpGameScene();
    createNewLevel(STARTING_LEVEL_FILEPATH, STARTING_SCORE, STARTING_LIVES, STARTING_LEVEL_NUMBER);
    myStage.setScene(gameScene);
    myStage.setTitle(StatusDisplay.TITLE);
    myStage.show();
    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step());
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    pauseGame();
    animation.play();
  }

  public void endAnimation() {
    animation.stop();
  }

  /**
   * Builds a new level given the level filepath and score, lives, and level num
   */
  public void createNewLevel(String currFilePath, int startingScore, int currLives, int currLevel) {
    myLevel = new LevelReader(root, currFilePath, this);
    statusDisplay.initializeProgress(currLives,
        myLevel.returnBrickList().size() - myLevel.getNumOfImpossibles(), startingScore, currLevel);
  }


  /**
   * Creates a new game scene
   */
  public Scene setUpGameScene() {
    root = new Group();
    allBalls = new ArrayList<>();
    allProjectiles = new ArrayList<>();
    Scene scene = new Scene(root, SIZE, SIZE, BACKGROUND);
    currScoreFile = new HighScoreFile();
    setKeyInput(scene);
    createNewPaddle(PADDLE_STARTING_X, PADDLE_VERTICAL_OFFSET, root, PADDLE_SIZE_LENGTH);
    allBalls.add(createNewBall(STARTING_BALL_RADIUS, STARTING_BALL_X, STARTING_BALL_Y, root,
        STARTING_BALL_XSPEED, STARTING_BALL_YSPEED, FIRST_BALL_INDEX, !AUTO_CREATE));
    powerUpController = new PowerUpMaker(root, this);
    currTimeSegment = 0;
    timeSegmentRunning = false;
    return scene;
  }


  void step() {
    if (!isPaused) {
      myPaddle.movePaddle();
      moveAllBalls();
      updateShapes();
      statusDisplay.displayProgress();
    }
  }

  public void setStage(Stage stage) {
    myStage = stage;
  }

  /**
   * Checks all collisions as well as powerups being activated/deactivated
   */
  private void updateShapes() {
    for (Ball currBall : allBalls) {
      myPaddle.checkPaddleWallCollision(SIZE);
      myPaddle.checkPaddleBallCollision(currBall);
      currBall.checkBallTopWallCollision();
      currBall.checkIfBallOutBottom(SIZE, STARTING_BALL_Y, statusDisplay.getMyProgress());
      currBall.checkBallSideWallCollision(SIZE);
      currBall.checkBrickCollision(myLevel, statusDisplay.getMyProgress(), powerUpController);
    }
    checkForPowerUps();
    checkForProjectiles();
  }

  /**
   * Move current balls in the game with respective x and y velocities
   */
  private void moveAllBalls() {
    for (Ball currBall : allBalls) {
      currBall.moveBall();
    }
  }

  /**
   * Checks if any projectiles spawned and if so, moves it to bottom of screen and checks for paddle
   * collision
   */
  private void checkForProjectiles() {
    if (!allProjectiles.isEmpty()) {
      for (Projectile currProjectile : allProjectiles) {
        currProjectile.moveFallingSpike();
        currProjectile.checkProjectileCollision();
        break;
      }
    }
  }

  /**
   * Checks to see if there is a power up happening, if there is it starts an internal timer
   * for the length of the powerup in seconds
   */
  private void checkForPowerUps() {
    if (powerUpController.getPowerUpFalling()) {
      currPowerUp = powerUpController.getCurrPowerUp();
      currPowerUp.movePowerUpBall();
    } else if (powerUpController.getPowerUpHappening() && !timeSegmentRunning) {
      timeSegmentRunning = true;
      saveTime();
    } else if (powerUpController.getPowerUpHappening() && timeSegmentRunning) {
      isPowerUpDone();
    }
  }

  /**
   * Method that checks if current power up time segment > allowed time segment and deactivates
   * power if it is
   */
  private void isPowerUpDone() {
    currTimeSegment = System.currentTimeMillis();
    System.out.println(currTimeSegment - startingPowerUpTime);
    if (currTimeSegment - startingPowerUpTime > POWER_UP_LENGTH_MILLIS) {
      currPowerUp.deactivatePowerUp();
      timeSegmentRunning = false;
    }
  }

  private void saveTime() {
    startingPowerUpTime = System.currentTimeMillis();
  }

  /**
   * Holds the keyinput for the game such as the movement of the paddle as well as test and cheat keys.
   */
  private void setKeyInput(Scene scene) {
    scene.setOnKeyPressed(event -> {
      switch (event.getCode()) {
        case LEFT -> myPaddle.setVel(-PADDLE_SPEED);
        case RIGHT -> myPaddle.setVel(PADDLE_SPEED);
        case X -> {
          endAnimation();
          makeGameScene();
        }
        case R -> resetBall();
        case SPACE -> togglePause();
        case L -> statusDisplay.getMyProgress().addLives();
        case P -> {
          if (!powerUpController.getPowerUpFalling() && !timeSegmentRunning && !powerUpController
              .getPowerUpHappening()) {
            powerUpController.createAPowerUp(STARTING_BALL_X, STARTING_BALL_Y, AUTO_CREATE);
          }
        }
        case U -> statusDisplay.getMyProgress().addScore();
        case N -> {
          if (myLevel.returnBrickList() != null) {
            myLevel.clearAllBricks();
          }
          statusDisplay.getMyProgress().skipLevel();
        }
        case D -> {
          if (myLevel.returnBrickList() != null) {
            myLevel.clearSingleBrick();
          }
        }
        case Q -> statusDisplay.getMyProgress()
            .loadUpNewLevel(statusDisplay.getMyProgress().getAllFilePaths().get(FIRST_BALL_INDEX),
                FIRST_LEVEL_INDEX + 1);
        case W -> statusDisplay.getMyProgress()
            .loadUpNewLevel(statusDisplay.getMyProgress().getAllFilePaths().get(SECOND_LEVEL_INDEX),
                SECOND_LEVEL_INDEX + 1);
        case E -> statusDisplay.getMyProgress()
            .loadUpNewLevel(statusDisplay.getMyProgress().getAllFilePaths().get(THIRD_LEVEL_INDEX),
                THIRD_LEVEL_INDEX + 1);
      }
    });
    scene.setOnKeyReleased(event -> {
      switch (event.getCode()) {
        case LEFT, RIGHT -> myPaddle.setVel(0);
      }
    });
  }

  /**
   * Resets the ball back to starting position and pauses in curr level
   */
  public void resetBall() {
    Rectangle paddle = myPaddle.getPaddleRectangle();
    paddle.setX(SIZE / 2 - PADDLE_SIZE_LENGTH / 2);
    Circle ballCircle = allBalls.get(FIRST_BALL_INDEX).getBallCircle();
    ballCircle.setCenterX(STARTING_BALL_X);
    ballCircle.setCenterY(STARTING_BALL_Y);
    allBalls.get(FIRST_BALL_INDEX).changeXSpeedTo(STARTING_BALL_XSPEED);
    allBalls.get(FIRST_BALL_INDEX).changeYSpeedTo(STARTING_BALL_YSPEED);
    pauseGame();
  }


  public void initializeNewLevel() {
    myStage.setScene(gameScene);
    resetBall();
  }


  public void togglePause() {
    isPaused = !isPaused;
  }

  public void pauseGame() {
    isPaused = true;
  }

  public void resumeGame() {
    isPaused = false;
  }


  /**
   * Creates a new ball object
   */
  public Ball createNewBall(double startingRadius, double startingX, double startingY, Group root,
      double startXSpeed, double startYSpeed, int num, boolean created) {
    return new Ball(startingRadius, startingX, startingY, root, startXSpeed, startYSpeed, num, this,
        created);
  }

  /**
   * Creates a new paddle object
   */
  public void createNewPaddle(double startingX, int verticalOffset, Group root,
      double paddleLength) {
    myPaddle = new Paddle(startingX, verticalOffset, root, paddleLength);
  }

  public boolean isTimeSegmentOn() {
    return timeSegmentRunning;
  }

  public Group getRoot() {
    return root;
  }

  public void removeBall(int index) {
    root.getChildren().remove(allBalls.get(index).getBallCircle());
    allBalls.remove(index);
  }

  public List<Ball> getAllBalls() {
    return allBalls;
  }

  public List<Projectile> getAllProjectiles() {
    return allProjectiles;
  }


  public Paddle getMyPaddle() {
    return myPaddle;
  }

  public StatusDisplay getStatusDisplay() {
    return statusDisplay;
  }

  public String getCurrPowerUpName() {
    if (powerUpController.getPowerUpHappening()) {
      return powerUpController.getCurrPowerUp().getPowerUpName();
    }
    return "";
  }

  public HighScoreFile getCurrScoreFile() {
    return currScoreFile;
  }

  public LevelReader getMyLevel() {
    return myLevel;
  }

  public PowerUpMaker getPowerUpController() {
    return powerUpController;
  }

  public Menu getMenu() {
    return myMenu;
  }
}
