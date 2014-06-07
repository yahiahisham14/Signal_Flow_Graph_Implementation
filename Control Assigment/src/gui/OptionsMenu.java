package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

import algorithms.Utils;

import Container.Node;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OptionsMenu extends JFrame {

	private JPanel contentPane;
	private static ArrayList<String> forPaths;
	private static ArrayList<String> loops;
	private static ArrayList<String> untouchdLoops;
	public  static ArrayList<Node> [] adjList;
	private static double transferFn;

	//The constructor will take three array Lists and transfer Function result.
	public OptionsMenu(ArrayList<Node> [] adList ,ArrayList<String> forP,ArrayList<String> loop,ArrayList<String> unlops,double tF) {
		forPaths=forP;
		loops=loop;
		untouchdLoops=unlops;
		transferFn=tF;
		adjList=adList;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 606, 464);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(392, 151, 72, -76);
		contentPane.add(scrollPane);
		
		final JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.BOLD, 16));
		textArea.setBounds(38, 185, 519, 219);
		contentPane.add(textArea);
		
		JButton btnForwardPaths = new JButton("Forward Paths");
		btnForwardPaths.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s="";
				HashSet<String> map=new HashSet<String>();
				for (int i = 0; i < forPaths.size(); i++) {
					
					if (!map.contains(forPaths.get(i))){
						s += forPaths.get(i) +"\n";
					}
					
					map.add(forPaths.get(i));
				}
				
				if (s.equals("")){
					s="There're No Forward Paths !";
				}
					textArea.setText(s);
				
			}
		});
		btnForwardPaths.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnForwardPaths.setBounds(40, 29, 127, 46);
		contentPane.add(btnForwardPaths);
		
		JButton btnLoops = new JButton("Loops");
		btnLoops.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s="";
				HashSet<String> map=new HashSet<String>();
				for (int i = 0; i <loops.size(); i++) {
					
					if (!map.contains(loops.get(i))){
						s += loops.get(i) +"\n";
					}
					
					map.add(loops.get(i));
				}
				if (s.equals("")){
					s="There're No Loops !";
				}
				
				textArea.setText(s);
			}
		});
		btnLoops.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnLoops.setBounds(38, 95, 129, 46);
		contentPane.add(btnLoops);
		
		JButton btnUntouchedLoops = new JButton("UnTouched Loops");
		btnUntouchedLoops.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s="";
				for (int i = 0; i < untouchdLoops.size(); i++) {
					s += untouchdLoops.get(i) +"\n";
				}
				if (s.equals("")){
					s="There're No untouched Loops !";
				}
				
				textArea.setText(s);
			}
		});
		btnUntouchedLoops.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnUntouchedLoops.setBounds(222, 29, 127, 46);
		contentPane.add(btnUntouchedLoops);
		
		JButton btnTransferFunction = new JButton("Transfer Function");
		btnTransferFunction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("Transfer Function equals ="+transferFn+"");
			}
			
		});
		btnTransferFunction.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnTransferFunction.setBounds(222, 95, 127, 46);
		contentPane.add(btnTransferFunction);
		
		JButton btnSignalFlowGraph = new JButton("Signal Flow Graph");
		btnSignalFlowGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							SGF frame = new SGF(adjList);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
				
			}
		});
		btnSignalFlowGraph.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnSignalFlowGraph.setBounds(402, 30, 127, 46);
		contentPane.add(btnSignalFlowGraph);
		
		JButton btnDeltas = new JButton("Deltas");
		btnDeltas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Double> deltaKsa=Utils.deltaKs;
				String s="";
				for (int i = 0; i < deltaKsa.size(); i++) {
					s+=(deltaKsa.get(i))+"/n";
				}
				textArea.setText(s);
			}
		});
		btnDeltas.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnDeltas.setBounds(402, 92, 127, 46);
		contentPane.add(btnDeltas);
	}
}
