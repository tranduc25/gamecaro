package model;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class ButtonCell extends JButton  {
	public Cell cell;
	public ButtonCell() {
		this.cell = new Cell();
		this.setBackground(Color.white);
}
}
