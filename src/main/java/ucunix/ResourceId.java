package ucunix;

/**
 *
 */
public class ResourceId {
    // FIXME: Esto requiere de sincronización en un contexto multihilo
    private static long nextId = 1;

    private final long id;

    public ResourceId() {
        this.id = nextId;

        nextId++;
    }

    public long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ResourceId other = (ResourceId) obj;
        return this.id == other.id;
    }
}
