# shapegame

ShapeGame is a Java game library for making EXTREMELY simple 2D games using only shapes and color.
The focus of this library is to make the writing of a simple game using Java very simple and quick. 



Why use ShapeGame? You should use ShapeGame if the game you are wanting to make is consisting of only shapes, color, and text.
A few examples of games that could be made using ShapeGame are Tetris, Snake, Flood-it, or pretty much any game on this list
https://inventwithpython.com/blog/2012/02/20/i-need-practice-programming-49-ideas-for-game-clones-to-code/

You might be thinking to yourself "If i'm making a simple game with only shpes and text, why dont I just use 
the java swing library, then I wont have to depend on something other than standard java". Thats a good point,
and I'm glad you brought it up. The entire reason for this library is because that my personal experience java swing
library does not offer enough power to sustain even simple shape games. It is possible, and it works, but it doesn't
take too before swing can't handle it. Moral of the story, drawing should be done on the GPU whenever games are
involved. 


ShapeGame uses OpenGL for rendering.
