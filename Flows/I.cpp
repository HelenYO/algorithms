//
// Created by Елена on 2019-04-04.
//

#include <iostream>
#include <limits>
#include <queue>
#include <vector>

using namespace std;

const int MAX = 10000;
int INF = 100 * 100 * 3;

char ans[MAX][MAX];

void writeAns(int n) {
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            cout << ans[i][j];
        }
        cout << endl;
    }
}

struct Edge {
    int to;
    int f;
    int c;
    int r;

    Edge(int to, int f, int c, int r) : to(to), f(f), c(c), r(r) {}
};

struct Graph {
    int N;
    int s;
    int t;
    int m;
    vector<Edge> adj[MAX];
    vector<bool> used;

    Graph(int N) : N(N) {}

    void setS(int s) {
        this->s = s;
    }

    void setT(int t) {
        this->t = t;
    }

    int ford(int u, int Cmin) {
        used[u] = true;
        if (u == t) {
            return Cmin;
        }
        for (auto &uv : adj[u]) {
            if (!used[uv.to] && uv.f < uv.c) {
                int delta = ford(uv.to, min(Cmin, uv.c - uv.f));
                if (delta > 0) {
                    uv.f += delta;
                    adj[uv.to][uv.r].f -= delta;
                    return delta;
                }
            }
        }
        return 0;
    }

    void addGame(int from, int to) {
        m++;
        int index = N - 1 + m;
        int wasFirstSize = static_cast<int>(adj[from].size());
        int wasSecondSize = static_cast<int>(adj[to].size());
        adj[index].emplace_back(from, 0, 3, wasFirstSize);
        adj[index].emplace_back(to, 0, 3, wasSecondSize);
        adj[from].emplace_back(index, 0, 3, 0);
        adj[to].emplace_back(index, 0, 3, 1);
    }

    void addOrEdge(int from, int to, int c) {
        int fromSize = static_cast<int>(adj[from].size());
        int toSize = static_cast<int>(adj[to].size());
        adj[from].emplace_back(to, 0, c, toSize);
        adj[to].emplace_back(from, 0, 0, fromSize);
    }

    void getInit(vector<int> &initValues, int n) {
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                char ij = ans[i][j];
                if (ij == '.') {
                    addGame(i, j);
                } else if (ij == 'W') {
                    initValues[i] += 3;
                } else if (ij == 'w') {
                    initValues[i] += 2;
                    initValues[j] += 1;
                } else if (ij == 'L') {
                    initValues[j] += 3;
                } else if (ij == 'l') {
                    initValues[i] += 1;
                    initValues[j] += 2;
                }
            }
        }
    }

    void writeAns() {
        for (int i = N - m - 2; i < N - 2; ++i) {
            int flow = adj[i][0].f;
            int from = adj[i][0].to;
            int to = adj[i][1].to;
            if (flow == 0) {
                ans[from][to] = 'L';
                ans[to][from] = 'W';
            } else if (flow == 1) {
                ans[from][to] = 'l';
                ans[to][from] = 'w';
            } else if (flow == 2) {
                ans[from][to] = 'w';
                ans[to][from] = 'l';
            } else if (flow == 3) {
                ans[from][to] = 'W';
                ans[to][from] = 'L';
            }
        }
    }
};


int main() {
    int n;
    cin >> n;
    Graph G(n);
    vector<int> initValues(n, 0);
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            cin >> ans[i][j];
        }
    }
    G.getInit(initValues, n);
    G.setS(G.N + G.m);
    G.setT(G.N + G.m + 1);
    for (int i = 0; i < G.m; ++i) {
        int index = G.N + i;
        G.addOrEdge(G.s, index, 3);
    }

    for (int i = 0; i < n; ++i) {
        int flow;
        cin >> flow;
        G.addOrEdge(i, G.t, flow - initValues[i]);
    }
    G.N += G.m + 2;
    int delta = 1;
    while (delta != 0) {
        G.used.assign(static_cast<unsigned long>(G.N * 2), false);
        delta = G.ford(G.s, INF);
    }
    G.writeAns();
    writeAns(n);

    return 0;
}