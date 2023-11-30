package comp5017_CW2;

import java.util.ArrayList;

/**
 *
 * @author Ben Foster (19152077)
 * template for use of students by D Lightfoot 2023-11
 */

public class StaffBST implements IStaffDB {

    private class Node {
        Employee data;
        Node left, right;
        private Node(Employee m) { data = m; left = null; right = null; }
    }
    private Node root; // this is the tree
    private int numEntries;
    ArrayList<String> list;

    public StaffBST() {
        System.out.println("Binary Search Tree");
        clearDB();
    }

    /**
     * Empties the database.
     * @pre true
     */
    @Override
    public void clearDB() {
        root = null; // garbage collector will tidy up
        numEntries = 0;
        list = new ArrayList<>();
    }

    /**
     * Determines whether a member's name exists as a key inside the database
     * @pre name is not null and not empty string or all blanks
     * @param name the member name (key) to locate
     * @return true iff the name exists as a key in the database
     */
    @Override
    public boolean containsName(String name){ return get(name) != null; }

    private Employee retrieve (Node tree, String name) {
        if (tree != null) {
            list.add(tree.data.getName());
            if (!name.equals(tree.data.getName())) {
                if (name.compareTo(tree.data.getName()) < 0) {
                    retrieve(tree.left, name);
                } else {
                    retrieve(tree.right, name);
                }
            }
            else return tree.data;
        }
        return null;
    }
    /*
    Node ptr = tree;
    while (ptr != null && !ptr.data.getName().equals(name)) {
            if (name.compareTo(ptr.data.getName()) < 0)
                ptr = ptr.left;
            else
                ptr = ptr.right;
            list.add(ptr.data.getName());
        if (ptr != null) {

            }
     */
    /**
     * Returns an Employee object mapped to the supplied name.
     * @pre name not null and not empty string or all blanks
     * @param name The Employee name (key) to locate
     * @return the Employee object mapped to the key name if the name
    exists as key in the database, otherwise null
     */
    @Override
    public Employee get(String name){
        assert name != null && !name.isBlank();
        Employee gotten = retrieve(root, name);
        System.out.print("---Get---");
        showLog(list);
        return gotten;
    }

    /**
     * Returns the number of members in the database
     * @pre true
     * @return number of members in the database.
     */
    @Override
    public int size() {return numEntries;}

    /**
     * Determines if the database is empty or not.
     * @pre true
     * @return true iff the database is empty
     */
    @Override
    public boolean isEmpty() { return size() == 0; }

    private Node insert(Node tree, Employee employee) {
        String key = employee.getName();
        if (tree == null) {
            tree = new Node(employee);
        } else if (key.compareTo(tree.data.getName()) > 0) {
            list.add(tree.data.getName());
            tree.right = insert(tree.right,employee);
        } else if (key.compareTo(tree.data.getName()) < 0) {
            list.add(tree.data.getName());
            tree.left = insert(tree.left, employee);
        } else {
            numEntries--;
            list.add(tree.data.getName());
            return new Node(employee); //overwrites duplicate with no increase to numEntries.
        }
        return tree;
    }

    /**
     * Inserts an Employee object into the database, with the key of the supplied
     * member's name.
     * Note: If the name already exists as a key, then the original entry
     * is overwritten.
     * This method must return the previous associated value
     * if one exists, otherwise null
     *
     * @pre member not null and member name not empty string or all blanks
     */
    @Override
    public Employee put(Employee member){
        assert member != null && !member.getName().isBlank();
        Employee previous = get(member.getName());
        root = insert(root, member);
        numEntries++;
        System.out.print("---Put---");
        showLog(list);
        return previous;
    }

    /**
     * Removes and returns a member from the database, with the key
     * the supplied name.
     * @param name The name (key) to remove.
     * @pre name not null and name not empty string or all blanks
     * @return the removed member object mapped to the name, or null if
     * the name does not exist.
     */
    @Override
    public Employee remove(String name){
    /* based on Object-Oriented Programming in Oberon-2
       Hanspeter Mössenböck Springer-Verlag 1993, page 78
       transcribed into Java by David Lightfoot
    */
        assert name != null && !name.isBlank();
        ArrayList<String> list = new ArrayList<>();
        Node parent = null, del, p = null, q = null;
        Employee result;
        del = root;

        while (del != null && !del.data.getName().equals(name)) {
            list.add(del.data.getName());
            parent = del;
            if (name.compareTo(del.data.getName()) < 0) {
                del = del.left;
            }
            else {
                del = del.right;
            }
        }// del == null || del.data.getName().equals(name))
        if(del != null) { // del.data.getName().equals(name)
            // find the pointer p to the node to replace del
            if (del.right == null) {
                p = del.left;
            }
            else if (del.right.left == null) {
                p = del.right; p.left = del.left;
            } else {
                p = del.right;
                while (p.left != null) {
                    q = p; p = p.left;
                }
                q.left = p.right; p.left = del.left; p.right = del.right;
            }

            assert parent != null;
            if(del == root) {root = p;}
            else if (del.data.getName().compareTo(parent.data.getName()) < 0) {
                parent.left = p;
            }
            else {parent.right = p;}

            numEntries--;
            result = del.data; list.add(del.data.getName());
            System.out.println("Employee deleted: " + result.getName());
        }
        else result = null;
        System.out.print("---Remove---");
        showLog(list);
        return result;
    } // delete
    private void showLog(ArrayList<String> list) {
        System.out.print("\nSequence of Nodes visited:\n[");
        if (list.isEmpty()) {
            System.out.print("None");
        }
        else {
            for (String i : list) {
                System.out.print("(" + i + ") ");
            }
        }
        System.out.println("]\n");
        list.clear();
    }
    private void traverse(Node tree) {
        if (tree != null) {
            traverse(tree.left);
            System.out.println("\n" + tree.data);
            traverse(tree.right);
        }
        else System.out.println("The tree is empty");
    }

    /**
     * Prints the names and affiliations of all the members in the database in
     * alphabetic order.
     * @pre true
     */
    @Override
    public void displayDB(){traverse(root);}
}

