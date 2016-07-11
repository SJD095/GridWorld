import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Flower;
import info.gridworld.grid.*;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

/**
 * A <code>MazeBug</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class MazeBug extends Bug {
	public Location next;
	public Location last;
	public boolean isEnd = false;
	public Integer stepCount = 0;
	public boolean reLoad = false;
	public boolean ganggang = false;
	public LocationNode wayBefore;

	boolean hasShown = false;//final message has been shown

	public MazeBug() {
		setColor(Color.GREEN);
		last = new Location(0, 0);
	}

	public void act() {
		boolean willMove = canMove();
		if (isEnd == true) {
		//to show step count when reach the goal
			if (hasShown == false) {
				String msg = stepCount.toString() + " steps";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
		} else if (willMove) {
			move();
			//increase step count when move
			stepCount++;
		}
	}

	public boolean canMove() {
		next = getNext();
		Grid<Actor> gr = getGrid();
		if(gr == null)
			return false;
		if(!next.equals(getLocation()))
			return true;
		else
			{
				return false;
			}
	}

	public Location getNext()
	{
		 if(reLoad == false)
		{

			if(ganggang == false)
			{
				Location locNow = getLocation();

				Stack<Location> newStack;
				newStack = getValid(locNow);

				boolean isSUper = false;
				if(newStack.size() > 1)
				{
					isSUper = true;
				}

				else if(newStack.size() == 0)
				{
					reLoad = true;

					if(wayBefore.isSuperNode() == true)
					{
						reLoad = false;
						ganggang = true;
					}

					return wayBefore.getLoc();
				}
				
				LocationNode tmp;
				tmp = wayBefore;
				wayBefore = new LocationNode(locNow, isSUper, tmp, newStack);

				return wayBefore.getCouldUse();
			}

			else
			{
				ganggang = false;
				return wayBefore.getCouldUse();
			}
		}

		else
		{

			wayBefore = wayBefore.getLastNode();
			if(wayBefore.isSuperNode() == true)
			{
				reLoad = false;
				ganggang = true;
			}

			return wayBefore.getLoc();
		}
	}

	public Stack<Location> getValid(Location loc)
	{

		Grid<Actor> gr = getGrid();
		if (gr == null)
			return null;

		Location locNow = loc;

		Stack<Location> nStack = new Stack<Location>();

		for(int i = -2; i < 2; i++)
				{
					Location locCould = locNow.getAdjacentLocation(getDirection() + i * 90);

					if(gr.isValid(locCould))
					{
						Actor a = gr.get(locCould);

						if(a instanceof Rock && a.getColor().getRed() == 255)
						{
							isEnd = true;
						}

						else if(a == null)
						{
							nStack.push(locCould);
						}
					}
				}

		return nStack;
	}

	public void move() {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return;
		Location loc = getLocation();
		if (gr.isValid(next)) {
			//setDirection(getLocation().getDirectionToward(next));x
			moveTo(next);
		} else
			removeSelfFromGrid();
		Flower flower = new Flower(getColor());
		flower.putSelfInGrid(gr, loc);
	}
}
