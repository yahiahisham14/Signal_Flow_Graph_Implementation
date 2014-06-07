package Container;

public class Node {

	private int from;
	private int to;
	private int weight;
	
	public Node(int to,int weight){
		
		this.setTo(to);
		this.setWeight(weight);
	}
	public Node(int from,int to,int weight){
		this.setFrom(from);
		this.setTo(to);
		this.setWeight(weight);
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}

}