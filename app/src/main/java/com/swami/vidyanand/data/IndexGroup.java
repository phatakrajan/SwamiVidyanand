package com.swami.vidyanand.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajan_Phatak on 10/11/2015.
 */
public class IndexGroup extends IndexCommon {

    private List<IndexItem> items = new ArrayList<IndexItem>();

    public IndexGroup(String uniqueId, String title, String description, String imagePath) {
        super(uniqueId,title,description,imagePath);
    }

    /**
     * @return the items
     */
    public List<IndexItem> getItems() {
        return items;
    }

}
