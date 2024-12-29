import java.io.File;

public class HuffmanApp {
    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Invalid usage. Expected format:");
            System.out.println("java HuffmanApp <encode/decode/info> <file_path>");
            return;
        }

        HuffmanProcessor processor = new HuffmanProcessor();

        String[] commands = {"encode", "decode", "info"};
        String operation = args[0];
        String filePath = args[1];

        switch (operation) {
            case "decode": {
                ArchiveDetails archive = FileHandler.loadArchive(filePath);
                byte[] decompressedData = processor.decompress(archive.encodedData, archive.decodingMap);
                FileHandler.saveFile(decompressedData, archive.sourceFileName);
                break;
            }

            case "encode": {
                byte[] inputData = FileHandler.loadFile(filePath);
                byte[] compressedData = processor.compress(inputData);

                File inputFile = new File(filePath);
                String originalName = inputFile.getName();
                String archiveName = originalName.substring(0, originalName.lastIndexOf('.')) + ".hfm";

                FileHandler.saveArchive(compressedData, processor.encodingMap, originalName, archiveName);
                break;
            }

            case "info": {
                ArchiveDetails archive = FileHandler.loadArchive(filePath);
                byte[] decompressedData = processor.decompress(archive.encodedData, archive.decodingMap);

                FileHandler.displayInfo(archive, decompressedData);
                break;
            }

            default:
                System.out.println("Unknown operation. Available options: 'encode', 'decode', 'info'.");
        }
    }
}
