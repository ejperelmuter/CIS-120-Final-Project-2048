=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: 62364437
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an approprate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays
      I used a 2D array of Tiles to model the boardstate at any given turn. Since the 
      game takes place on a grid, I felt that a 2D array modeled the state best. Since
      the game depends on interactions between tiles, it is much easier to calculate the collisions
      in a 2D array rather than attempt to find some sort of way to deal with it as an array of length 16.
      

  2. Collections
      While I used a 2D array to keep track of the current boardstate at any given time,
      I used a TreeMap mapping every past turn to the board state and score at that turn. Every time
      the user slides the board, the boardState and score are added to the map with the current turn as 
      the key before the board is even updated. Thus, the user is able to "undo" and return to any 
      previous board state by clicking undo enough. I found a TreeMap the most appropriate way to 
      manage past boardstates since it is unknown how many turns would need to be stored before
      the game ends. Furthermore, having a Map rather than a Set allowed access to any boardstate just
      by giving a turn.
      
  

  3. JUnit Tests
      Since the game depends on a set number of board interactions (slide up/down/right/left and rotate),
      I felt it was appropriate to test these features in specific board states I was able to
      specify. I found it easier to use a setBoard() method that I could create a board of my choosing
      that I would be able to manipulate rather than attempt to play the game to a certain state and
      test the interactions from there. This allowed me to set up the board in a specific state and attempt
      to modify it with no external factors to consider, such as bad graphics implementation.
      I also was able to test various error-catching situations to ensure that the game would not crash 
      from null inputs or incorrectly formatted files. 


  4. IO
      I used Java's FileReader class to manage a file of high scores by previous players. When
      the game ends, the User can input their name and the game will save their score and display
      the top 5 previous scores. The user inputs their name through the console, and the Scanner class
      takes care of getting the input the use typed and updating the scoreboard.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  Game2048.java: This is the main class that specifies the frame and widgets of the GUI
                  In this class, I create the actual Frame where the game
                  and every feature of the game actually is displayed. In this class,
                  I place the grid in the center frame as well as two buttons. 1) A 
                  Reset button that clears the board and resets the score and turn counter
                  and 2) an undo button that the player can use past turn 5 to go back
                  to the previous board state.
  
  
  Grid.java: This class models the actual board with a 2D array of Tile Objects (described
              below) as well as defines a couple methods that the player can invoke with the
              two buttons described above. The methods in Grid.java take care
              of user interaction when the game is playing played, appropriately
              sliding the board when a button or arrow key is pressed. This class also
              is responsible for maintaining the board state and actually drawing
              the tiles on the board. This class uses the methods in Tile.java to set
              the Graphics color when drawing each tile, and get the value of each
              tile. Additionally, this class maintains a TreeMap of all past turns in
              order to allow the user to undo to a previous move. 
  
  
  ScoreBoard.java:  This class is responsible for the IO portion of the game. Grid.java
                     creates an instance of the Scoreboard class in order to read past
                     high scores of the game. This class reads a given formatted file
                     and creates an array of names and scores to display. Furthermore,
                     this class can be given a new name and score, and appropriately
                     place the score in the file at its appropriate ranking by score.


  Tile.java: The Tile class is responsible for maintaining a tile on the board, and 
             allowing ease of access from within Grid.java to 1) change the value of the tile, 
             and 2) set the color of the tile given a value. This allows the Grid class to
             focus on actually displaying the tiles on the grid rather than having to determine
             the various aspects of the tile, such as Tile color or font size. Though a very
             simple class with only a few methods, having this class allows the board
             to be modeled and manipulated with much easier than if I had tried to model the
             grid as just integers.
  
  
  GameTest.java: This class contains all of my JUnit tests for testing the various
                 interactions on the board. I tested all of my sliding methods and
                 board setting/getting methods in order to assure that the game not only
                 works correctly, but also does not crash under any circumstance. I explain
                 this further in my JUnit test section above.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
      I found implementing the game to be more difficult than I had originally
      thought it to be. Since the game depends on interactions, I ran into
      many interactions I had not accounted for that occasionally led to incorrect
      updates or game crashes. I eventually realized I should model the entire board
      as Tiles, even the empty tiles, thus there would be Null pointers in the 2D array.
      Furthermore, actually displaying the board took longer than I had anticipated. 
      By playing around with Swing graphics and the sample code we were given, I was able to 
      find an efficient and aesthetically pleasing way to display the board. 
  


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
      I feel that my game encapsulates the game state fairly well since the board
      is represented by Tile objects rather than just numbers. By having
      a getTileValue() method, the program can return the integer value at any position
      without the fear of altering the boardState by altering that number. Since the board
      is modeled by Tiles, the Grid class must use Tile's methods to set the value
      of certain tiles. By having the Tile class actually set the appropriate color
      of the tile, the Grid class only had to take care of actually placing the tile
      on the board rather than calculating the color of each tile. 
      If I had the chance to refactor, I would build the Tile class as an actual
      object that could display itself given a Graphics object. Then it would be easier
      to implement further graphics, especially actual sliding of the tiles rather than
      just updating the board at each turn. Other than that, I found my implementation
      to work well and am happy with the end product. 



========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  I played around on the main website for 2048 (https://gabrielecirulli.github.io/2048/) 
  by the original developer to see how the original game handled various interactions between 
  tiles. I also used the javadocs for Color and for FileReader in order to implement unique
  colors of the tiles and help with reading and writing to my score file.


