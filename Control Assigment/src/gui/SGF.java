package gui;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JApplet;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import Container.Node;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jgraph.JGraph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

import Container.Node;

public class SGF extends JFrame {

	private JPanel contentPane;
	private static final long serialVersionUID = 1L;
	private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

	private JGraphModelAdapter m_jgAdapter;

	/**
	 * Create the frame.
	 */
	public SGF(ArrayList<Node> sgf[]) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		
		// create a JGraphT graph
				ListenableGraph g = new ListenableDirectedGraph(DefaultEdge.class);

				// create a visualization using JGraph, via an adapter
				m_jgAdapter = new JGraphModelAdapter(g);

				JGraph jgraph = new JGraph(m_jgAdapter);


				getContentPane().add(jgraph);
				contentPane.setSize(800, 800);
				int x=10;
				int y=10;
				boolean first=false;
				
				for (int i = 0; i < sgf.length; i++) {
					if (sgf[i].size() >0){
						if (!first){
							g.addVertex(i);
							positionVertexAt(i, x, y);
							x+=60;
						}
						
						for (int j = 0; j < sgf[i].size(); j++) {
							g.addVertex(sgf[i].get(j).getTo());
							if (x>=800){
								y+=60;
								x=10;
							}
							positionVertexAt(i, x, y);
							x+=60;
						}
					}
				}
				
				
				
				for (int i = 0; i < sgf.length; i++) {
					
					for (int j = 0; j < sgf[i].size(); j++) {
						int from=sgf[i].get(j).getFrom();
						int to =sgf[i].get(j).getTo();
						System.out.println(from +" "+to);
						g.addEdge(from, to);
					}
				}
				
			

		
	}



	private void positionVertexAt(Object vertex, int x, int y) {
		DefaultGraphCell cell = m_jgAdapter.getVertexCell(vertex);
		Map attr = cell.getAttributes();
		Rectangle2D b = GraphConstants.getBounds(attr);

		GraphConstants.setBounds(attr, new Rectangle(x, y, (int) b.getWidth(),
				(int) b.getHeight()));

		Map cellAttr = new HashMap();
		cellAttr.put(cell, attr);
		m_jgAdapter.edit(cellAttr, null, null, null);
	}


}
