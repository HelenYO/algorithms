//
// Created by Елена on 2019-04-01.
//

#include <iostream>
#include <vector>
#include <set>
#include <climits>

using namespace std;

long long INF = 10000000500;

struct box {
    long long x1, y1, x2, y2;

    box(long long x1, long long y1, long long x2, long long y2) : x1(x1), y1(y1), x2(x2), y2(y2) {}
};

vector<box> boxes{};


struct graph {
public:
    long long n = 0;
    long long w = 0;
    vector<long long> g[5005];
    vector<long long> dist;
    vector<bool> visited;

    void dijkstra() {
        dist.assign(n + 2, INF);
        dist[0] = 0;
        visited.assign(n + 2, false);
        for (long long i = 0; i < n + 2; ++i) {
            long long vMin = -1;
            for (long long j = 0; j < n + 2; ++j) {
                if (!visited[j] && (vMin == -1 || dist[j] < dist[vMin])) {
                    vMin = j;
                }
            }
            visited[vMin] = true;
            for (long long u = 0; u < n + 2; ++u) {
                if (vMin != u) {
                    long long dv = g[vMin][u];
                    long long f = dist[vMin] + dv;
                    if (f < dist[u]) {
                        dist[u] = f;
                    }
                }
            }
        }
    }

    graph() {

    }

    long long getDist(long long a, long long b) {
        long long deltaY = boxes[a].y1 > boxes[b].y1 ? (boxes[a].y1 - boxes[b].y2) : (boxes[b].y1 - boxes[a].y2);
        long long deltaX = boxes[a].x1 > boxes[b].x1 ? (boxes[a].x1 - boxes[b].x2) : (boxes[b].x1 - boxes[a].x2);
        return max(0ll, max(deltaY, deltaX));
    }
};


int main() {
    graph G;
    cin >> G.n;
    cin >> G.w;
    for (long long i = 0; i < G.n + 2; i++) {
        G.g[i].assign(G.n + 2, -1);
    }

    auto& boxes_copy = boxes;
    for (long long i = 1; i < G.n + 1; ++i) {
        long long x1, y1, x2, y2;
        cin >> x1 >> y1 >> x2 >> y2;
        boxes.emplace_back(x1, y1, x2, y2);
    }

    for (long long i = 1; i < G.n + 1; ++i) {
        long long distLeft = G.w - max(boxes[i - 1].y1, boxes[i - 1].y2);
        G.g[0][i] = distLeft;
        G.g[i][0] = distLeft;

        long long distRight = min(boxes[i - 1].y1, boxes[i - 1].y2);
        G.g[G.n + 1][i] = distRight;
        G.g[i][G.n + 1] = distRight;
        for (long long j = 1; j < G.n + 1; ++j) {
            if (i != j) {
                long long w = G.getDist(i - 1, j - 1);
                G.g[i][j] = w;
                G.g[j][i] = w;

            }
        }

    }
    G.g[0][G.n + 1] = G.w;
    G.g[G.n + 1][0] = G.w;

    G.dijkstra();
    if (G.dist[G.n + 1] < INF) {
        cout << G.dist[G.n + 1];
    } else {
        cout << 0;
    }
    return 0;
}