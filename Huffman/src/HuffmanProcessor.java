import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanProcessor {
    HashMap<Byte, String> encodingMap = new HashMap<>();

    public byte[] compress(byte[] inputData) {
        HashMap<Byte, Integer> frequencyMap = calculateFrequencyMap(inputData);
        PriorityQueue<TreeNode> priorityQueue = buildPriorityQueue(frequencyMap);
        TreeNode root = constructHuffmanTree(priorityQueue);

        root.populateEncodingMap("", this.encodingMap);

        String binaryEncodedData = encodeToBinary(inputData);
        binaryEncodedData = addPadding(binaryEncodedData);

        return convertBinaryToBytes(binaryEncodedData);
    }


    public byte[] decompress(String encodedBinaryData, HashMap<String, Byte> decodingTable) {
        ArrayList<Byte> decodedBytes = new ArrayList<>();
        StringBuilder tempBinarySequence = new StringBuilder();

        for (int i = 0; i < encodedBinaryData.length(); i++) {
            tempBinarySequence.append(encodedBinaryData.charAt(i));

            if (decodingTable.containsKey(tempBinarySequence.toString())) {
                decodedBytes.add(decodingTable.get(tempBinarySequence.toString()));
                tempBinarySequence.setLength(0);
            }
        }

        return convertByteListToArray(decodedBytes);
    }

    private String addPadding(String binaryData) {
        int paddingLength = 8 - binaryData.length() % 8;

        for (int i = 0; i < paddingLength; i++) {
            binaryData += "0";
        }

        return String.format("%8s", Integer.toBinaryString(paddingLength & 0xff)).replace(" ", "0") + binaryData;
    }

    private TreeNode constructHuffmanTree(PriorityQueue<TreeNode> nodeQueue) {
        // Handle case with only one unique symbol
        if (nodeQueue.size() == 1) {
            TreeNode singleNode = nodeQueue.poll();
            TreeNode dummyParent = new TreeNode(singleNode.frequencyValue);
            dummyParent.leftChild = singleNode;
            nodeQueue.add(dummyParent);
        }

        while (nodeQueue.size() > 1) {
            TreeNode leftNode = nodeQueue.poll();
            TreeNode rightNode = nodeQueue.poll();

            TreeNode mergedNode = new TreeNode(leftNode.frequencyValue + rightNode.frequencyValue);
            mergedNode.leftChild = leftNode;
            mergedNode.rightChild = rightNode;

            nodeQueue.add(mergedNode);
        }

        return nodeQueue.poll();
    }


    private PriorityQueue<TreeNode> buildPriorityQueue(HashMap<Byte, Integer> frequencyTable) {
        PriorityQueue<TreeNode> queue = new PriorityQueue<>();

        for (Map.Entry<Byte, Integer> entry : frequencyTable.entrySet()) {
            Byte dataByte = entry.getKey();
            Integer frequency = entry.getValue();
            TreeNode leafNode = new LeafNode(dataByte, frequency);

            queue.add(leafNode);
        }

        return queue;
    }

    private HashMap<Byte, Integer> calculateFrequencyMap(byte[] inputData) {
        HashMap<Byte, Integer> frequencyMap = new HashMap<>();

        for (byte b : inputData) {
            frequencyMap.merge(b, 1, Integer::sum);
        }

        return frequencyMap;
    }

    private String encodeToBinary(byte[] inputData) {
        StringBuilder binaryRepresentation = new StringBuilder();

        for (byte b : inputData) {
            binaryRepresentation.append(encodingMap.get(b));
        }

        return binaryRepresentation.toString();
    }

    private byte[] convertBinaryToBytes(String binaryString) {
        byte[] byteArray = new byte[binaryString.length() / 8];

        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (byte) Integer.parseInt(
                    binaryString.substring(i * 8, (i + 1) * 8), 2
            );
        }

        return byteArray;
    }

    private byte[] convertByteListToArray(ArrayList<Byte> byteList) {
        byte[] byteArray = new byte[byteList.size()];
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = byteList.get(i);
        }
        return byteArray;
    }
}
