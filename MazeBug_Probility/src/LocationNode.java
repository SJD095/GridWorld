import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

public class LocationNode
{
	private Location location;
	private boolean superNode;
	private LocationNode lastNode;
	private Stack<Location> couldUse;

	public LocationNode(Location obj, boolean superN, LocationNode last, Stack<Location> could)
    {
        location = obj;
        superNode = superN;
        lastNode = last;
        couldUse = could;
    }

    public Location getLoc()
    {
    	return location;
    }

    public boolean isSuperNode()
    {
    	return superNode;
    }

    public LocationNode getLastNode()
    {
    	return lastNode;
    }

    public Location getCouldUse()
    {

        int couldUseSize = couldUse.size();
    	if(couldUseSize == 1)
    		{
    		superNode = false;
    		return couldUse.pop();
    		}
    	
        else
        {
            ArrayList<Location> zanshi = new ArrayList<Location>();

            for(int i = 0; i < couldUseSize; i++)
            {
                Location obj;
                obj = couldUse.pop();
                zanshi.add(obj);
            }

        while(couldUseSize > 1)
        {
            int randomNumber = (int) ((Math.random()) * 10000);
            System.out.println(randomNumber);
        	Location random = zanshi.remove(randomNumber%couldUseSize);
            couldUse.push(random);
            couldUseSize--;
        }
            return zanshi.remove(0);
        }
    }
}
