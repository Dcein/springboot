package dcein.springboot.demo.utils;

import dcein.springboot.demo.vo.NotifyTO;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.net.URL;
import java.util.*;


/** 
 * @author 作者 E-mail: xdzhao@wiz-tech.com.cn
 * @version 创建时间：2016年11月30日 上午9:48:26
 * 解析notify.xml 工具类  
 */
public class NotifyXmlUtil {
	
	//任务通知map集合
	private  static Map<String, NotifyTO> notityMap = new HashMap<String, NotifyTO>();
	
	public static void resolveNotify() {
		Document doc;
		
		try {
			URL url = NotifyXmlUtil.class.getResource("/");
			SAXReader sax=new SAXReader(); //创建解析器
			doc=sax.read(new File(url.getPath()+"notify.xml")); //创建对应的Document对象
			Element taskbase=doc.getRootElement(); //获取XML文档的根节点对象
			Iterator<Element> itTaskbase = taskbase.elementIterator();
			//遍历taskbase节点下的所有用子节点
			while(itTaskbase.hasNext()){
				Element task = itTaskbase.next();//解读到 param位置
				String taskName = task.attribute("name").getText();
				Iterator<Element> itTask = task.elementIterator();
				while(itTask.hasNext()){
					Element work = itTask.next();
					String key = work.attribute("name").getText();
					NotifyTO notifyTO = new NotifyTO();
					
					List<String> emailList = new ArrayList<String>();
					List<String> msgList = new ArrayList<String>();
					List<String> wechatList = new ArrayList<String>();
					
					Iterator<Element> itWork = work.elementIterator();
					while(itWork.hasNext()){
						Element info = itWork.next();
//						String infoName1 = info.attribute("name").getText();
						String email = info.attributeValue("email");
						String msg = info.attributeValue("msg");
						String wechat = info.attributeValue("wechat");
						
						if(email != null){
							emailList.add(email);
						}
						if(msg != null){
							msgList.add(msg);
						}
						if(wechat != null){
							wechatList.add(wechat);
						}
						
//						System.out.println("key="+key+"^^^^"+email+"&&&&"+msg+"****"+wechat);
						
					}
					notifyTO.setEmailList(emailList);
					notifyTO.setPhoneList(msgList);
					notifyTO.setWechatList(wechatList);
					
					notityMap.put(key, notifyTO);
					System.out.println(notityMap);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * 初始化得到任务通知模板
	 * @param key
	 * @return
	 */
	public static NotifyTO getNotifyMap(String key){
		NotifyTO notifyTO = notityMap.get(key);
		return notifyTO;
	}
	
	public static void main(String[] args) throws Exception{
		NotifyXmlUtil.resolveNotify();
	}
}
