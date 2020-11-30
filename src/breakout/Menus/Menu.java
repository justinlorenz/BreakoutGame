package breakout.Menus;

import breakout.Game;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class Menu {

  public static final int MENU_X = 200;
  public static final int MENU_Y = 200;
  public static final int SUB_MENU_X = 250;
  public static final int SUB_MENU_Y = 250;
  public static final int MENU_TEXT_SIZE = 70;
  public static final int SUB_MENU_TEXT_SIZE = 25;
  public static final String FONT = "Verdana";
  private final Game myGame;
  private final boolean needUserInput;
  private Group root;
  private String menuString;
  private String subMenuString;
  private String colorString;

  public Menu(Game game, boolean userInput) {
    myGame = game;
    needUserInput = userInput;
  }

  public Scene setUpScene() {
    root = new Group();
    Scene scene = new Scene(root, Game.SIZE, Game.SIZE, Game.BACKGROUND);
    if (needUserInput) {
      myGame.getStatusDisplay().getMyProgress().createUserInputField(root);
    }
    setKeyInput(scene);
    displayMenuText();
    return scene;
  }

  public void makeMenuText(String textFilePath) throws FileNotFoundException {
    File textFile = new File(textFilePath);
    Scanner s = new Scanner(textFile);
    menuString = s.nextLine();
    subMenuString = s.nextLine();
    colorString = s.nextLine();

  }

  public abstract void setKeyInput(Scene scene);


  private void displayMenuText() {
    Text menuText = new Text(MENU_X, MENU_Y, menuString);
    menuText.setFont(Font.font(FONT, MENU_TEXT_SIZE));
    menuText.setFill(Color.valueOf(colorString));
    root.getChildren().add(menuText);
    Text subMenuText = new Text(SUB_MENU_X, SUB_MENU_Y, subMenuString);
    subMenuText.setFont(Font.font(FONT, SUB_MENU_TEXT_SIZE));
    subMenuText.setFill(Color.valueOf(colorString));
    root.getChildren().add(subMenuText);
  }

  public Game getGame() {
    return myGame;
  }

  public String getMenuString() {
    return menuString;
  }

}
