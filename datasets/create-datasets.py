import networkx as nx

def generate_and_save_graph(n, m, filename, seed=20160):
    # Create a random graph
    G = nx.gnm_random_graph(n, m, seed=seed)

    # some properties
    print(f"Graph with {n} nodes and {m} edges")
    print("node degree clustering")
    for v in nx.nodes(G):
        print(f"{v} {nx.degree(G, v)} {nx.clustering(G, v)}")

    print("\nThe edge list")
    # Write edge list to a file
    with open(filename, 'wb') as file:  # Notice 'wb' instead of 'w'
        nx.write_edgelist(G, file, data=False)
        for line in nx.generate_edgelist(G, data=False):
            print(line)

# Parameters for small, medium, and large datasets
params = [(16, 20, "small_graph_edgelist.txt"),
          (18, 26, "medium_graph_edgelist.txt"),
          (20, 35, "large_graph_edgelist.txt")]

# Generate and save graphs
for n, m, filename in params:
    generate_and_save_graph(n, m, filename)