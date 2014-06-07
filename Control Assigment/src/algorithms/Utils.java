package algorithms;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import Container.Node;

public class Utils {
	private static int size;
	public static ArrayList<Node> adjList[];
	private static boolean visited[];
	private static int des;
	private static int src;
	private static ArrayList<Integer> path;
	public static ArrayList<Double> deltaKs;
	// for printing in gui
	public static ArrayList<String> loops;
	public static ArrayList<String> forPaths;
	public static ArrayList<String> twountouched;
	private ArrayList<String[]> untouchedLoops;
	public static double transferFunction;

	// This constructor takes the nodes ArrayList from UI.
	// And performs all the logic
	// has three static arrayLists for options menu class in gui.
	public Utils(ArrayList<Node> nodes, int sr, int dest) {
		// used to get size of nodes;
		size = 0;
		src = sr;
		des = dest;
		int max=0;
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for (int i = 0; i < nodes.size(); i++) {
			max=Math.max(max, nodes.get(i).getFrom());
			max=Math.max(max, nodes.get(i).getTo());
			if (!arr.contains(new Integer(nodes.get(i).getFrom()))) {
				size++;
			}

		}
		untouchedLoops = new ArrayList<String[]>();
		adjList = new ArrayList[max+1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Node>();
		}
		for (int i = 0; i < nodes.size(); i++) {
			int from = nodes.get(i).getFrom();
			adjList[from].add(nodes.get(i));
		}
		visited = new boolean[max+1];
		loops = new ArrayList<String>();
		path = new ArrayList<Integer>();
		deltaKs=new ArrayList<Double>();
		// for Gui
		forPaths = new ArrayList<>();
		twountouched = new ArrayList<String>();

		getForwardPaths(src);
		System.out.println("All Loops");
		getLoops(src);
		System.out.println();
		System.out.println("UnTouched Loops");
		twountouched = getUnTouchedLoops(loops);
		transferFunction=masonFormula();
	}

	public static void getForwardPaths(int src) {

		visited[src] = true;
		path.add(src);

		if (src == des) {
			String s = "";
			for (int i : path) {
				s += i + " ";
			}
			forPaths.add(s);
			path.remove(new Integer(src));
			return;
		}

		for (Node i : adjList[src]) {
			
			if (!visited[i.getTo()]) {
				getForwardPaths(i.getTo());
				// backtrack.
				visited[i.getTo()] = false;
			}

		}
		path.remove(new Integer(src));

	}

	public static void getLoops(int src) {

		visited[src] = true;
		path.add(src);

		for (Node i : adjList[src]) {
			if (!visited[i.getTo()]) {
				getLoops(i.getTo());
				// backtrack.
				visited[i.getTo()] = false;
			} else if (path.contains(new Integer(i.getTo()))) {
				String s = "";
				for (int j = path.size() - 1; j >= 0; j--) {
					// to print the loop backward.
					s = path.get(j) + " " + s;
					if (path.get(j) == i.getTo())
						break;
				}
				System.out.println(s + i.getTo());
				loops.add(s + i.getTo());
			}

		}
		path.remove(new Integer(src));

	}

	public static ArrayList<String> getUnTouchedLoops(ArrayList<String> s) {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < s.size(); i++) {
			String current = s.get(i);
			for (int j = i + 1; j < s.size(); j++) {
				boolean touched = isTouched(s.get(i), s.get(j));

				if (!touched) {
					result.add(current + "   " + s.get(j));
					System.out.println(current + "   " + s.get(j));
				}
			}
		}
		return result;

	}

	private static boolean isTouched(String s1, String s2) {
		for (int i = 0; i < s1.length(); i++) {
			for (int j = 0; j < s2.length(); j++) {
				if (s1.charAt(i) != ' ' && s2.charAt(j) != ' '
						&& s1.charAt(i) == s2.charAt(j))
					return true;
			}
		}
		return false;
	}
	
/////////////////////////////////////////////////////New CODE////////////////////////////////////
	public double masonFormula(){
		//Forward Paths:
		double sum = 0.0;
		for( int i = 0 ;i < forPaths.size() ;i++ ){
			List<Integer> list = getList( forPaths.get(i) );
			double gain1 = getGain( list );
			double gain2 = getDeltaK( list );
			double gain = gain1 * gain2;
			sum += gain;
		}//end for.
		
		//get Delta:
		double delta = 1.0;
		delta = delta - getSumLoopGains() + getSumUntouchedLoopGains();
		
		double mason = sum / delta;
		
		return mason;
	}//end method.
	
	public List<Integer> getList( String path ){
		List<Integer> list = new ArrayList<Integer>();
		StringTokenizer st = new StringTokenizer( path );
		while( st.hasMoreTokens() ){
			list.add( Integer.parseInt( st.nextToken() ) );
		}//end while.
		return list;
	}//end method.
	
	public double getGain( List<Integer> path ){
		double gain = 1.0;
		for( int i = 0 ;i < path.size() - 1 ;i++){
			int from = path.get(i);
			int to = path.get(i + 1);
			for( int j = 0 ; j < adjList[from].size() ;j++ ){
				Node b = adjList[from].get(j);
				if( b.getTo() == to ){
					gain = gain * b.getWeight(); 
				}
				
			}//end for j.
			
		}//end for i.
		
		return gain;
	}//end method.
	
	public double getDeltaK( List<Integer> path ){
		double gain = 0.0;
		
		for( int i = 0 ;i < loops.size() ;i++){
			List<Integer> loopList = getList( loops.get(i) );
			boolean collide = false;
			for( int j = 0 ;j < loopList.size() ;j++){
				int x = loopList.get(j);
				if( path.contains( x ) ){
					collide = true;
					break;
				}
				
			}//end for j.
			if( !collide ){
				gain += getGain( loopList );
			}
			
		}//end for i.
		
		double check = 1 - gain;
		deltaKs.add( check );
		return check;
	}//end method.
	
	public double getSumLoopGains(){
		
		double sum = 0.0;
		for( int i = 0 ;i < loops.size() ;i++ ){
			List<Integer> list = getList( loops.get(i) );
			double gain = getGain( list );
			sum += gain;
		}//end for i.
		
		return sum;
	}//end method.
	
	public double getSumUntouchedLoopGains(){
		
		double sum = 0.0;
		List<Integer> list1;
		List<Integer> list2;
		
		for( int i = 0 ;i < loops.size() - 1 ;i++ ){
			list1 = getList( loops.get(i) );
			boolean touched = false;
			for( int j = i + 1 ;j < loops.size() ;j++ ){
				list2 = getList( loops.get(j) );
				touched = checkTouched(list1, list2);
				if( !touched ){
					double gain1 = getGain(list1);
					double gain2 = getGain(list2);
					double gain = gain1 * gain2;
					sum += gain;
					String[] arr = new String[2];
					arr[0] = loops.get(i);
					arr[1] = loops.get(i);
					untouchedLoops.add( arr );
				}//end if.
				
			}//end for j.
			
		}//end for i.
		return sum;
	}//end method.
	
	public boolean checkTouched( List<Integer> list1 , List<Integer> list2 ){
		
		for( int i = 0 ;i < list1.size() ;i++ ){
			int x = list1.get(i);
			if( list2.contains(x) ){
				return true;
			}//end if.
			
		}//end for i.
		
		return false;
	}//end method.
	
	public ArrayList<String> getForwardPaths(){
		return forPaths;
	}
	
	public ArrayList<String> getLoops(){
		return loops;
	}
	
	public ArrayList<String[]> getUntouchedLoops(){
		return untouchedLoops;
	}
	
	public ArrayList<Double> getDeltaKs(){
		return deltaKs;
	}

	
	
	/////////////////////////////////////////////OLD CODE/////////////////////////////////////////////
//
//	private static int getTransferFn() {
//		int delta = getDelta();
//		System.out.println(delta);
//		int[] deltas = new int[forPaths.size() + 1];
//		int[] forwardGains = new int[forPaths.size() + 1];
//		for (int i = 0; i < forPaths.size(); i++) {
//			deltas[i + 1] = getDeltaI(i);
//			forwardGains[i + 1] = getForwardGains(i);
//		}
//		
//		int transferFn=0;
//		for (int i = 1; i < deltas.length; i++) {
//			transferFn+=(long) (double)(deltas[i]*forwardGains[i])/(double)delta;
//		}
//
//		return transferFn;
//	}
//
//	private static int getForwardGains(int index) {
//		String path=forPaths.get(index);
//		StringTokenizer st=new StringTokenizer(path);
//		int product=1;
//		int from=Integer.parseInt(st.nextToken());
//		while (st.hasMoreElements()) {
//			int to=Integer.parseInt(st.nextToken());
//			for (int k = 0; k < adjList[from].size(); k++) {
//
//				if (adjList[from].get(k).getTo() == to)
//					product *= adjList[from].get(k).getWeight();
//			}
//			from = to;
//		}
//		
//		return product;
//	}
//
//	private static int getDelta() {
//
//		// first get sum of all loops gains.
//		int allLoopsSum = 0;
//		for (int i = 0; i < loops.size(); i++) {
//			StringTokenizer st = new StringTokenizer(loops.get(i));
//
//			// ignore last bec last=first;
//			int product = 1;
//			int from = Integer.parseInt(st.nextToken());
//			for (int j = 0; j < st.countTokens() - 1; j++) {
//				int to = Integer.parseInt(st.nextToken());
//				// loops in the adjacent list to find the node.
//				for (int k = 0; k < adjList[from].size(); k++) {
//
//					if (adjList[from].get(k).getTo() == to)
//						product *= adjList[from].get(k).getWeight();
//				}
//				from = to;
//			}
//			allLoopsSum += product;
//		}
//
//		// second : get sop of 2 untouchde loops.
//
//		int twountouchedSum = 0;
//		for (int i = 0; i < twountouched.size(); i++) {
//
//			String s[] = twountouched.get(i).split("   ");
//			// sop of every combination of 2 untouched loops//
//			for (int j = 0; j < s.length; j++) {
//				StringTokenizer st = new StringTokenizer(s[j]);
//
//				// ignore last bec last=first;
//				int product = 1;
//				int from = Integer.parseInt(st.nextToken());
//				for (int m = 0; m < st.countTokens() - 1; m++) {
//					int to = Integer.parseInt(st.nextToken());
//					// loops in the adjacent list to find the node.
//					for (int k = 0; k < adjList[from].size(); k++) {
//
//						if (adjList[from].get(k).getTo() == to)
//							product *= adjList[from].get(k).getWeight();
//					}
//					from = to;
//				}
//				twountouchedSum += product;
//
//			}
//		}
//
//		System.out.println(allLoopsSum);
//		System.out.println(twountouchedSum);
//		// delta=1-(sop of all loop gains)+(sop of every 2 untoched loops)
//
//		return 1 - allLoopsSum + twountouchedSum;
//
//	}
//
//	// given index of forward path//
//	// if found one similarity bet any loop this loop will be terminated//
//	private static int getDeltaI(int index) {
//		boolean collision = false;
//		ArrayList<String> newloops = new ArrayList<String>();
//
//		String s = forPaths.get(index);
//		StringTokenizer st = new StringTokenizer(s);
//
//		for (int i = 0; i < loops.size(); i++) {
//			String test = loops.get(i);
//			collision = false;
//			while (st.hasMoreTokens()) {
//				if (test.contains(st.nextToken()))
//					collision = true;
//			}
//			// if it has no similar nodes , add it to the new arraylist.
//			if (!collision)
//				newloops.add(test);
//		}
//
//		// compute the delta on the new loop arrayList.
//		// first get sum of all loops gains.
//		int allLoopsSum = 0;
//		for (int i = 0; i < newloops.size(); i++) {
//			st = new StringTokenizer(newloops.get(i));
//
//			// ignore last bec last=first;
//			int product = 1;
//			int from = Integer.parseInt(st.nextToken());
//			for (int j = 0; j < st.countTokens() - 1; j++) {
//				int to = Integer.parseInt(st.nextToken());
//				// loops in the adjacent list to find the node.
//				for (int k = 0; k < adjList[from].size(); k++) {
//
//					if (adjList[from].get(k).getTo() == to)
//						product *= adjList[from].get(k).getWeight();
//				}
//				from = to;
//			}
//			allLoopsSum += product;
//		}
//
//		ArrayList<String> unt = getUnTouchedLoops(newloops);
//		// second : get sop of 2 untouchde loops.
//
//		int twountouchedSum = 0;
//		for (int i = 0; i < unt.size(); i++) {
//
//			String ma[] = unt.get(i).split("   ");
//			// sop of every combination of 2 untouched loops//
//			for (int j = 0; j < ma.length; j++) {
//				st = new StringTokenizer(ma[j]);
//
//				// ignore last bec last=first;
//				int product = 1;
//				int from = Integer.parseInt(st.nextToken());
//				for (int m = 0; m < st.countTokens() - 1; m++) {
//					int to = Integer.parseInt(st.nextToken());
//					// loops in the adjacent list to find the node.
//					for (int k = 0; k < adjList[from].size(); k++) {
//
//						if (adjList[from].get(k).getTo() == to)
//							product *= adjList[from].get(k).getWeight();
//					}
//					from = to;
//				}
//				twountouchedSum += product;
//
//			}
//		}
//
//		// delta=1-(sop of all loop gains)+(sop of every 2 untoched loops)
//
//		return 1 - allLoopsSum + twountouchedSum;
//
//	}

}
