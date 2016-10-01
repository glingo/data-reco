/*
 * Grid Control Created by Matthew Bull for Quantigma Ltd requires: dynapi.api.*
 * dynapi.events.* dynapi.util.thread dynapi.util.pathanim dynapi.gui.scrollbar
 * dynapi.gui.viewport dynapi.gui.scrollpane dynapi.gui.label
 * enigma.gui.editlabel
 */
//subclass editlabel and add a couple of properties

function GridEditLabel(text, row, column)
{
	this.EditLabel = Label;
	this.EditLabel(text);
	this.row = row;
	this.columnNo = column;
}
GridEditLabel.prototype = new Label();
// plus override formatText to change data in datagrid to match edited label and
// trigger resort
GridEditLabel.prototype.formatText = function(txt)
{
	if (this.created && this.row)
		this.row.grid.datagrid[this.row.rowno][this.columnNo] = txt;

	if (this.row.grid.sortInd == this.columnNo)
	{
		this.row.grid.sortBy();
		return this.row.grid.datagrid[this.row.rowno][this.columnNo];
	}
	else
		return txt;
}
//grid row class
function GridRow(grid, rowno, selected)
{
	this.DynLayer = DynLayer;
	this.DynLayer();
	this.setSize(grid.getWidth(), 20);
	this.rowno = rowno;
	this.grid = grid;
	this.defaultSelected= selected;

	this.onCreate(function()
	{
		this.createRow();
	});
}
GridRow.prototype = new DynLayer();
GridRow.prototype.rowSelect = function(multi)
{
	if (this.grid.selectedrow.length > 0 && this.grid.selectedrow[0] != this.rowno)
	{
		if (multi && this.grid.multiselect)
		{
			nselect =[];
			for (var i = Math.min(this.grid.selectedrow[0], this.rowno); i <= Math.max(this.grid.selectedrow[0], this.rowno); i++)
				nselect[nselect.length] = i;

			this.grid.setSelected(nselect);
		}
		else
			this.grid.setSelected([this.rowno]);
	}
	else
		this.grid.setSelected([this.rowno]);
}
GridRow.prototype.createRow = function()
{
	var cellevents = new EventListener();
	cellevents.onmouseup = function(e)
	{
		e.preventBubble();
	}
	cellevents.onclick = function(e)
	{
		e.preventBubble();
	}
	cellevents.onmousedown = function(e)
	{
		e.preventBubble();
		var rowobj = e.getSource().parent;
		rowobj.rowSelect(e.shiftKey);
	}
	this.grid.cellgrid[this.rowno] = new Array();
	this.setBgColor(this.defaultSelected ? '#9999CC' : '#FFFFFF');
	for (var i = 0; i < this.grid.colHead.length; i++)
	{
		if (this.grid.editflags[i])
		{
			var l = new GridEditLabel(this.grid.datagrid[this.rowno][i], this, i);
			l.setPadding(4);
		}
		else
		{
			var l = new Label(this.grid.datagrid[this.rowno][i]);
			l.selectable = false;
			l.setWrap(false);
			l.setPadding(4);
		}
		l.setSize(this.grid.colWidths[i], 20);
		l.setLocation(this.grid.colOffset[i], 0);
		this.addChild(l);
		l.addEventListener(cellevents);
		this.grid.cellgrid[this.rowno][i] = l}
}

function Grid(chead, hrh, datag, selected, ef, cwidth)
{
	this.DynLayer = DynLayer;
	this.DynLayer();
	this.datagrid = datag ? datag : new Array();

	this.cellgrid = new Array();
	this.selectedrow = new Array();
	this.colHead = chead ? chead : new Array();
	this.editflags = ef ? ef : new Array(this.colHead.length);
	this.colWidth = cwidth ? cwidth : new Array(this.colHead.length);
	this.hrHeight = hrh ? hrh : 20;
	this.colOffset =[];
	this.sortInd = null;
	this.sortDir = true;
	this.multiselect = false;
	this.defaultSelected= selected;

	this.onCreate(function()
	{
		this.wCreate();
	});
}

Grid.prototype = new DynLayer();
Grid.prototype.wCreate = function()
{
	this.setBgColor('#999999');
	this.headerRow = new DynLayer();
	this.cont = new DynLayer();
	this.scrollpane = new ViewPane(this.cont);
	this.cont.setSize(this.getWidth() - this.scrollpane.vscBar.getWidth(), this.getHeight());
	this.cont.setBgColor('#FFFFFF');
	this.headerRow.setWidth(this.getWidth() - this.scrollpane.vscBar.getWidth());
	this.headerRow.setHeight(this.hrHeight);
	this.headerRow.setLocation(0, 0);
	this.addChild(this.headerRow);
	this.prcFree = 100;
	this.hr =[];
	this.totFreeWidth = 0;
	this.redrawing = false;
	//this.updateListener="null";

	mainevent = new EventListener(this);
	mainevent.onresize = function(e)
	{
		t = e.getSource();
		t.cont.setSize(t.getWidth() - t.scrollpane.vscBar.getWidth(), t.getHeight());
		t.scrollpane.setSize(t.getWidth(), t.getHeight() - t.headerRow.getHeight());
		t.headerRow.setWidth(t.getWidth() - t.scrollpane.vscBar.getWidth());
		t.setColWidths();
		for (var i = 0; i < t.cellgrid.length; i++)
		{
			t.rows[i].setWidth(t.getWidth());
			for (var ii = 0; ii < t.cellgrid[i].length; ii++)
			{
				t.cellgrid[i][ii].setX(t.colOffset[ii]);
				t.cellgrid[i][ii].setWidth(t.colWidths[ii]);
			}
		}
	}
	this.addEventListener(mainevent);
	var hrevents = new EventListener(this);
	hrevents.onclick = function(e)
	{
		var b = e.getSource();
		var t = b.parent.parent;
		if (t.sortInd == b.column)
		{
			t.sortDir = t.sortDir ? false : true;
			t.sortBy();
		}
		else
			t.sortBy(b.column, true);
	}
	for (var i = 0; i < this.colHead.length; i++)
	{
		this.hr[i] = new Button(this.colHead[i]);
		this.hr[i].addEventListener(hrevents);
		this.hr[i].column = i;
		this.hr[i].setSize(200, 20);
		this.headerRow.addChild(this.hr[i]);
		if (this.colWidth[i])
			this.prcFree -= this.colWidth[i];
		else
			this.totFreeWidth += this.colHead[i].length;
	}

	this.setColWidths();
	this.scrollpane.setSize(this.getWidth(), this.getHeight() - this.headerRow.getHeight());
	this.scrollpane.setLocation(0, this.headerRow.getHeight());
	this.addChild(this.scrollpane);
	this.addRows();
	this.selectedrow= this.defaultSelected > -1 ? [this.defaultSelected] : [];
}

Grid.prototype.setColWidths = function()
{
	this.colWidths = new Array();
	for (var i = 0; i < this.colHead.length; i++)
	{
		if (this.colWidth[i])
		{
			//alert(this.headerRow.w*(this.colWidth[i]/100))
			this.colWidths[i] = Math.round(this.headerRow.getWidth() * (this.colWidth[i] / 100));
		}
		else
			this.colWidths[i] = Math.round(this.headerRow.getWidth() * (this.prcFree * (this.colHead[i].length / this.totFreeWidth)) / 100);

		//alert(this.colWidths);
		// bit of a hack to ensure tot fill cos of rounds above
		this.colOffset[i] = (i > 0) ? (this.colOffset[i - 1] + this.colWidths[i - 1]) : 0;
		this.colWidths[i] = (i == this.colHead.length - 1) ? (this.headerRow.getWidth() - this.colOffset[i]) : this.colWidths[i];
		this.hr[i].setLocation(this.colOffset[i], 0);
		this.hr[i].setWidth(this.colWidths[i]);
	}
}
Grid.prototype.addRows = function()
{
	var voffset = 0;
	this.rows = new Array();
	for (var i = 0; i < this.datagrid.length; i++)
	{
		this.datagrid[i][this.datagrid[i].length] = false;
		this.rows[i] = new GridRow(this, i, this.defaultSelected == i);
		this.rows[i].setLocation(0, (i * this.hrHeight));
		this.cont.addChild(this.rows[i]);
		voffset += this.rows[i].getHeight();
	}
	this.cont.setHeight(voffset);
}
Grid.prototype.addRow = function(rowArr, selected)
{
	rowArr[rowArr.length] = selected;
	this.datagrid[this.datagrid.length] = rowArr;
	newInd = this.rows.length;
	this.rows[newInd] = new GridRow(this, newInd);
	this.rows[newInd].setLocation(0, newInd * this.hrHeight);
	this.cont.addChild(this.rows[newInd]);
	this.cont.setHeight((newInd + 1) * this.hrHeight);
	
	if (this.sortInd != null)
		this.sortBy();
	else
		this.reDraw();
}
Grid.prototype.removeRows = function(ind)
{
	for (var i = 0; i > ind.length; i++)
	{
		this.datagrid[ind[i]] = 0;
		this.rows[ind[i]].setVisiblity(false);
	}
	this.selectedrow =[];
	this.reDraw();
}
Grid.prototype.setSelected = function(rowNos)
{
	for (var i = 0; i < this.selectedrow.length; i++)
	{
		this.rows[this.selectedrow[i]].setBgColor('#FFFFFF');
		this.datagrid[this.selectedrow[i]][this.datagrid[this.selectedrow[i]].length - 1] = false;
	}
	
	for (var i = 0; i < rowNos.length; i++)
	{
		this.rows[rowNos[i]].setBgColor('#9999CC');
		this.datagrid[rowNos[i]][this.datagrid[rowNos[i]].length - 1] = true;
	}
	this.selectedrow = rowNos;

	if (!this.redrawing)
		this.updateListener();
}
Grid.prototype.getSelected = function()
{
	rselect =[];
	for (var i = 0; i < this.selectedrow.length; i++)
	{
		rselect[rselect.length] = this.datagrid[this.selectedrow[i]];
		delete(rselect[rselect.length - 1][rselect[rselect.length - 1].length - 1]);
	}
	return rselect;
}
Grid.prototype.sortBy = function(ind, dir)
{
	if (this.sortInd != null)
		this.hr[this.sortInd].setCaption(this.colHead[this.sortInd]);

	this.sortInd = (ind >= 0) ? ind : this.sortInd;
	this.sortDir = dir ? dir : this.sortDir;
	headText = (this.sortDir) ? "<font face='wingdings'>ò</font>" : "<font face='wingdings'>ñ</font>";
	this.hr[this.sortInd].setCaption(this.colHead[this.sortInd] + headText);
	var ci = 1;
	while (ci > 0)
	{
		ci = 0;
		for (var i = 0; i < this.datagrid.length - 1; i++)
		{
			var c1 = this.datagrid[i][this.sortInd];
			var c2 = this.datagrid[i + 1][this.sortInd];
			//check for type (thanks doug) and hack around javascripts bad
			// string comparisons
			if (typeof(c1) == 'string')
			{
				var compLen = Math.min(c1.length, c2.length);
				c1 = c1.substring(0, compLen);
				c2 = c2.substring(0, compLen);
			}
			var c = this.sortDir ? (c1 > c2) : (c1 < c2);
			if (c)
			{
				var s = this.datagrid[i];
				this.datagrid[i] = this.datagrid[i + 1];
				this.datagrid[i + 1] = s;
				ci++;
			}
		}
	}
	this.reDraw();
}
Grid.prototype.reDraw = function()
{
	nselect =[];
	for (var i = 0; i < this.cellgrid.length; i++)
		if (this.datagrid != 0)
		{
			for (var ii = 0; ii < this.cellgrid[i].length - 1; ii++)
			{
				this.cellgrid[i][ii].setText(this.datagrid[i][ii]);
			}
			if (this.datagrid[i][this.datagrid[i].length - 1])
			{
				nselect[nselect.length] = i;
			}
		}

	this.redrawing = true;
	this.setSelected(nselect);
	this.redrawing = false;
}
