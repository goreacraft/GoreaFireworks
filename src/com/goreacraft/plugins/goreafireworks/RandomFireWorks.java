package com.goreacraft.plugins.goreafireworks;


import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class RandomFireWorks {
 
private static RandomFireWorks fireWorks = new RandomFireWorks();
 

 
public static RandomFireWorks getManager(){
return fireWorks;
}

static ArrayList<Color> colors = new ArrayList<Color>();
static ArrayList<FireworkEffect.Type> types = new ArrayList<FireworkEffect.Type>();

public void addColors(){

colors.add(Color.WHITE);
colors.add(Color.PURPLE);
colors.add(Color.RED);
colors.add(Color.GREEN);
colors.add(Color.AQUA);
colors.add(Color.BLUE);
colors.add(Color.FUCHSIA);
colors.add(Color.GRAY);
colors.add(Color.LIME);
colors.add(Color.MAROON);
colors.add(Color.YELLOW);
colors.add(Color.SILVER);
colors.add(Color.TEAL);
colors.add(Color.ORANGE);
colors.add(Color.OLIVE);
colors.add(Color.NAVY);
colors.add(Color.BLACK);

}
 
public void addTypes(){

types.add(FireworkEffect.Type.BURST);
types.add(FireworkEffect.Type.BALL);
types.add(FireworkEffect.Type.BALL_LARGE);
types.add(FireworkEffect.Type.CREEPER);
types.add(FireworkEffect.Type.STAR);

}
 

 
public static FireworkEffect.Type getRandomType(){
int size = types.size();
Random ran = new Random();
FireworkEffect.Type theType = types.get(ran.nextInt(size));
 
return theType;
}
 
public static Color getRandomColor(){
int size = colors.size();
Random ran = new Random();
Color color = colors.get(ran.nextInt(size));
 
return color;
}
 
public void launchRandomFirework(Location loc){
Firework fw = loc.getWorld().spawn(loc, Firework.class);
FireworkMeta fm = fw.getFireworkMeta();
fm.setPower(1);

fm.addEffects(FireworkEffect.builder().with(getRandomType()).withColor(getRandomColor()).build());

fw.setFireworkMeta(fm);
}
}