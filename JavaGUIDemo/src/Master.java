import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;



/**
 * this class knows the agents' plans and what moves they should do next
 * @author eugenia
 *
 */
public class Master {
	
	
	public static ArrayList<String> all_plans = new ArrayList<String>();
	public static ArrayList<List<String>> agent_plan = new ArrayList<List<String>>();
	public static List<String> plan_new = new ArrayList<String>();
	public static ArrayList<List<String>> agent_plan_new = new ArrayList<List<String>>();
	public static Hashtable<String, String> worldMap = new Hashtable();
	public static Hashtable<String,String> overall_map = new Hashtable();
	public static Hashtable<String, String> imp_build = new Hashtable();
	public static Hashtable<String, Hashtable<String,String>> moves = new Hashtable();
	public static Hashtable<String, Hashtable<String,String>> overall_know = new Hashtable();
	public static int rows;
	public static int columns;
	public static int x_world = -1;
	public static int y_world = -1;
	public static Agent agent;
	Hashtable<String, String> agents_pos = new Hashtable();
	public static UI ui;
	public boolean first_time = true;
	public boolean endOfExecution = false;
	public String who_terminated;
	
	public static String[][] plans = {{"P","M","B"}, {"M","P","B"}, {"P","M","B"}, {"P","B","M"}, {"B","M","P"}, {"B","P","M"}};
		
	
	public Master() {
		// TODO Auto-generated constructor stu
		
	}
	
	
	
	public Hashtable<String, String> nextMove(Hashtable<String, String> worldMap){
		
		String field = "";
	    Hashtable<String, String> agents = new Hashtable();
	    for (int i = 0; i < this.rows; i++) {
	      for (int j = 0; j < this.columns; j++)
	      {
	        field = worldMap.get(i + "_" + j);
	        if (!field.equalsIgnoreCase("*")) {
	          if (!field.equalsIgnoreCase(" ")) {
	            if ((!field.equalsIgnoreCase("M")) || (!field.equalsIgnoreCase("P") || (!field.equalsIgnoreCase("B"))))
	            {
	              int agentId = -1;
	              try
	              {
	                agentId = Integer.parseInt(field);
	              }
	              catch (Exception localException) {}
	              // an entopises kapoion agent tote summazepse tous se enan pinaka
	              if (agentId > 0) {
	                agents.put(field, i + "_" + j);
	              }
	            }
	          }
	        }
	      }
	    }
	    // iterate through agents' map
	    Iterator<String> keyIt = agents.keySet().iterator();
	    String agent = null;
	    while (keyIt.hasNext())
	    {
	      agent = keyIt.next();
	      String xy = agents.get(agent);
	      String[] xyArr = xy.split("_");
	      // get position of each agent
	      int x = Integer.parseInt(xyArr[0]);
	      int y = Integer.parseInt(xyArr[1]);
	      
	      if(first_time){
	      // agent_move => dinei to agent_position (int i, int j, String agent, Hashtable<String, String> worldContent, Hashtable<String, Hashtable<String, String>> agentMovements, String[][] plan
		      Hashtable<String, Hashtable> ret = Agent.moveAgent(x, y, agent, worldMap, this.moves, plans);
	          worldMap= ret.get("virtualworld");
	          this.moves = (ret.get("virtualmoves"));
	          // x,y is the position of the agent, world content is the map and overall knowledge is the knowledge of the agent
	          this.overall_know = Agent.exchangeknowledge(this.rows, this.columns, x, y, agent, worldMap, this.overall_know);
//	          for(int k=0;k<2;k++){
//	  	    	System.out.print("This is the plan of agent "+agent+" "+plans[Integer.valueOf(agent)][k]);
//	  	    }
	          System.out.print("This is the plan of agent "+agent);
	          for (int i = 0; i <plans.length; i++) {
		          for (int j = 0; j < plans[i].length; j++) {
		            System.out.print(plans[i][j] + " ");
		          }
		          System.out.println();
		        }
	      }
	      else{
	    	  Hashtable<String, Hashtable> ret = Agent.moveAgent(x, y, agent, worldMap, this.moves, Agent.new_plan);
	          worldMap= ret.get("virtualworld");
	          this.moves = (ret.get("virtualmoves"));
	          // x,y is the position of the agent, world content is the map and overall knowledge is the knowledge of the agent
	          this.overall_know = Agent.exchangeknowledge(this.rows, this.columns, x, y, agent, worldMap, this.overall_know);
	          System.out.println("This is new plan for agent: "+agent);
	          int empty = 0;
	          for (int i = 0; i <Agent.new_plan.length; i++) {
		          for (int j = 0; j < Agent.new_plan[i].length; j++) {
			            System.out.print(plans[i][j] + " ");
		            	
		            }
		          	System.out.println();

		          }
		       
	          System.out.println("empty is:"+Agent.empty_plan);
	          
	          
	          Iterator<String> it = Agent.empty_plan.keySet().iterator();
	          String key="";
	          while(it.hasNext()){
	        	  key=it.next();
	        	  if(Agent.empty_plan.get(key).equals("1")){
	        		  endOfExecution = true;
	        		  who_terminated=key;
		        	  System.out.println("System should terminate execution!");
	        	  }
	          }
	          
	      }
	      
	      System.out.println("This is agent: "+agent+" and his moves are: "+this.moves.get(agent));
		  System.out.println("His overall Knowledge is: "+this.overall_know.get(agent));
	      
	    }
	   
	   
	    first_time = false;
	    return worldMap;
	}
	
	

	
	public static Hashtable<String, String> WorldMap(String filename){
		
		/*if ((vw_fl != null) && (vw_fl.length() > 0L))
	    {*/
	      try
	      {
	        FileReader fr = new FileReader(new File(filename));
	        BufferedReader br = new BufferedReader(fr);
	        
	        rows = 0;
	        columns = 0;
	        String tmpField = null;
	        String tmpLineCont = br.readLine();
	        while (tmpLineCont != null)
	        {
	          int tmpCol = 0;
	          for (int i = 0; i < tmpLineCont.length(); i++)
	          {
	            tmpField = Character.toString(tmpLineCont.charAt(i));
	            System.out.print(tmpField);
	            // an einai ena apo ta shmantika ktiria
	            if ((tmpField != null) && ((tmpField.equalsIgnoreCase("B")||tmpField.equalsIgnoreCase("P")||tmpField.equalsIgnoreCase("M"))))
	            {
	              x_world = rows;
	              System.out.println("Row is: "+ x_world+"\n");
	              y_world = tmpCol;
	              System.out.println("Col is: "+y_world+"\n");
	              
	              imp_build.put(tmpField,x_world+"_"+y_world);
	            }
	            worldMap.put(rows + "_" + tmpCol, tmpField);
	            System.out.println("worldMap putting: "+rows + "_" + tmpCol + " " +tmpField);
	            tmpCol++;
	          }
	          if (tmpCol > columns) {
	            columns = tmpCol;
	          }
	          rows += 1;
	          
	          tmpLineCont = br.readLine();
	        }
	        br.close();
	        fr.close();
	      }
	      catch (Exception exe)
	      {
	        exe.printStackTrace();
	      }
	    //}
		
		return worldMap;
	}
	
	
	
	
	
	
	
	// all agents move until their plan is empty
	
	
	
	
	
	
	/**
	 * reads from txt file agents_plans and returns a list with all plans
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<String> readFromFile(String filename) throws IOException{
		
		
		ArrayList<String> list = new ArrayList<>();

		// New BufferedReader.
		BufferedReader reader = new BufferedReader(new FileReader(filename));

		// Add all lines from file to ArrayList.
		while (true) {
		    String line = reader.readLine();
		    if (line == null) {
			break;
		    }
		    list.add(line);
		}

		// Close it.
		reader.close();
		
		// Print size of ArrayList.
		//System.out.println("Lines: " + list.size());

		// Print each line.
		/*for (String line : list) {
		    System.out.println(line);
		}*/

	    return list;
	  
		
	}
	
}
