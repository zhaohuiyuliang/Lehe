package com.xiangsheng.net;

import android.util.Log;

import com.xiangsheng.dao.TablePeopleProfile;
import com.xiangsheng.dao.model.People;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangliang on 2016/12/23.
 */

public class TaskPeopleProfile extends LoadDataTask {
    private String TAG = "TaskPeopleProfile";

    public TaskPeopleProfile() {
        url = "http://www.tingcd.com/";
    }

    @Override
    public void run() {
        List<People> spellingList = parserPeopleProfile();
        TablePeopleProfile tablePyRead = getAppliction().getDaoManager().getTablePeopleProfile();
        tablePyRead.batchInsertData(spellingList);
    }

    @Override
    public void completeLoad(String str) {

    }

    /**
     * 解析人物属性
     *
     * @return
     */
    private List<People> parserPeopleProfile() {
        List<People> spellingList = new ArrayList<>();
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
                Node nodeContentChi = nodeContentDivWrapOfBody.getChildren().elementAt(0);

                NodeList divTableList = nodeContentChi.getChildren().elementAt(14).getChildren().elementAt(5).getChildren();

                for (int i = 1; i < divTableList.size(); i += 4) {
                    NodeList rowTable = divTableList.elementAt(i).getChildren();
                    for (int k = 0; k < rowTable.size(); k++) {
                        Node rowTableName = rowTable.elementAt(k);
                        if (rowTableName.getChildren() == null) {
                            continue;
                        }
                        Node peopleHeadNode = rowTableName.getChildren().elementAt(1);
                        String detailUrl = ((LinkTag) peopleHeadNode).getLink();
                        String headUrl = ((ImageTag) peopleHeadNode.getFirstChild()).getAttribute("src");

                        String peopleName = ((LinkTag) rowTable.elementAt(k).getChildren().elementAt(4).getFirstChild()).getLinkText();

                        People people = new People();

                        people.setHeadUrl(headUrl);
                        people.setName(peopleName);
                        people.setDetailUrl(detailUrl);

                        spellingList.add(people);
                        Log.d(TAG, people.getName() +
                                " " + people.getHeadUrl() + " " + people.getDetailUrl());
                    }
                }

            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return spellingList;
    }
}
