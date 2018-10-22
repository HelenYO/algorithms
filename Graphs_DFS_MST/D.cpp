#include <algorithm>
#include <vector>
#include <iostream>

using namespace std;

const int MAXN = 20000;
const int MAXM = 200000;
vector<pair<int, int>> g[MAXN];
bool used[MAXN]{};
bool used_in_count[MAXN]{};
int timer, tin[MAXN], tout[MAXN];
int n, m;
vector<int> answer;
vector<bool> isBridge;
int maxcount = 0;


void dfs(int v, int ed = -1) {
    used[v] = true;
    tin[v] = tout[v] = timer++;
    for (int i = 0; i < g[v].size(); ++i) {
        int to = g[v][i].first;
        if (ed != g[v][i].second) {
            if (used[to])
                tout[v] = min(tout[v], tin[to]);
            else {
                dfs(to, g[v][i].second);
                tout[v] = min(tout[v], tout[to]);
                if (tout[to] > tin[v]) {
                    isBridge[g[v][i].second] = true;
                }
            }
        }
    }
}

void find_bridges() {
    timer = 0;
    for (int i = 0; i < n; ++i) {
        if (!used[i]) {
            dfs(i);
        }
    }
}

void dfs_to_count(int v, int count) {
    used_in_count[v] = true;
    for (int i = 0; i < g[v].size(); ++i) {
        int to = g[v][i].first;
        if (!used_in_count[to]) {
            if (!isBridge[g[v][i].second]) {
                answer[g[v][i].first] = count;
                dfs_to_count(to, count);
            } else {
                maxcount++;
                answer[g[v][i].first] = maxcount;
                dfs_to_count(to, maxcount);
            }
        }
    }
}

void do_count() {
    answer.resize(n, 0);
    for (int i = 0; i < n; ++i) {
        if (!used_in_count[i]) {
            ++maxcount;
            answer[i] = maxcount;
            dfs_to_count(i, maxcount);
        }
    }
}

int main() {
    cin >> n >> m;
    for (int i = 1; i <= m; ++i) {
        int to, from;
        cin >> from >> to;
        g[from - 1].emplace_back(to - 1, i);
        g[to - 1].emplace_back(from - 1, i);
    }
    isBridge.resize(m + 1, false);
    find_bridges();
    do_count();
    cout << maxcount << "\n";
    for (int i = 0; i < n; ++i) {
        cout << answer[i] << " ";
    }

}

/*
5 8 1 2 1 3 1 4 1 5 4 5 3 4 1 2 1 2
 */
