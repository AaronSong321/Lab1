package com.aaron;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;


public class ListDG {
    public class ENode {
        int ivex;
        int weight;
        ENode nextEdge;
    }
    public class VNode {
        String data;
        ENode firstEdge;
    };
    private int mEdgNum;
    public VNode[] mVexs;
    public Hashtable<String,Integer> hashTable;
    private final int MAX_WORDS = 100;
<<<<<<< HEAD
    
=======

>>>>>>> a053be062e65bb40b1ff71864afa4aff06bc2b35
    public ListDG(File file) throws FileNotFoundException{
    	BufferedReader reader = null;
    	try{
    		reader = new BufferedReader(new FileReader(file));
    		String tempString = null;
    		int numOfStrings = 0;
    		String[] tempStrings = new String[MAX_WORDS];
    		while (true){
    			tempString = reader.readLine();
    			if (tempString == null) break;
    			tempString = tempString.toLowerCase();
    			String[] s = tempString.split("[^a-zA-Z]+");
    			for (int i = 0; i < s.length; i++){
    				if (!s[i].equals(""))
    				tempStrings[numOfStrings++] = s[i];
    			}
    		}
    		int repeatTimes = 0;
    		int[] tokens = new int[numOfStrings];
    		for (int i = 0; i < numOfStrings; i++){
    			tokens[i] = -1;
    			for (int j = 0; j < i; j++) {
    				if (tempStrings[i].equals(tempStrings[j])){
    					tokens[i]=tokens[j];
    					repeatTimes++;
    					break;
    				}
    			}
    			if (tokens[i] == -1) tokens[i] = i-repeatTimes;
    		}
    		int numOfVexs = 0;
    		for (int i = 0; i < numOfStrings; i++){
    			if (numOfVexs < tokens[i])
    				numOfVexs = tokens[i];
    		}
    		numOfVexs++;
    		mVexs = new VNode[numOfVexs];
    		boolean[] notCreated = new boolean[numOfVexs];
    		for (int i = 0; i < numOfVexs; i++) notCreated[i] = true;
    		for (int i = 0; i < numOfStrings; i++){
    			if (notCreated[tokens[i]]){
    				mVexs[tokens[i]] = new VNode();
    				mVexs[tokens[i]].data = tempStrings[i];
    				mVexs[tokens[i]].firstEdge = null;
    			}
    		}
    		mEdgNum = numOfStrings-1;
    		int repeatEdge = 0;
    		for (int i = 0; i < mEdgNum; i++){
    			ENode tempEdge = mVexs[tokens[i]].firstEdge;
    			boolean hasEdge = false;
    			while (tempEdge != null){
    				if (tempEdge.ivex == tokens[i+1]){
    					tempEdge.weight++;
    					hasEdge = true;
    					repeatEdge++;
    					break;
    				}
    				tempEdge = tempEdge.nextEdge;
    			}
    			if (hasEdge == false){
	    			ENode newNode = new ENode();
	    			newNode.ivex = tokens[i+1];
	    			newNode.weight = 1;
	    			linkLast(tokens[i], newNode);
    			}
    		}
<<<<<<< HEAD
    		mEdgNum -= repeatEdge;   
=======
    		mEdgNum -= repeatEdge;
>>>>>>> a053be062e65bb40b1ff71864afa4aff06bc2b35
    		hashTable = new Hashtable<String, Integer>();
    		for(int i=0;i<mVexs.length;i++)
    			hashTable.put(mVexs[i].data, i);

    		/*
    		for (int i = 0; i < mVexs.length; i++){
    			ENode tempEdge = mVexs[tokens[i]].firstEdge;
    			System.out.print(mVexs[tokens[i]].data);
    			while (tempEdge != null){
    				System.out.print(" " + tempEdge.weight + " " + mVexs[tempEdge.ivex].data);
    				tempEdge = tempEdge.nextEdge;
    			}
    			System.out.println();
<<<<<<< HEAD
    		}	
=======
    		}
>>>>>>> a053be062e65bb40b1ff71864afa4aff06bc2b35
    		*/
    	} catch(IOException e){
    		e.printStackTrace();
    	} finally{
    		if (reader != null){
    			try{
    				reader.close();
    			} catch(IOException e1){
    				e1.printStackTrace();
    			}
    		}
    	}
    }
<<<<<<< HEAD
    
=======

>>>>>>> a053be062e65bb40b1ff71864afa4aff06bc2b35
    private void linkLast(int startv, ENode node){
    	if (mVexs[startv].firstEdge == null) mVexs[startv].firstEdge = node;
    	else{
    		ENode end = mVexs[startv].firstEdge;
    		while (end.nextEdge != null) end = end.nextEdge;
    		end.nextEdge = node;
    	}
    }
<<<<<<< HEAD
/*	
    public void dijkstra(int vs, int[] prev, int[] dist) {
    	FibHeap H = new FibHeap();
        boolean[] flag = new boolean[mVexs.length];
        for (int i = 0; i < mVexs.length; i++) {
            flag[i] = false;
            prev[i] = 0;
            dist[i] = INF;
        }
        dist[vs] = 0;
        H.insert(vs, 0);
        while(H.minimum_key() != -1) {
        	int u = H.minimum_name();
        	H.removeMin();
            if(flag[u])	continue;
            flag[u] = true;
            ENode p = mVexs[u].firstEdge;
            
            while(p != null){
            	int v = p.ivex;
            	if(flag[v]==false&&dist[v]>dist[u]+p.weight){
            		dist[v] = dist[u]+p.weight;
            		prev[v] = u;
            		H.insert(v, dist[v]);
            	}
            	p = p.nextEdge;
            }
        }
    }
*/
	public int length() {
		return mVexs.length;
	}
	
	public int edgeNum(){
		return mEdgNum;
	}
	
=======

	public int length() {
		return mVexs.length;
	}

	public int edgeNum(){
		return mEdgNum;
	}

>>>>>>> a053be062e65bb40b1ff71864afa4aff06bc2b35
	public void toDot(){
		File dotFile;
		FileWriter dotFileWriter;
		BufferedWriter dotBufferedWriter;
		try{
			dotFile = new File("DotFile.gv");
			if (!dotFile.exists()) dotFile.createNewFile();
			dotFileWriter = new FileWriter(dotFile, false);
			dotBufferedWriter = new BufferedWriter(dotFileWriter);
<<<<<<< HEAD
			
=======

>>>>>>> a053be062e65bb40b1ff71864afa4aff06bc2b35
			dotBufferedWriter.write("digraph G {\n");
			dotBufferedWriter.write("size = \"4,4\";\n");
			dotBufferedWriter.write("rankdir = LR;\n");
			for (int i = 0; i < mVexs.length; i++)
				dotBufferedWriter.write(mVexs[i].data+" [shape = ellipse, width = 1.5, height = 1.0, color = black];\n");
			for (int i = 0; i < mVexs.length; i++){
				ENode temp = mVexs[i].firstEdge;
				while (temp != null){
					dotBufferedWriter.write(mVexs[i].data+" -> "+mVexs[temp.ivex].data+"[color = black, label = \""+
							temp.weight+"\"];\n");
					temp = temp.nextEdge;
				}
			}
			dotBufferedWriter.write("}");
			dotBufferedWriter.flush();
			dotBufferedWriter.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
<<<<<<< HEAD
}
=======
}

>>>>>>> a053be062e65bb40b1ff71864afa4aff06bc2b35
