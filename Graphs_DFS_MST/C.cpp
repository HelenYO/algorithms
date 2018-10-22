#include <algorithm>
#include <vector>
#include <iostream>
#include <set>

using namespace std;

const int MAXN = 20000;
const int MAXM = 200000;
vector<pair<int, int>> g[MAXN];
bool used[MAXN]{};
//bool used_in_count[MAXN]{};
int timer, tin[MAXN], tout[MAXN];
int n, m;
//vector<int> answer;
set<int> answer;
vector<bool> isBridge;
//int maxcount = 0;


void dfs(int v, int ed = -1) {
    used[v] = true;
    tin[v] = tout[v] = timer++;
    int count_for_root = 0;
    for (int i = 0; i < g[v].size(); ++i) {
        int to = g[v][i].first;
        if (ed != g[v][i].second) {
            if (used[to])
                tout[v] = min(tout[v], tin[to]);
            else {
                dfs(to, g[v][i].second);
                count_for_root++;
                tout[v] = min(tout[v], tout[to]);
                if (ed != -1 && tout[to] >= tin[v]) {
                    //isBridge[g[v][i].second] = true;
                    //answer.push_back(v);
                    answer.emplace(v);
                }
                if(ed == -1 && count_for_root > 1) {
                    //cout << "in p " << v << "\n";
                    answer.emplace(v);
                }
            }
        }
    }
}

void find_points() {
    timer = 0;
    for (int i = 0; i < n; ++i) {
        if (!used[i]) {
            dfs(i);
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
    find_points();
    //do_count();
    //cout << maxcount << "\n";
    cout << answer.size() << "\n";
    for (int i:answer) {
        cout << (i + 1) << " ";
    }

}

/*
5 8 1 2 1 3 1 4 1 5 4 5 3 4 1 2 1 2
 */

/*
 4 3
1 2 2 3 2 4
 */

/*
 4 4
1 2 2 3 2 4 3 4
 */
/*
 6 7  1 2 1 3 2 3 2 5 2 4 3 6 4 5
 */
