package com.xiangsheng.net;

import android.os.Bundle;
import android.os.Message;

import com.xiangsheng.ControlApplication;
import com.xiangsheng.dao.model.WorksName;
import com.xiangsheng.dao.model.WorksSize;

import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


/**
 * Created by wangliang on 2016/12/26.
 */

public class TaskWorksSize extends LoadDataTask {
    private WorksName mWorksName;

    public TaskWorksSize(Message message) {
        Bundle bundle = message.getData();
        mWorksName = (WorksName) bundle.getSerializable("worksName");
        url = mWorksName.getDetailUrl();
    }

    @Override
    public void completeLoad(String str) {
        parserWorksSize(url);
    }

    /**
     * 解析相声作品属性大小
     *
     * @return
     */
    private WorksSize parserWorksSize(String url) {
       WorksSize worksSize = new WorksSize();
        worksSize.setID(mWorksName.getID());
        try {
            Parser htmlParser = new Parser(url);
            htmlParser.setEncoding("GBK");
            // 获取指定的 <div id="wrap"> 节点，即 <div> 标签
            NodeList divWrapOfBody = htmlParser.extractAllNodesThatMatch(
                    new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "wrap")));
            if (divWrapOfBody != null && divWrapOfBody.size() > 0) {
                NodeList nodeList = divWrapOfBody.elementAt(0).getChildren().elementAt(3).getFirstChild().getFirstChild().getChildren().elementAt(1).getChildren().elementAt(3).getChildren().elementAt(4).getChildren().elementAt(5).getChildren().elementAt(3).getChildren().elementAt(3).getChildren().elementAt(1).getChildren().elementAt(3).getChildren().elementAt(1).getChildren();
                String detailUrl = ((LinkTag) nodeList.elementAt(1)).getLink();
                String name = ((LinkTag) nodeList.elementAt(1)).getLinkText();
                String size = (nodeList.elementAt(2)).getText();
                worksSize.setFileLayout(name);
                worksSize.setSize(size);
                worksSize.setDetailUrl(detailUrl);
                ControlApplication.getApplication().getDaoManager().getTableWorksSize().insertData(worksSize);

            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return worksSize;
    }
}
