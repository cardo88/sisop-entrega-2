package com.sisop.sisop.OS.Sdk.SharedQueue;

import java.util.ArrayList;

import com.sisop.sisop.UcuLang.Types.UcuAppendOp;
import com.sisop.sisop.UcuLang.Types.UcuLengthOp;
import com.sisop.sisop.UcuLang.Types.UcuType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UcunixSharedQueue implements UcuType, 
                                          UcuAppendOp,
                                          UcuLengthOp {
    
    private static final Map<String, UcunixSharedQueue> queues = new HashMap<>();

    private final ArrayList<UcuType> queue;
    private int insertIndex;
    private int readIndex;
    private int elementCount;

    public static UcunixSharedQueue create(String name, int capacity) {
        if (!queues.containsKey(name)) {
            queues.put(name, new UcunixSharedQueue(capacity));
        }
        return queues.get(name);
    }
    
    private UcunixSharedQueue(int capacity) {
        queue = new ArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            queue.add(null);
        }

        insertIndex = 0;
        readIndex = 0;
        elementCount = 0;
    }

    public static List<UcunixSharedQueue> getAll() {
        return queues.values().stream().toList();
    }

    public UcuType pop() {
        if (elementCount > 0) {
            var value = queue.get(readIndex++);
            readIndex %= queue.size();
            elementCount--;
            return value;
        } else {
            throw new RuntimeException("FIXME");
        }
    }

    @Override
    public String toString() {
        return "SharedQueue";
    }

    @Override
    public UcuType append(UcuType other) {
        if (elementCount < queue.size()) {
            queue.set(insertIndex++, other);
            insertIndex %= queue.size();
            elementCount++;
        } else {
            throw new RuntimeException("FIXME");
        }
        return this;
    }

    @Override
    public UcuType duplicate() {
        return this;
    }

    @Override
    public int length() {
        return elementCount;
    }
    
    public UcuType get(int index) {
        return queue.get((readIndex + index) % queue.size());
    }

    @Override
    public UcuType copy() {
        throw new RuntimeException("FIXME");
    }
}
