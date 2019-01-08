package com.zhuang.data.mybatis.util;

import com.zhuang.data.util.ResourcesUtils;
import com.zhuang.data.util.XmlUtils;
import org.apache.ibatis.io.Resources;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by zhuang on 4/18/2018.
 */
public class MyBatisConfigUtils {

    public static void resolveMapperLocations(String configFile) throws Exception {
        resolveMapperLocations(Resources.getResourceAsStream(configFile));
    }

    public static void resolveMapperLocations(InputStream inputStream) throws Exception {
        Document document = XmlUtils.toDocument(inputStream);
        NodeList mapperList = document.getElementsByTagName("mapper");
        List<String> wildcardResourceValues = new ArrayList<>();
        List<Node> wildcardResourceNodes = new ArrayList<>();
        for (int i = 0; i < mapperList.getLength(); i++) {
            String resourceValue = XmlUtils.getAttributeValue(mapperList.item(i), "resource");
            if (resourceValue.endsWith("**")) {
                wildcardResourceValues.add(resourceValue);
                wildcardResourceNodes.add(mapperList.item(i));
            }
        }
        Node mappersNode = document.getElementsByTagName(("mappers")).item(0);
        for (Node node : wildcardResourceNodes) {
            mappersNode.removeChild(node);
        }
        String wildcardMergeValue = "";
        for (String wildcardResource : wildcardResourceValues) {
        }
        Pattern pattern = Pattern.compile(".*" + wildcardMergeValue + ".*xml$");
        Collection<String> list = ResourcesUtils.getResources(pattern);
        String xmlStr = XmlUtils.toString(document);
        System.out.println(xmlStr);
    }
}
