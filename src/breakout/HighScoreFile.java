package breakout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HighScoreFile {

  public static final String HIGHSCORE_FILEPATH = "blockLevels/scorefile.txt/";
  public static final int PLAYER_NAME_INDEX = 1;
  public static final int PLAYER_SCORE_INDEX = 0;
  public static final boolean APPEND_TO_FILE = true;
  private final Map<String, Integer> highestScores;
  private String currHighestScore;
  private Scanner scan;

  /**
   * Loads in the score file to be read
   */
  public HighScoreFile() {
    File scoreFile = new File(HIGHSCORE_FILEPATH);
    highestScores = new HashMap<>();
    try {
      scan = new Scanner(scoreFile);
      findHighestScore();
    } catch (FileNotFoundException e) {
      System.out.println("The data file entered is not working");
    }
  }

  /**
   * Finds the current max name and score in the given hashmap
   */
  public void findHighestScore() {
    readInAllScores();
    currHighestScore = findMaxScoreOfMap();
  }

  /**
   * Finds the max score/player in the score map
   */
  private String findMaxScoreOfMap() {
    if (highestScores.isEmpty()) {
      return "";
    }
    int highestPlayerScore = Collections.max(highestScores.values());
    for (String playerName : highestScores.keySet()) {
      if (highestPlayerScore == highestScores.get(playerName)) {
        return highestPlayerScore + ", " + playerName;
      }
    }
    return "";
  }


  /**
   * Reads in all the scores/playernames from the file path indicated above
   */
  private void readInAllScores() { //assumes players put distinguishing names b/c only keep highest score for each name, no duplicate names
    while (scan != null && scan.hasNextLine()) {
      String[] scoreData = scan.nextLine().split(",");
      String playerName = scoreData[PLAYER_NAME_INDEX];
      try {
        int playerScore = Integer.parseInt(scoreData[PLAYER_SCORE_INDEX]);
        if (highestScores.containsKey(playerName)) {
          highestScores.replace(playerName, Math.max(highestScores.get(playerName), playerScore));
        } else {
          highestScores.put(playerName, playerScore);
        }
      } catch (NumberFormatException cantParse) {
        continue;
      }
    }
  }

  /**
   * Writes the current inputted player name and score to the scorefile.txt
   */
  public void addScoreToScoreFile(String currPlayerName, int newScore) {
    try {
      FileWriter myWriter = new FileWriter(HIGHSCORE_FILEPATH, APPEND_TO_FILE);
      myWriter.write("\n" + newScore + ", " + currPlayerName);
      myWriter.close();
    } catch (IOException e) {
      System.out.println("Could not write to that file");
    }
  }

  /**
   * Returns the current high score that was found when game loaded in
   */
  public String getCurrHighestScore() {
    if (currHighestScore == null) {
      return "";
    }
    return currHighestScore;
  }


}
