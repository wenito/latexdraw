package net.sf.latexdraw.gui.hand;

import javafx.scene.paint.Color;
import net.sf.latexdraw.gui.CompositeGUIVoidCommand;
import net.sf.latexdraw.gui.ShapePropInjector;
import net.sf.latexdraw.gui.TestFillingStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeFillingCustomiser;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
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
public class TestHandFillingStyle extends TestFillingStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException {
				super.configure();
				pencil = mock(Pencil.class);
				bindAsEagerSingleton(ShapeFillingCustomiser.class);
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
	public void testControllerActivatedWhenSelection() {
		new CompositeGUIVoidCommand(selectionAddRec, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotFillable() {
		new CompositeGUIVoidCommand(selectionAddAxes, activateHand, updateIns).execute();
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
	public void testNotFillingWidgetsNotEnabledHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectGradStyle, updateIns).execute();
		assertFalse(fillColButton.getParent().isVisible());
	}

	@Test
	public void testNotGradWidgetsNotEnabledHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectHatchingsStyle, updateIns).execute();
		assertFalse(gradAngleField.getParent().isVisible());
	}

	@Test
	public void testNotHatchWidgetsNotEnabledHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectGradStyle, updateIns).execute();
		assertFalse(hatchAngleField.getParent().isVisible());
	}

	@Test
	public void testSelectFillingPlainHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, updateIns).execute();
		FillingStyle style = fillStyleCB.getSelectionModel().getSelectedItem();
		selectStyle.execute(FillingStyle.PLAIN);
		FillingStyle newStyle = fillStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, drawing.getSelection().getShapeAt(1).getFillingStyle());
		assertEquals(newStyle, drawing.getSelection().getShapeAt(2).getFillingStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testPickFillingColourHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, selectPlainStyle, updateIns).execute();
		Color col = fillColButton.getValue();
		pickfillCol.execute();
		assertEquals(fillColButton.getValue(), drawing.getSelection().getShapeAt(1).getFillingCol().toJFX());
		assertEquals(fillColButton.getValue(), drawing.getSelection().getShapeAt(2).getFillingCol().toJFX());
		assertNotEquals(col, fillColButton.getValue());
	}

	@Test
	public void testPickHatchingsColourHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, selectHatchingsStyle, updateIns).execute();
		Color col = hatchColButton.getValue();
		pickhatchCol.execute();
		assertEquals(hatchColButton.getValue(), drawing.getSelection().getShapeAt(1).getHatchingsCol().toJFX());
		assertEquals(hatchColButton.getValue(), drawing.getSelection().getShapeAt(2).getHatchingsCol().toJFX());
		assertNotEquals(col, hatchColButton.getValue());
	}

	@Test
	public void testPickGradStartColourHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, selectGradStyle, updateIns).execute();
		Color col = gradStartColButton.getValue();
		pickgradStartCol.execute();
		assertEquals(gradStartColButton.getValue(), drawing.getSelection().getShapeAt(1).getGradColStart().toJFX());
		assertEquals(gradStartColButton.getValue(), drawing.getSelection().getShapeAt(2).getGradColStart().toJFX());
		assertNotEquals(col, gradStartColButton.getValue());
	}

	@Test
	public void testPickGradEndColourHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, selectGradStyle, updateIns).execute();
		Color col = gradEndColButton.getValue();
		pickgradEndCol.execute();
		assertEquals(gradEndColButton.getValue(), drawing.getSelection().getShapeAt(1).getGradColEnd().toJFX());
		assertEquals(gradEndColButton.getValue(), drawing.getSelection().getShapeAt(2).getGradColEnd().toJFX());
		assertNotEquals(col, gradEndColButton.getValue());
	}

	@Test
	public void testIncrementGradMidHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, updateIns, selectGradStyle).execute();
		double val = gradMidPtField.getValue();
		incrementgradMidPt.execute();
		assertEquals(gradMidPtField.getValue(), drawing.getSelection().getShapeAt(1).getGradMidPt(), 0.0001);
		assertEquals(gradMidPtField.getValue(), drawing.getSelection().getShapeAt(2).getGradMidPt(), 0.0001);
		assertNotEquals(val, gradMidPtField.getValue(), 0.0001);
	}

	@Test
	public void testIncrementGradAngleHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, updateIns, selectGradStyle).execute();
		double val = gradAngleField.getValue();
		incrementgradAngle.execute();
		assertEquals(gradAngleField.getValue(), Math.toDegrees(drawing.getSelection().getShapeAt(1).getGradAngle()), 0.0001);
		assertEquals(gradAngleField.getValue(), Math.toDegrees(drawing.getSelection().getShapeAt(2).getGradAngle()), 0.0001);
		assertNotEquals(val, gradAngleField.getValue(), 0.0001);
	}

	@Test
	public void testIncrementHatchAngleHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, updateIns, selectHatchingsStyle).execute();
		double val = hatchAngleField.getValue();
		incrementhatchAngle.execute();
		assertEquals(hatchAngleField.getValue(), Math.toDegrees(drawing.getSelection().getShapeAt(1).getHatchingsAngle()), 0.0001);
		assertEquals(hatchAngleField.getValue(), Math.toDegrees(drawing.getSelection().getShapeAt(2).getHatchingsAngle()), 0.0001);
		assertNotEquals(val, hatchAngleField.getValue(), 0.0001);
	}

	@Test
	public void testIncrementHatchWidthHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, updateIns, selectHatchingsStyle).execute();
		double val = hatchWidthField.getValue();
		incrementhatchWidth.execute();
		assertEquals(hatchWidthField.getValue(), drawing.getSelection().getShapeAt(1).getHatchingsWidth(), 0.0001);
		assertEquals(hatchWidthField.getValue(), drawing.getSelection().getShapeAt(2).getHatchingsWidth(), 0.0001);
		assertNotEquals(val, hatchWidthField.getValue(), 0.0001);
	}

	@Test
	public void testIncrementHatchSepHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, updateIns, selectHatchingsStyle).execute();
		double val = hatchSepField.getValue();
		incrementhatchSep.execute();
		assertEquals(hatchSepField.getValue(), drawing.getSelection().getShapeAt(1).getHatchingsSep(), 0.0001);
		assertEquals(hatchSepField.getValue(), drawing.getSelection().getShapeAt(2).getHatchingsSep(), 0.0001);
		assertNotEquals(val, hatchSepField.getValue(), 0.0001);
	}
}
