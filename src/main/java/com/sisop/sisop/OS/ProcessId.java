package com.sisop.sisop.OS;

/**
 *
 */
public class ProcessId {
    // FIXME: Esto requiere de sincronización en un contexto multihilo
    private static long nextId = 1;

    private final long id;

    public ProcessId() {
        id = nextId;
        nextId++;
    }

    public long getId() {
        return id;
    }
}
