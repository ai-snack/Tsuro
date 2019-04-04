import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

/** 
 * Tsuro: a two-player game in which each player strategizes to attempt to force the other player's stone off the board.
 * @author Anthony Istfan
 */
public class Tsuro extends Object{
  
  /** The grid of TsuroButtons placed atop the game board. */
  private TsuroButton[][] tiles;
  
  /** The number of rows on the game board. */
  private int rows;
  
  /** The number of columns on the game board. */
  private int columns;
  
  /** The grid of TsuroButtons placed within player1's hand. */
  private TsuroButton[] moves1;
  
  /** The grid of TsuroButtons placed within player2's hand. */
  private TsuroButton[] moves2;
  
  /** The number of playable tiles provided to each player every turn. */
  private int handsize;
  
  /** The tile that is played onto the board. */
  private TsuroButton playedtile = null;
  
  /** The tile that is currently selected in a player's hand. */
  private TsuroButton selectedtile = null;
  
  /** A counter to keep track of how many turns have progressed. */
  private int turncount = 1;
  
  /** The endpoint at which player1's stone is drawn. */
  private int drawpoint1 = 6;
  
  /** The endpoint at which player2's stone is drawn. */
  private int drawpoint2 = 2;
  
  /** The initial endpoint on which the stone is displayed in player1's hand. */
  private int initialdisplay1 = 6;
  
  /** The initial endpoint on which the stone is displayed in player2's hand. */
  private int initialdisplay2 = 2;
  
  /** The endpoint on the adjacent tile of initialpoint1 that shares the same location on the game board. */
  private int adjacentpoint1;
  
  /** The endpoint on the adjacent tile of initialpoint2 that shares the same location on the game board. */
  private int adjacentpoint2; 
  
  /** The row of the the game board in which the drawn stone for player1 is located. */
  private int row1;
  
  /** The column of the game board in which the drawn stone for player1 is located. */
  private int column1;
  
  /** The row of the game board in which the drawn stone for player2 is located. */
  private int row2;
  
  /** The column of the game board in which the drawn stone for player2 is located. */
  private int column2;
  
  /** Determine whether or not there is a winner of the game. */
  private boolean gameover = false;
  
  /**
   * Create a game with a six by six board and a hand of three tiles for each player by initializing the handsize, rows, columns, tiles, moves1, and moves2 fields to their appropriate values.
   */
  public Tsuro(){
    this.handsize = 3;
    this.rows = 6;
    this.columns = 6;
    JFrame border = new JFrame("Tsuro"); 
    JPanel board = new JPanel(new GridLayout(rows, columns));
    this.tiles = new TsuroButton[rows][columns]; 
    // Utilize a nested for loop to add a tile to each square on the game board.
    for(int index = 0; index < rows; index++){
      for(int index2 = 0; index2 < columns; index2++){
        TsuroButton square = new TsuroButton();
        Tsuro.BoardTileListener boardListen = new Tsuro.BoardTileListener();
        square.addActionListener(boardListen);
        tiles[index][index2] = square;
        board.add(square);
      }
    }
    border.setSize(700, 700);
    border.setLocation(600, 200);
    border.getContentPane().add(board, "Center");
    border.setVisible(true);
  
    JFrame player1 = new JFrame("Player 1");
    JPanel hand1 = new JPanel(new GridLayout(1, handsize));
    this.moves1 = new TsuroButton[handsize];
    // Add a playable tile to each square in player1's hand. 
    for(int index = 0; index < handsize; index++){
      TsuroButton player1move = new TsuroButton();
      Tsuro.Hand1TileListener hand1Listen = new Tsuro.Hand1TileListener();
      player1move.addActionListener(hand1Listen);
      player1move.setConnections(TsuroButton.makeRandomConnectArray());
      player1move.addStone(Color.BLUE, drawpoint1);
      moves1[index] = player1move;
      hand1.add(player1move);
    }
    player1.setSize(450, 195);
    player1.setLocation(100, 200);
    player1.getContentPane().add(hand1, "Center");
    player1.setVisible(true);
    
    JFrame player2 = new JFrame("Player 2");
    JPanel hand2 = new JPanel(new GridLayout(1, handsize));
    this.moves2 = new TsuroButton[handsize];
    // Add a playable tile to each square in player2's hand.
    for(int index = 0; index < handsize; index++){
      TsuroButton player2move = new TsuroButton();
      Tsuro.Hand2TileListener hand2Listen = new Tsuro.Hand2TileListener();
      player2move.addActionListener(hand2Listen);
      player2move.setConnections(TsuroButton.makeRandomConnectArray());
      player2move.addStone(Color.GREEN, drawpoint2);
      moves2[index] = player2move;
      hand2.add(player2move);
    }
    player2.setSize(450, 195);
    player2.setLocation(100, 400);
    player2.getContentPane().add(hand2, "Center");
    player2.setVisible(true);
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch (Exception e) {
    }
  }
  
  /**
   * Create a game with variable dimensions for the board and a hand of three tiles for each player by initializing the handsize, rows, columns, tiles, moves1, and moves2 fields to their appropriate values.
   * @param rows the desired number of rows on the game board.
   * @param columns the desired number of columns on the game board.
   */
  public Tsuro(int rows, int columns){
    this.handsize = 3;
    this.rows = rows;
    this.columns = columns;
    JFrame border = new JFrame("Tsuro"); 
    JPanel board = new JPanel(new GridLayout(rows, columns));
    this.tiles = new TsuroButton[rows][columns]; 
    // Utilize a nested for loop to add a tile to each square on the game board.
    for(int index = 0; index < rows; index++){
      for(int index2 = 0; index2 < columns; index2++){
        TsuroButton square = new TsuroButton();
        Tsuro.BoardTileListener boardListen = new Tsuro.BoardTileListener();
        square.addActionListener(boardListen);
        tiles[index][index2] = square;
        board.add(square);
      }
    }
    border.setSize(700, 700);
    border.setLocation(600, 200);
    border.getContentPane().add(board, "Center");
    border.setVisible(true);
    
    JFrame player1 = new JFrame("Player 1");
    JPanel hand1 = new JPanel(new GridLayout(1, handsize));
    this.moves1 = new TsuroButton[handsize];
    // Add a playable tile to each square in player1's hand. 
    for(int index = 0; index < handsize; index++){
      TsuroButton player1move = new TsuroButton();
      Tsuro.Hand1TileListener hand1Listen = new Tsuro.Hand1TileListener();
      player1move.addActionListener(hand1Listen);
      player1move.setConnections(TsuroButton.makeRandomConnectArray());
      player1move.addStone(Color.BLUE, drawpoint1);
      moves1[index] = player1move;
      hand1.add(player1move);
    }
    player1.setSize(450, 195);
    player1.setLocation(100, 200);
    player1.getContentPane().add(hand1, "Center");
    player1.setVisible(true);
    
    JFrame player2 = new JFrame("Player 2");
    JPanel hand2 = new JPanel(new GridLayout(1, handsize));
    this.moves2 = new TsuroButton[handsize];
    // Add a playable tile to each square in player2's hand.
    for(int index = 0; index < handsize; index++){
      TsuroButton player2move = new TsuroButton();
      Tsuro.Hand2TileListener hand2Listen = new Tsuro.Hand2TileListener();
      player2move.addActionListener(hand2Listen);
      player2move.setConnections(TsuroButton.makeRandomConnectArray());
      player2move.addStone(Color.GREEN, drawpoint2);
      moves2[index] = player2move;
      hand2.add(player2move);
    }
    player2.setSize(450, 195);
    player2.setLocation(100, 400);
    player2.getContentPane().add(hand2, "Center");
    player2.setVisible(true);
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch (Exception e) {
    }
  }
  
  /**
   * Create a game with variable dimensions for the board and a variable number of tiles in each player's hand by initializing the handsize, rows, columns, tiles, moves1, and moves2 fields to their appropriate values
   * @param rows the desired number of rows on the game board.
   * @param columns the desired number of columns on the game board.
   * @param handsize the desired number of playable tiles in each player's hand.
   */
  public Tsuro(int rows, int columns, int handsize){
    this.handsize = handsize;
    this.rows = rows;
    this.columns = columns;
    JFrame border = new JFrame("Tsuro"); 
    JPanel board = new JPanel(new GridLayout(rows, columns));
    this.tiles = new TsuroButton[rows][columns]; 
    // Utilize a nested for loop to add a tile to each square on the game board.
    for(int index = 0; index < rows; index++){
      for(int index2 = 0; index2 < columns; index2++){
        TsuroButton square = new TsuroButton();
        Tsuro.BoardTileListener boardListen = new Tsuro.BoardTileListener();
        square.addActionListener(boardListen);
        tiles[index][index2] = square;
        board.add(square);
      }
    }
    border.setSize(700, 700);
    border.setLocation(600, 200);
    border.getContentPane().add(board, "Center");
    border.setVisible(true);
   
    JFrame player1 = new JFrame("Player 1");
    JPanel hand1 = new JPanel(new GridLayout(1, handsize));
    this.moves1 = new TsuroButton[handsize];
    // Add a playable tile to each square in player1's hand. 
    for(int index = 0; index < handsize; index++){
      TsuroButton player1move = new TsuroButton();
      Tsuro.Hand1TileListener hand1Listen = new Tsuro.Hand1TileListener();
      player1move.addActionListener(hand1Listen);
      player1move.setConnections(TsuroButton.makeRandomConnectArray());
      player1move.addStone(Color.BLUE, drawpoint1);
      moves1[index] = player1move;
      hand1.add(player1move);
    }
    player1.setSize(450, 195);
    player1.setLocation(100, 200);
    player1.getContentPane().add(hand1, "Center");
    player1.setVisible(true);
    
    JFrame player2 = new JFrame("Player 2");
    JPanel hand2 = new JPanel(new GridLayout(1, handsize));
    this.moves2 = new TsuroButton[handsize];
    // Add a playable tile to each square in player2's hand.
    for(int index = 0; index < handsize; index++){
      TsuroButton player2move = new TsuroButton();
      Tsuro.Hand2TileListener hand2Listen = new Tsuro.Hand2TileListener();
      player2move.addActionListener(hand2Listen);
      player2move.setConnections(TsuroButton.makeRandomConnectArray());
      player2move.addStone(Color.GREEN, drawpoint2);
      moves2[index] = player2move;
      hand2.add(player2move);
    }
    player2.setSize(450, 195);
    player2.setLocation(100, 400);
    player2.getContentPane().add(hand2, "Center");
    player2.setVisible(true);
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch (Exception e) {
    }
  }
    
  /**
   * An inner class that is the action listener for the buttons on the game board. 
   * @author Anthony Istfan
   */
  private class BoardTileListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
      Tsuro.this.playedtile = (TsuroButton)e.getSource();
      if(Tsuro.this.getGameOver() == true)
        return;
      if(Tsuro.this.getSelectedTile() == null || Tsuro.this.isLegal(Tsuro.this.getPlayedTile()) == false)
          return;
      // If the turn counter yields a remainder of one when divided by two, it is player1's turn. If it is player2's turn, the turn counter will yield a remainder of zero when divided by two. 
      if(Tsuro.this.getTurnCount() % 2 == 1){
        Tsuro.this.getPlayedTile().setConnections(Tsuro.this.getSelectedTile().getConnections());
        Tsuro.this.moveStoneOne();
      }
      // Locate the previously played tile from player1. If player2's stone is adjacent to this tile, move player2's stone as well.
      else{
        Tsuro.this.getPlayedTile().setConnections(Tsuro.this.getSelectedTile().getConnections());
        Tsuro.this.moveStoneTwo();
      }
      Tsuro.this.getSelectedTile().setConnections(TsuroButton.makeRandomConnectArray());
      Tsuro.this.getSelectedTile().setBackground(Color.WHITE);
      Tsuro.this.setSelectedTile(null);
      Tsuro.this.nextTurn();
    }
  }
  
  /**
   * An inner class that is the action listener for the playable tiles in player1's hand.
   * @author Anthony Istfan
   */
  private class Hand1TileListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
      if(Tsuro.this.getGameOver() == true)
        return;
      // Only run this code if it is currently player1's turn.
      if(Tsuro.this.getTurnCount() % 2 == 1){
        TsuroButton clickedtile = (TsuroButton)e.getSource();  
        // If a button has not yet been clicked, set the selected tile field to equal this button. 
        if(Tsuro.this.getSelectedTile() == null){
          Tsuro.this.setSelectedTile(clickedtile);
          clickedtile.setBackground(Color.YELLOW);
        }
        // At this point in the code, there must be a tile selected.
        else{
          if(clickedtile == Tsuro.this.getSelectedTile()){
            Tsuro.rotate(clickedtile);
          }
          else{
            Tsuro.this.getSelectedTile().setBackground(Color.WHITE);
            Tsuro.this.setSelectedTile(clickedtile);
            clickedtile.setBackground(Color.YELLOW);
          }
        }
      }
    }
  }
  
  /**
   * An inner class that is the action listener for the playable tiles in player2's hand.
   * @author Anthony Istfan
   */
  private class Hand2TileListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
      if(Tsuro.this.getGameOver() == true)
        return;
      // Only run this code if it is currently player2's turn.
      if(Tsuro.this.getTurnCount() % 2 == 0){
        TsuroButton clickedtile = (TsuroButton)e.getSource();  
        // If a button has not yet been clicked, set the selected tile field to equal this button. 
        if(Tsuro.this.getSelectedTile() == null){
          Tsuro.this.setSelectedTile(clickedtile);
          clickedtile.setBackground(Color.YELLOW);
        }
        // At this point in the code, there must be a tile selected.
        else{
          if(clickedtile == Tsuro.this.getSelectedTile()){
            Tsuro.rotate(clickedtile);
          }
          else{
            Tsuro.this.getSelectedTile().setBackground(Color.WHITE);
            Tsuro.this.setSelectedTile(clickedtile);
            clickedtile.setBackground(Color.YELLOW);
          }
        }
      }
    }
  }
  
  /**
   * Retrieve the endpoint on the adjacent tile of initialpoint1 that shares the same location on the game board.
   * @return an integer between 0 and 7 representing the endpoint adjacent to the endpoint at which the stone is drawn.
   */
  public int getAdjacentPointOne(){
    return this.adjacentpoint1;
  }
  
  /**
   * Set the int value of the endpoint on the adjacent tile of initialpoint1 that shares the same location on the game board.
   * @param adjacentpoint1 an integer between 0 and 7 representing the endpoint adjacent to the endpoint at which the stone is drawn.
   */
  public void setAdjacentPointOne(int adjacentpoint1){
    if(adjacentpoint1 > -1 && adjacentpoint1 < 8)
      this.adjacentpoint1 = adjacentpoint1;
  }
  
  /**
   * Retrieve the endpoint on the adjacent tile of initialpoint2 that shares the same location on the game board.
   * @return an integer between 0 and 7 representing the endpoint adjacent to the endpoint at which the stone is drawn.
   */
  public int getAdjacentPointTwo(){
    return this.adjacentpoint2;
  }
  
  /**
   * Set the int value of the endpoint on the adjacent tile of initialpoint2 that shares the same location on the game board.
   * @param adjacentpoint2 an integer between 0 and 7 representing the endpoint adjacent to the endpoint at which the stone is drawn.
   */
  public void setAdjacentPointTwo(int adjacentpoint2){
    if(adjacentpoint2 > -1 && adjacentpoint2 < 8)
      this.adjacentpoint2 = adjacentpoint2;
  }
  
  /**
   * Retrieve the number of columns on the game board.
   * @return the int value dictating the number of columns on the game board, as initialized in the constructor.
   */
  public int getColumns(){
    return this.columns;
  }
  
  /**
   * Retrieve the int value representing the size of each player's hand.
   * @return the number of playable tiles in each player's hand, as initialized in the constructor.
   */
  public int getHandSize(){
    return this.handsize;
  }
  
  /**
   * Retrieve the int value of the endpoint at which the stone is displayed in player1's hand.
   * @return an integer between 0 and 7 representing the endpoint on which each tile in player1's hand displays the stone.
   */
  public int getInitialDisplayOne(){
    return this.initialdisplay1;
  } 
  
  /**
   * Set the int value of the endpoint at which the stone is displayed in player1's hand.
   * @param initialdisplay1 the int value between 0 and 7 representing the point on player1's playable tiles at which the stone is displayed.
   */
  public void setInitialDisplayOne(int initialdisplay1){
    if(initialdisplay1 > -1 && initialdisplay1 < 8)
      this.initialdisplay1 = initialdisplay1;
  }
  
  /**
   * Retrieve the int value of the endpoint at which the stone is displayed in player2's hand.
   * @return an integer between 0 and 7 representing the endpoint on which each tile in player2's hand displays the stone.
   */
  public int getInitialDisplayTwo(){
    return this.initialdisplay2;
  }
  
  /**
   * Set the int value of the endpoint at which the stone is displayed in player2's hand.
   * @param initialdisplay2 the int value between 0 and 7 representing the point on player2's playable tiles at which the stone is displayed.
   */
  public void setInitialDisplayTwo(int initialdisplay2){
    if(initialdisplay2 > -1 && initialdisplay2 < 8)
      this.initialdisplay2 = initialdisplay2;
  }
  
  /**
   * Retrieve the int value of the endpoint at which player1's stone is drawn. 
   * @return an integer between 0 and 7 representing the point on the tile at which the stone is drawn.
   */
  public int getDrawPointOne(){
    return this.drawpoint1;
  }
  
  /**
   * Set the int value of the endpoint at which player1's stone begins on a played tile.
   * @param drawpoint1 the int value between 0 and 7 representing the new endpoint at which player1's stone begins.
   */
  public void setDrawPointOne(int drawpoint1){
    if(drawpoint1 > -1 && drawpoint1 < 8)
      this.drawpoint1 = drawpoint1;
  }
  
   /**
   * Retrieve the int value of the endpoint at which player2's stone is drawn. 
   * @return an integer between 0 and 7 representing the point on the tile at which the stone starts before moving.
   */
  public int getDrawPointTwo(){
    return this.drawpoint2;
  }
  
   /**
   * Set the int value of the endpoint at which player2's stone begins on a played tile.
   * @param drawpoint2 the int value between 0 and 7 representing the new endpoint at which player2's stone begins.
   */
  public void setDrawPointTwo(int drawpoint2){
    if(drawpoint2 > -1 && drawpoint2 < 8)
      this.drawpoint2 = drawpoint2;
  }
  
  /**
   * Retrieve the status on whether or not the game has ended.
   * @return false until the game has concluded.
   */
  public boolean getGameOver(){
    return this.gameover;
  }
  
  /**
   * Declare that the game has concluded.
   */
  public void endGame(){
    this.gameover = true;
  }
  
  /**
   * Retrieve the row of TsuroButtons that forms player1's hand.
   * @return the array of TsuroButtons that represents player1's playable tiles.
   */
  public TsuroButton[] getPlayerOneHand(){
    return this.moves1;
  }
  
  /**
   * Retrieve the value of the currently played tile on the game board.
   * @return the value of the currently played tile initialized by the action listener.
   */
  public TsuroButton getPlayedTile(){
    return this.playedtile;
  }
  
  /**
   * Retrieve the row of TsuroButtons that forms player2's hand.
   * @return the array of TsuroButtons that represents player2's playable tiles.
   */
  public TsuroButton[] getPlayerTwoHand(){
    return this.moves2;
  }
  
  /**
   * Retrieve the number of the row in which the currently drawn stone for player1 is located.
   * @return an integer value representing the specific row on the game board containing player1's drawn stone.
   */
  public int getRowOne(){
    return this.row1;
  }
  
  /**
   * Update the number of the row in which the currently drawn stone for player1 is located.
   * @param row1 the new row that player1's drawn stone has moved to.
   */
  public void setRowOne(int row1){
    this.row1 = row1;
  }
  
  /**
   * Retrieve the number of the row in which the currently drawn stone for player2 is located.
   * @return an integer value representing the specific row on the game board containing player2's drawn stone.
   */
  public int getRowTwo(){
    return this.row2;
  }
  
  /**
   * Update the the number of the row in which the currently drawn stone for player2 is located.
   * @param row2 the new row that player2's drawn stone has moved to.
   */
  public void setRowTwo(int row2){
    this.row2 = row2;
  }
  
  /**
   * Retrieve the number of the column in which the currently drawn stone for player1 is located.
   * @return an integer value representing the specific column on the game board containing player1's drawn stone.
   */
  public int getColumnOne(){
    return this.column1;
  }
  
  /**
   * Update the number of the column in which the currently drawn stone for player1 is located.
   * @param column1 the new column that player1's drawn stone has moved to.
   */
  public void setColumnOne(int column1){
    this.column1 = column1;
  }
  
  /**
   * Retrieve the number of the column in which the currently drawn stone for player2 is located.
   * @return an integer value representing the specific column on the game board containing player2's drawn stone.
   */
  public int getColumnTwo(){
    return this.column2;
  }
  
  /**
   * Update the number of the column in which the currently drawn stone for player2 is located.
   * @param column2 the new column that player2's drawn stone has moved to.
   */
  public void setColumnTwo(int column2){
    this.column2 = column2;
  }
  
  /**
   * Retrieve the number of rows on the game board.
   * @return the int value dictating the number of rows on the game board, as initialized in the constructor.
   */
  public int getRows(){
    return this.rows;
  }
  
  /**
  * Retrieve the value of the currently selected tile in a player's hand.
  * @return the value of the currently selected tile initialized by the action listener.
  */
  public TsuroButton getSelectedTile(){
    return this.selectedtile;
  }
   
  /**
  * Set the value of the currently selected tile in a player's hand.
  * @param selectedtile the new value of the currently selected tile.
  */
  public void setSelectedTile(TsuroButton selectedtile){
    this.selectedtile = selectedtile;
  }
  
  /**
   * Retrieve the grid of TsuroButtons that form the game board. 
   * @return the array of TsuroButtons within an array of TsuroButtons that emulates a 2D board.
   */
  public TsuroButton[][] getTiles(){
    return this.tiles;
  }
  
  /**
   * Retrieve the int value of the current turn.
   * @return the number of the current turn in the game. 
   */
  
  public int getTurnCount(){
    return this.turncount;
  }
  
  /**
   * Progress to the next turn of the game by incrementing the turn count field by 1.
   */
  public void nextTurn(){
    this.turncount = this.turncount + 1;
  }
  
  /**
   * Move player1's stone.
   */
  public void moveStoneOne(){
    // Utilize a nested for loop to locate the coordinates of the currently played tile within the grid of tiles. 
    for(int index = 0; index < this.getRows(); index++){
      for(int index2 = 0; index2 < this.getColumns(); index2++){
        if(this.getTiles()[index][index2] == this.getPlayedTile()){
          this.setRowOne(index);
          this.setColumnOne(index2);
        }
      }
    }
    // After setting the location for the played tile, check to see if player2's most recently played tile is adjacent to this played tile and if the two stones will collide.
    if(this.adjacentTileTwo() == this.getPlayedTile())
      this.moveStoneTwo();
    // If it is player1's first turn, there will be no tiles adjacent to the tile played. 
    if(this.getTurnCount() == 1){
      this.adjacentpoint1 = this.getDrawPointOne(); /** The initial starting position of the stone before moving. */
      this.setDrawPointOne(this.getTiles()[this.getRowOne()][this.getColumnOne()].getConnections()[this.getAdjacentPointOne()]);
      this.getTiles()[this.getRowOne()][this.getColumnOne()].addStone(Color.BLUE, this.getDrawPointOne());
      this.getTiles()[this.getRowOne()][this.getColumnOne()].removeStone(this.getAdjacentPointOne());       
      this.setInitialDisplayOne(Tsuro.adjacentCoordinate(this.getDrawPointOne()));         
      // After moving the stone, ensure that all of the tiles in player1's hand display the correct new starting position for the stone.   
      if(this.getInitialDisplayOne() != this.getAdjacentPointOne()){
        for(int index = 0; index < this.getHandSize(); index++){
          this.getPlayerOneHand()[index].addStone(Color.BLUE, this.getInitialDisplayOne());
          this.getPlayerOneHand()[index].removeStone(this.getAdjacentPointOne());
        }
      }
      this.setAdjacentPointOne(this.getInitialDisplayOne());
    }
    else{
      boolean reachend = false; /** A variable to keep track of whether or not the stone has completed its path in the following loop. */
      // Search for the path for the stone.
      while(reachend == false){
        // If the stone ends on endpoint 0 or 1, move the stone to the above tile. 
        if(this.getDrawPointOne() == 0 || this.getDrawPointOne() == 1){
          this.getTiles()[this.getRowOne() + 1][this.getColumnOne()].removeStone(this.getDrawPointOne());
          this.setDrawPointOne(this.getTiles()[this.getRowOne()][this.getColumnOne()].getConnections()[this.getAdjacentPointOne()]);
          this.setAdjacentPointOne(Tsuro.adjacentCoordinate(this.getDrawPointOne()));
          this.getTiles()[this.getRowOne()][this.getColumnOne()].addStone(Color.BLUE, this.getDrawPointOne());
          TsuroButton adjacenttile = this.adjacentTileOne(); /** The new adjacent tile after moving the stone. */
          // If the tile adjacent to the drawn stone is not a blank tile, utilize a nested for loop to find the location of the adjacent tile.
          if(adjacenttile != null && adjacenttile.getConnections() != null){
            for(int index = 0; index < this.getRows(); index++){
              for(int index2 = 0; index2 < this.getColumns(); index2++){
                if(this.getTiles()[index][index2] == adjacenttile){
                  this.setRowOne(index);
                  this.setColumnOne(index2);
                }
              }
            }
          }
          else{
            reachend = true;
            if(this.getInitialDisplayOne() != this.getAdjacentPointOne()){
              for(int index = 0; index < this.getHandSize(); index++){
                this.getPlayerOneHand()[index].addStone(Color.BLUE, this.getAdjacentPointOne());
                this.getPlayerOneHand()[index].removeStone(this.getInitialDisplayOne());
              }
            }
            this.setInitialDisplayOne(this.getAdjacentPointOne());
          }
        }
        // If the stone ends on endpoint 2 or 3, move the stone to the right tile.
        else if(this.getDrawPointOne() == 2 || this.getDrawPointOne() == 3){
          this.getTiles()[this.getRowOne()][this.getColumnOne() - 1].removeStone(this.getDrawPointOne());
          this.setDrawPointOne(this.getTiles()[this.getRowOne()][this.getColumnOne()].getConnections()[this.getAdjacentPointOne()]);
          this.setAdjacentPointOne(Tsuro.adjacentCoordinate(this.getDrawPointOne()));
          this.getTiles()[this.getRowOne()][this.getColumnOne()].addStone(Color.BLUE, this.getDrawPointOne());
          TsuroButton adjacenttile = this.adjacentTileOne();
          if(adjacenttile != null && adjacenttile.getConnections() != null){
            for(int index = 0; index < this.getRows(); index++){
              for(int index2 = 0; index2 < this.getColumns(); index2++){
                if(this.getTiles()[index][index2] == adjacenttile){
                  this.setRowOne(index);
                  this.setColumnOne(index2);
                }
              }
            }
          }
          else{
            reachend = true;
            if(this.getInitialDisplayOne() != this.getAdjacentPointOne()){
              for(int index = 0; index < this.getHandSize(); index++){
                this.getPlayerOneHand()[index].addStone(Color.BLUE, this.getAdjacentPointOne());
                this.getPlayerOneHand()[index].removeStone(this.getInitialDisplayOne());
              }
            }
            this.setInitialDisplayOne(this.getAdjacentPointOne());
          }
        }
        // If the stone ends on endpoint 4 or 5, move the stone to the below tile. 
        else if(this.getDrawPointOne() == 4 || this.getDrawPointOne() == 5){
          this.getTiles()[this.getRowOne() - 1][this.getColumnOne()].removeStone(this.getDrawPointOne());
          this.setDrawPointOne(this.getTiles()[this.getRowOne()][this.getColumnOne()].getConnections()[this.getAdjacentPointOne()]);
          this.setAdjacentPointOne(Tsuro.adjacentCoordinate(this.getDrawPointOne()));
          this.getTiles()[this.getRowOne()][this.getColumnOne()].addStone(Color.BLUE, this.getDrawPointOne());
          TsuroButton adjacenttile = this.adjacentTileOne();
          if(adjacenttile != null && adjacenttile.getConnections() != null){
            for(int index = 0; index < this.getRows(); index++){
              for(int index2 = 0; index2 < this.getColumns(); index2++){
                if(this.getTiles()[index][index2] == adjacenttile){
                  this.setRowOne(index);
                  this.setColumnOne(index2);
                }
              }
            }
          }
          else{
            reachend = true;
            if(this.getInitialDisplayOne() != this.getAdjacentPointOne()){
              for(int index = 0; index < this.getHandSize(); index++){
                this.getPlayerOneHand()[index].addStone(Color.BLUE, this.getAdjacentPointOne());
                this.getPlayerOneHand()[index].removeStone(this.getInitialDisplayOne());
              }
            }
            this.setInitialDisplayOne(this.getAdjacentPointOne());
          }
        }
        // If the stone ends on endpoint 6 or 7, move the stone to the left tile.
        else if(this.getDrawPointOne() == 6 || this.getDrawPointOne() == 7){
          this.getTiles()[this.getRowOne()][this.getColumnOne() + 1].removeStone(this.getDrawPointOne());
          this.setDrawPointOne(this.getTiles()[this.getRowOne()][this.getColumnOne()].getConnections()[this.getAdjacentPointOne()]);
          this.setAdjacentPointOne(Tsuro.adjacentCoordinate(this.getDrawPointOne()));
          this.getTiles()[this.getRowOne()][this.getColumnOne()].addStone(Color.BLUE, this.getDrawPointOne());
          TsuroButton adjacenttile = this.adjacentTileOne();
          if(adjacenttile != null && adjacenttile.getConnections() != null){
            for(int index = 0; index < this.getRows(); index++){
              for(int index2 = 0; index2 < this.getColumns(); index2++){
                if(this.getTiles()[index][index2] == adjacenttile){
                  this.setRowOne(index);
                  this.setColumnOne(index2);
                }
              }
            }
          }
          else{
            reachend = true;
            if(this.getInitialDisplayOne() != this.getAdjacentPointOne()){
              for(int index = 0; index < this.getHandSize(); index++){
                this.getPlayerOneHand()[index].addStone(Color.BLUE, this.getAdjacentPointOne());
                this.getPlayerOneHand()[index].removeStone(this.getInitialDisplayOne());
              }
            }
            this.setInitialDisplayOne(this.getAdjacentPointOne());
          }
        }
      }
    }
    // If it is still player2's turn, do not check for endgame conditions
    if(this.getTurnCount() % 2 == 1){
      // After moving player1's stone, check to see if either stone has reached the edge of the board.
      if(this.adjacentTileOne() == null && this.adjacentTileTwo() == null){
        this.endGame();
        System.out.println("Tie!");
      }
      else if(this.adjacentTileOne() == null){
        this.endGame();
        System.out.println("Player 1 Eliminated");
      }
      else if(this.adjacentTileTwo() == null){
        this.endGame();
        System.out.println("Player 2 Eliminated");
      }
    }
  }
  
  /**
   * Move player2's stone.
   */
  public void moveStoneTwo(){
    // Utilize a nested for loop to locate the coordinates of the currently played tile within the grid of tiles. 
    for(int index = 0; index < this.getRows(); index++){
      for(int index2 = 0; index2 < this.getColumns(); index2++){
        if(this.getTiles()[index][index2] == this.getPlayedTile()){
          this.setRowTwo(index);
          this.setColumnTwo(index2);
        }
      }
    }
    // After setting the location for the played tile, check to see if player1's most recently played tile is adjacent to this played tile.
    if(this.adjacentTileOne() == this.getPlayedTile())
      this.moveStoneOne();
    // If it is player2's first turn, there will be no tiles adjacent to the tile played. 
    if(this.getTurnCount() == 2){
      this.adjacentpoint2 = this.getDrawPointTwo(); /** The initial starting position of the stone before moving. */
      this.setDrawPointTwo(this.getTiles()[this.getRowTwo()][this.getColumnTwo()].getConnections()[this.getAdjacentPointTwo()]);
      this.getTiles()[this.getRowTwo()][this.getColumnTwo()].addStone(Color.GREEN, this.getDrawPointTwo());
      this.getTiles()[this.getRowTwo()][this.getColumnTwo()].removeStone(this.getAdjacentPointTwo());       
      this.setInitialDisplayTwo(Tsuro.adjacentCoordinate(this.getDrawPointTwo()));         
      // After moving the stone, ensure that all of the tiles in player1's hand display the correct new starting position for the stone.   
      if(this.getInitialDisplayTwo() != this.getAdjacentPointTwo()){
        for(int index = 0; index < this.getHandSize(); index++){
          this.getPlayerTwoHand()[index].addStone(Color.GREEN, this.getInitialDisplayTwo());
          this.getPlayerTwoHand()[index].removeStone(this.getAdjacentPointTwo());  
        }
      }
      this.setAdjacentPointTwo(this.getInitialDisplayTwo());
    }
    else{
      boolean reachend = false; /** A variable to keep track of whether or not the stone has completed its path in the following loop. */
      // Search for the path for the stone.
      while(reachend == false){
        // If the stone ends on endpoint 0 or 1, move the stone to the above tile. 
        if(this.getDrawPointTwo() == 0 || this.getDrawPointTwo() == 1){
          this.getTiles()[this.getRowTwo() + 1][this.getColumnTwo()].removeStone(this.getDrawPointTwo());
          this.setDrawPointTwo(this.getTiles()[this.getRowTwo()][this.getColumnTwo()].getConnections()[this.getAdjacentPointTwo()]);
          this.setAdjacentPointTwo(Tsuro.adjacentCoordinate(this.getDrawPointTwo()));
          this.getTiles()[this.getRowTwo()][this.getColumnTwo()].addStone(Color.GREEN, this.getDrawPointTwo());
          TsuroButton adjacenttile = this.adjacentTileTwo(); /** The new adjacent tile after moving the stone. */
          // If the tile adjacent to the drawn stone is not a blank tile, utilize a nested for loop to find the location of the adjacent tile.
          if(adjacenttile != null && adjacenttile.getConnections() != null){
            for(int index = 0; index < this.getRows(); index++){
              for(int index2 = 0; index2 < this.getColumns(); index2++){
                if(this.getTiles()[index][index2] == adjacenttile){
                  this.setRowTwo(index);
                  this.setColumnTwo(index2);
                }
              }
            }
          }
          else{
            reachend = true;
            if(this.getInitialDisplayTwo() != this.getAdjacentPointTwo()){
              for(int index = 0; index < this.getHandSize(); index++){
                this.getPlayerTwoHand()[index].addStone(Color.GREEN, this.getAdjacentPointTwo());
                this.getPlayerTwoHand()[index].removeStone(this.getInitialDisplayTwo());
              }
            }
            this.setInitialDisplayTwo(this.getAdjacentPointTwo());
          }
        }
        // If the stone ends on endpoint 2 or 3, move the stone to the right tile.
        else if(this.getDrawPointTwo() == 2 || this.getDrawPointTwo() == 3){
          this.getTiles()[this.getRowTwo()][this.getColumnTwo() - 1].removeStone(this.getDrawPointTwo());
          this.setDrawPointTwo(this.getTiles()[this.getRowTwo()][this.getColumnTwo()].getConnections()[this.getAdjacentPointTwo()]);
          this.setAdjacentPointTwo(Tsuro.adjacentCoordinate(this.getDrawPointTwo()));
          this.getTiles()[this.getRowTwo()][this.getColumnTwo()].addStone(Color.GREEN, this.getDrawPointTwo());
          TsuroButton adjacenttile = this.adjacentTileTwo();
          if(adjacenttile != null && adjacenttile.getConnections() != null){
            for(int index = 0; index < this.getRows(); index++){
              for(int index2 = 0; index2 < this.getColumns(); index2++){
                if(this.getTiles()[index][index2] == adjacenttile){
                  this.setRowTwo(index);
                  this.setColumnTwo(index2);
                }
              }
            }
          }  
          else{
            reachend = true;
            if(this.getInitialDisplayTwo() != this.getAdjacentPointTwo()){
              for(int index = 0; index < this.getHandSize(); index++){
                this.getPlayerTwoHand()[index].addStone(Color.GREEN, this.getAdjacentPointTwo());
                this.getPlayerTwoHand()[index].removeStone(this.getInitialDisplayTwo());
              }
            }  
            this.setInitialDisplayTwo(this.getAdjacentPointTwo());
          }
        }
        // If the stone ends on endpoint 4 or 5, move the stone to the below tile.    
        else if(this.getDrawPointTwo() == 4 || this.getDrawPointTwo() == 5){
          this.getTiles()[this.getRowTwo() - 1][this.getColumnTwo()].removeStone(this.getDrawPointTwo());
          this.setDrawPointTwo(this.getTiles()[this.getRowTwo()][this.getColumnTwo()].getConnections()[this.getAdjacentPointTwo()]);
          this.setAdjacentPointTwo(Tsuro.adjacentCoordinate(this.getDrawPointTwo()));
          this.getTiles()[this.getRowTwo()][this.getColumnTwo()].addStone(Color.GREEN, this.getDrawPointTwo());
          TsuroButton adjacenttile = this.adjacentTileTwo();
          if(adjacenttile != null && adjacenttile.getConnections() != null){
            for(int index = 0; index < this.getRows(); index++){
              for(int index2 = 0; index2 < this.getColumns(); index2++){
                if(this.getTiles()[index][index2] == adjacenttile){
                  this.setRowTwo(index);
                  this.setColumnTwo(index2);
                }
              }
            }
          }  
          else{
            reachend = true;
            if(this.getInitialDisplayTwo() != this.getAdjacentPointTwo()){
              for(int index = 0; index < this.getHandSize(); index++){
                this.getPlayerTwoHand()[index].addStone(Color.GREEN, this.getAdjacentPointTwo());
                this.getPlayerTwoHand()[index].removeStone(this.getInitialDisplayTwo());
              }
            }  
            this.setInitialDisplayTwo(this.getAdjacentPointTwo());
          }
        }
        // If the stone ends on endpoint 6 or 7, move the stone to the left tile.
        else if(this.getDrawPointTwo() == 6 || this.getDrawPointTwo() == 7){
          this.getTiles()[this.getRowTwo()][this.getColumnTwo() + 1].removeStone(this.getDrawPointTwo());
          this.setDrawPointTwo(this.getTiles()[this.getRowTwo()][this.getColumnTwo()].getConnections()[this.getAdjacentPointTwo()]);
          this.setAdjacentPointTwo(Tsuro.adjacentCoordinate(this.getDrawPointTwo()));
          this.getTiles()[this.getRowTwo()][this.getColumnTwo()].addStone(Color.GREEN, this.getDrawPointTwo());
          TsuroButton adjacenttile = this.adjacentTileTwo();
          if(adjacenttile != null && adjacenttile.getConnections() != null){
            for(int index = 0; index < this.getRows(); index++){
              for(int index2 = 0; index2 < this.getColumns(); index2++){
                if(this.getTiles()[index][index2] == adjacenttile){
                  this.setRowTwo(index);
                  this.setColumnTwo(index2);
                }
              }
            }
          }
          else{
            reachend = true;
            if(this.getInitialDisplayTwo() != this.getAdjacentPointTwo()){
              for(int index = 0; index < this.getHandSize(); index++){
                this.getPlayerTwoHand()[index].addStone(Color.GREEN, this.getAdjacentPointTwo());
                this.getPlayerTwoHand()[index].removeStone(this.getInitialDisplayTwo());
              }
            }
            this.setInitialDisplayTwo(this.getAdjacentPointTwo());
          }
        }
      }
    }
    // If it is still player1's turn, do not check for endgame conditions. 
    if(this.getTurnCount() % 2 == 0){
      // After moving player2's stone, check to see if either stone has reached the edge of the board.
      if(this.adjacentTileOne() == null && this.adjacentTileTwo() == null){
        this.endGame();
        System.out.println("Tie!");
      }
      else if(this.adjacentTileOne() == null){
        this.endGame();
        System.out.println("Player 1 Eliminated");
      }
      else if(this.adjacentTileTwo() == null){
        this.endGame();
        System.out.println("Player 2 Eliminated");
      }
    }
  }
  
  /**
   * Retrieve the value of the tile adjacent to the current position of player1's drawn stone.
   * @return the tile adjacent to the tile containing the drawn stone.
   */
  public TsuroButton adjacentTileOne(){
    if(this.getDrawPointOne() == 0 || this.getDrawPointOne() == 1){
      if(this.getRowOne() - 1 > -1)
        return this.getTiles()[this.getRowOne() - 1][this.getColumnOne()];
    }
    else if(this.getDrawPointOne() == 2 || this.getDrawPointOne() == 3){
      if(this.getColumnOne() + 1 < this.getColumns())
        return this.getTiles()[this.getRowOne()][this.getColumnOne() + 1];
    }  
    else if(this.getDrawPointOne() == 4 || this.getDrawPointOne() == 5){
      if(this.getRowOne() + 1 < this.getRows())
        return this.getTiles()[this.getRowOne() + 1][this.getColumnOne()];
    }
    else if(this.getDrawPointOne() == 6 || this.getDrawPointOne() == 7){
      if(this.getColumnOne() - 1 > -1)
        return this.getTiles()[this.getRowOne()][this.getColumnOne() - 1];
    }
    return null;
  }
    
  /**
   * Retrieve the value of the tile adjacent to the current position of player2's drawn stone.
   * @return the tile adjacent to the tile containing the drawn stone.
   */
  public TsuroButton adjacentTileTwo(){
    if(this.getDrawPointTwo() == 0 || this.getDrawPointTwo() == 1){
      if(this.getRowTwo() - 1 > -1)
        return this.getTiles()[this.getRowTwo() - 1][this.getColumnTwo()];
    }
    else if(this.getDrawPointTwo() == 2 || this.getDrawPointTwo() == 3){
      if(this.getColumnTwo() + 1 < this.getColumns())
        return this.getTiles()[this.getRowTwo()][this.getColumnTwo() + 1];
    }
    else if(this.getDrawPointTwo() == 4 || this.getDrawPointTwo() == 5){
      if(this.getRowTwo() + 1 < this.getRows())
        return this.getTiles()[this.getRowTwo() + 1][this.getColumnTwo()];
    }
    else if(this.getDrawPointTwo() == 6 || this.getDrawPointTwo() == 7){
      if(this.getColumnTwo() - 1 > -1)
        return this.getTiles()[this.getRowTwo()][this.getColumnTwo() - 1];
    }
    return null;
  }
  
   /** Determine if playing a selected tile from a player's hand on a particular board tile is a legal move.
   * @return true if playing the selected tile on this board tile is a valid move. 
   * @param move the TsuroButton on the game board on which the player wants to make his or her move.
   */
  public boolean isLegal(TsuroButton move){
    int column = 0; /** The column of the game board in which the button is located. */
    // Locate the button on the game board.
    for(int index = 0; index < this.getRows(); index++){
      for(int index2 = 0; index2 < this.getColumns(); index2++){
        if(this.getTiles()[index][index2] == move){
          column = index2;
        }
      }                    
    } 
    // Only run this code if it is currently player1's turn.
    if(this.getTurnCount() % 2 == 1){
      // If it is player1's first turn, he or she can only play a tile in the first column of the board.
      if(this.getTurnCount() == 1){
        if(column != 0)
          return false;
        else
          return true;
      }
      // Otherwise, player1 must always play a tile adjacent to the endpoint at which their stone is drawn.
      else{
        if(this.adjacentTileOne() != move)
          return false;
        else
          return true;
      }
    }
    // Only run this code if it is currently player2's turn.
    else{
      // If it is player2's first turn, he or she can only play a tile in the last column of the board.
      if(this.getTurnCount() == 2){
        if(column != this.getColumns() - 1)
          return false;
        else
          return true;
      }
      // Otherwise, player2 must always play a tile adjacent to the endpoint at which their stone is drawn.    
      else{
        if(this.adjacentTileTwo() != move)
          return false;
        else
          return true;
      }
    }
  }
      
  /**
  * Retrieve the int value of the endpoint of the adjacent tile at the stone's draw point.
  * @param stop the endpoint on the original tile at which the stone stops.
  * @return the endpoint on the adjacent tile at which the stone will begin.
  */
  public static int adjacentCoordinate(int stop){
    if(stop < 0 || stop > 7)
      return stop;
    if(stop > -1 && stop < 4)
      return stop + 4;
    else
      return stop - 4;
  }
  
  /**
   * Rotate a playable tile by 90 degrees clockwise while keeping the player's stone at the same endpoint.
   * @param the currently selected tile that is to be rotated 90 degrees clockwise. 
   */
  public static void rotate(TsuroButton selectedbutton){
    // Store each element of the initial endpoint to endpoint connections array.
    int zero = selectedbutton.getConnections()[0];
    int one = selectedbutton.getConnections()[1];
    int two = selectedbutton.getConnections()[2];
    int three = selectedbutton.getConnections()[3];
    int four = selectedbutton.getConnections()[4];
    int five = selectedbutton.getConnections()[5];
    int six = selectedbutton.getConnections()[6];
    int seven = selectedbutton.getConnections()[7];
    int[] connections = {Tsuro.toOriginal(seven), Tsuro.toOriginal(six), Tsuro.toOriginal(zero), Tsuro.toOriginal(one), Tsuro.toOriginal(three), Tsuro.toOriginal(two), Tsuro.toOriginal(four), Tsuro.toOriginal(five)}; /** The array of new connections after rotation. */ 
    selectedbutton.setConnections(connections);
  }
  
  /**
   * Translate the endpoints to their original numbering prior to rotation.
   * @param endpoint the particular endpoint that will be changed to its original value.
   * @return the original int value of this endpoint before the tile was rotated or the number itself if the input is not an integer between 0 and 7.
   */
  public static int toOriginal(int endpoint){
    if(endpoint == 0)
      return 2;
    else if(endpoint == 1)
      return 3;
    else if(endpoint == 2)
      return 5;
    else if(endpoint == 3)
      return 4;
    else if(endpoint == 4)
      return 6;
    else if(endpoint == 5)
      return 7;
    else if(endpoint == 6)
      return 1;
    else if(endpoint == 7)
      return 0;
    else
      return endpoint;
  }
  
  /**
   * The main method to launch a game of Tsuro.
   * @param args the arguments inputted to the main method by the user.
   */
  public static void main(String[] args){
    try{
      if(args.length == 0)
        new Tsuro();
      else if(args.length == 2)
        new Tsuro(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
      else if(args.length == 3)
        new Tsuro(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
      else
        System.out.println("Illegal input!");
    } catch(NumberFormatException e){
        System.out.println("Only input integers!");
      }
      catch(IllegalArgumentException e){
        System.out.println("Only input positive numbers");
      }
  }       
}
    
    
  
  
  
  
          
        
      
      
      
      
      
    
                          
    

    
    