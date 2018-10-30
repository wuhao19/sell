package com.wuhao.sell.util;
import java.util.ArrayList;
import java.util.List;

public class Comme {
    public static List<Integer> removeDuplicate(List<Integer> list){
        List<Integer> listTemp = new ArrayList();
        for(int i=0;i<list.size();i++){
            if(!listTemp.contains(list.get(i))){
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }
}
