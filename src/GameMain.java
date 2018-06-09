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

	//每局游戏的一副牌
	private static Cards gameCards = new Cards(); 
	
	//玩家得到的牌
	private static Vector userCards = new Vector();
	
	//电脑方得到的牌
	private static Vector computerCards = new Vector();
	
	//牌的图片存放目录
	private static String imageFile = "images/";
	
	//玩家得到的牌的点数总和
	private static int userCardsValue = 0;
	
	//电脑方得到的牌的点数总和
	private static int computerCardsValue = 0;
	
	//赌注
	private int gameMoney = 0;
	
	private JPanel computerCardsPanel = new JPanel();
	private JPanel userCardsPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	
	final static JButton b_chechout = new JButton();
	//构造函数
	GameMain(){
		
		/*
		 *初始化窗体 
		 */
		this.setSize(900,700);
		Font fonts=new Font("新宋体",Font.PLAIN,12);
		this.setFont(fonts);
		this.setTitle("BlackJack小游戏");
		getContentPane().setLayout(new BorderLayout());
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int left = (screen.width - this.getWidth()) / 2;
        int top = (screen.height - this.getHeight()) / 2;
        this.setLocation(left, top);
	}
	
	//返回gameCards--一副游戏牌
	public Cards getGameCards(){
		return gameCards;
	}
	//返回userCards;
	public Vector getUserCards(){
		return userCards;
	}
	
	//返回computerCards
	public Vector getComputerCards(){
		return computerCards;
	}
	
	//开局
	public void gameStart(){
		
		//初始化游戏牌
		gameCards.init();
		
		//清空原有牌
		userCards.clear();
		computerCards.clear();
		
		//给电脑方发牌
		addCard(0);
		addCard(0);
		
		
		//给玩家方初始发牌2张
		addCard(1);
		addCard(1);
	}
	
	/*
	 * 发一张牌
	 * 参数： int u
	 * u = 0 给电脑方发牌
	 * u = 1 给玩家方发牌
	 */
	public static void addCard(int u){
		if(u == 0)
			computerCards.add(gameCards.deal());
		if(u == 1)
			userCards.add(gameCards.deal());
	}
	
	/*
	 * 计算牌的点数总和
	 * 
	 * 参数: int u
	 * u = 0 计算电脑方的牌的点数总和
	 * u = 1 计算玩家方的牌的点数总和
	 * 
	 * 计算算法:
	 * 1.计算非A的牌的点数和得到值1并统计出A的牌的个数
	 * 2.如果A的牌的个数大于0，则用1中的得到的值加上11加上A的牌的个数减1得到值2，
	 *   判断值2是否小于21，如果是则用值2作为此步骤的值，如果不是则用值1加上A的
	 *   牌的个数作为此步骤的值
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
	 * 返回牌的点数总和
	 * 参数：int u
	 * u = 0 返回电脑方的牌的点数总和
	 * u = 1 返回玩家方的牌的点数总和
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
	 * 是否展示电脑玩家的牌
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
	 * 展示玩家的牌
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
		/*--------------界面创建---------------*/
		/*
		 * 菜单栏
		 */
		//创建菜单栏
		final JMenuBar menuBar = new JMenuBar();
		////首级菜单项
		final JMenu system = new JMenu("游戏");
		final JMenu user = new JMenu("用户");
		//游戏-菜单项
		final JMenuItem quit = new JMenuItem("退出");
		//用户-菜单项
		final JMenuItem setUser = new JMenuItem("用户设置");
		//游戏-菜单项 添加
		system.add(quit);
		//用户-菜单项 添加
		user.add(setUser);
		//首级菜单项  添加
		menuBar.add(system);
		menuBar.add(user);
		//菜单栏 添加
		game.setJMenuBar(menuBar);
		
		
		/*
		 *工具栏
		 * 
		 */
		
		//工具栏--创建
		final JToolBar toolBar = new JToolBar();
		
		//工具栏按钮--创建
		final JButton b_dealCard = new JButton();
		b_dealCard.setText("发牌");
		b_chechout.setText("开牌");
		final JButton b_newGame = new JButton();
		b_newGame.setText("重新开局");
		
		//工具栏按钮-- 添加
		toolBar.add(b_dealCard);
		toolBar.add(b_chechout);
		toolBar.add(b_newGame);
		
		//工具栏--添加
		game.getContentPane().add(toolBar,BorderLayout.NORTH);
		
		/*
		 * 牌的显示
		 * 
		 */
		//电脑方牌的显示，一开局默认不显示
		game.getCenterPanel().add(game.getComputerCardsPanel(false));
		
		//牌的背面
		JLabel cardBack = new JLabel("",new ImageIcon(imageFile + "backSide.png"),JLabel.CENTER);
		cardBack.setBounds(300, 210, 130, 180);
		game.getCenterPanel().add(cardBack);
		//玩家方的牌的显示
		game.getCenterPanel().add(game.getUserCardsPanel());
		/*
		 * 赌注
		 */
		final JLabel label = new JLabel();
		label.setText("赌注:");
		label.setBounds(470, 274, 60, 15);
		game.getCenterPanel().add(label);
		
		//赌注输入文本域
		final JTextField T_gameMoney = new JTextField();
		T_gameMoney.setBounds(470, 307, 130, 21);
		T_gameMoney.setText("20");
		game.getCenterPanel().add(T_gameMoney);
		
		//赌注加按钮
		final JButton b_moneyAdd = new JButton();
		b_moneyAdd.setText("+");
		b_moneyAdd.setBounds(605, 306, 50, 20);
		game.getCenterPanel().add(b_moneyAdd);
		
		//赌注减按钮
		final JButton b_moneySub = new JButton();
		b_moneySub.setText("-");
		b_moneySub.setBounds(665, 306, 50, 20);
		game.getCenterPanel().add(b_moneySub);
		
		/*
		 * 玩家姓名显示
		 */
		//电脑方
		final JLabel l_computerName = new JLabel();
		l_computerName.setText("电脑");
		l_computerName.setBounds(136, 210, 60, 15);
		game.getCenterPanel().add(l_computerName);
		
		//玩家方
		final JLabel l_userName = new JLabel();
		l_userName.setText(gameUser.getUserName());
		l_userName.setBounds(136, 320, 60, 15);
		game.getCenterPanel().add(l_userName);
		
		/*
		 * 玩家电脑图片显示
		 */
		final JLabel computerIco = new JLabel("",new ImageIcon(imageFile + computerUser.getUserIco()),JLabel.CENTER);
		computerIco.setBounds(60, 200, 80, 80);
		game.getCenterPanel().add(computerIco);
		final JLabel userIco = new JLabel("",new ImageIcon(imageFile + gameUser.getUserIco()),JLabel.CENTER);
		userIco.setBounds(60, 290, 80, 80);
		game.getCenterPanel().add(userIco);
		/*
		 *玩家金钱显示 
		 * 
		 */
		//电脑金钱
		final JLabel l_computerMoney = new JLabel();
		l_computerMoney.setText("金钱:" + computerUser.getUserMoney());
		l_computerMoney.setBounds(136, 235, 60, 15);
		game.getCenterPanel().add(l_computerMoney);
		
		//玩家金钱
		final JLabel l_userMoney = new JLabel();
		l_userMoney.setText("金钱:" + gameUser.getUserMoney());
		l_userMoney.setBounds(136, 340, 60, 15);
		game.getCenterPanel().add(l_userMoney);
		
		/*
		 * 玩家的点数显示
		 */
		
		//玩家方得点
		final JLabel l_userValue = new JLabel();
		l_userValue.setBounds(470, 375, 60, 15);
		game.getCenterPanel().add(l_userValue);
		
		//电脑方得点
		final JLabel l_computerValue = new JLabel();
		l_computerValue.setBounds(470, 210, 60, 15);
		game.getCenterPanel().add(l_computerValue);
		
		//赢标志显示
		final JLabel l_win = new JLabel();
		l_win.setForeground(new Color(255, 128, 64));
		l_win.setFont(new Font("", Font.BOLD, 26));
		l_win.setText("赢");
		l_win.setBounds(470, 220, 33, 44);
		l_win.setVisible(false);
		game.getCenterPanel().add(l_win);
		
		/*--------------------事件处理------------*/
		
		/*
		 *菜单栏按钮事件 
		 * 
		 */
		
		//退出按钮事件
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		//用户设置事件
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
		        l_setUserName.setText("用户名：");
		        l_setUserName.setBounds(100, 10, 100, 20);
		        
		        final JTextField t_userName = new JTextField();
		        t_userName.setText(gameUser.getUserName());
		        t_userName.setBounds(160, 10, 100, 20);
		        
		        final  JLabel l_setUserMoney = new JLabel();
		        l_setUserMoney.setText("用户金钱：");
		        l_setUserMoney.setBounds(90, 40, 100, 20);
		        
		        final JTextField t_userMoney = new JTextField();
		        t_userMoney.setText(gameUser.getUserMoney()+"");
		        t_userMoney.setBounds(160, 40, 100, 20);
		        
		        final  ButtonGroup chooseIco = new ButtonGroup();
		        final JRadioButton ico1 = new JRadioButton();
		        ico1.setText("天河");
		        ico1.setBounds(60, 160, 60, 20);
		        final JRadioButton ico2 = new JRadioButton();
		        ico2.setText("梦瑶");
		        ico2.setBounds(160, 160, 60, 20);
		        final JRadioButton ico3 = new JRadioButton();
		        ico3.setText("菱纱");
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
		        b_setUser.setText("确定");
		        
		        final JButton b_setUserCancel = new JButton();
		        b_setUserCancel.setBounds(200, 200, 100, 20);
		        b_setUserCancel.setText("取消");
		        
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
						l_userMoney.setText("金钱:" + gameUser.getUserMoney());
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
		 * 工具栏按钮事件处理
		 * 
		 */
		//发牌按钮事件
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
					String message = "<html><p>你21点啊，还不开牌...</p></html>";
					JOptionPane.showMessageDialog(game,message );
				}
				else if(game.getUserCards().size() < 5){
					String message = "<html><p>不好意思！你的点数大于21</p><p>你挂了... -_-</p></html>";
					JOptionPane.showMessageDialog(game,message );
				}
				else{
					String message = "<html><p>已经发了5张牌了，不能再发了</p></html>";
					JOptionPane.showMessageDialog(game,message );
				}
			}
		});
		
		//重新开局按钮事件
		b_newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//发牌按钮可用
				b_dealCard.setEnabled(true);
				//开牌按钮可用
				b_chechout.setEnabled(true);
				//重新初始化游戏
				game.gameStart();
				
				//更新空间
				game.getComputerCardsPanel(false).removeAll();
				game.getComputerCardsPanel(false).updateUI();
				game.getUserCardsPanel().removeAll();
				game.getUserCardsPanel().updateUI();
				
				//重新设置部分空间显示
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
		
		//开牌按钮事件
		b_chechout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				countCardsValue(0);
				if(getCardsValue(0) < 16)
					addCard(0);
				//将电脑方牌正面显示
				game.getComputerCardsPanel(false).removeAll();
				game.getComputerCardsPanel(true).updateUI();
				game.countCardsValue(0);
				game.countCardsValue(1);
				
				//发牌按钮不可用
				b_dealCard.setEnabled(false);
				b_chechout.setEnabled(false);
				
				//显示玩家得点
				l_userValue.setText(game.getCardsValue(1) + "");
				l_computerValue.setText(game.getCardsValue(0) + "");
				
				//设置赌注
				game.setGameMoney(Integer.parseInt(T_gameMoney.getText()));
				
				//判断输赢
				if(
						(game.getCardsValue(1) < 21 && game.getCardsValue(0) < game.getCardsValue(1))
						||
						(game.getCardsValue(1) < 21 && game.getCardsValue(0) > 21)
					){
					//玩家赢单倍
					l_win.setBounds(470, 340, 33, 44);
					gameUser.setUserMoney(gameUser.getUserMoney() + game.getGameMoney());
					computerUser.setUserMoney(computerUser.getUserMoney() - game.getGameMoney());
				}
				else if(game.getCardsValue(0) == 21 ){
					//庄家得双倍
					l_win.setBounds(470, 220, 33, 44);
					computerUser.setUserMoney(computerUser.getUserMoney() + 2 * game.getGameMoney());
					gameUser.setUserMoney(gameUser.getUserMoney() + game.getGameMoney());
				}
				else if(game.getCardsValue(1) == 21 && game.getCardsValue(0) != 21){
					//玩家赢双倍
					l_win.setBounds(470, 340, 33, 44);
					gameUser.setUserMoney(gameUser.getUserMoney() + 2 * game.getGameMoney());
					computerUser.setUserMoney(computerUser.getUserMoney() - 2 * game.getGameMoney());
				}
				else{
					//庄家赢单倍
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
		
		//赌注输入事件处理
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
		
		//赌注加事件
		b_moneyAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((Integer.parseInt(T_gameMoney.getText()) + 10) < computerUser.getUserMoney() && 
						(Integer.parseInt(T_gameMoney.getText()) + 10) < gameUser.getUserMoney()){
					T_gameMoney.setText(Integer.parseInt(T_gameMoney.getText()) + 10  + "");
					game.setGameMoney(Integer.parseInt(T_gameMoney.getText()));
				}
			}
		});
		
		//赌注减事件
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