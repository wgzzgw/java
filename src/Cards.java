import java.util.*;

public class Cards {
	private static final long serialVersionUID = 1L;
	private String[] suits = {"红桃","方块","黑桃","梅花"};
	private String[] names = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
	private Vector cards = new Vector();
	
	Cards(){
		init();
	}

	/*
	 * 初始化整副牌
	 */
	public void init(){
		cards.clear();
		for(int i = 0; i < 4; i++){
			for( int j = 0; j < 13; j++){
				cards.add(new Card(suits[i],names[j],(j>=10) ? 10:(j+1)));
			}
		}
	}
	
	/*
	 * 随机分牌，返回值是分出去的一张牌，并在牌堆去掉
	 */
	public Card deal(){
		Random cardIndexRandom = new Random();
		int cardIndex = cardIndexRandom.nextInt(cards.size()-1);
		Card tempCard = (Card)cards.elementAt(cardIndex);
		cards.remove(cardIndex);
		return tempCard;
	}
	//向外提供获取整副牌
	public Vector getCards(){
		return cards;
	}
}
