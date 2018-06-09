import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import javax.xml.parsers.*;
public class User {
	private static final long serialVersionUID = 1;
	private String name;//玩家名字
	private String ico;//玩家头像
	private int money;//玩家财产
	private static String userInfomationFile = "config\\userInformation.xml";
	User(){
	}
	User(String n, int m, String i){
		name = n;
		money = m;
		ico = i;
	}
	
	public void init(String n){
		name = n;
		ico = readUserIco();
		money = readUserMoney();
	}
	/*
	 * 根据XML返回Document
	 */
	private Document creatDoc(){
		try{
			File f = new File(userInfomationFile);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);		
			return doc;
		}catch(Exception e){
			System.out.println("Error");
			return null;
		}
	}

	public void writeDoc(Document doc){
		
		TransformerFactory tFactory =TransformerFactory.newInstance();
		try{
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
		
			StreamResult result = new StreamResult(new File(userInfomationFile));

			transformer.transform(source, result);
		}catch(Exception e){
			System.out.println("Error");
		}
	}
	private int readUserMoney(){
		return Integer.parseInt(getUserNode().getNextSibling().getFirstChild().getNodeValue());
	}
	
	private String readUserIco(){
		return getUserNode().getNextSibling().getNextSibling().getFirstChild().getNodeValue();
	}
	public String getUserName(){
		return this.name;
	}
	
	public int getUserMoney(){
		return this.money;
	}	
	
	public String getUserIco(){
		return this.ico;
	}
	
	public void setUserName(String n){
		this.name = n;
	}
	
	public void setUserMoney(int m){
		this.money = m;
	}
	
	public void setUserIco(String i){
		this.ico = i;
	}
	/*
	 * 对外提供添加用户（自己）到系统的功能
	 */
	public void addUser(){
		
		Document XMLDocument = creatDoc();
		Element nameNode=XMLDocument.createElement("name");
		nameNode.appendChild(XMLDocument.createTextNode(name));
		
		Element moneyNode=XMLDocument.createElement("money");
		moneyNode.appendChild(XMLDocument.createTextNode(money + ""));
		
		Element icoNode=XMLDocument.createElement("ico");
		icoNode.appendChild(XMLDocument.createTextNode(ico));
		
		Element userNode=XMLDocument.createElement("user");
		userNode.appendChild(nameNode);
		userNode.appendChild(moneyNode);
		userNode.appendChild(icoNode);
		
		XMLDocument.getDocumentElement().appendChild(userNode);
		
		writeDoc(XMLDocument);
	}
	
	public boolean isUser(String n){
		if( getUserNode() != null){
			return true;
		}
		else{
			return false;
		}
			
	}
	/*
	 * 获取用户的类型
	 */
	public Node getUserNode(){
		Document XMLDocument = creatDoc();
		NodeList userList = XMLDocument.getElementsByTagName("user");
		for(int i = 0;i < userList.getLength(); i++){
			if(userList.item(i).getChildNodes().item(0).getTextContent().equals(name)){
				Node tempN =userList.item(i).getChildNodes().item(0);
				return tempN;
			}
		}
		return null;
	}
	
	public void removeUser(){
		Document XMLDocument = creatDoc();
		XMLDocument.getDocumentElement().removeChild(getUserNode());
		writeDoc(XMLDocument);
	}
	
	public void save(){
		Document XMLDocument = creatDoc();
		NodeList userList = XMLDocument.getElementsByTagName("user");
		for(int i = 0;i < userList.getLength(); i++){
			if(name.equals(XMLDocument.getElementsByTagName("name").item(i).getFirstChild().getNodeValue())){
				Node tempN = XMLDocument.getElementsByTagName("name").item(i);
				tempN.getNextSibling().getFirstChild().setNodeValue(money + "");//保存money
				tempN.getNextSibling().getNextSibling().getFirstChild().setNodeValue(ico);
			}
		}
		writeDoc(XMLDocument);
		init(name);
	}
}