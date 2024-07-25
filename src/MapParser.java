import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Scanner;

// Name : Tharuka Gamage
// Student No: 20212177

public class MapParser {

    private int maxY = 0, maxX = 0;
    private Node endNode;
    private Node startNode;
    Node[][] nodes;

    public int getMaxY() {
        return maxY;
    }

    public int getMaxX() {
        return maxX;
    }

    public Node getEndNode() {
        return this.endNode;
    }

    public Node getStartNode() {
        return startNode;
    }

    public MapParser(String pathFile) throws Exception {
        initArray(pathFile);
        parseMap(pathFile);
    }

    public void initArray(String pathFile){
        try {
            FileReader fileReader = new FileReader(pathFile);
            Scanner scanner = new Scanner(fileReader);

            int height = 0, width = 0;

            while (scanner.hasNextLine()) {
                height++;
                if (height == 1) {
                    width = scanner.nextLine().length();
                }
                scanner.nextLine();
            }
            nodes = new Node[height + 1][width];

            scanner.close();
            fileReader.close();
        } catch (Exception e) {
            System.out.println("File not found.");
        }
    }

    public void parseMap(String pathFile) throws Exception{

            //FileReader fileReader = new FileReader(pathFile);
        FileInputStream fis = new FileInputStream(pathFile);
        Scanner scanner = new Scanner(fis);

            int x = 0;
            int y = 0;
            boolean isStartFound = false;
            boolean isFinishFound = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                x = line.length();

                for (int i = 0; i < x;i++){
                    char nodeValue = line.charAt(i);

                    if(nodeValue == 'S'){
                        startNode =  new Node((i+1),(y+1));
                        startNode.setG(0);
                        startNode.setH(999999);
                        startNode.setNodeType('S');
                        isStartFound = true;

                    }else if (nodeValue == 'F'){
                        endNode = new Node((i+1),(y+1));
                        endNode.setG(0);
                        endNode.setH(0);
                        endNode.setNodeType('F');
                        isFinishFound = true;
                        nodes[y][i] = endNode;

                    }else if(nodeValue == '0'){
                        Node rock = new Node((i+1),(y+1));
                        rock.setNodeType('0');
                        nodes[y][i] = rock;
                    }else if(nodeValue == '.') {

                    }else {
                        throw new Exception("Invalid character found");
                    }
                }
                y++;



            }
        if(!isStartFound){
            throw new Exception("Start not found");
        }
        if(!isFinishFound){
            throw new Exception("Finish not found");
        }
            maxX = x;
            maxY = y;

            scanner.close();
           // fileReader.close();
        fis.close();


    }
}
