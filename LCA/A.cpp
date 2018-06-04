#include <iostream>
#include <vector>
#include <cmath>
 
using namespace std;
vector<vector<int>> children(0);
vector<int> parent;
vector<int> depth;
vector<vector<int>> dp(0);
int lg;
 
void dfs(int v, int height) {
    depth[v] = height;
    for (int i = 0; i < children[v].size(); i++) {
        int child = children[v][i];
        dfs(child, height + 1);
    }
}
 
int lca(int v, int u) {
    if (v == u) {
        return v;
    }
    if (depth[v] > depth[u]) {
        int tmp = u;
        u = v;
        v = tmp;
    }
    for (int i = lg; i >= 0; i--) {
        if (depth[dp[u][i]] - depth[v] >= 0) {
            u = dp[u][i];
        }
    }
    if (v == u) {
        return v;
    }
    for (int i = lg; i >= 0; i--) {
        if (dp[v][i] != dp[u][i]) {
            v = dp[v][i];
            u = dp[u][i];
        }
    }
    return parent[v];
}
 
int main() {
    int n;
    cin >> n;
    for (int i = 0; i < n + 1; i++) {
        vector<int> temp(0);
        children.push_back(temp);
    }
    parent.resize(n + 1);
    depth.resize(n + 1);
    lg = (int) ceil(log(n + 1) / log(2));
    for (int i = 0; i < n + 1; i++) {
        vector<int> temp(lg + 1);
        dp.push_back(temp);
    }
    parent[1] = 1;
    for (int i = 2; i <= n; i++) {
        int tmp;
        cin >> tmp;
        children[tmp].push_back(i);
        parent[i] = tmp;
    }
    dfs(1, 1);
    for (int i = 1; i < n + 1; i++) {
        dp[i][0] = parent[i];
    }
    for (int j = 1; j < lg + 1; j++) {
        for (int i = 1; i < n + 1; i++) {
            dp[i][j] = dp[dp[i][j - 1]][j - 1];
        }
    }
 
    int m;
    cin >> m;
    for (int i = 0; i < m; i++) {
        int u, v;
        cin >> u >> v;
        cout << lca(v, u) << "\n";
    }
}
/*
 * 5 1 1 2 3 2 2 3 4 5 
 */
