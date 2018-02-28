package com.Shashank.BlockRunner.Main;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;



public class BlockRunnerDesktop extends JFrame{
	
	
	private JPanel contentPane;
	private JButton control;
	private JTextArea field;
	private JRadioButton but;
	private JCheckBox Easy;
	private JCheckBox Hard;
	private JCheckBox Med;
	private JButton options;
	public static String modeSel = "";
	
	public static void main(String[] args)
	{
			mtgr();
	}
	public static void mtgr()
	{
		int numberOfTasks = 1;//sets # of tasks
		int batchSize = 2000;//sets the batch size for system to process the less the better, if greater than 2000 then you will get blue screen
		ExecutorService es = Executors.newFixedThreadPool(3);//sets the number of threads which is 3 in this case
		for (int i = 0; i < numberOfTasks; i += batchSize) {//loop thrue the batch size
		    final int start = i;//create a startubg point 
		    final int last = Math.min(i + batchSize, numberOfTasks);//get the last predicted set of loop
		    es.submit(new Runnable() {//submit the thread to the executor that is hanfeling the thread
		        @Override
		        public void run() {//override the run method in the runnable
		            for (int j = start; j < last; j++)//loop thru the # of tasks
		            {
						LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
						
						cfg.title = Game.TITLE;
						cfg.width = Game.V_WIDTH * Game.SCALE;
						cfg.height = Game.V_HEIGHT * Game.SCALE;
						
						
						new LwjglApplication(new Game(),cfg);
						

						//BlockRunnerDesktop frame = new BlockRunnerDesktop();
						//frame.setVisible(true);
		            }//end
		        }//end
		    });//end
		}//end
		
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
		//System.out.print(es.toString()+"\n");
		for(int i = 0;i < threadArray.length;i++)
		{
			//System.out.println(threadArray[i]);
		}
	}
	
	static Dimension dimen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	public  BlockRunnerDesktop() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, dimen.width,dimen.height);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new FlowLayout());
		setContentPane(contentPane);
		
		////SETUP BUTTONS
		JButton start = new JButton ("Start");
		control = new JButton ("Controls");
		contentPane.add(start);
		contentPane.add(control);
		
		////SETUP INTRO
		this.field = new JTextArea(3,6);
		field.setSize(500,50);
		field.setEditable(false);
		field.setLocation(20, 50);
		
		////SETUP OPTIONS BUTTON
		options = new JButton("Options");
		contentPane.add(options);
		
		
		///SETUP RADIO BUTTONS
		Easy = new JCheckBox("EASY");
		Easy.setMnemonic(KeyEvent.VK_C);
		Easy.setLocation(300,300);
		
		Med = new JCheckBox("Medium");
		Med.setMnemonic(KeyEvent.VK_C);
		Med.setLocation(300, 350);
		
		Hard = new JCheckBox("HARD");
		Hard.setLocation(300,400);
		Hard.setMnemonic(KeyEvent.VK_C);
		
		contentPane.updateUI();
		
		System.out.print(contentPane.getSize());

		
		
		
		
		
		
		start.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent e){
				System.out.println("Game Started");
				
				LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
				
				
				cfg.title = Game.TITLE;
				cfg.width = Game.V_WIDTH * Game.SCALE;
				cfg.height = Game.V_HEIGHT * Game.SCALE;
				
				
				new LwjglApplication(new Game(),cfg);
				
				dispose();

			}
		});
		
		control.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent e){
				contentPane.remove(Easy);
				contentPane.remove(Med);
				contentPane.remove(Hard);
				getFile(field, "test");
				
				contentPane.add(field);
				contentPane.updateUI();

				
			}
		});
		
		options.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				contentPane.remove(field);
				contentPane.add(Easy);
				contentPane.add(Med);
				contentPane.add(Hard);
				
					

					
					contentPane.updateUI();
					
					Easy.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent e)
						{
							System.out.println("Easy Selected "+Easy.isSelected());
							modeSel = "Easy";
							if(Easy.isSelected())
								Med.setVisible(false);
							else
								Med.setVisible(true);
							if(Easy.isSelected())
								Hard.setVisible(false);
							else
								Hard.setVisible(true);
						}
						
						
					});
					
					Med.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent e)
						{
							System.out.println("Medium Selected "+Med.isSelected());
							modeSel = "Med";
							if(Med.isSelected())
								Easy.setVisible(false);
							else
								Easy.setVisible(true);
							if(Med.isSelected())
								Hard.setVisible(false);
							else
								Hard.setVisible(true);
						}
						
						
					});
					Hard.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent e)
						{
							System.out.println("Hard Selected "+Hard.isSelected());
							modeSel = "Hard";
							if(Hard.isSelected())
								Easy.setVisible(false);
							else
								Easy.setVisible(true);
							if(Hard.isSelected())
								Med.setVisible(false);
							else
								Med.setVisible(true);
						}
						
						
					});

				
			}
		});
		}
	
	public void getFile(JTextArea edit, String name)
	{
		try
        {
            FileReader reader = new FileReader( name+".txt" );
            BufferedReader br = new BufferedReader(reader);
            edit.read( br, null );
            br.close();
            edit.requestFocus();
        }
        catch(Exception e2) { 
        	System.out.println("Cannot open file");
        }
	}

}

