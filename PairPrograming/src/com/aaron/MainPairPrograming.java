
package com.aaron;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.*;

import net.Command;

//add something
//add 
public class MainPairPrograming extends JFrame implements ActionListener{
	static String DotPath = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot";
	JMenuBar theMenuBar;
	JMenu fileMenu, operationMenu, settingsMenu;
	JMenuItem importJMI, generateJMI, saveJMI ,closeJMI ,showJMI, bridgeWordsJMI, newTextJMI;
	JMenuItem shortestPathJMI, randomlyTravelJMI, interfaceJMI, testJMI;
	JLabel label;
	Canvas panelForDrawing;
	
	private static final long serialVersionUID = -8534844170998963067L;
	

	MainPairPrograming(){
		super("PairProgramming");
		
		theMenuBar = new JMenuBar();
		fileMenu = new JMenu("File(F)");
		operationMenu = new JMenu("Operation(O)");
		settingsMenu = new JMenu("Settings(S)");
		
		importJMI = new JMenuItem("Import(I)", 'I');
		generateJMI = new JMenuItem("Generate directed graph(G)", 'G');
		saveJMI = new JMenuItem("Save the picture(V)", 'V');
		closeJMI = new JMenuItem("Close the file(C)", 'C');
		showJMI = new JMenuItem("Show directed graph(S)", 'S');
		bridgeWordsJMI = new JMenuItem("Find bridge words(B)", 'B');
		newTextJMI = new JMenuItem("Formulate new text(N)", 'N');
		shortestPathJMI = new JMenuItem("Calculate shortest path(P)", 'P');
		randomlyTravelJMI = new JMenuItem("Randomly travel(T)", 'T');
		interfaceJMI = new JMenuItem("Interface settings(U)", 'U');
		testJMI = new JMenuItem("Test(X)", 'X');
		
		setJMenuBar(theMenuBar);
		theMenuBar.add(fileMenu);
		theMenuBar.add(operationMenu);
		theMenuBar.add(settingsMenu);
		fileMenu.add(importJMI);
		fileMenu.add(generateJMI);
		fileMenu.add(saveJMI);
		fileMenu.add(closeJMI);
		operationMenu.add(showJMI);
		operationMenu.add(bridgeWordsJMI);
		operationMenu.add(newTextJMI);
		operationMenu.add(shortestPathJMI);
		operationMenu.add(randomlyTravelJMI);
		settingsMenu.add(interfaceJMI);
		settingsMenu.add(testJMI);
		
		importJMI.addActionListener(this);
		generateJMI.addActionListener(this);
		saveJMI.addActionListener(this);
		closeJMI.addActionListener(this);
		showJMI.addActionListener(this);
		bridgeWordsJMI.addActionListener(this);
		newTextJMI.addActionListener(this);
		shortestPathJMI.addActionListener(this);
		randomlyTravelJMI.addActionListener(this);
		interfaceJMI.addActionListener(this);
		testJMI.addActionListener(this);
		
		settingsMenu.setEnabled(false);
		operationMenu.setEnabled(false);
		generateJMI.setEnabled(false);
		saveJMI.setEnabled(false);
		closeJMI.setEnabled(false);
		showJMI.setEnabled(false);
		bridgeWordsJMI.setEnabled(false);
		newTextJMI.setEnabled(false);
		shortestPathJMI.setEnabled(false);
		randomlyTravelJMI.setEnabled(false);
		
		label = new JLabel();
		getContentPane().add(label, BorderLayout.NORTH);
	}
	
	File fileToOpen;
	ListDG graph;
	GraphComponents componentsToDraw;
	
	public void actionPerformed(ActionEvent e){
		JMenuItem source = (JMenuItem)(e.getSource());
		label.setText("the chosen menu item is "+source.getText());
		label.setHorizontalAlignment(JLabel.CENTER);
		String operation = source.getText();
		int temp = operation.length()-2;
		switch(operation.charAt(temp)){
		case 'I': importFile(); break;
		case 'G': generate(); break;
		case 'V': save(); break;
		case 'C': close(); break;
		case 'S': showGraph(); break;
		case 'B': queryBridgeWords(); break;
		case 'N': formulateNewText(); break;
		case 'P': shortestPath(); break;
		case 'T': randomTravel(); break;
		}
	}

	public static void main(String[] args){
		JFrame frame = new MainPairPrograming();
		SetLookAndFeel.setNativeLookAndFeel();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(800, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void importFile(){
		final JFileChooser fc = new JFileChooser();
		int returnValue = fc.showOpenDialog(MainPairPrograming.this);
		if (returnValue == JFileChooser.APPROVE_OPTION){
			fileToOpen = fc.getSelectedFile();
			label.setText("Opening File: " + fileToOpen.getName() + ".\n");
			
			importJMI.setEnabled(false);
			closeJMI.setEnabled(true);
			generateJMI.setEnabled(true);
			}
		else {
			label.setText("Cancel opening operation.\n");
			fileToOpen = null;
		}
	}
	
	private void generate(){
		try {
			graph = new ListDG(fileToOpen);
			componentsToDraw = new GraphComponents(graph);
			
			operationMenu.setEnabled(true);
			saveJMI.setEnabled(true);
			showJMI.setEnabled(true);
		}
		catch(FileNotFoundException e){
			if (fileToOpen.getName() == null)
				label.setText("The file cannot be null.");
			else label.setText("File "+fileToOpen.getName()+" is not found.");
		}
	}

	private void save(){
		componentsToDraw = new GraphComponents(graph);
		componentsToDraw.toDot("DotFile.gv");
		File dotFile = new File("DotFile.gv");
		File thisDirectory = new File("");
		Command.execute(thisDirectory.getAbsolutePath()+"\\dot "+dotFile.getAbsolutePath()+" -T gif -o Graph.gif");
	}
	
	private void close(){
		fileToOpen = null;
		graph = null;
		componentsToDraw = null;
		
		operationMenu.setEnabled(false);
		generateJMI.setEnabled(false);
		saveJMI.setEnabled(false);
		closeJMI.setEnabled(false);
		showJMI.setEnabled(false);
		bridgeWordsJMI.setEnabled(false);
		newTextJMI.setEnabled(false);
		shortestPathJMI.setEnabled(false);
		randomlyTravelJMI.setEnabled(false);
		importJMI.setEnabled(true);
	}
	
	private void showGraph(){
		componentsToDraw.toDot("DotFileRunning.gv");
		File theDirectory = new File("DotFileRunning.gv");
		File thisDirectory = new File("");
		Command.execute(thisDirectory.getAbsolutePath()+"\\dot "+theDirectory.getAbsolutePath()+" -T gif -o GraphRunning.gif");
		
		bridgeWordsJMI.setEnabled(true);
		newTextJMI.setEnabled(true);
		shortestPathJMI.setEnabled(true);
		randomlyTravelJMI.setEnabled(true);
	}
	
	JDialog bridgeWordsDialog;
	JLabel inputWord1, inputWord2;
	JTextField word1, word2;
	JLabel outputWords;
	JButton query;
	GridBagLayout gridbag;
	GridBagConstraints c;
	private void gridset(GridBagConstraints c, int gx, int gy, int gw, int gh, int ix, int iy, double wx, double wy
			){
		c.gridx = gx;
		c.gridy = gy;
		c.gridwidth = gw;
		c.gridheight = gh;
		c.ipadx = ix;
		c.ipady = iy;
		c.weightx = wx;
		c.weighty = wy;
	}
	private void queryBridgeWords(){
		bridgeWordsDialog = new JDialog(this, "Query Bridge Words");
		gridbag = new GridBagLayout();
		c = new GridBagConstraints();
		bridgeWordsDialog.setFont(new Font("SansSerif", Font.PLAIN, 14));
		bridgeWordsDialog.setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		inputWord1 = new JLabel("Input word 1:");
		inputWord2 = new JLabel("Input word 2:");
		word1 = new JTextField();
		word2 = new JTextField();
		query = new JButton("query");
		outputWords = new JLabel();
		
		query.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String wordString1 = new String(word1.getText());
				String wordString2 = new String(word2.getText());
				wordString1 = wordString1.toLowerCase();
				wordString2 = wordString2.toLowerCase();
				outputWords.setText(GraphFunctions.queryBridgeWords(graph, wordString1, wordString2));
			}
		});
		
		gridset(c,0,0,1,1,6,1,0,0);
		gridbag.setConstraints(inputWord1, c);
		bridgeWordsDialog.add(inputWord1);
		gridset(c,1,0,1,1,12,1,1,0);
		gridbag.setConstraints(word1, c);
		bridgeWordsDialog.add(word1);
		gridset(c,0,1,1,1,6,1,0,0);
		gridbag.setConstraints(inputWord2, c);
		bridgeWordsDialog.add(inputWord2);
		gridset(c,1,1,1,1,12,1,1,0);
		gridbag.setConstraints(word2, c);
		bridgeWordsDialog.add(word2);
		gridset(c,0,2,1,1,6,1,0,0);
		gridbag.setConstraints(query, c);
		bridgeWordsDialog.add(query);
		gridset(c,0,3,2,1,18,1,1,1);
		gridbag.setConstraints(outputWords, c);
		bridgeWordsDialog.add(outputWords);
		
		bridgeWordsDialog.setModal(true);
		bridgeWordsDialog.setSize(500, 300);
		bridgeWordsDialog.setLocationRelativeTo(this);
		bridgeWordsDialog.setVisible(true);
	}
	
	
	JDialog newTextDialog;
	JLabel inputNewText, outputNewText;
	JButton formulateNewText;
	JTextField inNewText;
	GridBagLayout newTextLayout;
	GridBagConstraints newTextConstraints;
	private void formulateNewText(){
		newTextDialog = new JDialog(this, "Formulate new text");
		newTextLayout = new GridBagLayout();
		newTextConstraints = new GridBagConstraints();
		inputNewText = new JLabel("input the original text:");
		outputNewText = new JLabel();
		inNewText = new JTextField();
		formulateNewText = new JButton("formulate");
		
		formulateNewText.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String inputString = new String(inNewText.getText());
				inputString = inputString.toLowerCase();
				outputNewText.setText("The result is: "+GraphFunctions.generateNewText(graph, inputString));
			}
		});
		
		newTextDialog.setLayout(newTextLayout);
		gridset(newTextConstraints,0,0,1,1,6,1,0,0);
		newTextLayout.setConstraints(inputNewText, newTextConstraints);
		newTextDialog.add(inputNewText);
		gridset(newTextConstraints,1,0,2,1,12,1,1,0);
		newTextLayout.setConstraints(inNewText, newTextConstraints);
		newTextDialog.add(inNewText);
		gridset(newTextConstraints,0,1,1,1,6,1,0,0);
		newTextLayout.setConstraints(formulateNewText, newTextConstraints);
		newTextDialog.add(formulateNewText);
		gridset(newTextConstraints,0,2,3,1,18,1,1,0);
		newTextLayout.setConstraints(outputNewText, newTextConstraints);
		newTextDialog.add(outputNewText);
		
		newTextDialog.setModal(true);
		newTextDialog.setSize(500, 300);
		newTextDialog.setLocationRelativeTo(this);
		newTextDialog.setVisible(true);
	}
	
	JDialog pathDialog;
	GridBagLayout pathLayout;
	GridBagConstraints pathConstraints;
	JLabel pathInputWord1, pathInputWord2, pathOutput;
	JTextField pathWord1, pathWord2;
	JButton pathCalculate, pathAll;//pathNext, 
	int[] theway;
	int wayIndex;
	private void shortestPath(){
		pathDialog = new JDialog(this, "Calculate the shortest path");
		pathLayout = new GridBagLayout();
		pathConstraints = new GridBagConstraints();
		pathInputWord1 = new JLabel("input word 1:");
		pathInputWord2 = new JLabel("input word 2:");
		pathWord1 = new JTextField();
		pathWord2 = new JTextField();
		pathCalculate = new JButton("calculate");
		pathOutput = new JLabel("");
		//pathNext = new JButton("show next step");
		pathAll = new JButton("show all steps");
		
		pathDialog.setLayout(pathLayout);
		gridset(pathConstraints,0,0,1,1,6,1,0,0);
		pathLayout.setConstraints(pathInputWord1, pathConstraints);
		pathDialog.add(pathInputWord1);
		gridset(pathConstraints,1,0,2,1,12,1,1,0);
		pathLayout.setConstraints(pathWord1, pathConstraints);
		pathDialog.add(pathWord1);
		gridset(pathConstraints,0,1,1,1,6,1,0,0);
		pathLayout.setConstraints(pathInputWord2, pathConstraints);
		pathDialog.add(pathInputWord2);
		gridset(pathConstraints,1,1,2,1,12,1,1,0);
		pathLayout.setConstraints(pathWord2, pathConstraints);
		pathDialog.add(pathWord2);
		gridset(pathConstraints,0,2,1,1,6,1,0,0);
		pathLayout.setConstraints(pathCalculate, pathConstraints);
		pathDialog.add(pathCalculate);
		gridset(pathConstraints,0,3,3,1,18,1,1,1);
		pathLayout.setConstraints(pathOutput, pathConstraints);
		pathDialog.add(pathOutput);
		//gridset(pathConstraints,1,2,1,1,6,1,0,0);
		//pathLayout.setConstraints(pathNext, pathConstraints);
		//pathDialog.add(pathNext);
		gridset(pathConstraints,2,2,1,1,6,1,0,0);
		pathLayout.setConstraints(pathAll, pathConstraints);
		pathDialog.add(pathAll);
		
		pathCalculate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String word1 = new String(pathWord1.getText());
				String word2 = new String(pathWord2.getText());
				word1 = word1.toLowerCase();
				word2 = word2.toLowerCase();
				if(!graph.hashTable.containsKey(word1) && !graph.hashTable.containsKey(word2)) {
					pathOutput.setText("Neither "+word1+" nor "+word2+" exists.");
				}else if(!graph.hashTable.containsKey(word1)) {
					pathOutput.setText("The word "+word1+" does not exist.");
				}else if(!graph.hashTable.containsKey(word2)) {
					pathOutput.setText("The word "+word2+" does not exist.");
				}else{
					for (int i = 0; i < componentsToDraw.vertexes.length; i++)
						componentsToDraw.vertexes[i].setColor("black");
					for (int i = 0; i < componentsToDraw.edges.length; i++)
						componentsToDraw.edges[i].setColor("black");
					theway = GraphFunctions.Dijkstra(graph, word1, word2);
					pathOutput.setText("The shortest path between "+word1+" and "+word2+" is "+theway[theway.length-1]+".");
					wayIndex = 0;
					componentsToDraw.vertexes[theway[wayIndex++]].setColor("red");
					File thisDirectory = new File("");
					componentsToDraw.toDot("DotFileRunning.gv");
					Command.execute(thisDirectory.getAbsolutePath()+"\\dot "+thisDirectory.getAbsolutePath()+" -T gif -o "+thisDirectory.getAbsolutePath()+"\\GraphRunning.gif");
				}
			}
		});
		/*
		pathNext.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for (int i = 0; i < componentsToDraw.edges.length; i++)
					if (componentsToDraw.edges[i].src == componentsToDraw.vertexes[theway[wayIndex-1]].name && componentsToDraw.edges[i].dst == componentsToDraw.vertexes[theway[wayIndex]].name) {
						componentsToDraw.edges[i].setColor("red");
						break;
					}
				wayIndex++;
				File thisDirectory = new File("");
				componentsToDraw.toDot("DotFileRunning.gv");
				Command.execute(DotPath+" "+thisDirectory.getAbsolutePath()+" -T gif -o GraphRunning.gif");
			}
		});
		*/
		pathAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for (int i = 0; i < theway.length-1; i++)
					componentsToDraw.vertexes[theway[i]].setColor("red");
				while (wayIndex != theway.length-1){
					for (int i = 0; i < componentsToDraw.edges.length; i++)
						if (componentsToDraw.edges[i].src == componentsToDraw.vertexes[theway[wayIndex-1]].name && componentsToDraw.edges[i].dst == componentsToDraw.vertexes[theway[wayIndex]].name) {
							componentsToDraw.edges[i].setColor("red");
							break;
						}
					wayIndex++;
				}
				File oldRunningGraph = new File("GraphRunning.gif");
				if (oldRunningGraph.exists()) oldRunningGraph.delete();
				File thisDirectory = new File("");
				componentsToDraw.toDot("DotFileRunning.gv");
				Command.execute(thisDirectory.getAbsolutePath()+"\\dot "+thisDirectory.getAbsolutePath()+" -T gif -o "+thisDirectory.getAbsolutePath()+"\\GraphRunning.gif");
			}
		});
		
		pathDialog.setModal(true);
		pathDialog.setSize(500,300);
		pathDialog.setLocationRelativeTo(this);
		pathDialog.setVisible(true);
	}
	
	JDialog travelDialog;
	JButton travelStart, travelStop;//travelContinue,
	JLabel travel;
	GridBagLayout travelLayout;
	GridBagConstraints travelConstraints;
	int[] places;
	int placeIndex;
	private void randomTravel(){
		travelLayout = new GridBagLayout();
		travelConstraints = new GridBagConstraints();
		travelDialog = new JDialog(this, "Random travel");
		travelStart = new JButton("start");
		//travelContinue = new JButton("Continue");
		travelStop = new JButton("stop");
		travel = new JLabel("Welcome to random travel");
		
		travelDialog.setLayout(travelLayout);
		gridset(travelConstraints,0,0,3,1,18,1,3,0);
		travelLayout.setConstraints(travel, travelConstraints);
		travelDialog.add(travel);
		gridset(travelConstraints,0,1,1,1,6,1,1,0);
		travelLayout.setConstraints(travelStart, travelConstraints);
		travelDialog.add(travelStart);
		//gridset(travelConstraints,1,1,1,1,6,1,1,0);
		//travelLayout.setConstraints(travelContinue, travelConstraints);
		//travelDialog.add(travelContinue);
		gridset(travelConstraints,2,1,1,1,6,1,1,0);
		travelLayout.setConstraints(travelStop, travelConstraints);
		travelDialog.add(travelStop);
		
		//travelContinue.setEnabled(false);
		travelStop.setEnabled(false);
		travelStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for (int i = 0; i < componentsToDraw.vertexes.length; i++)
					componentsToDraw.vertexes[i].setColor("black");
				for (int i = 0; i < componentsToDraw.edges.length; i++)
					componentsToDraw.edges[i].setColor("black");
				places = GraphFunctions.randomRun(graph);
				placeIndex = 0;
				travel.setText("The start word of this travel is "+componentsToDraw.vertexes[places[placeIndex]].name);
				componentsToDraw.vertexes[places[placeIndex]].setColor("red");
				File thisDirectory = new File("");
				componentsToDraw.toDot("DotFileRunning.gv");
				Command.execute(thisDirectory.getAbsolutePath()+"\\dot "+thisDirectory.getAbsolutePath()+" -T gif -o "+thisDirectory.getAbsolutePath()+"\\GraphRunning.gif");
				//travelContinue.setEnabled(true);
				travelStop.setEnabled(true);
			}
		});
		/*
		travelContinue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (placeIndex == places.length){ 
					travel.setText("The travel has finished.");
					travelStop.setEnabled(false);
					travelContinue.setEnabled(false);
				}
				else {
					travel.setText("Travel to "+componentsToDraw.vertexes[places[placeIndex]].name);
					componentsToDraw.vertexes[places[placeIndex]].setColor("red");
					for (int i = 0; i < componentsToDraw.edges.length; i++)
						if (componentsToDraw.edges[i].src == componentsToDraw.vertexes[places[placeIndex-1]].name && componentsToDraw.edges[i].dst == componentsToDraw.vertexes[places[placeIndex]].name) {
							componentsToDraw.edges[i].setColor("red");
							break;
						}
					placeIndex++;
					File thisDirectory = new File("");
					componentsToDraw.toDot("DotFileRunning.gv");
					Command.execute(DotPath+" "+thisDirectory.getAbsolutePath()+" -T gif -o GraphRunning.gif");
				}
			}
		});
		*/
		travelStop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Travel words in GUI is:");
				for (int i = 0; i < places.length; i++){
					System.out.print(componentsToDraw.vertexes[places[i]].name+" ");
					componentsToDraw.vertexes[places[i]].setColor("red");
				}
				System.out.println();
				for (placeIndex = 1; placeIndex < places.length; placeIndex++)
					for (int i = 0; i < componentsToDraw.edges.length; i++)
						if (componentsToDraw.edges[i].src == componentsToDraw.vertexes[places[placeIndex-1]].name && componentsToDraw.edges[i].dst == componentsToDraw.vertexes[places[placeIndex]].name) {
							componentsToDraw.edges[i].setColor("red");
							System.out.println("The edge from "+componentsToDraw.edges[i].src+" to "+componentsToDraw.edges[i].dst+" is found.");
							break;
						}
				File oldRunningGraph = new File("GraphRunning.gif");
				if (oldRunningGraph.exists()) oldRunningGraph.delete();
				File thisDirectory = new File("");
				componentsToDraw.toDot("DotFileRunning.gv");
				Command.execute(thisDirectory.getAbsolutePath()+"\\dot "+thisDirectory.getAbsolutePath()+" -T gif -o "+thisDirectory.getAbsolutePath()+"\\GraphRunning.gif");
				travelStop.setEnabled(false);
				//travelContinue.setEnabled(false);
			}
		});
		
		travelDialog.setModal(true);
		travelDialog.setSize(500,300);
		travelDialog.setLocationRelativeTo(this);
		travelDialog.setVisible(true);
	}
}