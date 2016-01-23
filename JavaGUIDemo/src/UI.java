import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.Leading;



public class UI implements Runnable{
	public boolean paused = false;
	public JLabel message;
	public boolean endOfExecution = false;
	public static Hashtable<String, Hashtable<String,String>> overall_know = new Hashtable();
	private Master master;
	public Thread execThrd = null;
	private JFrame frame;
	private JPanel panel;
	private JTextField FilePath;
	private JLabel lblWorldMap;
	private File fl;
	public int rows;
	public int columns;
	public int x_world = -1;
	public int y_world = -1;	
	public Hashtable<String, String> worldMap = new Hashtable();
	public Hashtable<String, JLabel> worldGUI = new Hashtable();
	public static ArrayList<List<String>> agent_plan = new ArrayList<List<String>>();
	private GroupLayout groupLayout;
	private JButton btnAgent1;
	private JButton btnAgent2;
	private JButton btnAgent3;
	private JButton btnAgent4;
	private JButton btnAgent5;
	private JButton btnAgent6;
	private JButton btnStart;
	private JButton btnPause;
	private JButton btnStop;
	private JButton btnLoad;
	private JTextField PathAgentPlan;
	private JLabel lblLoadedPlans;
	private JLabel lblTerminated;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					
					UI window = new UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		if (worldMap.isEmpty()){
			worldMap = new Hashtable();
		}
		master = new Master();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 859, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*worldMap = master.DefineNextMove(master.WorldMap(fl.getAbsolutePath()));
				paintWorld(worldMap);
				System.out.println("painted world");*/
				buttonStart();
			}
		});
		btnStart.setVisible(false);
		
		btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*worldMap = master.DefineNextMove(master.WorldMap(fl.getAbsolutePath()));
				paintWorld(worldMap);
				System.out.println("painted world");*/
				pauseExecution();
			}
		});
		btnPause.setVisible(false);
		
		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*worldMap = master.DefineNextMove(master.WorldMap(fl.getAbsolutePath()));
				paintWorld(worldMap);
				System.out.println("painted world");*/
				pauseExecution();
			}
		});
		btnStop.setVisible(false);
		
		
		btnLoad = new JButton("Load");
		// when load button is pressed worldmap, buttons start, pause, stop, agent1,....agent10 become visible
		btnLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showWorld();
				ArrayList<String> all_plans = new ArrayList<String>();
				try {
					all_plans = readFromFile("src/Agent_plans.txt");
					System.out.println("this is ui and all_plans"+all_plans);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				btnStart.setVisible(true);
				btnStop.setVisible(true);
				btnPause.setVisible(true);
				btnAgent1.setVisible(true);
				btnAgent2.setVisible(true);
				btnAgent3.setVisible(true);
				btnAgent4.setVisible(true);
				btnAgent5.setVisible(true);
				btnAgent6.setVisible(true);
				
			}
		});
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ChooseFile();
			}
		});
		
		FilePath = new JTextField();
		FilePath.setText("Absolute Path of the chosen file");
		FilePath.setColumns(10);
		
		JLabel lblVirtualWorld = new JLabel("Virtual World");
		
		btnAgent1 = new JButton("Agent no1");
		btnAgent1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pauseExecution();
			    if (paused) {
			      markKnownPlaces("1");
			    } else {
			      hideKnownPlaces("1");
			    }
			}
		});
		btnAgent1.setVisible(false);
		
		btnAgent2 = new JButton("Agent no2");
		
		btnAgent2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pauseExecution();
			    if (paused) {
			      markKnownPlaces("2");
			    } else {
			      hideKnownPlaces("2");
			    }
			}
		});
		btnAgent2.setVisible(false);

		btnAgent3 = new JButton("Agent no3");
		btnAgent3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pauseExecution();
			    if (paused) {
			      markKnownPlaces("3");
			    } else {
			      hideKnownPlaces("3");
			    }
			}
		});
		
		btnAgent3.setVisible(false);

		lblWorldMap = new JLabel("");
		
		panel = new JPanel(new SpringLayout());
		
		JLabel lblAgentPlan = new JLabel("Agents' Plans");
		
		PathAgentPlan = new JTextField();
		PathAgentPlan.setText("Absolute Path of the chosen file");
		PathAgentPlan.setColumns(10);
		
		lblLoadedPlans = new JLabel("");
		
		btnAgent4 = new JButton("Agent no4");
		btnAgent4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pauseExecution();
			    if (paused) {
			      markKnownPlaces("4");
			    } else {
			      hideKnownPlaces("4");
			    }
			}
		});
		btnAgent4.setVisible(false);
		
		btnAgent5 = new JButton("Agent no5");
		btnAgent5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pauseExecution();
			    if (paused) {
			      markKnownPlaces("5");
			    } else {
			      hideKnownPlaces("5");
			    }
			}
		});
		btnAgent5.setVisible(false);
		
		btnAgent6 = new JButton("Agent no6");
		btnAgent6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pauseExecution();
			    if (paused) {
			      markKnownPlaces("6");
			    } else {
			      hideKnownPlaces("6");
			    }
			}
		});
		btnAgent6.setVisible(false);
		
		lblTerminated = new JLabel("Program terminated!");
		lblTerminated.setVisible(false);
		
		groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(30)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblAgentPlan)
										.addComponent(lblVirtualWorld)))
								.addComponent(btnAgent4)
								.addComponent(btnAgent1)
								.addComponent(btnAgent5)
								.addComponent(btnAgent6)
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(getbtnStart())
									.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
									.addComponent(getbtnPause())))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(50)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(panel, GroupLayout.PREFERRED_SIZE, 479, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(lblTerminated))
										.addGroup(groupLayout.createSequentialGroup()
											.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(PathAgentPlan, Alignment.LEADING)
												.addComponent(FilePath, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE))
											.addGap(162)
											.addComponent(lblLoadedPlans))))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(getbtnStop()))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnAgent3)
								.addComponent(btnAgent2))
							.addGap(87)
							.addComponent(lblWorldMap)))
					.addContainerGap(69, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(695, Short.MAX_VALUE)
					.addComponent(btnSearch)
					.addGap(18)
					.addComponent(btnLoad)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(getbtnStart())
						.addComponent(getbtnPause())
						.addComponent(getbtnStop()))
					.addGap(135)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVirtualWorld)
						.addComponent(FilePath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(40)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnLoad)
						.addComponent(btnSearch))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblWorldMap)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblAgentPlan)
									.addComponent(PathAgentPlan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblLoadedPlans)))
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(84)
									.addComponent(btnAgent1)
									.addGap(18)
									.addComponent(btnAgent2)
									.addGap(18)
									.addComponent(btnAgent3)
									.addGap(18)
									.addComponent(btnAgent4)
									.addGap(18)
									.addComponent(btnAgent5)
									.addGap(18)
									.addComponent(btnAgent6)
									.addContainerGap(145, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(panel, GroupLayout.PREFERRED_SIZE, 387, GroupLayout.PREFERRED_SIZE)
									.addContainerGap())))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(224)
							.addComponent(lblTerminated)
							.addContainerGap())))
		);
		
		
		frame.getContentPane().setLayout(groupLayout);
	}
	
	private JLabel getEndMsg()
	  {
	    if (this.message == null)
	    {
	      this.message = new JLabel();
	      this.message.setBackground(Color.WHITE);
	      this.message.setFont(new Font("Dialog", 1, 18));
	      this.message.setForeground(Color.BLUE);
	      this.message.setHorizontalAlignment(0);
	      this.message.setText("End of Execution!");
	    }
	    return this.message;
	  }
	
	private JButton getbtnLoad(){
		
		 if (this.btnLoad == null)
		    {
		      this.btnLoad = new JButton();
		      this.btnLoad.setText("Load");
		      btnLoad.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						buttonLoad();
					}
				});
		    }
		    return this.btnLoad;
	}
	
	private JButton getbtnStart()
	  {
	    if (this.btnStart == null)
	    {
	      this.btnStart = new JButton();
	      this.btnStart.setText("Start");
	      btnStart.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					buttonStart();
				}
			});
	    }
	    return this.btnStart;
	  }
	
	
	
	 private JButton getbtnPause()
	  {
	    if (this.btnPause == null)
	    {
	      this.btnPause = new JButton();
	      this.btnPause.setText("Pause");
	      btnPause.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					pauseExecution();
				}
			});
	    }
	    
	    return this.btnPause;
	  }
	 
	 
	 private JButton getbtnStop()
	  {
	    if (this.btnStop == null)
	    {
	      this.btnStop = new JButton();
	      this.btnStop.setText("Stop");
	      btnStop.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					buttonStop();
				}
			});
	    }
	    
	    return this.btnStop;
	  }
	
	 	private void buttonStart(){
			/*worldMap = master.DefineNextMove(master.WorldMap(fl.getAbsolutePath()));
			paintWorld(worldMap);
			System.out.println("painted world");*/
			
			 if (this.execThrd == null) {
			      this.execThrd = new Thread(this);
			    }
			    if (this.execThrd.isAlive()) {
			      this.execThrd.interrupt();
			    }
			    this.execThrd.start();
		}
	
	 	
	 	private void pauseExecution()
	 	  {
	 	    try
	 	    {
	 	      this.paused = (!this.paused);
	 	    }
	 	    catch (Exception e)
	 	    {
	 	      e.printStackTrace();
	 	    }
	 	  }
	 	
	 	
	 	 private void buttonStop()
	 	  {
	 	    Thread.currentThread().interrupt();
	 	  }
	 	 
	 	 
	 	 private void buttonLoad(){
	 		 
	 		showWorld();
			
	 	 }
	
	 	 
	 	
	/** File Chooser behind "Search" Button 
	 * @return 
	 * 
	 */
	private void ChooseFile(){
		JFileChooser chooser = new JFileChooser(); //menu for choosing file
	    int result = chooser.showOpenDialog(null); 
	    

	    switch (result) {
	    case JFileChooser.APPROVE_OPTION:
	    	fl = chooser.getSelectedFile();
	    	FilePath.setText(fl.getAbsolutePath());
	    	PathAgentPlan.setText("Agent_plans.txt");
	    	
	      System.out.println("Approve (Open or Save) was clicked");
	      break;
	    case JFileChooser.CANCEL_OPTION:
	      System.out.println("Cancel or the close-dialog icon was clicked");
	      break;
	    case JFileChooser.ERROR_OPTION:
	      System.out.println("Error");
	      break;
	    }
	    
	   
	}
	

		
	
		private void showWorld()
		{
	      paintWorld(Master.WorldMap(fl.getAbsolutePath()));
	    }

	
		
		/***
		 * This method takes the ArrayList given as parameter and splits it into equal sublists.
		 * The returned list is an arraylist of lists.
		 * @param all_plans
		 * @return
		 */
		public static ArrayList<List<String>> splitArrayList(ArrayList<String> all_plans){
			
			ArrayList<List<String>> agent_plan = new ArrayList<List<String>>();
			
			for (int start = 0; start < all_plans.size(); start += 4) {
		        int end = Math.min(start + 4, all_plans.size());
		        List<String> plan = all_plans.subList(start, end);
		        agent_plan.add(plan);
			}
			
			return agent_plan;
		}
		
		
		
		
		
		
		
		
		
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
		
	/*public void paintWorld(Hashtable<String, String> worldContent)
	  {
	    try
	    {
	      Iterator<String> it = this.worldGUI.keySet().iterator();
	      
	      String key = "";
	      JLabel tmpLabel = null;
	      while (it.hasNext())
	      {
	        key = it.next();
	        System.out.println("tmpLabel: "+tmpLabel);
	        tmpLabel = this.worldGUI.get(key);
	        if (tmpLabel != null)
	        {
	          tmpLabel.setText("");
	          tmpLabel.repaint();
	        }
	      }
	      String tmpField = "";
	      // Looping through the map
	      for (int i = 0; i < master.rows; i++) {
	        for (int j = 0; j < master.columns; j++)
	        {
	          tmpField = worldContent.get(i + "_" + j);
	          if (!this.worldGUI.containsKey(i + "_" + j))
	          {
	            if ((tmpField != null) && (tmpField.length() > 0))
	            {
	              JLabel newJL = new JLabel();
	              newJL.setHorizontalAlignment(0);
	              newJL.setName("shmeio" + i + "_" + j);
	              newJL.setText(tmpField);
	              if ((i == 0) && (j == 0))
	              {
	                this.lblWorldMap.setText(tmpField);
	                this.lblWorldMap.setVisible(true);
	                this.worldGUI.put(i + "_" + j, this.lblWorldMap);
	              }
	              else
	              {
	            	System.out.println("newJL get name: "+newJL.getName());
	            	panel.add(newJL);
	                this.worldGUI.put(i + "_" + j, newJL);
	              }
	            }
	            
	          }
	         
	          else
	          {
	        	 
	            JLabel tmplabel = this.worldGUI.get(i + "_" + j);
	            tmplabel.setText(tmpField);
	            tmpLabel.repaint();
	          }
	          
	          
	        }
	      }
	      
	      SpringUtilities.makeGrid(panel, //parent
                  20,20,
                  10,10,  //initX, initY
                  8,8); //xPad, yPad
	    }
	    catch (Exception exe)
	    {
	      exe.printStackTrace();
	    }
	  }*/
	
		public void markKnownPlaces(String agentKey)
		  {
		    Hashtable<String, String> know = master.overall_know.get(agentKey);
		    if ((know != null) && (!know.isEmpty()))
		    {
		      Iterator<String> kit = know.keySet().iterator();
		      while (kit.hasNext())
		      {
		        String knownPosition = kit.next();
		        JLabel knownJLabel = this.worldGUI.get(knownPosition);
		        knownJLabel.setBorder(LineBorder.createBlackLineBorder());
		      }
		    }
		  }
		  
		  public void hideKnownPlaces(String agentKey)
		  {
		    Iterator<String> kit = worldMap.keySet().iterator();
		    while (kit.hasNext())
		    {
		      String knownPosition = kit.next();
		      JLabel knownJLabel = worldGUI.get(knownPosition);
		      knownJLabel.setBorder(null);
		    }
		  }
		
		
		
	/**
	 * This function paints the worldMap to the window GUI
	 * @param worldContent
	 */
	public void paintWorld(Hashtable<String, String> worldcontent){
		
		
		 try
		    {
			 // to hashtable worldGUI krataei tis labels pou periexouhn ta shmeia tou xarth
		    // elegxw an h label uparxei sto hashtable worldGUI
		      Iterator<String> it = this.worldGUI.keySet().iterator();
		      
		      String key = "";
		      JLabel tmpLabel = null;
		      while (it.hasNext())
		      {
		        key = it.next();
		        tmpLabel = this.worldGUI.get(key);
		        // an h tmpLabel DEN einai kenh tote shmainei pws krataei tis times tou prohgoymenoy xarth kai ara prepei na thn ksanazografisw
		        if (tmpLabel != null)
		        {
		        // opote thn adeiazw	
		          tmpLabel.setText("");
		          tmpLabel.repaint();
		        }
		      }
			String tmpField="";
	        for (int r = 0; r < Master.rows; r++) {
	            for (int c = 0; c < Master.columns; c++) {
	                
	            	tmpField = worldcontent.get(r+"_"+c);
	            	if (!this.worldGUI.containsKey(r + "_" + c)){
	                
	                  if ((tmpField != null) && (tmpField.length() > 0)){
	                
		            	JLabel newJL = new JLabel();
		            	 newJL.setHorizontalAlignment(0);
			             newJL.setName("shmeio" + r + "_" + c);
			             newJL.setText(tmpField);
			             panel.add(newJL);
			             
			             this.worldGUI.put(r+"_"+c, newJL);
	                  }
	                }
	            	else
	                 {
	                   JLabel tmplabel = this.worldGUI.get(r + "_" + c);
	                   tmplabel.setText(tmpField);
	                   tmpLabel.repaint();
	                   panel.add(tmplabel);
	                 }
	            }
	 
	        }
	        //Lay out the panel.
	        SpringUtilities.makeCompactGrid(panel, //parent
	                                        Master.rows, Master.columns,
	                                        10, 10,  //initX, initY
	                                        10, 10); //xPad, yPad
	        
		    }
		 catch (Exception exe)
		    {
		      exe.printStackTrace();
		    }
		 
	}
	
	
	
	
	
	
	public void run()
	  {
		btnStart.setVisible(false);
		master = new Master();
	    try
	    {
	      int cycles = 0;
	      long start = System.currentTimeMillis();
	      for (; !master.endOfExecution; Thread.sleep(100L))
	      {
	        cycles++;
	        for (; paused; Thread.sleep(100L)) {
	          Thread.currentThread();
	        }
	        worldMap = master.nextMove(master.worldMap);
	      
	        
	        paintWorld(worldMap);
	        Thread.currentThread();
	        System.out.println(cycles);
	      }
	      long end = System.currentTimeMillis();
	      Hashtable<String, String> path = new Hashtable();
	      int steps= 0;
	      Iterator<String> iter = master.moves.keySet().iterator();
	      String key ="";
	      while (iter.hasNext()){
	    	  key = iter.next();
	    	  if(key.equals(master.who_terminated)){
	    		  
	    		  path = master.moves.get(key);
	    		  steps = master.moves.get(key).size();
	    	  }
	      }
	      
	      File file = new File("stats.txt");
	      // creates the file
	      file.createNewFile();
	      // creates a FileWriter Object
	      FileWriter writer = new FileWriter(file); 
	      // Writes the content to the file
	      writer.write("Algorithm executed in : "+(end-start)+"msec as agent "+master.who_terminated+" reached home \n"); 
	      writer.write("Number of steps he did : "+steps+"\n");
	      writer.write("Path he followed : "+path+"\n");
	      writer.write("He participated in "+Agent.exchanged+" exchanges of knowledge");
	      writer.flush();
	      writer.close();
	      
	      
	      lblTerminated.setVisible(true);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    btnStart.setVisible(true);
	  }
}
