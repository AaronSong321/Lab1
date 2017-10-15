package com.aaron;

import java.util.Calendar;
import java.util.Random;
import com.aaron.ListDG.ENode;

public class GraphFunctions {
	public static String queryBridgeWords(ListDG G,String src,String dst) {
		StringBuilder result = new StringBuilder();
		int countnum=0;
		if(!G.hashTable.containsKey(src) && !G.hashTable.containsKey(dst)) {
			return "Neither "+src+" nor "+dst+" exists.";
		}else if(!G.hashTable.containsKey(src)) {
			return "The word "+src+" does not exist.";
		}else if(!G.hashTable.containsKey(dst)) {
			return "The word "+dst+" does not exist.";
		}else {
			ENode u = G.mVexs[G.hashTable.get(src)].firstEdge;
			while(u != null){
				ENode v = G.mVexs[u.ivex].firstEdge;
				while(v != null) {
					if(G.mVexs[v.ivex].data.equals(dst)) {
						if(countnum==0) {
							result.append(" ");
							result.append(G.mVexs[u.ivex].data);
						}else {
							result.append(", ");
							result.append(G.mVexs[u.ivex].data);
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

	public static void newTextTest(ListDG g) {
	//	String s = "but the wine and the sun like the seasons have all gone";
		//System.out.println(generateNewText(s,g));
		String s="we seasons the we joy";
		System.out.println(generateNewText(g, s));
	}

	public static String generateNewText(ListDG G, String passin) {
		if(r==null) {
			randomStart();
		}
		StringBuilder recreate=new StringBuilder();
		String[] divided=passin.split("\\,| |\\-");
		for(int i=0;i<divided.length-1;i++) {
			if(!(G.hashTable.containsKey(divided[i]) && G.hashTable.containsKey(divided[i+1]))) {
				if (i==0) {
					recreate.append(divided[i]);
					recreate.append(" ");
					recreate.append(divided[i+1]);
				}else {
					recreate.append(" ");
					recreate.append(divided[i+1]);
				}
			}else {
				StringBuilder words=new StringBuilder();
				int numcount=0;
				ENode u = G.mVexs[G.hashTable.get(divided[i])].firstEdge;
				while(u != null){
					ENode v = G.mVexs[u.ivex].firstEdge;
					while(v != null) {
						if(G.mVexs[v.ivex].data.equals(divided[i+1])) {
							words.append(G.mVexs[u.ivex].data);
							words.append(" ");
							numcount=numcount+1;
						}
						v = v.nextEdge;
					}
					u = u.nextEdge;
				}
				String[] builder=words.toString().split(" ");
				if (i==0) {
					if(numcount>0){
						if(numcount==1) {
							recreate.append(divided[i]);
							recreate.append(" ");
							recreate.append(builder[0]);
						}else {
							recreate.append(divided[i]);
							recreate.append(" ");
							recreate.append(builder[numcount-1]);
						}
					}else {
						recreate.append(divided[i]);
					}
					recreate.append(" ");
					recreate.append(divided[i+1]);
				}else {
					if(numcount>0){
						if(numcount==1) {
							recreate.append(" ");
							recreate.append(builder[0]);
						}else {
							recreate.append(" ");
							recreate.append(builder[generateNextRandom(numcount)]);
						}
					}
					recreate.append(" ");
					recreate.append(divided[i+1]);
				}
			}
		}
//		System.out.println(recreate.toString());
		return recreate.toString();
	}

//	 static boolean endsign=false;
		static Random r;
		static int nowplace;
		private static void randomStart(){
			Calendar c = Calendar.getInstance();
			r=new Random(c.getTimeInMillis());
		}
		private static int generateNextRandom(int x) {
			return r.nextInt(x);
		}
		public static void randombegin(ListDG G) {
			if(r==null) {
				randomStart();
			}
			nowplace=r.nextInt(G.mVexs.length);
//			System.out.println(nowplace);
		}
		public static int[] randomRun(ListDG G) {
			boolean endsign=false;
			int[] nodechild=new int[G.mVexs.length];
			int childnum=0;
			int tmpplace=0;

			int[] result=new int[G.length()];
			randombegin(G);
			result[0]=nowplace;
			tmpplace=tmpplace+1;

			boolean[][]visited=new boolean[G.mVexs.length][G.mVexs.length];
			ENode tmpvisit=G.mVexs[nowplace].firstEdge;

			for(int i=0;i<G.mVexs.length;i++) {
				for(int j=0;j<G.mVexs.length;j++) {
					visited[i][j]=false;
				}
			}

			while(endsign==false) {
				if(tmpvisit==null) {
					endsign=true;
				}else {
					childnum=0;
					while(tmpvisit!=null) {
						nodechild[childnum++]=tmpvisit.ivex;
						tmpvisit=tmpvisit.nextEdge;
					}
					int nextplace=nodechild[generateNextRandom(childnum)];
					if(visited[nowplace][nextplace]==false) {
						result[tmpplace]=nextplace;
						tmpplace=tmpplace+1;
						visited[nowplace][nextplace]=true;
						nowplace=nextplace;
						tmpvisit=G.mVexs[nowplace].firstEdge;
					}else {
						endsign=true;
					}
				}
			}

			int[] res=new int[tmpplace];
			System.out.println("Travel words in functions is:");
			for(int i=0;i<tmpplace;i++) {
				res[i]=result[i];
				System.out.print(result[i]+" ");
			}
			System.out.println();
			return res;
		}

	public static int[] Dijkstra(ListDG G,String src,String dst) {
		int[][] graph=new int[G.mVexs.length][G.mVexs.length];
		int[][] store=new int[G.mVexs.length][G.mVexs.length];
		int size=G.mVexs.length;
		for(int i=0;i<G.mVexs.length;i++) {
			for(int j=0;j<G.mVexs.length;j++) {
				graph[i][j]=1000;
				store[i][j]=0;
			}
		}
		for(int i=0;i<size;i++) {
			ENode tmp=G.mVexs[i].firstEdge;
			while(tmp!=null) {
				graph[i][tmp.ivex]=tmp.weight;
				tmp=tmp.nextEdge;
			}
		}

		int srcnum=G.hashTable.get(src);
		int dstnum=G.hashTable.get(dst);

		int[] num=new int[G.mVexs.length];
		int[] shortest=new int[G.mVexs.length];
		int shortnum=0;

		boolean[] finish=new boolean[G.mVexs.length];
		int[] D=new int[G.mVexs.length];

		for(int i=0;i<G.mVexs.length;i++) {
			D[i]=graph[srcnum][i];
			finish[i]=false;
			store[i][num[i]++]=srcnum;
			num[i]=0;
		}
		finish[srcnum]=true;
		D[srcnum]=-1;
		int v=0;
		int min=0;
		for(int i=1;i<G.mVexs.length;i++) {
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
//				queue[v].insert(v);
		//		shortest[0]=queue[v];
				break;
			}
			for(int w=0;w<size;w++) {
				if(!finish[w]&&graph[v][w]!=-1) {
					if((graph[v][w]+min)<D[w] || D[w]==-1) {
						D[w]=graph[v][w]+min;
						store[w]=store[v];
//						queue[w]=queue[v];
						store[w][num[w]++]=v;
//						queue[w].insert(v);
					}
				}
			}
			store[v][num[v]++]=v;
//			queue[v].insert(v);
		}

		int count=1;
		int[] result=new int[shortnum+2];
		result[0]=srcnum;
		//System.out.println(srcnum);
//		shortnum=shortnum-1;
		for(int i=0;i<shortnum;i++) {
			result[count]=shortest[i];
			count=count+1;
		//	System.out.print(shortest[i]+" ");
		}
		//System.out.print("\n");
		result[result.length-1] = D[dstnum];
		return result;
	}

}


