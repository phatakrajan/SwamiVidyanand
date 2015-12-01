package com.swami.vidyanand.data;

import java.util.ArrayList;

/**
 * Created by Rajan_Phatak on 10/11/2015.
 */
public class IndexDataSource {

    private static IndexDataSource indexDataSrc = new IndexDataSource();

    private ArrayList<IndexGroup> groups = new ArrayList<IndexGroup>();

    /**
     * @return the groups
     */
    public ArrayList<IndexGroup> getGroups() {
        return groups;
    }

    public static IndexGroup GetGroup (String id){
        for(IndexGroup group : indexDataSrc.getGroups()){
            if(group.get_uniqueId() != null && group.get_uniqueId().contains(id)){
                return group ;
            }
        }
        return null;
    }

    public static IndexItem GetItem (IndexGroup group, String id){
        for(IndexItem item : group.getItems()){
            if(item.get_title() != null && item.get_title().contains(id)){
                return item ;
            }
        }
        return null;
    }

    public IndexDataSource(){
        IndexGroup grpAshram = new IndexGroup(Constants.ID_ASHRAM,
                Constants.ASHRAM,
                "Self cure will provide you with description of problem, its causes, possible symptoms, things you can can do to avoid it, and also most importantly things which you can do to cure it easily at home. ",
                Constants.PIC_ASHRAM
        );

        IndexGroup grpMataji = new IndexGroup(Constants.ID_MATAJI,
                Constants.MATAJI,
                "There are certain health problems which are seasonal. In order to fight against such seasonal problems one needs to be careful and follow simple steps to stay healthy and enjoy the season.",
                Constants.PIC_MATAJI);

        IndexGroup grpParampara = new IndexGroup(Constants.ID_PARAMPARA,
                Constants.PARAMPARA,
                "What would you do when there is emergency and medical help is taking too long to come by? Don't worry. This section describes all medical help which you could require in case of emergency.",
                Constants.PIC_PARAMPARA);

        IndexGroup grpParichay = new IndexGroup(Constants.ID_PARICHAY,
                Constants.PARICHAY,
                "We all have followed our granny's tips sometimes in our lifetime and might have found it to be better than medicines at times. So here are all those tips which can be easily followed at home without having any side effects.",
                Constants.PIC_PARICHAY);

        IndexGroup grpPrabodhan = new IndexGroup(Constants.ID_PRABODHAN,
                Constants.PRABODHAN,
                "In case you cannot find help for your problems from the solution we have provided then you have an option to contact our doctors team. You can describe your problem to us and we will get back to you.",
                Constants.PIC_PRABODHAN);

        IndexGroup grpSadhana = new IndexGroup(Constants.ID_SADHANA,
                Constants.SADHANA,
                "All the doctors, pharmacies and hospitals are listed below. Get the nearest help you require.",
                Constants.PIC_SADHANA);

        IndexGroup grpSuvichar = new IndexGroup(Constants.ID_SUVICHAR,
                Constants.SUVICHAR,
                "All the doctors, pharmacies and hospitals are listed below. Get the nearest help you require.",
                Constants.PIC_SUVICHAR);

        IndexGroup grpGranthalay = new IndexGroup(Constants.ID_GRANTHALAY,
                Constants.GRANTHALAY,
                "All the doctors, pharmacies and hospitals are listed below. Get the nearest help you require.",
                Constants.PIC_GRANTHALAY);

        IndexGroup grpSampark = new IndexGroup(Constants.ID_SAMPARK,
                Constants.SAMPARK,
                "All the doctors, pharmacies and hospitals are listed below. Get the nearest help you require.",
                Constants.PIC_SAMPARK);

        this.groups.add(grpParampara);
        this.groups.add(grpSuvichar);
        this.groups.add(grpPrabodhan);
        this.groups.add(grpAshram);
        this.groups.add(grpSadhana);
        this.groups.add(grpMataji);
        this.groups.add(grpParichay);
        this.groups.add(grpGranthalay);
        this.groups.add(grpSampark);
    }

}
