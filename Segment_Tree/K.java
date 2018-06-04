//
// Created by Елена on 22.04.18.
//
#include <vector>
#include <string>
#include <ios>
#include <iostream>
#include <tgmath.h>
 
using namespace std;
 
int n;
int P;
 
vector<long long> sum;
vector<long long> hashI;
vector<long long> prevSum;
vector<long long> hashsums;
vector<long long> pred;
vector<int> a;
int prosto = 1000000000;
 
void build(vector<int> const& a, int v, int tl, int tr) {
    if (tl == tr) {
        sum[v] = a[tl];
        hashI[v] = pred[a[tl] - 1];
    } else {
        int tm = (tl + tr) / 2;
        build(a, v * 2, tl, tm);
        build(a, v * 2 + 1, tm + 1, tr);
        sum[v] = sum[v * 2] + sum[v * 2 + 1];
        hashI[v] = (hashI[v * 2] + hashI[v * 2 + 1]);
    }
}
 
void set(int v, int tl, int tr, int index, int d) {
    if (tl == tr) {
        sum[v] = d;
        hashI[v] = pred[d - 1];
    } else {
        int tm = (tl + tr) / 2;
        if (index <= tm) {
            set(v * 2, tl, tm, index, d);
        } else {
            set(v * 2 + 1, tm + 1, tr, index, d);
        }
        sum[v] = sum[v * 2] + sum[v * 2 + 1];
        hashI[v] = (hashI[v * 2] + hashI[v * 2 + 1]);
    }
}
 
pair<long long, long long> summi(int v, int tl, int tr, int l, int r) {
    pair<long long, long long> summ;
 
    if (l > r) {
        return {0, 0};
    }
    if (l == tl && r == tr) {
        summ.first = sum[v];
        summ.second = hashI[v];
        return summ;
    }
    int tm = (tl + tr) / 2;
    pair<long long, long long> temp = summi(v * 2, tl, tm, l, min(r, tm));
    pair<long long, long long> tempi = summi(v * 2 + 1, tm + 1, tr, max(l, tm + 1), r);
    summ.first = temp.first + tempi.first;
    summ.second = (temp.second + tempi.second) ;
    return summ;
}
 
int main() {
    std::ios_base::sync_with_stdio(false);
//    freopen("in.txt", "r", stdin);
//    freopen("out.txt", "w", stdout);
    freopen("permutation.in", "r", stdin);
    freopen("permutation.out", "w", stdout);
 
    P = 31;
    cin >> n;
 
    a.resize(n);
    prevSum.resize(static_cast<unsigned long>(n));
    hashsums.resize(static_cast<unsigned long>(n));
    sum.resize(static_cast<unsigned long>(4 * n));
    hashI.resize(static_cast<unsigned long>(4 * n));
 
    for (int j = 0; j < n; ++j) {
        cin >> a[j];
    }
 
    pred.resize(n);
    prevSum.resize(n);
    hashsums.resize(n);
    long long temp = P;
    prevSum[0] = 1;
    hashsums[0] = P;
    for (int i = 0; i < n; i++) {
        pred[i] = temp;
        temp *= P;
    }
    temp = P * P;
    for (int i = 2; i <= n; i++) {
        prevSum[i - 1] = prevSum[i - 2] + i;
        hashsums[i - 1] = hashsums[i - 2] + temp;
        temp *= P;
    }
    build(a, 1, 0, n - 1);
 
    int m;
    cin >> m;
    for (int i = 0; i < m; i++) {
        int t;
        cin >> t;
        if (t == 1) {
            int temp;
            int tempi;
            cin >> temp >> tempi;
            set(1, 0, n - 1, temp - 1, tempi);
        } else {
            int l;
            int r;
            cin >> l >> r;
            l--;
            r--;
            pair<long long, long long> temp = summi(1, 0, n - 1, l, r);
            if (temp.first == prevSum[r - l] && temp.second == hashsums[r - l ]) {
                cout << "YES" << "\n";
            } else {
                cout << "NO" << "\n";
            }
        }
    }
 
    return 0;
}
