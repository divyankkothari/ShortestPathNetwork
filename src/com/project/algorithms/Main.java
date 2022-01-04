// DIVYANK KOTHARI 801252325
package com.project.algorithms;

import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class Main {

    /**
     * @param in command line arguments
     * @param g  Graph
     * @return
     */
    public static boolean processRequest( Scanner in, Graph g )
    {
        try
        {
            String command = in.nextLine( );
            StringTokenizer str = new StringTokenizer( command );

            if(str.countTokens()==1)
            {
                String s= str.nextToken();
                if(s.equals("print"))
                {
                    g.printGraph();
                    System.out.println();
                }
                else if(s.equals("quit"))
                {
                    return false;
                }
                else if(s.equals("reachable"))
                {
                    g.reachable();
                    System.out.println();
                }
            }
            else if (str.countTokens()==2 ) {
                String s = str.nextToken();
                if (s.equals("vertexdown"))
                    g.changeVertexStatusToDown(str.nextToken());
                else if (s.equals("vertexup")) {
                    g.changeVertexStatusToUp(str.nextToken());
                }
            }
            else if(str.countTokens()==3 )
            {
                String s=str.nextToken();
                if(s.equals("path")) {
                    String startName = str.nextToken();
                    String destName = str.nextToken();
                    g.dijkstras(startName);
                    g.printPath(destName);
                    System.out.println();
                }
                else if(s.equals("deleteedge"))
                {
                    String startName = str.nextToken();
                    String destName = str.nextToken();
                    g.deleteEdge(startName,destName);
                }
                else if(s.equals("edgedown"))
                {
                    String startName = str.nextToken();
                    String destName = str.nextToken();
                    g.edgedown(startName,destName);
                }

                else if(s.equals("edgeup"))
                {
                    String startName = str.nextToken();
                    String destName = str.nextToken();
                    g.edgeup(startName,destName);
                }
            }
            else if(str.countTokens()==4 )
            {
                String s=  str.nextToken();
                if(s.equals("addedge"))
                {
                    String startName = str.nextToken();
                    String destName = str.nextToken();
                    Float weight = Float.parseFloat(str.nextToken());
                    g.addEdge(startName,destName,weight);
                }
            }
            else
            {
                System.out.println("Incorrect Input");
            }
        }
        catch( NoSuchElementException e )
        { return false; }
        catch( GraphException e )
        { System.err.println( e ); }
        return true;
    }

    // Used to signal violations of preconditions for
    // various shortest path algorithms.
    class GraphException extends RuntimeException
    {
        public GraphException( String name )
        {
            super( name );
        }
    }

    /**
     * @param args Arguments from command line
     */
    public static void main(String[] args) {
        Graph g = new Graph(args[0]);
        Scanner in = new Scanner( System.in );
        while( processRequest( in, g ) )
           ;
    }

    // Represents a vertex in the graph.
    public static class Vertex {

        public String     name;   // Vertex name
        public List<Edge> adj;    // Adjacent vertices
        public Vertex prev;   // Previous vertex on shortest path
        public Float      dist;   // Distance of path
        public String     status = "UP";  // Vertex Up or Down

        /**
         * @param nm name of the vertex
         */
        public Vertex( String nm )
        {
            name = nm;
            adj = new LinkedList<>( );
            reset( );
        }

        /**
         *  resets all variables of the vertex
         */
        public void reset( )
        {
            dist = Graph.FLOATINFINITY;
            prev = null;
        }

        /**
         * @return a string with all the variables of the vertex
         */
        @Override
        public String toString()
        {

            String s ="";
            if(status == "DOWN"){
                s = s +" "+ status;
            }
            List<Edge> adja = new ArrayList<>(adj);
            Collections.sort(adja,new Comparator<Edge>(){
                @Override
                public int compare(Edge v1, Edge v2) {
                    return (v1.target.name.compareTo(v2.target.name));
                }
            });
            for(int i = 0; i< adja.size(); i++)
            {
                s = s + adja.get(i);
            }
            s.trim();
            return name + s;
        }

    }

    /**
     *Implementation of Min heap using Priority Queue
     */
    static class BinaryPriorityQueue {
        Vertex H[];

        /**
         * Constructor initialize size
         * @param maxsize int
         */
        public BinaryPriorityQueue(int maxsize){
             H = new Vertex[maxsize*2];
        }
        public int size =-1;

        /**
         * Finds parent of a node
         * @param i int
         */
        public int parent(int i)
        {
            return i/2;
        }

        /**
         * Checks if queue is Empty
         */
        public boolean isEmpty()
        {
            if(size == -1)
            {
                return false;
            }
            return true;
        }

        /**
         *     Finds left child of a node
         *     @param i int
         */
        public int leftchild(int i)
        {
            return 2*i;
        }

        /**
         * Finds right child of a node
         *     @param i index of node
         *     @return index of right child
         */
        public int rightchild(int i)
        {
            return (2*i)+1;
        }

        /**
         * Insert vertex
         *  @param p Vertex to be inserted
         */
        public void insert(Vertex p)
        {
            size = size + 1;
            H[size] = p;
            floatUp(size);
        }

        /**
         * Bring Heap property Back
         *  @param i index to be checked
         */
        public void floatUp(int i)
        {
            while(i!=0 && H[i].dist<H[parent(i)].dist) {
                swap(i, parent(i));
                i=parent(i);
            }
        }

        /**
         * Swap Elements
         *  @param i, j index to be swapped
         */
        public void swap(int i, int j)
        {
            Vertex temp= H[i];
            H[i] = H[j];
            H[j] = temp;
        }

        /**
         *  Extract minimum element from heap
         *  @return Vertex to be returned
         */
        public Vertex extractMin() {
            if(size<0)
            {
                throw new ArrayIndexOutOfBoundsException( "size is less than 0" );
            }
            Vertex result = H[0];
            H[0] = H[size];
            size = size - 1;
            floatDown(0);

            return result;
        }
        /**
         * Bring Heap property Back
         *  @param p index to be checked
         */
        public void floatDown(int p)
        {
            int l= leftchild(p);
            int r = rightchild(p);
            int smallest = p;

            if( l <= size && H[l].dist < H[p].dist)
            {
                smallest = l;
            }
            if( r <= size && H[r].dist < H[smallest].dist)
            {
                smallest = r;
            }
            if(smallest != p)
            {
                Vertex temp = H[smallest];
                H[smallest] = H[p];
                H[p] = temp;
                floatDown(smallest);
            }
        }
    }

    // Represents a Edge in the graph
    public static class Edge {
        public Vertex target;
        public Float weight;
        public String status = "UP";

        @Override
        public String toString() {

            String s= "\n" +
                    "  " + this.target.name +
                    " " + this.weight;
            if(this.status.equals("DOWN"))
            {
                return s+" "+ this.status;
            }
            return s;
        }

    }

    /**
     *  Graph class: evaluate shortest paths.
     *
     *  CONSTRUCTION: with no parameters.
     *
     *  ******************PUBLIC OPERATIONS**********************
     *  void addEdge( String sourceName, String destName, Float weight ) --> Add additional edge
     *  void deleteEdge( String sourceName, String destName) --> Deletes Edge
     *  void edgedown( String sourceName, String destName) --> Marks Status of Edge DOWN
     *  void edgeup( String sourceName, String destName) --> Marks Status of Edge UP
     *  void changeVertexStatusToDown(String sourceName) --> Marks Status of Vertex DOWN
     *  void changeVertexStatusToUp(String sourceName) --> Marks Status of Vertex UP
     *  void printPath( String w ) --> Print path after alg is run
     *  void printGraph() --> Prints Graph
     *  void bfs(String startName ) --> Takes vertex name and finds all reachable vertex
     *  void dijkstras( String startName ) --> Takes Vertex name and finds shortest distance to each vertex
     */

    public static class Graph {
        public static final int INFINITY = Integer.MAX_VALUE;
        public static final float FLOATINFINITY = Float.MAX_VALUE;

        public Map<String, Vertex> vertexMap = new HashMap<String, Vertex>( );

        /**
         * Adds Edge
         * @param sourceName
         * @param destName
         * @param weight
         */
        public void addEdge( String sourceName, String destName, Float weight )
        {
            Vertex v = getVertex( sourceName );
            Vertex w = getVertex( destName );
            deleteEdge( sourceName,  destName);
            Edge e = new Edge();
            e.weight = weight;
            e.target = w;
            v.adj.add(e);

        }

        /**
         * Deletes Edge
         * @param sourceName
         * @param destName
         */
        public void deleteEdge( String sourceName, String destName)
        {
            Vertex v = getVertex( sourceName );
            Vertex w = getVertex( destName );
            for(Edge p : v.adj)
            {
                if(p.target.equals(w))
                {
                    v.adj.remove(p);
                    break;
                }
            }
        }

        /**
         * @param sourceName
         * @param destName
         */
        public void edgedown( String sourceName, String destName)
        {
            Vertex v = getVertex( sourceName );
            Vertex w = getVertex( destName );
            for(int i =0;i < v.adj.size();i++)
            {
                if(v.adj.get(i).target.equals(w))
                {
                    v.adj.get(i).status="DOWN";
                }
            }
        }

        /**
         * Change Status of Edge to Up
         * @param sourceName
         * @param destName
         */
        public void edgeup( String sourceName, String destName)
        {
            Vertex v = getVertex( sourceName );
            Vertex w = getVertex( destName );
            for(int i =0;i < v.adj.size();i++)
            {
                if(v.adj.get(i).target.equals(w))
                {
                    v.adj.get(i).status="UP";
                }
            }
        }

        /**
         * Gets Vertex
         * @param sourceName
         * @return
         */
        private Vertex getVertex(String sourceName)
        {
            Vertex v = vertexMap.get( sourceName );
            if( v == null )
            {
                v = new Vertex( sourceName );
                vertexMap.put( sourceName, v );
            }
            return v;
        }

        /**
         * change Vertex Status To Down
         * @param sourceName
         */
        public void changeVertexStatusToDown(String sourceName)
        {
            Vertex v = vertexMap.get( sourceName );
            v.status = "DOWN";
        }

        /**
         * change Vertex Status To Up
         * @param sourceName
         */
        public void changeVertexStatusToUp(String sourceName)
        {
            Vertex v = vertexMap.get( sourceName );
            v.status = "UP";
        }

        /**
         * Prints Graph, all vertex and adjacent edges
         */
        public void printGraph()
        {
            List<String> employeeByKey = new ArrayList<>(vertexMap.keySet());
            Collections.sort(employeeByKey);
           for(String v :employeeByKey)
           {
               System.out.println(vertexMap.get(v));
           }
        }

        /**
         * Funciton to get reachable function from each vertex that is Up
         */
        public void reachable()
        {
            List<String> employeeByKey = new ArrayList<>(vertexMap.keySet());
            Collections.sort(employeeByKey);
            for(String v :employeeByKey)
            {
                if(vertexMap.get(v).status.equals("UP"))
                    bfs(v);
            }
        }

        /**
         * @param filename --> Input Graph
         */
        public Graph(String filename)
        {
            try
            {
                FileReader fin = new FileReader(filename);
                Scanner graphFile = new Scanner( fin );
                // Read the edges and insert
                String line;
                while( graphFile.hasNextLine( ) )
                {
                    line = graphFile.nextLine( );
                    StringTokenizer st = new StringTokenizer( line );
                    try
                    {
                        if( st.countTokens( ) != 3 )
                        {
                            System.err.println( "Skipping ill-formatted line " +
                                    line );
                            continue;
                        }
                        String source  = st.nextToken( );
                        String dest    = st.nextToken( );
                        Float distance = (Float.parseFloat( st.nextToken( )));
                        addEdge( source, dest, distance);
                        addEdge( dest, source, distance);
                    }
                    catch( NumberFormatException e )
                    {
                        System.err.println( "Skipping ill-formatted line " + line );
                    }
                }
            }
            catch( IOException e )
            {
                System.err.println( e );
            }
        }

        /**
         * Driver routine to print total distance.
         * It calls recursive routine to print shortest path to
         * destNode after a shortest path algorithm has run.
         */
        public void printPath( String destName )
        {
            Vertex w = vertexMap.get( destName );
            if( w == null  || w.status.equals("DOWN"))
                throw new NoSuchElementException( "Destination vertex not found" );
            else if( w.dist == INFINITY )
                System.out.println( destName + " is unreachable" );
            else
            {
                DecimalFormat format = new DecimalFormat("0.##"); // Choose the number of decimal places to work with in case they are different than zero and zero value will be removed
                format.setRoundingMode(RoundingMode.HALF_DOWN); // Choose your Rounding Mode
                printPath( w );
                System.out.print( " " + format.format(w.dist) );
                System.out.println("");

            }
        }

        /**
         * @param dest
         */
        private void printPath( Vertex dest )
        {
            if( dest.prev != null )
            {
                printPath( dest.prev );
                System.out.print( " " );
            }
            System.out.print( dest.name );
        }

        private void clearAll( )
        {
            for( Vertex v : vertexMap.values( ) )
                v.reset( );
        }

        /**
         * @param startName
         */
        public void bfs(String startName )
        {

                clearAll( );
                List<String> reachable=new ArrayList<>();
                Vertex start = vertexMap.get( startName );

                if( start == null )
                    throw new NoSuchElementException( "Start vertex not found" );
                Queue<Vertex> q = new LinkedList<Vertex>( );
                q.add( start );
                start.dist = 0.0f;
                while( !q.isEmpty( ) )
                {
                    Vertex v = q.remove( );
                    for( Edge w : v.adj )
                    {
                        if( w.target.dist == FLOATINFINITY && w.target.status.equals("UP") && v.status.equals("UP"))
                        {
                            reachable.add(w.target.name);
                            w.target.dist = v.dist + 1;
                            w.target.prev = v;
                            q.add( w.target );
                        }
                    }
                }
            System.out.println(startName);
                Collections.sort(reachable);
                for(String s : reachable)
                {
                    System.out.println("  "+s);
                }
        }

        /**
         * @param startName
         */
        public void dijkstras( String startName )
        {
            clearAll( );
            Vertex start = vertexMap.get( startName );
            if( start == null || start.status.equals("DOWN"))
                throw new NoSuchElementException( "Start vertex is out" );
            BinaryPriorityQueue q = new BinaryPriorityQueue(vertexMap.size());
            q.insert( start );
            start.dist = 0.0f;
            Map<Vertex, Boolean> hash = new HashMap<>();

            while( q.isEmpty( ) )
            {
                Vertex v = q.extractMin();
                hash.put(v, true);
                for( Edge w : v.adj )
                {
                    if(w.target.status.equals( "DOWN") || w.status.equals("DOWN"))
                    {
                        continue;
                    }
                    if(hash.containsKey(w.target))
                        continue;
                    if( w.target.dist >= v.dist+ w.weight)
                    {
                        w.target.dist = v.dist+ w.weight;
                        w.target.prev = v;
                        q.insert( w.target );
                    }
                }
            }
        }
    }
}
