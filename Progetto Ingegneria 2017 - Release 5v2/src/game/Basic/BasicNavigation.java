package game.Basic;

import java.util.HashMap;

import game.Abstract.Ground;
import game.Abstract.Navigation;
import game.Abstract.World;
import it.unibs.ing.myutility.Menu;

public class BasicNavigation extends Navigation{
	
	
	private final String[] DIRECTION_MENU = {"Avanti", "Indietro", "Sinistra", "Destra", "Sopra", "Sotto"};
	private BasicWorld world;
	private HashMap<String, String> local_string, common_string;
	private Menu direction_list;
	
	BasicNavigation(World world, HashMap<String, String> local_string, HashMap<String, String> common_string){
		direction_list= new Menu(DIRECTION_MENU);
		this.world=(BasicWorld) world;
		this.local_string=local_string;
		this.common_string=common_string;
	}
	
	
	
	
	public Ground navigate(Ground _current_ground) {
		BasicGround current_ground= (BasicGround) _current_ground;
		BasicGround next_ground;
		int direction_choice;
		
		do{

			next_ground=current_ground;
			
			if(current_ground.isEnd()) {
				System.out.println(local_string.get("END"));
				return null;	
			}
			
			direction_choice=direction_list.stampaMenu();
			
			switch(direction_choice){
			
			case 0:{}; break;
			case 1: {
				
				next_ground= world.searchGround(current_ground.getHeight()+1, current_ground.getWidth(), current_ground.getLevel());
			}
			
		    break;
		    
			case 2:{
				next_ground= world.searchGround(current_ground.getHeight()-1, current_ground.getWidth(), current_ground.getLevel());
				
			}break;
			
			case 3:{
				next_ground= world.searchGround(current_ground.getHeight(), current_ground.getWidth()-1, current_ground.getLevel());
				
			}break;
			
			case 4:{
				next_ground= world.searchGround(current_ground.getHeight(), current_ground.getWidth()+1, current_ground.getLevel());
				
			}break;
			
			case 5:{
				next_ground= world.searchGround(current_ground.getHeight(), current_ground.getWidth(), current_ground.getLevel()+1);
				
			}break;
			
			case 6:{
				next_ground= world.searchGround(current_ground.getHeight(), current_ground.getWidth(), current_ground.getLevel()-1);
				
			}break;
			
			default: System.out.println(common_string.get("NO_OPZ"));
			break;
			
			}
			
			if(next_ground==null) System.out.println(local_string.get("NO_GROUND"));
			else if(next_ground==current_ground);
			else {
				
				BasicPassage ptemp=world.searchPassage(current_ground, next_ground);
				if(ptemp==null) System.out.println("Passaggio nullo ---- Incongruenza");
				else if(ptemp.isOpen()) current_ground=next_ground;
				else System.out.println(local_string.get("CLOSED_PASSAGE"));
			}
			
		}while(direction_choice!=0);
		
		
		return current_ground;
	}
	


}
