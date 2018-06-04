#include <iostream>
 
using namespace std;
 
struct Node {
    Node *parent;
    Node *left;
    Node *right;
    int key;
 
    Node(Node *parent, Node *left, Node *right, int key) : parent(parent), left(left), right(right), key(key) {}
};
 
 
bool exists(Node *v, int x);
 
void InsertRec(Node *v, int x);
 
void deleteI(Node* v);
 
void deleteFirst(int x);
 
void InsertIter(Node *v, int x);
 
Node *prev(int x);
Node* find(Node *v, int x);
 
Node *next(int x);
 
Node *glava = nullptr;
 
int main() {
    string komand;
    while (cin >> komand) {
        if (komand == "insert") {
            int x;
            cin >> x;
            if (glava != nullptr) {
                InsertRec(glava, x);
                //InsertIter(glava, x);
            } else {
                glava = new Node(nullptr, nullptr, nullptr, x);
            }
        } else if (komand == "delete") {
            int x;
            cin >> x;
            deleteFirst(x);
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
                cout << temp->key << endl;
            }
        } else if (komand == "prev") {
            int x;
            cin >> x;
            Node *temp = prev(x);
            if (temp == nullptr) {
                cout << "none\n";
            } else {
                cout << temp->key << endl;
            }
        }
    }
}
 
void InsertIter(Node *v, int x) {
    while (v != nullptr) {
        if (x > v->key) {
            if (v->right != nullptr) {
                v = v->right;
            } else {
                v->right = new Node(v, nullptr, nullptr, x);
                break;
            }
        } else if (x < v->key) {
            if (v->left != nullptr) {
                v = v->left;
            } else {
                v->left = new Node(v, nullptr, nullptr, x);
                break;
            }
        }
    }
}
 
void InsertRec(Node *v, int x) {
    if (x > v->key) {
        if (v->right == nullptr) {
            v->right = new Node(v, nullptr, nullptr, x);
        } else {
            InsertRec(v->right, x);
        }
    } else if (x < v->key) {
        if (v->left == nullptr) {
            v->left = new Node(v, nullptr, nullptr, x);
        } else {
            InsertRec(v->left, x);
        }
    }
}
 
void deleteFirst(int x) {
    Node* tmp = find(glava, x);
    if (tmp == nullptr) {
        return;
    }
    deleteI(tmp);
}
 
void deleteI(Node *v) {
    if (v != glava) {
        Node *p = v->parent;
        /*if (p == nullptr) {
            glava = nullptr;
        }*/
        if (v->left == nullptr && v->right == nullptr) {
            if (p->left == v) {
                p->left = nullptr;
            }
            if (p->right == v) {
                p->right = nullptr;
            }
        } else if (v->left == nullptr || v->right == nullptr) {
            if (v->left == nullptr) {
                if (p->left == v) {
                    p->left = v->right;
                } else {
                    p->right = v->right;
                    v->right->parent = p;
                }
            } else {
                if (p->left == v) {
                    p->left = v->left;
                } else {
                    p->right = v->left;
                    v->left->parent = p;
                }
            }
        } else {
            Node *temp = next(v->key);
            v->key = temp->key;
            deleteI(temp);
        }
    } else {
        if (v->left == nullptr && v->right == nullptr) {
            glava = nullptr;
        } else if (v->left == nullptr || v->right == nullptr) {
            if (v->left == nullptr) {
                glava = v->right;
            } else {
                glava = v->left;
            }
        } else {
            Node *temp = next(v->key);
            v->key = temp->key;
            deleteI(temp);
        }
    }
}
 
Node* find(Node *v, int x) {
    if (v == nullptr) {
        return nullptr;
    }
    if (x > v->key) {
        if (v->right != nullptr) {
            return find(v->right, x);
        } else {
            return nullptr;
        }
    } else if (x < v->key) {
        if (v->left != nullptr) {
            return find(v->left, x);
        } else {
            return nullptr;
        }
    } else {
        return v;
    }
}
 
bool exists(Node *v, int x) {
    if (v == nullptr) {
        return false;
    }
    if (x > v->key) {
        if (v->right != nullptr) {
            return exists(v->right, x);
        } else {
            return false;
        }
    } else if (x < v->key) {
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
        if (now->key > x) {
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
        if (now->key >= x) {
            now = now->left;
        } else {
            answer = now;
            now = now->right;
        }
    }
    return answer;
}
