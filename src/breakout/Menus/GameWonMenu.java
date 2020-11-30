package breakout.Menus;

import breakout.Game;
import java.io.FileNotFoundException;
import javafx.scene.Scene;


public class GameWonMenu extends Menu {

  private static final boolean NEED_USER_INPUT = true;
  private final String textFilePath = "blockLevels/menuText/gameWon.txt";

  public GameWonMenu(Game game) {
    super(game, NEED_USER_INPUT);
    try {
      this.makeMenuText(textFilePath);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void setKeyInput(Scene scene) {
    scene.setOnKeyPressed(event -> {
      switch (event.getCode()) {
        case SPACE -> {
          this.getGame().endAnimation();
          this.getGame().makeGameScene();
        }
        case ENTER -> this.getGame().getStatusDisplay().getMyProgress().grabUserInput();
      }
    });
  }
}
