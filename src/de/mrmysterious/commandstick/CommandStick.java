package de.mrmysterious.commandstick;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandStick extends JavaPlugin implements CommandExecutor, Listener 
{
	private HashMap<Player, String> userMap = new HashMap<Player, String>();
	
	@Override
	public void onEnable()
	{
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) 
	{
		if(arg1.getName().equalsIgnoreCase("commandstick") 
		|| arg1.getName().equalsIgnoreCase("cstick")
		|| arg1.getName().equalsIgnoreCase("cs"))
		{
			if(arg0 instanceof Player)
			{
				Player player = ((Player) arg0);
				
				if(player.hasPermission("commandstick.use") || player.isOp())
				{
					String command = "";
					
					for(int x = 0; x < arg3.length; x++)
					{
						command += String.format("%s ", arg3[x]);
					}
					
					userMap.put(player, command.replace("/", ""));
				}
			}
		}
		
		return true;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent arg0)
	{
		Player player = arg0.getPlayer();
		
		if(arg0.getItem().getType() == Material.STICK)
		{
			if(userMap.containsKey(player))
			{
				player.performCommand(userMap.get(player));
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent arg0)
	{
		Player player = arg0.getPlayer();
		
		if(userMap.containsKey(player))
		{
			userMap.remove(player);
		}
	}
}
