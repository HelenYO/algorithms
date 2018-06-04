//
// Created by Елена on 27.04.18.
//
 
 
#include <iostream>
#include <vector>
#include <algorithm>
 
using namespace std;
 
struct Node {
    Node* parent;
    Node* left;
    Node* right;
    int number;
    int x;
    int y;
 
    Node(Node *parent, Node *left, Node *right, int number, int x, int y) : parent(parent), left(left), right(right),
                                                                            number(number), x(x), y(y) {}
};
 
bool comp(Node* a, Node* b)
{
    return (a->x - b->x) < 0;
}
 
Node* findLeft(Node* v, int x) {
    if (v->parent != nullptr) {
        v = v->parent;
        if (v->y < x) {
            return v->right;
        } else {
            return findLeft(v, x);
        }
    } else {
        return v;
    }
}
 
Node* root = nullptr;
//Node* prev = nullptr;
 
int main() {
    int n;
    cin >> n;
    vector<Node*> begin(n);
    vector<Node*> tree(n);
    for (int i = 0; i < n; i++) {
        int a, b;
        cin >> a >> b;
        begin[i] = new Node(nullptr, nullptr, nullptr, i + 1, a, b);
        tree[i] = begin[i];
    }
 
    sort(tree.begin(),tree.end(), comp);
 
    root = tree[0];
    for(int i = 1; i < n; i ++) {
        if (tree[i]->y > tree[i-1]->y) {
            tree[i-1]->right = tree[i];
            tree[i]->parent = tree[i-1];
        } else if(tree[i-1]->parent == nullptr) {
            tree[i]->left = tree[i-1];
            tree[i-1]->parent = tree[i];
            //cout << i << "\n";
            root = tree[i];
        } else {
            Node* tempLeft = findLeft(tree[i - 1], tree[i]->y);
            if(tempLeft->parent == nullptr) {
                root = tree[i];
                tempLeft->parent = tree[i];
                tree[i]->left = tempLeft;
            } else {
                tempLeft->parent->right = tree[i];
                tree[i]->parent = tempLeft->parent;
                tempLeft->parent = tree[i];
                tree[i]->left = tempLeft;
            }
        }
    }
 
    cout<<"YES"<<endl;
 
    for (int i = 0; i < n;i++) {
 
        if(begin[i]->parent== nullptr) {
            cout << 0 << " ";
        } else {
            cout << begin[i]->parent->number << " ";
        }
        if(begin[i]->left== nullptr) {
            cout << 0 << " ";
        } else {
            cout << begin[i]->left->number << " ";
        }
        if(begin[i]->right== nullptr) {
            cout << 0 << endl;
        } else {
            cout << begin[i]->right->number << endl;
        }
    }
}
