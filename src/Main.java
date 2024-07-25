import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;

// Name : Tharuka Gamage
// Student No: 20212177

public class Main {
    private static Node[][] nodes;
    private static Node startNode;
    private static Node endNode;
    private static int height;
    private static int width;

    public static void main(String[] args) throws Exception {
        String path = "input/benchmark_series/puzzle_10.txt";
        PerformanceAnalysis pa = new PerformanceAnalysis();
        pa.startTime();
        MapParser mp = new MapParser(path);



        startNode = mp.getStartNode();
        endNode = mp.getEndNode();
        height = mp.getMaxY();
        width = mp.getMaxX();
        nodes = mp.nodes;

        Node node = pathFinder();

        if(node == null){
            System.out.println("No path found");
            return;
        }

        List<Node> resolvedPaths = resolvePaths(node);

        for (Node p : resolvedPaths) {
            System.out.println(p);
        }

        pa.endTime();
        System.out.println("------Summary-------");
        System.out.println("PATH node count  : " + resolvedPaths.size());
        System.out.println(pa.executedTime());


    }


    private static List<Node> resolvePaths(Node endNode) {
        List<Node> path = new ArrayList<Node>();

        path.add(endNode);
        Node temp = null;

        if (endNode.getParent() != null) {
            temp = endNode.getParent();
        }

        while (temp != null) {
            path.add(temp);
            temp = temp.getParent();
        }
        Collections.reverse(path);
        return path;
    }

    private static Node pathFinder() {
        ArrayList<Node> openList = new ArrayList<Node>();
        ArrayList<Node> closeList = new ArrayList<Node>();

        Node currNode = null;
        openList.add(startNode);

        while (openList.size()> 0){
            Node minFNode = openList.stream().min(Comparator.comparing(Node::getF))
                    .orElseThrow(NoSuchElementException::new);
            currNode = minFNode;

            openList.remove(currNode);
            closeList.add(currNode);

            if(currNode.equals(endNode)){
                return currNode;

            }

            ArrayList<Node>neighborNodes = getNeighborNodes(currNode);

            if (neighborNodes.size() == 0) {
                continue;
            }

            for (Node node : neighborNodes) {
                if (closeList.contains(node))
                    continue;
                if (openList.indexOf(node) < 0) {
                    openList.add(node);
                } else {
                    Node exsistingNode = openList.get(openList.indexOf(node));
                    if (node.getG() < exsistingNode.getG()) {
                        openList.add(node);
                    }
                }
            }

        }
        return null;
    }

    private static ArrayList<Node> getNeighborNodes(Node currNode) {
        ArrayList<Node> neighbors = new ArrayList<>();

        int currX = currNode.getCoordinate().x;
        int currY = currNode.getCoordinate().y;

        Node leftNode = null;
        Node rightNode = null;
        Node UpNode = null;
        Node DownNode = null;

        for (int i = currX; i >= 1; i--) {
            Node node = nodes[currY - 1][i - 1];
            if (node == null) {
                continue;
            }
            int rockX = node.getCoordinate().x;
            if (currX > rockX) {
                if (leftNode == null) {
                    leftNode = (Node) node.clone();
                    break;
                }
            }
        }

        for (int i = currX; i <= width; i++) {
            Node node = nodes[currY - 1][i - 1];
            if (node == null) {
                continue;
            }
            int rockX = node.getCoordinate().x;
            if (currX < rockX) {
                if (rightNode == null) {
                    rightNode = (Node) node.clone();
                    break;
                }

                if (rightNode != null && rightNode.getCoordinate().x > rockX) {
                    rightNode = (Node) node.clone();
                }
            }
        }

        for (int i = currY; i >= 1; i--) {
            Node node = nodes[i - 1][currX - 1];
            if (node == null) {
                continue;
            }

            int rockY = node.getCoordinate().y;
            if (currY > rockY) {
                if (UpNode == null) {
                    UpNode = (Node) node.clone();
                    break;
                }

            }
        }

        for (int i = currY; i <= height; i++) {
            Node node = nodes[i - 1][currX - 1];
            if (node == null) {
                continue;
            }

            int rockY = node.getCoordinate().y;
            if (currY < rockY) {
                if (DownNode == null) {
                    DownNode = (Node) node.clone();
                    break;
                }
            }
        }

        if (leftNode == null) {
            if (currX != 1) {
                Node edgeNode = new Node(1, currY);
                edgeNode.setParent(currNode);
                edgeNode.findF(endNode);
                neighbors.add(edgeNode);
            }
        } else {

            if (leftNode.getNodeType() == 'F') {
                leftNode.setParent(currNode);
                leftNode.getCoordinate().x = leftNode.getCoordinate().x;
                leftNode.findF(endNode);
                neighbors.add(leftNode);
            }


            if (leftNode.getCoordinate().x < currX - 1) {
                leftNode.setParent(currNode);
                if (!(leftNode.getNodeType() == 'F')) {
                    leftNode.getCoordinate().x= leftNode.getCoordinate().x + 1;
                }
                leftNode.findF(endNode);
                neighbors.add(leftNode);
            }
        }


        if (rightNode == null) {
            if (currX < width) {
                Node edgeNode = new Node(width, currY);
                edgeNode.setParent(currNode);
                edgeNode.findF(endNode);
                neighbors.add(edgeNode);
            }
        } else {
            if (rightNode.getNodeType() == 'F') {
                rightNode.setParent(currNode);
                rightNode.getCoordinate().x = rightNode.getCoordinate().x;
                rightNode.findF(endNode);
                neighbors.add(rightNode);
            }

            if (rightNode.getCoordinate().x > currX + 1) {
                rightNode.setParent(currNode);
                if (!(rightNode.getNodeType() == 'F')) {
                    rightNode.getCoordinate().x= rightNode.getCoordinate().x - 1;
                }
                rightNode.findF(endNode);
                neighbors.add(rightNode);
            }
        }


        if (UpNode == null) {
            if (currY > 1) {
                Node edgeNode = new Node(currX, 1);
                edgeNode.setParent(currNode);
                edgeNode.findF(endNode);
                neighbors.add(edgeNode);
            }
        } else {
            if (UpNode.getNodeType() == 'F') {
                UpNode.setParent(currNode);
                UpNode.getCoordinate().y = UpNode.getCoordinate().y;
                UpNode.findF(endNode);
                neighbors.add(UpNode);
            }



            if (UpNode.getCoordinate().y < currY - 1) {
                UpNode.setParent(currNode);
                if (!(UpNode.getNodeType() == 'F')) {
                    UpNode.getCoordinate().y= UpNode.getCoordinate().y + 1;
                }
                UpNode.findF(endNode);
                neighbors.add(UpNode);
            }
        }


        if (DownNode == null) {
            if (currY < height) {
                Node edgeNode = new Node(currX, height);
                edgeNode.setParent(currNode);
                edgeNode.findF(endNode);
                neighbors.add(edgeNode);
            }
        } else {
            if (DownNode.getNodeType() == 'F') {
                DownNode.setParent(currNode);
                DownNode.getCoordinate().y = DownNode.getCoordinate().y;
                DownNode.findF(endNode);
                neighbors.add(DownNode);
            }
            if ( DownNode.getCoordinate().y > currY + 1) {
                DownNode.setParent(currNode);
                if (!(DownNode.getNodeType() == 'F')) {
                    DownNode.getCoordinate().y = DownNode.getCoordinate().y - 1;
                }
                DownNode.findF(endNode);
                neighbors.add(DownNode);
            }


        }

        return neighbors;
    }
}