package com.github.lwhite1.tablesaw;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.github.lwhite1.tablesaw.api.DateColumn;
import com.github.lwhite1.tablesaw.api.FloatColumn;
import com.github.lwhite1.tablesaw.api.Table;
import com.github.lwhite1.tablesaw.columns.Column;
import com.github.lwhite1.tablesaw.store.TableMetadata;
import com.google.common.base.Stopwatch;

/**
 *
 */
public class SymbolTest {

  private Table table;
  private Column open = new FloatColumn("open");
  private Column close = new FloatColumn("close");
  private Column hign = new FloatColumn("hign");
  private Column low = new FloatColumn("low");
  private Column volumn = new FloatColumn("volumn");
  private Column date = DateColumn.create("date");

  @Before
  public void setUp() throws Exception {
    table = Table.create("t");
    table.addColumn(open);
    table.addColumn(close);
    table.addColumn(hign);
    table.addColumn(low);
    table.addColumn(volumn);
    table.addColumn(date);
  }

  @Test
  public void testToJson() {
//	  open.addCell("1.0");
	 
	  LocalDate now = LocalDate.now();
		for (int i = 0; i < 1*100; i++) {
			open.addCell("1.1");
			close.addCell("1.1");
			hign.addCell("1.1");
			low.addCell("1");
			volumn.addCell("1250");
			date.addCell(now.plusDays(i).toString());
		}

//	  System.out.println(table.print());
	  
	  String folder = System.getProperty("user.dir") + File.separator + "tmp/tablesaw/testdata";
	  if(!new File(folder).exists()) {
		  new  File(folder).mkdirs();
	  }
	  System.out.println(folder);
	  Stopwatch stopwatch = Stopwatch.createStarted();
	  String dbName = table.save(folder);
	  System.out.println(dbName);
	  stopwatch.stop();
	  System.out.println("write cost: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
	  
	  stopwatch = Stopwatch.createStarted();
	  Table tornadoes = Table.readTable(dbName);
	  stopwatch.stop();
	  System.out.println("read cost: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
	  
	  tornadoes.exportToCsv(folder + "/test.cvs");
	  System.out.println(tornadoes.rowCount());
  }
  
}