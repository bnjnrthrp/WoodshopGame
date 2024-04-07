import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class WorkshopModelTest {
  WorkshopModel ws;

  TableSaw tableSaw;
  MiterSaw miterSaw;
  Router router;

  ADTList<Wood> woodInventory;
  ADTList<Tool> tools;
  ADTList<Jig> jigs;

  @Before
  public void setUp(){
    ws = new WorkshopModel();
    ws.loadWorkshop();
    ws.buyWood(WoodType.plywood);
    ws.buyWood(WoodType.dimensional);

    }

  @Test
  public void goToWork() {
    ws.goToWork();
    assertEquals(100, ws.getBalance());
    }

  @Test
  public void loadWorkshop() {
    assertEquals(0, ws.getBalance());
    TableSaw ts = new TableSaw();
    MiterSaw ms = new MiterSaw();
    Router rt = new Router();

    ADTList<Tool> toolShed = new ADTList<>();
    toolShed.add(ts);
    toolShed.add(ms);
    toolShed.add(rt);

    // Uses to string as the two arrays will be different, but contents equal
    assertEquals(toolShed.toString(), ws.getTools().toString());
    }

  @Test
  public void getItem(){
    ws.buyWood(WoodType.plywood);
    Object actual = ws.getWood( 0);
    Plywood expected = new Plywood();

    assertEquals(expected, actual);
  }

  @Test
  public void buyWood() {
    ADTList<Wood> expected = new ADTList<>();
    expected.add(new Plywood());
    expected.add(new DimensionalWood());

    assertEquals(expected.toString(), ws.getWoodInventory().toString());

    }

  @Test
  public void unlockTool() {
    assertFalse(ws.getTools().get(0).isUnlocked());
    ws.getTools().get(0).unlock();
    assertTrue(ws.getTools().get(0).isUnlocked());
    }

  @Test
  public void cutWood() {
    ws.unlockTool(ws.getTools().get(0));
    ws.unlockTool(ws.getJigs().get(0));

    double[] expected1 = {48, 30, .75};
    double[] expected2 = {96, 18, .75};
    ws.cutWood(0,0, 30);

    ws.cutWood(0, 0, 0, 48);

    ADTList<Wood> expected = new ADTList<>();
    expected.add(new Plywood(expected1)); // Original piece cross cut
    expected.add(new DimensionalWood());  // Untouched dimensional wood
    expected.add(new Plywood(expected2)); // Original remaining from rip cut
    expected.add(new Plywood(expected1)); // Remaining from cross cut

    assertEquals(expected.toString(), ws.getWoodInventory().toString());
  }

  //Tests for when the tool hasn't been unlocked
  @Test (expected=IllegalStateException.class)
  public void testCutWoodLockedTool(){
    ws.cutWood(0, 0, 48);
  }
  // Test for when the jig hasn't been unlocked
  @Test (expected= IllegalStateException.class)
  public void testCutWoodLockedJig(){
    ws.cutWood(0, 0, 0, 48);
  }

  @Test
  public void makeFurniture() {

    }

  @Test
  public void getWoodInventory() {
    ADTList<Wood> expected = new ADTList<>();
    expected.add(new Plywood());
    expected.add(new DimensionalWood());

    assertEquals(expected.toString(), ws.getWoodInventory().toString());
    }

  @Test
  public void getTools() {
    ADTList<Tool> expected = new ADTList<>();
    expected.add(new TableSaw());
    expected.add(new MiterSaw());
    expected.add(new Router());

    assertEquals(expected.toString(), ws.getTools().toString());
    }

  @Test
  public void getJigs() {
    ADTList<Jig> expected = new ADTList<>();
    expected.add(Jig.crossCutSled);
    expected.add(Jig.tapeMeasure);
    expected.add(Jig.circleCut);

    assertEquals(expected.toString(), ws.getJigs().toString());
    }
}