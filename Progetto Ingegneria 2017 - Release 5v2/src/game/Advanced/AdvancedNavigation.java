package game.Advanced;

import java.util.ArrayList;
import java.util.HashMap;

import game.Token;
import game.Trial;
import game.Abstract.Ground;
import game.Abstract.Navigation;
import game.Abstract.World;
import game.Medium.MediumPassage;
import it.unibs.ing.myutility.LeggiInput;
import it.unibs.ing.myutility.Menu;

public class AdvancedNavigation extends Navigation{
	

	private static final long serialVersionUID = 1L;
	private final String[] DIRECTION_MENU = {"Avanti", "Indietro", "Sinistra", "Destra", "Sopra", "Sotto"};
	private AdvancedWorld world;
	private HashMap<String, String> local_string, common_string;
	private Menu direction_list;
	
	AdvancedNavigation(World world, HashMap<String, String> local_string, HashMap<String, String> common_string){
		direction_list= new Menu(DIRECTION_MENU);
		this.world=(AdvancedWorld) world;
		this.local_string=local_string;
		this.common_string=common_string;
	}

	
	public Ground navigate(Ground _current_ground) {
		AdvancedGround current_ground= (AdvancedGround) _current_ground;
		AdvancedGround next_ground;
		int direction_choice;
		
		do{
			next_ground=current_ground;
			
			if(current_ground.isEnd()) {
				System.out.println(local_string.get("END"));
				if(world.getTrials().isEmpty()) 
					return null;
				else {
					if(!world.getTrials().isEmpty() && world.getPoints()<world.getFinal_score()) 
						System.out.println(local_string.get("INSUFF_POINTS"));
					else if(!world.getTrials().isEmpty() && world.getPoints()>=world.getFinal_score()) {
							System.out.println(local_string.get("ENOUGH_POINTS"));
							return null;
					}
				}
			}
			
			//----------------------------------------------------------------------------------------------------
	
			if(current_ground.getKey()!=null&&!world.isDeposited()) {
				System.out.println(local_string.get("KEY_PRESENT") + current_ground.getKey());
				if(LeggiInput.doppiaScelta(local_string.get("GET_KEY"))) System.out.println(retrieveKey(current_ground));
				
			}
			
			//-------------------------------------------------------------------------------------------
			
			else if(!world.getPlayerkeys().isEmpty()&&!current_ground.isEnd()&&!current_ground.isStart()&&current_ground.getKey()==null&&
					LeggiInput.doppiaScelta(local_string.get("PUT_KEY"))
					){
						System.out.println(depositKey(current_ground));
			}
			//-------------------------------------------------------------------------------------------------------------------------
			if(!world.isTrialDone()&&current_ground.getTrial()!=null&&LeggiInput.doppiaScelta(local_string.get("TRIAL")+current_ground.getTrial()+local_string.get("TRIAL2"))){
			System.out.println(attemptTrial(current_ground));
			System.out.println(local_string.get("POINTS")+world.getPoints());
			}
			
			//--------------------------------------------------------------------------------------------
			
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
			
			//----------------------------------------------------------------------------------------------------------------------------------
			current_ground=attemptEntry(current_ground, next_ground);
			
		}while(direction_choice!=0);
		
		return current_ground;
	}

	private String retrieveKey(AdvancedGround current_ground){
		int total_weight=world.totWeight();
		int total_number=world.getPlayerkeys().size();
		Token key=current_ground.getKey();

			if(total_weight + key.getWeight() <= world.getMax_transportable_keys_weight() && total_number + 1 <= world.getMax_transportable_keys_number()){											//peso_totale + key.getWeight() <= peso_max && num_totale + 1 <= numero_max
				world.getPlayerkeys().add(key);
				current_ground.setKey(null);
				return local_string.get("GOT_KEY");
			}
			else return local_string.get("WEIGHT");
	}
	
	private String depositKey(AdvancedGround current_ground){
		ArrayList<String> temp= new ArrayList<String>();
		for(Token a: world.getPlayerkeys()) temp.add(a.toString());
		String[] temp2= new String[temp.size()];
		temp2=temp.toArray(temp2);
		Menu key_list = new Menu(temp2);
		int key_choice=key_list.stampaSottoMenu();
		

		if(key_choice-1>=0&&key_choice<=temp.size()){
			
			Token key=world.getPlayerkeys().get(key_choice-1);
			current_ground.setKey(key);
			world.getPlayerkeys().remove(key);
			world.setDeposited(true);
			return local_string.get("PUT_KEY_OK");
			
		}else if(key_choice==0) return "";
		else return common_string.get("NO_OPZ");
	}
	
	private String attemptTrial(AdvancedGround current_ground){
			Trial trial= current_ground.getTrial();
			String question=trial.getQuestion();
			LeggiInput.terminaRiga();
			String answer=LeggiInput.riga(question);
			boolean correct= trial.getAnswer(question, answer);
			world.updatePoint(trial, correct);
			world.setTrial_done(true);
			if(correct) return local_string.get("RIGHT");
			else return local_string.get("WRONG");
	}
	
	private AdvancedGround attemptEntry(AdvancedGround current_ground, AdvancedGround next_ground){
		
		if(next_ground==null) System.out.println(local_string.get("NO_GROUND"));
		else if(next_ground==current_ground);
		else {
				MediumPassage ptemp=world.searchPassage(current_ground, next_ground);
				if(ptemp==null) System.out.println("Passaggio nullo ---- Incongruenza");
				else {
					if(ptemp.getKey()!=null){
						System.out.println(local_string.get("KEY_NEEDED")+ptemp.getKey());
						
						if(world.getPlayerkeys().contains(ptemp.getKey())){
							System.out.println(local_string.get("YES_KEY"));
							ptemp.setOpen(true);
							ptemp.setKey(null);
						}
						else System.out.println(local_string.get("NO_KEY"));
					}
					
					if(ptemp.isOpen()) {
						current_ground=next_ground;
						System.out.println(local_string.get("CURRENT_GROUND")+current_ground.getName());
						world.setDeposited(false);
						world.setTrial_done(false);
					}
					else if(ptemp.getKey()==null) System.out.println(local_string.get("CLOSED_PASSAGE"));
				}
		}
		
		return current_ground;
	}
}
