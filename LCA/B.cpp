#include <iostream>
#include <vector>
#include <cmath>
 
using namespace std;
vector<vector<int>> children(0);
vector<int> dep;
vector<vector<int>> parentsBin(0);
vector<vector<int>> minimum(0);
int lg;
int djerri_li = 2147483647;
 
void dfs(int v, int h) {
    dep[v] = h;
    for (int child : children[v]) {
        dfs(child, h + 1);
    }
}
 
int lca(int v, int u) {
    if (dep[v] > dep[u]) {
        int tmp = u;
        u = v;
        v = tmp;
    }
    for (int i = lg; i >= 0; i--) {
        if (dep[parentsBin[u][i]] >= dep[v]) {
            u = parentsBin[u][i];
        }
    }
    if (v == u) {
        return v;
    }
    for (int i = lg; i >= 0; i--) {
        if (parentsBin[v][i] != parentsBin[u][i]) {
            v = parentsBin[v][i];
            u = parentsBin[u][i];
        }
    }
    return parentsBin[v][0];
}
 
 
int minSearch(int u, int v) {
    int predok = lca(u, v);
    int answer = djerri_li;
    //cout << lg << "\n";
    for (int i = lg; i >= 0; i--) {
        if (dep[parentsBin[u][i]] - dep[predok] >= 0) {
            answer = min(answer, minimum[u][i]);
            //cout << "u  "  << answer << " " << u << " " << i << '\n';
            u = parentsBin[u][i];
        }
    }
    //answer = min(answer, minimum[u][0]);
 
    for (int i = lg; i >= 0; i--) {
        if (dep[parentsBin[v][i]] - dep[predok] >= 0) {
            answer = min(answer, minimum[v][i]);
            //cout << "v  "  << answer << " " << v << " " << i << '\n';
            v = parentsBin[v][i];
        }
    }
    //answer = min(answer, minimum[v][0]);
    return answer;
}
 
int main() {
    std::ios_base::sync_with_stdio(false);
    freopen("minonpath.out","w",stdout);
    freopen("minonpath.in","r",stdin);
    int n;
    cin >> n;
    for (int i = 0; i < n + 1; i++) {
        vector<int> temp(0);
        children.push_back(temp);
    }
    dep.resize(n + 1);
    lg = ceil(log2(n + 1));
    for (int i = 0; i < n + 1; i++) {
        vector<int> temp(lg + 1);
        parentsBin.push_back(temp);
    }
    for (int i = 0; i < n + 1; i++) {
        vector<int> temp(lg + 1);
        minimum.push_back(temp);
    }
 
    parentsBin[1][0] = 1;
    minimum[1][0] = djerri_li;
    for (int i = 2; i <= n; i++) {
        int tmp;
        cin >> tmp;
        children[tmp].push_back(i);
        parentsBin[i][0] = tmp;
        int tmpPrice;
        cin >> tmpPrice;
        minimum[i][0] = tmpPrice;
    }
    dfs(1, 0);
 
    for (int j = 1; j < lg + 1; j++) {
        for (int i = 1; i < n + 1; i++) {
            parentsBin[i][j] = parentsBin[parentsBin[i][j - 1]][j - 1];
            minimum[i][j] = min(minimum[i][j-1], minimum[parentsBin[i][j-1]][j-1]);
        }
    }
 
    int m;
    cin >> m;
    for (int i = 0; i < m; i++) {
        int u, v;
        cin >> u >> v;
        cout <<  minSearch(v, u) << "\n";
    }
}
 
/*
 *
 * 5 1 1 1 2 2 3 3 4 2 1 4 3 2
 */
