package ai.lab.pkg1;

import java.util.*;

public class PathFinder {
    public static void main(String[] args) {
        
        // the plan in 2D array
        int[][] plan = { { 0, -1, 1, 1, 1}, 
                         { 1, 1, -1, -1, 1}, 
                         { 1, 1, 1, -1, 2}};
        
        // RootNode and TargetNode finder
        Node RootNode = new Node(0,0);
        Node TargetNode = new Node(0,0);
        for (int i = 0; i < plan.length; i++) {
            for (int j = 0; j < plan[0].length; j++) {
                if (plan[i][j] == 0) {
                    RootNode = new Node(i,j);
                }
                else if (plan[i][j] == 2) {
                    TargetNode = new Node(i,j);
                }
            }
        }
        
        System.out.println("Using BFS algo to find the goal: ");
        BFS(new NodeList(plan), RootNode, TargetNode);
        
        System.out.println("-----------");
        
        System.out.println("Using DFS algo to find the goal: ");
        DFS(new NodeList(plan), RootNode, TargetNode);
    }
    
static void BFS(NodeList nodeList, Node rootNode, Node targetNode){
    
    boolean found = false;  //a flag, set true when the goal is found
    int explored = 1;  //num of explored nodes untill we reach the goal, initialized to 1 cuz we have counted the root as explored
    Queue<Node> q = new LinkedList();  //queue that the BFS uses
    q.add(rootNode);
    
    while (!q.isEmpty()){
        
        Node head = q.peek();  //saving the head of the queue
        
        if (head.equals(targetNode)) { //checks if the head is the goal
            found = true;
            ArrayList<Node> path = new ArrayList();
            System.out.println("found after " + explored + " nodes explored");
            System.out.println("Path to goal is:");
            path.add(head);
            while (head.parent != null){  // traversing back to get the goal path
                path.add(head.parent);
                head = head.parent;
            }
            for (int i = path.size()-1; i >= 0; i--) {
                System.out.println(path.get(i));
            }
            break;
        }
        else{ //if the head is not the goal then we explore more
            explored += 1;
            q.remove();
            ArrayList<Node> neigh = nodeList.Neigh(head); //getting the neighbors of the head
            for (int i = 0; i < neigh.size(); i++) {
                if (!neigh.get(i).equals(head.parent)) { //making sure we dont revisit the parent of the head
                    q.add(neigh.get(i));
                }
            }
        }
    }
    if (found == false) { //if found flag is still false after the q is empty that means we didnt reach the goal
            System.out.println("FAILURE");
    }
}

static void DFS(NodeList nodeList, Node rootNode, Node targetNode){
    
    boolean found = false;  //a flag, set true when the goal is found
    int explored = 1;  //num of explored nodes untill we reach the goal, initialized to 1 cuz we have counted the root as explored
    Stack<Node> stk = new Stack<>(); //stack that the dfs uses
    ArrayList visited = new ArrayList(); //added a visited array to avoid getting stuck in infinite loops
    stk.add(rootNode);
    
    while (!stk.isEmpty()){
        
        Node head = stk.peek();  //saving the head of the stack
        
        if (head.equals(targetNode)) { //checks if the head is the goal
            found = true;
            ArrayList<Node> path = new ArrayList();
            System.out.println("found after " + explored + " nodes explored");
            System.out.println("Path to goal is:");
            path.add(head);
            while (head.parent != null){  // traversing back to get the goal path
                path.add(head.parent);
                head = head.parent;
            }
            for (int i = path.size()-1; i >= 0; i--) {
                System.out.println(path.get(i));
            }
            break;
        }
        else{ //if the head is not the goal then we explore more
            explored += 1;
            stk.pop();
            ArrayList<Node> neigh = nodeList.Neigh(head); //getting the neighbors of the head
            for (int i = neigh.size()-1; i >= 0; i--) {
                if ((!neigh.get(i).equals(head.parent)) || (!neigh.get(i).equals(head.parent.parent)) || (!neigh.get(i).equals(head.parent.parent.parent)) || (!neigh.get(i).equals(head.parent.parent.parent.parent)) || (!neigh.get(i).equals(head.parent.parent.parent.parent.parent))) { //making sure we dont revisit the parent of the head
                    if (!visited.contains(neigh.get(i))) {
                        stk.add(neigh.get(i));
                        visited.add(neigh.get(i));
                    }
                }
            }
        }
    }
    if (found == false) { //if found flag is still false after the stk is empty that means we didnt reach the goal
            System.out.println("FAILURE");
    }
}

static class NodeList{
        //NodeList class takes a 2D array plan and converts it into ArrayList of class Node
        int[][] plan;
        ArrayList<Node> nodes = new ArrayList<Node>();
        
        public NodeList(int[][] plan) {
            this.plan = plan;
            for (int i = 0; i < plan.length; i++) {
                for (int j = 0; j < plan[0].length; j++) {
                    Node node = new Node(i,j);
                    nodes.add(node);
                }
            }
        }
        
        //returns an arraylist with all the neighbors of a certain node and its parent
        public ArrayList<Node> Neigh(Node n){
            
            ArrayList<Node> NeighList = new ArrayList<Node>();
            
            if (plan[n.x][n.y] == -1) {
                System.out.println("this node is a (-1) node, you cant be here");
                return null;
            }
            //left neigh
            if ((n.y) != (0)) {
                if (plan[n.x][n.y-1] != -1) {
                    Node temp = new Node(n.x, n.y-1);
                    temp.setParent(n);
                    NeighList.add(temp);
                }
            }
            //right neigh
            if ((n.y) != (plan[0].length - 1)) {
                if (plan[n.x][n.y+1] != -1) {
                    Node temp = new Node(n.x, n.y+1);
                    temp.setParent(n);
                    NeighList.add(temp);
                }
            }
            //up neigh
            if ((n.x) != (0)) {
                if (plan[n.x-1][n.y] != -1) {
                    Node temp = new Node(n.x-1, n.y);
                    temp.setParent(n);
                    NeighList.add(temp);
                }
            }
            //down neigh
            if ((n.x) != (plan.length - 1)) {
                if (plan[n.x+1][n.y] != -1) {
                    Node temp = new Node(n.x+1, n.y);
                    temp.setParent(n);
                    NeighList.add(temp);
                }
            }
            //up right neigh
            if (((n.x) != (0)) && ((n.y) != (plan[0].length - 1))) {
                if (plan[n.x-1][n.y+1] != -1) {
                    Node temp = new Node(n.x-1, n.y+1);
                    temp.setParent(n);
                    NeighList.add(temp);
                }
            }
            //down right neigh
            if (((n.x) != (plan.length - 1)) && ((n.y) != (plan[0].length - 1))) {
                if (plan[n.x+1][n.y+1] != -1) {
                    Node temp = new Node(n.x+1, n.y+1);
                    temp.setParent(n);
                    NeighList.add(temp);
                }
            }
            //up left neigh
            if (((n.x) != (0)) && ((n.y) != (0))) {
                if (plan[n.x-1][n.y-1] != -1) {
                    Node temp = new Node(n.x-1, n.y-1);
                    temp.setParent(n);
                    NeighList.add(temp);
                }
            }
            //down left neigh
            if (((n.x) != (plan.length - 1)) && ((n.y) != (0))) {
                if (plan[n.x+1][n.y-1] != -1) {
                    Node temp = new Node(n.x+1, n.y-1);
                    temp.setParent(n);
                    NeighList.add(temp);
                }
            }
            return NeighList;
        }
        
        //returns an arraylist of the cost of all the neighbors of a certain node
        public ArrayList<Float> k(Node n){
            
            ArrayList<Float> cost = new ArrayList<>();
            ArrayList<Node> nlist = Neigh(n);
            int x = n.getX();
            int y = n.getY();
            
            for (int i = 0; i < nlist.size(); i++) {
                if ((x == (nlist.get(i).x)+1 && y == (nlist.get(i).y)) ||
                    (x == (nlist.get(i).x)-1 && y == (nlist.get(i).y)) ||
                    (y == (nlist.get(i).y)+1 && x == (nlist.get(i).x)) ||
                    (y == (nlist.get(i).y)-1 && x == (nlist.get(i).x))) {
                    cost.add(1.0f);//horizontal move cost
                }
                else{
                    cost.add(1.41f);//diagonal move cost
                }
            }
            return cost;
        }
     
    }

static class Nodes{
        //Nodes class takes an 2D array plan and converts it into a matrix of class Node
        Node [][] NodesPlan;
        int [][] plan;
        
        public Nodes(int [][] plan) {
            
            this.plan = plan;
            this.NodesPlan = new Node[plan.length][plan[0].length];
            
            for (int i = 0; i < plan.length; i++) {
                for (int j = 0; j < plan[0].length; j++) {
                    NodesPlan[i][j] = new Node(i,j);
                }
            }
        }
        
        //returns the cost of any 2 nodes, if they are not neighbors it returns 0
        public float NodeK(Node i, Node j){
            
            NodeList all = new NodeList(plan);
            ArrayList<Node> neigh = all.Neigh(i);
            ArrayList<Float> neighK = all.k(i);
            boolean found = false;
            float nk = 0;
            
            for (int k = 0; k < neigh.size(); k++) {
                if ((neigh.get(k).getX() == j.getX()) && (neigh.get(k).getY() == j.getY())){
                    found = true;
                    nk = neighK.get(k);
                }
            }
            if (found == true) {
                return nk;
            }
            else{
                return 0;
            }
        }
    
}

static class Node{
        //Node class saves the x cordinate and y cordinate of the node, and also saves the parent of the node
        int x;
        int y;
        Node parent = null;
        
        //constructer
        public Node(int x, int y){
            this.x = x;
            this.y = y;
        }
    
        //Getters
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        
        //Set the parent
        public void setParent(Node p){
            this.parent = p;
        }
        
        //equals method to compare between nodes
        @Override
        public boolean equals(Object obj) {
            
            Node objj = new Node(0,0);
            if (obj instanceof  Node) {
                objj = (Node)obj;
            }
            
            if ((this.getX() == objj.getX()) && (this.getY() == objj.getY())){
                return true;
            }
            else{
                return false;
            }
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 37 * hash + this.x;
            hash = 37 * hash + this.y;
            return hash;
        }
        
        //toString fucnction for printing
        @Override
        public String toString() {
            return "Node(" + this.x + ", " + this.y + ")";
        }
    }
}
