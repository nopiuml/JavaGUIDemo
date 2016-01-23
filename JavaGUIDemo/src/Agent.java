
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class Agent {
	
	private static int agent_id;
	private static Hashtable<Integer, String> agent_moves;
	private static ArrayList<String> agent_plan;

	private static Random randomizer = new Random();
	private static Master master;
	public static int exchanged;
	private Hashtable<String, String> imp_build = new Hashtable(); 
	
	public static String[][]  new_plan = new String[5][3];
	
	public static Hashtable<String, String> empty_plan = new Hashtable();
	public static int empty_1 = 0;
	public static int empty_2 = 0;
	public static int empty_3 = 0;
	public static int empty_4 = 0;
	public static int empty_5 = 0;
	public static int empty_6 = 0;
	
	public Agent() {
		// TODO Auto-generated constructor stub
	
	}
	
	public Agent(int agent_id, Hashtable<Integer, String> agent_moves, ArrayList<String> agent_plan){
		
			this.agent_id = agent_id;
			this.agent_moves = agent_moves;
			this.agent_plan = agent_plan;
	}

	// oi praktores mporoyn na antilhfthoun to periballon tous
	// an dhladh stis geitonikes theseis uparxoun praktores h tmhmata ktiriou h einai kenes
	/**
	 * H synarthsh auth pairnei to xarth kai tsekarei ta geitonika kelia
	 * Apothikeuei ston hashtable tmpknowledge thn proswrinh gnwsh gia ta geitonika kelia 
	 * Amesws meta tsekarei an kapoio apo ta geitonika kelia einai praktoras
	 * kai tote apothikeuei ston teliko xarth th geitonikh gnwsh kathws kai thn gnwsh pou exei apo prin o praktoras
	 * @param linesOfField
	 * @param columnsOfField
	 * @param i
	 * @param j
	 * @param field
	 * @param worldMap
	 * @param knowledge
	 * @return
	 */
	public static Hashtable<String, Hashtable<String, String>> exchangeknowledge(int linesOfField, int columnsOfField, int i, int j, String field, Hashtable<String, String> worldMap, Hashtable<String, Hashtable<String, String>> knowledge)
	  {
		
		
		exchanged = 0;
		
	    if (knowledge == null) {
	      knowledge = new Hashtable();
	    }
	    Hashtable<String, String> tmpKnowledge = (Hashtable)knowledge.get(field);
	    if (tmpKnowledge == null) {
	      tmpKnowledge = new Hashtable();
	    }
	    tmpKnowledge.put(i + "_" + j, " ");
	    int upI = i - 1;
	    int upJ = j;
	    int rightI = i;
	    int rightJ = j + 1;
	    int downI = i + 1;
	    int downJ = j;
	    int leftI = i;
	    int leftJ = j - 1;
	    if (worldMap.get(upI + "_" + upJ) != null) {
	      tmpKnowledge.put(upI + "_" + upJ, (String)worldMap.get(upI + "_" + upJ));
	    }
	    if (worldMap.get(downI + "_" + downJ) != null) {
	      tmpKnowledge.put(downI + "_" + downJ, (String)worldMap.get(downI + "_" + 
	        downJ));
	    }
	    if (worldMap.get(rightI + "_" + rightJ) != null) {
	      tmpKnowledge.put(rightI + "_" + rightJ, (String)worldMap.get(rightI + 
	        "_" + rightJ));
	    }
	    if (worldMap.get(leftI + "_" + leftJ) != null) {
	      tmpKnowledge.put(leftI + "_" + leftJ, (String)worldMap.get(leftI + "_" + 
	        leftJ));
	    }
	    //geitonika kelia
	    // an yparxei agent dipla tote antallakse gnwsh
	    String adjField = "";
	    int agentchecker = -1;
	    Hashtable<String, String> adjKnowledge = null;
	    if (i < linesOfField - 1)
	    {
	      int tt = i + 1;
	      int ss = j + 1;
	      
	      adjField = (String)worldMap.get(tt + "_" + j);
	      agentchecker = -1;
	      try
	      {
	        agentchecker = Integer.parseInt(adjField);
	      }
	      catch (Exception localException) {}
	      if (agentchecker > 0)
	      {
	        tmpKnowledge.put(tt + "_" + j, " ");
	        adjKnowledge = (Hashtable)knowledge.get(adjField);
	        if ((adjKnowledge != null) && (!adjKnowledge.isEmpty())) {
	          tmpKnowledge.putAll(adjKnowledge);
	          exchanged++;
	        }
	      }
	      else
	      {
	        tmpKnowledge.put(tt + "_" + j, (String)worldMap.get(tt + "_" + j));
	      }
	      if (j < columnsOfField - 1)
	      {
	        adjField = (String)worldMap.get(tt + "_" + ss);
	        agentchecker = -1;
	        try
	        {
	          agentchecker = Integer.parseInt(adjField);
	        }
	        catch (Exception localException1) {}
	        if (agentchecker > 0)
	        {
	          tmpKnowledge.put(tt + "_" + ss, " ");
	          adjKnowledge = (Hashtable)knowledge.get(adjField);
	          if ((adjKnowledge != null) && (!adjKnowledge.isEmpty())) {
	            tmpKnowledge.putAll(adjKnowledge);
	            exchanged++;
	          }
	        }
	        else
	        {
	          tmpKnowledge.put(tt + "_" + ss, (String)worldMap.get(tt + "_" + 
	            ss));
	        }
	      }
	      if (j > 0)
	      {
	        ss = j - 1;
	        adjField = (String)worldMap.get(tt + "_" + ss);
	        agentchecker = -1;
	        try
	        {
	          agentchecker = Integer.parseInt(adjField);
	        }
	        catch (Exception localException2) {}
	        if (agentchecker > 0)
	        {
	          tmpKnowledge.put(tt + "_" + ss, " ");
	          adjKnowledge = (Hashtable)knowledge.get(adjField);
	          if ((adjKnowledge != null) && (!adjKnowledge.isEmpty())) {
	            tmpKnowledge.putAll(adjKnowledge);
	            exchanged++;

	          }
	        }
	        else
	        {
	          tmpKnowledge.put(tt + "_" + ss, (String)worldMap.get(tt + "_" + 
	            ss));
	        }
	      }
	    }
	    if (i > 0)
	    {
	      int tt = i - 1;
	      int ss = j + 1;
	      
	      adjField = (String)worldMap.get(tt + "_" + j);
	      agentchecker = -1;
	      try
	      {
	        agentchecker = Integer.parseInt(adjField);
	      }
	      catch (Exception localException3) {}
	      if (agentchecker > 0)
	      {
	        tmpKnowledge.put(tt + "_" + j, " ");
	        adjKnowledge = (Hashtable)knowledge.get(adjField);
	        if ((adjKnowledge != null) && (!adjKnowledge.isEmpty())) {
	          tmpKnowledge.putAll(adjKnowledge);
	            exchanged++;

	        }
	      }
	      else
	      {
	        tmpKnowledge.put(tt + "_" + j, (String)worldMap.get(tt + "_" + j));
	      }
	      if (j < columnsOfField - 1)
	      {
	        adjField = (String)worldMap.get(tt + "_" + ss);
	        agentchecker = -1;
	        try
	        {
	          agentchecker = Integer.parseInt(adjField);
	        }
	        catch (Exception localException4) {}
	        if (agentchecker > 0)
	        {
	          tmpKnowledge.put(tt + "_" + ss, " ");
	          adjKnowledge = (Hashtable)knowledge.get(adjField);
	          if ((adjKnowledge != null) && (!adjKnowledge.isEmpty())) {
	            tmpKnowledge.putAll(adjKnowledge);
	            exchanged++;

	          }
	        }
	        else
	        {
	          tmpKnowledge.put(tt + "_" + ss, (String)worldMap.get(tt + "_" + 
	            ss));
	        }
	      }
	      if (j > 0)
	      {
	        ss = j - 1;
	        adjField = (String)worldMap.get(tt + "_" + ss);
	        agentchecker = -1;
	        try
	        {
	          agentchecker = Integer.parseInt(adjField);
	        }
	        catch (Exception localException5) {}
	        if (agentchecker > 0)
	        {
	          tmpKnowledge.put(tt + "_" + ss, " ");
	          adjKnowledge = (Hashtable)knowledge.get(adjField);
	          if ((adjKnowledge != null) && (!adjKnowledge.isEmpty())) {
	            tmpKnowledge.putAll(adjKnowledge);
	            exchanged++;

	          }
	        }
	        else
	        {
	          tmpKnowledge.put(tt + "_" + ss, (String)worldMap.get(tt + "_" + 
	            ss));
	        }
	      }
	    }
	    if (j < columnsOfField - 1)
	    {
	      int tt = i + 1;
	      int ss = j + 1;
	      adjField = (String)worldMap.get(i + "_" + ss);
	      agentchecker = -1;
	      try
	      {
	        agentchecker = Integer.parseInt(adjField);
	      }
	      catch (Exception localException6) {}
	      if (agentchecker > 0)
	      {
	        tmpKnowledge.put(i + "_" + ss, " ");
	        adjKnowledge = (Hashtable)knowledge.get(adjField);
	        if ((adjKnowledge != null) && (!adjKnowledge.isEmpty())) {
	          tmpKnowledge.putAll(adjKnowledge);
	          exchanged++;

	        }
	      }
	      else
	      {
	        tmpKnowledge.put(i + "_" + ss, (String)worldMap.get(i + "_" + ss));
	      }
	      if (i < linesOfField - 1)
	      {
	        adjField = (String)worldMap.get(tt + "_" + ss);
	        agentchecker = -1;
	        try
	        {
	          agentchecker = Integer.parseInt(adjField);
	        }
	        catch (Exception localException7) {}
	        if (agentchecker > 0)
	        {
	          tmpKnowledge.put(tt + "_" + ss, " ");
	          adjKnowledge = (Hashtable)knowledge.get(adjField);
	          if ((adjKnowledge != null) && (!adjKnowledge.isEmpty())) {
	            tmpKnowledge.putAll(adjKnowledge);
	            exchanged++;

	          }
	        }
	        else
	        {
	          tmpKnowledge.put(tt + "_" + ss, (String)worldMap.get(tt + "_" + 
	            ss));
	        }
	      }
	      if (i > 0)
	      {
	        tt = i - 1;
	        adjField = (String)worldMap.get(tt + "_" + ss);
	        agentchecker = -1;
	        try
	        {
	          agentchecker = Integer.parseInt(adjField);
	        }
	        catch (Exception localException8) {}
	        if (agentchecker > 0)
	        {
	          tmpKnowledge.put(tt + "_" + ss, " ");
	          adjKnowledge = (Hashtable)knowledge.get(adjField);
	          if ((adjKnowledge != null) && (!adjKnowledge.isEmpty())) {
	            tmpKnowledge.putAll(adjKnowledge);
	            exchanged++;

	          }
	        }
	        else
	        {
	          tmpKnowledge.put(tt + "_" + ss, (String)worldMap.get(tt + "_" + 
	            ss));
	        }
	      }
	    }
	    if (j > 0)
	    {
	      int tt = i + 1;
	      int ss = j - 1;
	      
	      adjField = (String)worldMap.get(tt + "_" + j);
	      agentchecker = -1;
	      try
	      {
	        agentchecker = Integer.parseInt(adjField);
	      }
	      catch (Exception localException9) {}
	      if (agentchecker > 0)
	      {
	        tmpKnowledge.put(tt + "_" + ss, " ");
	        adjKnowledge = (Hashtable)knowledge.get(adjField);
	        if ((adjKnowledge != null) && (!adjKnowledge.isEmpty())) {
	          tmpKnowledge.putAll(adjKnowledge);
	            exchanged++;

	        }
	      }
	      else
	      {
	        tmpKnowledge.put(tt + "_" + j, (String)worldMap.get(tt + "_" + j));
	      }
	      tmpKnowledge.put(tt + "_" + j, (String)worldMap.get(tt + "_" + j));
	      if (i < linesOfField - 1)
	      {
	        adjField = (String)worldMap.get(tt + "_" + ss);
	        agentchecker = -1;
	        try
	        {
	          agentchecker = Integer.parseInt(adjField);
	        }
	        catch (Exception localException10) {}
	        if (agentchecker > 0)
	        {
	          tmpKnowledge.put(tt + "_" + ss, " ");
	          adjKnowledge = (Hashtable)knowledge.get(adjField);
	          if ((adjKnowledge != null) && (!adjKnowledge.isEmpty())) {
	            tmpKnowledge.putAll(adjKnowledge);
	            exchanged++;

	          }
	        }
	        else
	        {
	          tmpKnowledge.put(tt + "_" + ss, (String)worldMap.get(tt + "_" + 
	            ss));
	        }
	      }
	      if (i > 0)
	      {
	        tt = i - 1;
	        adjField = (String)worldMap.get(tt + "_" + ss);
	        agentchecker = -1;
	        try
	        {
	          agentchecker = Integer.parseInt(adjField);
	        }
	        catch (Exception localException11) {}
	        if (agentchecker > 0)
	        {
	          tmpKnowledge.put(tt + "_" + ss, " ");
	          adjKnowledge = (Hashtable)knowledge.get(adjField);
	          if ((adjKnowledge != null) && (!adjKnowledge.isEmpty())) {
	            tmpKnowledge.putAll(adjKnowledge);
	            exchanged++;

	          }
	        }
	        else
	        {
	          tmpKnowledge.put(tt + "_" + ss, (String)worldMap.get(tt + "_" + 
	            ss));
	        }
	      }
	    }
	    knowledge.put(field, tmpKnowledge);
	    return knowledge;
	  }

	
	
	/***
	 * Auth h sunarthsh anaferetai sthn kinhsh tou praktora
	 * Dexetai ws eisodo to xarth kai sthn periptwsh poy anixneusei kenh thesh, h' ktirio tote kanei tis analoges energeies
	 * - sthn periptwsh pou einai ktirio tsekarei to plano tou agent (an einai mesa h oxi) kai to diagrafei apo auto
	 * - sthn periptwsh pou einai keno tote kataxwrei stis kenes theseis tis diathesimes kai apo autes dialegei tyxaia th mia epikratesterh kai kineitai pros auth
	 * @param i
	 * @param j
	 * @param agent
	 * @param worldContent
	 * @param agentMovements
	 * @param plan
	 * @return
	 */
	public static Hashtable<String, Hashtable> moveAgent(int i, int j, String agent, Hashtable<String, String> worldContent, Hashtable<String, Hashtable<String, String>> agentMovements, String[][] plan)
	  {
	    String nextFieldUp = "";
	    String nextFieldRight = "";
	    String nextFieldDown = "";
	    String nextFieldLeft = "";
	    int upI = i - 1;
	    int upJ = j;
	    int rightI = i;
	    int rightJ = j + 1;
	    int downI = i + 1;
	    int downJ = j;
	    int leftI = i;
	    int leftJ = j - 1;
	    master = new Master();
	    

	    Hashtable<String, String> am = (Hashtable)agentMovements.get(agent);
	    if (am == null) {
	      am = new Hashtable();
	    }
	    nextFieldUp = (String)worldContent.get(upI + "_" + upJ);
	    nextFieldRight = (String)worldContent.get(rightI + "_" + rightJ);
	    nextFieldDown = (String)worldContent.get(downI + "_" + downJ);
	    nextFieldLeft = (String)worldContent.get(leftI + "_" + leftJ);
	    
	    boolean upFieldOK = (nextFieldUp != null) && (
	      (nextFieldUp.equalsIgnoreCase(" ")) || 
	      (nextFieldUp.equalsIgnoreCase("M")) ||
	      (nextFieldUp.equalsIgnoreCase("P")) ||
	      (nextFieldUp.equalsIgnoreCase("B")));
	    boolean upOK = (!am.containsValue(upI + "_" + upJ)) && (upFieldOK);
	    
	    boolean downFieldOK = (nextFieldDown != null) && (
	    		(nextFieldDown.equalsIgnoreCase(" ")) || 
	  	      (nextFieldDown.equalsIgnoreCase("M")) ||
	  	      (nextFieldDown.equalsIgnoreCase("P")) ||
	  	      (nextFieldDown.equalsIgnoreCase("B")));
	    boolean downOK = (!am.containsValue(downI + "_" + downJ)) && (downFieldOK);
	    
	    boolean leftFieldOK = (nextFieldLeft != null) && (
	    		(nextFieldLeft.equalsIgnoreCase(" ")) || 
	  	      (nextFieldLeft.equalsIgnoreCase("M")) ||
	  	      (nextFieldLeft.equalsIgnoreCase("P")) ||
	  	      (nextFieldLeft.equalsIgnoreCase("B")));
	    boolean leftOK = (!am.containsValue(leftI + "_" + leftJ)) && (leftFieldOK);
	    
	    boolean rightFieldOK = (nextFieldRight != null) && (
	    		(nextFieldLeft.equalsIgnoreCase(" ")) || 
	  	      (nextFieldLeft.equalsIgnoreCase("M")) ||
	  	      (nextFieldLeft.equalsIgnoreCase("P")) ||
	  	      (nextFieldLeft.equalsIgnoreCase("B")));
	    boolean rightOK = (!am.containsValue(rightI + "_" + rightJ)) && 
	      (rightFieldOK);
	    
	    boolean foundNewPos = false;
	    
	    int nextPosIndicator = -1;
	    
	    int availableStrictPos = 0;
	    if (upOK) {
	      availableStrictPos++;
	    }
	    if (rightOK) {
	      availableStrictPos++;
	    }
	    if (downOK) {
	      availableStrictPos++;
	    }
	    if (leftOK) {
	      availableStrictPos++;
	    }
	    // an den exei brei kamia thesh tote dialegw mia apo tis 4 tuxaia
	    if (availableStrictPos == 0)
	    {
	      nextPosIndicator = randomizer.nextInt(4);
	    }
	    
	    // gia kathe mia periptwsh dinw kwdikous stis theseis
	    else if (availableStrictPos == 1)
	    {
	      if (upOK) {
	        nextPosIndicator = 0;
	      } else if (rightOK) {
	        nextPosIndicator = 1;
	      } else if (downOK) {
	        nextPosIndicator = 2;
	      } else if (leftOK) {
	        nextPosIndicator = 3;
	      }
	    }
	    else if ((availableStrictPos == 2) || (availableStrictPos == 3))
	    {
	      int cc = 0;
	      int pseudorandom = randomizer.nextInt(availableStrictPos);
	      while (cc < availableStrictPos)
	      {
	        cc++;
	        if ((upOK) && (cc == availableStrictPos)) {
	          nextPosIndicator = 0;
	        } else if ((rightOK) && (cc == availableStrictPos)) {
	          nextPosIndicator = 1;
	        } else if ((downOK) && (cc == availableStrictPos)) {
	          nextPosIndicator = 2;
	        } else if ((leftOK) && (cc == availableStrictPos)) {
	          nextPosIndicator = 3;
	        }
	      }
	    }
	    else
	    {
	      nextPosIndicator = randomizer.nextInt(availableStrictPos);
	    }
	    
	    
	    Iterator<String> iter = master.moves.keySet().iterator();
	    String key = "";
	    
	    
	    // epanalambanw th diadikasia ews otou brethei thesh
	    while ((!foundNewPos))
	    {
	      switch (nextPosIndicator)
	      {
	      case 0: 
	        if (upFieldOK) {
	        
	        // an brei se kapoia geitonikh thesh ktirio tote sbhnw apo to plano tou th thesh auth 	
	          if (!nextFieldUp.equalsIgnoreCase(" "))
	        		  // an sunanthsei ta ktiria tote psaxnei sto plano tou
	          {
	        	for (int k=0;k<5;k++){
	        		for (int l=0;l<2;l++){
	        			if(plan[Integer.valueOf(agent)-1][l].equalsIgnoreCase(nextFieldUp)){
	        				plan[Integer.valueOf(agent)-1][l]="";
	        				if(agent.equals("1")){
		        				empty_1++;
		        				empty_plan.put(agent,String.valueOf(empty_1));
	        				}
	        				if(agent.equals("2")){
		        				empty_2++;
		        				empty_plan.put(agent,String.valueOf(empty_2));
	        				}
	        				if(agent.equals("3")){
		        				empty_3++;
		        				empty_plan.put(agent,String.valueOf(empty_3));
	        				}
	        				if(agent.equals("4")){
		        				empty_4++;
		        				empty_plan.put(agent,String.valueOf(empty_4));
	        				}
	        				if(agent.equals("5")){
		        				empty_5++;
		        				empty_plan.put(agent,String.valueOf(empty_5));
	        				}
	        				if(agent.equals("6")){
		        				empty_6++;
		        				empty_plan.put(agent,String.valueOf(empty_6));
	        				}
	        				
	        			}
	        		}
	        	}

	            foundNewPos = true;
	          }
	          // alliws apothikeuw th thesh pou prokeitai na paei
	          // efoson brw oti den uparxei ston pinaka
	          else
	          {

	        	  // oxi merh pou exei ksanaepiskeftei px 4_3=4_4,....5_3=4_3
//		        		while(iter.hasNext()){
//		        			
//		        			key = iter.next();
//	        				System.out.println(master.moves.get(key));
//
//		        			if(key.equalsIgnoreCase(agent)){
//		        				
//		        				if (!master.moves.get(key).isEmpty()){
//			        				if(!master.moves.get(key).containsKey(upI+"_"+upJ)){
//					        			worldContent.put(i + "_" + j, " ");
//							            worldContent.put(upI + "_" + upJ, agent);
//							            foundNewPos = true;
//							            am.put(i + "_" + j, upI + "_" + upJ);
//			        				}
//		        				
//			        				else{
//			        					System.out.println(master.moves.get(key)+" and key is: "+upI+"_"+upJ);
//							            foundNewPos = false;
//			        					}
//		        				}
//		        				else{
		        					worldContent.put(i + "_" + j, " ");
						            worldContent.put(upI + "_" + upJ, agent);
						            foundNewPos = true;
						            am.put(i + "_" + j, upI + "_" + upJ);
//		        				}
//		        			}
//		        	
//		        		}	
//		           
	        	 
	        		  
	          }
	         
	        }
	        break;
	      case 1: 
	        if (rightFieldOK) {
	        	if (!nextFieldRight.equalsIgnoreCase(" "))
	        		  // an sunanthsei ta ktiria tote psaxnei sto plano tou
	          {
	        	for (int k=0;k<5;k++){
	        		for (int l=0;l<2;l++){
	        			if(plan[Integer.valueOf(agent)-1][l].equalsIgnoreCase(nextFieldRight)){
	        				plan[Integer.valueOf(agent)-1][l]="";
	        				if(agent.equals("1")){
		        				empty_1++;
		        				empty_plan.put(agent,String.valueOf(empty_1));
	        				}
	        				if(agent.equals("2")){
		        				empty_2++;
		        				empty_plan.put(agent,String.valueOf(empty_2));
	        				}
	        				if(agent.equals("3")){
		        				empty_3++;
		        				empty_plan.put(agent,String.valueOf(empty_3));
	        				}
	        				if(agent.equals("4")){
		        				empty_4++;
		        				empty_plan.put(agent,String.valueOf(empty_4));
	        				}
	        				if(agent.equals("5")){
		        				empty_5++;
		        				empty_plan.put(agent,String.valueOf(empty_5));
	        				}
	        				if(agent.equals("6")){
		        				empty_6++;
		        				empty_plan.put(agent,String.valueOf(empty_6));
	        				}
	        				
	        			
	        			}
	        		}
	        	}

	            foundNewPos = true;
	          }
	          else
	          {
	        	 
//	        	  while(iter.hasNext()){
//	        			
//	        			key = iter.next();
//        				System.out.println(master.moves.get(key));
//
//	        			if(key.equalsIgnoreCase(agent)){
//	        				
//	        				if (!master.moves.get(key).isEmpty()){
//
//		        				if(!master.moves.get(key).containsKey(rightI+"_"+rightJ)){
//				        			worldContent.put(i + "_" + j, " ");
//						            worldContent.put(rightI + "_" + rightJ, agent);
//						            foundNewPos = true;
//						            am.put(i + "_" + j, rightI + "_" + rightJ);
//		        				}
//		        				else{
//		        					System.out.println(master.moves.get(key)+" and key is: "+rightI+"_"+rightJ);
//						            foundNewPos = false;
//	
//		        				}
//	        				}
	        			//	else{
	        					worldContent.put(i + "_" + j, " ");
					            worldContent.put(rightI + "_" + rightJ, agent);
					            foundNewPos = true;
					            am.put(i + "_" + j, rightI + "_" + rightJ);
//	        					
//	        				}
//	        			}
//	        	
//	        		}	
		        			
		           
	          }
	        }
	        break;
	      case 2: 
	        if (downFieldOK) {
	        	if (!nextFieldDown.equalsIgnoreCase(" "))
	        		  // an sunanthsei ta ktiria tote psaxnei sto plano tou
	        	{
	        	for (int k=0;k<5;k++){
	        		for (int l=0;l<2;l++){
	        			if(plan[Integer.valueOf(agent)-1][l].equalsIgnoreCase(nextFieldDown)){
	        				plan[Integer.valueOf(agent)-1][l]="";
	        				if(agent.equals("1")){
		        				empty_1++;
		        				empty_plan.put(agent,String.valueOf(empty_1));
	        				}
	        				if(agent.equals("2")){
		        				empty_2++;
		        				empty_plan.put(agent,String.valueOf(empty_2));
	        				}
	        				if(agent.equals("3")){
		        				empty_3++;
		        				empty_plan.put(agent,String.valueOf(empty_3));
	        				}
	        				if(agent.equals("4")){
		        				empty_4++;
		        				empty_plan.put(agent,String.valueOf(empty_4));
	        				}
	        				if(agent.equals("5")){
		        				empty_5++;
		        				empty_plan.put(agent,String.valueOf(empty_5));
	        				}
	        				if(agent.equals("6")){
		        				empty_6++;
		        				empty_plan.put(agent,String.valueOf(empty_6));
	        				}
	        				
	        			}
	        		}
	        	}
	            foundNewPos = true;
	          }
	          else
	          {
//	        	  while(iter.hasNext()){
//	        			
//	        			key = iter.next();
//        				System.out.println(master.moves.get(key));
//
//	        			if(key.equalsIgnoreCase(agent)){
//	        				
//	        				if (!master.moves.get(key).isEmpty()){
//
//
//		        				if(!master.moves.get(key).containsKey(downI+"_"+downJ)){
//				        			worldContent.put(i + "_" + j, " ");
//						            worldContent.put(downI + "_" + downJ, agent);
//						            foundNewPos = true;
//						            am.put(i + "_" + j, downI + "_" + downJ);
//		        				}
//		        				else{
//		        					System.out.println(master.moves.get(key)+" and key is: "+downI+"_"+downJ);
//						            foundNewPos = false;
//	
//		        				}
//	        				}
//	        				else{
	        					worldContent.put(i + "_" + j, " ");
					            worldContent.put(downI + "_" + downJ, agent);
					            foundNewPos = true;
					            am.put(i + "_" + j, downI + "_" + downJ);
//	        				}
//	        			}
//	        	
//	        		}	
		        			
	           
	          }
	        }
	        break;
	      case 3: 
	        if (leftFieldOK) {
	        	if (!nextFieldLeft.equalsIgnoreCase(" "))
	        		  // an sunanthsei ta ktiria tote psaxnei sto plano tou
	          {
	        	for (int k=0;k<5;k++){
	        		for (int l=0;l<2;l++){
	        			if(plan[Integer.valueOf(agent)-1][l].equalsIgnoreCase(nextFieldLeft)){
	        				plan[Integer.valueOf(agent)-1][l]="";
	        				if(agent.equals("1")){
		        				empty_1++;
		        				empty_plan.put(agent,String.valueOf(empty_1));
	        				}
	        				if(agent.equals("2")){
		        				empty_2++;
		        				empty_plan.put(agent,String.valueOf(empty_2));
	        				}
	        				if(agent.equals("3")){
		        				empty_3++;
		        				empty_plan.put(agent,String.valueOf(empty_3));
	        				}
	        				if(agent.equals("4")){
		        				empty_4++;
		        				empty_plan.put(agent,String.valueOf(empty_4));
	        				}
	        				if(agent.equals("5")){
		        				empty_5++;
		        				empty_plan.put(agent,String.valueOf(empty_5));
	        				}
	        				if(agent.equals("6")){
		        				empty_6++;
		        				empty_plan.put(agent,String.valueOf(empty_6));
	        				}
	        				
	        			}
	        		}
	        	}

	            foundNewPos = true;
	          }
	          else
	          {
	        	  
	        	
//	        	  while(iter.hasNext()){
//	        			
//	        			key = iter.next();
//        				System.out.println(master.moves.get(key));
//
//	        			if(key.equalsIgnoreCase(agent)){
//	        				
//	        				if (!master.moves.get(key).isEmpty()){
//
//		        				if(!master.moves.get(key).containsKey(leftI+"_"+leftJ)){
//				        			worldContent.put(i + "_" + j, " ");
//						            worldContent.put(leftI + "_" + leftJ, agent);
//						            foundNewPos = true;
//						            am.put(i + "_" + j, leftI + "_" + leftJ);
//		        				}
//		        				else{
//		        					System.out.println(master.moves.get(key)+" and key is: "+leftI+"_"+leftJ);
//						            foundNewPos = false;
//	
//		        				}
//	        				}
//	        				else{
	        					worldContent.put(i + "_" + j, " ");
					            worldContent.put(leftI + "_" + leftJ, agent);
					            foundNewPos = true;
					            am.put(i + "_" + j, leftI + "_" + leftJ);
//	        				}
//	        			}
	        	
	  //      		}	
		        			
		        	
		           
	          }
	        }
	        break;
	      }
	      nextPosIndicator = randomizer.nextInt(4);
	    }
	    agentMovements.put(agent, am);
	    new_plan = plan;
	    
	    Hashtable<String, Hashtable> ret = new Hashtable();
	    ret.put("virtualworld", worldContent);
	    ret.put("virtualmoves", agentMovements);
	    
	    return ret;
	  }
}


	

