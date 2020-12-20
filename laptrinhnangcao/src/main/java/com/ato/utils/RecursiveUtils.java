package com.ato.utils;

import com.ato.model.dto.TreeDTO;

import java.util.ArrayList;
import java.util.List;

public class RecursiveUtils {
    public static List<TreeDTO> dequy(List<TreeDTO> list, Long paren){
        List<TreeDTO> arr = new ArrayList<>(  );
        if(list.size()>0){
            for(int i = 0; i < list.size();i++){
                if(list.get( i ).getParenId().equals( paren )){
                    List<TreeDTO> children = dequy( list, list.get( i ).getId());
                    if(children.size()>0){
                        list.get( i ).setChildren( children );
                    }
                    arr.add( list.get( i ) );
                }
            }
        }
        return arr;
    }
}
