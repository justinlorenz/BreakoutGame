package breakout.Menus;

import breakout.Game;
import java.io.FileNotFoundException;
import javafx.scene.Scene;

public class StartMenu extends Menu {

  private static final boolean NEED_USER_INPUT = false;
  private final String textFilePath = "blockLevels/menuText/start.txt";


  public StartMenu(Game game) {
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
          this.getGame().makeGameScene();
        }
      }
    });
  }
}
