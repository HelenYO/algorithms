#include <iostream>
#include <vector>
#include <cmath>
 
using namespace std;
vector<vector<int>> children(0);
vector<int> dep;
vector<int> nextDino;
vector<bool> isDelete;
vector<vector<int>> parentsBin(0);
int lg;
 
void dfs(int v, int height) {
    dep[v] = height;
    for (int i = 0; i < children[v].size(); i++) {
        int child = children[v][i];
        dfs(child, height + 1);
    }
}
 
int findNext(int v) {
    if (!isDelete[v]) {
        return v;
    }
    nextDino[v] = findNext(nextDino[v]);
    return nextDino[v];
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
        //return v;
        return findNext(v);
    }
    for (int i = lg; i >= 0; i--) {
        if (parentsBin[v][i] != parentsBin[u][i]) {
            v = parentsBin[v][i];
            u = parentsBin[u][i];
        }
    }
    //return parentsBin[v][0];
    return findNext(parentsBin[v][0]);
}
 
void add(int v, int parent) {
    children[parent].push_back(v);
    nextDino[v] = parent;
    dep[v] = dep[parent] + 1;
    parentsBin[v][0] = parent;
    for (int j = 1; j < lg + 1; j++) {
            parentsBin[v][j] = parentsBin[parentsBin[v][j - 1]][j - 1];
    }
}
 
void toDelete(int v) {
    isDelete[v] = true;
}
 
int main() {
    int m;
    cin >> m;
    for (int i = 0; i < m + 1; i++) {
        vector<int> temp(0);
        children.push_back(temp);
    }
    dep.resize(m + 1);
    isDelete.resize(m + 1);
    nextDino.resize(m + 1);
    lg = (int) ceil(log(m + 1) / log(2));
    for (int i = 0; i < m + 1; i++) {
        vector<int> temp(lg + 1);
        parentsBin.push_back(temp);
    }
    parentsBin[1][0] = 1;
    int freeNumber = 2;
    for (int i = 0; i < m; i ++) {
        string whatToDo;
        cin >> whatToDo;
        switch (whatToDo[0]) {
            case '+':
                int parentTmp;
                cin >> parentTmp;
                add(freeNumber, parentTmp);
                freeNumber++;
                break;
            case '?':
                int a, b;
                cin >> a >> b;
                cout << lca(a , b) << "\n";
                break;
            case '-':
                int tmp;
                cin >> tmp;
                toDelete(tmp);
                break;
        }
    }
 
}
/*
 * 11 + 1 + 1 + 2 ? 2 3 ?  1 3 ? 2 4  + 4 + 4 - 4  ? 5 6 ? 5 5
 */
