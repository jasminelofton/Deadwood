/*

   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane

*/

import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BoardLayersListener extends JFrame {

   private HashMap<Integer, JLabel> playerLabelsMap = new HashMap<>();
   private HashMap<String, JLabel> sceneCardLabelsMap = new HashMap<>();
   private HashMap<String, ArrayList<JLabel>> shotCountersMap = new HashMap<>();

  // JLabels
  JLabel boardlabel;
  JLabel cardlabel;
  JLabel playerlabel;
  JLabel mLabel;
  JLabel imagelabel;
  
  //JButtons
  public JButton bAct;
  public JButton bRehearse;
  public JButton bMove;
  public JButton bEndTurn;
  public JButton bUpgrade;
  
  // JLayered Pane
  JLayeredPane bPane;

  // Console output area
  JTextArea console;

  // Player info bar below the board
  JLabel playerInfoLabel;
  
  // Constructor
  public BoardLayersListener() {
      
       // Set the title of the JFrame
       super("Deadwood");
       // Set the exit option for the JFrame
       setDefaultCloseOperation(EXIT_ON_CLOSE);
      
       // Create the JLayeredPane to hold the display, cards, dice and buttons
       bPane = getLayeredPane();
    
       // Create the deadwood board
       boardlabel = new JLabel();
       ImageIcon icon =  new ImageIcon("board.jpg");
       boardlabel.setIcon(icon); 
       boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
      
       // Add the board to the lowest layer
       bPane.add(boardlabel, Integer.valueOf(0));
      
       // Set the size of the GUI (extra 120px height for player info bar below board)
       setSize(icon.getIconWidth()+200,icon.getIconHeight()+120);

       // Player info bar below the board
       playerInfoLabel = new JLabel("Player info will appear here.");
       playerInfoLabel.setBounds(0, icon.getIconHeight(), icon.getIconWidth(), 120);
       playerInfoLabel.setVerticalAlignment(JLabel.TOP);
       bPane.add(playerInfoLabel, Integer.valueOf(2));
       
       // Add a scene card to this room
      //  cardlabel = new JLabel();
      //  //Random randomCard = new Random(); // in the case the card is random
      //  ImageIcon cIcon =  new ImageIcon("01.png");
      //  cardlabel.setIcon(cIcon); 
      //  cardlabel.setBounds(20,65,cIcon.getIconWidth()+2,cIcon.getIconHeight());
      //  cardlabel.setOpaque(true);
      
      // Add the card to the lower layer
      //  bPane.add(cardlabel, Integer.valueOf(1));
       
      

    
       // Add a dice to represent a player. 
       // Role for Crusty the prospector. The x and y co-ordiantes are taken from Board.xml file
      //  playerlabel = new JLabel();
      //  ImageIcon pIcon = new ImageIcon("r2.png");
      //  playerlabel.setIcon(pIcon);
      //  //playerlabel.setBounds(114,227,pIcon.getIconWidth(),pIcon.getIconHeight());  
      //  playerlabel.setBounds(114,227,46,46);
      //  playerlabel.setVisible(true);
      //  bPane.add(playerlabel, Integer.valueOf(3));
      
       // Create the Menu for action buttons
       mLabel = new JLabel("MENU");
       mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
       bPane.add(mLabel,Integer.valueOf(2));

       // Create Action buttons
       bAct = new JButton("ACT");
       bAct.setBackground(Color.white);
       bAct.setBounds(icon.getIconWidth()+10, 30,100, 20);
       bAct.addMouseListener(new boardMouseListener());
       
       bRehearse = new JButton("REHEARSE");
       bRehearse.setBackground(Color.white);
       bRehearse.setBounds(icon.getIconWidth()+10,60,100, 20);
       bRehearse.addMouseListener(new boardMouseListener());
       
       bMove = new JButton("MOVE");
       bMove.setBackground(Color.white);
       bMove.setBounds(icon.getIconWidth()+10,90,100, 20);
       bMove.addMouseListener(new boardMouseListener());

       bUpgrade = new JButton("UPGRADE");
       bUpgrade.setBackground(Color.white);
       bUpgrade.setBounds(icon.getIconWidth()+10,120,100, 20);
       bUpgrade.addMouseListener(new boardMouseListener());

       bEndTurn = new JButton("END TURN");
       bEndTurn.setBackground(Color.white);
       bEndTurn.setBounds(icon.getIconWidth()+10,150,100, 20);
       bEndTurn.addMouseListener(new boardMouseListener());

       // Place the action buttons in the top layer
       bPane.add(bAct, Integer.valueOf(2));
       bPane.add(bRehearse, Integer.valueOf(2));
       bPane.add(bMove, Integer.valueOf(2));
       bPane.add(bUpgrade, Integer.valueOf(2));
       bPane.add(bEndTurn, Integer.valueOf(2));

  }

  
  
  // This class implements Mouse Events
  class boardMouseListener implements MouseListener{
  
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {
         
         if (e.getSource()== bAct){
            playerlabel.setVisible(true);
            System.out.println("Acting is Selected\n");
         }
         else if (e.getSource()== bRehearse){
            System.out.println("Rehearse is Selected\n");
         }
         else if (e.getSource()== bMove){
            System.out.println("Move is Selected\n");
         }
         else if (e.getSource()== bUpgrade){
            System.out.println("Upgrade is Selected\n");
         }
         else if (e.getSource()== bEndTurn){
            System.out.println("Turn Ended\n");
         }                  
      }

      public void mousePressed(MouseEvent e) {
      }
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseEntered(MouseEvent e) {
      }
      public void mouseExited(MouseEvent e) {
      }
   }

   public void MenuInputTrue() {
       bAct.setVisible(true);
       bMove.setVisible(true);
       bRehearse.setVisible(true);
   }

   public void MenuInputFalse() {
       bAct.setVisible(false);
       bMove.setVisible(false);
       bRehearse.setVisible(false);
   }   

   public void updatePlayerInfo(String info) {
         playerInfoLabel.setText(info);
   }

   public void printStatement(String message) {
      JOptionPane.showMessageDialog(this, message);
   }

   public String AskForStatement(String prompt) {
      return JOptionPane.showInputDialog(this, prompt);
   }

   public void addPlayerToken(int playerID, String dieImageFilename, int x, int y) {
      playerlabel = new JLabel();
      ImageIcon pIcon = new ImageIcon(dieImageFilename);
      playerlabel.setIcon(pIcon);
      playerlabel.setBounds(x,y,46,46);
      playerlabel.setVisible(true);
      bPane.add(playerlabel, Integer.valueOf(3));
      playerLabelsMap.put(playerID, playerlabel);
   }

   public void movePlayerToken(int playerID, int x, int y) {
      JLabel pLabel = playerLabelsMap.get(playerID);
      
      if (pLabel != null) {

         pLabel.setBounds(x, y, 46, 46);
         bPane.repaint();
      }
   }

   public void addSceneCard(ActingSet set) {
      JLabel cardLabel = new JLabel();

      ImageIcon cIcon = new ImageIcon("Cards/Cardback.png");
      cardLabel.setIcon(cIcon);

      int x = set.getX();
      int y = set.getY();
      
      cardLabel.setBounds(x, y, cIcon.getIconWidth(), cIcon.getIconHeight());
      cardLabel.setOpaque(true);
      cardLabel.setVisible(true);
      
      bPane.add(cardLabel, Integer.valueOf(1));
      
      sceneCardLabelsMap.put(set.getName(), cardLabel);
      
      bPane.repaint();

   }

   public void revealSceneCard(ActingSet set, SceneCard card) {
      JLabel existingCardLabel = sceneCardLabelsMap.get(set.getName());

      if (existingCardLabel != null) {
         ImageIcon faceUpIcon = new ImageIcon("Cards/" + card.getImg());
         existingCardLabel.setIcon(faceUpIcon);

         existingCardLabel.setBounds(set.getX(), set.getY(), faceUpIcon.getIconWidth(), faceUpIcon.getIconHeight());
         
         bPane.repaint();
      }
   }


   public void removeSceneCard(ActingSet set) {
      JLabel cardLabel = sceneCardLabelsMap.remove(set.getName());
      if (cardLabel != null) {
         bPane.remove(cardLabel);
         bPane.repaint();
      }
   }


   public void addShotCounters(ActingSet set) {     

      shotCountersMap.putIfAbsent(set.getName(), new ArrayList<>());
   
      for(Take take : set.getTakes()) {
         Area a = take.getArea();
         int x = a.getX();
         int y = a.getY();

         JLabel shotLabel = new JLabel();
         
         ImageIcon shotIcon = new ImageIcon("shot.png"); 
         shotLabel.setIcon(shotIcon);
         
         shotLabel.setBounds(x, y, shotIcon.getIconWidth(), shotIcon.getIconHeight());
         shotLabel.setVisible(true);
         
         bPane.add(shotLabel, Integer.valueOf(1));

         shotCountersMap.get(set.getName()).add(shotLabel);
      }

      bPane.repaint();
   }

   public void removerShotCounter(ActingSet set) {
      ArrayList<JLabel> shots = shotCountersMap.get(set.getName());
      
      if (shots != null && !shots.isEmpty()) {
         JLabel lastShotLabel = shots.remove(shots.size() - 1);
         bPane.remove(lastShotLabel);
         bPane.repaint();
      }
   }

   public void updatePlayerDice(int rank) {

   }




}

