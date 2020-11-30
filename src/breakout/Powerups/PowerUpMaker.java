package breakout.Powerups;

import breakout.Game;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.Group;


public class PowerUpMaker {

  public static final int RANDOM_POWER_UP_CHANCE = 4;
  public static final int TOTAL_POWER_UPS_ALLOWED = 2;
  public static final int NUM_OF_POWER_UPS = 4;
  public static final int CREATE_POWER_UP_NUM = 0;
  private final Random chance;
  private final Game myGame;
  private final List<PowerUp> allPowerUps;
  private int currentPowerUpCount;
  private boolean powerUpFalling;
  private boolean powerUpHappening;
  private PowerUp currPowerUp;

  /**
   * Initializes the starting power up stats and creates an arraylist of random powerups
   */
  public PowerUpMaker(Group root, Game game) {
    chance = new Random();
    powerUpFalling = false;
    powerUpHappening = false;
    currentPowerUpCount = 0;
    myGame = game;
    allPowerUps = new ArrayList<>();
    allPowerUps.add(new LongPaddle("LongPaddle", root, myGame, this));
    allPowerUps.add(new MultiBall("MultiBall", root, myGame, this));
    allPowerUps.add(new SlowBall("SlowBall", root, myGame, this));
    allPowerUps.add(new ScoreMultiplier("x3 Score", root, myGame, this));
  }

  /**
   * Chooses a random powerup from the list to create
   */
  private PowerUp chooseRandomPowerUp() {
    int randomIndex = chance.nextInt(NUM_OF_POWER_UPS);
    currPowerUp = allPowerUps.get(randomIndex);
    return currPowerUp;
  }

  /**
   * Has a random chance to create a power up when a brick is broken or auto creates a power up if
   * told to
   */
  public void createAPowerUp(int centerX, int centerY, boolean autoCreate) {
    System.out.println(currentPowerUpCount);
    if (!(currentPowerUpCount >= TOTAL_POWER_UPS_ALLOWED) || autoCreate) {
      if (autoCreate && !powerUpFalling && !powerUpHappening) {
        currPowerUp = chooseRandomPowerUp();
        currPowerUp.newPowerUp(centerX, centerY); //create powerup ball
      } else if (chance.nextInt(RANDOM_POWER_UP_CHANCE) == CREATE_POWER_UP_NUM && !powerUpFalling
          && !myGame.isTimeSegmentOn() && !powerUpHappening) {
        currPowerUp = chooseRandomPowerUp();
        currPowerUp.newPowerUp(centerX, centerY); //create powerup ball
      }
    }
  }

  public boolean getPowerUpHappening() {
    return powerUpHappening;
  }

  public void updatePowerUpHappening() {
    powerUpHappening = !powerUpHappening;
  }

  public boolean getPowerUpFalling() {
    return powerUpFalling;
  }

  public void updatePowerUpFalling() {
    powerUpFalling = !powerUpFalling;
  }

  public void updateTotalPowerUps() {
    currentPowerUpCount++;
  }

  public void resetTotalPowerUps() {
    currentPowerUpCount = 0;
  }

  public PowerUp getCurrPowerUp() {
    return currPowerUp;
  }

  public List<PowerUp> getAllPowerUps() {
    return allPowerUps;
  }


}
