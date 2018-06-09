import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import sun.audio.*;

public class GameMain extends JFrame {
	private static final long serialVersionUID = 1;

	//ÿ����Ϸ��һ����
	private static Cards gameCards = new Cards(); 
	
	//��ҵõ�����
	private static Vector userCards = new Vector();
	
	//���Է��õ�����
	private static Vector computerCards = new Vector();
	
	//�Ƶ�ͼƬ���Ŀ¼
	private static String imageFile = "images/";
	
	//��ҵõ����Ƶĵ����ܺ�
	private static int userCardsValue = 0;
	
	//���Է��õ����Ƶĵ����ܺ�
	private static int computerCardsValue = 0;
	
	//��ע
	private int gameMoney = 0;
	
	private JPanel computerCardsPanel = new JPanel();
	private JPanel userCardsPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	
	final static JButton b_chechout = new JButton();
	//���캯��
	GameMain(){
		
		/*
		 *��ʼ������ 
		 */
		this.setSize(900,700);
		Font fonts=new Font("������",Font.PLAIN,12);
		this.setFont(fonts);
		this.setTitle("BlackJackС��Ϸ");
		getContentPane().setLayout(new BorderLayout());
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int left = (screen.width - this.getWidth()) / 2;
        int top = (screen.height - this.getHeight()) / 2;
        this.setLocation(left, top);
	}
	
	//����gameCards--һ����Ϸ��
	public Cards getGameCards(){
		return gameCards;
	}
	//����userCards;
	public Vector getUserCards(){
		return userCards;
	}
	
	//����computerCards
	public Vector getComputerCards(){
		return computerCards;
	}
	
	//����
	public void gameStart(){
		
		//��ʼ����Ϸ��
		gameCards.init();
		
		//���ԭ����
		userCards.clear();
		computerCards.clear();
		
		//�����Է�����
		addCard(0);
		addCard(0);
		
		
		//����ҷ���ʼ����2��
		addCard(1);
		addCard(1);
	}
	
	/*
	 * ��һ����
	 * ������ int u
	 * u = 0 �����Է�����
	 * u = 1 ����ҷ�����
	 */
	public static void addCard(int u){
		if(u == 0)
			computerCards.add(gameCards.deal());
		if(u == 1)
			userCards.add(gameCards.deal());
	}
	
	/*
	 * �����Ƶĵ����ܺ�
	 * 
	 * ����: int u
	 * u = 0 ������Է����Ƶĵ����ܺ�
	 * u = 1 ������ҷ����Ƶĵ����ܺ�
	 * 
	 * �����㷨:
	 * 1.�����A���Ƶĵ����͵õ�ֵ1��ͳ�Ƴ�A���Ƶĸ���
	 * 2.���A���Ƶĸ�������0������1�еĵõ���ֵ����11����A���Ƶĸ�����1�õ�ֵ2��
	 *   �ж�ֵ2�Ƿ�С��21�����������ֵ2��Ϊ�˲����ֵ�������������ֵ1����A��
	 *   �Ƶĸ�����Ϊ�˲����ֵ
	 */
	public static void countCardsValue(int u){
		
		int Anumber = 0;
		if(u == 0 ){
			computerCardsValue = 0;
			if(computerCards.size() > 0){
				for(int i = 0;i < computerCards.size();i++){
					
					if(((Card)computerCards.elementAt(i)).getName() == "A"){
						Anumber = Anumber + 1;
					}
					else{
						computerCardsValue += ((Card)computerCards.elementAt(i)).getValue();
					}
				}
				if(Anumber > 0){
					if((computerCardsValue + 11 + Anumber-1 ) <= 21){
						computerCardsValue = computerCardsValue + Anumber-1 + 11;
						
					}
					else{
						computerCardsValue = computerCardsValue+ Anumber;
					}
				}
			}
		}
		
		if(u == 1 ){
			userCardsValue = 0;
			if(userCards.size() > 0){
				for(int i = 0;i < userCards.size();i++){
					
					if(((Card)userCards.elementAt(i)).getName() == "A"){
						Anumber = Anumber + 1;
					}
					else{
						userCardsValue += ((Card)userCards.elementAt(i)).getValue();
					}
				}
				if(Anumber > 0){
					if((userCardsValue + 11 + Anumber-1 )<= 21){
						userCardsValue = userCardsValue + Anumber-1 + 11;	
					}
					else{
						userCardsValue = userCardsValue+ Anumber;
					}
				}
			}
		}
	}
	
	/*
	 * �����Ƶĵ����ܺ�
	 * ������int u
	 * u = 0 ���ص��Է����Ƶĵ����ܺ�
	 * u = 1 ������ҷ����Ƶĵ����ܺ�
	 */
	public static int getCardsValue(int u){
		
		if(u == 0){
			return computerCardsValue;
		}
		else if(u == 1){
			return userCardsValue;
		}
		else{
			return 0;
		}
	}

	public JPanel getComputerCardsPanel(boolean o){
		displayComputerCards(o);
		return this.computerCardsPanel;
	}
	
	public JPanel getUserCardsPanel(){
		displayUserCards();
		return this.userCardsPanel;
	}
	
	public JPanel getCenterPanel(){
		return centerPanel;
	}
	
	public void setGameMoney(int v){
		gameMoney = v;
	}
	
	public int getGameMoney(){
		return gameMoney;
	}
	/*
	 * �Ƿ�չʾ������ҵ���
	 */
	public void displayComputerCards(boolean o){
		if(o){
			for(int i = 0;i < computerCards.size();i++){
				JLabel newLabel = new JLabel("",new ImageIcon(imageFile + ((Card)(computerCards.elementAt(i))).getIco()),JLabel.CENTER);
				newLabel.setBounds(70+130*i, 20, 130, 180);
				computerCardsPanel.add(newLabel);
			}
		}
		else{
			for(int i = 0;i < 5;i++){
				JLabel newLabel = new JLabel("",new ImageIcon(imageFile + "backSide.png"),JLabel.CENTER);
				newLabel.setBounds(70+130*i, 20, 130, 180);
				computerCardsPanel.add(newLabel);
			}
		}
		computerCardsPanel.setBounds(70,20,800,180);
	}
	/*
	 * չʾ��ҵ���
	 */ 
	public void  displayUserCards(){
		for(int i = 0;i < userCards.size();i++){
			JLabel newLabel = new JLabel("",new ImageIcon(imageFile + ((Card)(userCards.elementAt(i))).getIco()),JLabel.CENTER);
			newLabel.setBounds(100+135*i, 400, 130, 180);
			userCardsPanel.add(newLabel);
		}
		userCardsPanel.setBounds(100,400,800,180);
	}
	
	public static void main(String[] args){
		
		final GameMain game = new GameMain();
		final User gameUser = new User();
		gameUser.init("feifei");
		final User computerUser = new User();
		computerUser.init("computer");
		game.getContentPane().add(game.getCenterPanel(), BorderLayout.CENTER);
		game.getCenterPanel().setLayout(null);
		game.gameStart();
		/*--------------���洴��---------------*/
		/*
		 * �˵���
		 */
		//�����˵���
		final JMenuBar menuBar = new JMenuBar();
		////�׼��˵���
		final JMenu system = new JMenu("��Ϸ");
		final JMenu user = new JMenu("�û�");
		//��Ϸ-�˵���
		final JMenuItem quit = new JMenuItem("�˳�");
		//�û�-�˵���
		final JMenuItem setUser = new JMenuItem("�û�����");
		//��Ϸ-�˵��� ���
		system.add(quit);
		//�û�-�˵��� ���
		user.add(setUser);
		//�׼��˵���  ���
		menuBar.add(system);
		menuBar.add(user);
		//�˵��� ���
		game.setJMenuBar(menuBar);
		
		
		/*
		 *������
		 * 
		 */
		
		//������--����
		final JToolBar toolBar = new JToolBar();
		
		//��������ť--����
		final JButton b_dealCard = new JButton();
		b_dealCard.setText("����");
		b_chechout.setText("����");
		final JButton b_newGame = new JButton();
		b_newGame.setText("���¿���");
		
		//��������ť-- ���
		toolBar.add(b_dealCard);
		toolBar.add(b_chechout);
		toolBar.add(b_newGame);
		
		//������--���
		game.getContentPane().add(toolBar,BorderLayout.NORTH);
		
		/*
		 * �Ƶ���ʾ
		 * 
		 */
		//���Է��Ƶ���ʾ��һ����Ĭ�ϲ���ʾ
		game.getCenterPanel().add(game.getComputerCardsPanel(false));
		
		//�Ƶı���
		JLabel cardBack = new JLabel("",new ImageIcon(imageFile + "backSide.png"),JLabel.CENTER);
		cardBack.setBounds(300, 210, 130, 180);
		game.getCenterPanel().add(cardBack);
		//��ҷ����Ƶ���ʾ
		game.getCenterPanel().add(game.getUserCardsPanel());
		/*
		 * ��ע
		 */
		final JLabel label = new JLabel();
		label.setText("��ע:");
		label.setBounds(470, 274, 60, 15);
		game.getCenterPanel().add(label);
		
		//��ע�����ı���
		final JTextField T_gameMoney = new JTextField();
		T_gameMoney.setBounds(470, 307, 130, 21);
		T_gameMoney.setText("20");
		game.getCenterPanel().add(T_gameMoney);
		
		//��ע�Ӱ�ť
		final JButton b_moneyAdd = new JButton();
		b_moneyAdd.setText("+");
		b_moneyAdd.setBounds(605, 306, 50, 20);
		game.getCenterPanel().add(b_moneyAdd);
		
		//��ע����ť
		final JButton b_moneySub = new JButton();
		b_moneySub.setText("-");
		b_moneySub.setBounds(665, 306, 50, 20);
		game.getCenterPanel().add(b_moneySub);
		
		/*
		 * ���������ʾ
		 */
		//���Է�
		final JLabel l_computerName = new JLabel();
		l_computerName.setText("����");
		l_computerName.setBounds(136, 210, 60, 15);
		game.getCenterPanel().add(l_computerName);
		
		//��ҷ�
		final JLabel l_userName = new JLabel();
		l_userName.setText(gameUser.getUserName());
		l_userName.setBounds(136, 320, 60, 15);
		game.getCenterPanel().add(l_userName);
		
		/*
		 * ��ҵ���ͼƬ��ʾ
		 */
		final JLabel computerIco = new JLabel("",new ImageIcon(imageFile + computerUser.getUserIco()),JLabel.CENTER);
		computerIco.setBounds(60, 200, 80, 80);
		game.getCenterPanel().add(computerIco);
		final JLabel userIco = new JLabel("",new ImageIcon(imageFile + gameUser.getUserIco()),JLabel.CENTER);
		userIco.setBounds(60, 290, 80, 80);
		game.getCenterPanel().add(userIco);
		/*
		 *��ҽ�Ǯ��ʾ 
		 * 
		 */
		//���Խ�Ǯ
		final JLabel l_computerMoney = new JLabel();
		l_computerMoney.setText("��Ǯ:" + computerUser.getUserMoney());
		l_computerMoney.setBounds(136, 235, 60, 15);
		game.getCenterPanel().add(l_computerMoney);
		
		//��ҽ�Ǯ
		final JLabel l_userMoney = new JLabel();
		l_userMoney.setText("��Ǯ:" + gameUser.getUserMoney());
		l_userMoney.setBounds(136, 340, 60, 15);
		game.getCenterPanel().add(l_userMoney);
		
		/*
		 * ��ҵĵ�����ʾ
		 */
		
		//��ҷ��õ�
		final JLabel l_userValue = new JLabel();
		l_userValue.setBounds(470, 375, 60, 15);
		game.getCenterPanel().add(l_userValue);
		
		//���Է��õ�
		final JLabel l_computerValue = new JLabel();
		l_computerValue.setBounds(470, 210, 60, 15);
		game.getCenterPanel().add(l_computerValue);
		
		//Ӯ��־��ʾ
		final JLabel l_win = new JLabel();
		l_win.setForeground(new Color(255, 128, 64));
		l_win.setFont(new Font("", Font.BOLD, 26));
		l_win.setText("Ӯ");
		l_win.setBounds(470, 220, 33, 44);
		l_win.setVisible(false);
		game.getCenterPanel().add(l_win);
		
		/*--------------------�¼�����------------*/
		
		/*
		 *�˵�����ť�¼� 
		 * 
		 */
		
		//�˳���ť�¼�
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		//�û������¼�
		setUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.exit(0);
				final JFrame userSetFrame = new JFrame();
				userSetFrame.setSize(400, 300);
				Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		        int left = (screen.width - userSetFrame.getWidth()) / 2;
		        int top = (screen.height - userSetFrame.getHeight()) / 2;
		        userSetFrame.setLocation(left, top);
		        userSetFrame.setLayout(null);
		        userSetFrame.setResizable(false);
		        
		        final JLabel l_setUserName = new JLabel();
		        l_setUserName.setText("�û�����");
		        l_setUserName.setBounds(100, 10, 100, 20);
		        
		        final JTextField t_userName = new JTextField();
		        t_userName.setText(gameUser.getUserName());
		        t_userName.setBounds(160, 10, 100, 20);
		        
		        final  JLabel l_setUserMoney = new JLabel();
		        l_setUserMoney.setText("�û���Ǯ��");
		        l_setUserMoney.setBounds(90, 40, 100, 20);
		        
		        final JTextField t_userMoney = new JTextField();
		        t_userMoney.setText(gameUser.getUserMoney()+"");
		        t_userMoney.setBounds(160, 40, 100, 20);
		        
		        final  ButtonGroup chooseIco = new ButtonGroup();
		        final JRadioButton ico1 = new JRadioButton();
		        ico1.setText("���");
		        ico1.setBounds(60, 160, 60, 20);
		        final JRadioButton ico2 = new JRadioButton();
		        ico2.setText("����");
		        ico2.setBounds(160, 160, 60, 20);
		        final JRadioButton ico3 = new JRadioButton();
		        ico3.setText("��ɴ");
		        ico3.setBounds(245, 160, 60, 20);
		        
		        chooseIco.add(ico1);
		        chooseIco.add(ico2);
		        chooseIco.add(ico3);
		        
		        final JLabel l_ico1 = new JLabel("",new ImageIcon(imageFile + "1.png"),JLabel.CENTER);
		        final JLabel l_ico2 = new JLabel("",new ImageIcon(imageFile + "2.png"),JLabel.CENTER);
		        final JLabel l_ico3 = new JLabel("",new ImageIcon(imageFile + "3.png"),JLabel.CENTER);
		        final JPanel icoPanel = new JPanel();
		        icoPanel.add(l_ico1);
		        icoPanel.add(l_ico2);
		        icoPanel.add(l_ico3);
		        icoPanel.setBounds(30, 70, 300, 85);
		        
		        final JButton b_setUser = new JButton();
		        b_setUser.setBounds(80, 200, 100, 20);
		        b_setUser.setText("ȷ��");
		        
		        final JButton b_setUserCancel = new JButton();
		        b_setUserCancel.setBounds(200, 200, 100, 20);
		        b_setUserCancel.setText("ȡ��");
		        
		        userSetFrame.add(l_setUserName);
		        userSetFrame.add(t_userName);
		        userSetFrame.add(l_setUserMoney);
		        userSetFrame.add(t_userMoney);
		        userSetFrame.add(ico1);
		        userSetFrame.add(ico2);
		        userSetFrame.add(ico3);
		        userSetFrame.add(icoPanel);
		        userSetFrame.add(b_setUser);
		        userSetFrame.add(b_setUserCancel);
				userSetFrame.setVisible(true);
				
				b_setUser.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gameUser.setUserMoney(Integer.parseInt(t_userMoney.getText()));
						if(ico1.isSelected())
							gameUser.setUserIco("1.png");
						else if(ico2.isSelected())
							gameUser.setUserIco("2.png");
						else if(ico3.isSelected())
							gameUser.setUserIco("3.png");
						gameUser.save();
						l_userMoney.setText("��Ǯ:" + gameUser.getUserMoney());
						userIco.setIcon(new ImageIcon(imageFile + gameUser.getUserIco()));
						userSetFrame.setVisible(false);
					}
				});
				
				b_setUserCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						userSetFrame.setVisible(false);
					}
				});
			}
		});
		/*
		 * ��������ť�¼�����
		 * 
		 */
		//���ư�ť�¼�
		b_dealCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				countCardsValue(0);
				if(getCardsValue(0) < 16)
					addCard(0);
				game.countCardsValue(1);
				if(game.getUserCards().size() < 5 && game.getCardsValue(1)< 21){
					game.addCard(1);
					game.countCardsValue(1);
					game.getUserCardsPanel().removeAll();
					game.getUserCardsPanel().updateUI();
				}
				else if(game.getUserCards().size() < 5 && game.getCardsValue(1) == 21){
					String message = "<html><p>��21�㰡����������...</p></html>";
					JOptionPane.showMessageDialog(game,message );
				}
				else if(game.getUserCards().size() < 5){
					String message = "<html><p>������˼����ĵ�������21</p><p>�����... -_-</p></html>";
					JOptionPane.showMessageDialog(game,message );
				}
				else{
					String message = "<html><p>�Ѿ�����5�����ˣ������ٷ���</p></html>";
					JOptionPane.showMessageDialog(game,message );
				}
			}
		});
		
		//���¿��ְ�ť�¼�
		b_newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//���ư�ť����
				b_dealCard.setEnabled(true);
				//���ư�ť����
				b_chechout.setEnabled(true);
				//���³�ʼ����Ϸ
				game.gameStart();
				
				//���¿ռ�
				game.getComputerCardsPanel(false).removeAll();
				game.getComputerCardsPanel(false).updateUI();
				game.getUserCardsPanel().removeAll();
				game.getUserCardsPanel().updateUI();
				
				//�������ò��ֿռ���ʾ
				l_userValue.setText("");
				l_computerValue.setText("");
				l_win.setVisible(false);
				if(((Card)computerCards.elementAt(0)).getName().equals("A")&&(((Card)computerCards.elementAt(1)).getName().equals("10")||
						((Card)computerCards.elementAt(1)).getName().equals("J")||((Card)computerCards.elementAt(1)).getName().equals("Q")||
						((Card)computerCards.elementAt(1)).getName().equals("K"))||
						((Card)computerCards.elementAt(1)).getName().equals("A")&&(((Card)computerCards.elementAt(0)).getName().equals("10")||
						((Card)computerCards.elementAt(0)).getName().equals("J")||((Card)computerCards.elementAt(0)).getName().equals("Q")||
						((Card)computerCards.elementAt(0)).getName().equals("K"))) {
					b_chechout.doClick();
				}
			}
		});
		
		//���ư�ť�¼�
		b_chechout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				countCardsValue(0);
				if(getCardsValue(0) < 16)
					addCard(0);
				//�����Է���������ʾ
				game.getComputerCardsPanel(false).removeAll();
				game.getComputerCardsPanel(true).updateUI();
				game.countCardsValue(0);
				game.countCardsValue(1);
				
				//���ư�ť������
				b_dealCard.setEnabled(false);
				b_chechout.setEnabled(false);
				
				//��ʾ��ҵõ�
				l_userValue.setText(game.getCardsValue(1) + "");
				l_computerValue.setText(game.getCardsValue(0) + "");
				
				//���ö�ע
				game.setGameMoney(Integer.parseInt(T_gameMoney.getText()));
				
				//�ж���Ӯ
				if(
						(game.getCardsValue(1) < 21 && game.getCardsValue(0) < game.getCardsValue(1))
						||
						(game.getCardsValue(1) < 21 && game.getCardsValue(0) > 21)
					){
					//���Ӯ����
					l_win.setBounds(470, 340, 33, 44);
					gameUser.setUserMoney(gameUser.getUserMoney() + game.getGameMoney());
					computerUser.setUserMoney(computerUser.getUserMoney() - game.getGameMoney());
				}
				else if(game.getCardsValue(0) == 21 ){
					//ׯ�ҵ�˫��
					l_win.setBounds(470, 220, 33, 44);
					computerUser.setUserMoney(computerUser.getUserMoney() + 2 * game.getGameMoney());
					gameUser.setUserMoney(gameUser.getUserMoney() + game.getGameMoney());
				}
				else if(game.getCardsValue(1) == 21 && game.getCardsValue(0) != 21){
					//���Ӯ˫��
					l_win.setBounds(470, 340, 33, 44);
					gameUser.setUserMoney(gameUser.getUserMoney() + 2 * game.getGameMoney());
					computerUser.setUserMoney(computerUser.getUserMoney() - 2 * game.getGameMoney());
				}
				else{
					//ׯ��Ӯ����
					l_win.setBounds(470, 220, 33, 44);
					computerUser.setUserMoney(computerUser.getUserMoney() + 2 * game.getGameMoney());
					gameUser.setUserMoney(gameUser.getUserMoney() - game.getGameMoney());
				}
				l_computerMoney.setText(computerUser.getUserMoney() + "");
				l_userMoney.setText(gameUser.getUserMoney() + "");
				l_win.setVisible(true);
				gameUser.save();
				computerUser.save();
			}
		});
		
		//��ע�����¼�����
		T_gameMoney.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				int tempMoney = Integer.parseInt(T_gameMoney.getText());
				if( tempMoney < 10){
					T_gameMoney.setText("10");
				}
				else if(tempMoney < gameUser.getUserMoney() && tempMoney < computerUser.getUserMoney() ){
					game.setGameMoney(tempMoney);
					T_gameMoney.setText(tempMoney + "");
				}
				else if(tempMoney > gameUser.getUserMoney() || tempMoney > computerUser.getUserMoney()){
					T_gameMoney.setText(Math.min(gameUser.getUserMoney(),computerUser.getUserMoney()) + "");
				}
				game.setGameMoney(Integer.parseInt(T_gameMoney.getText()));
			}
		});
		
		//��ע���¼�
		b_moneyAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((Integer.parseInt(T_gameMoney.getText()) + 10) < computerUser.getUserMoney() && 
						(Integer.parseInt(T_gameMoney.getText()) + 10) < gameUser.getUserMoney()){
					T_gameMoney.setText(Integer.parseInt(T_gameMoney.getText()) + 10  + "");
					game.setGameMoney(Integer.parseInt(T_gameMoney.getText()));
				}
			}
		});
		
		//��ע���¼�
		b_moneySub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((Integer.parseInt(T_gameMoney.getText()) - 10) > 0)
					T_gameMoney.setText(Integer.parseInt(T_gameMoney.getText()) - 10  + "");
				else
					T_gameMoney.setText("10");
				game.setGameMoney(Integer.parseInt(T_gameMoney.getText()));
			}
		});
		game.setVisible(true);
		if(((Card)computerCards.elementAt(0)).getName().equals("A")&&(((Card)computerCards.elementAt(1)).getName().equals("10")||
				((Card)computerCards.elementAt(1)).getName().equals("J")||((Card)computerCards.elementAt(1)).getName().equals("Q")||
				((Card)computerCards.elementAt(1)).getName().equals("K"))||
				((Card)computerCards.elementAt(1)).getName().equals("A")&&(((Card)computerCards.elementAt(0)).getName().equals("10")||
				((Card)computerCards.elementAt(0)).getName().equals("J")||((Card)computerCards.elementAt(0)).getName().equals("Q")||
				((Card)computerCards.elementAt(0)).getName().equals("K"))) {
			b_chechout.doClick();
		}
	}
}