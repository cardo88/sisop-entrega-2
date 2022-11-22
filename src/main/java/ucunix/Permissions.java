package ucunix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 */
public class Permissions {
    private final String owner;
    private final String group;

    private final boolean ownerRead;
    private final boolean ownerWrite;
    private final boolean ownerExecute;

    private final boolean groupRead;
    private final boolean groupWrite;
    private final boolean groupExecute;

    private final boolean otherRead;
    private final boolean otherWrite;
    private final boolean otherExecute;

    public Permissions(
        String owner, 
        String group,
        boolean ownerRead,
        boolean ownerWrite,
        boolean ownerExecute,
        boolean groupRead,
        boolean groupWrite,
        boolean groupExecute,
        boolean otherRead,
        boolean otherWrite,
        boolean otherExecute
    ) {
        this.owner = owner;
        this.group = group;
        this.ownerRead = ownerRead;
        this.ownerWrite = ownerWrite;
        this.ownerExecute = ownerExecute;
        this.groupRead = groupRead;
        this.groupWrite = groupWrite;
        this.groupExecute = groupExecute;
        this.otherRead = otherRead;
        this.otherWrite = otherWrite;
        this.otherExecute = otherExecute;
    }
    
    /**
     * Lee los permisos a partir de una cadena
     * 
     * Ejemplo:
     *    nombreUsuario nombreGrupo rwx r-- r-x 
     * 
     * @param content
     * @return
     */
    public static Permissions fromString(String content) {
        var parts = content.split(" ");

        String owner = parts[0];
        String group = parts[1];
        boolean ownerRead = parts[2].charAt(0) == 'r';
        boolean ownerWrite = parts[2].charAt(1) == 'w';
        boolean ownerExecute = parts[2].charAt(2) == 'x';

        boolean groupRead = parts[3].charAt(0) == 'r';
        boolean groupWrite = parts[3].charAt(1) == 'w';
        boolean groupExecute = parts[3].charAt(2) == 'x';

        boolean otherRead = parts[4].charAt(0) == 'r';
        boolean otherWrite = parts[4].charAt(1) == 'w';
        boolean otherExecute = parts[4].charAt(2) == 'x';

        return new Permissions(
            owner, 
            group, 
            ownerRead, ownerWrite, ownerExecute, 
            groupRead, groupWrite, groupExecute, 
            otherRead, otherWrite, otherExecute
        );
    }
    
    public static Permissions fromMetadataFile(Path path) throws IOException {
        String content = Files.readString(path);
        return Permissions.fromString(content);
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();

        builder.append(owner);
        builder.append(" ");
        builder.append(group);
        builder.append(" ");

        builder.append(ownerRead ? "r" : "-");
        builder.append(ownerWrite ? "w" : "-");
        builder.append(ownerExecute ? "x" : "-");
        builder.append(" ");

        builder.append(groupRead ? "r" : "-");
        builder.append(groupWrite ? "w" : "-");
        builder.append(groupExecute ? "x" : "-");
        builder.append(" ");

        builder.append(otherRead ? "r" : "-");
        builder.append(otherWrite ? "w" : "-");
        builder.append(otherExecute ? "x" : "-");

        return builder.toString();
    }

    public String getOwner() {
        return owner;
    }

    public String getGroup() {
        return group;
    }

    public boolean canOwnerRead() {
        return this.ownerRead;
    }

    public boolean canOwnerWrite() {
        return this.ownerWrite;
    }

    public boolean canOwnerExecute() {
        return this.ownerExecute;
    }

    public boolean canGroupRead() {
        return this.groupRead;
    }

    public boolean canGroupWrite() {
        return this.groupWrite;
    }

    public boolean canGroupExecute() {
        return this.groupExecute;
    }

    public boolean canOtherRead() {
        return this.otherRead;
    }

    public boolean canOtherWrite() {
        return this.otherWrite;
    }

    public boolean canOtherExecute() {
        return this.otherExecute;
    }
    
    public boolean canRead(User user) {
        if (user.getUserName().equals(owner)) {
            return canOwnerRead();
        }
        
        if (user.getGroups().stream().anyMatch(x -> x.equals(group))) {
            return canGroupRead();
        }
        
        return canOtherRead();
    }
    
    
    public boolean canWrite(User user) {
        if (user.getUserName().equals(owner)) {
            return canOwnerWrite();
        }
        
        if (user.getGroups().stream().anyMatch(x -> x.equals(group))) {
            return canGroupWrite();
        }
        
        return canOtherWrite();
    }
    
    public boolean canExecute(User user) {
        if (user.getUserName().equals(owner)) {
            return canOwnerExecute();
        }
        
        if (user.getGroups().stream().anyMatch(x -> x.equals(group))) {
            return canGroupExecute();
        }
        
        return canOtherExecute();
    }
}

