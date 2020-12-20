package com.ato.model.baseModel;

import java.util.List;
import java.util.concurrent.Callable;

public abstract class Task<E> implements Callable<Integer> {
    private List<E> items;
    public void setItems(List<E> items){
        this.items = items;
    }
    public List<E> getItems(){
        return items;
    }
}