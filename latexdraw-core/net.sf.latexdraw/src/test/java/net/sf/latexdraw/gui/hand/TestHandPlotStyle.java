package net.sf.latexdraw.gui.hand;

import net.sf.latexdraw.gui.CompositeGUIVoidCommand;
import net.sf.latexdraw.gui.ShapePropInjector;
import net.sf.latexdraw.gui.TestPlotStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapePlotCustomiser;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
import net.sf.latexdraw.util.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestHandPlotStyle extends TestPlotStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException {
				super.configure();
				pencil = mock(Pencil.class);
				bindAsEagerSingleton(ShapePlotCustomiser.class);
				bindAsEagerSingleton(Hand.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindToInstance(Pencil.class, pencil);
			}
		};
	}

	@Test
	public void testControllerNotActivatedWhenSelectionEmpty() {
		new CompositeGUIVoidCommand(activateHand, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testControllerActivatedWhenSelectionGrid() {
		new CompositeGUIVoidCommand(selectionAddPlot, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotGrid() {
		new CompositeGUIVoidCommand(selectionAddRec, activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionEmpty() {
		new CompositeGUIVoidCommand(activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testSelectDOTSStyleHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.DOTS);
		PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot)drawing.getSelection().getShapeAt(1)).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot)drawing.getSelection().getShapeAt(2)).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectCCURVEStyleHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.CCURVE);
		PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot)drawing.getSelection().getShapeAt(1)).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot)drawing.getSelection().getShapeAt(2)).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectPOLYGONStyleHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.POLYGON);
		PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot)drawing.getSelection().getShapeAt(1)).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot)drawing.getSelection().getShapeAt(2)).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectECURVEStyleHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.ECURVE);
		PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot)drawing.getSelection().getShapeAt(1)).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot)drawing.getSelection().getShapeAt(2)).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectCURVEStyleHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		selectplotStyleCB.execute(PlotStyle.ECURVE);
		PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.CURVE);
		PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot)drawing.getSelection().getShapeAt(1)).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot)drawing.getSelection().getShapeAt(2)).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectLINEStyleHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		PlotStyle style = plotStyleCB.getSelectionModel().getSelectedItem();
		selectplotStyleCB.execute(PlotStyle.LINE);
		PlotStyle newStyle = plotStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot)drawing.getSelection().getShapeAt(1)).getPlotStyle());
		assertEquals(plotStyleCB.getSelectionModel().getSelectedItem(), ((IPlot)drawing.getSelection().getShapeAt(2)).getPlotStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testIncrementnbPtsSpinnerHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		int val = nbPtsSpinner.getValue();
		incrementnbPtsSpinner.execute();
		assertEquals((int)nbPtsSpinner.getValue(), ((IPlot)drawing.getSelection().getShapeAt(1)).getNbPlottedPoints());
		assertEquals((int)nbPtsSpinner.getValue(), ((IPlot)drawing.getSelection().getShapeAt(2)).getNbPlottedPoints());
		assertNotEquals(val, (int)nbPtsSpinner.getValue());
	}

	@Test
	public void testIncrementminXSpinnerHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		double val = minXSpinner.getValue();
		incrementminXSpinner.execute();
		assertEquals(minXSpinner.getValue(), ((IPlot)drawing.getSelection().getShapeAt(1)).getPlotMinX(), 0.0001);
		assertEquals(minXSpinner.getValue(), ((IPlot)drawing.getSelection().getShapeAt(2)).getPlotMinX(), 0.0001);
		assertNotEquals(val, minXSpinner.getValue(), 0.0001);
	}

	@Test
	public void testIncrementmaxXSpinnerHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		double val = maxXSpinner.getValue();
		incrementmaxXSpinner.execute();
		assertEquals(maxXSpinner.getValue(), ((IPlot)drawing.getSelection().getShapeAt(1)).getPlotMaxX(), 0.0001);
		assertEquals(maxXSpinner.getValue(), ((IPlot)drawing.getSelection().getShapeAt(2)).getPlotMaxX(), 0.0001);
		assertNotEquals(val, maxXSpinner.getValue(), 0.0001);
	}

	@Test
	public void testIncrementxScaleSpinnerHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		double val = xScaleSpinner.getValue();
		incrementxScaleSpinner.execute();
		assertEquals(xScaleSpinner.getValue(), ((IPlot)drawing.getSelection().getShapeAt(1)).getXScale(), 0.0001);
		assertEquals(xScaleSpinner.getValue(), ((IPlot)drawing.getSelection().getShapeAt(2)).getXScale(), 0.0001);
		assertNotEquals(val, xScaleSpinner.getValue(), 0.0001);
	}

	@Test
	public void testIncrementyScaleSpinnerHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		double val = yScaleSpinner.getValue();
		incrementyScaleSpinner.execute();
		assertEquals(yScaleSpinner.getValue(), ((IPlot)drawing.getSelection().getShapeAt(1)).getYScale(), 0.0001);
		assertEquals(yScaleSpinner.getValue(), ((IPlot)drawing.getSelection().getShapeAt(2)).getYScale(), 0.0001);
		assertNotEquals(val, yScaleSpinner.getValue(), 0.0001);
	}

	@Test
	public void testSelectpolarCBHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddPlot, selectionAddPlot, updateIns).execute();
		boolean sel = polarCB.isSelected();
		clickpolarCB.execute();
		assertEquals(polarCB.isSelected(), ((IPlot)drawing.getSelection().getShapeAt(1)).isPolar());
		assertEquals(polarCB.isSelected(), ((IPlot)drawing.getSelection().getShapeAt(2)).isPolar());
		assertNotEquals(sel, polarCB.isSelected());
	}
}
