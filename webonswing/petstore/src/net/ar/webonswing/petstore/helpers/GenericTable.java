package net.ar.webonswing.petstore.helpers;

import java.awt.*;

import javax.swing.*;

public class GenericTable extends JPanel
{
	protected String[] columnsNames;
	private int rowCount= 0;

	public GenericTable(String[] aColumnsNames)
	{
		columnsNames= aColumnsNames;
		setLayout(new GridLayout(0, 1));
		setName("generic-table");
	}

	public void addRow(JComponent[] aComponents)
	{
		JPanel row= new JPanel();
		row.setLayout(new GridLayout(1, columnsNames.length));

		int columnCount= 1;
		for (int i= 0; i < aComponents.length; i++)
			row.add(aComponents[i]).setName("column" + columnCount++);

		add(row).setName("row");

		rowCount++;
	}

	public Component[] getRow(int i)
	{
		return ((Container)getComponent(i)).getComponents();
	}

	public String[] getColumnsNames()
	{
		return columnsNames;
	}

	public void setColumnsNames(String[] aColumnsNames)
	{
		columnsNames= aColumnsNames;
	}

	public int getRowCount()
	{
		return rowCount;
	}

	public void setRowCount(int aRowCount)
	{
		this.rowCount= aRowCount;
	}
}