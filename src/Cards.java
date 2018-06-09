import java.util.*;

public class Cards {
	private static final long serialVersionUID = 1L;
	private String[] suits = {"����","����","����","÷��"};
	private String[] names = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
	private Vector cards = new Vector();
	
	Cards(){
		init();
	}

	/*
	 * ��ʼ��������
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
	 * ������ƣ�����ֵ�Ƿֳ�ȥ��һ���ƣ������ƶ�ȥ��
	 */
	public Card deal(){
		Random cardIndexRandom = new Random();
		int cardIndex = cardIndexRandom.nextInt(cards.size()-1);
		Card tempCard = (Card)cards.elementAt(cardIndex);
		cards.remove(cardIndex);
		return tempCard;
	}
	//�����ṩ��ȡ������
	public Vector getCards(){
		return cards;
	}
}
