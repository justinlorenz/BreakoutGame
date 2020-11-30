# Game Plan
## NAMEs
Justin Lorenz

Ryan Krakower 


### Breakout Variation Ideas

### Interesting Existing Game Variations

* Game 1

Vortex - variant on iPod nanos. It’s in the shape of a circle. On the outside of the circle, the paddle can move 360 degrees. The paddle hits the ball into the center of the circle, where the bricks are. The ball gets smaller and goes slower when it goes towards the center of the circle. It can also, if there are no bricks in the way, go through the center of the circle and out the other side.

* Game 2

Centipong - variant on the normal breakout game. What’s different in this version is that there are multiple “centipedes” roaming at the top of the game, and if the ball hits one of these, they split into a bunch of extra balls. This could be interesting for thinking about what types of power ups to implement such as a multiple ball explosion as in this game. The game also has a slow down effect that would be really interesting to implement as well because it could basically be triggered in our game due to a “freeze” powerup. 

#### Block Ideas

* Block 1

We want to implement a color based block system in our game. How this block system will work is the color of the block determines how many more hits the block needs until it disappears. For example, we could set red to be 1 more hit left, so any red blocks only need one more hit to disappear. For further explanation, imagine we set the color palette to be blue = 3 hits, green = 2 hits, and red = 1 hit. Then a blue block hit once will turn green, then if hit again it will turn red, and then if hit again it will disappear. 

* Block 2

There could be invincible blocks. These would probably be grey or black, and would serve as obstacles to hitting the other blocks. These do not need to be destroyed to complete the level (since they can’t be destroyed anyway).

* Block 3

We would want to add a random power up block. Basically, if the user is able to hit this block, they will receive a random power up for a short period of time. One way we could generate this is have an array of random powers and once the block is hit, generate a random index which corresponds to a random powerup. 

* Block 4

Blocks that move. This might be harder to implement, but would be a fun challenge.


#### Power Up Ideas

* Power Up 1

A power up that splits the ball into multiple (we’ll do 2 probably) balls. Each ball would have to be going at a different velocity / direction. The level is only over if all balls are lost.

* Power Up 2

It makes the paddle bigger. We could also have another power up (that is bad to get) that makes the paddle smaller.

* Power Up 3

Another power up that we can institute is a slow down/free powerup. This would make the ball move slower and be easier to hit. We could also make the alternative bad powerup version of this where it would cause the ball to speed up.

* Power Up 4
“Sticky paddle”, where the  ball stays on the paddle when it lands on it. Upon clicking a button (probably space bar), the ball is released.

#### Cheat Key Ideas

* Cheat Key 1

Especially for testing purposes, it would be helpful to have individual cheat keys to activate each power up. Another cheat key could activate a random power up.

* Cheat Key 2
One cheat key that we could have would give all your life back. This could be useful for testing purposes as well because we don’t need to keep restarting the game. 

* Cheat Key 3

One cheat key could reset the level to its starting point.

* Cheat Key 4

One cheat key could automatically “beat” the level, and move on to the next one.


#### Level Descriptions

* Level 1
  * Block Configuration

Just multi - colored blocks. It is a solid rectangle four or five blocks high, with the blocks towards the top requiring multiple hits to break. There is empty space on top, so the ball could bounce around on top and break a bunch of blocks that way.
	
  * Variation features

One or two of the blocks are random power up blocks.

* Level 2
  * Block Configuration

For this variation, we are going to add invincible blocks to make a harder challenge. There will be a wall of blocks on top of the level, like in level one, but there will be two sets of invincible blocks jutting into the center. It will create a “Z shape” where the ball has to maneuver around these blocks to reach the blocks on top.
  * Variation features
Invincible blocks
One or two are random power up blocks


* Level 3

  * Block Configuration

For this variation, we are going to add moving blocks to make a harder challenge. Blocks will move back and forth along a fixed range of motion, to make them harder to hit. There will be two or three groups of blocks that move together, back and forth, along the level. 


  * Variation features
Invincible blocks
Multiple random power up blocks

* Level 4

  * Block Configuration

This one could have invincible and moving blocks. This level would be a challenge! Also, it will be filled with power ups.


  * Variation features
Invincible blocks
Moving blocks
A large number of random power up blocks

### Possible Classes

* Class 1: Block Class
  * Purpose

Every block in this game is going to be a Block object. In this class, we will host all the data about the current block, and this is where we will update the data about the block as well (i.e the health of the block)

  * Method
We can have a health update method that destroys the object once the health becomes zero.

* Class 2: Ball class.
  * Purpose

The ball will be its own object. This class will need to have size, position, and velocity. There can be more than one of these at once. It will need correct behavior when bouncing off other objects.

  * Method: collide
We can have a collide method that changes the ball’s velocity when it collides with another object.

  * Method: disappear
We can have a method that makes the ball object disappear when it reaches the bottom of the screen (below the paddle).

* Class 3: Power Up Class

  * Purpose

This class will be where all of our power ups in the game are located.

  * Method

This class will have a random powerup method that chooses a random power up to be thrown into the game. 


* Class 4: Paddle Class
  * Purpose

This class creates a paddle object. It will need attributes for size and stickiness, to allow it to be modified by the power ups.

  * Method

Update paddle size method that could be referenced when the paddle is shrunk/widened due to some power ups. 

* Class 5: Each level has its own class

  * Purpose

Each level class should take in the specified .txt file and send this to a stage class. Then this level will be created and keep track of what is happening.

  * Method

This class should have a constructor that calls the scene class and sends in the text file related to that level. 


* Class 6: Stage class

  * Purpose

This class generates the “playing field.” This sets up the stage for the other levels.

  * Method

* Class 7: Game menu

  * Purpose

This class generates the menu upon either starting the game, or the game being over (all balls disappear), or winning.

  * Method: Start game

This method loads up level one when the start game button is pressed.






