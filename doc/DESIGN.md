# Game Design Final
### Names
Justin Lorenz (jml166)

Ryan Krakower (rmk44)

## Team Roles and Responsibilities

 * Team Member #1 - Justin
    
    Focused a lot of time on how to add certain features into the game such as the physics of the ball/paddle collision or powerups, and also spent my time trying to figure out how to continually refactor and make my code better in an iterative approach. 
   



## Design goals

#### What Features are Easy to Add

* Displays were very easy to add because they were just text objects that needed to be updated, and they relied on the other classes like Progress to actually be keeping track of the data
* Creating the bricks were easy to add because they were just rectangles between stacked together (only hard part was finding the math on how to space them out correctly)
* The HighScore file was easy to add because this only required reading in from a text file and then writing to the text file
* The different powerups were easy to add because they didn't require too much code to activate/deactivate but it was the powerup ball and power up timing that took a long time

## High-level Design

#### Core Classes

* Game - launches the game and updates the current actions that are taking place throughout the game functioning
* Ball - constantly bounces around the screen checking different types of collisions and updating the rest of the game once a collision has occured
* Paddle - moves due to key input and is checking if the ball has collided with the paddle and reflects the ball at varying angles based off where it hits
* Progress - keeps track of the current progress of the game and is used to find when to transition to a new level/won game/lost game.
* Brick & Level Reader - both go hand and hand because level reader takes in the input files and builds bricks which each continuously update based off ball collision occurring

## Assumptions that Affect the Design

One assumption that we made was that the user would implement their new levels in the correct way. This means that user would make sure to only build a level that fit within the constraints of the SIZE window as well as followed the guidelines for the name of the level files in the data folder. One other assumption that we made was that new users who wanted to manually edit the score file should also follow the guidelines referenced in the readme. This meant that the user would have to make sure there was no gaps in between lines and that they were inputting the correct type of info (i.e not putting score in name's place) into the file.

#### Features Affected by Assumptions

The features affected by the level building assumption are the main gameplay features of the game mostly. If the game is wrongly built (say the user inputs too many bricks off to the right or too far down to where the ball spawns in the middle of the bricks structure), then the overall game will not play out how it should. If we had still had time to go back through the project, it would definitely be advantageous to add some edge case checking in that would prevent the user from inputting the dimensions and incorrect wording into the level files. The feature that is affected by the score file assumptions is the ability to keep a running log of the past and present players of the game. If the user decides to manually change the score file and doesn't follow the correct guidelines that we had noted, then the game will not load correctly. Specifically, if the user creates an empty line in the middle of the scores and names, this will cause the game to not load in the highest score correctly. Although we did include a lot of edge casing for this class such as checking corrupt files, if the score could actually be parseInt'd, etc., there was definitely some things that we could do to try and reduce our assumptions such as maybe figuring out a way to not have the file loader break if there is an empty space in the text.


## New Features HowTo

#### Easy to Add Features

The features that are easiest to add to this game from now on are new levels, bricks, and powerups. The reason that these are easy are because of the design of our game. As noted in the readme, the levels can be easily created by following our boilerplate code that is given to you in the 3 levels we made. You can easily enter your own type of levels, and you only have to change two variables for this to work as noted in the readme. Also bricks are very easy to implement because new bricks just need to inherit the brick class and need to be able to be referenced by the level reader class. The new type of brick just needs one method or so in its class about what it should do when loaded in/breaks. The powerups are super easy to add too because you just need a new type of powerup in its class extending the powerup class. Just create the specific activate() and deactivate() method and it will work great. Also, you would just need to change the powerupmaker class to make sure to add in your powerup into the list random powerups so that it could be chosen to randomly happen.


#### Other Features not yet Done

* We were not able to add more types of bricks. Some brick ideas could be moving breaks or exploding bricks. We made our game with the aim to be able to easily implement these later
* We were not able to add some more types of powerups. Some power up ideas could include lasers or exploding balls. These also wouldn't be very tough to add because the power up classes are easy to implement because they already have most of their code done due to inheritance.

