//
// Created by Елена on 2019-04-05.
//

#include <iostream>
#include <vector>
#include <queue>
#include <limits>
#include <memory>
#include <set>

const int MAXN = 6000;
int INF = 50 * 50;

using namespace std;

char map[100][100]{};

bool is_neighbours(int i1, int y1, int i2, int y2) {
    return (abs(i1 - i2) == 1 && abs(y1 - y2) == 0) ||
           (abs(i1 - i2) == 0 && abs(y1 - y2) == 1);
}

struct Edge
{
    int to;
    int f;
    int c;
    int r;

    Edge(int to, int f, int c, int r) : to(to), f(f), c(c), r(r) {}
};

struct Network
{
    int N;
    int AFrom;
    int BInto;
    int AInto;
    int BFrom;
    vector<Edge> adj[MAXN];
    vector<bool> used{};
    vector<bool> attainable;
    vector<int> deletedV;

    Network(int N) : N(N) {}

    void add_oriented_edge(int from, int to, int c) {
        int from_size = adj[from].size();
        int to_size = adj[to].size();
        adj[from].emplace_back(to, 0, c, to_size);
        adj[to].emplace_back(from, 0, 0, from_size);
    }


    int ford(int u, int Cmin) {
        used[u] = true;
        if (u == BInto) {
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

    void dfs_fill_attainable(int s) {
        attainable[s] = true;
        for (auto& e : adj[s]) {
            if (!attainable[e.to] && e.c > e.f) {
                dfs_fill_attainable(e.to);
            }
        }
    }

    void dfs_attainable(int s) {
        used[s] = true;
        for (auto& e : adj[s]) {
            if (e.c != 0 && !attainable[e.to] && e.c == e.f) {
                deletedV.push_back(s);
            }
        }

        for (auto& e : adj[s]) {
            if (!used[e.to] && e.c > e.f) {
                dfs_attainable(e.to);
            }
        }
    }

    void mark_attainable() {
        used.assign((unsigned int) N + 1, false);
        attainable.assign(N + 1, false);
        dfs_fill_attainable(AFrom);
        dfs_attainable(AFrom);
    }

    void fill_map(int m, int n) {
        for (auto& v : deletedV) {
            int x = v / n;
            int y = v % n;
            map[x][y] = '+';
        }
    }
};

int main() {
    int m, n;
    cin >> m >> n;
    Network Net(n * m * 2);
    for (int i = 0; i < m; ++i) {
        for (int j = 0; j < n; ++j) {
            int vInto = n * i + j;
            int vFrom = n * m + vInto;
            char ch;
            cin >> ch;
            map[i][j] = ch;
            if (ch == 'A') {
                Net.AFrom = vFrom;
                Net.AInto = vInto;
            } else if (ch == 'B') {
                Net.BInto = vInto;
                Net.BFrom = vFrom;
            }
        }
    }

    for (int i = 0; i < m; ++i) {
        for (int j = 0; j < n; ++j) {
            int vInto = n * i + j;
            int vFrom = n * m + vInto;
            vector<int> neighbours = {vInto - 1,
                                           vInto + 1,
                                           vInto - n,
                                           vInto + n};
            for (int k = 0; k < 4; ++k) {
                if (!(neighbours[k] >= 0 && neighbours[k] <= n * m - 1) ||
                    neighbours[k] == Net.AInto ||
                    vInto == Net.BInto ||
                    !is_neighbours(i, j, neighbours[k] / n, neighbours[k] % n) ||
                    map[neighbours[k] / n][neighbours[k] % n] == '#') {
                    neighbours[k] = -1;
                }
            }
            char ch = map[i][j];
            switch (ch) {
                case '#':
                    break;
                case '.':
                    Net.add_oriented_edge(vInto, vFrom, 1);
                    for (int k = 0; k < 4; ++k) {
                        if (neighbours[k] != -1) {
                            Net.add_oriented_edge(vFrom, neighbours[k], INF);
                        }
                    }
                    break;
                case '-':
                case 'A':
                case 'B':
                    Net.add_oriented_edge(vInto, vFrom, INF);
                    for (int k = 0; k < 4; ++k) {
                        if (neighbours[k] != -1) {
                            Net.add_oriented_edge(vFrom, neighbours[k], INF);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    int max_f = 0;
    int delta = 1;
    while (delta != 0) {
        Net.used.assign(static_cast<unsigned long>(Net.N * 2), false);
        max_f += delta = Net.ford(Net.AInto, INF);
    }
    if (max_f == 0) {
        cout << max_f << endl;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                cout << map[i][j];
            }
            cout << endl;
        }
    } else if (max_f > 0 && max_f <= 50 * 50 - 2) {
        Net.mark_attainable();
        Net.fill_map(m, n);
        cout << max_f << endl;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                cout << map[i][j];
            }
            cout << endl;
        }
    } else {
        cout << -1 << endl;
    }

    return 0;
}