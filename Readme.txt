DIVYANK KOTHARI
 801252325
Programming Project 1
Data Structures and Algorithms

ReadMe file for Project

Programming language: openjdk version "16.0.2" 2021-07-20
Compiler:	javac 16.0.2
Algorithm:	Dijkstra's Algorithm

Files:
1. Main.java  -- Contains multiple classes
    Vertex
    Graph
    Edge
    BinaryPriorityQueue
    GraphException

Setup
Building this project requires that system must have java development kit(JDK) installed.
Latest version is preferred, but older one can also work.

Inputs:
This program requires user input of file name that contains the initial state of the graph.
Each line representing two directed edges is listed on a line, and is specified by the names of its
two vertices followed by the transmission time.
Vertex1 Vertex2 weight

Program Description:
Shortest Path First implementation using Dijkstra's Algorithm
Also, implementing the priority queue using min-heap to have an
Efficient running time.

Classes:
Main class:
Takes the Intial graph input and passes it to Graph Class.
Also, take the corresping next inputs from the file or user and executes them.

Vertex class:
It holds the information of vertex such as Distance from source,
vertex name, previous vertex on shortest path, vertex status and maintains an
adjacency list.

Edge Class:
It holds the edge in the graph including
destination, weight and status of the edge.

Graph Class:
It contains all the function for making changes to the Graph.
It builds a graph by implementing an adjacency list which includes the
graph information.

Running time:
Shortest path algorithm is O((|V|+|E|)lnV)
The PriorityQueue Class holds the information of the min-heap data structure.

Computing reachable vertices using the function (reachable):
The concept of Breadth first search (BFS) algorithm is incorporated to
Find out the reachable vertices from each vertex in the graph.
This also prints the reachable vertices (in up status) from every vertex
Whose status is up.
Running time:
complexity of this is O(V+E) for each
Vertex or edge that is up. So, in worst case if every vertex
is up then it becomes O( V(V+E) ).

Implementation of the Data Structure:
The priority queue is implemented in the form of a min-heap for
good efficiency.
The min-heap is used to store the distances of the vertices from
the source vertex and help to extract the closest vertex very fast.

Changes to the Graph
*	addedge source destination weight: add a new edge or update a previous edge.
*	deleteedge source destination: delete an existing edge going from tail vertex to headvertex.
*	edgedown source destination: make the edge status as down.
*	edgeup source destination: make the edge status as up.
*	vertexdown vertex: make the vertex as down.
*	vertexup vertex: make the vertex status as up.

Print the graph
*	print - Prints the graph
prints all the edges from a vertex even if there are more than 1.

Reachable vertices:
*	reachable: prints all the reachable vertices.

Finding the Shortest Path
*	path source destination
prints shortest path from source to destination and rounds of it to 2 digit decimal Half down.

Exit the program
*	quit


How to run the file:
1. Open the command window.
2. Set the current directory to the location where the file is present.
3. Make sure that the file with graph details (network) and the query file are in the current working directory.
4. Please write quit at the end of file which consists of all the queries.
5. At the command prompt enter:
    javac Main.java
	java Main.java network.txt < queries.txt > output.txt

The output file will be stored as output.txt

The program works well with the example provided on canvas,
for other files the efficiency depends on the size of query file and
the graph (network) file repeating data values.


What works:
    It works for all the inputs given in the project example.
What fails:
    Negative Inputs : A edge can not have a negative distance as we are using Dijkstras
    Vertex should be present for shortest path.
    Edge is required to be in graph for addedge and downedge.
