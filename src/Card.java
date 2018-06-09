
public class Card {
	private static final long serialVersionUID = 1L;
	private String suit;//��ɫ
	private String name;//����
	private int value;//��ֵ
	private String ico;//ͼƬ

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
