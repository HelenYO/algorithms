#include <iostream>
 
using namespace std;
 
struct Node {
    Node *left;
    Node *right;
    int x;
    int y;
 
    Node(Node *left, Node *right, int x, int y) : left(left), right(right), x(x), y(y) {}
};
 
Node *glava = nullptr;
 
bool exists(Node *v, int x);
 
 
Node *prev(int x);
 
 
Node *next(int x);
 
 
Node *Merge(Node *L, Node *R) {
    if (L == nullptr) return R;
    if (R == nullptr) return L;
 
    if (L->y > R->y) {
        Node *newR = Merge(L->right, R);
        return new Node(L->left, newR, L->x, L->y);
    } else {
        Node *newL = Merge(L, R->left);
        return new Node(newL, R->right, R->x, R->y);
    }
}
 
pair<Node *, Node *> split(Node *v, int x) { // <,  >=
    if (v == nullptr) {
        return make_pair(nullptr, nullptr);//
    } else if (x > v->x) {
        pair<Node *, Node *> p = split(v->right, x);
        v->right = p.first;
        return make_pair(v, p.second);
    } else {
        pair<Node *, Node *> p = split(v->left, x);
        v->left = p.second;
        return make_pair(p.first, v);
    }
}
 
void insert(int x) {
    if(exists(glava, x)) {
        return;
    }
    pair<Node*, Node*> p = split(glava, x);
    Node *m = new Node(nullptr, nullptr, x, rand());
    glava = Merge(Merge(p.first, m), p.second);
}
 
Node *remove(int x) {
    pair<Node*, Node*> p = split(glava, x);
    pair<Node*, Node*> p2 = split(p.second, x + 1);
    return glava = Merge(p.first, p2.second);
}
 
int main() {
    string komand;
    while (cin >> komand) {
        if (komand == "insert") {
            int x;
            cin >> x;
            if (glava != nullptr) {
                insert(x);
            } else {
                glava = new Node(nullptr, nullptr, x, rand());
            }
        } else if (komand == "delete") {
            int x;
            cin >> x;
            remove(x);
        } else if (komand == "exists") {
            int x;
            cin >> x;
            if (exists(glava, x)) {
                cout << "true" << endl;
            } else {
                cout << "false" << endl;
            }
        } else if (komand == "next") {
            int x;
            cin >> x;
            Node *temp = next(x);
            if (temp == nullptr) {
                cout << "none\n";
            } else {
                cout << temp->x << endl;
            }
        } else if (komand == "prev") {
            int x;
            cin >> x;
            Node *temp = prev(x);
            if (temp == nullptr) {
                cout << "none\n";
            } else {
                cout << temp->x << endl;
            }
        }
    }
}
 
bool exists(Node *v, int x) {
    if (v == nullptr) {
        return false;
    }
    if (x > v->x) {
        if (v->right != nullptr) {
            return exists(v->right, x);
        } else {
            return false;
        }
    } else if (x < v->x) {
        if (v->left != nullptr) {
            return exists(v->left, x);
        } else {
            return false;
        }
    } else {
        return true;
    }
}
 
Node *next(int x) {
    Node *now = glava;
    Node *answer = nullptr;
    while (now != nullptr) {
        if (now->x > x) {
            answer = now;
            now = now->left;
        } else {
            now = now->right;
        }
    }
    return answer;
}
 
Node *prev(int x) {
    Node *now = glava;
    Node *answer = nullptr;
    while (now != nullptr) {
        if (now->x >= x) {
            now = now->left;
        } else {
            answer = now;
            now = now->right;
        }
    }
    return answer;
}
