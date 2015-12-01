package com.swami.vidyanand.data;

/**
 * Created by Rajan_Phatak on 10/11/2015.
 */
public class IndexItem extends IndexCommon {

    private String content ;
    private IndexGroup group;
    public IndexItem(String uniqueId, String title, String description, String imagePath,String content, IndexGroup group ){
        super(uniqueId,title,description,imagePath);
        this.content = content ;
        this.group = group;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }
    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the group
     */
    public IndexGroup getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(IndexGroup group) {
        this.group = group;
    }

}
