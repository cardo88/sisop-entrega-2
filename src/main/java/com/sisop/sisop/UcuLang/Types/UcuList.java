package com.sisop.sisop.UcuLang.Types;

import java.util.LinkedList;
import java.util.List;

public class UcuList implements UcuType, 
                                UcuLengthOp,
                                UcuAppendOp,
                                UcuGetOp,
                                UcuSetOp,
                                UcuAssignOp {
   
    private List<UcuType> value;

    public UcuList() {
        this.value = new LinkedList<>();
    }

    public UcuList(List<UcuType> data) {
        this.value = new LinkedList<>(data);
    }
    
    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int length() {
        return value.size();
    }

    @Override
    public UcuType duplicate() {
        return this;
    }

    @Override
    public UcuType append(UcuType other) {
        value.add(other);
        return this;
    }

    @Override
    public UcuType copy() {
        return new UcuList(value);
    }

    @Override
    public UcuType get(UcuType key) {
        if (key instanceof UcuNumber index) {
            return value.get(index.intValue());
        } else {
            throw new RuntimeException("FIXME");
        }
    }

    @Override
    public UcuType set(UcuType key, UcuType object) {
        if (key instanceof UcuNumber index) {
            return value.set(index.intValue(), object);
        } else {
            throw new RuntimeException("FIXME");
        }
    }

    @Override
    public UcuType assign(UcuType other) {
        if (other instanceof UcuList o) {
            value = o.value;
            return this;
        } else {
            throw new RuntimeException("FIXME");
        }
    }
}
