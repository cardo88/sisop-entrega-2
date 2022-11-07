package com.sisop.sisop.UcuLang.Types;

import java.util.LinkedList;
import java.util.List;

public class UcuList implements UcuType, 
                                UcuLengthOp,
                                UcuAppendOp,
                                UcuGetOp,
                                UcuSetOp {
   
    private final List<UcuType> list;

    public UcuList() {
        this.list = new LinkedList<>();
    }

    public UcuList(List<UcuType> data) {
        this.list = new LinkedList<>(data);
    }
    
    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public int length() {
        return list.size();
    }

    @Override
    public UcuType duplicate() {
        return this;
    }

    @Override
    public UcuType append(UcuType other) {
        list.add(other);
        return this;
    }

    @Override
    public UcuType copy() {
        return new UcuList(list);
    }

    @Override
    public UcuType get(UcuType key) {
        if (key instanceof UcuNumber index) {
            return list.get(index.intValue());
        } else {
            throw new RuntimeException("FIXME");
        }
    }

    @Override
    public UcuType set(UcuType key, UcuType value) {
        if (key instanceof UcuNumber index) {
            return list.set(index.intValue(), value);
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
