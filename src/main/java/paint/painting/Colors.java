package charades.painting;

import javafx.scene.paint.Color;

// Enum constants are serialized differently than ordinary serializable or externalizable objects.
// The serialized form of an enum constant consists solely of its name; field values of the constant are not present in the form.
// To serialize an enum constant, ObjectOutputStream writes the value returned by the enum constant's name method.
// To deserialize an enum constant, ObjectInputStream reads the constant name from the stream; the deserialized constant is then obtained by calling the java.lang.Enum.valueOf method, passing the constant's enum type along with the received constant name as arguments.
// Like other serializable or externalizable objects, enum constants can function as the targets of back references appearing subsequently in the serialization stream.
//
// The process by which enum constants are serialized cannot be customized: any class-specific writeObject, readObject, readObjectNoData, writeReplace, and readResolve methods defined by enum types are ignored during serialization and deserialization.
// Similarly, any serialPersistentFields or serialVersionUID field declarations are also ignored--all enum types have a fixed serialVersionUID of 0L. Documenting serializable fields and data for enum types is unnecessary, since there is no variation in the type of data sent.
public enum Colors{
    black(Color.BLACK),
    white(Color.WHITE),
    grey(Color.GRAY),
    green(Color.GREEN),
    blue(Color.BLUE),
    pink(Color.PINK),
    turquoise(Color.TURQUOISE),
    yellow(Color.YELLOW),
    red(Color.RED);

    private final Color color;

    Colors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
