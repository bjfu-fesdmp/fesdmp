package cn.bjfu.fesdmp.algorithm;


public class Alert {
	Alert(){
	}
	Alert(int k){
		Alert.anum=k;
	}
	static int anum;
	int alertNum;
	int staticNum;
	Node[] element=new Node[anum];
	int[] order =new int[anum];
	String alertName; 
	
	public void setAnum(int k){
		this.anum=k;
	}
	public int getAnum(){
		return this.anum;
	}
	public void setAlertNum(int k){
		this.alertNum=k;
	}
	public int getAlertNum(){
		return this.alertNum;
	}
	public void setOrder(int order,int k){
		this.order[k]=order;
	}
	public int getOrder(int k){
		return this.order[k];
	}
	
	public void setElement(Node node,int k){
		 this.element[k]=node;
	}	
	public Node getElement(int k){
		return this.element[k];
	}	
	
	
	public boolean isBigger(Alert k){
//		System.out.println(this.alertName);
//		System.out.println(k.alertName);
		if(this.alertName.compareTo(k.alertName)>=0)
		return true;
		else
			return false;
	}
	
	
	
	
	public static void main(String[] args) {

	}

}

