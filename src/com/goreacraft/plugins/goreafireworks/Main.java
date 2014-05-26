package com.goreacraft.plugins.goreafireworks;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;



/**
 * @author goreacraft
 *
 */
public class Main extends JavaPlugin {
	public final Logger logger = Logger.getLogger("minecraft");
	public static Main plugin;
	private List<String> aliases;
	int max;
	boolean debug;
	
	@Override
    public void onEnable()
    { 
		PluginDescriptionFile pdfFile = this.getDescription();
    	this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " has been enabled! " + pdfFile.getWebsite());
    	getConfig().options().copyDefaults(true);
    	getConfig().options().header("================================================================================================="
    			+ "\r\n If you need help with this plugin you can contact goreacraft on teamspeak ip: goreacraft.com\n Website http://www.goreacraft.com "
    			+ "\r\n Permissions: "
    			+ "\r\n 	gorea.fireworks.me          - Players can use '/gf me'"
    			+ "\r\n 	gorea.fireworks.others      - Players can use '/gf <player_name>'"
    			+ "\r\n 	gorea.fireworks.me.nr       - Players can use '/gf me <nr>'"
    			+ "\r\n 	gorea.fireworks.others.nr   - Players can use '/gf <player_name> <nr>'"
    			+ "\r\n 	gorea.fireworks.location    - Players can use '/gf <world_name> <x> <y> <z>'"
    			+ "\r\n 	gorea.fireworks.location.nr - Players can use '/gf <world_name> <x> <y> <z> <nr>'"
    			+ "\r\n  gorea.fireworks.reload      - Reloads configs."
    			+ "\r\n     "
    			+ "\r\n Max fireworks in one shoot: how many maximum fireworks can be spawn at one time"
    			+ "\r\n Debug: Some more messages in console when things go wrong"
    			+ "\r\n=================================================================================================");
    	saveConfig();
    	
		plugin=this;
		aliases = getCommand("goreafireworks").getAliases();
		//Bukkit.getServer().getPluginManager().registerEvents(this, this);
		RandomFireWorks.getManager().addColors();
		RandomFireWorks.getManager().addTypes();
		max = getConfig().getInt("Max fireworks in one shoot");
		debug = getConfig().getBoolean("Debug");
		//====================================== METRICS STUFF =====================================================
   	 try {
   		    Metrics metrics = new Metrics(this);
   		    metrics.start();
   		} catch (IOException e) {
   		    // Failed to submit the stats :-(
   		}
   	 
   	if(getConfig().getBoolean("ChechUpdates"))
		new Updater(79148);
    }
	
	
	@Override
    public void onDisable()
    { 
		PluginDescriptionFile pdfFile = this.getDescription();
    	this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " has been disabled!" + pdfFile.getWebsite());
    }
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
    //      
		if (sender instanceof Player)
		{
		Player p = (Player)sender;
	    if (aliases.contains(commandLabel))
	    {
	    	//if (p.hasPermission("gorea.fireworks") || p.isOp())
	    	//{
	    		if (args.length == 1)
            	{
	    			if ( args[0].equals("reload"))
	    			{
	    				if(p.hasPermission("gorea.fireworks.reload") || p.isOp() )
		    			{
	    				plugin.reloadConfig();
	    				return true;
		    			} else {p.sendMessage("You dont have permissions to use this command."); return false;}
	    			}
	    			
	    			if ( args[0].equals("?") || args[0].equals("help"))
	    			{
	    				showhelpplayer(p);
	    				return true;
	    			}
	    			
	    			
		    			if (args[0].equals("me") )
		    			{	 
			    			if(p.hasPermission("gorea.fireworks.me") || p.isOp() )
			    			{
			    				fireAtPlayer(sender.getName());
			    				return true;
			    			} else { p.sendMessage("You dont have permissions to use this command."); return false;}
		    			}
	    			
	    			
	    			if (findPlayerByString(args[0]) !=null)
	    			{
	    				if( p.hasPermission("gorea.fireworks.others") || p.isOp())
				    			{
				    				fireAtPlayer(args[0]);
				    				return true;
				    			} 
	    					else { p.sendMessage("You dont have permissions to use this command."); return false;}
            		}
	    			else { p.sendMessage("No player with this name"); return false;}
            	}
	    		if (args.length == 2)
	    		{
	    			if (args[0].equals("me") )
	    			{	 
		    			if(p.hasPermission("gorea.fireworks.me.nr") || p.isOp() )
		    			{
		    				try 
		    				{
		    					int nr = Integer.parseInt(args[1]);
			    				if (nr<=max)
			    				{
			    					for ( int i =0; i<= nr; i++)
			    					{
			    						fireAtPlayer(sender.getName());
			    					}
			    				}
		    					
		    				
		    				}catch (Exception e)
							{
								if(debug)
								System.out.println(e);
							}	 
		    				return true;
		    			} else { p.sendMessage("You dont have permissions to use this command."); return false;}
	    			}
	    			
	    			if (findPlayerByString(args[0]) !=null)
	    			{
	    				if( p.hasPermission("gorea.fireworks.others.nr") || p.isOp())
				    			{
	    						try {
	    								int nr = Integer.parseInt(args[1]);
					    				if (nr<=max)
					    				{
					    					for ( int i =0; i<= nr; i++)
					    					{
					    						fireAtPlayer(args[0]);
					    					}
					    					
					    				}
					    				
	    							} 
	    							catch (Exception e)
	    							{
	    								if(debug)
	    								System.out.println(e);
	    							}	    										    				
				    				return true;
				    				
				    			} else { p.sendMessage("You dont have permissions to use this command."); return false;}
            		}else { p.sendMessage("No player with this name"); return false;}
	    			
	    			
	    		}
	    		/*if (args.length == 3)
	    		{
	    			//here delay
	    			if (findPlayerByString(args[0]) !=null)
	    			{
	    				if( p.hasPermission("gorea.fireworks.others.delay") || p.isOp())
				    			{
	    						try {
	    								int nr = Integer.parseInt(args[1]);
	    								
					    				if (nr<=max)
					    				{
					    					int delay = Integer.parseInt(args[1]);
					    					for ( int i =0; i<= nr; i++)
					    					{
					    						
					    						//DELAY args[2]
					    						taskid = new BukkitRunnable() {
							            			@Override
													public void run() {
							            				
							            				fireAtPlayer(args[0]);
							            				
							            			}
					    						}.runTaskLater(plugin,delay).getTaskId();
					    						
					    					}
					    					
					    				}
					    				
	    							} 
	    							catch (Exception e)
	    							{
	    								if(debug)
	    								System.out.println(e);
	    							}	    										    				
				    				return true;
				    				
				    			} else { p.sendMessage("You dont have permissions to use this command."); return false;}
            		}else { p.sendMessage("No player with this name"); return false;}
	    		}*/
	    		if (args.length == 4)
	    		{
	    			if ( p.hasPermission("gorea.fireworks.location") || p.isOp())
	    			{
			    			if (Bukkit.getServer().getWorld((String) args[0]) != null)
			    			{
			    				World world = getServer().getWorld((String) args[0]);
			    				try{
			    				Location loc = new Location(world, Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
			    				
			    				RandomFireWorks.getManager().launchRandomFirework(loc);
			    				return true;
			    				} catch(Exception e)
			    				{
			    					
			    				}
			    			} 
			    			else { p.sendMessage("No world with this name"); return false;}
	    			}
	    			else { p.sendMessage("You dont have permissions for this.");
	    		}
	    		
	    		}
	    		if (args.length == 5)
	    		{
	    			if ( p.hasPermission("gorea.fireworks.location.nr") || p.isOp())
	    			{
			    		if (Bukkit.getServer().getWorld((String) args[0]) != null)
			    			{
			    				World world = getServer().getWorld((String) args[0]);
			    				try{
				    				int nr = Integer.parseInt(args[4]);
				    				if (nr<=max)
				    				{
					    				Location loc = new Location(world, Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
					    				for ( int i =0; i<= nr; i++)
						    				{
						    				RandomFireWorks.getManager().launchRandomFirework(loc);
						    				}
					    					return true;
				    				}else { p.sendMessage("Cant spawn more then: " + max); return false;}
			    				} catch(Exception e)
			    				{
			    					if(debug)
			    					System.out.println(e);
			    				}
			    			} 
			    			else { p.sendMessage("No world with this name"); return false;}
			    			
	    				}  
	    			else { p.sendMessage("You dont have permissions for this.");}	    						
	    		}	
	    		/*if (args.length == 6)
	    		{
	    			//HERE DELAY
	    			if ( p.hasPermission("gorea.fireworks.location.delay") || p.isOp())
	    			{
			    		if (Bukkit.getServer().getWorld((String) args[0]) != null)
			    			{
			    				World world = getServer().getWorld((String) args[0]);
			    				try{
				    				int nr = Integer.parseInt(args[4]);
				    				if (nr<=max)
				    				{
					    				Location loc = new Location(world, Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
					    				for ( int i =0; i<= nr; i++)
						    				{
					    					//DELAY args[5]
						    				RandomFireWorks.getManager().launchRandomFirework(loc);
						    				}
					    					return true;
				    				}else { p.sendMessage("Cant spawn more then: " + max); return false;}
			    				} catch(Exception e)
			    				{
			    					if(debug)
			    					System.out.println(e);
			    				}
			    			} 
			    			else { p.sendMessage("No world with this name"); return false;}
			    			
	    				}  
	    			else { p.sendMessage("You dont have permissions for this.");}	   
	    		}*/
	    	}
	    }
	
	    
	    if (!(sender instanceof Player))
		{

    		if (args.length == 1)
        	{
    			if ( args[0].equals("reload"))
    			{
    				plugin.reloadConfig();
    				return true;	    			
    			}
    			
		    	if (findPlayerByString(args[0]) !=null)
				{
					fireAtPlayer(args[0]);
					return true;
				} else { 
					if(debug)System.out.println("No player with this name"); return false;}
        	}
    		
    		if (args.length == 2)
        	{
    			if (findPlayerByString(args[0]) !=null)
    			{
    			try {
    					int nr = Integer.parseInt(args[1]);
				    	if (nr<=max)
				    	{
				    		for ( int i =0; i<= nr; i++)
				    		{
				    				fireAtPlayer(args[0]);
				    		}
				    					
				    	}
				    				
    				} 
    				catch (Exception e)
    					{
    					if(debug)
    					System.out.println(e);
    					}	    										    				
			    		return true;		
        		}
        	}    		

    		if (args.length == 4)
        	{
    			if (Bukkit.getServer().getWorld((String) args[0]) != null)
    			{
	    			World world = getServer().getWorld((String) args[0]);
					try{
					Location loc = new Location(world, Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
					
					RandomFireWorks.getManager().launchRandomFirework(loc);
					return true;
					} catch(Exception e)
					{
						if(debug)System.out.println(e);
					}
    			} else { if(debug) System.out.println("No world with this name"); return false;}
        	}
    			
    		if (args.length == 5)
        	{
    			if (Bukkit.getServer().getWorld((String) args[0]) != null)
    			{
    				World world = getServer().getWorld((String) args[0]);
    				try
    				{
    					
	    				int nr = Integer.parseInt(args[4]);
	    				if (nr<=max)
	    				{
		    				Location loc = new Location(world, Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
		    				for ( int i=0; i<= nr; i++)
			    				{
			    				RandomFireWorks.getManager().launchRandomFirework(loc);
			    				}
		    					return true;
	    				}
    				} catch(Exception e)
    				{
    					if(debug)
    					System.out.println(e);
    				}
    			} else { if(debug) System.out.println("No world with this name"); return false;}
        	} 
    		
		}
    
	return false;
    }
	
	
	
	private void fireAtPlayer(String name){
		Location loc = findPlayerByString(name).getLocation();
		
		RandomFireWorks.getManager().launchRandomFirework(loc);
		
	}
	 
	 private Player findPlayerByString(String name) 
		{
			for ( Player player : Bukkit.getServer().getOnlinePlayers())
			{
				if(player.getName().equals(name)) 
				{
					return player;
				}
			}
			
			return null;
		}
	 private void showhelpplayer(Player player){
			
			player.sendMessage( ChatColor.YELLOW + "......................................................." + ChatColor.GOLD + " Plugin made by: "+ ChatColor.YELLOW + ".......................................................");
     	player.sendMessage( ChatColor.YELLOW + "     o   \\ o /  _ o              \\ /               o_   \\ o /   o");
     	player.sendMessage( ChatColor.YELLOW + "    /|\\     |      /\\   __o        |        o__    /\\      |     /|\\");
     	player.sendMessage( ChatColor.YELLOW + "    / \\   / \\    | \\  /) |       /o\\       |  (\\   / |    / \\   / \\");
     	player.sendMessage( ChatColor.YELLOW + "......................................................." + ChatColor.GOLD + ChatColor.BOLD + " GoreaCraft  "+ ChatColor.YELLOW + ".......................................................");
     	
     	player.sendMessage("");
     	player.sendMessage( ChatColor.YELLOW + "Aliases: " + ChatColor.LIGHT_PURPLE +  aliases );
     	player.sendMessage( ChatColor.YELLOW + "/gf ?/help :" + ChatColor.RESET + " Shows this.");
     	player.sendMessage( ChatColor.YELLOW + "/gf reload :" + ChatColor.RESET + " Reloads Configs.");
     	player.sendMessage( ChatColor.YELLOW + "/gf me <nr>:" + ChatColor.RESET + " Spawns a random firework at your location" );
     	player.sendMessage( ChatColor.YELLOW + "/gb <player_name> <nr>:" + ChatColor.RESET + " Spawns random fireworks to that player location.");
     	player.sendMessage( ChatColor.YELLOW + "/gf <world_name> <x> <y> <z> <nr>:" + ChatColor.RESET + " Spawns random fireworks to that location.");
     	
		}
	 
	
	 
}
