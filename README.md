APIWiz Assignment

In this assignment in Graph folder it is a answer of 1st question:
So, what is the approach behind 1st Question is as follows:
Purpose of the Code
The code performs a topological traversal of a Directed Acyclic Graph (DAG), where:
Each node represents a task or operation.
Each directed edge represents a dependency between tasks.
Tasks (nodes) can only be executed once all their dependencies (incoming edges) are resolved.
Multithreading is used to allow independent tasks to execute concurrently, improving efficiency.
Step-by-Step Explanation
1. Reading Input
The program first reads the number of nodes.
Then it reads each node's ID and label (e.g., 1:Node-1) and stores them in vertexMap.
An adjacency list (adjList) and an in-degree map (inDegree) are initialized for each node.
The program then reads the number of edges and processes each edge (e.g., 1:2), populating adjList and updating the in-degree of the destination node.

2. Starting the Traversal
The traversal begins from all nodes that have an in-degree of 0, meaning they have no dependencies and can be executed immediately.
For each such node, the traverse function is called.

3. Traversing the Graph
When a node is visited, its label is printed.
For each child of the current node:
The in-degree of the child is decreased.
If the in-degree becomes 0, this means all dependencies of the child have been resolved.
A new thread is created to traverse this child node.
After starting all child threads, the current thread waits for all of them to complete using join().

Multithreading Behavior
Each node may spawn new threads for its children, but only when those children are ready to be processed (in-degree is 0).
Threads run in parallel where possible, enabling concurrent execution of independent parts of the graph.
The synchronized keyword ensures thread-safe updates to shared data structures (inDegree and visited), avoiding race conditions.

Main Thread: traverse(1)
    ├── Thread-1: traverse(2)
    │     └── Thread-3: traverse(4)
    └── Thread-2: traverse(3)
          └── Thread-4: traverse(5)

