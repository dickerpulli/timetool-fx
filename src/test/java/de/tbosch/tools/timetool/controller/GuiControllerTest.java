package de.tbosch.tools.timetool.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.tbosch.tools.timetool.AbstractSpringTest;
import de.tbosch.tools.timetool.model.Timeslot;

public class GuiControllerTest extends AbstractSpringTest {

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

	@Autowired
	private GuiController guiController;

	@Test
	public void testExpandActiveProjectAndMonth() {
		DefaultMutableTreeNode treeNodeRoot = new DefaultMutableTreeNode("root");
		DefaultMutableTreeNode treeNodeProject1 = new DefaultMutableTreeNode("customer1 - project1");
		DefaultMutableTreeNode treeNodeTimeslot1 = new DefaultMutableTreeNode("12 Uhr - 13 Uhr");
		DefaultMutableTreeNode treeNodeProject2 = new DefaultMutableTreeNode("customer1 - project2");
		DefaultMutableTreeNode treeNodeTimeslot2 = new DefaultMutableTreeNode("12 Uhr - 13 Uhr");
		treeNodeProject1.add(treeNodeTimeslot1);
		treeNodeProject2.add(treeNodeTimeslot2);
		treeNodeRoot.add(treeNodeProject1);
		treeNodeRoot.add(treeNodeProject2);
		JTree timeslotsTree = new JTree(treeNodeRoot);

		guiController.expandActiveProjectAndMonth(treeNodeRoot, timeslotsTree);

		assertEquals(true, timeslotsTree.isExpanded(0));
		assertEquals(false, timeslotsTree.isExpanded(1));
	}

	@Test
	public void testGetAllTimeslotsPerProjectAsTreeNode() throws ParseException {
		Timeslot timeslot1 = new Timeslot();
		Timeslot timeslot2 = new Timeslot();
		Timeslot timeslot3 = new Timeslot();
		Timeslot timeslot4 = new Timeslot();
		Timeslot timeslot5 = new Timeslot();
		Timeslot timeslot6 = new Timeslot();
		timeslot1.setStarttime(sdf.parse("01.01.2009 12:00"));
		timeslot2.setStarttime(sdf.parse("02.01.2009 12:00"));
		timeslot3.setStarttime(sdf.parse("01.02.2009 12:00"));
		timeslot4.setStarttime(sdf.parse("01.03.2009 12:00"));
		timeslot5.setStarttime(sdf.parse("01.04.2009 00:00"));
		timeslot6.setStarttime(sdf.parse("30.04.2009 23:59"));
		List<Timeslot> all = new ArrayList<Timeslot>();
		all.add(timeslot1);
		all.add(timeslot2);
		all.add(timeslot3);
		all.add(timeslot4);
		all.add(timeslot5);
		all.add(timeslot6);

		TreeNode treeNode = guiController.createTreeFromTimeslotsPerProject();

		assertNotNull(treeNode);
	}

}
