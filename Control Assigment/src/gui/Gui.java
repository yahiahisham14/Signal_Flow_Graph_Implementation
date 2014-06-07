package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;

import algorithms.Utils;

import Container.Node;

public class Gui extends JFrame {

	// Variable From and To:To send them to the algorithms class to do
	// operations//
	private int from = 0;
	private int to = 0;
	private int weight = 0;
	private int src = 0;
	private int des = 0;
	private static ArrayList<Node> nodes;

	// for handling destination//
	private static int max = -1;
	private static int min = 100000000;
	private JPanel contentPane;
	private JButton btnExit;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Gui() {
		nodes = new ArrayList<Node>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 607, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// ADD Node Button : All operations are done here.//
		JButton btnAddNode = new JButton("ADD NODE");
		btnAddNode.setBounds(217, 145, 114, 44);
		contentPane.add(btnAddNode);

		JButton btnNewButton = new JButton("Next");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBounds(445, 307, 97, 25);
		contentPane.add(btnNewButton);

		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contentPane.setVisible(false);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnExit.setBounds(86, 307, 97, 25);
		contentPane.add(btnExit);

		final JSpinner spinner_From = new JSpinner();
		spinner_From.setModel(new SpinnerNumberModel(new Integer(0),
				new Integer(0), null, new Integer(1)));
		spinner_From.setFont(new Font("Tahoma", Font.BOLD, 20));
		spinner_From.setBounds(49, 77, 97, 44);
		contentPane.add(spinner_From);

		JLabel lblFrom = new JLabel("From");
		lblFrom.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFrom.setBounds(71, 48, 56, 16);
		contentPane.add(lblFrom);

		final JSpinner spinner_To = new JSpinner();
		spinner_To.setModel(new SpinnerNumberModel(new Integer(0), new Integer(
				0), null, new Integer(1)));
		spinner_To.setFont(new Font("Tahoma", Font.BOLD, 20));
		spinner_To.setBounds(234, 77, 97, 44);
		contentPane.add(spinner_To);

		JLabel lblTo = new JLabel("To");
		lblTo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTo.setBounds(275, 48, 56, 16);
		contentPane.add(lblTo);

		final JSpinner spinner_Weight = new JSpinner();
		spinner_Weight.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		spinner_Weight.setFont(new Font("Tahoma", Font.BOLD, 20));
		spinner_Weight.setBounds(428, 77, 97, 44);
		contentPane.add(spinner_Weight);

		JLabel lblWeight = new JLabel("Weight");
		lblWeight.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblWeight.setBounds(445, 39, 97, 34);
		contentPane.add(lblWeight);

		final JSpinner spinner_src = new JSpinner();
		spinner_src.setFont(new Font("Tahoma", Font.BOLD, 13));
		spinner_src.setBounds(127, 223, 56, 34);
		contentPane.add(spinner_src);

		JLabel lblSource = new JLabel("Source");
		lblSource.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSource.setBounds(49, 227, 56, 25);
		contentPane.add(lblSource);

		final JSpinner spinner_des = new JSpinner();
		spinner_des.setFont(new Font("Tahoma", Font.BOLD, 13));
		spinner_des.setModel(new SpinnerNumberModel(new Integer(0),
				new Integer(0), null, new Integer(1)));
		spinner_des.setBounds(487, 223, 56, 34);
		contentPane.add(spinner_des);

		JLabel lblDestination = new JLabel("Destination");
		lblDestination.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDestination.setBounds(366, 217, 109, 44);
		contentPane.add(lblDestination);

		btnAddNode.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// When add button is pushed make new node object and add it to
				// the static array list.//
				from = (int) spinner_From.getValue();
				to = (int) spinner_To.getValue();
				weight = (int) spinner_Weight.getValue();
				max = Math.max(max, from);
				max = Math.max(max, to);
				min = Math.min(min, from);
				min = Math.min(min, to);
				Node newNode = new Node(from, to, weight);
				nodes.add(newNode);

			}
		});

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// array nodes should be passed to perform all calculations.//
				src = (int) spinner_src.getValue();
				des = (int) spinner_des.getValue();
				
				if (des > max || src < min || src > max || des < min) {
					JOptionPane optionPane = new JOptionPane(
							"Error in source OR/AND destination values",
							JOptionPane.ERROR_MESSAGE);
					JDialog dialog = optionPane.createDialog("Failure");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				} else {
					final Utils doOperation = new Utils(nodes, src, des);

					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								OptionsMenu frame = new OptionsMenu(doOperation.adjList,
										doOperation.forPaths,
										doOperation.loops,
										doOperation.twountouched,
										doOperation.transferFunction);
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}

			}
		});

	}

}
