#include<iostream>
#include <vector>
#include <list>

using namespace std;

struct Edge {
    int v;
    int flow;
    int C;
    int rev;
};

vector<pair<int, int>> edg;

struct Graph {
    int V;
    vector<int> level;
    vector<Edge> adj[120];


    Graph(int V) {
        this->V = V;
        level.assign(V, -1);
    }

    void addEdge(int u, int v, int C) {
        Edge a{v, 0, C, (int) adj[v].size()};
        Edge b{u, 0, C, (int) adj[u].size()};

        adj[u].push_back(a);
        adj[v].push_back(b);

        edg.push_back({u, adj[u].size() - 1});
    }

    bool BFS(int s, int t) {
        level.assign(V, -1);
        level[s] = 0;

        list<int> q;
        q.push_back(s);

        while (!q.empty()) {
            int u = q.front();
            q.pop_front();
            for (auto e: adj[u]) {
                if (level[e.v] < 0 && e.flow < e.C) {
                    level[e.v] = level[u] + 1;
                    q.push_back(e.v);
                }
            }
        }
        return (level[t] >= 0);
    }

    int dinic(int s, int t) {
        if (s == t) {
            return -1;
        }
        int total = 0;
        while (BFS(s, t)) {
            vector<int> start(V + 1);
            while (int flow = sendFlow(s, INT_MAX, t, start)) {
                total += flow;
            }
        }
        return total;
    }

    int sendFlow(int u, int flow, int t, vector<int> &start) {
        if (u == t)
            return flow;

        for (int i = start[u]; i < adj[u].size(); i++) {
            Edge &e = adj[u][i];

            if (level[e.v] == level[u] + 1 && e.flow < e.C) {
                int now_flow = min(flow, e.C - e.flow);

                int maybe_flow = sendFlow(e.v, now_flow, t, start);

                if (maybe_flow > 0) {
                    e.flow += maybe_flow;
                    adj[e.v][e.rev].flow -= maybe_flow;
                    return maybe_flow;
                }
            }
            start[u]++;
        }
        return 0;
    }
};

int main() {
    int n, m;
    cin >> n >> m;
    Graph g(n);
    for (int i = 0; i < m; ++i) {
        int a, b, c;
        cin >> a >> b >> c;
        g.addEdge(a - 1, b - 1, c);
    }

    cout << g.dinic(0, n - 1) << endl;
    for (int i = 0; i < m; ++i) {
        cout << g.adj[edg[i].first][edg[i].second].flow << endl;
    }
    return 0;
}
