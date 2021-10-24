package top.xwace.utils;


import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import top.xwace.ObjValue;

public class XmlUtil {
    public ArrayList getXmlPropsByFile(String filePath) {
        return getXmlPropsByFile(filePath, null, null);
    }

    public ArrayList getXmlPropsByFile(String filePath, String PROPSROW_DESC) {
        return getXmlPropsByFile(filePath, PROPSROW_DESC, null);
    }

    public ArrayList getXmlPropsByFile(String filePath, String PROPSROW_DESC, String KEY_DESC) {
        if (!filePath.endsWith(".xml")) filePath += ".xml";


        ArrayList al = new ArrayList();
        try {
            XmlCallback handler = new XmlCallback();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            InputSource src = new InputSource(new FileInputStream(filePath));
            if (PROPSROW_DESC != null)
                handler.setPROPSROW_DESC(PROPSROW_DESC);
            if (KEY_DESC != null)
                handler.setKEY_DESC(KEY_DESC);
            saxParser.parse(src, handler);
            al = handler.getPropsAl();
        } catch (Throwable t) {
            //LogUtil.fine("[XmlUtil]", "[getXmlPropsByFile]", "[Error Exception:"+filePath+"]", t);
            System.err.println("[XmlConfig][Error:get XmlProps From File]" + t);
        }
        return al;
    }

    public ArrayList getXmlPropsByTable() {
        ArrayList al = new ArrayList();
        return al;
    }

    public ArrayList getXmlPropsByObject() {
        ArrayList al = new ArrayList();
        return al;
    }

    public void getXmlFileByTable() {
    }

    public ArrayList getXmlObjectByFile(String filePath) {
        return getXmlObjectByFile(filePath, null, null);
    }

    public ArrayList getXmlObjectByFile(String filePath, String PROPSROW_DESC) {
        return getXmlObjectByFile(filePath, PROPSROW_DESC, null);
    }

    public ArrayList getXmlObjectByFile(String filePath, String PROPSROW_DESC, String KEY_DESC) {
        if (!filePath.endsWith(".xml")) filePath += ".xml";

        ArrayList al = new ArrayList();
        try {
            XmlObjectCallback handler = new XmlObjectCallback();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            //LogUtil.fine(filePath);
            InputSource src = new InputSource(new FileInputStream(filePath));
            //LogUtil.fine(filePath);
            if (PROPSROW_DESC != null)
                handler.setPROPSROW_DESC(PROPSROW_DESC);
            if (KEY_DESC != null)
                handler.setKEY_DESC(KEY_DESC);
            saxParser.parse(src, handler);
            al = handler.getObjAl();
        } catch (Throwable t) {
            System.err.println("[XmlConfig][Error:get XmlObject From File]" + t);
        }
        return al;
    }

    public static void main(String args[]) {
        XmlUtil xu = new XmlUtil();
        ArrayList al = xu.getXmlPropsByFile("config.xml", "PARK");//
        System.out.println(al);
    }
}





class XmlCallback extends DefaultHandler
{
    private boolean textFlag = false;
    private ArrayList propsAl;
    private Properties curProps;
    private String curKey;
    private String PROPSROW_DESC;
    private String KEY_DESC;
    private String curPROPSROW_DESC;
    private String curKEY_DESC;

    public XmlCallback()
    {
    }

    public void startDocument() throws SAXException
    {
        //LogUtil.fine("start parse xml");
    }

    public void startElement(String uri, String sName, String qName, Attributes attrs)
    {
        if(qName.equals("PROPSTABLE"))
        {
            //LogUtil.fine(attrs.getValue("DESC"));
            propsAl = new ArrayList();
        }
        else if(qName.equals("PROPSROW"))
        {
            curPROPSROW_DESC = attrs.getValue("DESC");
            curProps = new Properties();
        }
        else
        {
            //LogUtil.fine(qName);
            curKEY_DESC = attrs.getValue("DESC");
            curKey = qName;
            textFlag = true;
        }
    }

    public void characters(char[] data, int start, int length)
    {
        String content = new String(data, start, length);
        if(textFlag)
        {
            //LogUtil.fine(content);
            if(KEY_DESC==null||(curKEY_DESC!=null&&curKEY_DESC.equals(KEY_DESC)))
                curProps.put(curKey, content.trim());
        }
    }

    public void endElement(String uri, String sName, String qName)
    {

        if(qName.equals("PROPSTABLE"))
        {
        }
        else if(qName.equals("PROPSROW"))
        {
            if(PROPSROW_DESC==null||(curPROPSROW_DESC!=null&&curPROPSROW_DESC.equals(PROPSROW_DESC)))
                propsAl.add(curProps);
        }
        else
        {
            //LogUtil.fine("/"+qName);
            textFlag = false;
        }
    }

    public void endDocument() throws SAXException
    {
        //LogUtil.fine("end parse xml");
    }

    public ArrayList getPropsAl()
    {
        return propsAl;
    }

    public void setPROPSROW_DESC(String PROPSROW_DESC)
    {
        this.PROPSROW_DESC = PROPSROW_DESC;
    }

    public void setKEY_DESC(String KEY_DESC)
    {
        this.KEY_DESC = KEY_DESC;
    }
}



class XmlObjectCallback extends DefaultHandler {
    private boolean textFlag = false;
    private ArrayList objAl;
    private ObjValue curObj;
    private String curKey;
    private String PROPSROW_DESC;
    private String KEY_DESC;
    private String curPROPSROW_DESC;
    private String curKEY_DESC;

    public XmlObjectCallback() {
    }

    public void startDocument() throws SAXException {
        //LogUtil.fine("start parse xml");
    }

    public void startElement(String uri, String sName, String qName, Attributes attrs) {
        if (qName.equals("PROPSTABLE")) {
            //LogUtil.fine(attrs.getValue("DESC"));
            objAl = new ArrayList();
        } else if (qName.equals("PROPSROW")) {
            curPROPSROW_DESC = attrs.getValue("DESC");
            curObj = new ObjValue();
        } else {
            //LogUtil.fine(qName);
            curKEY_DESC = attrs.getValue("DESC");
            curKey = qName;
            textFlag = true;
        }
    }

    public void characters(char[] data, int start, int length) {
        String content = new String(data, start, length);
        if (textFlag) {
            //LogUtil.fine(content);
            if (KEY_DESC == null || (curKEY_DESC != null && curKEY_DESC.equals(KEY_DESC)))
                curObj.setString(curKey, content.trim());
        }
    }

    public void endElement(String uri, String sName, String qName) {

        if (qName.equals("PROPSTABLE")) {
        } else if (qName.equals("PROPSROW")) {
            if (PROPSROW_DESC == null || (curPROPSROW_DESC != null && curPROPSROW_DESC.equals(PROPSROW_DESC)))
                objAl.add(curObj);
        } else {
            //LogUtil.fine("/"+qName);
            textFlag = false;
        }
    }

    public void endDocument() throws SAXException {
        //LogUtil.fine("end parse xml");
    }

    public ArrayList getObjAl() {
        return objAl;
    }

    public void setPROPSROW_DESC(String PROPSROW_DESC) {
        this.PROPSROW_DESC = PROPSROW_DESC;
    }

    public void setKEY_DESC(String KEY_DESC) {
        this.KEY_DESC = KEY_DESC;
    }
}