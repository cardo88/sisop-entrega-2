package com.sisop.sisop.UcuLang;

import java.util.ArrayList;

public class UcuList {
    public ArrayList<UcuValue> value;

    public UcuList() {
        value = new ArrayList<>();
    }

    public UcuList(ArrayList<UcuValue> v) {
        value = v;
    }

    public UcuList deepCopy() {
        var result = new UcuList();
        for (var x : value) {
            result.value.add(x.deepCopy());
        }
        return result;
    }

    public UcuList append(UcuValue o) {
        value.add(o);
        return this;
    }

    public int length() {
        return value.size();
    }

    public UcuValue get(int index) {
        return value.get(index);
    }

    public void set(int index, UcuValue v) {
        value.set(index, v);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        return value.equals(o);
    }
}
