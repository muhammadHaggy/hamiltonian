import os
import networkx as nx
import random

def generate_hamiltonian_path_graph(vertices):
    """
    Generates a graph with a guaranteed Hamiltonian path.
    """
    # Create a path graph which is a trivially Hamiltonian
    H = nx.path_graph(vertices)

    # Add additional edges to make the graph more complex
    # while still maintaining the Hamiltonian path
    additional_edges = vertices * 2  # Number of additional edges
    for _ in range(additional_edges):
        v1, v2 = random.sample(range(vertices), 2)
        if not H.has_edge(v1, v2):
            H.add_edge(v1, v2)

    return H

def save_graph_to_file(graph, file_name):
    """
    Saves the graph in edge list format to a file.
    """
    nx.write_edgelist(graph, file_name, data=False)

# Filenames for the datasets
file_names = {
    "small": "hamiltonian_16.txt",
    "medium": "hamiltonian_18.txt",
    "large": "hamiltonian_20.txt"
}

# Vertex counts for each dataset
vertex_counts = {
    "small": 16,
    "medium": 18,
    "large": 20
}

# Generate and save the datasets
for size, file_name in file_names.items():
    if not os.path.exists(file_name):
        vertices = vertex_counts[size]
        graph = generate_hamiltonian_path_graph(vertices)
        save_graph_to_file(graph, file_name)
        print(f"Generated and saved {size} dataset: {file_name}")
    else:
        print(f"File {file_name} already exists. Skipping generation.")