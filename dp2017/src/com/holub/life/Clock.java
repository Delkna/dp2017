package com.holub.life;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Timer;		// overrides java.awt.timer
import com.holub.ui.MenuSite;
import com.holub.tools.Publisher;

/***
 * The <code>Clock</code> class handles the timing of gameboard
 * updates. It creates its own menu (which sets the clock speed),
 * and sends notifications off to any observers every time the
 * clock "ticks."
 *
 * <h2>Revisions</h2>
 * <p>
 * 12-8-2004	AIH	Added a kludge to the clock-tick handler that
 * 				checks whether any menu item is active before it
 * 				allows the clock to tick. This mod fixes a bug
 * 				that caused the running game to overwrite any
 * 				displayed menus. See {@link #menuIsActive} for
 * 				details.
 *
 * @include /etc/license.txt
 */
enum ClockSpeed {
	
	HALT("Halt", 1, 0), TICK("Tick", 2, 0), AGONIZING("Agonizing", 0, 500), SLOW("Slow", 0, 150),
	MEDIUM("Medium", 0, 70), FAST("Fast", 0, 30), SLOWER("Slower", 3, 0), FASTER("Faster", 4, 0);
	
	final private String name;
	final private int type;
	final private int cycleMiliSecond;	
	
	private ClockSpeed(String name, int type, int cycleMiliSecond) {
		this.name = name;
		this.type = type;
		this.cycleMiliSecond = cycleMiliSecond;
	}
	
	public String getName() {
		return name;
	}
	
	public int getType() {
		return type;
	}
	
	public int getCycleMiliSecond() {
		return cycleMiliSecond;
	}
}

public class Clock
{	private Timer			clock		= new Timer();
	private TimerTask		tick		= null;
	private int				clockMiliSecond = -1;
	

	// The clock can't be an everything-is-static singleton because
	// it creates a menu, and it can't do that until the menus
	// are established.
	//
	private Clock()
	{	createMenus();
	}

	private static Clock instance;

	/** The clock is a singleton. Get a reference to it by calling
	 *  <code>Clock.instance()</code>. It's illegal to call
	 *  <code>new Clock()</code>.
	 */
	public synchronized static Clock instance()
	{	if( instance == null )
			instance = new Clock();
		return instance;
	}

	/** Start up the clock.
	 *  @param millisecondsBetweenTicks The number of milliseconds between
	 *  					 ticks. A value of 0 indicates that
	 *  					 the clock should be stopped.
	 */

	public void startTicking( int millisecondsBetweenTicks )
	{	if(tick != null)
		{	tick.cancel();
			tick=null;
		}

		if( millisecondsBetweenTicks > 0 )
		{	tick =	new TimerTask()
					{	public void run(){ tick(); }
					};
			clock.scheduleAtFixedRate( tick, 0, millisecondsBetweenTicks);
		}
	}
	
	public void processTick(String name) {
		if(name.equals(ClockSpeed.TICK.getName())) {
			tick();
		}
		for(ClockSpeed cs : ClockSpeed.values()) {
			if(name.equals(cs.getName())) {
				switch(cs.getType()) {
				case 0: // agonizing, slow, medium, fast
					clockMiliSecond = cs.getCycleMiliSecond();
					startTicking(clockMiliSecond);
					break;
					
				case 1: // halt
					stop();
					break;
					
				case 2: // tick
					tick();
					break;
					
				case 3: // slower
					clockMiliSecond *= 1.3;
					startTicking(clockMiliSecond);
					break;
					
				case 4: // faster
					clockMiliSecond *= 0.7;
					startTicking(clockMiliSecond);
					break;
					
				default:
					break;
				}				
				break;
			}
		}
	}

	/** Stop the clock
	 */

	public void stop()
	{	startTicking( 0 );
	}

	/** Create the menu that controls the clock speed and
	 *  put it onto the menu site. 
	 */
	private void createMenus()
	{
		// First set up a single listener that will handle all the
		// menu-selection events except "Exit"

		ActionListener modifier =									//{=startSetup}
			new ActionListener()
			{	public void actionPerformed(ActionEvent e)
				{
					String name = ((JMenuItem)e.getSource()).getName();
					processTick(name);
				}
			};
																	// {=midSetup}
		MenuSite.addLine(this, "Go", ClockSpeed.HALT.getName(), modifier);
		MenuSite.addLine(this, "Go", ClockSpeed.TICK.getName(),	modifier);
		MenuSite.addLine(this, "Go", ClockSpeed.AGONIZING.getName(), modifier);
		MenuSite.addLine(this, "Go", ClockSpeed.SLOW.getName(), modifier);
		MenuSite.addLine(this, "Go", ClockSpeed.MEDIUM.getName(), modifier);
		MenuSite.addLine(this, "Go", ClockSpeed.FAST.getName(), modifier); 
		MenuSite.addLine(this, "Go", ClockSpeed.SLOWER.getName(), modifier);
		MenuSite.addLine(this, "Go", ClockSpeed.FASTER.getName(), modifier); // {=endSetup}
	}	//{=endCreateMenus}

	private Publisher publisher = new Publisher();

	/** Add a listener that's notified every time the clock ticks:
	 *  <PRE>
	 *  Clock.instance().addClockListener
	 *  (	new Clock.Listener()
	 *  	{	public void tick()
	 *  		{	System.out.println("tick!");
	 *  		}
   	 *		}
	 *  );
	 *  </PRE>
	 */
	public void addClockListener( Listener observer )
	{	publisher.subscribe(observer);
	}

	/** Implement this interface to be notified about clock ticks.
	 *  @see Clock
	 */
	public interface Listener
	{	void tick();
	}

	/** Force the clock to "tick," even if it's not time for
	 *  a tick. Useful for forcing a tick when the clock is
	 *  stopped. (Life uses this for single stepping.)
	 */
	public void tick()
	{	publisher.publish
		(	new Publisher.Distributor()
			{	public void deliverTo( Object subscriber )
				{	if( !menuIsActive() )
						((Listener)subscriber).tick();
				}
			}
		);
	}

	/** Check if any item on the menu bar has been selected.
	 *  This is an incredible kluge. The problem is that Swing draws the
	 *  menus on the same "canvas" as the main frame. As a consequence,
	 *  displayed menus are overwritten by a running game. Moreover, Swing
	 *  provides no notification on the order of "some menu item has been
	 *  selected," so the only way to detect menu-bar activity is to poll
	 *  it. This method does just that, and returns true if some menu
	 *  is being displayed. The {@link #tick} method effectively suspends
	 *  clock ticks as long as a menu is displayed, (which slows down
	 *  the clock, unfortunately).
	 *  <p>
	 *  From a design-patterns perspective, this method demonstrates that
	 *  a facade (the MenuSite) does not provide strict isolation from
	 *  the underlying subsystem. I can't immagine another application
	 *  that would need to know whether or not the menu bar is active,
	 *  so adding this menu to the MenuSite would be "noise." Since
	 *  the method does something that's of no relevance to other
	 *  MenuSite users, it makes an "end run" around the facade.
	 */

	private boolean menuIsActive()
	{	MenuElement[] path =
					MenuSelectionManager.defaultManager().getSelectedPath();
		return ( path != null && path.length > 0 );
	}
}
