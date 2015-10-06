package cn.bjfu.fesdmp.algorithm;


public class Node {
	boolean is=true;
	Node father=null;
	Node son=null;
	Node brother=null;
	String data=null;
	Node (String data){
		this.data=data;
	}
	int[] coverNode;
	public Node nextLeaf(){
		Node k=this;
		if (k.son!=null)
		k=k.son;
		else if(k.brother!=null)
			k=k.brother;
		else
		while(k.father!=null){
			k=k.father;
			if(k.brother!=null){
				k=k.brother;
				break;
			}
		}
		if(k.father==null)
			return null;
		
		while(k.son!=null)
			k=k.son;
		return k;

	}
	public Node nextNode(){						//注：中序遍历，最后一个叶子节点的nextNode是根节点
		Node k=this;
		if (k.son!=null)
		k=k.son;
		else if(k.brother!=null)
			k=k.brother;
		else
		while(k.father!=null){
			k=k.father;
			if(k.brother!=null){
				k=k.brother;
				break;
			}
		}
		return k;

	}
	public Node leftLeaf(){					
		Node k=this;
		while (k.son!=null)
		k=k.son;
		return k;
	}
	public Node rightLeaf(){
		Node k=this;
		while(k.son!=null){
			k=k.son;
			while(k.brother!=null){
				k=k.brother;
			}
		}
		return k;
	}
	
	
	public boolean ifcover(String str){		//判断是否是cover
		if (data.equals(str))
		return true;
		else if(son!=null)
			return son.ifcovernew(str);
		else return false;
	}
	public boolean ifcovernew(String str){	//辅助判断是否是cover

		if (data.equals(str))
		return true;
		else
			if(son!=null){
				if(son.ifcovernew(str)==true)
				return true;
				else if(brother!=null){
					if(brother.ifcovernew(str)==true)
						return true;				
					else return false;
				}
				else return false;
			}
			else if(brother!=null){
				if(brother.ifcovernew(str)==true)
					return true;				
				else return false;
			}	
			else return false;
}
	public float distance(String str){		//计算距离

			float dis=0;
			Node Pointer=this;
			if (Pointer.data.equals(str))
				return dis;
			while (Pointer.son!=null){
				Pointer=Pointer.son;
			}
			while(Pointer!=null){

				if (Pointer.data.equals(str))
				break;
				else
					if (Pointer.brother!=null){
						Pointer=Pointer.brother;
						while(Pointer.son!=null)
							Pointer=Pointer.son;
					}
					else Pointer=Pointer.father;
			}

			while(!Pointer.data.equals(this.data)){
				dis=dis+1;
				Pointer=Pointer.father;
			}
			return dis;
		}
		
	public boolean isBigger(Node k){
		if(this.data.compareTo(k.data)>=0)
		return true;
		else
			return false;
	}
	public boolean isSmaller(Node k){
		if(this.data.compareTo(k.data)<=0)
		return true;
		else
			return false;
	}
	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根

	}

}
