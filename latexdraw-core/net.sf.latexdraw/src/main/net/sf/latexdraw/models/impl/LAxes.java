/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.models.impl;

import java.util.ArrayList;
import java.util.List;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.IAxesProp;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.ILine;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle;
import net.sf.latexdraw.models.interfaces.shape.TicksStyle;
import net.sf.latexdraw.view.pst.PSTricksConstants;

/**
 * An implementation of axes.
 */
class LAxes extends LAbstractGrid implements IAxes, LArrowableShape {
	private final List<IArrow> arrows;
	/** The increment of X axe (Dx in PST). */
	private double incrementX;
	/** The increment of Y axe (Dy in PST). */
	private double incrementY;
	/** The distance between each label of the X axe; if 0, the default value will be used (in cm). */
	private double distLabelsX = 1.0;
	/** The distance between each label of the Y axe; if 0, the default value will be used (in cm). */
	private double distLabelsY = 1.0;
	/** Define which labels must be displayed. */
	private PlottingStyle labelsDisplayed = PlottingStyle.ALL;
	/** Define the origin must be shown. */
	private boolean showOrigin = true;
	/** Define how the ticks must be shown. */
	private PlottingStyle ticksDisplayed = PlottingStyle.ALL;
	/** Define the style of the ticks. */
	private TicksStyle ticksStyle = TicksStyle.FULL;
	/** The size of the ticks. */
	private double ticksSize = PSTricksConstants.DEFAULT_TICKS_SIZE * IShape.PPC;
	/** The style of the axes. */
	private AxesStyle axesStyle = AxesStyle.AXES;

	LAxes(final IPoint pt) {
		super(pt);
		arrows = new ArrayList<>();
		// The first arrow is for the bottom of the Y-axis.
		arrows.add(ShapeFactory.INST.createArrow(this));
		// The second arrow is for the left of the X-axis.
		arrows.add(ShapeFactory.INST.createArrow(this));
		// The third arrow is for the top of the Y-axis.
		arrows.add(ShapeFactory.INST.createArrow(this));
		// The fourth arrow is for the right of the X-axis.
		arrows.add(ShapeFactory.INST.createArrow(this));
		incrementX = PSTricksConstants.DEFAULT_DX;
		incrementY = PSTricksConstants.DEFAULT_DY;
	}


	@Override
	public void copy(final IShape s) {
		super.copy(s);
		LArrowableShape.super.copy(s);

		if(s instanceof IAxesProp) {
			final IAxesProp axes = (IAxesProp) s;
			setTicksDisplayed(axes.getTicksDisplayed());
			setTicksSize(axes.getTicksSize());
			setTicksStyle(axes.getTicksStyle());
			setAxesStyle(axes.getAxesStyle());
			setShowOrigin(axes.isShowOrigin());
			setDistLabelsX(axes.getDistLabelsX());
			setDistLabelsY(axes.getDistLabelsY());
			setIncrementX(axes.getIncrementX());
			setIncrementY(axes.getIncrementY());
			setLabelsDisplayed(axes.getLabelsDisplayed());
		}
	}

	@Override
	public void setArrowStyle(ArrowStyle style, final int position) {
		final IArrow arr1 = getArrowAt(position);

		if(style != null && arr1 != null) {
			LArrowableShape.super.setArrowStyle(style, position);
			final int pos = (position == -1 ? arrows.size() - 1 : position) % 4;
			switch(pos) {
				case 0:
					arrows.get(1).setArrowStyle(style);
					break;
				case 1:
					arrows.get(0).setArrowStyle(style);
					break;
				case 2:
					arrows.get(3).setArrowStyle(style);
					break;
				case 3:
					arrows.get(2).setArrowStyle(style);
					break;
			}
		}
	}

	@Override
	public ILine getArrowLine(final IArrow arrow) {
		// For the X-axis
		if(arrow == arrows.get(1) || arrow == arrows.get(3)) {
			return getArrowLineX(arrow == arrows.get(1));
		}
		// For the Y-axis.
		if(arrow == arrows.get(0) || arrow == arrows.get(2)) {
			return getArrowLineY(arrow == arrows.get(2));
		}
		return null;
	}


	/**
	 * @return The line of the Y-axis.
	 */
	private ILine getArrowLineY(final boolean topY) {
		final IPoint pos = getPosition();
		final IPoint p2 = ShapeFactory.INST.createPoint(pos.getX(), pos.getY() - gridEndy * IShape.PPC);
		final IPoint p1 = ShapeFactory.INST.createPoint(pos.getX(), pos.getY() - gridStarty * IShape.PPC);

		if(topY) {
			return ShapeFactory.INST.createLine(p2, p1);
		}
		return ShapeFactory.INST.createLine(p1, p2);
	}


	/**
	 * @return The line of the X-axis.
	 */
	private ILine getArrowLineX(final boolean leftX) {
		final IPoint pos = getPosition();
		final IPoint p2 = ShapeFactory.INST.createPoint(pos.getX() + gridEndx * IShape.PPC, pos.getY());
		final IPoint p1 = ShapeFactory.INST.createPoint(pos.getX() + gridStartx * IShape.PPC, pos.getY());

		if(leftX) {
			return ShapeFactory.INST.createLine(p1, p2);
		}
		return ShapeFactory.INST.createLine(p2, p1);
	}

	@Override
	public AxesStyle getAxesStyle() {
		return axesStyle;
	}

	@Override
	public double getDistLabelsX() {
		return distLabelsX;
	}

	@Override
	public double getDistLabelsY() {
		return distLabelsY;
	}

	@Override
	public PlottingStyle getLabelsDisplayed() {
		return labelsDisplayed;
	}

	@Override
	public PlottingStyle getTicksDisplayed() {
		return ticksDisplayed;
	}

	@Override
	public double getTicksSize() {
		return ticksSize;
	}

	@Override
	public TicksStyle getTicksStyle() {
		return ticksStyle;
	}

	@Override
	public boolean isShowOrigin() {
		return showOrigin;
	}

	@Override
	public void setAxesStyle(final AxesStyle style) {
		if(style != null) {
			axesStyle = style;
		}
	}

	@Override
	public void setDistLabelsX(final double distX) {
		if(distX > 0.0 && MathUtils.INST.isValidCoord(distX)) {
			distLabelsX = distX;
		}
	}

	@Override
	public void setDistLabelsY(final double distY) {
		if(distY > 0.0 && MathUtils.INST.isValidCoord(distY)) {
			distLabelsY = distY;
		}
	}

	@Override
	public void setIncrementX(final double incr) {
		if(incr > 0.0 && MathUtils.INST.isValidCoord(incr)) {
			incrementX = incr;
		}
	}


	@Override
	public void setIncrementY(final double incr) {
		if(incr > 0.0 && MathUtils.INST.isValidCoord(incr)) {
			incrementY = incr;
		}
	}

	@Override
	public void setLabelsDisplayed(final PlottingStyle style) {
		if(style != null) {
			labelsDisplayed = style;
		}
	}

	@Override
	public void setShowOrigin(final boolean show) {
		showOrigin = show;
	}

	@Override
	public void setTicksDisplayed(final PlottingStyle style) {
		if(style != null) {
			ticksDisplayed = style;
		}
	}

	@Override
	public void setTicksSize(final double ticks) {
		if(ticks > 0.0 && MathUtils.INST.isValidCoord(ticks)) {
			ticksSize = ticks;
		}
	}

	@Override
	public void setTicksStyle(final TicksStyle style) {
		if(style != null) {
			ticksStyle = style;
		}
	}

	@Override
	public double getStep() {
		return IShape.PPC;
	}

	@Override
	public boolean isLineStylable() {
		return true;
	}

	@Override
	public boolean isThicknessable() {
		return true;
	}

	@Override
	public double getIncrementX() {
		return incrementX;
	}

	@Override
	public double getIncrementY() {
		return incrementY;
	}

	@Override
	public IPoint getIncrement() {
		return ShapeFactory.INST.createPoint(incrementX, incrementY);
	}

	@Override
	public void setIncrement(final IPoint increment) {
		if(increment != null) {
			setIncrementX(increment.getX());
			setIncrementY(increment.getY());
		}
	}

	@Override
	public IPoint getDistLabels() {
		return ShapeFactory.INST.createPoint(distLabelsX, distLabelsY);
	}

	@Override
	public void setDistLabels(final IPoint distLabels) {
		if(distLabels != null) {
			setDistLabelsX(distLabels.getX());
			setDistLabelsY(distLabels.getY());
		}
	}

	@Override
	public List<IArrow> getArrows() {
		return arrows;
	}
}
