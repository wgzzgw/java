
public class Card {
	private static final long serialVersionUID = 1L;
	private String suit;//花色
	private String name;//名称
	private int value;//数值
	private String ico;//图片

	Card(String s,String n,int v){
		suit = s;
		name = n;
		value = v;
		ico = this.toString() + ".png";
	}
	
	public String getName(){
		return name;
	}
	
	public String getIco(){
		return this.ico;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public String toString(){
		return this.suit + this.name; 
	}
	
}
