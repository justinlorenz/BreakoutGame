Breakout!
====

This project is our created version of Breakout.

Name: Justin Lorenz (jml166) & Ryan Krakower (rmk44)

### Timeline

Start Date: 9/12/20

Finish Date: 9/28/20

Hours Spent: 35+

### Resources Used

https://www.tutorialspoint.com/javafx/index.htm

https://medium.com/@adam_carroll/user-interface-testing-with-testfx-5747ba02b0ec

https://www2.cs.duke.edu/courses/fall20/compsci307d/readings/Mercer_Ch16_part1.pdf

Various webpages showing different JavaFX methods such as how to create a Vbox.

### Photos Of The Game

![GamePlay](/doc/readmeAssets/multiball.png)

![GameOver](/doc/readmeAssets/gameover.png)

### Running the Program

Main class: Game.java

Data files needed: 

The data files given are level1.txt - level3.txt and scorefile.txt. These run the current levels provided, but the game is built to be able to be run for unlimited amount of levels. By following the level guidelines that will be referenced below, you can create your own levels and run the game for even longer than just 3 levels. However, for right now, we have provided 3 levels to play, and also have the mandatory scorefile.txt that holds the current scores (you can change this file to be a clean slate if you want).


Key/Mouse inputs:
* Left -> Move paddle to the left
* Right -> Move paddle to the right


Cheat keys:
* X -> Restarts the whole game
* R -> Reset the ball position to start position and pause on current level
* Space -> Toggle the pause on the game
* L -> Add 1 more live (can hold down)
* P -> Auto drop a powerup from the center to be collected
* U -> Add 1 to your score (can hold down)
* N -> Auto advance to next level
* D -> Clears a single brick (breaks the brick that is closest to left and closest to top) & (can also hold down)
* Q -> Loads in the first level
* W -> Loads in the second level
* E -> Loads in the third level
* (Made it really easy to be able to skip to future levels added in, just create a new key input and follow the same boilerplate code as the Q,W,& E key input)

Known Bugs:
* One bug noticed is that sometimes the intersection of two shapes screws up during a side collision of the ball with a brick, and this could the ball to bounce as if it was a top/bottom collision versus side collision (this doesn't happen often so we thought we would prioritize our design over small functionality because it was a tiny bug)


### Notes/Assumptions
One Assumption that we made about this game deals with the level names. As seen in our given file names, the names are in the form of level + # + .txt. We decided that instead of assuming they would be like this, we think they could be called anything like newBlockLevel + # + .newtxt. However, the one assumption we did have to make is that all the level files follow the same structure being a starting part of text + LEVEL# + ending part of file. For example, our given file names start with level + "#" + .txt. We made this assumption because it was a more reasonable assumption than assuming the name of data files, and this assumption also allows future additions to the game to be easily implemented because you can easily add more levels with any name you want (just all files have to follow the same naming scheme of start + level# + end)

How our levels vary currently:

As of right now we have included three different levels to play from. Our first level is really simple and is there to get the user used to really how the game plays out. There is only one unbreakable brick in the game, and the rest of the blocks are just defaults. Once the player advances to the next level, they are introduced to more unbreakable bricks and a few projectile bricks. The unbreakable bricks make it a little more challenging to play while the new projectile bricks add a little more suprise into the game to catch the player off guard to try and dim their lives down. This level isn't meant to be too challenging but should make the player lose maybe one life. The last level is really where the game is the hardest. The level has impossible bricks placed at strategic locations to force the player to hit certain bricks, and the level is ridden with tons of projectile bricks to make the player always trying to guess where to move next. This level is supposed to be really challenging and hard for a normal player to complete perfectly. Other than the brick types varying, we also increase the number of bricks per level to break. If we had more time, we would easily implement more types of bricks to be added and introduced to current or future levels.

#### Key Notes for User:

* How to create levels:
    * To change our created levels to your own inputted ones, you need to change the STARTING_LEVEL_FILEPATH constant in game and NUMBER_OF_LEVELS constant in progress. This should automatically find the remaining file names that you have loaded in our blockLevels data folder.
    * To create new levels, follow the boilerplate code in our given levels. Basically, right now we have three brick types, and the way you add to a level is either put a brickstrength, color (i.e 4, PURPLE) or the special brick's name. You can build the level how you want, but make sure to not build the level out of the width of the screen or too low to where the ball starts in the blocks.

* How to add new bricks:
    * We have implemented a very easy way to add new bricks to the game. Each special brick inherits from the Brick class but also has its own special methods. To create a new brick, simply create a new class like ProjectileBrick that extends brick, create the special thing you want this break to do, and then add a switch case in level reader to recognize to build this type of brick if you put the name of it in your level files. 
    
* How the scorefile.txt works:
    * When the game is loaded, the game finds the current max name and score in this file. This file is open for you to change as much as you would like. Also, at the end of any game, the game will prompt you to input your name to save your score to this file. You can choose to do this, or just close the game to not have your score saved. One thing to note is that the scorefile must not have any empty lines between names or the method will throw an error and not allow you to play until that error is fixed. 


### Impressions

Justin's Impressions: Overall, I felt that we designed the project well. However, there is a few things that I wish we had more time to add to the game but couldn't end up doing so. Some examples would be exploding bricks or a laser shooting powerup. Although we weren't able to get more features done, we made sure to implement our project in a way such that we could easily implement new bricks/powerups using our inheritance system. When looking back, I would say that I would personally try and focus more on the overall design of the project from the get-go because I found that I spent a ton of time trying to tweak tiny functionality features like the collision bugs when I should have been focused on the design of the project as a whole.



