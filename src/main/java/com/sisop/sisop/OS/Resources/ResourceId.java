package com.sisop.sisop.OS.Resources;

/**
 *
 */
public class ResourceId {
    // FIXME: Esto requiere de sincronización en un contexto multihilo
    private static long nextId = 1;

    private final long id;

    public ResourceId() {
        id = nextId;
        nextId++;
    }

    public long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
