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
package net.sf.latexdraw.handlers;

import javafx.beans.binding.Bindings;
import javafx.scene.shape.Rectangle;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import net.sf.latexdraw.models.interfaces.shape.IPoint;

/**
 * A handler that changes the start/end angle of an arc.
 * @author Arnaud BLOUIN
 */
public class ArcAngleHandler extends Rectangle implements Handler {
	/** Defines whether the handled angle is the starting or the ending angle. */
	private final boolean start;

	/**
	 * Creates and initialises an arc angle handler.
	 * @param isStart Defines whether the handled angle is the starting or the ending angle.
	 * @since 3.0
	 */
	public ArcAngleHandler(final boolean isStart) {
		super();
		start = isStart;
		setWidth(DEFAULT_SIZE);
		setHeight(DEFAULT_SIZE);
		setStroke(null);
		setFill(DEFAULT_COLOR);
	}

	public void setCurrentArc(final IArc arc) {
		translateXProperty().unbind();
		translateYProperty().unbind();

		translateXProperty().bind(Bindings.createDoubleBinding(() -> getPosition(arc).getX() - DEFAULT_SIZE / 2d,
			arc.angleStartProperty(), arc.angleEndProperty(), arc.getPtAt(0).xProperty(), arc.getPtAt(2).xProperty()));
		translateYProperty().bind(Bindings.createDoubleBinding(() -> getPosition(arc).getY() - DEFAULT_SIZE / 2d,
			arc.angleStartProperty(), arc.angleEndProperty(), arc.getPtAt(0).yProperty(), arc.getPtAt(2).yProperty()));
	}

	private IPoint getPosition(final IArc arc) {
		return start ? arc.getStartPoint() : arc.getEndPoint();
	}
}
