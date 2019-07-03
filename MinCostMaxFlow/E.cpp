#include<iostream>
#include <vector>
#include <list>
#include <set>
#include <limits>

using namespace std;

int MAX = std::numeric_limits<int>::max();

struct Edge {
    long long v;
    long long cost;
    long long flow;
    long long C;
    long long rev;

};

struct Graph {
    long long n;
    vector<Edge> adj[1000];
    vector<long long> dist{};
    set<pair<long long, long long>> queue;
    vector<pair<long long, long long>> parent{};

    Graph(long long V) {
        n = V;
    }

    void addEdge(long long u, long long v, long long C, long long cost) {
        Edge a{v, cost, 0, C, (long long) adj[v].size()};
        Edge b{u, -cost, C, C, (long long) adj[u].size()};

        adj[u].push_back(a);
        adj[v].push_back(b);
    }

    void dijkstra (long long v) {
        parent.resize(n);
        dist.assign(n, MAX);
        dist[v] = 0;
        queue.insert({0, v});
        while(!queue.empty()) {
            pair<long long, long long> temp = *queue.begin();
            queue.erase(queue.begin());
            for (long long i = 0; i < adj[temp.second].size(); ++i) {
                auto& u = adj[temp.second][i];
                if(u.flow == u.C) {
                    continue;
                }
                if (dist[u.v] > dist[temp.second] + u.cost) {
                    queue.erase({dist[u.v], u.v});
                    dist[u.v] = dist[temp.second] + u.cost;
                    parent[u.v] = {temp.second, i};
                    queue.insert({dist[u.v], u.v});
                }
            }
        }
    }

    long long find_flow () {
        long long ans = 0;
        while(true) {
            dijkstra(0);
            if (dist[n - 1] >= MAX) {
                return ans;
            }
            long long can_push = MAX;
            long long s = n - 1;
            while(s != 0) {
                auto& ed  = adj[parent[s].first][parent[s].second];
                can_push = min(can_push, ed.C - ed.flow);
                s = parent[s].first;
            }
            s = n - 1;
            while(s != 0) {
                auto& ed  = adj[parent[s].first][parent[s].second];
                ed.flow += can_push;
                adj[ed.v][ed.rev].flow -= can_push;
                ans += can_push * ed.cost;
                s = parent[s].first;
            }

        }
    }

};

int main() {
    long long n, m;
    cin >> n >> m;
    Graph g(2 * n + 2);

    for (int i = 1; i <= n; ++i) {
        int a;
        cin >> a;
        g.addEdge(0, n + i, 1, 0);
        g.addEdge(i, n + n + 1, 1, 0);
        g.addEdge(i, n + i, MAX, 0);
        g.addEdge(n + i, i, MAX, a);
    }

    for (long long i = 0; i < m; ++i) {
        long long a, b, c;
        cin >> a >> b >> c;
        g.addEdge(n + a, b, MAX, c);
    }

    cout << g.find_flow() << endl;

    return 0;
}