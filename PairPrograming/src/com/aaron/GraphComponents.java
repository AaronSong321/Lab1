package com.aaron;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/*第三次修改*/
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

}
