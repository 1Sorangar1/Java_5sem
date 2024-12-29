import java.util.HashMap;

public class TreeNode implements Comparable<TreeNode> {
    public Integer frequencyValue;
    public TreeNode leftChild;
    public TreeNode rightChild;

    public TreeNode(Integer frequencyValue) {
        this.frequencyValue = frequencyValue;
    }

    @Override
    public int compareTo(TreeNode otherNode) {
        return this.frequencyValue.compareTo(otherNode.frequencyValue);
    }

    @Override
    public String toString() {
        return "Frequency=" + this.frequencyValue;
    }

    public void populateEncodingMap(String codePrefix, HashMap<Byte, String> encodingMap) {
        if (this.leftChild != null) {
            this.leftChild.populateEncodingMap(codePrefix + "0", encodingMap);
        }
        if (this.rightChild != null) {
            this.rightChild.populateEncodingMap(codePrefix + "1", encodingMap);
        }
    }
}
