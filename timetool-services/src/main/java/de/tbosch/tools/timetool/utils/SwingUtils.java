package de.tbosch.tools.timetool.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * Utilities for Swing Components.
 * 
 * @author Thomas Bosch
 */
public final class SwingUtils {

	private static final List<TreeNode> LEAFS = new ArrayList<TreeNode>();

	private static final long DOUBLE_CLICK_DELAY = 300;

	private static long timeLastClick = 0;

	/**
	 * Utils.
	 */
	private SwingUtils() {
		// Utils
	}

	/**
	 * Cheks if the given mouse event is the second click of a double click.
	 * 
	 * @param e The MouseEvent
	 * @return <code>true</code> if click is double click
	 */
	public static boolean isDoubleClick(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			long time = System.currentTimeMillis();
			if (timeLastClick + DOUBLE_CLICK_DELAY > time) {
				// double click
				return true;
			}
			timeLastClick = time;
		}
		return false;
	}

	/**
	 * Checks if all selected nodes in the tree are leaf nodes.
	 * 
	 * @param tree The JTree
	 * @return <code>true</code> if there exists at least one selected node and all nodes are leafs
	 */
	public static boolean isSelectedNodesLeaf(JTree tree) {
		List<TreeNode> selectedNodes = getSelectedNodes(tree);
		if (selectedNodes.isEmpty()) {
			return false;
		}
		for (TreeNode selectedNode : selectedNodes) {
			if (!selectedNode.isLeaf()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Centers the given window on the screen.
	 * 
	 * @param window The window
	 */
	public static void centerOnScreen(Window window) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) (screenSize.getWidth() - window.getWidth()) / 2;
		int y = (int) (screenSize.getHeight() - window.getHeight()) / 2;
		window.setLocation(x, y);
	}

	/**
	 * Returns the selected element in a jtree.
	 * 
	 * @param tree The JTree
	 * @param type The type of the user object
	 * @param <T> The type of the user object
	 * @return The selected element - NULL, if not existent
	 * @throws IllegalArgumentException if more than one Element is selected
	 */
	public static <T> T getSelectedLeafUserObject(JTree tree, Class<T> type) {
		List<T> selectedLeafElements = getSelectedLeafsUserObjects(tree, type);
		if (!selectedLeafElements.isEmpty()) {
			if (selectedLeafElements.size() > 1) {
				throw new IllegalArgumentException("there is more than one element selected in the tree");
			}
			return selectedLeafElements.get(0);
		}
		return null;
	}

	/**
	 * Returns all selected elements of the user-object type in a jtree. It iterates over all leafs.
	 * 
	 * @param tree The JTree
	 * @param type The type of the user object
	 * @param <T> The type of the user object
	 * @return The selected elements - empty list, if nothing selected
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getSelectedLeafsUserObjects(JTree tree, Class<T> type) {
		List<T> selectedLeafElements = new ArrayList<T>();
		List<TreeNode> selectedNodes = getSelectedLeafs(tree);
		for (TreeNode selectedNode : selectedNodes) {
			if (selectedNode instanceof DefaultMutableTreeNode) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) selectedNode;
				Object userObject = node.getUserObject();
				if (type.isInstance(userObject)) {
					T selectedElement = (T) userObject;
					selectedLeafElements.add(selectedElement);
				}
			}
		}
		return selectedLeafElements;
	}

	/**
	 * Returns all selected elements of the user-object type in a jtree. It iterates over all nodes.
	 * 
	 * @param tree The JTree
	 * @param type The type of the user object
	 * @param <T> The type of the user object
	 * @return The selected elements - empty list, if nothing selected
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getSelectedNodesUserObjects(JTree tree, Class<T> type) {
		List<T> selectedNodeElements = new ArrayList<T>();
		List<TreeNode> selectedNodes = getSelectedNodes(tree);
		for (TreeNode selectedNode : selectedNodes) {
			if (selectedNode instanceof DefaultMutableTreeNode) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) selectedNode;
				Object userObject = node.getUserObject();
				if (type.isInstance(userObject)) {
					T selectedElement = (T) userObject;
					selectedNodeElements.add(selectedElement);
				}
			}
		}
		return selectedNodeElements;
	}

	/**
	 * Returns all selected leafs in a jtree.
	 * 
	 * @param tree The JTree
	 * @return The selected leafs - empty list, if nothing selected
	 */
	public static List<TreeNode> getSelectedLeafs(JTree tree) {
		List<TreeNode> selectedLeafs = new ArrayList<TreeNode>();
		List<TreeNode> selectedNodes = getSelectedNodes(tree);
		for (TreeNode selectedNode : selectedNodes) {
			if (selectedNode.isLeaf()) {
				selectedLeafs.add(selectedNode);
			}
		}
		return selectedNodes;
	}

	/**
	 * Returns all selected nodes in a jtree.
	 * 
	 * @param tree The JTree
	 * @return The selected nodes - empty list, if nothing selected
	 */
	public static List<TreeNode> getSelectedNodes(JTree tree) {
		List<TreeNode> selectedNodes = new ArrayList<TreeNode>();
		TreePath[] selectionPaths = tree.getSelectionPaths();
		if (selectionPaths != null) {
			for (TreePath selectionPath : selectionPaths) {
				if (selectionPath != null) {
					Object lastPathComponent = selectionPath.getLastPathComponent();
					if (lastPathComponent instanceof TreeNode) {
						TreeNode treeNode = (TreeNode) lastPathComponent;
						selectedNodes.add(treeNode);
					}
				}
			}
		}
		return selectedNodes;
	}

	/**
	 * Gets all leefs.
	 * 
	 * @param tree The jtree
	 * @return The list of leefs
	 */
	public static List<TreeNode> getAllLeafs(JTree tree) {
		TreePath rootPath = tree.getPathForRow(0);
		TreeNode rootNode = (TreeNode) rootPath.getPath()[0];
		List<TreeNode> allLeafs = new ArrayList<TreeNode>();
		synchronized (LEAFS) {
			LEAFS.clear();
			putLeafs(rootNode);
			allLeafs.addAll(LEAFS);
			LEAFS.clear();
		}
		return allLeafs;
	}

	/**
	 * Puts all found leafs in the static class field. Beware of threading.
	 * 
	 * @param node The root node
	 */
	@SuppressWarnings("unchecked")
	private static void putLeafs(TreeNode node) {
		Enumeration<TreeNode> children = node.children();
		while (children.hasMoreElements()) {
			TreeNode nextElement = children.nextElement();
			if (nextElement.isLeaf()) {
				LEAFS.add(nextElement);
			} else {
				putLeafs(nextElement);
			}
		}
	}

	/**
	 * Checks if the father node has the given child.
	 * 
	 * @param father The father node
	 * @param child The child node to search for
	 * @return <code>true</code> if the father has this child
	 */
	public static boolean contains(TreeNode father, TreeNode child) {
		for (int i = 0; i < father.getChildCount(); i++) {
			TreeNode actualChild = father.getChildAt(i);
			if (actualChild.equals(child)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a list of all children of this node.
	 * 
	 * @param node The father node
	 * @return The list
	 */
	public static List<TreeNode> getAllChildren(TreeNode node) {
		List<TreeNode> children = new ArrayList<TreeNode>();
		for (int i = 0; i < node.getChildCount(); i++) {
			TreeNode actualChild = node.getChildAt(i);
			children.add(actualChild);
		}
		return children;
	}

}
