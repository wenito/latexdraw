/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import net.sf.latexdraw.actions.shape.AlignShapes;
import net.sf.latexdraw.actions.shape.DistributeShapes;
import net.sf.latexdraw.actions.shape.MirrorShapes;
import net.sf.latexdraw.models.interfaces.shape.IGroup;

/**
 * This instrument transforms (mirror, etc.) the selected shapes.
 * @author Arnaud Blouin
 */
public class ShapeTransformer extends ShapePropertyCustomiser implements Initializable {
	/** The widget to mirror horizontally. */
	@FXML private Button mirrorH;
	/** The widget to mirror vertically. */
	@FXML private Button mirrorV;
	/** The widget to BOTTOM align the shapes. */
	@FXML private Button alignBot;
	/** The widget to LEFT align the shapes. */
	@FXML private Button alignLeft;
	/** The widget to RIGHT align the shapes. */
	@FXML private Button alignRight;
	/** The widget to TOP align the shapes. */
	@FXML private Button alignTop;
	/** The widget to middle horizontal align the shapes. */
	@FXML private Button alignMidHoriz;
	/** The widget to middle vertical align the shapes. */
	@FXML private Button alignMidVert;
	/** The widget to BOTTOM-vertically distribute the shapes. */
	@FXML private Button distribVertBot;
	/** The widget to equal-vertically distribute the shapes. */
	@FXML private Button distribVertEq;
	/** The widget to middle-vertically distribute the shapes. */
	@FXML private Button distribVertMid;
	/** The widget to TOP-vertically distribute the shapes. */
	@FXML private Button distribVertTop;
	/** The widget to equal-horizontally distribute the shapes. */
	@FXML private Button distribHorizEq;
	/** The widget to LEFT-horizontally distribute the shapes. */
	@FXML private Button distribHorizLeft;
	/** The widget to middle-horizontally distribute the shapes. */
	@FXML private Button distribHorizMid;
	/** The widget to RIGHT-horizontally distribute the shapes. */
	@FXML private Button distribHorizRight;
	@FXML private VBox mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeTransformer() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
		alignBot.setUserData(AlignShapes.Alignment.BOTTOM);
		alignLeft.setUserData(AlignShapes.Alignment.LEFT);
		alignMidHoriz.setUserData(AlignShapes.Alignment.MID_HORIZ);
		alignMidVert.setUserData(AlignShapes.Alignment.MID_VERT);
		alignRight.setUserData(AlignShapes.Alignment.RIGHT);
		alignTop.setUserData(AlignShapes.Alignment.TOP);
		distribHorizEq.setUserData(DistributeShapes.Distribution.HORIZ_EQ);
		distribHorizLeft.setUserData(DistributeShapes.Distribution.HORIZ_LEFT);
		distribHorizMid.setUserData(DistributeShapes.Distribution.HORIZ_MID);
		distribHorizRight.setUserData(DistributeShapes.Distribution.HORIZ_RIGHT);
		distribVertBot.setUserData(DistributeShapes.Distribution.VERT_BOT);
		distribVertEq.setUserData(DistributeShapes.Distribution.VERT_EQ);
		distribVertMid.setUserData(DistributeShapes.Distribution.VERT_MID);
		distribVertTop.setUserData(DistributeShapes.Distribution.VERT_BOT);
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void update(final IGroup shape) {
		setActivated(hand.isActivated() && shape.size() > 1);
	}

	@Override
	protected void configureBindings() throws IllegalAccessException, InstantiationException {
		buttonBinder(AlignShapes.class).on(alignBot, alignLeft, alignMidHoriz, alignMidVert, alignRight, alignTop).init((a, i) -> {
			a.setAlignment((AlignShapes.Alignment) i.getWidget().getUserData());
			a.setCanvas(canvas);
			a.setShape(pencil.canvas.getDrawing().getSelection().duplicateDeep(false));
		}).bind();

		buttonBinder(MirrorShapes.class).on(mirrorH, mirrorV).init((a, i) -> {
			a.setHorizontally(i.getWidget() == mirrorH);
			a.setShape(pencil.canvas.getDrawing().getSelection().duplicateDeep(false));
		}).bind();

		buttonBinder(DistributeShapes.class).on(distribHorizEq, distribHorizLeft, distribHorizMid, distribHorizRight,distribVertBot,
			distribVertEq, distribVertMid, distribVertTop).init((a, i) -> {
			a.setDistribution((DistributeShapes.Distribution) i.getWidget().getUserData());
			a.setCanvas(canvas);
			a.setShape(pencil.canvas.getDrawing().getSelection().duplicateDeep(false));
		}).bind();
	}
}
