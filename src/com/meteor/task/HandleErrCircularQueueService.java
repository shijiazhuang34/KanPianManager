package com.meteor.task;

import com.meteor.model.po.errpage;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Meteor on 2015/9/4.
 *
 * @category (这里用一句话描述这个类的作用)
 */
public class HandleErrCircularQueueService {
    private static ConcurrentLinkedQueue<errpage> linkedlist = new ConcurrentLinkedQueue<errpage>();

    public static void add(List<errpage> list) {
        if (list != null && list.size()>0) {
            linkedlist.addAll(list);
        }
    }

    public static boolean isEmpty(){
        return linkedlist.isEmpty();
    }

    public static ConcurrentLinkedQueue<errpage>  getList(){
        return linkedlist;
    }
}
