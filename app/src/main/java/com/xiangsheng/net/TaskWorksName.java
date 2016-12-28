package com.xiangsheng.net;

import android.os.Bundle;
import android.os.Message;

import com.xiangsheng.dao.TableWorksName;
import com.xiangsheng.dao.model.People;
import com.xiangsheng.dao.model.WorksName;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangliang on 2016/12/26.
 */

public class TaskWorksName extends LoadDataTask {
    private People mPeople;

    public TaskWorksName(Message message) {
        Bundle bundle = message.getData();
        mPeople = (People) bundle.getSerializable("people");
        url = mPeople.getDetailUrl();
    }

    @Override
    public void completeLoad(String str) {
        List<WorksName> workses = new ArrayList<>();
        List<WorksName> list = parserWorksName(url, workses);
        TableWorksName tablePyRead = getAppliction().getDaoManager().getTableWorksName();
        tablePyRead.batchInsertData(list);
    }

    /**
     * 解析相声作品名称
     *
     * @return
     */
    private List<WorksName> parserWorksName(String url, List<WorksName> workses) {
        try {
            Parser htmlParser = new Parser(url);
            htmlParser.setEncoding("GBK");
            // 获取指定的 <div id="wrap"> 节点，即 <div> 标签
            NodeList divWrapOfBody = htmlParser.extractAllNodesThatMatch(
                    new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "wrap")));
            if (divWrapOfBody != null && divWrapOfBody.size() > 0) {
                // 获取指定 <div class="main"> 标签
                Node nodeMainDivWrapOfBody = divWrapOfBody.elementAt(0);
                Node nodeContentDivWrapOfBody = nodeMainDivWrapOfBody.getChildren().elementAt(1);
                Node nodeContentChi = nodeContentDivWrapOfBody.getChildren().elementAt(1);
                NodeList tableList = nodeContentChi.getChildren().elementAt(9).getChildren().elementAt(1).getChildren().elementAt(5).getChildren();
                for (int i = 25; i < tableList.size(); i += 5) {
                    WorksName worksName = new WorksName();
                    LinkTag rowNode = (LinkTag) tableList.elementAt(i).getChildren().elementAt(5).getChildren().elementAt(8).getFirstChild();
                    worksName.setDetailUrl(rowNode.getLink());
                    worksName.setName(rowNode.getLinkText());
                    worksName.setAuthorID(mPeople.getID());
                    workses.add(worksName);
                }
                NodeList pagerList = nodeContentChi.getChildren().elementAt(11).getChildren().elementAt(1).getChildren();
                for (int k = 3; k < pagerList.size(); k++) {
                    LinkTag linkTag = (LinkTag) pagerList.elementAt(k);
                    if ("下一页".compareTo(linkTag.getLinkText()) == 0) {
                        break;
                    }

                    Parser htmlParser2 = new Parser(linkTag.getLink());
                    htmlParser2.setEncoding("GBK");
                    // 获取指定的 <div id="wrap"> 节点，即 <div> 标签
                    NodeList divWrapOfBody2 = htmlParser2.extractAllNodesThatMatch(
                            new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "wrap")));
                    if (divWrapOfBody2 != null && divWrapOfBody2.size() > 0) {
                        // 获取指定 <div class="main"> 标签
                        Node nodeMainDivWrapOfBody2 = divWrapOfBody2.elementAt(0);
                        Node nodeContentDivWrapOfBody2 = nodeMainDivWrapOfBody2.getChildren().elementAt(1);
                        Node nodeContentChi2 = nodeContentDivWrapOfBody2.getChildren().elementAt(1);
                        NodeList tableList2 = nodeContentChi2.getChildren().elementAt(9).getChildren().elementAt(1).getChildren().elementAt(5).getChildren();
                        for (int i = 9; i < tableList2.size(); i += 5) {
                            WorksName worksName = new WorksName();
                            LinkTag rowNode = (LinkTag) tableList2.elementAt(i).getChildren().elementAt(5).getChildren().elementAt(8).getFirstChild();
                            worksName.setDetailUrl(rowNode.getLink());
                            worksName.setName(rowNode.getLinkText());
                            worksName.setAuthorID(mPeople.getID());
                            workses.add(worksName);
                        }
                    }
                }
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return workses;
    }


}
