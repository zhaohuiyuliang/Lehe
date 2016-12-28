package com.xiangsheng.net;

import android.os.Bundle;
import android.os.Message;

import com.xiangsheng.ControlApplication;
import com.xiangsheng.dao.model.Works;
import com.xiangsheng.dao.model.WorksSize;

import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


/**
 * Created by wangliang on 2016/12/27.
 */

public class TaskWorks extends LoadDataTask {
    private WorksSize mWorksSize;

    public TaskWorks(Message message) {
        Bundle bundle = message.getData();
        mWorksSize = (WorksSize) bundle.getSerializable("WorksSize");
        url = mWorksSize.getDetailUrl();
    }

    @Override
    public void completeLoad(String str) {
        parserWorks(url);
    }

    /**
     * 解析相声作品属性大小
     *
     * @return
     */
    private Works parserWorks(String url) {
        Works works = new Works();
        works.setSize(mWorksSize.getSize());
        works.setID(mWorksSize.getID());
        works.setName(mWorksSize.getFileLayout());
        try {
            Parser htmlParser = new Parser(url);
            htmlParser.setEncoding("GBK");
            // 获取指定的 <div id="wrap"> 节点，即 <div> 标签
            NodeList divWrapOfBody = htmlParser.extractAllNodesThatMatch(
                    new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "common")));
            if (divWrapOfBody != null && divWrapOfBody.size() > 0) {
                LinkTag linkTag = (LinkTag) divWrapOfBody.elementAt(0).getChildren().elementAt(5).getChildren().elementAt(1).getChildren().elementAt(7).getChildren().elementAt(1).getChildren().elementAt(1);
                String content = linkTag.getAttribute("onclick");
                String downloadUrl = content.replaceAll("window.open\\('", "").replaceAll("'\\)", "");
                works.setDownloadUrl(downloadUrl);
                ControlApplication.getApplication().getDaoManager().getTableWorks().insertData(works);
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return works;
    }
}
