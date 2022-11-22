package ucunix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import uculang.UcuType;

public class Filesystem {
    private final String root;
    
    public class UcunixFile extends UcuType {
        private final Permissions permissions;
        private final Path filePath;
        
        public UcunixFile(Permissions permissions, Path filePath) {
            this.permissions = permissions;
            this.filePath = filePath;
        }

        public Permissions getPermissions() {
            return permissions;
        }

        public String getContent() throws IOException {
            return Files.readString(filePath);
        }
        
        public String getName() {
            return filePath.getFileName().toString();
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            
            if (other instanceof UcunixFile f) {
                return filePath.equals(f);
            }
            
            return false;
        }

        @Override
        public int hashCode() {
            return filePath.hashCode();
        }

        @Override
        public String toString() {
            return "File(" + filePath.toString() + ")";
        }
    }
    
    public Filesystem(Path root) {
        assert root.toFile().isDirectory();
        try {
            this.root = root.toFile().getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException("FIXME");
        }
    }

    public List<UcunixFile> listFiles(User user) throws IOException {
        return Files.list(Path.of(root))
                    .map(path -> {
                        try {
                            return new UcunixFile(
                                Permissions.fromMetadataFile(Path.of(path + ".meta")), 
                                path
                            );
                        } catch (IOException ex) {
                            // No se pudo leer el archivo de metadatos.
                            return null;
                        }
                    })
                    .filter(x -> x != null)
                    .toList();
    }

    public UcunixFile open(User user, String filename) throws IOException {
        // FIXME: Verificar que root/filename no se sale de "root/"
        return new UcunixFile(
            Permissions.fromMetadataFile(Path.of(root, filename + ".meta")), 
            Path.of(root, filename)
        );
    }
}
