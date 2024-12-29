import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class LeafNode extends TreeNode {
    public Byte dataSymbol;
    public String binaryPrefix;

    public LeafNode(Byte dataSymbol, Integer frequencyValue) {
        super(frequencyValue);
        this.dataSymbol = dataSymbol;
    }

    @Override
    public String toString() {
        return this.dataSymbol + "(" + new String(new byte[]{this.dataSymbol}, StandardCharsets.UTF_8) + ")=" + this.binaryPrefix;
    }

    @Override
    public void populateEncodingMap(String codePrefix, HashMap<Byte, String> encodingMap) {
        this.binaryPrefix = codePrefix;
        encodingMap.put(this.dataSymbol, this.binaryPrefix);
    }
}
