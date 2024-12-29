import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileHandler {
    static String HEADER_SIGNATURE = "ARCHIVE";

    private static void parseHeader(String filePath, ArchiveDetails archiveDetails) {
        HashMap<String, Byte> decodingTable = new HashMap<>();
        StringBuilder headerBuilder = new StringBuilder();
        String originalFile = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerSignature = reader.readLine();
            headerBuilder.append(headerSignature).append('\n');

            if (!headerSignature.equals(HEADER_SIGNATURE)) {
                throw new UnsupportedOperationException("Invalid file format");
            }

            originalFile = reader.readLine();
            headerBuilder.append(originalFile).append('\n');

            if (originalFile.isEmpty()) {
                throw new Exception("Missing original file name");
            }

            String tableSizeString = reader.readLine();
            headerBuilder.append(tableSizeString).append('\n');

            int tableSize = Integer.parseInt(tableSizeString);
            if (tableSize == 0) {
                throw new Exception("Decoding table is missing");
            }

            for (int i = 0; i < tableSize; i++) {
                char symbol;
                String code;
                String line = reader.readLine();

                if (line.isEmpty()) {
                    symbol = '\n';
                    code = reader.readLine();
                    headerBuilder.append(symbol).append(code).append('\n');
                } else {
                    symbol = line.charAt(0);
                    code = line.substring(1);
                    headerBuilder.append(line).append('\n');
                }

                decodingTable.put(code, (byte) symbol);
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        archiveDetails.headerOffset = headerBuilder.toString().getBytes().length;
        archiveDetails.decodingMap = decodingTable;
        archiveDetails.sourceFileName = originalFile;
    }

    private static int calculatePaddingBits(String compressedData) {
        String paddingInfo = compressedData.substring(0, 8);
        return Integer.parseInt(paddingInfo, 2);
    }

    private static void extractData(String filePath, ArchiveDetails archiveDetails) {
        File archiveFile = new File(filePath);
        StringBuilder compressedDataBuilder = new StringBuilder();
        int fileSize = 0;

        try (FileInputStream inputStream = new FileInputStream(archiveFile)) {
            inputStream.skip(archiveDetails.headerOffset);

            int byteRead;
            while ((byteRead = inputStream.read()) != -1) {
                fileSize++;
                compressedDataBuilder.append(
                        String.format("%8s", Integer.toBinaryString(byteRead & 0xFF)).replace(" ", "0")
                );
            }
        } catch (IOException e) {
            System.err.println(e);
        }

        int paddingBits = calculatePaddingBits(compressedDataBuilder.toString());

        archiveDetails.originalSize = fileSize;
        archiveDetails.encodedData = compressedDataBuilder.substring(8, compressedDataBuilder.length() - paddingBits);
    }

    public static ArchiveDetails loadArchive(String filePath) {
        ArchiveDetails archiveDetails = new ArchiveDetails();
        parseHeader(filePath, archiveDetails);
        extractData(filePath, archiveDetails);
        return archiveDetails;
    }

    public static byte[] loadFile(String filePath) {
        File inputFile = new File(filePath);
        byte[] fileData = new byte[(int) inputFile.length()];

        try (FileInputStream inputStream = new FileInputStream(inputFile)) {
            inputStream.read(fileData);
        } catch (IOException e) {
            System.err.println(e);
        }

        return fileData;
    }

    public static void saveArchive(byte[] compressedData, HashMap<Byte, String> encodingTable, String originalFileName, String outputFilePath) {
        StringBuilder archiveHeader = new StringBuilder(HEADER_SIGNATURE + "\n");
        archiveHeader.append(originalFileName).append("\n");
        archiveHeader.append(encodingTable.size()).append('\n');

        for (Map.Entry<Byte, String> entry : encodingTable.entrySet()) {
            archiveHeader.append((char) (entry.getKey() & 0xFF)).append(entry.getValue()).append('\n');
        }

        try (PrintWriter writer = new PrintWriter(outputFilePath)) {
            writer.write(archiveHeader.toString());
        } catch (FileNotFoundException e) {
            System.err.println("Error: Output file not found");
        }

        try (FileOutputStream outputStream = new FileOutputStream(outputFilePath, true)) {
            outputStream.write(compressedData);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void saveFile(byte[] fileData, String outputFilePath) {
        try (FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {
            outputStream.write(fileData);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void displayInfo(ArchiveDetails archiveDetails, byte[] decompressedData) {
        System.out.println("Archived file size: " + archiveDetails.originalSize);
        System.out.println("Source file name: " + archiveDetails.sourceFileName);
        System.out.println("Decompressed file size: " + decompressedData.length);

        if (decompressedData.length > 0) {
            System.out.println("Compression ratio: " + (100 * archiveDetails.originalSize / decompressedData.length) + "%");
        }

        System.out.println("Decoding map:");
        for (Map.Entry<String, Byte> entry : archiveDetails.decodingMap.entrySet()) {
            System.out.println((char) entry.getValue().byteValue() + "=" + entry.getKey());
        }
    }
}
