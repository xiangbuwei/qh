package com.xl.qh.method;

import com.xl.qh.bean.Entity;
import sun.applet.Main;

import javax.swing.plaf.synth.SynthSpinnerUI;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author xiangliang
 * @create 2019-08-28 14:38
 */
public class CycleConverts {

    public static List<Entity> M5ToM15(List<Entity> list){
        Set<String> ends = new HashSet<>();
        ends.add("05");
        ends.add("20");
        ends.add("35");
        ends.add("50");
        List<Entity> result = new ArrayList<>();
        /*for(int i=1; i<list.size(); i++){
            Entity e = list.get(i);
            if(isFrist(e, ends)i){

            }
        }*/
        int start = 0;
        Stream.iterate(0, f->f+1).limit(100).forEach(i -> System.out.println(i));
        //System.out.println(entity);
        return result;
    }

    private static boolean isFrist(Entity entity, Set<String> ends){
        String time = entity.getString("time");
        String minth = time.substring(time.length()-2);
        return ends.contains(minth);
    }

    public static void main(String[] args) {
        List<Entity> list = new ArrayList<>();
        Entity e1 = new Entity();
        e1.put("time", 910);
        Entity e2 = new Entity();
        e2.put("time", 915);
        Entity e3 = new Entity();
        e3.put("time", 920);
        list.add(e1);
        list.add(e2);
        list.add(e3);

        M5ToM15(list);
    }
}
