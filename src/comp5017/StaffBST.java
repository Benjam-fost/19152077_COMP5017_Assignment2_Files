package comp5017;// or whatever

import java.util.ArrayList;

/**
 *
 * @author Ben Foster (19152077)
 * template for use of students by D Lightfoot 2023-11
 */

public class StaffBST implements IStaffDB{

    private class Node {
        Employee data;
        Node left, right;
        private Node(Employee m) { data = m; left = null; right = null; }
    }
    private Node root; // this is the tree
    private int numEntries;

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

        return null; // not really
    }
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
        return retrieve(root, name);
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
        // to do
        return null; // not really
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
        assert member != null;
        Employee returned;
        returned = null; // for now
        root = insert(root, member);
        return returned;
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
    public  Employee remove(String name){
    /* based on Object-Oriented Programming in Oberon-2
       Hanspeter Mössenböck Springer-Verlag 1993, page 78
       transcribed into Java by David Lightfoot
    */
        ArrayList<String> list = new ArrayList<String>();
        list.add(root.data.getName());
        Node parent = null, del, p = null, q = null;
        Employee result;
        del = root;
        while (del != null && !del.data.getName().equals(name)) {
            parent = del;
            if (name.compareTo(del.data.getName()) < 0)
                del = del.left;
            else
                del = del.right;
            list.add(del.data.getName());
        }// del == null || del.data.getName().equals(name))
        if(del != null) { // del.data.getName().equals(name)
            // find the pointer p to the node to replace del
            if (del.right == null) {
                p = del.left;
                list.add(p.data.getName());
            }
            else if (del.right.left == null) {
                p = del.right; p.left = del.left;
                list.add(p.data.getName()); list.add(p.left.data.getName());
            } else {
                p = del.right;
                list.add(p.data.getName());
                while (p.left != null) {
                    q = p; p = p.left;
                    list.add(q.data.getName()); list.add(p.data.getName());
                }
                q.left = p.right; p.left = del.left; p.right = del.right;
                list.add(q.left.data.getName()); list.add(p.left.data.getName()); list.add(p.right.data.getName());
            }

            assert parent != null;
            if(del == root) {root = p; list.add(root.data.getName());}
            else if (del.data.getName().compareTo(parent.data.getName()) < 0) {
                parent.left = p;
                list.add(parent.left.data.getName());
            }
            else {parent.right = p; list.add(parent.right.data.getName());}

            numEntries--;
            result = del.data; list.add(del.data.getName());
            System.out.println("Employee deleted: " + result.getName());
            System.out.print("Sequence of Nodes visited:\n[");
            for (String i : list) {
                System.out.print(i + ", ");
            }
            System.out.println("]");
        }
        else result = null;
        System.out.println();
        return result;
    } // delete

    private void traverse(Node tree) {
        if (tree != null) {
            traverse(tree.left);
            System.out.println(tree.data);
            traverse(tree.right);
        }
    }

    /**
     * Prints the names and affiliations of all the members in the database in
     * alphabetic order.
     * @pre true
     */
    @Override
    public void displayDB(){traverse(root);}
}

