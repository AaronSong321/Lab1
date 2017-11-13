package com.arron;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Random;

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
    public int mEdgNum;
    public VNode[] mVexs;
    public Hashtable<String,Integer> hashTable;
    public final int MAX_WORDS = 100;
    
    public void linkLast(int startv, ENode node){
    	if (mVexs[startv].firstEdge == null) mVexs[startv].firstEdge = node;
    	else{
    		ENode end = mVexs[startv].firstEdge;
    		while (end.nextEdge != null) end = end.nextEdge;
    		end.nextEdge = node;
    	}
    }
    
    public ListDG(String tmpstr) {
    	int numOfStrings = 0;
    	String[] tempStrings = new String[MAX_WORDS];
    	boolean oo=true;
    	while(oo) {
    		if(tmpstr == null) break;
    		//tmpstr = tmpstr.toLowerCase();
    		String[] s = tmpstr.split("[^a-zA-Z]+");
    		for (int i=0;i<s.length;i++) {
    			if(!s[i].equals(""))
    			   //System.out.println(tempStrings[numOfStrings++]=s[i]);
    			   tempStrings[numOfStrings++]=s[i];
    		}
    		oo=false;
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
		mEdgNum -= repeatEdge;   
		hashTable = new Hashtable<String, Integer>();
		for(int i=0;i<mVexs.length;i++)
			hashTable.put(mVexs[i].data, i);
    }
    
    public String queryBridgeWords(String src,String dst) {
		StringBuilder result = new StringBuilder();
		int countnum=0;
		if(!hashTable.containsKey(src) && !hashTable.containsKey(dst)) {
			return "Neither "+src+" nor "+dst+" exists.";
		}else if(!hashTable.containsKey(src)) {
			return "The word "+src+" does not exist.";
		}else if(!hashTable.containsKey(dst)) {
			return "The word "+dst+" does not exist.";
		}else {
			ENode u = mVexs[hashTable.get(src)].firstEdge;
			while(u != null){
				ENode v = mVexs[u.ivex].firstEdge;
				while(v != null) {
					if(mVexs[v.ivex].data.equals(dst)) {
						if(countnum==0) {
							result.append(" ");
							result.append(mVexs[u.ivex].data);
						}else {
							result.append(", ");
							result.append(mVexs[u.ivex].data);
						}
						countnum=countnum+1;
					}
					v = v.nextEdge;
				}
				u = u.nextEdge;
			}
			if(countnum==0) {
				return("There are no bridge between "+src+" and "+dst+".");
			}else if(countnum==1) {
				return("The bridge word between "+src+" and "+dst+" is"+result.toString()+".");
			}else {
				return("The bridge word between "+src+" and "+dst+" are"+result.toString()+".");				
			}
		}
	}

    public int[] Dijkstra(String src,String dst) {
		if(!hashTable.containsKey(src) && !hashTable.containsKey(dst)) {
			int[] result1=new int[101];
			return result1;
		}else if(!hashTable.containsKey(src)) {
			int[] result1=new int[102];
			return result1;
		}else if(!hashTable.containsKey(dst)) {
		    int[] result1=new int[103];
			return result1;
		}else if(mEdgNum==0) {
			int[] result1=new int[104];
			return result1;
		}
		else {
			int[][] graph=new int[mVexs.length][mVexs.length];
			int[][] store=new int[mVexs.length][mVexs.length];
			int size=mVexs.length;
			for(int i=0;i<mVexs.length;i++) {
				for(int j=0;j<mVexs.length;j++) {
					graph[i][j]=1000;
					store[i][j]=0;
				}
			}
			for(int i=0;i<size;i++) {
				ENode tmp=mVexs[i].firstEdge;
				while(tmp!=null) {
					graph[i][tmp.ivex]=tmp.weight;
					tmp=tmp.nextEdge;
				}
			}
			
			int srcnum=hashTable.get(src);
			int dstnum=hashTable.get(dst);
			
			int[] num=new int[mVexs.length];
			int[] shortest=new int[mVexs.length];
			int shortnum=0;
			
			boolean[] finish=new boolean[mVexs.length];
			int[] D=new int[mVexs.length];		

			for(int i=0;i<mVexs.length;i++) {
				D[i]=graph[srcnum][i];
				finish[i]=false;
				store[i][num[i]++]=srcnum;
				num[i]=0;
			}
			finish[srcnum]=true;
			D[srcnum]=-1;
			int v=0;
			int min=0;
			for(int i=1;i<mVexs.length;i++) {
				min=10000;
				for(int j=0;j<size;j++) {
					if(!finish[j] && D[j] < min && D[j] != -1) {
						v=j;
						min=D[j];
					}
				}
				finish[v]=true;
				if(v==dstnum) {
					store[v][num[v]++]=v;
					shortest=store[v];
					shortnum=num[v];
//					queue[v].insert(v);
			//		shortest[0]=queue[v];
					break;
				}
				for(int w=0;w<size;w++) {
					if(!finish[w]&&graph[v][w]!=-1) {
						if((graph[v][w]+min)<D[w] || D[w]==-1) {
							D[w]=graph[v][w]+min;
							store[w]=store[v];
//							queue[w]=queue[v];
							store[w][num[w]++]=v;
						}
					}
				}
				store[v][num[v]++]=v;
			}
			/*
			System.out.println("\n");
			System.out.println(hashTable.get(dst));
			System.out.println(graph[hashTable.get(src)][hashTable.get(dst)]);
			*/
			int count=1;
			int[] result=new int[shortnum+2];
			result[0]=srcnum;
			for(int i=0;i<shortnum;i++) {
				result[count]=shortest[i];
				count=count+1;
			//	System.out.print(shortest[i]+" ");
			}
			result[result.length-1] = D[dstnum];
			//System.out.println(result.length);
			if(shortnum==1) {
				boolean judge=false;
				ENode tmpjudg=mVexs[hashTable.get(src)].firstEdge;	
				while(tmpjudg!=null) {
					if (tmpjudg.ivex==hashTable.get(dst)){
						judge=true;
					}
					tmpjudg=tmpjudg.nextEdge;
					//System.out.println(tmpjudg.nextEdge.ivex);
				}
				if(judge==false) {
					int[] result4=new int [105];
					System.out.println(result4.length);
					return result4;
				}else if(judge==true){
					//System.out.println("1111111");
				}
				}
			    System.out.println(result.length);
				return result;				
			//return result;
		}   
		}
    	
}
