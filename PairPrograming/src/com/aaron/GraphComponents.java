package com.aaron;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GraphComponents {
	public VNode[] vertexes;
	public ENode[] edges;
	public class VNode{
		String name, color, shape;
		double width, height;
		public VNode(String name){
			this.name = name; shape = "ellipse"; width = 0.75; height = 0.5; color = "black";
		}
		public String toString(){
			return name+"[shape = "+shape+", width = "+width+", height = "+height+", color = "+color+"];\n";
		}
		public void setColor(String color){
			this.color = color;
		}
	}
	public class ENode{
		String src, dst, color;
		int label;
		public ENode(String src, String dst, int weight){
			this.src = src; this.dst = dst; this.color = "black"; this.label = weight;
		}
		public String toString(){
			return src+" -> "+dst+" [label = "+label+", color = "+color+"];\n";
		}
		public void setColor(String color){
			this.color = color;
		}
	}
	public GraphComponents(ListDG g){
		vertexes = new VNode[g.length()];
		edges = new ENode[g.edgeNum()];
		for (int i = 0; i < g.mVexs.length; i++) vertexes[i] = new VNode(g.mVexs[i].data);
		int edgeNumber = 0;
		for (int i = 0; i < g.mVexs.length; i++){
			//System.out.println(vertexes[i].toString());
			ListDG.ENode temp = g.mVexs[i].firstEdge;
			while (temp != null){
				edges[edgeNumber++] = new ENode(g.mVexs[i].data, g.mVexs[temp.ivex].data, temp.weight);
				temp = temp.nextEdge;
			}
		}
	}
	
	public void toDot(String s){
		File dotFile;
		FileWriter dotFileWriter;
		BufferedWriter dotBufferedWriter;
		try{
			dotFile = new File(s);
			if (!dotFile.exists()) dotFile.createNewFile();
			dotFileWriter = new FileWriter(dotFile, false);
			dotBufferedWriter = new BufferedWriter(dotFileWriter);
			
			dotBufferedWriter.write("digraph G {\n");
			dotBufferedWriter.write("size = \"4,4\";\n");
			dotBufferedWriter.write("rankdir = LR;\n");
			for (int i = 0; i < vertexes.length; i++)
				dotBufferedWriter.write(vertexes[i].toString());
			for (int i = 0; i < edges.length; i++)
				dotBufferedWriter.write(edges[i].toString());
			dotBufferedWriter.write("}");
			dotBufferedWriter.flush();
			dotBufferedWriter.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	/*
	public void show(String runPath,String dotPath,String nameGraph){
		GraphViz gv = new GraphViz(runPath, dotPath, nameGraph);
		gv.start_graph();
		for (int i = 0; i < vertexes.length; i++)
			gv.add(vertexes[i].toString());
		for (int i = 0; i < edges.length; i++)
			gv.add(edges[i].toString());
		gv.end_graph();
		try{
			gv.run();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public enum Direction{
		northeast, sorthwest;
	}
	public class VertexComponent{
		int positionx, positiony;
		int coordinatex, coordinatey;
		int index;
		String name;
		Color color;
		int degreein, degreeout, degree;
		VertexComponent(int index, String name){
			this.name = name;
			this.index = index;
			color = Color.black;
		}
	}
	public class EdgeComponent{
		int a, b;
		Direction directionOfCircleCenter;
		double centralAngle;
		Color color;
		EdgeComponent(int x, int y){
			a = x; b = y;
		}
	}
	
	Hashtable<String, Integer> hs;
	VertexComponent[] vc;
	EdgeComponent[] ec;
	
	GraphComponents(ListDG g){
		//generate all vertex components and edge components
		vc = new VertexComponent[g.length()];
		ec = new EdgeComponent[g.edgeNum()];
		hs = new Hashtable<String, Integer>();
		for (int i = 0; i < g.length(); i++) {
			hs.put(g.mVexs[i].data, new Integer(i));
			vc[i] = new VertexComponent(i, g.mVexs[i].data);
		}
		int edgeNum = 0;
		for (int i = 0; i < vc.length; i++){
			ENode temp = g.mVexs[i].firstEdge;
			while (temp != null){
				ec[edgeNum++] = new EdgeComponent(i, temp.ivex);
				vc[i].degreeout++;
				vc[i].degree++;
				vc[temp.ivex].degreein++;
				vc[temp.ivex].degree++;
				temp = temp.nextEdge;
			}
		}
		
		//formulate the positions of all vertexes
		Queue<VertexComponent> q = new LinkedBlockingQueue<VertexComponent>();
		if (vc.length != 0) q.add(vc[0]);
		int minx = 0, maxx = 0, maxy = 0;
		vc[0].positionx = 0;
		vc[0].positiony = 0;
		final class Coordinate{
			public int x, y;
			Coordinate(int x, int y){
				this.x = x;
				this.y = y;
			}
			Coordinate(VertexComponent v){
				this.x = v.positionx;
				this.y = v.positiony;
			}
		}
		Hashtable<Coordinate, Integer> full = new Hashtable<Coordinate, Integer>();
		boolean[] placed = new boolean[vc.length];
		for (int i = 0; i < vc.length; i++) placed[i] = false;
		full.put(new Coordinate(1, 0), 0);
		placed[0] = true;
		
		while (q.peek() != null){
			VertexComponent temp = q.poll();
			int needToPlace = 0;
			ENode tempEdgeForCounting = g.mVexs[temp.index].firstEdge;
			while (tempEdgeForCounting != null){
				if (placed[tempEdgeForCounting.ivex] == false)
					needToPlace++;
				tempEdgeForCounting = tempEdgeForCounting.nextEdge;
			}
			if (needToPlace == 1){
				int v1 = -1;
				ENode tempEdge = g.mVexs[temp.index].firstEdge;
				while (tempEdge != null){
					if (placed[tempEdge.ivex] == false){
						v1 = tempEdge.ivex;
						break;
					}
					tempEdge = tempEdge.nextEdge;
				}
				vc[v1].positionx = temp.positionx;
				vc[v1].positiony = temp.positiony+1;
				full.put(new Coordinate(vc[v1]), v1);
				maxx = maxx > temp.positionx ? maxx : temp.positionx;
				maxy = maxy > temp.positiony+1 ? maxy : temp.positiony+1;
				q.add(vc[v1]);
				placed[v1] = true;
			}
			else if (needToPlace == 2){
				ENode tempEdge = g.mVexs[temp.index].firstEdge;
				int v1 = -1, v2 = -1;
				while (tempEdge != null){
					if (placed[tempEdge.ivex] == false){
						if (v1 == -1){
							v1 = tempEdge.ivex;
							placed[v1] = true;
							continue;
						}
						else if (v2 == -1){
							v2 = tempEdge.ivex;
							placed[v2] = true;
							break;
						}
					}
					tempEdge = tempEdge.nextEdge;
				}
				vc[v1].positionx = temp.positionx - 1;
				vc[v1].positiony = temp.positiony + 1;
				vc[v2].positionx = temp.positionx + 1;
				vc[v2].positiony = temp.positiony + 1;
				full.put(new Coordinate(vc[v1]), v1);
				full.put(new Coordinate(vc[v2]), v2);
				maxx = maxx > temp.positionx + 1 ? maxx : temp.positionx + 1;
				maxy = maxy > temp.positiony + 1 ? maxy : temp.positiony + 1;
				minx = minx < temp.positionx - 1 ? minx : temp.positionx - 1;
				q.add(vc[v1]);
				q.add(vc[v2]);
			}
			else {
				ENode tempEdge = g.mVexs[temp.index].firstEdge;
				int previousx = temp.positionx - 1;
				while (tempEdge != null){
					if (placed[tempEdge.ivex] == false){
						vc[tempEdge.ivex].positiony = temp.positiony + 1;
						vc[tempEdge.ivex].positionx = previousx++;
						full.put(new Coordinate(vc[tempEdge.ivex]), tempEdge.ivex);
						placed[tempEdge.ivex] = true;
						q.add(vc[tempEdge.ivex]);
					}
					tempEdge = tempEdge.nextEdge;
				}
				previousx--;
				maxx = maxx > previousx ? maxx : previousx;
				maxy = maxy > temp.positiony + 1 ? maxy : temp.positiony + 1;
				minx = minx < temp.positionx - 1 ? minx : temp.positionx - 1;
			}
		}
		
		if (minx != 1){
			maxx += 1-minx;
			for (int i = 0; i < vc.length; i++)
				vc[i].positionx += 1-minx;
		}
		for (int i = 0; i < vc.length; i++)
			System.out.println(i+vc[i].name+" x="+vc[i].positionx+" y="+vc[i].positiony);
		
	}
	*/
}
